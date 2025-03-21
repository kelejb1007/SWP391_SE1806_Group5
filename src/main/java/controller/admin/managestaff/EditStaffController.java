package controller.admin.managestaff;

import DAO.ManagerAccountDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ManagerAccount;

/**
 * Servlet xử lý cập nhật thông tin nhân viên
 * 
 * @author KHOA
 */
@WebServlet(name = "EditStaffController", urlPatterns = {"/EditStaff"})
public class EditStaffController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String managerIDParam = request.getParameter("managerID");

        if (managerIDParam == null || managerIDParam.trim().isEmpty()) {
            response.sendRedirect("managestaff");
            return;
        }

        int managerID;
        try {
            managerID = Integer.parseInt(managerIDParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("managestaff");
            return;
        }

        ManagerAccountDAO accountDAO = new ManagerAccountDAO();
        try {
            ManagerAccount staff = accountDAO.getAccountById(managerID);
            if (staff == null) {
                response.sendRedirect("managestaff");
                return;
            }
            request.setAttribute("staff", staff);

            // Kiểm tra có thông báo thành công từ session không
            HttpSession session = request.getSession();
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) {
                request.setAttribute("successMessage", successMessage);
                session.removeAttribute("successMessage");
            }

            request.getRequestDispatcher("/WEB-INF/views/common/EditStaff.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EditStaffController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving staff details.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int managerID = Integer.parseInt(request.getParameter("managerID"));
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String numberPhone = request.getParameter("numberPhone");
        String gender = request.getParameter("gender");

        ManagerAccountDAO accountDAO = new ManagerAccountDAO();

        try {
            // Lấy thông tin nhân viên hiện tại từ database
            ManagerAccount staff = accountDAO.getAccountById(managerID);
            if (staff == null) {
                response.sendRedirect("managestaff");
                return;
            }

            // Kiểm tra username hoặc email có bị trùng không (trừ tài khoản của nhân viên hiện tại)
            if (accountDAO.isUserExist(username, email, managerID)) {
                request.setAttribute("error", "Username or Email already exists. Please use a different one.");
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("/WEB-INF/views/common/EditStaff.jsp").forward(request, response);
                return;
            }

            // Kiểm tra email có đúng định dạng hay không
            if (!isValidEmail(email)) {
                request.setAttribute("error", "Invalid email format. Please enter a valid email address.");
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("/WEB-INF/views/common/EditStaff.jsp").forward(request, response);
                return;
            }

            // Kiểm tra số điện thoại có chứa chữ cái hay không
            if (containsLetter(numberPhone)) {
                request.setAttribute("error", "Invalid phone number. It must not contain letters.");
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("/WEB-INF/views/common/EditStaff.jsp").forward(request, response);
                return;
            }

            // Cập nhật thông tin nhân viên
            accountDAO.updateAccount(managerID, username, fullName, email, numberPhone, gender);

            // Lưu thông báo thành công vào session
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Staff profile updated successfully.");

            // Chuyển hướng để hiển thị thông báo thành công
            response.sendRedirect("EditStaff?managerID=" + managerID);
        } catch (SQLException ex) {
            Logger.getLogger(EditStaffController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating staff details.");
        }
    }

    // Kiểm tra định dạng email bằng regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Kiểm tra số điện thoại có chứa chữ cái không
    private boolean containsLetter(String numberPhone) {
        return Pattern.compile("[a-zA-Z]").matcher(numberPhone).find();
    }
}
