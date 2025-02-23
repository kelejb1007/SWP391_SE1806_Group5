/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staff;

import DAO.AccountDAO;
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
            case "viewdetail":
                // Nếu có chức năng xem chi tiết, triển khai tại đây.
                viewAccountDetail(request, response);
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
        AccountDAO accountDAO = new AccountDAO();
        try {
            List<ManagerAccount> listAccount = accountDAO.getAllAccounts();
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
        AccountDAO accountDAO = new AccountDAO();
        try {
            List<ManagerAccount> listAccount = accountDAO.searchAccounts(keyword);
            request.setAttribute("listAccount", listAccount);
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("/WEB-INF/views/staff/viewAllAccounts.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageAccountController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error searching accounts");
        }
    }
    
    /**
     * Xem chi tiết tài khoản (nếu cần).
     */
    private void viewAccountDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ví dụ: lấy managerID từ request, sau đó gọi DAO để lấy thông tin chi tiết.
        // Hiện tại chưa triển khai.
        response.getWriter().println("Chức năng xem chi tiết tài khoản chưa được triển khai.");
    }
}
