/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import DAO.ApprovingActivityDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ApprovingActivity;

/**
 *
 * @author ASUS
 */
@WebServlet(name="ApprovingActivityController", urlPatterns={"/approvingactivity"})
public class ApprovingActivityController extends HttpServlet {
    private final ApprovingActivityDAO approvingActivityDAO = new ApprovingActivityDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("ManagerLogin");
            return;
        }
        List<ApprovingActivity> activities = approvingActivityDAO.getAllApprovingActivities();
        request.setAttribute("activities", activities);
        request.getRequestDispatcher("/WEB-INF/views/admin/approvingActivityList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Placeholder for handling POST requests if needed in the future
    }

    @Override
    public String getServletInfo() {
        return "Approving Activity Management Controller";
    }
}
