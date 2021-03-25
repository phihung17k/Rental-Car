/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.filter;

import hungnp.daos.UserDAO;
import hungnp.errors.RegisterError;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
@WebFilter(filterName = "RegisterFilter", urlPatterns = {"/register"})
public class RegisterFilter implements Filter {
    
    private static final boolean debug = true;
    private static final String FILE_NOT_FOUND_PAGE="/WEB-INF/view/fileNotFound.jsp";
    private static final String REGISTER_PAGE="/WEB-INF/view/register.jsp";
    
    private static final String REGISTER_CONTROLLER="/RegisterServlet";

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public RegisterFilter() {
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
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        String url = REGISTER_PAGE;
        try {
            HttpSession session = req.getSession();
            session.removeAttribute("registerError");
            session.removeAttribute("email");
            session.removeAttribute("phone");
            session.removeAttribute("name");
            session.removeAttribute("address");
            session.removeAttribute("password");
            
            String email = request.getParameter("userId");
            String phone = request.getParameter("phone");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");
            
            RegisterError error = new RegisterError();
            boolean checkError = false;
            UserDAO userDAO = new UserDAO();
            if (email.trim().isEmpty()) {
                error.setEmailEmptyError("email is not empty");
                checkError = true;
            } else if (!email.matches("^[a-zA-Z](\\w+)?@[a-zA-Z](\\w+)?.\\w+(.\\w+)?$")) {
                error.setEmailFormatError("format email is invalid. Ex: abc@xyz.com or abc@xyz.com.vn");
                checkError = true;
            } else if (userDAO.checkExistedEmail(email)) {
                error.setEmailExistedError("email is existed!!!");
                checkError = true;
            }
            if (phone.trim().isEmpty()) {
                error.setPhoneEmptyError("phone is not empty");
                checkError = true;
            } else if (!phone.matches("^[0-9]{10,15}$")) {
                error.setPhoneInvalidError("phone is a series of numbers have length from 10 to 15 number");
                checkError = true;
            }
            if (name.trim().isEmpty()) {
                error.setNameEmptyError("name is not empty");
                checkError = true;
            }
            if (address.trim().isEmpty()) {
                error.setAddressEmptyError("address is not empty");
                checkError = true;
            }
            if (password.trim().isEmpty()) {
                error.setPasswordEmptyError("password is not empty");
                checkError = true;
            } else {
                if (!confirm.equals(password)) {
                    error.setConfirmNotMatchError("confirm is not match");
                    checkError = true;
                }
            }

            if (checkError) {
                session.setAttribute("registerError", error);
                session.setAttribute("email", email);
                session.setAttribute("phone", phone);
                session.setAttribute("name", name);
                session.setAttribute("address", address);
                session.setAttribute("password", password);
            } else {
                url = REGISTER_CONTROLLER;
            }

            
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
                log("RegisterFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("RegisterFilter()");
        }
        StringBuffer sb = new StringBuffer("RegisterFilter(");
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













































