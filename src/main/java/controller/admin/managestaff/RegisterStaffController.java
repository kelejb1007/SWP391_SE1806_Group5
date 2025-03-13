package controller.admin.managestaff;

import DAO.ManagerAccountDAO;
import model.ManagerAccount;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RegisterStaffController", urlPatterns = {"/RegisterStaff"})
public class RegisterStaffController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleStaffRegistration(request, response);
    }

    private void handleStaffRegistration(HttpServletRequest request, HttpServletResponse response)
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
            request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
            return;
        }

        // Kiểm tra username phải có ít nhất 3 ký tự
        if (username.length() < 3) {
            request.setAttribute("error", "Username must be at least 3 characters long.");
            request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu nhập lại có giống nhau không
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password must be the same.");
            request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
            return;
        }

        // Kiểm tra email có đúng định dạng @gmail.com không
        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            request.setAttribute("error", "Email must be a valid Gmail address (e.g., example@gmail.com).");
            request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
            return;
        }

        // Kiểm tra xem số điện thoại có phải số không
        if (!numberPhone.matches("\\d+")) {
            request.setAttribute("error", "Phone numbers must be entered numerically.");
            request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
            return;
        }
        // Kiểm tra mật khẩu: ít nhất 8 ký tự, 1 chữ số, 1 chữ hoa, 1 chữ thường
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
            request.setAttribute("error", "Password must be at least 8 characters long, with at least one digit, one lowercase, and one uppercase letter.");
            request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
            return;
        }

        ManagerAccountDAO managerAccountDAO = new ManagerAccountDAO();

        // Kiểm tra xem username hoặc email đã tồn tại chưa
        if (managerAccountDAO.isUserExist(username, email)) {
            request.setAttribute("error", "Username or Email already exists.");
            request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
            return;
        }

        // Tạo tài khoản staff mới
        ManagerAccount newAccount = new ManagerAccount();
        newAccount.setUsername(username);
        newAccount.setPassword(password);
        newAccount.setFullName(fullName);
        newAccount.setEmail(email);
        newAccount.setNumberPhone(numberPhone);
        newAccount.setGender(gender);
        newAccount.setRole("Staff"); // Vai trò là Staff
        newAccount.setCanLock(false); // Mặc định không có quyền khóa tài khoản
        newAccount.setCanApprove(false); // Mặc định không có quyền phê duyệt

        boolean isRegistered = managerAccountDAO.registerUser(newAccount);
        if (isRegistered) {
            // Nếu đăng ký thành công, thông báo thành công và quay về trang dashboard
            request.setAttribute("Complete", "Account created successfully.");
            // Chuyển hướng đến trang quản trị
            response.sendRedirect(request.getContextPath() + "/admindashboard#");
        } else {
            // Nếu đăng ký thất bại, thông báo lỗi và chuyển lại trang đăng ký
            request.setAttribute("error", "Registration failed. Try again.");
            request.getRequestDispatcher("/WEB-INF/views/admin/RegisterStaff.jsp").forward(request, response);
        }
    }
}
