package Indexer.Computations;

import DataManager.DbManager;
import DataManager.Indexing.DbModels.DocFreq;
import DataManager.Indexing.DbModels.IndexVectorWithTerm;
import DataManager.Indexing.TablesQuery;
import Indexer.AllTerms;
import Indexer.IndexerUtil.LinearCombinationCoeffecient;
import Indexer.IndexerUtil.TfIdfResult;
import Indexer.LinearCombination.DbActions.StoreAsExisting;
import Indexer.LinearCombination.DbActions.StoreAsNew;
import Indexer.LinearCombination.LinearCombination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * Created by Hp User on 2015/5/9.
 */
public class TfIdfComputer {
    public static final int CORPUS_SIZE = 42498;
    ArrayList<TfIdfResult> tfIdfResult = new ArrayList<>();
    Hashtable<Integer, DocFreq> docFreqs = new Hashtable<>(); //key is termid (used later with idxVec
    ArrayList<IndexVectorWithTerm> idxVecs = new ArrayList<>();
    String name = "";
    TablesQuery query;

    public TfIdfComputer(String name)
            throws SQLException, ClassNotFoundException {
        this.name = name;
        query = new TablesQuery(name, DbManager.getConn());
    }

    public static double computeTfIdf(
            double indexVectorFreqency, double documentFrequency) {
        return ((1.0 + Math.log(indexVectorFreqency))
                * Math.log(((double) CORPUS_SIZE) / documentFrequency));
    }

    public static void main(String args[])
            throws SQLException, ClassNotFoundException {
        TfIdfComputer coreTextTfIdf = new TfIdfComputer("CoreText");
        TfIdfResult[] coreTextResults = coreTextTfIdf.getTfIdf();
        Arrays.sort(coreTextResults, new SortByTermIdAscending());
        System.out.println("CoreText computed.");

        TfIdfComputer titleTfIdf = new TfIdfComputer("Title");
        TfIdfResult[] titleResults = titleTfIdf.getTfIdf();
        Arrays.sort(titleResults, new SortByTermIdAscending());
        System.out.println("Title computed.");

        TfIdfComputer headTfIdf = new TfIdfComputer("Head");
        TfIdfResult[] headResults = headTfIdf.getTfIdf();
        Arrays.sort(headResults, new SortByTermIdAscending());
        System.out.println("Head computed.");

        TfIdfComputer bodyTfIdf = new TfIdfComputer("Body");
        TfIdfResult[] bodyResults = bodyTfIdf.getTfIdf();
        Arrays.sort(bodyResults, new SortByTermIdAscending());
        System.out.println("Body computed.");

        AllTerms all = new AllTerms();
        all.add(coreTextResults);
        all.add(titleResults);
        all.add(headResults);
        all.add(bodyResults);

        LinearCombination linComb = new LinearCombination(
                new LinearCombinationCoeffecient[]{
                        new LinearCombinationCoeffecient("Title", .1),
                        new LinearCombinationCoeffecient("Body", .3),
                        new LinearCombinationCoeffecient("CoreText", .3),
                        new LinearCombinationCoeffecient("Head", .3),
                }, all);

        //linComb.loadFromDb();
        linComb.setExistAct(new StoreAsExisting());
        linComb.setNewAct(new StoreAsNew());
        linComb.getAllTfIdf();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public TfIdfResult[] getTfIdf() throws SQLException {
        if (tfIdfResult.isEmpty()) {
            populateDocFreq();
            loadDocFreqs();
            loadIdxVecs();

            for (IndexVectorWithTerm iv : idxVecs) {
                int idxVecFreq = iv.getFreq();
                int docFreq = docFreqs.get(iv.getTermId()).getDocFreq();
                TfIdfResult tfidf = new TfIdfResult(iv,
                        computeTfIdf(idxVecFreq, docFreq));
                tfIdfResult.add(tfidf);
            }
        }

        TfIdfResult[] result = new TfIdfResult[tfIdfResult.size()];
        tfIdfResult.toArray(result);
        return result;
    }

    private void loadIdxVecs() throws SQLException {
        ResultSet rs = query.getAllIndexVectorsWithTerms().executeQuery();
        while (rs.next())
            idxVecs.add(new IndexVectorWithTerm(rs, this.name));
    }

    private void loadDocFreqs() throws SQLException {
        ResultSet rs = query.getAllDocFreqs().executeQuery();
        while (rs.next()) {
            DocFreq df = new DocFreq(rs);
            docFreqs.put(df.getTermId(), df); // termId as key
        }
    }

    private void populateDocFreq() {
        try {
            if (!query.isDocFreqPopulated())
                query.populateDocFreq().executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static class SortByTermIdAscending
            implements Comparator<TfIdfResult> {
        @Override
        public int compare(TfIdfResult l, TfIdfResult r) {
            if (l.getIndexVector().getTermId() < r.getIndexVector().getTermId()) return -1;
            else if (l.getIndexVector().getTermId() > r.getIndexVector().getTermId()) return 1;
            return 0;
        }

    }

}
