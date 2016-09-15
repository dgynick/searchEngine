package Indexer.LinearCombination.DbActions;

import DataManager.Indexing.TableFinal;
import Indexer.IndexerUtil.TermDocument;
import Indexer.LinearCombination.ExistingTfIdfAction;

import java.sql.SQLException;

/**
 * Created by rbtlong on 5/24/15.
 */
public class StoreAsExisting extends ExistingTfIdfAction {
    @Override
    public void Triggered(TermDocument tDoc, double w) {
        try {
            TableFinal.updateTfIdf(tDoc, w);
            System.out.printf("%s persisted (existing)\n", tDoc.getTerm());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
