package Indexer.IndexerUtil;

/**
 * Created by rbtlong on 5/15/15.
 */
public class TermDocu {
    int docId;
    int frequency;

    public TermDocu(int docId, int frequency) {
        this.docId = docId;
        this.frequency = frequency;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermDocu) {
            TermDocu tDoc = (TermDocu) obj;
            return tDoc.getDocId() == this.docId
                    && tDoc.getFrequency() == this.frequency;
        }
        return false;
    }
}
