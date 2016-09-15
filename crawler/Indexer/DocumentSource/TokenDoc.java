package Indexer.DocumentSource;

/**
 * Created by rbtlong on 5/15/15.
 */
public class TokenDoc {
    int id;
    String content;
    String Url;

    public TokenDoc(int id, String content, String url) {
        this.id = id;
        this.content = content;
        Url = url;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return Url;
    }
}
