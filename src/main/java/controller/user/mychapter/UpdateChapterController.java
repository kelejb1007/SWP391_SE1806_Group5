/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.mychapter;

import DAO.ChapterDAO;
import DAO.ChapterSubmissionDAO;
import DAO.PostChapterDAO;
import DAO.NovelDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import model.Chapter;
import model.Novel;
import java.io.IOException;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import model.ChapterSubmission;
import model.UserAccount;
import utils.CloudinaryUtils;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "UpdateChapterController", urlPatterns = {"/updateChapter"})
@MultipartConfig(maxFileSize = 1048576) // Giới hạn file 1MB
public class UpdateChapterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateChapterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateChapterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static final Logger LOGGER = Logger.getLogger(UpdateChapterController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chapterIdParam = request.getParameter("chapterId");

        if (chapterIdParam == null || chapterIdParam.isEmpty()) {
            request.setAttribute("message", "No Chapter ID provided. Please select a chapter to update.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
            return;
        }

        try {
            int chapterId = Integer.parseInt(chapterIdParam);
            PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
            Chapter chapter = postChapterDAO.getChapterById(chapterId); // Cần tạo phương thức này trong PostChapterDAO

            if (chapter == null) {
                request.setAttribute("message", "Chapter not found.");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
                return;
            }

            NovelDAO novelDAO = new NovelDAO();
            Novel novel = novelDAO.getNovelById(chapter.getNovelID());

            if (novel == null) {
                request.setAttribute("message", "Novel not found for this chapter.");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
                return;
            }

            String chapterContent = getChapterContent(chapter, request);
            chapterContent = chapterContent.replaceAll("<p>", "    ").replaceAll("</p>", "\n");
            request.setAttribute("chapterContent", chapterContent);

            request.setAttribute("chapterId", chapterId);
            request.setAttribute("chapter", chapter);
            request.setAttribute("novelId", chapter.getNovelID());
            request.setAttribute("novelName", novel.getNovelName());
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid Chapter ID format.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
        }
    }

    private String getChapterContent(Chapter chapter, HttpServletRequest request) {
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

            try ( BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
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
        int chapterId = Integer.parseInt(request.getParameter("chapterId"));
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
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
            return;
        }

        int chapterDraftID;
        try {
            UserAccount ua = (UserAccount) session.getAttribute("user");

            int novelId = Integer.parseInt(novelIdParam);
            Novel novel = novelDAO.getNovelById(novelId);
            if (novel == null) {
                request.setAttribute("message", "Novel not found.");
                request.setAttribute("messageType", "error");
                request.setAttribute("novelId", null);
                request.setAttribute("nextChapterNumber", null);
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
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
                    "draft"
            );

            LOGGER.log(Level.INFO, "Attempting to post chapter for novel: {0}", novel.getNovelName());
            chapterDraftID = postChapterDAO.postChapter(newChapter);

            ChapterSubmission ns = new ChapterSubmission();
            ns.setChapterID(chapterId);
            ns.setUserID(ua.getUserID());
            ns.setType("update");
            ns.setDraftID(chapterDraftID);
            if (subDAO.addUpdatingSubmission(ns)) {
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
            response.sendRedirect(request.getContextPath() + "/updateChapter?chapterId=" + chapterId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format for Novel ID.", e);
            request.setAttribute("message", "Invalid number format for Novel ID.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", null);
            request.setAttribute("nextChapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing chapter file.", e);
            request.setAttribute("message", "Error writing chapter file.");
            request.setAttribute("messageType", "error");
            request.setAttribute("novelId", novelIdParam != null ? Integer.parseInt(novelIdParam) : null);
            request.setAttribute("nextChapterNumber", null);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
        }
    }

    private String getFile(Part filePart) throws IOException {
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

    private String getFileByContent(String content) throws IOException {
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
