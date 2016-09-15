package Indexer.IndexerUtil;

/**
 * Created by rbtlong on 5/23/15.
 */
public class TermDocument {
    int docId;
    String term;
    String suffix = "";
    int termid = -1;

    public TermDocument(int docId, String term, String suffix, int termid) {
        this.docId = docId;
        this.term = term;
        this.suffix = suffix;
        this.termid = termid;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getTermid() {
        return termid;
    }

    public void setTermid(int termid) {
        this.termid = termid;
    }

/*    public TermDocument(int docId, String term) {
        this.docId = docId;
        this.term = term;
    }*/

    public String getTerm() {
        return term;
    }

    public int getDocId() {
        return docId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermDocument) {
            TermDocument tDoc = (TermDocument) obj;
            return tDoc.getDocId() == this.docId
                    && tDoc.getTerm().equals(this.term)
                    && tDoc.getSuffix().equals(this.suffix)
                    && tDoc.getTermid() == this.termid;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
