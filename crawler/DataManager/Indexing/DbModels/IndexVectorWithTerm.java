package DataManager.Indexing.DbModels;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/23/15.
 */
public class IndexVectorWithTerm {

    String tableSuffix = null;
    String term = null;
    int termid;
    int docid;
    int freq;

    public IndexVectorWithTerm(String tableSuffix, String term, int termid, int docid, int freq) {
        this.tableSuffix = tableSuffix;
        this.term = term;
        this.termid = termid;
        this.docid = docid;
        this.freq = freq;
    }

    public IndexVectorWithTerm(ResultSet rs, String tableSuffix) throws SQLException {
        this.termid = rs.getInt("termid");
        this.docid = rs.getInt("docid");
        this.freq = rs.getInt("freq");
        this.term = rs.getString("term");
        this.tableSuffix = tableSuffix;
    }

    public int getTermId() {
        return termid;
    }

    public int getDocId() {
        return docid;
    }

    public int getFreq() {
        return freq;
    }

    public String getTerm() {
        return term;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

}
