package Tests.DomTests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by rbtlong on 5/16/15.
 */
public class DomTest2 {

    public static void main(String[] args) {
        String exampleString =
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"" +
                        " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                        "<head><title>asdfadsfasdfasdf</title>" +
                        "<meta name=\"description\" content=\"Free Web tutorials\">\n" +
                        "<meta name=\"keywords\" content=\"HTML,CSS,XML,JavaScript\">\n" +
                        "<meta name=\"author\" content=\"Hege Refsnes\">" +
                        "</head>" +
                        "<body>" +
                        "body" +
                        "<ul><li/><li/><li/><li/><li/><li/></ul>" +
                        "<table>" +
                        "<tbody>" +
                        "<td><tr><b>asdfasdfasdfljalsdfj</b></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr></td>" +
                        "<td><tr></tr></td>" +
                        "</tbody>" +
                        "</table>" +
                        "</body>" +
                        "</html>";
/*        Document doc = Jsoup.parse(exampleString);
        Elements elms = doc.select("meta");
        for(Element elm : elms){
            System.out.println(elm.getAllElements());
        }*/
        parseHead(exampleString);
    }

    private static String parseHead(String html) {
        Document doc = Jsoup.parse(html);
        Elements elms = doc.select("body");
        String result = "";
        for (Element elm : elms) {
            Elements subelms = elm.select("b");
            for (Element subelm : subelms) {
                result += subelm.text();
            }
        }
        return result;
    }
}
