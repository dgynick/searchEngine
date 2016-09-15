package Crawl;

import edu.uci.ics.crawler4j.url.WebURL;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * CS 121 Team BEES
 * Dongguang You - 80122555
 * Robert Long - 74151707
 */
public class WebSite implements Serializable {
    private String url;
    private String contentText;
    private String contentHtml;
    private ArrayList<WebURL> outgoingLinks = null;
    private WebSite referrer = null;


    public WebSite(String url, String contentText, String contentHtml,
                   Set<WebURL> outgoing, WebSite referrer) {
        this.url = url;
        this.contentText = contentText;
        this.contentHtml = contentHtml;
        this.outgoingLinks = new ArrayList<>(outgoing);
        this.referrer = referrer;
    }

    public WebSite(){

    }

    public WebSite(String url) {
        this.url = url;
    }

    public static void persist(WebSite sites) {
        try {

            File fi = new File("data.ser");
            if (!fi.exists()) fi.createNewFile();
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

    public static WebSite load() {
        WebSite sites = new WebSite();
        File[] files = new File("dump/").listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".ser");
            }
        });

        for (File fi : files) {
            if (fi.exists()) {
                try {
                    FileInputStream fis = new FileInputStream(fi);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    sites = (WebSite) ois.readObject();
                    fis.close();
                    ois.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return sites;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof String
                && o.equals(url);
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    @Override
    public String toString(){
        return url;
    }

    public ArrayList<WebURL> getOutgoingLinks() {
        return outgoingLinks;
    }

    public void setOutgoingLinks(ArrayList<WebURL> outgoingLinks) {
        this.outgoingLinks = outgoingLinks;
    }

    public String getInlineOutgoingLinks() {
        String inline = "";
        for (int i = 0; i < outgoingLinks.size(); ++i) {
            inline += outgoingLinks.get(i).getURL();
            if (i != outgoingLinks.size() - 1)
                inline += "[;;]";
        }
        return inline;
    }

    public WebSite getReferrer() {
        return referrer;
    }

    public void setReferrer(WebSite referrer) {
        this.referrer = referrer;
    }
}
