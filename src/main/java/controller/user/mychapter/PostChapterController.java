package controller.user.mychapter;

import DAO.ChapterDAO;
import DAO.ChapterSubmissionDAO;
import DAO.PostChapterDAO;
import model.Chapter;
import model.Novel;
import DAO.NovelDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import model.ChapterSubmission;
import model.UserAccount;
import utils.CloudinaryUtils;

@WebServlet(name = "PostChapterController", urlPatterns = {"/postChapter"})
@MultipartConfig(maxFileSize = 1048576) // Giới hạn file 1MB
public class PostChapterController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PostChapterController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        }

        String novelIdParam = request.getParameter("novelId");
        String chapterIdParam = request.getParameter("chapterId");
        Integer novelId = (Integer) session.getAttribute("novelId");

        if (novelId == null && (novelIdParam == null || novelIdParam.isEmpty())) {
            request.setAttribute("message", "No Novel ID provided. Please select a novel first.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", null);
            request.setAttribute("chapterNumber", null);
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
                request.setAttribute("chapterNumber", null);
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
            request.setAttribute("chapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
            return;
        }

        PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
        int chapterNumber;

        if (chapterIdParam != null && !chapterIdParam.isEmpty()) {
            try {
                int chapterId = Integer.parseInt(chapterIdParam);
                Chapter existingChapter = postChapterDAO.getChapterById(chapterId);
                if (existingChapter != null && existingChapter.getNovelID() == novelId) {
                    chapterNumber = existingChapter.getChapterNumber();
                    request.setAttribute("chapterId", chapterId);
                    request.setAttribute("chapterName", existingChapter.getChapterName());
                    request.setAttribute("chapterContent", existingChapter.getFileURL());
                } else {
                    request.setAttribute("message", "Chapter not found or does not belong to this novel.");
                    request.setAttribute("messageType", "error");
                    request.setAttribute("novelId", novelId);
                    request.setAttribute("chapterNumber", null);
                    request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid Chapter ID format.");
                request.setAttribute("messageType", "error");
                request.setAttribute("novelId", novelId);
                request.setAttribute("chapterNumber", null);
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                return;
            }
        } else {
            chapterNumber = postChapterDAO.getNextChapterNumber(novelId);
        }

        String novelName = novel.getNovelName() != null ? novel.getNovelName() : "Unknown";
        String filePath = postChapterDAO.getChapterFilePath(novelName, chapterNumber);

        request.setAttribute("novelId", novelId);
        request.setAttribute("novelName", novel.getNovelName());
        request.setAttribute("chapterNumber", chapterNumber);
        request.setAttribute("filePath", filePath);
        request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        }

        PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
        NovelDAO novelDAO = new NovelDAO();
        ChapterDAO chapterDAO = new ChapterDAO();

        String novelIdParam = request.getParameter("novelId");
        String chapterIdParam = request.getParameter("chapterId");
        String chapterNumberParam = request.getParameter("chapterNumber");
        String chapterTitle = request.getParameter("chapterName");
        String chapterContent = request.getParameter("chapterContent");
        Part filePart = request.getPart("file");

        ChapterSubmissionDAO subDAO = new ChapterSubmissionDAO();
        String message;

        LOGGER.log(Level.INFO, "Processing post request with novelId: {0}, chapterId: {1}, chapterNumber: {2}, chapterTitle: {3}, content length: {4}, has file: {5}",
                new Object[]{novelIdParam, chapterIdParam, chapterNumberParam, chapterTitle,
                    chapterContent != null ? chapterContent.length() : 0,
                    filePart != null && filePart.getSize() > 0});

        if ((filePart == null || filePart.getSize() == 0) && (chapterContent == null || chapterContent.trim().isEmpty())) {
            request.setAttribute("message", "Please enter content or upload a file.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", novelIdParam != null ? Integer.parseInt(novelIdParam) : null);
            request.setAttribute("chapterNumber", chapterNumberParam != null ? Integer.parseInt(chapterNumberParam) : null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
            return;
        }

        int chapterID;
        try {
            UserAccount ua = (UserAccount) session.getAttribute("user");
            int novelId = Integer.parseInt(novelIdParam);
            int chapterNumber = Integer.parseInt(chapterNumberParam);

            Novel novel = novelDAO.getNovelById(novelId);
            if (novel == null) {
                request.setAttribute("message", "Novel not found.");
                request.setAttribute("messageType", "error");
                request.setAttribute("novelId", null);
                request.setAttribute("chapterNumber", null);
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                return;
            }

            // Kiểm tra trùng chapterNumber (chỉ kiểm tra với các chương có trạng thái "active")
            int existingChapterId = (chapterIdParam != null && !chapterIdParam.isEmpty()) ? Integer.parseInt(chapterIdParam) : -1;
            if (postChapterDAO.isChapterNumberExists(novelId, chapterNumber, existingChapterId)) {
                request.setAttribute("message", "Chapter number " + chapterNumber + " already exists for this novel in active state.");
                request.setAttribute("messageType", "error");
                request.setAttribute("novelId", novelId);
                request.setAttribute("chapterNumber", chapterNumber);
                request.setAttribute("chapterName", chapterTitle);
                request.setAttribute("chapterContent", chapterContent);
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                return;
            }

            String fileURL = null;
            if (filePart != null && filePart.getSize() > 0) {
                fileURL = getFile(filePart);
            } else if (chapterContent != null) {
                fileURL = getFileByContent(chapterContent);
            }

            Chapter newChapter = new Chapter(
                    0,
                    novelId,
                    novel.getNovelName(),
                    chapterNumber, // Sử dụng chapterNumber từ form
                    chapterTitle,
                    fileURL,
                    LocalDateTime.now(),
                    "pending"
            );

            LOGGER.log(Level.INFO, "Attempting to post chapter for novel: {0}, chapterNumber: {1}", new Object[]{novel.getNovelName(), chapterNumber});
            chapterID = postChapterDAO.postChapter(newChapter);

            if (chapterID == -1) {
                request.setAttribute("message", "Failed to post chapter.");
                request.setAttribute("messageType", "error");
                request.setAttribute("novelId", novelId);
                request.setAttribute("chapterNumber", chapterNumber);
                request.setAttribute("chapterName", chapterTitle);
                request.setAttribute("chapterContent", chapterContent);
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
                return;
            }

            ChapterSubmission ns = new ChapterSubmission();
            ns.setUserID(ua.getUserID());
            ns.setType("update");
            ns.setDraftID(chapterID);
            if (subDAO.addPostingSubmission(chapterID, ua.getUserID(), "post")) {
                message = "Create chapter and send posting requirement successfully!";
            } else {
                message = "Error in sending posting requirement!";
            }

            request.setAttribute("novelId", novelId);
            request.setAttribute("novelName", novel.getNovelName());
            request.setAttribute("chapterNumber", postChapterDAO.getNextChapterNumber(novelId));
            request.setAttribute("filePath", fileURL);
            request.setAttribute("message", "Chapter posted successfully!");
            request.setAttribute("messageType", "success");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format for Novel ID or Chapter Number.", e);
            request.setAttribute("message", "Invalid number format for Novel ID or Chapter Number.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", null);
            request.setAttribute("chapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing chapter file.", e);
            request.setAttribute("message", "Error writing chapter file.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", novelIdParam != null ? Integer.parseInt(novelIdParam) : null);
            request.setAttribute("chapterNumber", chapterNumberParam != null ? Integer.parseInt(chapterNumberParam) : null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
        }
    }

    private String getFile(Part filePart) throws IOException {
        String fileName = filePart.getSubmittedFileName();

        File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
        try (InputStream input = filePart.getInputStream(); FileOutputStream output = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        Cloudinary cloudinary = CloudinaryUtils.getInstance();
        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
                "resource_type", "auto"));

        tempFile.delete();

        String fileUrl = (String) uploadResult.get("secure_url");
        return fileUrl;
    }

    private String getFileByContent(String content) throws IOException {
        String filePath = "temp.txt";
        Files.write(Paths.get(filePath), content.getBytes());

        Cloudinary cloudinary = CloudinaryUtils.getInstance();
        Map uploadResult = cloudinary.uploader().upload(new File(filePath), ObjectUtils.asMap(
                "resource_type", "auto"));

        Files.delete(Paths.get(filePath));

        String fileUrl = (String) uploadResult.get("secure_url");
        return fileUrl;
    }
}