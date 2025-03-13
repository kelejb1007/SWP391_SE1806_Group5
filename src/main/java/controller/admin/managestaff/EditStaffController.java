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

            // Kiểm tra email đã tồn tại chưa (trừ email của nhân viên hiện tại)
            if (accountDAO.isEmailExists(email, managerID)) {
                request.setAttribute("error", "Email already exists. Please use a different email.");
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
            response.sendRedirect("managestaff");
        } catch (SQLException ex) {
            Logger.getLogger(EditStaffController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating staff details.");
        }
    }

    // Hàm kiểm tra số điện thoại có chứa chữ cái hay không
    private boolean containsLetter(String numberPhone) {
        return Pattern.compile("[a-zA-Z]").matcher(numberPhone).find();
    }
}
