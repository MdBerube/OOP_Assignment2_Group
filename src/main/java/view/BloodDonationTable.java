package view;
 import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.BloodDonation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import logic.BloodDonationLogic;
import logic.Logic;
import logic.LogicFactory;


/**
 *
 * @author Mohammad Abou Haibeh
 */
public class BloodDonationTable extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException , IOException
    {
        response.setContentType("text/html;charset=UTF-8");
            try( PrintWriter out = response.getWriter() ) {
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>BloodDonationView</title>" );
            out.println( "</head>" );
            out.println( "<body>" );

            out.println( "<table style=\"margin-left: auto; margin-right: auto;\" border=\"1\">" );
            out.println( "<caption>Blood Donation</caption>" );

            Logic<BloodDonation> logic = LogicFactory.getFor( "BloodDonation" );
            out.println( "<tr>" );
            logic.getColumnNames().forEach( clck -> out.printf( "<th>%s</th>", clck ) );
            out.println( "</tr>" );
            logic.getAll().forEach( elment -> 
                    out.printf( "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                                logic.extractDataAsList(elment).toArray()) 
            );

            out.println( "</tr>" );
            out.println( "</body>" );
            out.println( "</html>" );
        }
    }

     /**
      * 
      * @param m
      * @return 
      */
    private String toString(Map<String, String[]> m ) {
     StringBuilder stringbuilder = new StringBuilder();
     for(String k: m.keySet()){
     stringbuilder.append("Key=").append(k) .append(",").append("\"Value/s=\" ").append(Arrays.toString( m.get( k ) ) ).append( System.lineSeparator() );
             
         }
     return stringbuilder.toString();
    }
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
//        log( "GET" );
        processRequest( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
//        log( "POST" );
         processRequest( request, response );
    }    
 
       
            
        }
        
    
    