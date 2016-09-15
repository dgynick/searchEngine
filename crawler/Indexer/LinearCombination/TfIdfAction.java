package Indexer.LinearCombination;

import Indexer.IndexerUtil.TermDocument;

/**
 * Created by rbtlong on 5/24/15.
 */
public interface TfIdfAction {
    void Triggered(TermDocument tDoc, double w);
}
