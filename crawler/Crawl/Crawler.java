package Crawl;

import CrawlerUtil.CrawlDataList;
import CrawlerUtil.CrawlerData;
import DataManager.DbManager;
import StrUtil.UrlStr;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * CS 121 Team BEES
 * Dongguang You - 80122555
 * Robert Long - 74151707
 */
public class Crawler extends WebCrawler {
    public final static String[] doNotCrawl = new String[]{
            "calendar.ics.uci.edu/calendar.php",
            "calendar.ics.uci.edu",
            "ironwood.ics.uci.edu"
    };
    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);
    private static final Pattern FILTERS = Pattern.compile(
            ".*(\\.(css|js|bmp|gif|jpe?g"
                    + "|ppt|pptx|ps|java|cs|c|cc|h|exe|zip|tar|dat|jar|txt|csv|R"
                    + "|tgz|bin|doc|docx|gz|mat|mso|iso|js|xml|json|thmx|data|lisp|lua"
                    + "|png|tiff?|mid|mp2|mp3|mp4"
                    + "|wav|avi|mov|mpe?g|ram|m4v|pdf"
                    + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    private static ArrayList<IHandleGeneralListener> generalEvts = new ArrayList<>();
    private static Crawler _instance = null;
    private static CrawlerData _crawlData;
    private int blocked, filtered, notInDom, visited;

    public Crawler() {
        _instance = this;
        loadFromDb();
    }

    public static Crawler getInstance(){
        return _instance;
    }

    public static boolean isBlockedUrl(String s) {
        String stripped = UrlStr.stripHttp(s);
        for (String itm : doNotCrawl)
            if (itm.equals(stripped) || stripped.contains(itm)) return true;
        return false;
    }

    public static boolean isInDom(String url) {
        return (url.contains(".") &&
                url.contains(".ics.uci.edu") &&
                url.indexOf(".ics.uci.edu") - url.indexOf(".") == 0)
                || (url.indexOf(".") == url.indexOf("ics.uci.edu") + 3);
    }

    public static void addGeneralListener(IHandleGeneralListener pt) {
        generalEvts.add(pt);
    }

    public static void onGeneralEvt() {
        for (IHandleGeneralListener evt : generalEvts)
            evt.Triggered();
    }

    private void loadFromDb() {
        try {
            populateCrawlerData();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void populateCrawlerData() throws SQLException, ClassNotFoundException {
        ResultSet rs = DbManager.getPrepStmt_AllUrlAndNormalized_Vists().executeQuery();
        _crawlData = new CrawlerData(rs);
        logger.info(String.format("Crawler Data Populated with %d websites.\n", _crawlData.getUrl_id().size()));
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        String rejectReason = "";
        boolean allowed = true;

        if (!isInDom(href)) {
            rejectReason = "Not in main domain.";
            notInDom++;
            allowed = false;
        } else if (isBlockedUrl(href)) {
            rejectReason = "Blocked site.";
            blocked++;
            allowed = false;
        } else if (FILTERS.matcher(UrlStr.getBeforeUrlQuery(href)).matches()) {
            rejectReason = "Matches filter pattern.";
            filtered++;
            allowed = false;
        } else if (checkVisited_AddIfNotVisited(href)) {
            rejectReason = "This site has been visited.";
            visited++;
            allowed = false;
        }

        if (!allowed)
            logger.info(String.format("[Rejected] %s {%s}", rejectReason, href));
        onGeneralEvt();

        return allowed;
    }

    public boolean checkVisited_AddIfNotVisited(String href) {
        boolean visited = _crawlData.visited(href);
        if (!visited) {
            href = UrlStr.stripHttp(href);
            _crawlData.addURLToQ(href);
            _crawlData.addNURL(UrlStr.normalizeURL(href));
            logger.info(String.format("Added '%s' to filtered visited.", href));
        }
        return visited;
    }

    @Override
    public void visit(Page page){
        String url = page.getWebURL().getURL();
        logger.info("Visiting " + url);

        if(page.getParseData() instanceof HtmlParseData){
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html  = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            logger.info(String.format("Outgoing Links for '%s': %s", url, links.size()));
            WebSite referrer = null;
            WebSite ws = new WebSite(UrlStr.stripHttp(url), text, html, links, referrer);
            persist(url, ws);
            onGeneralEvt();
        }

    }

    private void persist(String url, WebSite ws) {
        try {
            DbManager.getPrepStmt_WebsiteInsert(ws).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        logger.info(String.format("[%d] '%s' has been persisted.", _crawlData.getUrl_id().size(), url));
    }

    public int getRejected() {
        return filtered + blocked + notInDom;
    }

    public int getBlocked() {
        return blocked;
    }

    public int getFiltered() {
        return filtered;
    }

    public int getTries() {
        return _crawlData.getUrl_id().size();
    }

    public CrawlDataList getSites() {
        return _crawlData.getUrl_id();
    }

    public int getVisited() {
        return visited;
    }
}
