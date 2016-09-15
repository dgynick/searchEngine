package Indexer.TokenizationStrategies;

import DataManager.DbManager;
import Indexer.DocumentSource.IDocSource;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/15/15.
 */
public abstract class HtmlSource implements IDocSource {
    protected ResultSet getHtml(int docId)
            throws SQLException, ClassNotFoundException {
        return DbManager.getHtmlContent(docId).executeQuery();
    }

}
