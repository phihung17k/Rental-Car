/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.daos.OrderDAO;
import hungnp.daos.OrderDetailDAO;
import hungnp.dtos.OrderDetailDTO;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "ShowDetailOrderServlet", urlPatterns = {"/ShowDetailOrderServlet"})
public class ShowDetailOrderServlet extends HttpServlet {

    private static final String FILE_NOT_FOUND_PAGE = "/WEB-INF/view/fileNotFound.jsp";
    private static final String SHOW_DETAIL_ORDER_PAGE = "/WEB-INF/view/showDetailOrder.jsp";

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
        String url = FILE_NOT_FOUND_PAGE;
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("listOrderDetail");

            String orderId = request.getParameter("orderId");
            if (orderId != null) {
                String userId = (String) session.getAttribute("email");
                OrderDAO orderDAO = new OrderDAO();
                if (orderDAO.checkExistedOrder(orderId, userId)) {
                    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                    List<OrderDetailDTO> listOrderDetail = orderDetailDAO.getListOrderDetailByOrderId(orderId);
                    if (listOrderDetail != null) {
                        session.setAttribute("listOrderDetail", listOrderDetail);
                        url = SHOW_DETAIL_ORDER_PAGE;
                    }
                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
            log("ShowDetailOrderServlet_processRequest:" + e.getMessage());
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



