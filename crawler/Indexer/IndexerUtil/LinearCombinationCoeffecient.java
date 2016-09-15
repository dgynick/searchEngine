package Indexer.IndexerUtil;

/**
 * Created by rbtlong on 5/23/15.
 */
public class LinearCombinationCoeffecient {
    String name;
    double weight;

    public LinearCombinationCoeffecient(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
