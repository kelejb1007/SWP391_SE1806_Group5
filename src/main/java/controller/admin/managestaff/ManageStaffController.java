/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin.managestaff;

import DAO.LockAccountLogDAO;
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
            case "lock":
                suspendAccount(request, response);
                break;
            case "unlock":
                reactivateAccount(request, response);
                break;

            case "viewlocked":
                viewLockedAccounts(request, response);
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

    private void suspendAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int managerID = Integer.parseInt(request.getParameter("managerID"));
        String lockReason = request.getParameter("lockReason");  // Get lock reason from request

        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
        LockAccountLogDAO logDAO = new LockAccountLogDAO();

        try {
            // Suspend the account
            accountDAO.updateLockStatus(managerID, true); // Set account status to locked (true)

            // Log the action
            logDAO.logAccountLockAction(1, managerID, "lock", lockReason);  // Assuming managerID is 1, update as necessary

            // Redirect to viewLockedStaff.jsp after locking the account
            response.sendRedirect("managestaff?action=viewlocked"); // Redirect to locked accounts page
        } catch (SQLException ex) {
            Logger.getLogger(ManageStaffController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error suspending account");
        }
    }

    private void reactivateAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int managerID = Integer.parseInt(request.getParameter("managerID"));

        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
        LockAccountLogDAO logDAO = new LockAccountLogDAO();

        try {
            // Reactivate the account
            accountDAO.updateLockStatus(managerID, false);
            // Log the action
            logDAO.logAccountLockAction(1, managerID, "unlock", "");  // Empty reason for reactivation, modify as needed
            response.sendRedirect("managestaff?action=viewAll&success=unlock");
        } catch (SQLException ex) {
            Logger.getLogger(ManageStaffController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reactivating account");
        }
    }

    private void viewLockedAccounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
        List<ManagerAccount> lockedAccounts = accountDAO.getLockedAccounts();
        if (lockedAccounts == null || lockedAccounts.isEmpty()) {
            Logger.getLogger(ManageStaffController.class.getName()).log(Level.WARNING, "No locked accounts found.");
        } else {
            Logger.getLogger(ManageStaffController.class.getName()).log(Level.INFO, "Loaded {0} locked accounts.", lockedAccounts.size());
        }
        request.setAttribute("listLockedAccounts", lockedAccounts);
        request.getRequestDispatcher("/WEB-INF/views/admin/viewLockedStaff.jsp").forward(request, response);
    }

    private void lockStaffAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String managerID = request.getParameter("managerID");
        String lockReason = request.getParameter("lockReason");

        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
        try {
            // Thực hiện khóa tài khoản
            boolean locked = accountDAO.lockStaffAccount(managerID, lockReason);

            if (locked) {
                // Tài khoản đã bị khóa, cập nhật lại danh sách staff
                List<ManagerAccount> updatedList = accountDAO.getAllAccounts();
                request.setAttribute("listStaff", updatedList);
                // Forward đến trang hiện tại để cập nhật lại dữ liệu
                request.getRequestDispatcher("/WEB-INF/views/admin/ViewAllStaff.jsp").forward(request, response);
            } else {
                // Xử lý khi không thể khóa tài khoản
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error locking account");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManageStaffController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error locking account");
        }
    }

}
