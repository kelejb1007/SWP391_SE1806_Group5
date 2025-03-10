/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.PostChapterDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "DeleteChapterController", urlPatterns = {"/deleteChapter"})
public class DeleteChapterController extends HttpServlet {

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
            out.println("<title>Servlet DeleteChapterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteChapterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static final Logger LOGGER = Logger.getLogger(DeleteChapterController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Processing GET request for deleteChapter with novelName: {0}, chapterNumber: {1}",
                new Object[]{request.getParameter("novelName"), request.getParameter("chapterNumber")});

        String novelName = request.getParameter("novelName");
        String chapterNumberParam = request.getParameter("chapterNumber");

        // Kiểm tra tham số đầu vào
        if (novelName == null || novelName.isEmpty() || chapterNumberParam == null || chapterNumberParam.isEmpty()) {
            request.setAttribute("message", "Novel name and chapter number are required.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/deleteChapter.jsp").forward(request, response);
            return;
        }

        try {
            int chapterNumber = Integer.parseInt(chapterNumberParam);
            PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
            model.Chapter chapter = postChapterDAO.getChapterByNovelNameAndChapterNumber(novelName, chapterNumber);

            if (chapter == null) {
                request.setAttribute("message", "Chapter not found for novel: " + novelName + ", chapter number: " + chapterNumber);
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/deleteChapter.jsp").forward(request, response);
                return;
            }

            request.setAttribute("chapter", chapter);
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/deleteChapter.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid chapter number format: {0}", e.getMessage());
            request.setAttribute("message", "Invalid chapter number format.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/deleteChapter.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Processing POST request for deleteChapter with novelName: {0}, chapterNumber: {1}",
                new Object[]{request.getParameter("novelName"), request.getParameter("chapterNumber")});

        PostChapterDAO postChapterDAO = new PostChapterDAO(getServletContext());
        String novelName = request.getParameter("novelName");
        String chapterNumberParam = request.getParameter("chapterNumber");

        if (novelName == null || novelName.isEmpty() || chapterNumberParam == null || chapterNumberParam.isEmpty()) {
            request.setAttribute("message", "Novel name and chapter number are required.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/chapter/deleteChapter.jsp").forward(request, response);
            return;
        }

        try {
            int chapterNumber = Integer.parseInt(chapterNumberParam);
            model.Chapter chapter = postChapterDAO.getChapterByNovelNameAndChapterNumber(novelName, chapterNumber);

            if (chapter == null) {
                request.setAttribute("message", "Chapter not found for novel: " + novelName + ", chapter number: " + chapterNumber);
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("/WEB-INF/views/user/chapter/deleteChapter.jsp").forward(request, response);
                return;
            }

            boolean deleted = postChapterDAO.deleteChapter(novelName, chapterNumber);

            if (deleted) {
                request.setAttribute("message", "Chapter deleted successfully!");
                request.setAttribute("messageType", "success");
                // Có thể redirect thay vì forward
                // response.sendRedirect(request.getContextPath() + "/user/chapters");
            } else {
                LOGGER.log(Level.SEVERE, "Failed to delete chapter for novelName: {0}, chapterNumber: {1}",
                        new Object[]{novelName, chapterNumber});
                request.setAttribute("message", "Failed to delete chapter. Please check server logs for details.");
                request.setAttribute("messageType", "error");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid chapter number format: {0}", e.getMessage());
            request.setAttribute("message", "Invalid chapter number format.");
            request.setAttribute("messageType", "error");
        }

        request.getRequestDispatcher("/WEB-INF/views/user/chapter/deleteChapter.jsp").forward(request, response);
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
