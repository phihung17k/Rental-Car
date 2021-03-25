/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.filter;

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
@WebFilter(filterName = "DispatcherFilter", urlPatterns = {"/*"})
public class DispatcherFilter implements Filter {

    private static final boolean debug = true;
    private static final String FILE_NOT_FOUND_PAGE = "/WEB-INF/view/fileNotFound.jsp";
    private static final String HOME_PAGE = "/WEB-INF/view/home.jsp";
    private static final String LOGIN_PAGE = "/WEB-INF/view/login.jsp";
    private static final String REGISTER_PAGE = "/WEB-INF/view/register.jsp";
    private static final String SHOW_CART_PAGE = "/WEB-INF/view/showCar.jsp";
    private static final String ORDER_HISTORY_PAGE = "/WEB-INF/view/orderHistory.jsp";

    private static final String LOGIN_CONTROLLER = "/LoginServlet";
    private static final String VERIFYING_EMAIL_CONTROLLER = "/VerifyingEmailServlet";
    private static final String SENDING_CODE_VERIFICATION_CONTROLLER = "/SendingCodeVerificationServlet";
    private static final String LOGOUT_CONTROLLER = "/LogoutServlet";
    private static final String GETTING_CAR_DEFAULT_CONTROLLER = "/GettingCarDefaultServlet";
    private static final String SEARCH_CAR_CONTROLLER = "/SearchCarServlet";
    private static final String ADD_TO_CART_CONTROLLER = "/AddToCartServlet";
    private static final String VIEW_CART_CONTROLLER = "/ViewCartServlet";
    private static final String DELETE_CAR_IN_CART_CONTROLLER = "/DeleteCarInCartServlet";
    private static final String CONFIRM_DISCOUNT_CONTROLLER="/ConfirmDiscountServlet";
    private static final String CONFIRM_ORDER_CONTROLLER = "/ConfirmOrderServlet";
    private static final String ORDER_HISTORY_CONTROLLER = "/OrderHistoryServlet";
    private static final String SHOW_DETAIL_ORDER_CONTROLLER = "/ShowDetailOrderServlet";
    private static final String DELELTE_ORDER_CONTROLLER = "/DeleteOrderServlet";
    private static final String VIEW_FEEDBACK_CONTROLLER="/ViewFeedbackServlet";
    private static final String UPDATE_RATE_CONTROLLER="/UpdateRateServlet";

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public DispatcherFilter() {
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
        HttpServletRequest req = (HttpServletRequest) request;
//        System.out.println("p:"+projectPath);
        String url = FILE_NOT_FOUND_PAGE;

        try {
            HttpSession session = req.getSession();
            session.removeAttribute("emailNotConfirm");
            session.removeAttribute("searchError");

            String userName = (String) session.getAttribute("userName");
            String uri = req.getRequestURI();
//            System.out.println("uri:"+uri);
            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex + 1);
//            System.out.println("re:"+resource);
            if (userName == null) {
                if (resource.trim().isEmpty() || resource.equals("homePage")) {
                    url = HOME_PAGE;
                } else if (resource.equals("loginPage")) {
                    url = LOGIN_PAGE;
                } else if (resource.equals("login")) {
                    url = LOGIN_CONTROLLER;
                } else if (resource.equals("registerPage")) {
                    url = REGISTER_PAGE;
                } else if (resource.equals("register")) {
                    chain.doFilter(request, response);
                } else if (resource.equals("sendCodeVerification")) {
                    url = SENDING_CODE_VERIFICATION_CONTROLLER;
                } else if (resource.equals("verifyEmail")) {
                    url = VERIFYING_EMAIL_CONTROLLER;
                } else if (resource.equals("showCar")) {
                    url = GETTING_CAR_DEFAULT_CONTROLLER;
                } else if (resource.equals("search")) {
                    chain.doFilter(request, response);
                }
            } else {
                if (resource.equals("logout")) {
                    url = LOGOUT_CONTROLLER;
                } else if (resource.equals("showCar")) {
                    url = GETTING_CAR_DEFAULT_CONTROLLER;
                } else if (resource.equals("search")) {
                    chain.doFilter(request, response);
                } else if (resource.equals("addCar")) {
                    url = ADD_TO_CART_CONTROLLER;
                } else if (resource.equals("viewCart")) {
                    url = VIEW_CART_CONTROLLER;
                } else if (resource.equals("updateAmount")) {
                    chain.doFilter(request, response);
                } else if (resource.equals("showCarPage")) {
                    url = SHOW_CART_PAGE;
                } else if (resource.equals("delete")) {
                    chain.doFilter(request, response);
                } else if(resource.equals("confirmDiscount")){
                    url = CONFIRM_DISCOUNT_CONTROLLER;
                }else if (resource.equals("confirmOrder")) {
                    url = CONFIRM_ORDER_CONTROLLER;
                } else if (resource.equals("orderHistory")) {
                    url = ORDER_HISTORY_CONTROLLER;
                } else if (resource.equals("detailOrder")) {
                    url = SHOW_DETAIL_ORDER_CONTROLLER;
                } else if (resource.equals("orderHistoryPage")) {
                    url = ORDER_HISTORY_PAGE;
                } else if (resource.equals("deleteOrder")) {
                    url = DELELTE_ORDER_CONTROLLER;
                } else if(resource.equals("searchOrderHistory")){
                    chain.doFilter(request, response);
                } else if (resource.equals("viewFeedback")){
                    url = VIEW_FEEDBACK_CONTROLLER;
                } else if(resource.equals("updateRating")){
                    url = UPDATE_RATE_CONTROLLER;
                }
            }

            if (url != null) {
                if (!resource.equals("register") && !resource.equals("search") 
                        && !resource.equals("updateAmount") && !resource.equals("delete") && !resource.equals("searchOrderHistory")) {
                    request.getServletContext().getRequestDispatcher(url).forward(request, response);
                }
            } else {
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
                log("DispatcherFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("DispatcherFilter()");
        }
        StringBuffer sb = new StringBuffer("DispatcherFilter(");
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



























