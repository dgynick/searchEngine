package Indexer;

import DataManager.DbManager;
import DataManager.Indexing.TablesBuilder;
import DataManager.Indexing.TablesQuery;
import Indexer.DocumentSource.IDocSource;
import Indexer.DocumentSource.TokenDoc;
import Indexer.IndexerUtil.LinguisticModule;
import Indexer.IndexerUtil.TermDocu;
import Indexer.IndexerUtil.TfIdfResult;
import StrUtil.TokenUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by rbtlong on 5/15/15.
 */
public class TfIdfIndexer {
    String name = "";
    TablesBuilder tbls;
    TablesQuery query;
    IDocSource src;
    ArrayList<TfIdfResult> tfIdfResult = new ArrayList<>();

    private HashMap<String, Integer> terms = new HashMap<>();
    private HashMap<Integer, LinkedList<TermDocu>> term_doc = new HashMap<>();
    private HashMap<String, Integer> freq = new HashMap<>();
    private ArrayList<Integer> docParsed = new ArrayList<>();

    public TfIdfIndexer(String name, IDocSource src)
            throws SQLException, ClassNotFoundException {
        this.src = src;
        this.name = name;
        tbls = new TablesBuilder(name, DbManager.getConn());
        query = tbls.getQuery();
        tbls.constructIndexTables();

    }

/*    public TfIdfResult[] getTfIdf() {
        if (tfIdfResult.isEmpty()) {
            populateDocFreq();
            try {
                ResultSet res = query.computeTfIdx().executeQuery();
                while (res.next())
                    tfIdfResult.add(new TfIdfResult(res.getInt(1), res.getInt(2), res.getDouble(3)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        TfIdfResult[] result = new TfIdfResult[tfIdfResult.size()];
        tfIdfResult.toArray(result);
        return result;
    }*/

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

    public void initialize()
            throws SQLException, ClassNotFoundException {
        loadParsedDocuments();
        loadTermsFromDb();
        loadVectorIndexFromDb();
    }

    private void loadParsedDocuments() throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = DbManager.getConn().prepareStatement(
                "SELECT doc_id FROM ParsedDoc_" + name);
        ResultSet res = stmt.executeQuery();
        while (res.next())
            docParsed.add(res.getInt(1));
    }

    public void tokenize() {
        tokenizeDocuments();
    }

    private void tokenizeDocuments() {
        try {
            ResultSet rsAllUrl = DbManager.getPrepStmt_AllUrl().executeQuery();
            rsAllUrl.last();
            rsAllUrl.beforeFirst();
            DbManager.getConn().setAutoCommit(false);
            while (rsAllUrl.next()) {
                int docId = rsAllUrl.getInt("id");
                if (!docParsed.contains(docId)) {
                    TokenDoc doc = src.getSource(docId);
                    parseDoc(doc.getId(), doc.getContent(), doc.getUrl());
                    updateVectorIndex();
                    query.insertParsedDoc(docId).executeUpdate();
                    DbManager.getConn().commit();
                } else System.out.printf("[%s] skipped\n", docId);
            }
            DbManager.getConn().setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTermsFromDb()
            throws SQLException, ClassNotFoundException {
        PreparedStatement s = DbManager.getConn().prepareStatement("select * from Terms_" + name);
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String term = rs.getString("term");
            if (!terms.containsKey(term))
                terms.put(term, id);
        }
    }

    private void loadVectorIndexFromDb()
            throws SQLException, ClassNotFoundException {
        PreparedStatement s = DbManager.getConn().prepareStatement("select * from IndexVector_" + name);
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            if (!term_doc.containsKey(id))
                term_doc.put(id, new LinkedList<TermDocu>());
            LinkedList<TermDocu> termDoc = term_doc.get(id);
            termDoc.add(new TermDocu(rs.getInt("docid"), rs.getInt("freq")));
        }
    }

    private void updateVectorIndex()
            throws SQLException, ClassNotFoundException {
        Iterator itr = term_doc.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Integer, LinkedList<TermDocu>> entry = (Map.Entry) itr.next();
            int term = entry.getKey();
            LinkedList<TermDocu> termValue = entry.getValue();
            for (TermDocu tDoc : termValue)
                query.insertIndex(term, tDoc.getDocId(),
                        tDoc.getFrequency()).executeUpdate();
            termValue.clear();
        }
    }

    private void parseDoc(int doc, String text, String url) throws SQLException {
        int parsedLen = parse(doc, text);
        System.out.printf("[%s] %s : %d\n", doc, url, parsedLen);
    }

    private int parse(int docid, String text) {
        freq.clear();
        String token = "";
        char c;
        int parsed = 0;

        for (int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            if (TokenUtil.isAcceptableChar(c)) token += c;
            else if (Character.isWhitespace(c)) {
                if (!token.isEmpty()) {
                    String term = LinguisticModule.process(token);
                    addNewTerm(term);
                    if (freq.containsKey(term))
                        freq.put(term, freq.get(term) + 1);
                    else freq.put(term, 1);
                    token = "";
                    parsed++;
                }
            } else token = "";
        }

        if (token.length() > 0) {
            String term = LinguisticModule.process(token);
            addNewTerm(term);
            if (freq.containsKey(term)) {
                freq.put(term, freq.get(term) + 1);
            } else freq.put(term, 1);
        }

        updatePosting(docid);
        return parsed;
    }

    private void addNewTerm(String term) {
        if (!terms.containsKey(term)) {
            int id = terms.size();
            terms.put(term, id);
            try {
                query.insertTerm(term, id).executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        }
        if(!term_doc.containsKey(terms.get(term))){
            term_doc.put(terms.get(term), new LinkedList<TermDocu>());
        }

    }

    private void updatePosting(int doc) {
        for (String term : freq.keySet()) {
            if (!term_doc.containsKey(terms.get(term)))
                term_doc.put(terms.get(term), new LinkedList<TermDocu>());
            TermDocu termDoc = new TermDocu(doc, freq.get(term));
            term_doc.get(terms.get(term)).add(termDoc);
        }
    }

    public String getName() {
        return name;
    }
}
