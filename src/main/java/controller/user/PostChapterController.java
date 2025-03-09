package controller.user;

import DAO.PostChapterDAO;
import model.Chapter;
import model.Novel;
import DAO.NovelDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "PostChapterController", urlPatterns = {"/postChapter"})
@MultipartConfig(maxFileSize = 1048576) // Giới hạn file 1MB
public class PostChapterController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PostChapterController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String novelIdParam = request.getParameter("novelId");
        Integer novelId = (Integer) session.getAttribute("novelId");

        // Nếu novelId không có trong session, kiểm tra trong query parameter
        if (novelId == null && (novelIdParam == null || novelIdParam.isEmpty())) {
            request.setAttribute("message", "No Novel ID provided. Please select a novel first.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", null); // Đặt tạm để JSP biết có lỗi
            request.setAttribute("nextChapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
            return;
        }

        if (novelIdParam != null && !novelIdParam.isEmpty()) {
            try {
                novelId = Integer.parseInt(novelIdParam);
                session.setAttribute("novelId", novelId);
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid Novel ID format.");
                request.setAttribute("messageType", "error");
                request.setAttribute("novelId", null);
                request.setAttribute("nextChapterNumber", null);
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                return;
            }
        }

        NovelDAO novelDAO = new NovelDAO();
        Novel novel = novelDAO.getNovelById(novelId);

        if (novel == null) {
            request.setAttribute("message", "Novel not found.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", null);
            request.setAttribute("nextChapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
            return;
        }

        PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
        int nextChapterNumber = postChapterDAO.getNextChapterNumber(novelId);
        
        String novelName = novel.getNovelName() != null ? novel.getNovelName() : "Unknown";
        String filePath = postChapterDAO.getChapterFilePath(novelName, nextChapterNumber);

        // Đặt tất cả attribute cần thiết
        request.setAttribute("novelId", novelId);
        request.setAttribute("novelName", novel.getNovelName());
        request.setAttribute("nextChapterNumber", nextChapterNumber);
        request.setAttribute("filePath", filePath);
        request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
        NovelDAO novelDAO = new NovelDAO();

        String novelIdParam = request.getParameter("novelId");
        String chapterTitle = request.getParameter("chapterName");
        String chapterContent = request.getParameter("chapterContent");
        Part filePart = request.getPart("file");

        LOGGER.log(Level.INFO, "Processing post request with novelId: {0}, chapterTitle: {1}, content length: {2}, has file: {3}",
                new Object[]{novelIdParam, chapterTitle, 
                    chapterContent != null ? chapterContent.length() : 0, 
                    filePart != null && filePart.getSize() > 0});

        if ((filePart == null || filePart.getSize() == 0) && (chapterContent == null || chapterContent.trim().isEmpty())) {
            request.setAttribute("message", "Please enter content or upload a file.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", novelIdParam != null ? Integer.parseInt(novelIdParam) : null);
            request.setAttribute("nextChapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
            return;
        }

        try {
            int novelId = Integer.parseInt(novelIdParam);
            Novel novel = novelDAO.getNovelById(novelId);
            if (novel == null) {
                request.setAttribute("message", "Novel not found.");
                request.setAttribute("messageType", "error");
                request.setAttribute("novelId", null);
                request.setAttribute("nextChapterNumber", null);
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                return;
            }

            Chapter newChapter = new Chapter(
                0, 
                novelId, 
                novel.getNovelName(), 
                0, 
                chapterTitle, 
                "", 
                LocalDateTime.now(), 
                "pending"
            );
            
            LOGGER.log(Level.INFO, "Attempting to post chapter for novel: {0}", novel.getNovelName());
            int chapterID = postChapterDAO.postChapter(newChapter);

            if (chapterID > 0) {
                int chapterNumber = postChapterDAO.getNextChapterNumber(novelId) - 1;
                String filePath = postChapterDAO.getChapterFilePath(novel.getNovelName(), chapterNumber);
                
                if (filePath == null) {
                    LOGGER.log(Level.SEVERE, "Failed to create directory for novel: {0}", novel.getNovelName());
                    request.setAttribute("message", "Failed to create chapter directory.");
                    request.setAttribute("messageType", "error");
                    request.setAttribute("novelId", novelId);
                    request.setAttribute("nextChapterNumber", null);
                    request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                    return;
                }

                boolean saveSuccess = false;
                
                if (filePart != null && filePart.getSize() > 0) {
                    LOGGER.log(Level.INFO, "Saving content from uploaded file");
                    saveSuccess = postChapterDAO.saveChapterFile(filePath, filePart.getInputStream());
                } else if (chapterContent != null && !chapterContent.trim().isEmpty()) {
                    LOGGER.log(Level.INFO, "Saving content from textarea");
                    saveSuccess = postChapterDAO.saveChapterContent(filePath, chapterContent);
                }

                if (saveSuccess) {
                    LOGGER.log(Level.INFO, "Content saved successfully, updating file path in database");
                    postChapterDAO.updateChapterFilePath(chapterID, filePath);
                    
                    request.setAttribute("novelId", novelId);
                    request.setAttribute("novelName", novel.getNovelName());
                    request.setAttribute("nextChapterNumber", postChapterDAO.getNextChapterNumber(novelId));
                    request.setAttribute("filePath", filePath);
                    request.setAttribute("message", "Chapter posted successfully!");
                    request.setAttribute("messageType", "success");
                    
                    request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                    return;
                } else {
                    LOGGER.log(Level.SEVERE, "Failed to save chapter content");
                    request.setAttribute("message", "Failed to save chapter content.");
                    request.setAttribute("messageType", "error");
                    request.setAttribute("novelId", novelId);
                    request.setAttribute("nextChapterNumber", null);
                    request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                }
            } else {
                LOGGER.log(Level.SEVERE, "Failed to post chapter, returned ID: {0}", chapterID);
                request.setAttribute("message", "Failed to post chapter.");
                request.setAttribute("messageType", "error");
                request.setAttribute("novelId", novelId);
                request.setAttribute("nextChapterNumber", null);
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format for Novel ID.", e);
            request.setAttribute("message", "Invalid number format for Novel ID.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", null);
            request.setAttribute("nextChapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing chapter file.", e);
            request.setAttribute("message", "Error writing chapter file.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", novelIdParam != null ? Integer.parseInt(novelIdParam) : null);
            request.setAttribute("nextChapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
        }
    }
}