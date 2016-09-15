package Indexer;

import Indexer.TokenizationStrategies.BodySource;
import Indexer.TokenizationStrategies.HeadSource;
import Indexer.TokenizationStrategies.TextContentSource;
import Indexer.TokenizationStrategies.TitleSource;

import java.sql.SQLException;

/**
 * Created by Hp User on 2015/5/6.
 */
public class IndexBuilder {
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        TfIdfIndexer idxCore = new TfIdfIndexer("CoreTextDemo", new TextContentSource());
        idxCore.initialize();

        TfIdfIndexer idxTitle = new TfIdfIndexer("TitleDemo", new TitleSource());
        idxTitle.initialize();

        TfIdfIndexer idxHead = new TfIdfIndexer("HeadDemo", new HeadSource());
        idxHead.initialize();

        TfIdfIndexer idxBody = new TfIdfIndexer("BodyDemo", new BodySource());
        idxBody.initialize();

        idxCore.tokenize();
        idxTitle.tokenize();
        idxHead.tokenize();
        idxBody.tokenize();

    }
}
