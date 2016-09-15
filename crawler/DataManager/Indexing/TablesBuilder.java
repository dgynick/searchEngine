package DataManager.Indexing;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/15/15.
 */
public class TablesBuilder {
    Connection conn;
    String name = "";
    TablesQuery query;

    public TablesBuilder(String name, Connection conn) {
        this.name = name;
        this.conn = conn;
        query = new TablesQuery(name, conn);
    }

    public TablesQuery getQuery() {
        return query;
    }

    public String getName() {
        return name;
    }

    public void constructIndexTables() throws SQLException {
        constructDb();
        constructTermsTable();
        constructParsedDocTable();
        constructIndexVectorTable();
        constructTfIdfTable();
        constructDocFreTable();

    }

    public void dropIndexTables() throws SQLException {
        conn.prepareStatement(
                "DROP TABLE IF EXISTS 121Crawler.DocFreq_" + name + ", " +
                        "121Crawler.ParsedDoc_" + name + ", " +
                        "121Crawler.IndexVector_" + name + ", " +
                        "121Crawler.TfIdf_" + name + ", " +
                        "121Crawler.Terms_" + name + ";")
                .executeUpdate();
    }

    private void constructDb() throws SQLException {
        conn.prepareStatement("create database if not exists 121Crawler;").executeUpdate();
    }

    private void constructDocFreTable() throws SQLException {
        conn.prepareStatement(
                "CREATE TABLE if not exists 121Crawler.DocFreq_" +
                        name +
                        "( id INT NOT NULL AUTO_INCREMENT," +
                        "  termid INT NOT NULL UNIQUE," +
                        "  doc_freq INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  INDEX fk_termid_idx_" + name + " (termid ASC)," +
                        "  CONSTRAINT fk_termid_docfreq_" + name +
                        "    FOREIGN KEY (termid)" +
                        "    REFERENCES 121Crawler.Terms_" + name + " (id)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION);")
                .executeUpdate();
    }

    private void constructIndexVectorTable() throws SQLException {
        conn.prepareStatement(
                "create table if not exists IndexVector_" + name +
                        "(termid Integer, docid Integer, freq Integer);")
                .executeUpdate();
    }

    private void constructParsedDocTable() throws SQLException {
        conn.prepareStatement(
                "CREATE TABLE if not exists 121Crawler.ParsedDoc_" +
                        name +
                        "( id INT NOT NULL AUTO_INCREMENT," +
                        "  doc_id INT NOT NULL UNIQUE," +
                        "  PRIMARY KEY (id, doc_id));")
                .executeUpdate();
    }

    private void constructTfIdfTable() throws SQLException {
        conn.prepareStatement(
                "CREATE TABLE if not exists 121Crawler.TfIdf_" +
                        name +
                        "(    id INT NOT NULL AUTO_INCREMENT," +
                        "    termid INT NOT NULL," +
                        "    docid INT NOT NULL," +
                        "    score DOUBLE NOT NULL," +
                        "    PRIMARY KEY (id)," +
                        "    INDEX fk_termid_idx_" + name + " (termid ASC)," +
                        "    INDEX fk_docid_idx_" + name + " (docid ASC)," +
                        "    CONSTRAINT fk_termid_" + name +
                        "    FOREIGN KEY (termid)" +
                        "    REFERENCES 121Crawler.Terms_" + name + " (id)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION," +
                        "    CONSTRAINT fk_docid_" + name +
                        "    FOREIGN KEY (docid)" +
                        "    REFERENCES 121Crawler.Website (Id)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION);")
                .executeUpdate();
    }

    private void constructTermsTable() throws SQLException {
        conn.prepareStatement(
                "create table if not exists 121Crawler.Terms_"
                        + name
                        + "(id Integer, term text, primary key(id));")
                .executeUpdate();
    }

}
