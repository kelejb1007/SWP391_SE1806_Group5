/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import DAO.LockChapterLogDAO;
import model.LockChapterLog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "ManageLockedChapterController", urlPatterns = {"/ViewLockedChapters"})
public class ManageLockedChapterController extends HttpServlet {

    private LockChapterLogDAO lockChapterLogDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Khởi tạo DAO
        lockChapterLogDAO = new LockChapterLogDAO();
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
        List<LockChapterLog> lockedChapters = null;

        try {
            int page = 1;
            int limit = 10; // Số bản ghi trên mỗi trang, có thể cấu hình trong config
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            if ("all".equals(action)) {
                // Lấy tất cả các chapter bị khóa với phân trang
                lockedChapters = lockChapterLogDAO.getAllLockedChapters(page, limit);
                request.setAttribute("lockedChapters", lockedChapters);
                request.setAttribute("type", "All");
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", calculateTotalPages(lockChapterLogDAO.getTotalLockedChapters(), limit));
            } else if ("byNovel".equals(action)) {
                int novelID = Integer.parseInt(request.getParameter("novelID"));
                lockedChapters = lockChapterLogDAO.getLockedChaptersByNovel(novelID, page, limit);
                request.setAttribute("lockedChapters", lockedChapters);
                request.setAttribute("type", "Novel");
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", calculateTotalPages(lockChapterLogDAO.getTotalLockedChaptersByNovel(novelID), limit));
            } else if ("byUser".equals(action)) {
                int userID = Integer.parseInt(request.getParameter("userID"));
                lockedChapters = lockChapterLogDAO.getLockedChaptersByUser(userID, page, limit);
                request.setAttribute("lockedChapters", lockedChapters);
                request.setAttribute("type", "User");
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", calculateTotalPages(lockChapterLogDAO.getTotalLockedChaptersByUser(userID), limit));
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
            }

            // Chuyển tiếp đến JSP để hiển thị
            request.getRequestDispatcher("/WEB-INF/views/staff/viewLockedChapter.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID or page format");
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
     * Tính tổng số trang dựa trên tổng số bản ghi và giới hạn mỗi trang
     *
     * @param totalRecords Tổng số bản ghi
     * @param limit Số bản ghi trên mỗi trang
     * @return Tổng số trang
     */
    private int calculateTotalPages(int totalRecords, int limit) {
        return (int) Math.ceil((double) totalRecords / limit);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller for staff to manage and view locked chapters";
    }
}
