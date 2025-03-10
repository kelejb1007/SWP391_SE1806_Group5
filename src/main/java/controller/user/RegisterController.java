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
import model.UserAccount;

/**
 * @author KHOA
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/Register"})
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/components/Register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleUserRegistration(request, response);
    }

    private void handleUserRegistration(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String numberPhone = request.getParameter("numberPhone");
        String gender = request.getParameter("gender");

        // Kiểm tra input rỗng
        if (username == null || password == null || confirmPassword == null || fullName == null || email == null
                || username.trim().isEmpty() || password.trim().isEmpty() || confirmPassword.trim().isEmpty()
                || fullName.trim().isEmpty() || email.trim().isEmpty() || numberPhone.trim().isEmpty()) {

            request.setAttribute("error", "All fields must be filled.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra username phải có ít nhất 3 ký tự
        if (username.length() < 3) {
            request.setAttribute("error", "Username must be at least 3 characters long.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu nhập lại có giống nhau không
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password must be the same.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra email có đúng định dạng @gmail.com không
        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            request.setAttribute("error", "Email must be a valid Gmail address (e.g., example@gmail.com).");
            request.getRequestDispatcher("/WEB-INF/views/user/components/Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra xem số điện thoại có phải số không
        if (!numberPhone.matches("\\d+")) {
            request.setAttribute("error", "Phone numbers must be entered numerically.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/Register.jsp").forward(request, response);
            return;
        }

        UserAccountDAO userDao = new UserAccountDAO();

        // Kiểm tra xem username hoặc email đã tồn tại chưa
        if (userDao.isUserExist(username, email)) {
            request.setAttribute("error", "Username or Email already exists.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/Register.jsp").forward(request, response);
            return;
        }

        // Tạo user mới
        UserAccount newUser = new UserAccount();
        newUser.setUserName(username);
        newUser.setPassword(password);
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setNumberPhone(numberPhone);
        newUser.setGender(gender);
        newUser.setStatus(0); // Mặc định không bị khóa

        boolean isRegistered = userDao.registerUser(newUser);
        if (isRegistered) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            request.setAttribute("error", "Registration failed. Try again.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/Register.jsp").forward(request, response);
        }
    }
}
