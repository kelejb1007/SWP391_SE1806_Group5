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
@WebServlet(name = "ViewProfileController", urlPatterns = {"/viewprofile"})
public class ViewProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        // Kiểm tra session, nếu không có user -> chuyển hướng đến trang login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        // Nếu có session, chuyển đến ViewProfile.jsp
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/user/components/ViewProfile.jsp").forward(request, response);
    }
}


