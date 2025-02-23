/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import DAO.NovelDAO;
import controller.staff.ManageNovelController;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Novel;
import model.UserAccount;

/**
 *
 * @author Nguyen Thanh Trung
 */
@WebServlet(name="MyNovelController", urlPatterns={"/mynovel"})
public class MyNovelController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyNovelController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyNovelController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null){
            response.sendRedirect("Login");
            return;
        }
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "viewdetail":
                viewNovelDetail(request, response);
                break;
            default:
                viewMyNovels(request, response);
        }
    } 
    
    private void viewMyNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        try {
            HttpSession session = request.getSession(false);
            UserAccount us = (UserAccount) session.getAttribute("user");
            listNovel = nd.getMyNovels(us.getUserID());
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovels.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   private void viewNovelDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        int novelID = Integer.parseInt(request.getParameter("novelID"));
        Novel nl;
        try {
            nl = nd.getNovelByID(novelID);
            request.setAttribute("novel", nl);
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovelDetail.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
