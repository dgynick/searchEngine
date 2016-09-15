package DataManager.Indexing.DbModels;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 5/18/15.
 */
public class DocFreq {
    int id;
    int termId;
    int docFreq;

    public DocFreq(int id, int termId, int freq) {
        this.id = id;
        this.termId = termId;
        this.docFreq = freq;
    }

    public DocFreq(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.termId = rs.getInt("termId");
        this.docFreq = rs.getInt("doc_freq");
    }

    public int getId() {
        return id;
    }

    public int getTermId() {
        return termId;
    }

    public int getDocFreq() {
        return docFreq;
    }
}
