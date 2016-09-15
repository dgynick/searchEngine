package Indexer.DocumentSource;

import java.sql.SQLException;

/**
 * Created by rbtlong on 5/15/15.
 */
public interface IDocSource {
    TokenDoc getSource(int id) throws SQLException, ClassNotFoundException;
}
