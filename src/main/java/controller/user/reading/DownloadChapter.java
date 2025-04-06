/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.reading;

import DAO.ChapterDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import model.Chapter;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DownloadChapter", urlPatterns = {"/DownloadChapter"})
public class DownloadChapter extends HttpServlet {

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
            out.println("<title>Servlet DownloadChapter</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DownloadChapter at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chapterIdParam = request.getParameter("id");
        if (chapterIdParam == null || chapterIdParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter.");
            return;
        }

        int chapterId;
        try {
            chapterId = Integer.parseInt(chapterIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter.");
            return;
        }

        ChapterDAO chapterDAO = new ChapterDAO();

        Chapter chapter = chapterDAO.getChapterForStaffById(chapterId);

        if (chapter == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Chapter not found.");
            return;
        }
        String fileURL = chapter.getFileURL();
        if (fileURL == null || fileURL.trim().isEmpty() || !fileURL.toLowerCase().startsWith("http")) {

            response.sendError(HttpServletResponse.SC_NOT_FOUND, "This chapter has no file or the file URL is invalid.");
            return;
        }

        // --- START CREATING THE FILE NAME YOU WANT ---
        String chapterName = chapter.getChapterName();
        if (chapterName == null || chapterName.trim().isEmpty()) {
            chapterName = "Chapter_" + chapterId;
        }

        // 3. Get the extension from the fileURL
        String extension = "";
        try {
            // Use URL to analyze the path more easily, avoiding query params if any
            String path = new URL(fileURL).getPath();
            int lastDotIndex = path.lastIndexOf('.');
            if (lastDotIndex > 0 && lastDotIndex < path.length() - 1) {
                extension = path.substring(lastDotIndex); // Include the "."
            } else {
                extension = ".file"; // Default if not found
                System.err.println("Warning: Could not determine file extension from URL path: " + path);
            }
        } catch (MalformedURLException e) {

            extension = ".file";
            System.err.println("Warning: MalformedURLException when parsing URL for extension: " + fileURL);
        }

        // 4. Clean up the chapter name and create the download file name
        String sanitizedChapterName = chapterName
                .replaceAll("[^a-zA-Z0-9\\s\\-_\\.]", "_")
                .replaceAll("[\\\\/:*?\"<>|]", "_")
                .replace(" ", "_");
        if (sanitizedChapterName.length() > 150) {
            sanitizedChapterName = sanitizedChapterName.substring(0, 150);
        }
        String desiredFilename = sanitizedChapterName + extension;

        // --- END CREATING THE FILE NAME YOU WANT ---
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fileURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();

            // 5. Check the Response Code from Cloudinary
            if (responseCode == HttpURLConnection.HTTP_OK) {

                // 6. Set headers for the client
                String contentType = connection.getContentType();
                if (contentType == null || contentType.equals("application/octet-stream")) {

                    contentType = getServletContext().getMimeType(desiredFilename);
                    if (contentType == null) {
                        contentType = "application/octet-stream";
                    }
                }
                response.setContentType(contentType);

                long contentLength = connection.getContentLengthLong();
                if (contentLength != -1) {
                    response.setContentLengthLong(contentLength);
                }

                String encodedFilename = URLEncoder.encode(desiredFilename, StandardCharsets.UTF_8.toString()).replace("+", "%20");
                response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);

                // 7. Read from the InputStream of Cloudinary and write to the OutputStream of the response
                try ( InputStream inStream = connection.getInputStream(); // Get InputStream from connection
                          OutputStream outStream = response.getOutputStream()) {

                    byte[] buffer = new byte[8192]; // Increase buffer size
                    int bytesRead;
                    while ((bytesRead = inStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                    outStream.flush();
                }

            } else {

                response.sendError(responseCode, "Cannot download file from the source. Error code: " + responseCode);
            }

        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + fileURL + " - Error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file path.");
        } catch (IOException e) {

            System.err.println("I/O error when downloading file from " + fileURL + " - Error: " + e.getMessage());

            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing file download request.");
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet cho phép tải chương từ Cloudinary";
    }// </editor-fold>

}
