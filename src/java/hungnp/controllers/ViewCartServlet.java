/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.object.CartObject;
import hungnp.daos.CarDAO;
import hungnp.object.TempAddedCar;
import java.io.IOException;
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
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {
    
    private static final String VIEW_CART_PAGE="/WEB-INF/view/viewCart.jsp";
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
            session.removeAttribute("listCarInCart");
            session.removeAttribute("updateAmountError");
            
            CartObject cart = (CartObject) session.getAttribute("cartObject");
            if(cart!=null){
                Map<TempAddedCar, Integer> cars = cart.getCars();//key: TempAddedCar; value: quantity
                if(cars!=null){
//                    List<CarDTO> listCarInCart = new ArrayList<>();
                    CarDAO carDAO = new CarDAO();
                    double totalPrice = 0;
                    for (TempAddedCar carInCart : cars.keySet()) {
                        List listInfo = carDAO.getCarForCart(carInCart.getCarId());
                        if(listInfo!=null){
                            carInCart.setCarName((String) listInfo.get(0));
                            carInCart.setCarCategory((String) listInfo.get(1));
                            carInCart.setPrice((float) listInfo.get(2));
                        }
                        totalPrice+=carInCart.getPrice()*cars.get(carInCart);
                    }
                    session.setAttribute("listCarInCart", cars);
                    session.setAttribute("totalPrice", totalPrice);
                    for (TempAddedCar car : cars.keySet()) {
                        System.out.println("car:"+car.getCarId());
                    }
                }
            }
            
        } catch (Exception e) {
//            e.printStackTrace();
            log("ViewCartServlet_processRequest:"+e.getMessage());
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













































