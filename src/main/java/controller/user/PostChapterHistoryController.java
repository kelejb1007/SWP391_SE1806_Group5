/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.PostChapterHistoryDAO;
import model.Chapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "PostChapterHistoryController", urlPatterns = {"/PostChapterHistory"})
public class PostChapterHistoryController extends HttpServlet {

    private PostChapterHistoryDAO postChapterHistoryDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Khởi tạo DAO
        postChapterHistoryDAO = new PostChapterHistoryDAO();
    }

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
        List<Chapter> historyList = null;

        try {
            if ("byNovel".equals(action)) {
                int novelID = Integer.parseInt(request.getParameter("novelID"));
                historyList = postChapterHistoryDAO.getChapterPostingHistoryByNovel(novelID);
                request.setAttribute("historyList", historyList);
                request.setAttribute("type", "Novel");
            } else if ("byUser".equals(action)) {
                int userID = Integer.parseInt(request.getParameter("userID"));
                historyList = postChapterHistoryDAO.getChapterPostingHistoryByUser(userID);
                request.setAttribute("historyList", historyList);
                request.setAttribute("type", "User");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
            }

            // Chuyển tiếp đến JSP để hiển thị
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/PostChapterHistory.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
            e.printStackTrace();
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
        doGet(request, response); // Gọi lại phương thức GET để xử lý, vì chức năng này chỉ cần GET
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller to handle chapter posting history requests";
    }
}
