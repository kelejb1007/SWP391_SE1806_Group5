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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleNormalLogin(request, response);
    }

    private void handleNormalLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Lấy URL trước khi đăng nhập
        HttpSession session = request.getSession();
        String redirectURL = (String) session.getAttribute("redirectURL");

        // Kiểm tra input rỗng
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and Password cannot be empty.");
            request.getRequestDispatcher("/WEB-INF/views/common/UserLogin.jsp").forward(request, response);
            return;
        }

        // Xác thực người dùng
        UserAccountDAO userDao = new UserAccountDAO();
        UserAccount user = userDao.authenticateUser(username, password);

        if (user == null) {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/views/common/UserLogin.jsp").forward(request, response);
        } else {
            session.setAttribute("user", user);
            session.removeAttribute("redirectURL"); // Xóa session redirect sau khi sử dụng

            // Chuyển hướng về trang cũ nếu có, ngược lại về Home
            if (redirectURL != null && !redirectURL.isEmpty()) {
                response.sendRedirect(redirectURL);
            } else {
                response.sendRedirect(request.getContextPath() + "/getGenre?target=/WEB-INF/views/user/Home.jsp");
            }
        }
    }
}
