package DataManager;

import Crawl.WebSite;
import StrUtil.UrlStr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * CS 121 Team BEES
 * Dongguang You - 80122555
 * Robert Long - 74151707
 */
public class DbManager {
    private static Connection conn = null;
    private static PreparedStatement
            stmt = null,
            websiteInsert = null,
            websiteFetchAllUrl = null,
            websiteFetchAllUrlAndNormalized_Visits = null,
            URLContent=null,
            getDocPaging = null;

    public static Connection getConn()
            throws ClassNotFoundException, SQLException {
        if(conn == null) {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/121Crawler", "cs121", "cs121");
        }
        return conn;
    }

    public static PreparedStatement getPrepStmt_WebsiteInsert(WebSite web)
            throws SQLException, ClassNotFoundException {
        if(websiteInsert == null)
            websiteInsert = getConn().prepareStatement(
                    "INSERT INTO 121Crawler.Website (" +
                            "Url, Noramlized_Url, Html_Content, " +
                            "Text_Content, Outgoing_Links, " +
                            "Visited_Normalized, Visited) " +
                            "VALUES (?, ?, ?, ?, ?, '1', '1');");
        websiteInsert.setString(1, web.getUrl());
        websiteInsert.setString(2, UrlStr.normalizeURL(web.getUrl()));
        websiteInsert.setString(3, web.getContentHtml());
        websiteInsert.setString(4, web.getContentText());
        websiteInsert.setString(5, web.getInlineOutgoingLinks());
        return websiteInsert;
    }

    public static PreparedStatement getPrepStmt_AllUrl()
            throws SQLException, ClassNotFoundException {
        if(websiteFetchAllUrl == null)
            websiteFetchAllUrl = getConn().prepareStatement("SELECT Url,Id FROM `121Crawler`.Website");
        return websiteFetchAllUrl;
    }

    public static PreparedStatement getPrepStmt_AllUrlAndNormalized_Vists()
            throws SQLException, ClassNotFoundException {
        if(websiteFetchAllUrlAndNormalized_Visits == null)
            websiteFetchAllUrlAndNormalized_Visits = getConn().prepareStatement("select Url, Visited_Normalized from `121Crawler`.Website limit 10000000");
        return websiteFetchAllUrlAndNormalized_Visits;
    }
    public static PreparedStatement getURLContent()throws SQLException, ClassNotFoundException{
        if(URLContent==null){
            URLContent= getConn().prepareStatement("select Url, Text_Content from `121Crawler`.Website limit 10000000");
        }
        return URLContent;
    }

    public static PreparedStatement getTextContent(int id)
            throws SQLException, ClassNotFoundException {
        if (stmt == null)
            stmt = conn.prepareStatement("SELECT Id,Text_Content,Url FROM Website WHERE id = ?");
        stmt.setInt(1, id);
        return stmt;
    }

    public static PreparedStatement getHtmlContent(int id)
            throws SQLException, ClassNotFoundException {
        if (stmt == null)
            stmt = conn.prepareStatement("SELECT Id,Html_Content,Url FROM Website WHERE id = ?");
        stmt.setInt(1, id);
        return stmt;
    }

    public PreparedStatement getTextContent()
            throws SQLException, ClassNotFoundException {
        if (stmt == null) {
            stmt = conn.prepareStatement(
                    "SELECT Id, Url, Text_Content " +
                            "FROM `121Crawler`.Website " +
                            "LIMIT 10000000");
        }
        return stmt;
    }

    public PreparedStatement getTextContent(int offset, int limit)
            throws SQLException, ClassNotFoundException {
        getDocPaging = conn.prepareStatement(
                "SELECT Id, Url, Text_Content " +
                        "FROM 121Crawler.Website " +
                        "OFFSET " + offset +
                        " LIMIT " + limit + ";");
        return getDocPaging;
    }


}
