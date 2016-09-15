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
public class HeadSource extends HtmlSource {
    @Override
    public TokenDoc getSource(int id)
            throws SQLException, ClassNotFoundException {
        ResultSet rs = getHtml(id);
        while (rs.next()) {
            String html = rs.getString(2);
            return new TokenDoc(rs.getInt(1), parseHead(html), rs.getString(3));
        }
        return null;
    }

    private String parseHead(String html) {
        Document doc = Jsoup.parse(html);
        Elements elms = doc.select("meta");
        String result = "";
        for (Element elm : elms) {
            if (elm.attr("name").toLowerCase().contains("description"))
                result += (elm.attr("content") + " ");
            if (elm.attr("name").toLowerCase().contains("keyword"))
                result += (elm.attr("content") + " ");
            if (elm.attr("name").toLowerCase().contains("author"))
                result += (elm.attr("content") + " ");
        }
        return result;
    }
}
