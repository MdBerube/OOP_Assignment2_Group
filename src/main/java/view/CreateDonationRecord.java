package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.DonationRecord;
import entity.BloodBank;
import logic.LogicFactory;
import common.ValidationException;
import logic.DonationRecordLogic;


/**
 *
 * @author Ngoc Que Huong Tran
 */
@WebServlet( name = "CreateDonationRecord", urlPatterns = {"/CreateDonationRecord"})
public class CreateDonationRecord extends HttpServlet{
    private String errorMessage = null;
    
    protected void processRequest( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
    
        try( PrintWriter out = response.getWriter() ) {
            
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>Create Donation Record</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<div style=\"text-align: center;\">" );
            out.println( "<div style=\"display: inline-block; text-align: left;\">" );
            out.println( "<form method=\"post\">" );
            
            out.println("Person Id:<br>");
            out.printf("<input type=\"number\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.PERSON_ID);
            out.println("<br>");
            out.println("Donation Id:<br>");
            out.printf("<input type=\"number\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.DONATION_ID);
            out.println("<br>");
            out.println("Tested:<br>");
            out.printf("<input type=\"name\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.TESTED);
            out.println("<br>");
            out.println("Administrator:<br>");
            out.printf("<input type=\"name\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.ADMINISTRATOR);
            out.println("<br>");
            out.println("Hospital:<br>");
            out.printf("<input type=\"name\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.HOSPITAL);
            out.println("<br>");
            out.println("Created:<br>");
            out.printf("<input type=\"name\" name=\"%s\" value=\"\"><br>", DonationRecordLogic.CREATED);
            out.println("<br>");
            out.println("Id:<br>");
            out.printf("<input type=\"name\" name=\"%s\" value=\"\"><br>", DonationRecordLogic. ID);
            out.println("<br>");
            out.printf("<br><input type=\"datetime-local\" step='1' name=\"%s\" value=\"\"><br><br>", DonationRecordLogic.CREATED);
            out.println("<br>");
            out.println( "<input type=\"submit\" name=\"view\" value=\"Add and View\">" );
            out.println( "<input type=\"submit\" name=\"add\" value=\"Add\">" );
            out.println( "</form>" );
            if( errorMessage != null && !errorMessage.isEmpty() ){
                out.println( "<p color=red>" );
                out.println( "<font color=red size=4px>" );
                out.println( errorMessage );
                out.println( "</font>" );
                out.println( "</p>" );
            }
            out.println( "<pre>" );
            out.println( "Submitted keys and values:" );
            out.println( toStringMap( request.getParameterMap() ) );
            out.println( "</pre>" );
            out.println( "</div>" );
            out.println( "</div>" );
            out.println( "</body>" );
            out.println( "</html>" );
        
        }
        
    }

    private String toStringMap(Map<String, String[]> values) {
        StringBuilder builder = new StringBuilder();
        values.forEach( ( k, v ) -> builder.append( "Key=" ).append( k )
                .append( ", " )
                .append( "Value/s=" ).append( Arrays.toString( v ) )
                .append( System.lineSeparator() ) );
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
        // creates an accountlogic object through the factory class
        DonationRecordLogic dLogic = LogicFactory.getFor( "Donation Record" );
        //  pulls username entered from the request
        String hospital = request.getParameter( DonationRecordLogic.HOSPITAL );
        // checks the db for a line with specific username and if it returns null then enter try
        if( dLogic.getDonationRecordWithHospital( hospital ) == null ){
            try {
                // creates a new account object via logic object using param map
                DonationRecord donationRecord = dLogic.createEntity( request.getParameterMap() );
                // adds account object to the db
                dLogic.add( donationRecord );
            } catch( Exception ex ) {
                errorMessage = ex.getMessage();
            }
        } else {
            //if duplicate print the error message
            errorMessage = "Username: \"" + hospital + "\" already exists";
        }


        if( request.getParameter("add") != null ) {
        processRequest(request, response);
        }
        else if(request.getParameter("view") != null) { 
            response.sendRedirect("DonationRecordTable");
        }
    }

    @Override
    public String getServletInfo() {
        return "Create a Donation Record Entity";
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
