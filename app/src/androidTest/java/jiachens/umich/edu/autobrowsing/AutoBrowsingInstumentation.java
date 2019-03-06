package jiachens.umich.edu.autobrowsing;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AutoBrowsingInstumentation {

    private static String configFolderName= Environment.getExternalStorageDirectory().getPath() + "/Config";
    private static String outputFileFoleder= Environment.getExternalStorageDirectory().getPath() + "/Data";
    private static String configFileName="config";
    private static String urlList="urlList";
    private static final String CHROME_PACKAGE
            = "com.android.chrome";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String TAG = "AutoBrowsing";
    private UiDevice mDevice;

    int time;
    ArrayList<String> url;
    ArrayList<Integer> urldeltat;
    File cacheFile;
    long startOfEntering;
    long endOfLoading;

    public AutoBrowsingInstumentation(){

        url=new ArrayList<String>();
        urldeltat=new ArrayList<Integer>();
        startOfEntering=-1;
        endOfLoading = -1;
    }


    @Before
    public void startChromeFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());
        // Start from the home screen
        mDevice.pressHome();

        // Launch chrome
        Context context = getApplicationContext();

        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(CHROME_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // get cache dir
        String[] file = context.getCacheDir().toString().split(context.getPackageName());
        cacheFile = new File(file[0] + CHROME_PACKAGE + file[1]);
        Log.d(TAG,"cache dir is: " + cacheFile.toString());

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(CHROME_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void start(){
        Log.d(TAG, "start chrome control.");
        testControl();
    }

    public void testControl(){
        // read config file
        readConfig();
        readUrlList();
        actionEnterUrls();
    }

    public void readConfig(){
        File file = new File(configFolderName+"/"+configFileName);
        Log.d(TAG,"read configuration: " + file.getAbsolutePath());
        file.getParentFile().mkdirs();
        if (file.exists()){ // Compatible with QoE doctor code (deprecated)
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line=null;
                Log.d(TAG, "file exist: " + file.getAbsolutePath());
                while ((line=br.readLine())!=null){
                        String[] lines=line.split("=");
                        String entry=lines[0];
                        if (entry.equals("Time")){
                            time=Integer.parseInt(lines[1]);
                        }
                }
                br.close();
            }catch (Exception e){
                Log.e(TAG, e.toString());
                e.printStackTrace();
            }
        }else{
            Log.d(TAG,"no special config.");
        }
    }

    void readUrlList(){
        Log.d(TAG,"read url list from "+configFolderName+"/"+urlList);
        File file = new File(configFolderName+"/"+urlList);
        file.getParentFile().mkdirs();
        if (file.exists()){   // Compatible with QoE doctor code (deprecated)
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line=null;
                while ((line=br.readLine())!=null){
                    String entries[] = line.split("[ \t]+");
                    int j=0;
                    String urlname=null;
                    String urldeltatime=null;
                    for (;j<entries.length;j++){
                        if (entries[j].length()>0){
                            urlname=entries[j];
                            break;
                        }
                    }
                    j++;
                    for (;j<entries.length;j++){
                        if (entries[j].length()>0){
                            urldeltatime=entries[j];
                            break;
                        }
                    }
                    if (urlname!=null && urldeltatime!=null){
                        url.add(urlname);
                        urldeltat.add(Integer.parseInt(urldeltatime));
                    }
                }
                br.close();
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                e.printStackTrace();
            }
        }else {   // read from UrlList Class
            Log.d(TAG, "no url in storage, read url list from CODE");
            int numUrl = UrlList.numberOfUrls();
            for (int i = 0; i < numUrl; i++) {
                url.add(UrlList.getUrl(i));
            }
        }
    }


    public int actionEnterUrls(){
        Log.d(TAG,"in actionEnterUrls()");
        int numUrl = UrlList.numberOfUrls();
        for (int i = 0; i < numUrl; i++) {
            UiObject2 urlBarInitial = mDevice.findObject(By.res(CHROME_PACKAGE, "search_box_text"));
            if (urlBarInitial != null){
                urlBarInitial.click();
            }
            UiObject2 urlBar = mDevice.findObject(By.res(CHROME_PACKAGE, "url_bar"));
            while (urlBar == null){
                urlBar = mDevice.findObject(By.res(CHROME_PACKAGE, "url_bar"));
            }
            urlBar.click();
            urlBar.setText(url.get(i));
            UiObject2 urlButton = mDevice.findObject(By.text(url.get(i)));
            urlButton.click();
            startOfEntering = (new Date()).getTime();
            UiObject2 progressBar = mDevice.findObject(By.clazz(ProgressBar.class));
            while (progressBar != null) {
                progressBar = mDevice.findObject(By.clazz(ProgressBar.class));
            }
            endOfLoading = (new Date()).getTime();
            Log.d(TAG, "PLT of " + url.get(i) + " is: " + String.valueOf(endOfLoading - startOfEntering));
        }
        return 0;
    }


}
