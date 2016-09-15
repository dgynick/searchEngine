package DataManager.Indexing;

import DataManager.DbManager;
import Indexer.IndexerUtil.TermDocument;
import Indexer.IndexerUtil.TfIdfResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/24/15.
 */
public class TableFinal {
    static PreparedStatement
            insertTfIdf = null,
            updateTerm = null,
            insertTerm = null,
            termCheck = null;

    public static PreparedStatement getAllTerm()
            throws SQLException, ClassNotFoundException {
        return DbManager.getConn().prepareCall("select * from Final_tfidf");
    }

    public static PreparedStatement insertTerm(TfIdfResult res)
            throws SQLException, ClassNotFoundException {
        //inserts term into new final term table
        if (insertTerm == null)
            insertTerm = DbManager.getConn().prepareStatement(
                    "insert into Final_tfidf (,,) " +
                            "values(,,)");
        //insertTerm.setInt
        //insertTerm.setInt
        //insertTerm.setDouble
        return null;
    }

    public static PreparedStatement insertTfIdf(TermDocument tDoc, double score)
            throws SQLException, ClassNotFoundException {
        if (insertTfIdf == null)
            insertTfIdf = DbManager.getConn().prepareStatement(
                    "insert into FinalTfIdf (term,docid,suffix,termid,score) " +
                            "VALUES (?, ?, ?, ?, ?);");
        insertTfIdf.setString(1, tDoc.getTerm());
        insertTfIdf.setInt(2, tDoc.getDocId());
        insertTfIdf.setString(3, tDoc.getSuffix());
        insertTfIdf.setInt(4, tDoc.getTermid());
        insertTfIdf.setDouble(5, score);
        return insertTfIdf;
    }

    public static PreparedStatement getAllTfIdf()
            throws SQLException, ClassNotFoundException {
        return DbManager.getConn().prepareStatement("select * from FinalTfIdf;");
    }

    public static PreparedStatement updateTfIdf(TermDocument tDoc, double w)
            throws SQLException, ClassNotFoundException {
        return DbManager.getConn().prepareStatement(
                "update FinalTfIdf f set score = " + w +
                        " where f.docid = " + tDoc.getDocId() +
                        " and f.term = " + tDoc.getTerm() +
                        " and f.termid = " + tDoc.getDocId() +
                        " and f.term = " + tDoc.getTerm() + ";");
    }

    public static PreparedStatement getSelectRelevantDoc(String[] terms)
            throws SQLException, ClassNotFoundException {
        if (terms == null || terms.length == 0) {
            return null;
        }
        String s = "(select distinct docid from FinalTfIdf_normalized where term like \"%" + terms[0] + "%\")";
        for (int i = 1; i < terms.length; i++) {
            s += "union (select distinct docid from FinalTfIdf_normalized where term like \"%" + terms[i] + "%\")";
        }
        return DbManager.getConn().prepareStatement(s);
    }

    public static boolean exists(TermDocument termDoc)
            throws SQLException, ClassNotFoundException {
        if (termCheck == null)
            termCheck = DbManager.getConn().prepareStatement(
                    "select * from FinalTfIdf f" +
                            " where f.term = ?" +
                            " and f.docid = ? " +
                            " and f.suffix = ? " +
                            " and f.termid = ?;");
        termCheck.setString(1, termDoc.getTerm());
        termCheck.setInt(2, termDoc.getDocId());
        termCheck.setString(3, termDoc.getSuffix());
        termCheck.setInt(4, termDoc.getTermid());
        ResultSet rs = termCheck.executeQuery();
        return rs.next();
    }


}
