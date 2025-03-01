package controller.user;

import DAO.ChapterDAO;
import DAO.NovelDAO;
import DAO.ReadingHistoryDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import model.Chapter;
import model.Novel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import model.ReadingHistory;
import model.UserAccount;

@WebServlet(name = "ChapterController", urlPatterns = {"/chapter"})
public class ChapterController extends HttpServlet {

    private ChapterDAO chapterDAO;
    private NovelDAO novelDAO;
      private ReadingHistoryDAO historyDAO;

    @Override
    public void init() throws ServletException {
        chapterDAO = new ChapterDAO();
        novelDAO = new NovelDAO();
        historyDAO = new ReadingHistoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        boolean isLoggedIn = (user != null);

        String chapterIdParam = request.getParameter("id");
        String sortParam = request.getParameter("sort");

        if (chapterIdParam == null || chapterIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id parameter");
            return;
        }

        int chapterId = Integer.parseInt(chapterIdParam);
        Chapter chapter = chapterDAO.getChapterById(chapterId);
        if (chapter == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Chapter not found");
            return;
        }

        Novel novel = novelDAO.getNovelById(chapter.getNovelID());
        if (novel == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Novel not found");
            return;
        }
          // Lưu lịch sử đọc
        if (user != null) {
            ReadingHistory history = historyDAO.getReadingHistory(user.getUserID(), novel.getNovelID(), chapter.getChapterID());

            if (history == null) {
                history = new ReadingHistory();
                history.setUserID(user.getUserID());
                history.setNovelID(novel.getNovelID());
                history.setChapterID(chapter.getChapterID());  // Set ChapterID
                history.setLastReadDate(LocalDateTime.now()); //set thoi gian
                 boolean added = historyDAO.addReadingHistory(history);
                   if (!added) {
                       System.err.println("Failed to add reading history for user " + user.getFullName() + ", novel " + novel.getNovelID() + ", chapter " + chapterId);
                   }
            } else {
              history.setLastReadDate(LocalDateTime.now()); // Cập nhật thời gian đọc
              boolean updated = historyDAO.updateLastReadDate(history);
                 if (!updated) {
                       System.err.println("Failed to update reading history for user " + user.getFullName() + ", novel " + novel.getNovelID() + ", chapter " + chapterId);
                   }
            }
        }
        List<Chapter> chapters = chapterDAO.getChaptersByNovelId(chapter.getNovelID(), sortParam);
        if (!chapters.isEmpty()) {
            String latestReleaseTime = getTimeElapsed(chapters.get(0).getChapterCreatedDate());
            request.setAttribute("latestReleaseChapter", chapters.get(0));
            request.setAttribute("latestReleaseTime", latestReleaseTime);
        }

        Chapter previousChapter = null;
        Chapter nextChapter = null;

        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).getChapterID() == chapterId) {
                if (i > 0) {
                    previousChapter = chapters.get(i - 1);
                }
                if (i < chapters.size() - 1) {
                    nextChapter = chapters.get(i + 1);
                }
                break;
            }
        }

        request.setAttribute("previousChapter", previousChapter);
        request.setAttribute("nextChapter", nextChapter);

        // Sử dụng isLoggedIn để xác định có thể xem nội dung hay không
        boolean canViewContent = isLoggedIn || isWithinFreeLimit(chapter.getChapterNumber());
        String chapterContent = canViewContent ? getChapterContent(chapter, request) : "";

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        request.setAttribute("chapter", chapter);
        request.setAttribute("chapters", chapters);
        request.setAttribute("novel", novel);
        request.setAttribute("canViewContent", canViewContent);
        request.setAttribute("sort", sortParam);
        request.setAttribute("chapterContent", chapterContent);
        request.getRequestDispatcher("/WEB-INF/views/user/reading/chapter/chapter-content.jsp").forward(request, response);
    }

    public boolean isWithinFreeLimit(int chapterNumber) {
        return chapterNumber <= 3;
    }

    private String getChapterContent(Chapter chapter, HttpServletRequest request) {
        String fileURL = chapter.getFileURL();
        if (fileURL == null || fileURL.isEmpty()) {
            return "Chapter content not available.";
        }

        ServletContext context = request.getServletContext();
        String realPath = context.getRealPath("/");
        File file = new File(realPath + fileURL);

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error in getChapterContent: " + e.getMessage());
            return "Chapter content not available.";
        }
        return content.toString();
    }

    private String getTimeElapsed(LocalDateTime chapterCreatedDate) {
        if (chapterCreatedDate == null) {
            return "";
        }
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(chapterCreatedDate, now);
        long minutesAgo = duration.toMinutes();
        if (minutesAgo >= 525600) {
            return (minutesAgo / 525600) + " years ago";
        } else if (minutesAgo >= 1440) {
            return (minutesAgo / 1440) + " days ago";
        } else if (minutesAgo >= 60) {
            return (minutesAgo / 60) + " hours ago";
        } else {
            return minutesAgo + " minutes ago";
        }
    }
}