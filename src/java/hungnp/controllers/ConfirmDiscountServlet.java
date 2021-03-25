/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.daos.DiscountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Win 10
 */
@WebServlet(name = "ConfirmDiscountServlet", urlPatterns = {"/ConfirmDiscountServlet"})
public class ConfirmDiscountServlet extends HttpServlet {
    private static final String VIEW_CART_PAGE = "/WEB-INF/view/viewCart.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_CART_PAGE;
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("discountInputError");
            session.removeAttribute("discountCode");
            
            String option = request.getParameter("optionInputDiscount");
            if (option != null) {
                if (option.equals("InputDiscount")) {
                    String discountCode = request.getParameter("discountCode");
                    DiscountDAO discountDAO = new DiscountDAO();
                    if (discountDAO.getDiscountValue(discountCode) > -1) {
                        int discountValue = discountDAO.getDiscountValue(discountCode);
                        double totalPrice = (double) session.getAttribute("totalPrice");
                        session.setAttribute("totalPrice", totalPrice*discountValue/100);
                        session.setAttribute("discountValue", discountValue);
                    }else{
                        session.setAttribute("discountInputError", "discount code is wrong!!!");
                    }
                    session.setAttribute("discountCode", discountCode);
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log("ConfirmDiscountServlet_processRequest:"+e.getMessage());
        } finally{
            this.getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}























