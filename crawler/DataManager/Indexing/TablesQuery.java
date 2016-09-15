package DataManager.Indexing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/15/15.
 */
public class TablesQuery {
    Connection conn;
    String name = "";
    PreparedStatement
            insertTerm = null,
            insertIndex = null,
            insertParsedDoc = null,
            insertTfIdf = null;

    public TablesQuery(String name, Connection conn) {
        this.name = name;
        this.conn = conn;
    }

    public String getName() {
        return name;
    }

    public PreparedStatement insertTerm(String term, int ID)
            throws SQLException, ClassNotFoundException {
        if (insertTerm == null) {
            insertTerm = conn.prepareStatement(
                    "insert into Terms_" + name + "(id,term) values (?,?)");
        }
        insertTerm.setString(2, term);
        insertTerm.setInt(1, ID);
        return insertTerm;
    }

    public PreparedStatement insertIndex(int term, int doc, int freq)
            throws SQLException, ClassNotFoundException {
        if (insertIndex == null) {
            insertIndex = conn.prepareStatement(
                    "INSERT INTO IndexVector_" + name + " VALUES (?,?,?)");
        }
        insertIndex.setInt(1, term);
        insertIndex.setInt(2, doc);
        insertIndex.setInt(3, freq);
        return insertIndex;
    }

    public boolean isDocParsed(int docId)
            throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT count(*) AS c FROM ParsedDoc_" + name + " WHERE doc_id = ?");
        stmt.setInt(1, docId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;
        return false;
    }

    public PreparedStatement insertParsedDoc(int docId)
            throws SQLException, ClassNotFoundException {
        if (insertParsedDoc == null)
            insertParsedDoc = conn.prepareStatement(
                    "INSERT INTO ParsedDoc_" + name + "(doc_id) VALUES (?)");
        insertParsedDoc.setInt(1, docId);
        return insertParsedDoc;
    }

    public PreparedStatement populateDocFreq() throws SQLException {
        return conn.prepareStatement(
                "insert into DocFreq_" + name + "(termid,doc_freq)" +
                        "select termid, count(*) from IndexVector_" + name + " group by termid;");
    }

    public boolean isDocFreqPopulated()
            throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT count(*) AS c FROM DocFreq_" + name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;
        return false;
    }

    public PreparedStatement computeTfIdx() throws SQLException {
        return conn.prepareCall(
                "select I.termid, I.docid, (1+log(I.freq))*log(42498/D.doc_freq)" +
                        "from DocFreq_" + name + " D, " +
                        "IndexVector_" + name + " I where D.termid=I.termid;");
    }

    public PreparedStatement insertTfIdf(int docId, int termId, double score)
            throws SQLException, ClassNotFoundException {
        if (insertTfIdf == null)
            insertTfIdf = conn.prepareStatement(
                    "INSERT INTO TfIdf_" + name + "(docid, termid, score) " +
                            "VALUES (?,?,?)");
        insertTfIdf.setInt(1, docId);
        insertTfIdf.setInt(2, docId);
        insertTfIdf.setDouble(3, score);
        return insertTfIdf;
    }

    public PreparedStatement getAllDocFreqs()
            throws SQLException {
        return conn.prepareStatement(
                "select * from DocFreq_" + name);
    }

    public PreparedStatement getAllIndexVectors()
            throws SQLException {
        return conn.prepareStatement(
                "select * from IndexVector_" + name);
    }

    public PreparedStatement getAllIndexVectorsWithTerms()
            throws SQLException {
        return conn.prepareStatement(
                "select iv.termid, iv.docid, iv.freq, t.term " +
                        " from IndexVector_" + name + " iv, Terms_" + name + " t" +
                        " where iv.termid = t.id; ");
    }

}
