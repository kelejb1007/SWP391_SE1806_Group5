/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user.mychapter;

import DAO.ChapterDAO;
import DAO.NovelDAO;
import DAO.ReadingHistoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import model.Chapter;
import model.Novel;
import model.ReadingHistory;
import model.UserAccount;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name="MyChapterController", urlPatterns={"/mychapter"})
public class MyChapterController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyChapterController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyChapterController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

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
        try {
            // 1. Validate and retrieve chapter ID
            int chapterId = getChapterIdFromRequest(request, response);
            if (chapterId == -1) return;

            // 2. Get chapter details
            Chapter chapter = getChapterDetails(chapterId, response);
            if (chapter == null) return;

            // 3. Get novel details
            Novel novel = getNovelDetails(chapter.getNovelID(), response);
            if (novel == null) return;

            // 4. Handle user login and reading history
            HttpSession session = request.getSession();
            UserAccount user = (UserAccount) session.getAttribute("user");
            handleReadingHistory(user, novel, chapter);

            // 5. Get chapter list and navigation
            String sortParam = request.getParameter("sort");
            List<Chapter> chapters = getChapters(chapter.getNovelID(), sortParam);
            setNavigationAttributes(request, chapter, chapters);

            // 6. Determine if user can view content
            boolean canViewContent = canViewContent(user, chapter);

            // 7. Get chapter content
            String chapterContent = canViewContent ? getChapterContent(chapter) : "";

            // 8. Set request attributes
            setRequestAttributes(request, chapter, chapters, novel, canViewContent, sortParam, chapterContent);

            // 9. Forward to view
            forwardToView(request, response);

        } catch (Exception e) {
            // Handle unexpected exceptions
            e.printStackTrace(); // Log the error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    private int getChapterIdFromRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String chapterIdParam = request.getParameter("id");
        if (chapterIdParam == null || chapterIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id parameter");
            return -1;
        }
        return Integer.parseInt(chapterIdParam);
    }

    private Chapter getChapterDetails(int chapterId, HttpServletResponse response) throws IOException {
        Chapter chapter = chapterDAO.getMyChapterById(chapterId);
        if (chapter == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Chapter not found");
            return null;
        }
        return chapter;
    }

    private Novel getNovelDetails(int novelId, HttpServletResponse response) throws IOException {
        Novel novel = novelDAO.getNovelById(novelId);
        if (novel == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Novel not found");
            return null;
        }
        return novel;
    }

    private void handleReadingHistory(UserAccount user, Novel novel, Chapter chapter) {
        if (user != null) {
            ReadingHistory history = historyDAO.getReadingHistory(user.getUserID(), novel.getNovelID(), chapter.getChapterID());
            if (history == null) {
                history = new ReadingHistory();
                history.setUserID(user.getUserID());
                history.setNovelID(novel.getNovelID());
                history.setChapterID(chapter.getChapterID());
                history.setLastReadDate(LocalDateTime.now());
                boolean added = historyDAO.addReadingHistory(history);
                if (!added) {
                    System.err.println("Failed to add reading history for user " + user.getFullName() + ", novel " + novel.getNovelID() + ", chapter " + chapter.getChapterID());
                }
            } else {
                history.setLastReadDate(LocalDateTime.now());
                boolean updated = historyDAO.updateLastReadDate(history);
                if (!updated) {
                    System.err.println("Failed to update reading history for user " + user.getFullName() + ", novel " + novel.getNovelID() + ", chapter " + chapter.getChapterID());
                }
            }
        }
    }

    private List<Chapter> getChapters(int novelId, String sortParam) {
        return chapterDAO.getMyChaptersByNovelId(novelId, sortParam);
    }

    private void setNavigationAttributes(HttpServletRequest request, Chapter chapter, List<Chapter> chapters) {
        Chapter previousChapter = null;
        Chapter nextChapter = null;
        String latestReleaseTime = null;

        if (!chapters.isEmpty()) {
            latestReleaseTime = getTimeElapsed(chapters.get(0).getChapterCreatedDate());
            request.setAttribute("latestReleaseChapter", chapters.get(0));
            request.setAttribute("latestReleaseTime", latestReleaseTime);
        }

        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).getChapterID() == chapter.getChapterID()) {
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
    }

    private boolean canViewContent(UserAccount user, Chapter chapter) {
        boolean isLoggedIn = (user != null);
        return isLoggedIn || isWithinFreeLimit(chapter.getChapterNumber());
    }

    public boolean isWithinFreeLimit(int chapterNumber) {
        return chapterNumber <= 3;
    }

   private String getChapterContent(Chapter chapter) {
        String fileURL = chapter.getFileURL();
        if (fileURL == null || fileURL.isEmpty()) {
            return "Chapter content not available.";
        }

        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(fileURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Quy tắc: Văn bản giữa dấu "" sẽ được in nghiêng
                    line = line.replaceAll("([“”\"])(.*?)([“”\"])", "$1<span class='in-nghieng'>$2</span>$3");

                    if (!line.trim().isEmpty()) {
                        content.append("<p>").append(line).append("</p>\n");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error in getChapterContent: " + e.getMessage());
            return "Chapter content not available.";
        }

        return content.toString();
    }

    private void setRequestAttributes(HttpServletRequest request, Chapter chapter, List<Chapter> chapters, Novel novel, boolean canViewContent, String sortParam, String chapterContent) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("chapter", chapter);
        request.setAttribute("chapters", chapters);
        request.setAttribute("novel", novel);
        request.setAttribute("canViewContent", canViewContent);
        request.setAttribute("sort", sortParam);
        request.setAttribute("chapterContent", chapterContent);
    }

    private void forwardToView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/views/user/reading/chapter/chapter-content.jsp").forward(request, response);
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

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
