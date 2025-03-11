package controller.admin;

import DAO.ManagerAccountDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ManagerAccount;
/**
 * @author KHOA
 */
@WebServlet(name = "ViewStaffInformationController", urlPatterns = {"/viewStaff"})
public class ViewStaffInformationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String managerIDParam = request.getParameter("managerID");
        if (managerIDParam == null || managerIDParam.trim().isEmpty()) {
            response.sendRedirect("/managestaff");
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
            request.getRequestDispatcher("/WEB-INF/views/common/DetailStaff.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewStaffInformationController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving staff details");
        }
    }
}
