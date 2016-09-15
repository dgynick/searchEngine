package Indexer.TokenizationStrategies;

import DataManager.DbManager;
import Indexer.DocumentSource.IDocSource;
import Indexer.DocumentSource.TokenDoc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/15/15.
 */
public class TextContentSource implements IDocSource {
    @Override
    public TokenDoc getSource(int id)
            throws SQLException, ClassNotFoundException {
        ResultSet rs = DbManager.getTextContent(id).executeQuery();
        if (rs.next())
            return new TokenDoc(rs.getInt("Id"),
                    rs.getString("Text_Content"),
                    rs.getString("Url"));
        return null;
    }
}
