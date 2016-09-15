package Indexer.IndexerUtil;

/**
 * Created by rbtlong on 5/17/15.
 */
public class TfIdfLists {
    private TfIdfResult[] tfIdfResult;
    private String name;
    private double weight;

    public TfIdfLists(TfIdfResult[] tfIdf, String name, double weight) {
        tfIdfResult = tfIdf;
        this.name = name;
        this.weight = weight;
    }

    public TfIdfResult[] getTfIdfResult() {
        return tfIdfResult;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double value) {
        weight = value;
    }

    public double computeScore() {
        return 0;
    }
}
