/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.ChapterDAO;
import DAO.PostChapterDAO;
import DAO.NovelDAO;
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
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
        PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
        NovelDAO novelDAO = new NovelDAO();

        String chapterIdParam = request.getParameter("chapterId");
        String novelIdParam = request.getParameter("novelId");
        String chapterNumberParam = request.getParameter("chapterNumber");
        String chapterTitle = request.getParameter("chapterName");
        String chapterContent = request.getParameter("chapterContent");
        Part filePart = request.getPart("file");

        LOGGER.log(Level.INFO, "Processing update request with chapterId: {0}, novelId: {1}, chapterNumber: {2}, chapterTitle: {3}, content length: {4}, has file: {5}",
                new Object[]{chapterIdParam, novelIdParam, chapterNumberParam, chapterTitle,
                    chapterContent != null ? chapterContent.length() : 0,
                    filePart != null && filePart.getSize() > 0});

        if ((filePart == null || filePart.getSize() == 0) && (chapterContent == null || chapterContent.trim().isEmpty())) {
            request.setAttribute("message", "Please enter content or upload a file.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
            return;
        }

        try {
            int chapterId = Integer.parseInt(chapterIdParam);
            int novelId = Integer.parseInt(novelIdParam);
            int chapterNumber = Integer.parseInt(chapterNumberParam);

            Novel novel = novelDAO.getNovelById(novelId);
            if (novel == null) {
                request.setAttribute("message", "Novel not found.");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
                return;
            }

            // Kiểm tra chapter tồn tại
            Chapter existingChapter = postChapterDAO.getChapterById(chapterId);
            if (existingChapter == null) {
                request.setAttribute("message", "Chapter not found.");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
                return;
            }

            // Kiểm tra trùng chapterNumber
            if (chapterNumber != existingChapter.getChapterNumber() && postChapterDAO.isChapterNumberExists(novelId, chapterNumber)) {
                request.setAttribute("message", "Chapter number already exists for this novel.");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
                return;
            }

            Chapter updatedChapter = new Chapter();
            updatedChapter.setChapterID(chapterId);
            updatedChapter.setNovelID(novelId);
            updatedChapter.setChapterNumber(chapterNumber);
            updatedChapter.setChapterName(chapterTitle);
            updatedChapter.setFileURL(existingChapter.getFileURL()); // Giữ nguyên fileURL cũ
            updatedChapter.setChapterCreatedDate(existingChapter.getChapterCreatedDate()); // Giữ nguyên ngày tạo
            updatedChapter.setChapterStatus(existingChapter.getChapterStatus()); // Giữ nguyên trạng thái

            boolean updateSuccess = postChapterDAO.updateChapter(updatedChapter);

            if (updateSuccess) {
                String filePath = existingChapter.getFileURL(); // Sử dụng fileURL hiện tại
                boolean saveSuccess = false;

                if (filePart != null && filePart.getSize() > 0) {
                    LOGGER.log(Level.INFO, "Updating content from uploaded file");
                    saveSuccess = postChapterDAO.saveChapterFile(filePath, filePart.getInputStream());
                } else if (chapterContent != null && !chapterContent.trim().isEmpty()) {
                    LOGGER.log(Level.INFO, "Updating content from textarea");
                    saveSuccess = postChapterDAO.saveChapterContent(filePath, chapterContent);
                }

                if (saveSuccess) {
                    request.setAttribute("chapter", updatedChapter);
                    request.setAttribute("novelId", novelId);
                    request.setAttribute("novelName", novel.getNovelName());
                    request.setAttribute("message", "Chapter updated successfully!");
                    request.setAttribute("messageType", "success");
                } else {
                    LOGGER.log(Level.SEVERE, "Failed to save updated chapter content");
                    request.setAttribute("message", "Failed to save updated chapter content.");
                    request.setAttribute("messageType", "error");
                }
            } else {
                LOGGER.log(Level.SEVERE, "Failed to update chapter");
                request.setAttribute("message", "Failed to update chapter.");
                request.setAttribute("messageType", "error");
            }

            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format.", e);
            request.setAttribute("message", "Invalid number format.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error updating chapter file.", e);
            request.setAttribute("message", "Error updating chapter file.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/updateChapter.jsp").forward(request, response);
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
