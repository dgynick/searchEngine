package DataManager.Indexing.DbModels;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/18/15.
 */
public class IndexVector {
    int termid;
    int docid;
    int freq;

    public IndexVector(int termid, int docid, int freq) {
        this.termid = termid;
        this.docid = docid;
        this.freq = freq;
    }

    public IndexVector(ResultSet rs) throws SQLException {
        this.termid = rs.getInt("termid");
        this.docid = rs.getInt("docid");
        this.freq = rs.getInt("freq");
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
}
