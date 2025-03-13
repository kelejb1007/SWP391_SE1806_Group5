/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin.managestaff;

import DAO.ManagerAccountDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ManagerAccount;

@WebServlet(name = "ManageStaffController", urlPatterns = {"/managestaff"})
public class ManageStaffController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.trim().equals("")) {
            action = "default";
        }
        
        switch (action) {
            case "search":
                searchStaff(request, response);
                break;
            case "viewdetail":
                viewStaffDetail(request, response);
                break;
            default:
                viewAllStaff(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void viewAllStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
        try {
            List<ManagerAccount> listStaff = accountDAO.getAccountsByRole("Staff");  
            request.setAttribute("listStaff", listStaff);
            request.getRequestDispatcher("/WEB-INF/views/admin/ViewAllStaff.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageStaffController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading staff accounts");
        }
    }

    private void searchStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
        try {
            List<ManagerAccount> listStaff = accountDAO.searchAccountsByRole("Staff", keyword);
            request.setAttribute("listStaff", listStaff);
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("/WEB-INF/views/admin/ViewAllStaff.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageStaffController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error searching staff accounts");
        }
    }

    private void viewStaffDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("Staff account detail functionality is not yet implemented.");
    }
}
