package StrUtil;

/**
 * CS 121 Team BEES
 * Dongguang You - 80122555
 * Robert Long - 74151707
 */
public class UrlStr {
    public static String stripHttp(String url) {
        String rest = url;
        if(url.startsWith("http://")) rest = url.replace("http://", "");
        else if ( url.startsWith("https://")) rest = url.replace("https://", "");
        return rest;
    }

    public static String normalizeURL(String url) {
        if(!url.contains("?")){
            return url;
        }
        return url.substring(0, url.indexOf("?"));
    }

    public static String getBeforeUrlQuery(String str) {
        int queryPos = str.indexOf('?');
        if (queryPos != -1) return str.substring(0, queryPos);
        return str;
    }
}
