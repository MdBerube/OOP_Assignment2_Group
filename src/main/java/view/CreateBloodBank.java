package view;

import entity.BloodBank;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.BloodBankLogic;
import logic.LogicFactory;
/**
 *
 * @author Mike Berube
 */
@WebServlet( name = "CreateBloodBank", urlPatterns = {"/CreateBloodBank"})
public class CreateBloodBank extends HttpServlet {
    
            private String errorMessage = null;
    // TODO figure this out kek
     protected void processRequest( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try( PrintWriter out = response.getWriter() ) {
            /* TODO output your page here. You may use following sample code. */
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>Create Account</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<div style=\"text-align: center;\">" );
            out.println( "<div style=\"display: inline-block; text-align: left;\">" );
            out.println( "<form method=\"post\">" );
            out.println( "Name:<br>" );
            //instead of typing the name of column manualy use the static vraiable in logic
            //use the same name as column id of the table. will use this name to get data
            //from parameter map.
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodBankLogic.OWNER_ID);
            out.println( "<br>" );
            out.println( "OwnerID:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodBankLogic.PRIVATELY_OWNED );
            out.println( "<br>" );
            out.println( "PrivatelyOwned:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodBankLogic.ESTABLISHED );
            out.println( "<br>" );
            out.println( "Established:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodBankLogic.NAME );
            out.println( "<br>" );
            out.println( "Employee_count:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodBankLogic.EMPLOYEE_COUNT );
            out.println( "<br>" );
            out.println( "ID:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodBankLogic.ID );
            out.println( "<br>" );
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

    private String toStringMap( Map<String, String[]> values ) {
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

   // handles entity creation. called by user submitting data through browser
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        log( "POST" );
        BloodBankLogic bLogic = LogicFactory.getFor( "BloodBank" );
        // why these checks?
        String name = request.getParameter( BloodBankLogic.NAME );
        if( bLogic.getBloodBankWithName( name ) == null ){
            try {
                BloodBank bloodbank = bLogic.createEntity( request.getParameterMap() );
                bLogic.add( bloodbank );
            } catch( Exception ex ) {
                errorMessage = ex.getMessage();
            }
        } else {
            //if duplicate print the error message
            errorMessage = "Bank Name: \"" + name + "\" already exists";
        }
        if( request.getParameter( "add" ) != null ){
            //if add button is pressed return the same page
            processRequest( request, response );
        } else if( request.getParameter( "view" ) != null ){
            //if view button is pressed redirect to the appropriate table
            response.sendRedirect( "BloodBankTable" );
        }
    }

    @Override
    public String getServletInfo() {
        return "Create a Blood Bank Entity";
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
