package Indexer.Computations;


import org.jblas.DoubleMatrix;
import org.jblas.Singular;

/**
 * Created by rbtlong on 5/25/15.
 */
public class SvdComputations {

    public static void main(String[] args) {
        DoubleMatrix dm = DoubleMatrix.randn(10, 10);
        Singular sing = new Singular();
        DoubleMatrix[] res = sing.fullSVD(dm);
    }
}
