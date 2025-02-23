package controller.staff;

import DAO.ManagerAccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ManagerAccount;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "ManagerLoginController", urlPatterns = {"/ManagerLogin"})
public class ManagerLoginController extends HttpServlet {

    private final ManagerAccountDAO userDAO = new ManagerAccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/common/ManagerLogin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ManagerAccount user = userDAO.authenticateUser(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("manager", user); // Giữ session cho admin

            response.sendRedirect(request.getContextPath() + "/getGenre?target=/WEB-INF/views/staff/dashboard.jsp");
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/views/common/ManagerLogin.jsp").forward(request, response);
        }
    }
}
