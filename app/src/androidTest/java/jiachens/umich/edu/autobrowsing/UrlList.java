package jiachens.umich.edu.autobrowsing;

public class UrlList {

    private static String[] ItemUrls= {
            "http://141.212.110.44:4000/www.youtube.com/www.youtube.com/index.html",
            "http://141.212.110.44:4000/www.google.com/www.google.com/index.html",
            "http://141.212.110.44:4000/www.amazon.com/www.amazon.com/index.html"
    };

    public static int numberOfUrls() {
        return ItemUrls.length;
    }

    public static String getUrl(int position) {
        return ItemUrls[position];
    }
}

