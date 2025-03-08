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

@WebServlet(name = "EditProfileController", urlPatterns = {"/editProfile"})
public class EditProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/user/components/EditProfile.jsp").forward(request, response);
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

        // Lấy dữ liệu từ form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String numberPhone = request.getParameter("numberPhone");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String imageUML = request.getParameter("imageUML"); // URL avatar
        // Kiểm tra input rỗng
        if (fullName == null || email == null || numberPhone == null || dateOfBirth == null || gender == null
                || fullName.trim().isEmpty() || email.trim().isEmpty() || numberPhone.trim().isEmpty() || dateOfBirth.trim().isEmpty() || gender.trim().isEmpty()) {

            request.setAttribute("error", "All fields must be filled.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/EditProfile.jsp").forward(request, response);
            return;
        }

        // Chuyển đổi dateOfBirth từ String sang java.sql.Date và kiểm tra năm
        java.sql.Date sqlDateOfBirth = null;
        try {
            sqlDateOfBirth = java.sql.Date.valueOf(dateOfBirth);

            // Kiểm tra năm sinh phải nhỏ hơn 2025
            int birthYear = sqlDateOfBirth.toLocalDate().getYear();
            if (birthYear >= 2025) {
                request.setAttribute("error", "Year of birth must be before 2025.");
                request.getRequestDispatcher("/WEB-INF/views/user/components/EditProfile.jsp").forward(request, response);
                return;
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date format. Please use yyyy-MM-dd.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/EditProfile.jsp").forward(request, response);
            return;
        }

        // Cập nhật thông tin
        user.setFullName(fullName);
        user.setEmail(email);
        user.setNumberPhone(numberPhone);
        user.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth));
        user.setGender(gender);
        user.setImageUML(imageUML);

        // Lưu thay đổi vào database
        UserAccountDAO dao = new UserAccountDAO();
        dao.updateUser(user);

        // Cập nhật session
        session.setAttribute("user", user);

        // Quay lại trang profile
        response.sendRedirect(request.getContextPath() + "/viewprofile");
    }
}
