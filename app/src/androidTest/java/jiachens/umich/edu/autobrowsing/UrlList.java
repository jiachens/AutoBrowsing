package jiachens.umich.edu.autobrowsing;

public class UrlList {

    private static String[] ItemUrls= {
            "beirut.eecs.umich.edu",
            "www.youtube.com",
            "www.amazon.com",
            "www.qq.com",
            "www.google.com"
    };

    public static int numberOfUrls() {
        return ItemUrls.length;
    }

    public static String getUrl(int position) {
        return ItemUrls[position];
    }
}

