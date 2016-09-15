package SearchDb;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by rbtlong on 6/2/15.
 */
public class DbCtx {

    private DbCtx() {}

    static PreparedStatement
            insertQueryTfIdf=null,
            getdf=null;

    static Connection conn;
    static DataSource src;

    public static Connection getConn()
            throws ClassNotFoundException, SQLException, NamingException {
        if(conn == null) {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/121Crawler", "cs121", "cs121");
        }
        return conn;
    }

    public static PreparedStatement createQueryTfIdf()
            throws SQLException, ClassNotFoundException, NamingException {
        return getConn().prepareStatement("create table if not exists QueryTfIdf(term nvarchar(50),score double);");
    }

    public static PreparedStatement dropQueryTfIdf()
            throws SQLException, ClassNotFoundException, NamingException {
        return getConn().prepareStatement("drop table if exists QueryTfIdf;");
    }

    public static PreparedStatement PopQueryTfIdf(String term, double score)
            throws SQLException, ClassNotFoundException, NamingException {
        if(insertQueryTfIdf==null){
            insertQueryTfIdf= getConn().prepareStatement("insert into QueryTfIdf values(?,?);");
        }
        insertQueryTfIdf.setString(1, term);
        insertQueryTfIdf.setDouble(2, score);
        return insertQueryTfIdf;
    }
    public static PreparedStatement getGetDocidranking()
            throws SQLException, ClassNotFoundException, NamingException {
        return getConn().prepareStatement("select docid from FinalTfIdf_normalized F, QueryTfIdf Q WHERE " +
                "F.term=Q.term group by docid order by sum(F.score*Q.score) desc limit 10");
    }
    public static PreparedStatement getDF(String term)
            throws SQLException, ClassNotFoundException, NamingException {
        if(getdf==null){
            getdf=getConn().prepareStatement("select doc_freq from DocFreq_CoreText D,Terms_CoreText T" +
                    " where D.termid=T.id and T.term=?;");
        }
        getdf.setString(1,term);
        return getdf;
    }


}
