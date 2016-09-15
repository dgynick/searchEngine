package CrawlerUtil;

import Crawl.WebSite;

import java.io.*;
import java.util.ArrayList;

/**
 * CS 121 Team BEES
 * Dongguang You - 80122555
 * Robert Long - 74151707
 */
public class Collectibles implements Serializable {

    ArrayList<WebSite> sites = new ArrayList<>();

    public Collectibles() {

    }

    public static void persist(Collectibles sites) {
        try {
            File fi = new File("data.ser");
            if(!fi.exists()) fi.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(fi);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(sites);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Collectibles load(){
        Collectibles sites = new Collectibles();
/*        File fi = new File("data.ser");
        if(fi.exists()){
            try {
                FileInputStream fis = new FileInputStream(fi);
                ObjectInputStream ois = new ObjectInputStream(fis);
                sites = (Collectibles)ois.readObject();
                fis.close();
                ois.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }*/
        return sites;
    }

    public boolean visited(String url) {
        for (WebSite w : sites)
            if (w.getUrl().equals(url))
                return true;
        return false;
    }

    public boolean visited(WebSite site) {
        return sites.contains(site);
    }

    public void add(String url) {
        sites.add(new WebSite(url));
    }

    public void add(WebSite web) {
        sites.add(web);
    }

    public int getVisited() {
        return sites.size();
    }

    public WebSite get(int i) {
        return sites.get(i);
    }
}
