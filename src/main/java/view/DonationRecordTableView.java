package view;

import entity.DonationRecord;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.DonationRecordLogic;
import logic.Logic;
import logic.LogicFactory;

/**
 *
 * @author Ngoc Que Huong Tran
 */
public class DonationRecordTableView extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try( PrintWriter out = response.getWriter() ) {
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>DonationRecordViewNormal</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<table style=\"margin-left: auto; margin-right: auto;\" border=\"1\">" );
            out.println( "<caption>DonationRecord</caption>" );
            Logic<DonationRecord> logic = LogicFactory.getFor( "DonationRecord" );
            logic.getAll().forEach( e -> out.printf( "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                    logic.extractDataAsList( e ).toArray() ) );
             logic.getColumnNames().forEach( c -> out.printf( "<th>%s</th>", c ) );
            out.println( "</tr>" );
            out.println( "</table>" );
            out.printf( "<div style=\"text-align: center;\"><pre>%s</pre></div>", toStringMap( request.getParameterMap() ) );
            out.println( "</body>" );
            out.println( "</html>" );
        }
      
    
    }

    private String toStringMap( Map<String, String[]> m ) {
        StringBuilder builder = new StringBuilder();
        for( String k: m.keySet() ) {
            builder.append( "Key=" ).append( k )
                    .append( ", " )
                    .append( "Value/s=" ).append( Arrays.toString( m.get( k ) ) )
                    .append( System.lineSeparator() );
        }
        return builder.toString();
    }
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        log( "GET" );
        processRequest( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        log( "POST" );
        DonationRecordLogic logic = LogicFactory.getFor( "DonationRecord" );
        DonationRecord donationRecord = logic.updateEntity( request.getParameterMap() );
        logic.update( donationRecord );
        processRequest( request, response );
    }


    @Override
    public String getServletInfo() {
        return "Sample of DonationRecord View Normal";
    }

    private static final boolean DEBUG = true;

    public void log( String msg ) {
        if( DEBUG ){
            String message = String.format( "[%s] %s", getClass().getSimpleName(), msg );
            getServletContext().log( message );
        }
    }

    public void log( String msg, Throwable t ) {
        String message = String.format( "[%s] %s", getClass().getSimpleName(), msg );
        getServletContext().log( message, t );
    }

}
