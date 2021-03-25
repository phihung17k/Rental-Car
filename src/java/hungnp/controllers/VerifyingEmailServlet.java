/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers;

import hungnp.daos.UserDAO;
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
@WebServlet(name = "VerifyingEmailServlet", urlPatterns = {"/VerifyingEmailServlet"})
public class VerifyingEmailServlet extends HttpServlet {
    private static final String VERIFYING_EMAIL_PAGE="/WEB-INF/view/verifyEmail.jsp";
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
        String url = VERIFYING_EMAIL_PAGE ;
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("incorrectCode");
            
            String codeInput = request.getParameter("codeInput");
            String codeVerification = (String) session.getAttribute("codeVerification");
            if(codeVerification!=null){
                if(codeInput.equals(codeVerification)){
                    String email = (String) session.getAttribute("email");
                    UserDAO userDAO = new UserDAO();
                    userDAO.updateStatusUser(email);
                    String userName = userDAO.getUserName(email);
                    session.setAttribute("userName", userName);
                    url = GETTING_CAR_DEFAULT_CONTROLLER;
                }else{
                    session.setAttribute("incorrectCode", "code verification is incorrect");
                    session.setAttribute("codeInput", codeInput);
                }
            }else{
                session.setAttribute("emailNotFound", "not found email to verify");
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log("VerifyingEmailServlet_processRequest:"+e.getMessage());
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





























