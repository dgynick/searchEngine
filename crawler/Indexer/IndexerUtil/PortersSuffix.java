package Indexer.IndexerUtil;

/**
 * Created by rbtlong on 5/13/15.
 */
public class PortersSuffix {
    String suffixSeek;
    String suffixReplace;


    public PortersSuffix(String suffixSeek, String suffixReplace) {
        this.suffixReplace = suffixReplace;
        this.suffixSeek = suffixSeek;
    }
    public String getSuffixSeek() {
        return suffixSeek;
    }

    public void setSuffixSeek(String suffixSeek) {
        this.suffixSeek = suffixSeek;
    }

    public String getSuffixReplace() {
        return suffixReplace;
    }

    public void setSuffixReplace(String suffixReplace) {
        this.suffixReplace = suffixReplace;
    }
}
