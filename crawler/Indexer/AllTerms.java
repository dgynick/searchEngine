package Indexer;

import Indexer.IndexerUtil.TermDocument;
import Indexer.IndexerUtil.TfIdfResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by rbtlong on 5/23/15.
 */
public class AllTerms {
    HashMap<String, ArrayList<TfIdfResult>> allTerms = new HashMap<>();

    public void add(TfIdfResult[] bodyResults) {
        for (TfIdfResult res : bodyResults) {
            String key = res.getIndexVector().getTerm();
            if (!allTerms.containsKey(key)) {
                ArrayList<TfIdfResult> lstTfIdf = new ArrayList<>();
                lstTfIdf.add(res);
                allTerms.put(key, lstTfIdf);
            } else allTerms.get(key).add(res);
        }
    }

    public HashMap<String, ArrayList<TfIdfResult>> getAllTerms() {
        return allTerms;
    }

    public Set<String> getKeys() {
        return allTerms.keySet();
    }

    public ArrayList<TfIdfResult> get(String key) {
        return allTerms.get(key);
    }

    public void remove(String key) {
        allTerms.remove(key);
    }

    public void remove(TermDocument tDoc) {
        ArrayList<TfIdfResult> res = allTerms.get(tDoc.getTerm());
        if (res != null) {
            System.out.printf("[%s : %d] Loaded\n", tDoc.getTerm(), tDoc.getDocId());
            ArrayList<TfIdfResult> cand = new ArrayList<>();
            for (TfIdfResult r : res)
                if (r.getIndexVector().getDocId() == tDoc.getDocId()
                        && r.getIndexVector().getTableSuffix().equals(tDoc.getSuffix())
                        && r.getIndexVector().getTermId() == tDoc.getTermid())
                    cand.add(r);
            for (TfIdfResult c : cand) res.remove(c);
            if (res.size() == 0) {
                allTerms.remove(tDoc.getTerm());
                System.out.printf("[%s] Term has been removed.\n", tDoc.getTerm());
            }
        } else System.out.printf("[%s] does not exist\n", tDoc.getTerm());
    }
}
