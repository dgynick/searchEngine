package Indexer.LinearCombination.DbActions;

import DataManager.Indexing.TableFinal;
import Indexer.IndexerUtil.TermDocument;
import Indexer.LinearCombination.NewTfIdfAction;

import java.sql.SQLException;

/**
 * Created by rbtlong on 5/24/15.
 */
public class StoreAsNew extends NewTfIdfAction {
    @Override
    public void Triggered(TermDocument tDoc, double w) {
        try {
            TableFinal.insertTfIdf(tDoc, w)
                    .executeUpdate();
            System.out.printf("[%s] persisted (new)\n", tDoc.getTerm());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
