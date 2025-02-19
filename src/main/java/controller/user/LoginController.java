/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.UserAccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserAccount;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "LoginController", urlPatterns = {"/Login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/common/UserLogin.jsp").forward(request, response);
    }

    private final UserAccountDAO userDAO = new UserAccountDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("normal".equals(action)) {
            handleNormalLogin(request, response);
        } else if ("google".equals(action)) {
            handleGoogleLogin(request, response);
        }
    }

    private void handleNormalLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserAccount user = userDAO.authenticateUser(username, password);
        if (user != null) {
            if (user.isIsBanned()) {
                request.setAttribute("error", "Your account has been banned.");
                request.getRequestDispatcher("/WEB-INF/views/common/UserLogin.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
           response.sendRedirect("homepage");
          
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/views/common/UserLogin.jsp").forward(request, response);
        }
    }

    private void handleGoogleLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
//        String code = request.getParameter("code");
//        String accessToken = GoogleUtils.getToken(code);
//        UserAccount googleUser = GoogleUtils.getUserInfo(accessToken);
//
//        UserAccount existingUser = userDAO.getUserByEmail(googleUser.getEmail());
//        if (existingUser == null) {
//            userDAO.createUserFromGoogle(googleUser.getEmail(), googleUser.getFullName());
//            existingUser = userDAO.getUserByEmail(googleUser.getEmail());
//        }
//
//        HttpSession session = request.getSession();
//        session.setAttribute("user", existingUser);
//
//        response.sendRedirect("homepage.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
