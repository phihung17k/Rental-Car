/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.daos.FeedbackDAO;
import hungnp.daos.OrderDetailDAO;
import hungnp.dtos.CarDTO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
@WebServlet(name = "ViewFeedbackServlet", urlPatterns = {"/ViewFeedbackServlet"})
public class ViewFeedbackServlet extends HttpServlet {
    private static final String FEEDBACK_PAGE="/WEB-INF/view/feedback.jsp";
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
        String url =FEEDBACK_PAGE;
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("mapRating");
//            System.out.println("a");
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            List<CarDTO> listReturnedCar = orderDetailDAO.getListReturnedCar();
            if(listReturnedCar!=null){
                FeedbackDAO feedbackDAO = new FeedbackDAO();
                String userId = (String) session.getAttribute("email");
                Map<CarDTO, Integer> mapRating = new HashMap<>();
                for (CarDTO car : listReturnedCar) {
                    int rating = feedbackDAO.getRating(userId, car.getCarId());
                    if(rating>-1){
                        mapRating.put(car, rating);
                    }else{
                        mapRating.put(car, null);
                    }
                }
//                System.out.println("size:"+mapRating.size());
                session.setAttribute("mapRating", mapRating);
            }
            
        } catch (Exception e) {
//            e.printStackTrace();
            log("ViewFeedbackServlet_processRequest:"+e.getMessage());
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
































