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

@WebServlet(name = "ManagerLoginController", urlPatterns = {"/ManagerLogin"})
public class ManagerLoginController extends HttpServlet {

    private final ManagerAccountDAO userDAO = new ManagerAccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String previousURL = request.getHeader("Referer"); // Lấy URL trước đó
        if (previousURL != null && !previousURL.contains("ManagerLogin")) {
            request.getSession().setAttribute("prevPage", previousURL);
        }
        request.getRequestDispatcher("/WEB-INF/views/common/ManagerLogin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleNormalLogin(request, response);
    }

    private void handleNormalLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ManagerAccount user = userDAO.authenticateUser(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            // Lấy trang trước khi đăng nhập
            String prevPage = (String) session.getAttribute("prevPage");
            if (prevPage != null) {
                session.removeAttribute("prevPage");
                response.sendRedirect(prevPage);
            } else {
                response.sendRedirect("homepage.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/views/common/ManagerLogin.jsp").forward(request, response);
        }
    }
}
