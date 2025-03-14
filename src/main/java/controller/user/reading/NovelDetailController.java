package controller.user.reading;

import DAO.*;
import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "NovelDetailController", urlPatterns = {"/novel-detail"})
public class NovelDetailController extends HttpServlet {

    private NovelDAO novelDAO;
    private GenreDAO genreDAO;
    private ChapterDAO chapterDAO;
    private ViewingDAO viewDAO;
    private FavoriteDAO favoriteDAO;
    private ReadingHistoryDAO historyDAO;
    private CommentDAO commentDAO;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

    @Override
    public void init() throws ServletException {
        novelDAO = new NovelDAO();
        genreDAO = new GenreDAO();
        chapterDAO = new ChapterDAO();
        viewDAO = new ViewingDAO();
        favoriteDAO = new FavoriteDAO();
        historyDAO = new ReadingHistoryDAO();
        commentDAO = new CommentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Xác thực và lấy ID tiểu thuyết
            int novelId = getNovelIdFromRequest(request, response);
            if (novelId == -1) return; // Error already handled

           // 2. Lấy thông tin chi tiết về tiểu thuyết
            Novel novel = getNovelDetails(novelId, request, response);
            if (novel == null) return; // Error already handled

            // 3. Xử lý dữ liệu cụ thể của người dùng (yêu thích, lịch sử đọc)
            handleUserData(novelId, request);

           // 4. Lấy và sắp xếp các chương
            List<Chapter> chapters = getChapters(novelId, request);

            // 5. Lấy số lượt xem
            int views = viewDAO.getViewsCount(novelId);
            request.setAttribute("views", views);

           // 6. Lấy bình luận
            List<Comment> comments = commentDAO.getCommentsByNovelId(novelId);
            request.setAttribute("comments", comments);

            // 7. Forward
            forwardToView(novel, chapters, request, response);

        } catch (Exception e) {
            // Handle unexpected exceptions
            e.printStackTrace(); // Log the error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    private int getNovelIdFromRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String novelIdParam = request.getParameter("id");
        if (novelIdParam == null || novelIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id parameter");
            return -1;
        }
        return Integer.parseInt(novelIdParam);
    }

    private Novel getNovelDetails(int novelId, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Novel novel = novelDAO.getNovelById(novelId);
        if (novel == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Novel not found");
            return null;
        }
        request.setAttribute("novel", novel);
        return novel;
    }

    private void handleUserData(int novelId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        if (user != null) {
            // Handle favorites
            Favorite favorite = favoriteDAO.getFavoriteByNovelIdAndUserId(novelId, user.getUserID());
            request.setAttribute("favorite", favorite);

            // Handle reading history
            updateReadingHistory(user, novelId);
        }
    }

    private void updateReadingHistory(UserAccount user, int novelId) {
        ReadingHistory history = historyDAO.getReadingHistory(user.getUserID(), novelId);
        if (history == null) {
            history = new ReadingHistory();
            history.setUserID(user.getUserID());
            history.setNovelID(novelId);
            history.setLastReadDate(LocalDateTime.now());
            boolean success = historyDAO.addReadingHistory(history);
             if (!success) {
                // Xử lý lỗi nếu thêm thất bại (ví dụ: ghi log)
                System.err.println("Failed to add reading history for user " + user.getFullName() + " and novel " + novelId);
                // Bạn có thể muốn rethrow một exception để thông báo cho lớp gọi
                // hoặc hiển thị một thông báo lỗi cho người dùng
            }
        } else {
            history.setLastReadDate(LocalDateTime.now());
            boolean success = historyDAO.updateLastReadDate(history);
             if (!success) {
                // Xử lý lỗi nếu cập nhật thất bại (ví dụ: ghi log)
                System.err.println("Failed to update reading history for user " + user.getFullName() + " and novel " + novelId);
                // Bạn có thể muốn rethrow một exception để thông báo cho lớp gọi
                // hoặc hiển thị một thông báo lỗi cho người dùng
            }
        }
    }

    private List<Chapter> getChapters(int novelId, HttpServletRequest request) {
        String sortParam = request.getParameter("sort");
        List<Chapter> chapters = chapterDAO.getChaptersByNovelId(novelId, null); // Get original list

        List<Chapter> sortedChapters = new ArrayList<>(chapters);
        if (sortParam != null && !sortParam.isEmpty() && chapters.size() > 1) {
            sortedChapters = chapterDAO.getChaptersByNovelId(novelId, sortParam);
        }

        setTimeElapsedForChapters(chapters);
        setTimeElapsedForChapters(sortedChapters);

        request.setAttribute("chapters", chapters);
        request.setAttribute("sortedChapters", sortedChapters);
        request.setAttribute("sort", sortParam);
        return chapters; // Or sortedChapters if you want to return the sorted list
    }

    private void setTimeElapsedForChapters(List<Chapter> chapters) {
        for (Chapter chapter : chapters) {
            String timeString = getTimeElapsed(chapter.getChapterCreatedDate());
            chapter.setTimeString(timeString);
        }
    }

    private void forwardToView(Novel novel, List<Chapter> chapters, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String target = "/WEB-INF/views/user/reading/novel-detail.jsp";
        request.getRequestDispatcher("/getGenre?target=" + target + (request.getParameter("sort") != null ? "&sort=" + request.getParameter("sort") : "")).forward(request, response);
    }

    private String getTimeElapsed(LocalDateTime chapterCreatedDate) {
        if (chapterCreatedDate == null) {
            return "";
        }
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(chapterCreatedDate, now);
        long diffMillis = duration.toMillis();
        if (diffMillis / (1000 * 60 * 60 * 24 * 365) >= 1) {
            long yearsAgo = diffMillis / (1000 * 60 * 60 * 24 * 365);
            return yearsAgo + " years ago";
        } else if (diffMillis / (1000 * 60 * 60 * 24) >= 1) {
            long daysAgo = diffMillis / (1000 * 60 * 60 * 24);
            return daysAgo + " days ago";
        } else if (diffMillis / (1000 * 60 * 60) >= 1) {
            long hoursAgo = diffMillis / (1000 * 60 * 60);
            return hoursAgo + " hours ago";
        } else {
            long minutesAgo = diffMillis / (1000 * 60);
            return minutesAgo + " minutes ago";
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // KHÔNG LÀM GÌ CẢ. RatingController SẼ XỬ LÝ
        // ĐỪNG ĐỂ TRỐNG, HÃY ĐỂ NÓ Ở ĐÂY, MẶC DÙ KHÔNG LÀM GÌ.
    }
}