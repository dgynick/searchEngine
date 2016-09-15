package Indexer.LinearCombination;

import DataManager.DbManager;
import DataManager.Indexing.TableFinal;
import Indexer.AllTerms;
import Indexer.IndexerUtil.LinearCombinationCoeffecient;
import Indexer.IndexerUtil.TermDocument;
import Indexer.IndexerUtil.TfIdfResult;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by rbtlong on 5/17/15.
 */
public class LinearCombination {
    HashMap<String, Double> coeffs = new HashMap<>();
    AllTerms all = new AllTerms();

    NewTfIdfAction newAct = null;
    ExistingTfIdfAction existAct = null;

    public LinearCombination(
            LinearCombinationCoeffecient[] coeffs,
            AllTerms all) {
        this.all = all;
        for (LinearCombinationCoeffecient res : coeffs)
            this.coeffs.put(res.getName(), res.getWeight());
    }

    public NewTfIdfAction getNewAct() {
        return newAct;
    }

    public void setNewAct(NewTfIdfAction newAct) {
        this.newAct = newAct;
    }

    public ExistingTfIdfAction getExistAct() {
        return existAct;
    }

    public void setExistAct(ExistingTfIdfAction existAct) {
        this.existAct = existAct;
    }

    public void getAllTfIdf() throws SQLException, ClassNotFoundException {
        int allSize = all.getKeys().size();
        int allCur = 1;
        for (String key : all.getKeys()) { // all terms
/*            if (isTrivialkey(key)) {
                System.out.printf("[%s] Ignoring\n", key);
                continue;
            }*/

            int len = all.get(key).size();
            int cur = 1;
            System.out.printf("Processing: %s \tLength: %d\n",
                    key, len);

            DbManager.getConn().setAutoCommit(false);
            for (TfIdfResult res : all.get(key)) { // list of tfidf results
                for (String suffix : coeffs.keySet()) { // coeffs

                    if (res.getIndexVector().getTerm().length() > 50) {
                        System.out.printf("[Too Large] %s", res.getIndexVector().getTerm());
                        continue;
                    }

                    if (res.getIndexVector()
                            .getTableSuffix()
                            .equals(suffix)) {
                        TermDocument tDoc = new TermDocument(
                                res.getIndexVector().getDocId(),
                                res.getIndexVector().getTerm(),
                                res.getIndexVector().getTableSuffix(),
                                res.getIndexVector().getTermId());
                        double w = coeffs.get(suffix) * res.getTfidf();

                        System.out.printf("[%d/%d]: %.2f\t [%d/%d]: %.2f\n",
                                allCur, allSize, (allCur * 1. / allSize) * 100,
                                cur, len, (cur * 1. / len) * 100);

                        if (!TableFinal.exists(tDoc)) {
                            if (newAct != null) newAct.Triggered(tDoc, w);
                            System.out.printf("New [%s] Doc: %d\t Suffix: %s\t Weight: %f\n",
                                    res.getIndexVector().getTerm(),
                                    res.getIndexVector().getDocId(),
                                    res.getIndexVector().getTableSuffix(),
                                    w);
                        } else
                            System.out.printf("[%s] Already processed\n", tDoc.getTerm());
                    }
                }
                DbManager.getConn().commit();
                cur++;
            }
            allCur++;
        }
        DbManager.getConn().setAutoCommit(true);
    }

    private boolean isTrivialkey(String key) {
        return (key.trim().length() == 1
                && Character.isAlphabetic(key.charAt(0)))
                || key.trim().length() < 1
                || key.trim().matches("[-+]?\\d*\\.?\\d+")
                || isStopWord(key);
    }

    private boolean isStopWord(String key) {
        return (key.toLowerCase().equals("by")
                || key.toLowerCase().equals("the")
                || key.toLowerCase().equals("and")
                || key.toLowerCase().equals("is")
                || key.toLowerCase().equals("as")
                || key.toLowerCase().equals("an")
                || key.toLowerCase().equals("if")
                || key.toLowerCase().equals("or")
                || key.toLowerCase().equals("of")
                || key.toLowerCase().equals("be")
                || key.toLowerCase().equals("am")
                || key.toLowerCase().equals("it")
                || key.toLowerCase().equals("to")
                || key.toLowerCase().equals("there")
                || key.toLowerCase().equals("are")
                || key.toLowerCase().equals("may")
                || key.toLowerCase().equals("has")
                || key.toLowerCase().equals("have")
                || key.toLowerCase().equals("on")
                || key.toLowerCase().equals("in")
                || key.toLowerCase().equals("we")
                || key.toLowerCase().equals("us"));
    }
}
