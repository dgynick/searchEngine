package Indexer.TokenizationStrategies;

import Indexer.DocumentSource.TokenDoc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/15/15.
 */
public class TitleSource extends HtmlSource {
    @Override
    public TokenDoc getSource(int id)
            throws SQLException, ClassNotFoundException {
        ResultSet rs = getHtml(id);
        while (rs.next()) {
            String html = rs.getString(2);
            return new TokenDoc(rs.getInt(1), parseTitle(html), rs.getString(3));
        }
        return null;
    }

    private String parseTitle(String html) {
        String lower = html.toLowerCase();
        int begin = lower.indexOf("<title>");
        int end = lower.indexOf("</title>");
        if (begin > -1 && end > -1)
            return html.substring(begin + "<title>".length(), end);
        return "";
    }
}
