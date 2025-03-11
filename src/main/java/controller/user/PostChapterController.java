package controller.user;

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
import model.UserAccount;
import utils.CloudinaryUtils;

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
        ChapterDAO chapterDAO = new ChapterDAO();

        String novelIdParam = request.getParameter("novelId");
        String chapterIdParam = request.getParameter("chapterID");
        String chapterTitle = request.getParameter("chapterName");
        String chapterContent = request.getParameter("chapterContent");
        Part filePart = request.getPart("file");

        ChapterSubmissionDAO subDAO = new ChapterSubmissionDAO();
        String message;

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

        int chapterID;
        try {
            HttpSession session = request.getSession(false);
            UserAccount ua = (UserAccount) session.getAttribute("user");

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
                    0,
                    chapterTitle,
                    fileURL,
                    LocalDateTime.now(),
                    "pending"
            );
           
            LOGGER.log(Level.INFO, "Attempting to post chapter for novel: {0}", novel.getNovelName());
            chapterID = postChapterDAO.postChapter(newChapter);

            if (subDAO.addPostingSubmission(chapterID, ua.getUserID(), "post")) {
                message = "Create novel and send posting requirement successfully!";
            } else {
                message = "Error in sending posting requirement!!!!";
            }
            
            request.setAttribute("novelId", novelId);
            request.setAttribute("novelName", novel.getNovelName());
            request.setAttribute("nextChapterNumber", postChapterDAO.getNextChapterNumber(novelId));
            request.setAttribute("filePath", fileURL);
            request.setAttribute("message", "Chapter posted successfully!");
            request.setAttribute("messageType", "success");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/postChapter.jsp").forward(request, response);
            //            if (chapterID > 0) {
            //                int chapterNumber = postChapterDAO.getNextChapterNumber(novelId) - 1;
            //                String filePath = postChapterDAO.getChapterFilePath(novel.getNovelName(), chapterNumber);
            //                
            //                if (filePath == null) {
            //                    LOGGER.log(Level.SEVERE, "Failed to create directory for novel: {0}", novel.getNovelName());
            //                    request.setAttribute("message", "Failed to create chapter directory.");
            //                    request.setAttribute("messageType", "error");
            //                    request.setAttribute("novelId", novelId);
            //                    request.setAttribute("nextChapterNumber", null);
            //                    request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
            //                    return;
            //                }
            //
            //                boolean saveSuccess = false;
            //                
            //                if (filePart != null && filePart.getSize() > 0) {
            //                    LOGGER.log(Level.INFO, "Saving content from uploaded file");
            //                    saveSuccess = postChapterDAO.saveChapterFile(filePath, filePart.getInputStream());
            //                } else if (chapterContent != null && !chapterContent.trim().isEmpty()) {
            //                    LOGGER.log(Level.INFO, "Saving content from textarea");
            //                    saveSuccess = postChapterDAO.saveChapterContent(filePath, chapterContent);
            //                }
            //
            //                if (saveSuccess) {
            //                    LOGGER.log(Level.INFO, "Content saved successfully, updating file path in database");
            //                    postChapterDAO.updateChapterFilePath(chapterID, filePath);
            //                    
            //                    request.setAttribute("novelId", novelId);
            //                    request.setAttribute("novelName", novel.getNovelName());
            //                    request.setAttribute("nextChapterNumber", postChapterDAO.getNextChapterNumber(novelId));
            //                    request.setAttribute("filePath", filePath);
            //                    request.setAttribute("message", "Chapter posted successfully!");
            //                    request.setAttribute("messageType", "success");
            //                    
            //                    request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
            //                    return;
            //                } else {
            //                    LOGGER.log(Level.SEVERE, "Failed to save chapter content");
            //                    request.setAttribute("message", "Failed to save chapter content.");
            //                    request.setAttribute("messageType", "error");
            //                    request.setAttribute("novelId", novelId);
            //                    request.setAttribute("nextChapterNumber", null);
            //                    request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
            //                }
            //            } else {
            //                LOGGER.log(Level.SEVERE, "Failed to post chapter, returned ID: {0}", chapterID);
            //                request.setAttribute("message", "Failed to post chapter.");
            //                request.setAttribute("messageType", "error");
            //                request.setAttribute("novelId", novelId);
            //                request.setAttribute("nextChapterNumber", null);
            //                request.getRequestDispatcher("/WEB-INF/views/user/postChapter.jsp").forward(request, response);
            //            }
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

    public String getFile(Part filePart) throws IOException {
        String fileName = filePart.getSubmittedFileName();

        // Lưu file tạm thời
        File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
        try ( InputStream input = filePart.getInputStream();  FileOutputStream output = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        // Kết nối Cloudinary và upload ảnh
        Cloudinary cloudinary = CloudinaryUtils.getInstance();
        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
                "resource_type", "auto")); // Tự động xác định loại file (hình ảnh, PDF, v.v.)

        // Xóa file tạm
        tempFile.delete();

        // Lấy URL file từ Cloudinary
        String fileUrl = (String) uploadResult.get("secure_url");
        return fileUrl;
    }

    public String getFileByContent(String content) throws IOException {
        String filePath = "temp.txt";
        Files.write(Paths.get(filePath), content.getBytes());

        // Kết nối Cloudinary và upload ảnh
        Cloudinary cloudinary = CloudinaryUtils.getInstance();
        Map uploadResult = cloudinary.uploader().upload(new File(filePath), ObjectUtils.asMap(
                "resource_type", "auto")); // Tự động xác định loại file (hình ảnh, PDF, v.v.)

        // Xóa file tạm
        Files.delete(Paths.get(filePath));

        // Lấy URL file từ Cloudinary
        String fileUrl = (String) uploadResult.get("secure_url");
        return fileUrl;
    }
}
