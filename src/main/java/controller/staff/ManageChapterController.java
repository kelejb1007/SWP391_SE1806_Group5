package controller.staff;

import DAO.ManageChapterDAO;
import model.ManageChapter;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ManageChapterController", urlPatterns = {"/ViewAllChapters"})
public class ManageChapterController extends HttpServlet {

    private final ManageChapterDAO chapterDAO = new ManageChapterDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy novelId từ request, nếu không có thì kiểm tra session
            HttpSession session = request.getSession();
            String novelIdParam = request.getParameter("novelId");
            Integer novelId = (novelIdParam != null && !novelIdParam.isEmpty()) ? Integer.parseInt(novelIdParam) 
                            : (Integer) session.getAttribute("novelId");

            if (novelId == null) {
                request.setAttribute("errorMessage", "Novel ID is missing.");
                request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
                return;
            }

            // Lưu lại novelId vào session để không bị mất khi submit form
            session.setAttribute("novelId", novelId);

            String sortOrder = request.getParameter("sortOrder");
            boolean isDescending = "desc".equalsIgnoreCase(sortOrder);

            // Lấy danh sách chương
            List<ManageChapter> chapterList = chapterDAO.getChaptersByNovelId(novelId, isDescending);

            // Gán dữ liệu vào request
            request.setAttribute("chapterList", chapterList);
            request.setAttribute("novelId", novelId);
            request.setAttribute("sortOrder", (sortOrder == null) ? "asc" : sortOrder);

            // Chuyển hướng tới ViewAllChapters.jsp
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid novel ID.");
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String chapterIdStr = request.getParameter("chapterId");
            String lockAction = request.getParameter("lockAction");
            String lockReason = request.getParameter("lockReason");

            // Kiểm tra chapterId
            if (chapterIdStr == null || chapterIdStr.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Chapter ID is missing or invalid.");
                request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
                return;
            }
            int chapterId = Integer.parseInt(chapterIdStr);

            // Lấy novelId từ request, nếu không có thì lấy từ session
            HttpSession session = request.getSession();
            String novelIdStr = request.getParameter("novelId");
            Integer novelId = (novelIdStr != null && !novelIdStr.isEmpty()) ? Integer.parseInt(novelIdStr) 
                            : (Integer) session.getAttribute("novelId");

            if (novelId == null) {
                request.setAttribute("errorMessage", "Novel ID is missing.");
                request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
                return;
            }

            // Lưu lại novelId vào session
            session.setAttribute("novelId", novelId);

            // Lấy managerID từ session
            Integer managerID = (Integer) session.getAttribute("managerID");
            if (managerID == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Kiểm tra lockAction
            boolean isLocked = "lock".equalsIgnoreCase(lockAction);

            // Cập nhật trạng thái khóa/mở khóa
            chapterDAO.updateChapterLock(chapterId, managerID, isLocked, lockReason);

            // Redirect về trang ViewAllChapters và giữ lại novelId
            response.sendRedirect("ViewAllChapters?novelId=" + novelId);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid chapter ID.");
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while processing the request.");
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
        }
    }
}
