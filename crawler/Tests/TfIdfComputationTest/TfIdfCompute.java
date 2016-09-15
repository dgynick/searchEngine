package Tests.TfIdfComputationTest;

import java.sql.SQLException;

/**
 * Created by rbtlong on 5/17/15.
 */
public class TfIdfCompute {
    public static void main(String[] args)
            throws SQLException, ClassNotFoundException {
/*        System.out.println("Computing TfIdf Scores...");

        System.out.print("> Computing CoreText...");
        TfIdfIndexer idxCore = new TfIdfIndexer("CoreText", new TextContentSource());
        TfIdfLists coreResults = new TfIdfLists(idxCore.getTfIdf(), idxCore.getName(), .3);
        System.out.println("Done");

        System.out.print("> Computing Title...");
        TfIdfIndexer idxTitle = new TfIdfIndexer("Title", new TitleSource());
        TfIdfLists titleResults = new TfIdfLists(idxTitle.getTfIdf(), idxTitle.getName(), .1);
        System.out.println("Done");

        System.out.print("> Computing Head...");
        TfIdfIndexer idxHead = new TfIdfIndexer("Head", new HeadSource());
        TfIdfLists headResults = new TfIdfLists(idxHead.getTfIdf(), idxHead.getName(), .3);
        System.out.println("Done");

        System.out.print("> Computing Body...");
        TfIdfIndexer idxBody = new TfIdfIndexer("Body", new BodySource());
        TfIdfLists BodyResults = new TfIdfLists(idxBody.getTfIdf(), idxBody.getName(), .3);
        System.out.println("Done");


        LinearCombination comboResult =
                new LinearCombination(
                        new TfIdfLists[]{
                                coreResults,
                                titleResults,
                                headResults,
                                BodyResults,
                        });*/
    }
}
