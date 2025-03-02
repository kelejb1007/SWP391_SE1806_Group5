package controller.user;

import DAO.ChapterDAO;
import DAO.CommentDAO;
import DAO.FavoriteDAO;
import DAO.NovelDAO;
import DAO.GenreDAO;
import DAO.ReadingHistoryDAO;
import DAO.ViewingDAO;
import model.Novel;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import model.Chapter;
import model.Comment;
import model.Favorite;
import model.ReadingHistory;
import model.UserAccount;

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

        String novelIdParam = request.getParameter("id");
        String sortParam = request.getParameter("sort");

        if (novelIdParam == null || novelIdParam.isEmpty()) {
            // If id not supplied send error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id parameter");
            return;
        }

        int novelId = Integer.parseInt(novelIdParam);

        // Retrieve the novel from database using novel id
        Novel novel = novelDAO.getNovelById(novelId);
        if (novel == null) {
            // If novel is not found, return error
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Novel not found");
            return;
        }

        // Set the novel object in request scope
        request.setAttribute("novel", novel);

        HttpSession session = request.getSession();

        UserAccount user = (UserAccount) session.getAttribute("user");
        // In NovelDetailController
        Favorite favorite = null;
        if (user != null) {
            favorite = favoriteDAO.getFavoriteByNovelIdAndUserId(novelId, user.getUserID());
            request.setAttribute("favorite", favorite);  // This is set in request scope
             ReadingHistory history = historyDAO.getReadingHistory(user.getUserID(), novelId);
            if (history == null) {
                // Tạo một đối tượng ReadingHistory mới nếu chưa có
                history = new ReadingHistory();
                history.setUserID(user.getUserID());
                history.setNovelID(novelId);
                history.setLastReadDate(LocalDateTime.now()); // Đặt thời điểm đọc
                boolean success = historyDAO.addReadingHistory(history);

                if (!success) {
                    // Xử lý lỗi nếu thêm thất bại (ví dụ: ghi log)
                    System.err.println("Failed to add reading history for user " + user.getFullName() + " and novel " + novelId);
                    // Bạn có thể muốn rethrow một exception để thông báo cho lớp gọi
                    // hoặc hiển thị một thông báo lỗi cho người dùng
                }
            } else {
                // Cập nhật lastReadDate nếu đã tồn tại
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


        List<Chapter> chapters = chapterDAO.getChaptersByNovelId(novelId, null); //Lấy chapter gốc trước khi sort

        // Tạo bản sao của danh sách chapters để sắp xếp
        List<Chapter> sortedChapters = new ArrayList<>(chapters);

        // Sắp xếp danh sách chapter nếu có parameter
        if (sortParam != null && !sortParam.isEmpty()) {
            sortedChapters = chapterDAO.getChaptersByNovelId(novelId, sortParam);
        }

        // Calculate time elapsed for each chapter, and set timeString
        for (Chapter chapter : sortedChapters) {
            String timeString = getTimeElapsed(chapter.getChapterCreatedDate());
            chapter.setTimeString(timeString);
        }
        // Calculate time elapsed for each chapter, and set timeString
        for (Chapter chapter : chapters) {
            String timeString = getTimeElapsed(chapter.getChapterCreatedDate());
            chapter.setTimeString(timeString);
        }

        request.setAttribute("chapters", chapters); // list chapter gốc
        request.setAttribute("sortedChapters", sortedChapters); // list chapter sau khi sort

        request.setAttribute("sort", sortParam);
        // Forward the request to novelDetail.jsp
        String target = "/WEB-INF/views/user/reading/novel-detail.jsp";
        // Gọi servlet /view trước khi forward đến trang chi tiết tiểu thuyết
        if (session.isNew()) {
            //  viewDAO.createViewing(novelId);
        }
        int views = viewDAO.getViewsCount(novelId);

        request.setAttribute("views", views);

        List<Comment> comments = commentDAO.getCommentsByNovelId(novelId);
        request.setAttribute("comments", comments);
        //   request.getRequestDispatcher("/getGenre?target=" + target + "&genre=" + novel.getGenreName() + (sortParam != null ? "&sort=" + sortParam : "")).forward(request, response);
        // request.getRequestDispatcher(target).forward(request, response);
        request.getRequestDispatcher("/getGenre?target=" + target + (sortParam != null ? "&sort=" + sortParam : "")).forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // KHÔNG LÀM GÌ CẢ. RatingController SẼ XỬ LÝ
        // ĐỪNG ĐỂ TRỐNG, HÃY ĐỂ NÓ Ở ĐÂY, MẶC DÙ KHÔNG LÀM GÌ.
    }

}
