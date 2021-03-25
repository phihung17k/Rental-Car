/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.object.CartObject;
import hungnp.daos.CarDAO;
import hungnp.daos.OrderDetailDAO;
import hungnp.dtos.CarDTO;
import hungnp.object.TempAddedCar;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "SearchCarServlet", urlPatterns = {"/SearchCarServlet"})
public class SearchCarServlet extends HttpServlet {

    private static final String FILE_NOT_FOUND_PAGE = "/WEB-INF/view/fileNotFound.jsp";
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
    private int findIndexInList(List<CarDTO> listCar, TempAddedCar carInCart, String stringRentalDate, String stringReturnDate) throws ParseException {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < listCar.size(); i++) {
            CarDTO car = listCar.get(i);
            Date rentalDate = simpleDate.parse(stringRentalDate);//input
            Date returnDate = simpleDate.parse(stringReturnDate);
            Date carRental = simpleDate.parse(carInCart.getRentalDate());//in cart
            Date carReturn = simpleDate.parse(carInCart.getReturnDate());
            if (car.getCarId().equals(carInCart.getCarId())) {
                if((rentalDate.compareTo(carRental)<0 && returnDate.compareTo(carRental)>=0) 
                    || (returnDate.compareTo(carReturn)>0 && rentalDate.compareTo(carReturn)<=0) 
                    || (rentalDate.compareTo(carRental)>=0 && returnDate.compareTo(carReturn)<=0)){
                    return i;
                }
            }
        }
        return -1;
    }
    
    private int findIndexInListByCarID(List<CarDTO> listCar, String carId) {
        for (int i = 0; i < listCar.size(); i++) {
            if(listCar.get(i).getCarId().equals(carId)){
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
            session.removeAttribute("listCar");
            session.removeAttribute("numOfPage");
            

            String stringRentalDate = request.getParameter("rentalDate");
            String stringReturnDate = request.getParameter("returnDate");
            String amountInput = request.getParameter("amountInput");

            if (stringRentalDate == null && stringReturnDate == null && amountInput == null) {
                stringRentalDate = (String) session.getAttribute("rentalDate");
                stringReturnDate = (String) session.getAttribute("returnDate");
                amountInput = (String) session.getAttribute("amountInput");
            }

            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            String option = request.getParameter("option");
            if (option == null) {
                option = (String) session.getAttribute("option");
            }
            String carName = null;
            String categoryId = null;
            List<CarDTO> listAllCar = null;
            CarDAO carDAO = new CarDAO();
            if (option.equals("carName")) {
                carName = request.getParameter("name");
                if (carName == null) {
                    carName = (String) session.getAttribute("carName");
                }
                listAllCar = carDAO.searchAllCarByName(carName);
            } else if (option.equals("carCategory")) {
                categoryId = request.getParameter("category");
                if (categoryId == null) {
                    categoryId = (String) session.getAttribute("categoryId");
                }
                listAllCar = carDAO.searchAllCarByCategory(categoryId);
            }

            if (listAllCar != null) {
                CartObject cartObject = (CartObject) session.getAttribute("cartObject");
                if (cartObject != null) {
                    Map<TempAddedCar, Integer> cars = cartObject.getCars();
                    if (cars != null) {

                        //listCar: carId, quantity ; rentalDate; returnDate
                        //carInCart: carId, rentalDate, returnDate; quantity
                        for (TempAddedCar carInCart : cars.keySet()) {
                            int index = findIndexInList(listAllCar, carInCart, stringRentalDate, stringReturnDate);
                            if (index > -1) {
                                int quantityInList = listAllCar.get(index).getQuantity();
                                if (quantityInList - cars.get(carInCart) >= Integer.parseInt(amountInput)) {
                                    listAllCar.get(index).setQuantity(quantityInList - cars.get(carInCart));
                                } else {
                                    listAllCar.remove(index);
                                }
                            }
                        }
                    }
                }
                
                //quantity in list - quantity in cart - quantity in orderdetail 
                Map<String, Integer> mapCarConflict = null; // key: carId, value: amount car in orderdetail
                if (option.equals("carName")) {
                    mapCarConflict = carDAO.getCarsConflictDateInDetailByName(stringRentalDate, stringReturnDate, carName);
                } else if (option.equals("carCategory")) {
                    mapCarConflict = carDAO.getCarsConflictDateInDetailByCategory(stringRentalDate, stringReturnDate, categoryId);
                }
                if (mapCarConflict != null) {
                    for (String carId : mapCarConflict.keySet()) {
                        int index = findIndexInListByCarID(listAllCar, carId);
                        if (index >= 0) {
                            int remainCar = listAllCar.get(index).getQuantity();
                            int amountCarInOrderDetail = mapCarConflict.get(carId);
                            if (remainCar - amountCarInOrderDetail >= Integer.parseInt(amountInput)) {
                                listAllCar.get(index).setQuantity(remainCar - amountCarInOrderDetail);
                            } else {
                                listAllCar.remove(index);
                            }
                        }
                    }
                }
                int index = 0;
                while(listAllCar.size()>0 && index < listAllCar.size()){
                    int remainCar = listAllCar.get(index).getQuantity();
                    if(remainCar < Integer.parseInt(amountInput)){
                        listAllCar.remove(index);
                    }else{
                        index++;
                    }
                }
                
//                System.out.println("AFTERlistAll:" + listAllCar.size());
                if (listAllCar.size() > 0) {
                    double totalCar = listAllCar.size();
                    double amountCarPerPage = 9;

                    int numOfPage = (int) Math.ceil(totalCar / amountCarPerPage);
                    int fromIndex = 0;
                    int toIndex = 0;

                    String page = request.getParameter("page");
                    if (page != null) {
                        if (page.matches("^\\d+$")) {
                            int currentPage = Integer.parseInt(page);
                            if (currentPage > 0 && currentPage <= numOfPage) {
                                fromIndex = (int) (currentPage * amountCarPerPage - amountCarPerPage);
                                toIndex = fromIndex + 9;
                                url = SHOW_CAR_PAGE;
                                if (currentPage == numOfPage && totalCar % 9 != 0) {
                                    toIndex = fromIndex + (int) (totalCar % 9);
                                }
                            }
                        }
                    } else {
                        if (listAllCar.size() >= 9) {
                            toIndex = 9;
                        } else {
                            toIndex = listAllCar.size();
                        }
                        url = SHOW_CAR_PAGE;
                    }
//                        System.out.println("from:"+fromIndex+";to:"+toIndex);
                    List<CarDTO> listCar = listAllCar.subList(fromIndex, toIndex);

                    session.setAttribute("listCar", listCar);
                    session.setAttribute("listAllCar", listAllCar);
                    if(numOfPage > 1){
                        session.setAttribute("numOfPage", numOfPage);
                    }
                } else {
                    url = SHOW_CAR_PAGE;
                }
            } else {
                url = SHOW_CAR_PAGE;
            }

        } catch (Exception e) {
//            e.printStackTrace();
            log("SearchCarServlet_processRequest:" + e.getMessage());
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













































































































