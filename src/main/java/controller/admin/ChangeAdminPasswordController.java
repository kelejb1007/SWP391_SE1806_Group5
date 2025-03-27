/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import DAO.ManagerAccountDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpSession;
import model.ManagerAccount;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ChangeAdminPasswordController", urlPatterns = {"/change-password"})
public class ChangeAdminPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get session information
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("ManagerLogin");
            return;
        }

        // Forward to change password page
        request.getRequestDispatcher("/WEB-INF/views/admin/changeAdminPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get session information

        HttpSession session = request.getSession(false); // Không tạo session mới nếu chưa có
        if (session == null || session.getAttribute("managerID") == null) {
            response.sendRedirect("ManagerLogin"); // Chuyển hướng nếu chưa đăng nhập
            return;
        }
        ManagerAccount manager = (ManagerAccount) session.getAttribute("manager");
        int managerID = manager.getManagerID();

        // Retrieve data from form
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Check if passwords match
        if (newPassword == null || !newPassword.equals(confirmPassword)) {

            request.setAttribute("errorMessage", " Confirmation password does not match!");
            request.getRequestDispatcher("/WEB-INF/views/admin/changeAdminPassword.jsp").forward(request, response);
            return;
        }

        // Call DAO to update password
        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
        boolean isUpdated = accountDAO.changeAdminPassword(managerID, oldPassword, newPassword);

        if (isUpdated) {
            session.setAttribute("successMessage", "Password changed successfully!");
            response.sendRedirect(request.getContextPath() + "/admindashboard");
        } else {
            request.setAttribute("errorMessage", "Password change failed. Please try again!");
            request.getRequestDispatcher("/WEB-INF/views/admin/changeAdminPassword.jsp").forward(request, response);
        }
    }
}
