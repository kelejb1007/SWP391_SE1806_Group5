/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.profile;

import DAO.NotificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import model.Notification;
import model.UserAccount;
import java.util.logging.Logger;
/**
 *
 * @author Admin
 */
@WebServlet(name = "NotificationController", urlPatterns = {"/NotificationController"})
public class NotificationController extends HttpServlet {
private static final Logger LOGGER = Logger.getLogger(NotificationController.class.getName());
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NotificationController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NotificationController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");


        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        try {
            NotificationDAO dao = new NotificationDAO();
            List<Notification> notifications = dao.getNotificationsByUserId(user.getUserID());

            if (notifications == null || notifications.isEmpty()) {
                LOGGER.info("No notifications found for user ID: " + user.getUserID());
            } else {
                LOGGER.info("Retrieved " + notifications.size() + " notifications for user ID: " + user.getUserID());
            }

            request.setAttribute("notifications", notifications);
            request.getRequestDispatcher("/WEB-INF/views/user/components/notification.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving notifications for user ID: " + user.getUserID(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving notifications");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        // Kiểm tra giá trị action và notificationId
        int notificationId = Integer.parseInt(request.getParameter("notificationId"));
        System.out.println("Action: " + action);
        System.out.println("Notification ID: " + notificationId);

        try {
            NotificationDAO notificationDAO = new NotificationDAO();

            switch (action) {
                case "delete":

                    notificationDAO.deleteNotification(notificationId);
                    LOGGER.info("Notification ID " + notificationId + " deleted.");
                    break;

                default:
                    LOGGER.warning("Unknown action: " + action);
                    break;
            }

 
            response.sendRedirect("NotificationController");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing action: " + action, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing action");
        }
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
