/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.UserAccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserAccount;
/**
 * @author KHOA
 */
@WebServlet(name = "ChangePasswordController", urlPatterns = {"/changePassword"})
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/components/ChangePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra rỗng
        if (oldPassword == null || newPassword == null || confirmPassword == null
                || oldPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "All fields must be filled.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/ChangePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mới có giống mật khẩu cũ không
        if (oldPassword.equals(newPassword)) {
            request.setAttribute("error", "New password must be different from the old password.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/ChangePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mới có đúng yêu cầu không
        if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            request.setAttribute("error", "Password must be at least 8 characters, contain 1 uppercase, 1 lowercase, and 1 digit.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/ChangePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu nhập lại
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Password confirmation does not match.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/ChangePassword.jsp").forward(request, response);
            return;
        }

        // Gọi DAO để cập nhật mật khẩu
        UserAccountDAO dao = new UserAccountDAO();
        boolean isUpdated = dao.updatePassword(user.getUserID(), oldPassword, newPassword);

        if (isUpdated) {
            request.setAttribute("message", "Password changed successfully.");
        } else {
            request.setAttribute("error", "Old password is incorrect.");
        }

        request.getRequestDispatcher("/WEB-INF/views/user/components/ChangePassword.jsp").forward(request, response);
    }
}
