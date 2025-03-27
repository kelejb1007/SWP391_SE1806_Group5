/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.ManagerAccountDAO;
import java.util.List;
import model.ManagerAccount;


/**
 *
 * @author ASUS
 */
@WebServlet(name="AssignPermissionController", urlPatterns={"/AssignPermissionController"})
public class AssignPermissionController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
    List<ManagerAccount> accounts = accountDAO.getAllManagers(); // Lấy danh sách từ DB
    request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/WEB-INF/views/admin/assignPermission.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            int managerID = Integer.parseInt(request.getParameter("managerID"));
            boolean canLock = "on".equals(request.getParameter("canLock"));
            boolean canApprove = "on".equals(request.getParameter("canApprove"));
            
            ManagerAccountDAO accountDAO = new ManagerAccountDAO();
            boolean success = accountDAO.updatePermissions(managerID, canLock, canApprove);
            
            if (success) {
                request.setAttribute("message", "Cập nhật quyền thành công!");
            } else {
                request.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            }
             // Load lại danh sách tài khoản
        List<ManagerAccount> accounts = accountDAO.getAllManagers();
        request.setAttribute("accounts", accounts);
        } catch (Exception e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ!");
        }
        request.getRequestDispatcher("/WEB-INF/views/admin/assignPermission.jsp").forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet xử lý việc gán quyền cho tài khoản quản trị";
    }
}
