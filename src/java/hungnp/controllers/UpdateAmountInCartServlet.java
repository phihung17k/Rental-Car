/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.object.CartObject;
import hungnp.daos.CarDAO;
import hungnp.dtos.CarDTO;
import hungnp.object.TempAddedCar;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
@WebServlet(name = "UpdateAmountInCartServlet", urlPatterns = {"/UpdateAmountInCartServlet"})
public class UpdateAmountInCartServlet extends HttpServlet {

    private static final String FILE_NOT_FOUND_PAGE = "/WEB-INF/view/fileNotFound.jsp";
    private static final String VIEW_CART_PAGE = "/WEB-INF/view/viewCart.jsp";

    private static final String VIEW_CART_CONTROLLER = "/ViewCartServlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private int getQuantityConflictInCart(Map<TempAddedCar, Integer> cars, String stringRentalDate, String stringReturnDate, String carId) throws ParseException {
        int quantityConflict = 0;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        Date carRental = simpleDate.parse(stringRentalDate);//input
        Date carReturn = simpleDate.parse(stringReturnDate);
        for (TempAddedCar tempCar : cars.keySet()) {
            Date rentalDate = simpleDate.parse(tempCar.getRentalDate());
            Date returnDate = simpleDate.parse(tempCar.getReturnDate());
            if (carId.equals(tempCar.getCarId())) {
                if ((rentalDate.compareTo(carRental) < 0 && returnDate.compareTo(carRental) >= 0)
                        || (returnDate.compareTo(carReturn) > 0 && rentalDate.compareTo(carReturn) <= 0)
                        || (rentalDate.compareTo(carRental) >= 0 && returnDate.compareTo(carReturn) <= 0)) {
                    quantityConflict += cars.get(tempCar);
                }
            }
        }

        return quantityConflict;
    }

    private int findIndexCar(List<CarDTO> listAllCar, String carId) {
        for (int i = 0; i < listAllCar.size(); i++) {
            if (listAllCar.get(i).getCarId().equals(carId)) {
                return i;
            }
        }
        return -1;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FILE_NOT_FOUND_PAGE;
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("carIdUpdateError");
            session.removeAttribute("updateAmountError");
            session.removeAttribute("amountUpdateError");
            session.removeAttribute("rentalDateUpdateCar");
            session.removeAttribute("returnDateUpdateCar");

            CarDAO carDAO = new CarDAO();

            String carId = request.getParameter("carId");
            String stringRentalDate = request.getParameter("rentalDate");
            String stringReturnDate = request.getParameter("returnDate");
            String amountInputInCart = request.getParameter("amountInput");
            int quantityInput = Integer.parseInt(amountInputInCart);

            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
            Date rentalDate = simpleDate.parse(stringRentalDate);
            Date returnDate = simpleDate.parse(stringReturnDate);

            List<CarDTO> listAllCar = (List<CarDTO>) session.getAttribute("listAllCar");
            int index = -1;
            if (carDAO.getQuantityById(carId) > -1) {

                CartObject cartObject = (CartObject) session.getAttribute("cartObject");
                if (cartObject != null) {
                    Map<TempAddedCar, Integer> cars = cartObject.getCars();
                    if (cars != null) {
                        int quantityConflict = getQuantityConflictInCart(cars, stringRentalDate, stringReturnDate, carId);
                        if (quantityConflict > 0) {
                            index = findIndexCar(listAllCar, carId);
                            if (index > -1) {
                                int remainQuantity = listAllCar.get(index).getQuantity();
                                System.out.println("re:" + remainQuantity + "; " + quantityInput);
                                if (remainQuantity - quantityInput < 0) {
                                    session.setAttribute("carIdUpdateError", carId);
                                    session.setAttribute("rentalDateUpdateCar", stringRentalDate);
                                    session.setAttribute("returnDateUpdateCar", stringReturnDate);
                                    session.setAttribute("amountUpdateError", amountInputInCart);
                                    session.setAttribute("updateAmountError", "amount is out of stock");
                                    url = VIEW_CART_PAGE;
                                }
                            }
                        }
                    }
                }
            }
            if (listAllCar.get(index) != null) {
                int remainCar = listAllCar.get(index).getQuantity();
                int amountCarInOrderDetail = carDAO.getAmountCarInOrderDetail(stringRentalDate, stringReturnDate, carId);
                if (remainCar - amountCarInOrderDetail >= quantityInput) {
                    CartObject cartObject = (CartObject) session.getAttribute("cartObject");
                    Map<TempAddedCar, Integer> cars = cartObject.getCars();
                    Set<TempAddedCar> setCar = cars.keySet();
                    Iterator<TempAddedCar> iter = setCar.iterator();
                    int flag = 1;
                    while (iter.hasNext() && flag == 1) {
                        TempAddedCar carInCart = iter.next();
                        Date carRental = simpleDate.parse(carInCart.getRentalDate());
                        Date carReturn = simpleDate.parse(carInCart.getReturnDate());
                        if (carId.equals(carInCart.getCarId()) && rentalDate.compareTo(carRental) == 0
                                && returnDate.compareTo(carReturn) == 0) {
                            cars.put(carInCart, quantityInput);
                            flag = 0;
                        }
                    }
                    url = VIEW_CART_CONTROLLER;

                } else {
                    session.setAttribute("carIdUpdateError", carId);
                    session.setAttribute("rentalDateUpdateCar", stringRentalDate);
                    session.setAttribute("returnDateUpdateCar", stringReturnDate);
                    session.setAttribute("amountUpdateError", amountInputInCart);
                    session.setAttribute("updateAmountError", "amount is out of stock");
                    url = VIEW_CART_PAGE;
                }
            }
        } catch (Exception e) {
            log("UpdateAmountInCart_processRequest:" + e.getMessage());
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










