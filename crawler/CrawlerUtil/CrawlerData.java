package CrawlerUtil;

import StrUtil.UrlStr;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * CS 121 Team BEES
 * Dongguang You - 80122555
 * Robert Long - 74151707
 */
public class CrawlerData implements Serializable {
    private CrawlDataList urls = new CrawlDataList();
    private HashMap<String, Integer> nurl_visits = new HashMap<String, Integer>();

    public CrawlerData() {
    }

    public CrawlerData(ResultSet rs) throws SQLException {
        while (rs.next()) {
            String url = rs.getString("Url");
            urls.add(url);
            String Nurl = UrlStr.normalizeURL(url);
            if (nurl_visits.containsKey(Nurl))
                nurl_visits.put(Nurl, nurl_visits.get(Nurl) + 1);
            else nurl_visits.put(UrlStr.normalizeURL(url), 1);
        }
    }

    public synchronized boolean visited(String url) {
        url = UrlStr.stripHttp(url);
        if (urls.contains(url)) return true;
        String Nurl = UrlStr.normalizeURL(url);
        if (nurl_visits.containsKey(Nurl) && nurl_visits.get(Nurl) > 5) {
            return true;
        }
        return false;
    }

    public synchronized void addURLToQ(String url) {
        urls.add(url);
    }

    public synchronized void addNURL(String Nurl) {
        if (nurl_visits.containsKey(Nurl))
            nurl_visits.put(Nurl, nurl_visits.get(Nurl) + 1);
        else nurl_visits.put(Nurl, 1);
    }

    public synchronized CrawlDataList getUrl_id() {
        return this.urls;
    }
}
