package jiachens.umich.edu.autobrowsing;

public class UrlList {

    private static String[] ItemUrls= {
            "http://141.212.110.44:4000/www.youtube.com/www.youtube.com/index.html",
            "http://141.212.110.44:4000/www.google.com/www.google.com/index.html",
            "http://141.212.110.44:4000/www.amazon.com/www.amazon.com/index.html",
            "http://141.212.110.44:4000/www.baidu.com/www.baidu.com/index.html",
            "http://141.212.110.44:4000/www.tmall.com/www.tmall.com/index.html",
            "http://141.212.110.44:4000/www.yahoo.com/www.yahoo.com/index.html",
            "http://141.212.110.44:4000/www.netflix.com/www.netflix.com/index.html",
            "http://141.212.110.44:4000/www.espn.com/www.espn.com/index.html",
            "http://141.212.110.44:4000/www.qq.com/www.qq.com/index.html",
            "http://141.212.110.44:4000/www.twitter.com/www.twitter.com/index.html",
            "http://141.212.110.44:4000/www.facebook.com/www.facebook.com/index.html",
            "http://141.212.110.44:4000/www.stackoverflow.com/www.stackoverflow.com/index.html",
            "http://141.212.110.44:4000/www.weibo.com/www.weibo.com/index.html",
            "http://141.212.110.44:4000/www.github.com/www.github.com/index.html",
            "http://141.212.110.44:4000/www.taobao.com/www.taobao.com/index.html",
            "http://141.212.110.44:4000/www.wikipedia.org/www.wikipedia.org/index.html",
            "http://141.212.110.44:4000/www.reddit.com/www.reddit.com/index.html",
            "http://141.212.110.44:4000/www.cnn.com/www.cnn.com/index.html",
            "http://141.212.110.44:4000/www.weather.com/www.weather.com/index.html",
            "http://141.212.110.44:4000/www.linkedin.com/www.linkedin.com/index.html"
    };

    public static int numberOfUrls() {
        return ItemUrls.length;
    }

    public static String getUrl(int position) {
        return ItemUrls[position];
    }
}

