package view;

import common.ValidationException;
import entity.BloodBank;
import entity.BloodDonation;
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
import logic.BloodDonationLogic;
import logic.LogicFactory;

/**
 * 
 * @author Mohammad Abou Haibeh
 */
@WebServlet(name = "CreateBloodDonation", urlPatterns = {"/CreateBloodDonation"})
public class CreateBloodDonation extends HttpServlet {
    
    private String errorMessage = null;
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>Create Blood Donation</title>" );
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=\"text-align: center;\">");
            out.println("<div style=\"display: inline-block; text-align: left;\">");
            out.println("<form method=\"post\">");
            out.println("Bank_Id:<br>");
            out.printf("<input type=\"number\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.BANK_ID);
            out.println("<br>");
            out.println("Milliliters:<br>");
            out.printf("<input type=\"number\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.MILLILITERS);
            out.println("<br>");
            out.println("Blood Group:<br>");
            out.printf("<input type=\"name\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.BLOOD_GROUP);
            out.println("<br>");
            out.println("Rhesus Factor:<br>");
            out.printf("<input type=\"name\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.RHESUS_FACTOR);
            out.println("<br>");
            out.println("Created:<br>");
            out.printf("<br><input type=\"datetime-local\" step='1' name=\"%s\" value=\"\"><br><br>", BloodDonationLogic.CREATED);
            out.println("<br>");
            out.println( "<input type=\"submit\" name=\"view\" value=\"Add and View\">" );
            out.println( "<input type=\"submit\" name=\"add\" value=\"Add\">" );
            out.println( "</form>" );
            
            if(!errorMessage.isEmpty() && errorMessage != null ){
                out.println( "<p color=red>" );
                out.println( "<font color=red size=8px>" );
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
    /**
     * 
     * @param values
     * @return 
     */
    
    private String toStringMap( Map<String, String[]> values ) {
        StringBuilder builder = new StringBuilder();
        values.forEach( ( k, v ) -> builder.append( "Key=" ).append( k )
                .append( ", " )
                .append( "Value/s=" ).append( Arrays.toString( v ) )
                .append( System.lineSeparator() ) );
        return builder.toString();
    }
    
     /**
      * 
      * @param request
      * @param response
      * @throws ServletException
      * @throws IOException 
      */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        processRequest( request, response );
    }
    
  /**
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException 
   */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        
        BloodDonationLogic bloodDLogic = LogicFactory.getFor("BloodDonation" );
         BloodBankLogic bloodBlogic =LogicFactory.getFor("BloodBank");
        try {
            BloodDonation bloodDonation = bloodDLogic.createEntity(request.getParameterMap());
            int bankId;
            bankId = Integer.parseInt(request.getParameterMap().get( BloodDonationLogic.BANK_ID)[0]);
            BloodBank bb = bloodBlogic.getWithId( bankId);
            bloodDonation.setBloodBank(bb);
            bloodDLogic.add(bloodDonation);
        }
        catch (ValidationException ex) {
            errorMessage = ex.getMessage();
        }
        if( request.getParameter("add") != null ) {
        processRequest(request, response);
        }
        else if(request.getParameter("view") != null) { 
            response.sendRedirect("BloodDonationTable");
        }
    }
    
     
    @Override
    public String getServletInfo() {
        return "Blood Donation Entity Creation";
    }
    
}
