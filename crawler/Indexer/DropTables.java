package Indexer;

import DataManager.DbManager;
import DataManager.Indexing.TablesBuilder;

import java.sql.SQLException;

/**
 * Created by rbtlong on 5/15/15.
 */
public class DropTables {
    public static void main(String args[])
            throws SQLException, ClassNotFoundException {
        new TablesBuilder("CoreTextDemo", DbManager.getConn()).dropIndexTables();
        new TablesBuilder("TitleDemo", DbManager.getConn()).dropIndexTables();
        new TablesBuilder("HeadDemo", DbManager.getConn()).dropIndexTables();
        new TablesBuilder("BodyDemo", DbManager.getConn()).dropIndexTables();

    }
}
