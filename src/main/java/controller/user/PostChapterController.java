package controller.user;

import DAO.PostChapterDAO;
import model.Chapter;
import model.Novel;
import DAO.NovelDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "PostChapterController", urlPatterns = {"/postChapter"})
public class PostChapterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy novelId từ request hoặc session
        String novelIdParam = request.getParameter("novelId");
        Integer novelId = (Integer) session.getAttribute("novelId");

        if (novelIdParam != null && !novelIdParam.isEmpty()) {
            try {
                novelId = Integer.parseInt(novelIdParam);
                session.setAttribute("novelId", novelId); // Lưu vào session
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid Novel ID format.");
                request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
                return;
            }
        }

        // Kiểm tra nếu novelId vẫn bị thiếu
        if (novelId == null) {
            response.sendRedirect("novelList"); // Chuyển hướng về danh sách truyện
            return;
        }

        // Kiểm tra novelId có tồn tại không
        NovelDAO novelDAO = new NovelDAO();
        Novel novel = novelDAO.getNovelById(novelId);

        if (novel == null) {
            request.setAttribute("errorMessage", "Novel not found.");
            request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
            return;
        }

        // Truyền dữ liệu đến JSP
        request.setAttribute("novelId", novelId);
        request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PostChapterDAO postChapterDAO = new PostChapterDAO();

        // Lấy dữ liệu từ request
        String novelIdParam = request.getParameter("novelId");
        String chapterNumberParam = request.getParameter("chapterNumber");
        String chapterTitle = request.getParameter("chapterName");
        String fileURL = request.getParameter("fileURL");

        // Kiểm tra dữ liệu đầu vào không được null hoặc rỗng
        if (novelIdParam == null || novelIdParam.trim().isEmpty()
                || chapterNumberParam == null || chapterNumberParam.trim().isEmpty()
                || chapterTitle == null || chapterTitle.trim().isEmpty()
                || fileURL == null || fileURL.trim().isEmpty()) {
            request.setAttribute("message", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
            return;
        }

        try {
            int novelId = Integer.parseInt(novelIdParam);
            int chapterNumber = Integer.parseInt(chapterNumberParam);

            // Kiểm tra độ dài tiêu đề chapter
            if (chapterTitle.length() < 3 || chapterTitle.length() > 100) {
                request.setAttribute("message", "Chapter title must be between 3 and 100 characters.");
                request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
                return;
            }

            // Kiểm tra định dạng URL
            if (!fileURL.matches("^(http://|https://).*")) {
                request.setAttribute("message", "Invalid URL format.");
                request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
                return;
            }

            // Tạo đối tượng Chapter
            Chapter newChapter = new Chapter(novelId, chapterNumber, chapterTitle, fileURL, LocalDateTime.now(), "pending");

            // Gọi DAO để thêm chapter
            int chapterID = postChapterDAO.postChapter(newChapter);

            if (chapterID > 0) {
                request.setAttribute("message", "Chapter posted successfully!");
            } else {
                request.setAttribute("message", "Failed to post chapter.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid number format for Novel ID or Chapter Number.");
        }

        request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
    }

}
