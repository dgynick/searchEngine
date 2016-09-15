package Crawl;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.util.Timer;

/**
 * CS 121 Team BEES
 * Dongguang You - 80122555
 * Robert Long - 74151707
 */
public class Controller {
    static final String usrAgent = "UCI Inf141-CS121 crawler 801225555 74151797";
    static final int politenessDelay = 500;
    public static Timer t = new Timer();

    int crawlers = 1;
    CrawlConfig config;
    CrawlController ctrl;

    public Controller(int n) throws Exception {
        crawlers = n;
        config = initConfig();
        ctrl = initController(config);
        ctrl.addSeed("http://www.ics.uci.edu");
    }

    public Controller() throws Exception {
        config = initConfig();
        ctrl = initController(config);
        ctrl.addSeed("http://www.ics.uci.edu");
    }

    public static void main(String[] args) throws Exception {
        final Controller control = new Controller(4);
        control.begin();
    }

    private static CrawlController initController(CrawlConfig config) throws Exception {
        PageFetcher pgFetch = new PageFetcher(config);
        RobotstxtConfig botTxtCfg = new RobotstxtConfig();
        RobotstxtServer botTxtServ = new RobotstxtServer(botTxtCfg, pgFetch);
        return new CrawlController(config, pgFetch, botTxtServ);
    }

    private static CrawlConfig initConfig() {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("res/crawling/root");
        config.setMaxDepthOfCrawling(-1);
        config.setPolitenessDelay(politenessDelay);
        config.setUserAgentString(usrAgent);
        config.setResumableCrawling(true);
        return config;
    }

    public void begin() {
        ctrl.start(Crawler.class, crawlers);
    }

    public void stop() {
        ctrl.shutdown();
    }

    public void reset() {
        stop();


        try {
            ctrl = initController(config);
            ctrl.addSeed("http://www.ics.uci.edu");
        } catch (Exception e) {
            e.printStackTrace();
        }

        begin();

    }


}
