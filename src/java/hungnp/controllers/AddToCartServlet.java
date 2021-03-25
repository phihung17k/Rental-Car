/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.object.CartObject;
import hungnp.dtos.CarDTO;
import hungnp.object.TempAddedCar;
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
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private static final String GET_DEFAULT_CAR_CONTROLLER = "/GettingCarDefaultServlet";
    private static final String SEARCH_CAR_CONTROLLER = "/SearchCarServlet";

    private static final String SHOW_CAR_PAGE = "/WEB-INF/view/showCar.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private int findIndexOfCar(String carId, List<CarDTO> listCar) {
        for (int i = 0; i < listCar.size(); i++) {
            if (carId.equals(listCar.get(i).getCarId())) {
                return i;
            }
        }
        return -1;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SEARCH_CAR_CONTROLLER;
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("addError");
//            System.out.println("ss:"+session.getAttribute("amountInput"));
            String amountInput = (String) session.getAttribute("amountInput");
            if (amountInput == null || amountInput.trim().isEmpty()) {
                url = SHOW_CAR_PAGE;
                session.setAttribute("addError", "please input amount and search");
            } else {
                
                String carId = request.getParameter("carId");
                String stringRentalDate = (String) session.getAttribute("rentalDate");
                String stringReturnDate = (String) session.getAttribute("returnDate");
                
//                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//                Date rentalDate = simpleDate.parse(stringRentalDate);
//                Date returnDate = simpleDate.parse(stringReturnDate);

                CartObject cartObject = (CartObject) session.getAttribute("cartObject");
                if (cartObject == null) {
                    cartObject = new CartObject();
                }
                cartObject.addCar(new TempAddedCar(carId, stringRentalDate, stringReturnDate));

                if (amountInput == null) {
                    url = GET_DEFAULT_CAR_CONTROLLER;
                }
                session.setAttribute("cartObject", cartObject);
            }

        } catch (Exception e) {
//            e.printStackTrace();
            log("AddToCartServlet_processRequest:" + e.getMessage());
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











