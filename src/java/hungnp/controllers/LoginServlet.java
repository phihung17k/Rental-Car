/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.daos.UserDAO;
import hungnp.vertification.Verification;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private static final String FILE_NOT_FOUND_PAGE="/WEB-INF/view/fileNotFound.jsp";
    private static final String LOGIN_PAGE="/WEB-INF/view/login.jsp";
    private static final String GETTING_CAR_DEFAULT_CONTROLLER="/GettingCarDefaultServlet";
    
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
            session.removeAttribute("errorLogin");
            session.removeAttribute("login_userId");
            session.removeAttribute("emailNotConfirm");
            
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");
            
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            
            boolean checkingRecaptcha = Verification.verifyRecaptcha(gRecaptchaResponse);
            
            UserDAO userDAO = new UserDAO();
            boolean checkingLogin = userDAO.checkLogin(userId, password);
            
            if(!checkingLogin){
                session.setAttribute("errorLogin", "UserID or Password is incorrect");
                url = LOGIN_PAGE;
            }else if(!checkingRecaptcha){
                session.setAttribute("errorLogin", "invalid ReCAPTCHA");
                url = LOGIN_PAGE;
            }else if(userDAO.checkNewUser(userId)){
                session.setAttribute("emailNotConfirm", "this email has not been confirmed");
                url = LOGIN_PAGE;
//                System.out.println("l");
            }
            session.setAttribute("email", userId);
            if(checkingRecaptcha && checkingLogin && !userDAO.checkNewUser(userId)){
                String userName = userDAO.getUserName(userId);
                session.setAttribute("userName", userName);
                url = GETTING_CAR_DEFAULT_CONTROLLER;
//                System.out.println("login success");
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log("LoginServlet_processRequest:"+e.getMessage());
        }finally{
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
























































