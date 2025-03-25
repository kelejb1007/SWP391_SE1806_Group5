/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff.manageaccount;

import DAO.LockAccountLogDAO;
import DAO.ManagerAccountDAO;
import DAO.UserAccountDAO;
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
import model.LockAccountLog;
import model.UserAccount;

/**
 *
 * @author Admin LienXuanThinh_ce182117
 */
@WebServlet(name = "ManageAccountController", urlPatterns = {"/manageaccount"})
public class ManageAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.trim().equals("")) {
            action = "default";
        }

        switch (action) {
            case "search":
                searchAccount(request, response);
                break;
            case "viewLocked":
                viewLockedAccounts(request, response);
                break;
            case "lock":
                lockAccount(request, response);
                break;
            case "unlock":
                unlockAccount(request, response);
                break;
            case "viewall":
                viewAllAccounts(request, response);
                break;
            case "viewLockingHistory":
                viewLockingHistory(request, response);
                break;
            default:
                viewAllAccounts(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hỗ trợ tìm kiếm nếu form được gửi bằng POST
        doGet(request, response);
    }

    /**
     * Hiển thị danh sách tất cả các tài khoản.
     */
    private void viewAllAccounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserAccountDAO accountDAO = new UserAccountDAO();
        try {
            List<UserAccount> listAccount = accountDAO.getAllAccounts();
            request.setAttribute("listAccount", listAccount);
            request.getRequestDispatcher("/WEB-INF/views/staff/viewAllAccounts.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageAccountController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading accounts");
        }
    }

    /**
     * Tìm kiếm tài khoản theo từ khóa.
     */
    private void searchAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        UserAccountDAO accountDAO = new UserAccountDAO();
        try {
            List<UserAccount> listAccount = accountDAO.searchAccounts(keyword);
            request.setAttribute("listAccount", listAccount);
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("/WEB-INF/views/staff/viewAllAccounts.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageAccountController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error searching accounts");
        }
    }

    /**
     * Hiển thị danh sách tài khoản bị khóa.
     */
    private void viewLockedAccounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserAccountDAO accountDAO = new UserAccountDAO();
        List<UserAccount> lockedAccounts = accountDAO.getLockedAccounts();
        if (lockedAccounts == null || lockedAccounts.isEmpty()) {
            Logger.getLogger(ManageAccountController.class.getName()).log(Level.WARNING, "No locked accounts found.");
        } else {
            Logger.getLogger(ManageAccountController.class.getName()).log(Level.INFO, "Loaded {0} locked accounts.", lockedAccounts.size());
        }
        request.setAttribute("listLockedAccounts", lockedAccounts);
        request.getRequestDispatcher("/WEB-INF/views/staff/viewLockedAccounts.jsp").forward(request, response);
    }

    private void lockAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        String lockReason = request.getParameter("lockReason"); // Assume lock reason comes from the request

        UserAccountDAO accountDAO = new UserAccountDAO();
        LockAccountLogDAO logDAO = new LockAccountLogDAO();
        try {
            accountDAO.updateLockStatus(userID, true); // true = locked
            // Log the action
            logDAO.logAccountLockAction(1, userID, "lock", lockReason); // Assuming managerID is 1, update it as necessary
            response.sendRedirect("manageaccount?action=viewall&success=lock");
        } catch (SQLException ex) {
            Logger.getLogger(ManageAccountController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error locking account");
        }
    }

    private void unlockAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));

        UserAccountDAO accountDAO = new UserAccountDAO();
        LockAccountLogDAO logDAO = new LockAccountLogDAO();
        try {
            accountDAO.updateLockStatus(userID, false); // false = unlocked
            // Log the action
            logDAO.logAccountLockAction(1, userID, "unlock", ""); // Empty reason for unlocking, modify as needed
            response.sendRedirect("manageaccount?action=viewLocked&success=unlock");
        } catch (SQLException ex) {
            Logger.getLogger(ManageAccountController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error unlocking account");
        }
    }

    private void viewLockingHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LockAccountLogDAO logDAO = new LockAccountLogDAO();
        try {
            List<LockAccountLog> logList = logDAO.getAllLockLogs();
            request.setAttribute("logList", logList);
            request.getRequestDispatcher("/WEB-INF/views/staff/viewLockingHistory.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageAccountController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading lock history");
        }
    }

}
