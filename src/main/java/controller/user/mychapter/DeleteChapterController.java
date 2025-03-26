package controller.user.mychapter;

import DAO.PostChapterDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "DeleteChapterController", urlPatterns = {"/deleteChapter"})
public class DeleteChapterController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteChapterController.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteChapterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteChapterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Processing GET request for deleteChapter with novelId: {0}, chapterId: {1}",
                new Object[]{request.getParameter("novelId"), request.getParameter("chapterId")});

        response.sendRedirect("error.jsp"); // Không cho phép truy cập trực tiếp qua GET
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Processing POST request for deleteChapter with novelId: {0}, chapterId: {1}",
                new Object[]{request.getParameter("novelId"), request.getParameter("chapterId")});

        PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
        String novelIdParam = request.getParameter("novelId");
        String chapterIdParam = request.getParameter("chapterId");

        if (novelIdParam == null || novelIdParam.isEmpty() || chapterIdParam == null || chapterIdParam.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Missing novelId or chapterId in request");
            response.sendRedirect("error.jsp");
            return;
        }

        try {
            int novelId = Integer.parseInt(novelIdParam);
            int chapterId = Integer.parseInt(chapterIdParam);

            boolean updated = postChapterDAO.deleteChapter(novelId, chapterId);

            if (updated) {
                LOGGER.log(Level.INFO, "Successfully deleted chapter for novelId: {0}, chapterId: {1}",
                        new Object[]{novelId, chapterId});
                // Chuyển hướng về trang chi tiết tiểu thuyết
                response.sendRedirect(request.getContextPath() + "/mynovel?action=viewdetail&novelID=" + novelId);
            } else {
                LOGGER.log(Level.SEVERE, "Failed to delete chapter for novelId: {0}, chapterId: {1}",
                        new Object[]{novelId, chapterId});
                response.sendRedirect("error.jsp");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid novelId or chapterId format: {0}", e.getMessage());
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for deleting chapters";
    }
}