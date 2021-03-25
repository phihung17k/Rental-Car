/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.daos.DiscountDAO;
import hungnp.daos.OrderDAO;
import hungnp.daos.OrderDetailDAO;
import hungnp.object.TempAddedCar;
import java.io.IOException;
import java.util.Map;
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
@WebServlet(name = "ConfirmOrderServlet", urlPatterns = {"/ConfirmOrderServlet"})
public class ConfirmOrderServlet extends HttpServlet {

    private static final String ORDER_SUCCESSFUL_PAGE = "/WEB-INF/view/orderSuccess.jsp";
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
    private String getOrderId(int countOrder) {
        if (countOrder < 0) {
            return "OR000001";
        } else if (countOrder >= 0 && countOrder < 9) {
            return "OR00000" + (countOrder + 1);
        } else if (countOrder >= 9 && countOrder < 99) {
            return "OR0000" + (countOrder + 1);
        }
        return "";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_CART_PAGE;
        try {
            HttpSession session = request.getSession();

            OrderDAO orderDAO = new OrderDAO();
            int countOrder = orderDAO.countAmountOfOrder();
            String orderId = getOrderId(countOrder);
            String userId = (String) session.getAttribute("email");
            double totalPrice = (double) session.getAttribute("totalPrice");
            String discountCode = (String) session.getAttribute("discountCode");
            
            boolean checkCreated = false;
            if(session.getAttribute("discountValue")!=null){
                checkCreated = orderDAO.createOrder(orderId, userId, totalPrice, discountCode);
                DiscountDAO discountDAO = new DiscountDAO();
                discountDAO.deleteDiscountCode(discountCode);
            }else{
                checkCreated = orderDAO.createOrder(orderId, userId, totalPrice);
            }

            if (checkCreated) {
                Map<TempAddedCar, Integer> cars =  (Map<TempAddedCar, Integer>) session.getAttribute("listCarInCart");
                OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                for (TempAddedCar carInCart : cars.keySet()) {
                    orderDetailDAO.createOrderDetail(orderId, carInCart.getCarId(), carInCart.getRentalDate(),
                            carInCart.getReturnDate(), cars.get(carInCart));
                }
                cars.clear();
                session.removeAttribute("listCarInCart");
                session.removeAttribute("totalPrice");
                session.removeAttribute("cartObject");
                session.removeAttribute("discountCode");
                session.removeAttribute("discountValue");
                
                url = ORDER_SUCCESSFUL_PAGE;
            }

        } catch (Exception e) {
//            e.printStackTrace();
            log("ConfirmOrderServlet_processRequest:" + e.getMessage());
        } finally {
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






































