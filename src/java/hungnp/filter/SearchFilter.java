/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.filter;

import hungnp.errors.SearchError;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Win 10
 */
@WebFilter(filterName = "SearchFilter", urlPatterns = {"/search"})
public class SearchFilter implements Filter {
    
    private static final boolean debug = true;
    
    private static final String SHOW_CAR_PAGE="/WEB-INF/view/showCar.jsp";
    private static final String SEARCH_CAR_CONTROLLER="/SearchCarServlet";

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public SearchFilter() {
    }    
    
  
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    private Date convertStringToDate(String string) throws ParseException{
        SimpleDateFormat simpleDate= new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDate.parse(string);
        return date;
    }
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        String url = SEARCH_CAR_CONTROLLER;
        try {
            HttpSession session = ((HttpServletRequest) request).getSession();
            session.removeAttribute("searchError");
            session.removeAttribute("addError");

            SearchError searchError = new SearchError();
            boolean checkError = false;
            
            String option = request.getParameter("option");
            if(option==null){
                option = (String) session.getAttribute("option");
            }
//            System.out.println("option:"+option);
            if(option.equals("carName")){
                String carName = request.getParameter("name");
                if(carName==null){
                    carName = (String) session.getAttribute("carName");
//                    System.out.println("carName:"+carName);
                }
                if (carName.trim().isEmpty()) {
                    searchError.setNameEmptyError("name is not empty");
                    checkError = true;
                }
                session.setAttribute("carName", carName);
            }else if(option.equals("carCategory")){
                String categoryId = request.getParameter("category");
                if (categoryId == null) {
                    categoryId = (String) session.getAttribute("categoryId");
                }
                session.setAttribute("categoryId", categoryId);
            }
            String rentalDate = request.getParameter("rentalDate");
            String returnDate = request.getParameter("returnDate");
            String amountInput = request.getParameter("amountInput");
//            System.out.println("rentalDate:"+rentalDate);
//            System.out.println("returnDate:"+returnDate);
//            System.out.println("amountCar:"+amountInput);
            
            if(rentalDate==null && returnDate==null && amountInput==null){
                rentalDate = (String) session.getAttribute("rentalDate");
                returnDate = (String) session.getAttribute("returnDate");
                amountInput = (String) session.getAttribute("amountInput");
            }
//            System.out.println("rent:" + rentalDate + "; return:" + returnDate);
            if(!rentalDate.isEmpty() && !returnDate.isEmpty()){
                Date dateRental = convertStringToDate(rentalDate);
                Date dateReturn = convertStringToDate(returnDate);
                if(dateRental.compareTo(dateReturn)>0){
                    searchError.setRentalLessThanReturnDateError("rental date is less than return date");
                    checkError = true;
                }
            }
//            System.out.println("am:"+amountInput);
            if(amountInput.trim().isEmpty()){
                searchError.setAmountEmptyError("amount is not empty");
                checkError = true;
            }else if(amountInput.matches("^\\d+$")){
                if(Integer.parseInt(amountInput)<1){
                    searchError.setAmountEmptyError("amount must be more than 0");
                    checkError = true;
                }
            }else{
                searchError.setAmountEmptyError("amount must be a number");
                checkError = true;
            }
            
            if(checkError){
                url = SHOW_CAR_PAGE;
                session.setAttribute("searchError", searchError);
            }
            
//            System.out.println("ul:"+url);
            
            session.setAttribute("option", option);
            session.setAttribute("carCategory", "carCategory");
            session.setAttribute("rentalDate", rentalDate);
            session.setAttribute("returnDate", returnDate);
            session.setAttribute("amountInput", amountInput);
            
            if(url!=null){
                request.getServletContext().getRequestDispatcher(url).forward(request, response);
            }else{
                chain.doFilter(request, response);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("SearchFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SearchFilter()");
        }
        StringBuffer sb = new StringBuffer("SearchFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}































































































































