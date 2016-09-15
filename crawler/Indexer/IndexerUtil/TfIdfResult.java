package Indexer.IndexerUtil;

import DataManager.Indexing.DbModels.IndexVectorWithTerm;

/**
 * Created by rbtlong on 5/17/15.
 */
public class TfIdfResult {
    IndexVectorWithTerm indexVector;
    double tfidf;

    public TfIdfResult(IndexVectorWithTerm idxVec, double score) {
        this.indexVector = idxVec;
        this.tfidf = score;
    }

    public double getTfidf() {
        return tfidf;
    }

    public void setTfidf(int tfidf) {
        this.tfidf = tfidf;
    }

    public IndexVectorWithTerm getIndexVector() {
        return indexVector;
    }

    public void setIndexVector(IndexVectorWithTerm indexVector) {
        this.indexVector = indexVector;
    }
}
