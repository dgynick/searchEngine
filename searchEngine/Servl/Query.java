package Servl;

import SearchDb.DbCtx;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rbtlong on 6/2/15.
 */
public class Query extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request,
                          javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request,
                         javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

        ServletContext ctx = getServletContext();
        RequestDispatcher disp = ctx.getRequestDispatcher("/results.jsp");
        String[] terms = request.getParameter("q").split("\\s");
        String answers[]= new String[10];

        try {
            DbCtx.dropQueryTfIdf().executeUpdate();
            DbCtx.createQueryTfIdf().executeUpdate();
            computeQueryTfIdf(terms);
            ResultSet rs = DbCtx.getGetDocidranking().executeQuery();
            int i=0;
            while(rs.next()){
                answers[i]=rs.getString(1);
                i++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        request.setAttribute("qresult", answers);
        disp.forward(request, response);
    }

    private void computeQueryTfIdf(String[] terms)
            throws SQLException, ClassNotFoundException, NamingException {

        boolean[] repeat= new boolean[terms.length];
        double[] scores = new double[terms.length];
        for(int i=0;i<repeat.length;i++){
            repeat[i]=false;
            scores[i]=0.0;
        }
        for(int i=0;i<terms.length;i++){
            if(!repeat[i]){
                scores[i]=1.0;
                for(int j=i+1;j<terms.length;j++) {
                    if (terms[j].equals(terms[i])){
                        repeat[j]=true;
                        scores[i]+=1.0;
                    }
                }
                scores[i]= 1+Math.log(scores[i]);
            }
        }
        double length=0.0;
        int df=0;
        for(int i=0;i<terms.length && !repeat[i];i++){
            df=1;
            ResultSet rs =DbCtx.getDF(terms[i]).executeQuery();
            if(rs.next()){
                df+=rs.getInt(1);
            }
            scores[i]*= Math.log(42498/df);
            length+=scores[i]*scores[i];
        }
        length=Math.sqrt(length);
        for(int i=0;i<terms.length && !repeat[i];i++){
            scores[i]/=length;
            DbCtx.PopQueryTfIdf(terms[i],scores[i]).executeUpdate();
        }
    }
}
