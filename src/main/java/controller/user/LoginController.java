package controller.user;

import DAO.UserAccountDAO;
import com.google.api.services.oauth2.Oauth2.Userinfo;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserAccount;
import utils.GoogleUtils;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "LoginController", urlPatterns = {"/Login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "google":
                loginByGG(request, response);
                break;
            case "googlereceive":
                sendDirect(request, response);
                break;
            default:
                request.getRequestDispatcher("/WEB-INF/views/common/UserLogin.jsp").forward(request, response);
        }

    }

    public void loginByGG(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String googleLoginUrl = GoogleUtils.getGoogleLoginUrl();
        response.sendRedirect(googleLoginUrl);
    }

    private void sendDirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserAccountDAO uaDAO = new UserAccountDAO();
        String code = request.getParameter("code");
        if (code != null) {
            // Lấy URL trước khi đăng nhập
            HttpSession session = request.getSession();
            String redirectURL = (String) session.getAttribute("redirectURL");
            session.removeAttribute("redirectURL");

            com.google.api.services.oauth2.model.Userinfo userInfo = GoogleUtils.getUserInfo(code);
            String email = userInfo.getEmail();
            String userName = userInfo.getName();

            if (email == null) {
                request.getRequestDispatcher("/WEB-INF/views/common/UserLogin.jsp").forward(request, response);
            }

            UserAccount ua = uaDAO.getUserByEmail(email);
            if (ua != null) {
                session.setAttribute("user", ua);
                if (redirectURL != null && !redirectURL.isEmpty()) {
                    response.sendRedirect(redirectURL);
                } else {
                    response.sendRedirect(request.getContextPath() + "/homepage");
                }
            } else {
                if (userInfo.getVerifiedEmail()) {
                    uaDAO.createUserFromGoogle(email, userName, userInfo.getPicture());
                    session.setAttribute("user", uaDAO.getUserByEmail(email));
                    if (redirectURL != null && !redirectURL.isEmpty()) {
                        response.sendRedirect(redirectURL);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/homepage?");
                    }
                } else {
                    response.sendRedirect("Login?error=Email has not been verified");
                }

            }
        } else {
            response.sendRedirect("Login?error=There are some error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }
        switch (action) {
            default:
                handleNormalLogin(request, response);
        }
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
                response.sendRedirect(request.getContextPath() + "/homepage");
            }
        }
    }

}
