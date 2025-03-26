/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import DAO.NovelDAO;
import DAO.UserAccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.Novel;
import model.UserAccount;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ViewNovelStatisticsController", urlPatterns = {"/viewnovelstatisticscontroller"})
public class ViewNovelStatisticsController extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
            out.println("<title>Servlet ViewNovelStatisticsController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewNovelStatisticsController at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        if (action == null || action.trim().equals("")) {
            action = "default";
        }

        switch (action) {
            case "viewNovelStatistics":
                viewNovelStatistics(request, response);
                break;
            case "viewUserStatistics":
                viewUserStatistics(request, response);
                break;
            default:
                viewNovelStatistics(request, response);
        }
    }

    private void viewNovelStatistics(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO dao = new NovelDAO();
        try {

            List<Novel> novelStatistics = dao.getAllNovelStatistics();
            request.setAttribute("statistics", novelStatistics);
            request.getRequestDispatcher("/WEB-INF/views/admin/viewNovelStatistics.jsp").forward(request, response);
        } catch (SQLException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading novel statistics");
        }
    }

    private void viewUserStatistics(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserAccountDAO dao = new UserAccountDAO();
        try {
            // Lấy danh sách thống kê người dùng từ DAO
            List<UserAccount> userStatistics = dao.getUserStatistics();

            // Đưa dữ liệu vào request để gửi tới JSP
            request.setAttribute("userStatistics", userStatistics);

            // Chuyển hướng tới trang JSP để hiển thị dữ liệu
            request.getRequestDispatcher("/WEB-INF/views/admin/viewUserStatistics.jsp").forward(request, response);
        } catch (SQLException ex) {
            // Gửi lỗi nếu có ngoại lệ xảy ra
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading user statistics");
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
        processRequest(request, response);
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
