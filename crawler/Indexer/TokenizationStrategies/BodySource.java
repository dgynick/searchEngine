package Indexer.TokenizationStrategies;

import Indexer.DocumentSource.TokenDoc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/16/15.
 */
public class BodySource extends HtmlSource {
    @Override
    public TokenDoc getSource(int id)
            throws SQLException, ClassNotFoundException {
        ResultSet rs = getHtml(id);
        while (rs.next()) {
            String html = rs.getString(2);
            return new TokenDoc(rs.getInt(1), parseBody(html), rs.getString(3));
        }
        return null;
    }

    private String parseBody(String html) {
        Document doc = Jsoup.parse(html);
        Elements elms = doc.select("body");
        String result = "";
        for (Element elm : elms) {
            result += parseBold(elm);
            result += parseItalics(elm);
            result += parseUnderline(elm);
            result += parseItalics(elm);
            result += parseEmphasis(elm);
            result += parseAnchor(elm);
            result += parseListItem(elm);
            result += parseHeading(elm);
        }
        return result;
    }

    private String parseBold(Element elm) {
        String result = "";
        for (Element subelm : elm.select("b"))
            result += subelm.text();
        return result;
    }

    private String parseItalics(Element elm) {
        String result = "";
        for (Element subelm : elm.select("i"))
            result += subelm.text();
        return result;
    }

    private String parseUnderline(Element elm) {
        String result = "";
        for (Element subelm : elm.select("u"))
            result += subelm.text();
        return result;
    }

    private String parseEmphasis(Element elm) {
        String result = "";
        for (Element subelm : elm.select("em"))
            result += subelm.text();
        return result;
    }

    private String parseAnchor(Element elm) {
        String result = "";
        for (Element subelm : elm.select("a"))
            result += subelm.text();
        return result;
    }

    private String parseListItem(Element elm) {
        String result = "";
        for (Element subelm : elm.select("li"))
            result += subelm.text();
        return result;
    }

    private String parseHeading(Element elm) {
        String result = "";
        for (Element subelm : elm.select("h1"))
            result += subelm.text();
        for (Element subelm : elm.select("h2"))
            result += subelm.text();
        for (Element subelm : elm.select("h3"))
            result += subelm.text();
        for (Element subelm : elm.select("h4"))
            result += subelm.text();
        for (Element subelm : elm.select("h5"))
            result += subelm.text();
        for (Element subelm : elm.select("h6"))
            result += subelm.text();
        return result;
    }
}
