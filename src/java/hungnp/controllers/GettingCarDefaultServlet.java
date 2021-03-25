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
import java.util.ArrayList;
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
@WebServlet(name = "GettingCarDefaultServlet", urlPatterns = {"/GettingCarDefaultServlet"})
public class GettingCarDefaultServlet extends HttpServlet {

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
            Date rentalDate = simpleDate.parse(stringRentalDate);
            Date returnDate = simpleDate.parse(stringReturnDate);
            Date carRental = simpleDate.parse(carInCart.getRentalDate());
            Date carReturn = simpleDate.parse(carInCart.getReturnDate());
            if (car.getCarId().equals(carInCart.getCarId()) && rentalDate.compareTo(carRental)==0 
                    && returnDate.compareTo(carReturn) == 0) {
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

            CarDAO carDAO = new CarDAO();
            List<CarDTO> listAllCar = carDAO.searchAllCarByName("");
            
            if (listAllCar != null) {
                //quantity in list - quantity in cart
                CartObject cartObject = (CartObject) session.getAttribute("cartObject");
                if (cartObject != null) {
                    Map<TempAddedCar, Integer> cars = cartObject.getCars();
                    if (cars != null) {
                        String stringRentalDate = (String) session.getAttribute("rentalDate");
                        String stringReturnDate = (String) session.getAttribute("returnDate");
//                        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//                        Date rentalDate = simpleDate.parse(stringRentalDate);
//                        Date returnDate = simpleDate.parse(stringReturnDate);

                        //listCar: carId, quantity ; rentalDate; returnDate
                        //carInCart: carId, rentalDate, returnDate; quantity
                        for (TempAddedCar carInCart : cars.keySet()) {
                            int index = findIndexInList(listAllCar, carInCart, stringRentalDate, stringReturnDate);
                            if (index > -1) {
                                int quantityInList = listAllCar.get(index).getQuantity();
                                if (quantityInList - cars.get(carInCart) > 0) {
                                    listAllCar.get(index).setQuantity(quantityInList - cars.get(carInCart));
                                } else {
                                    listAllCar.remove(index);
                                }
                            }
                        }
                    }
                }
                
                //quantity in list - quantity in cart - quantity in orderdetail 
                int count = 0;
                int index = 0;
                while(index < listAllCar.size() && count<20){
                    CarDTO car = listAllCar.get(index);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(new Date());
                    //get amount of car in order detail from rental to return date by carId
                    int amountCarInOrderDetail = carDAO.getAmountCarInOrderDetail(date, date, car.getCarId());
                    if(amountCarInOrderDetail>-1){//exist car in orderdetail 
                        if(car.getQuantity() - amountCarInOrderDetail > 0){
                            listAllCar.get(index).setQuantity(car.getQuantity() - amountCarInOrderDetail);
                            count++;
                        }else{
                            listAllCar.remove(index);
                        }
                    }else{//not exist
                        count++;
                    }
                    index++;
                }
                
                

                if (listAllCar.size() > 0) {
                    double totalCar = 20;
                    double amountCarPerPage = 9;
                    int numOfPage = (int) Math.ceil(totalCar / amountCarPerPage);
                    int fromIndex = 0;
                    int toIndex = 0;

                    String page = request.getParameter("page");
                    if (page != null) {
                        if (page.matches("^\\d+$")) {
                            int currentPage = Integer.parseInt(page);
                            if (currentPage >= 1 && currentPage <= numOfPage) {
                                fromIndex = (int) (currentPage * amountCarPerPage - amountCarPerPage);
                                toIndex = fromIndex + 9;
                                url = SHOW_CAR_PAGE;
                                if (currentPage == numOfPage && totalCar%9 != 0) {
                                    toIndex = (int) (fromIndex + (totalCar % 9));
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
                    List<CarDTO> listCar = null;
                    if (listCar == null) {
                        listCar = new ArrayList<>();
                    }
                    //subList: get between fromIndex and toIndex - 1
                    listCar = listAllCar.subList(fromIndex, toIndex);
//                    System.out.println("ft:"+fromIndex+";"+toIndex);
//                    for (CarDTO carDTO : listCar) {
//                        System.out.println(carDTO.getCarName());
//                    }
                    session.setAttribute("listCar", listCar);
                    if(numOfPage > 1){
                        session.setAttribute("numOfPage", numOfPage);
                    }
                } else {
                    url = SHOW_CAR_PAGE;
                }

            }else{
                url = SHOW_CAR_PAGE;
            }

            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, String> mapCarCategory = carDAO.getAllCarCategory();

            session.setAttribute("minDate", simpleDate.format(new Date()));
            session.setAttribute("mapCarCategory", mapCarCategory);

        } catch (Exception e) {
//            e.printStackTrace();
            log("GettingCarDefaultServlet_processRequest:" + e.getMessage());
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











































































































