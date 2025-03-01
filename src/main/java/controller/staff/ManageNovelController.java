/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import DAO.LockNovelLogDAO;
import DAO.NovelDAO;
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
import model.LockNovelLog;
import model.ManagerAccount;
import model.Novel;
import model.UserAccount;

/**
 *
 * @author Nguyen Thanh Trung
 */
@WebServlet(name = "ManageNovelController", urlPatterns = {"/managenovel"})
public class ManageNovelController extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NovelController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NovelController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("ManagerLogin");
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
            case "viewlockedlist":
                viewLockedNovels(request, response);
                break;
            default:
                viewAllNovels(request, response);
        }
    }

    private void viewAllNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        try {
            listNovel = nd.getAllActiveNovels("active");
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/staff/allNovels.jsp").forward(request, response);
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
            request.getRequestDispatcher("/WEB-INF/views/staff/novelDetail.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void viewLockedNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        try {
            listNovel = nd.getLockedNovels();
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/staff/lockedNovels.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("ManagerLogin");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "lock":
                lockNovel(request, response);
                break;
            case "unlock":
                unlockNovel(request, response);
                break;
            default:
                viewAllNovels(request, response);
        }
    }

    private void lockNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int novelID = Integer.parseInt(request.getParameter("novelID"));
        String lockReason = request.getParameter("lockReason");
        NovelDAO nd = new NovelDAO();
        LockNovelLogDAO ld = new LockNovelLogDAO();
        Boolean checkChange;
        Boolean checkAdd;
        String message;
        try {
            HttpSession session = request.getSession(false);
            ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

            LockNovelLog ll = new LockNovelLog(ma.getManagerID(), novelID, "lock", lockReason);

            checkChange = nd.changeNovelStatus(novelID);
            checkAdd = ld.addLockLog(ll);

            if (checkChange && checkAdd) {
                message = "Lock successfully!";
            } else {
                message = "Error!!!!";
            }
            request.setAttribute("message", message);
            viewAllNovels(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void unlockNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int novelID = Integer.parseInt(request.getParameter("novelID"));
        NovelDAO nd = new NovelDAO();
        LockNovelLogDAO ld = new LockNovelLogDAO();
        Boolean checkChange;
        Boolean checkAdd;
        String message;
        try {
            HttpSession session = request.getSession(false);
            ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

            LockNovelLog ll = new LockNovelLog(ma.getManagerID(), novelID, "unlock", null);

            checkChange = nd.changeNovelStatus(novelID);
            checkAdd = ld.addLockLog(ll);

            if (checkChange && checkAdd) {
                message = "Unlock successfully!";
            } else {
                message = "Error!!!!";
            }
            request.setAttribute("message", message);
            response.sendRedirect("managenovel?action=viewlockedlist");
//            viewLockedNovels(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
