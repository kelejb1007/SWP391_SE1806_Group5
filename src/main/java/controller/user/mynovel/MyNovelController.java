/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.mynovel;

import DAO.GenreDAO;
import DAO.NovelDAO;
import DAO.NovelSubmissionDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import controller.staff.managenovel.ManageNovelController;
import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Genre;
import model.Novel;
import model.NovelSubmission;
import model.UserAccount;
import utils.CloudinaryUtils;

/**
 *
 * @author Nguyen Thanh Trung
 */
@MultipartConfig
@WebServlet(name = "MyNovelController", urlPatterns = {"/mynovel"})
public class MyNovelController extends HttpServlet {

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
            out.println("<title>Servlet MyNovelController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyNovelController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "viewdetail":
                viewNovelDetail(request, response);
                break;
            case "post":
                showPostingForm(request, response);
                break;
            case "update":
                showUpdateForm(request, response);
                break;
            case "viewposthistory":
                viewPostingHistory(request, response);
                break;
            default:
                viewMyNovels(request, response);
        }
    }

    private void viewMyNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        try {
            HttpSession session = request.getSession(false);
            UserAccount us = (UserAccount) session.getAttribute("user");
            listNovel = nd.getMyNovels(us.getUserID());
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovels.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void viewNovelDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        int novelID = Integer.parseInt(request.getParameter("novelID"));
        Novel nl;
        try {
            nl = nd.getNovelByID(novelID);
            request.setAttribute("novel", nl);
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovelDetail.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showPostingForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GenreDAO genDAO = new GenreDAO();
        List<Genre> genreList;
        try {
            genreList = genDAO.getAllGenres();
            request.setAttribute("genreList", genreList);
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/postNovel.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GenreDAO genDAO = new GenreDAO();
        NovelDAO nDAO = new NovelDAO();
        List<Genre> genreList;
        Novel nl;
        String genreOfNovel;
        int novelID = Integer.parseInt(request.getParameter("novelID"));
        try {
            nl = nDAO.getNovelByID(novelID);
            genreOfNovel = genDAO.getGenreByNovelID(novelID);
            genreList = genDAO.getAllGenres();

            request.setAttribute("novel", nl);
            request.setAttribute("genreOfNovel", genreOfNovel);
            request.setAttribute("genreList", genreList);
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/updateNovel.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void viewPostingHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelSubmissionDAO nd = new NovelSubmissionDAO();
        List<NovelSubmission> list;
        String message = null;

        try {
            HttpSession session = request.getSession(false);
            UserAccount us = (UserAccount) session.getAttribute("user");

            list = nd.getSubmisstionHistory(us.getUserID());
            if (list == null) {
                message = "No Novels!";
            }
            request.setAttribute("message", message);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/postingHistory.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "post":
                postNovel(request, response);
                break;
            case "update":
                updateNovel(request, response);
                break;
            case "delete":
                deleteNovel(request, response);
                break;
            default:
                viewMyNovels(request, response);
        }
    }

    private void postNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String novelName = request.getParameter("novelName");
        String novelDescription = request.getParameter("novelDescription");
        int totalChapter = Integer.parseInt(request.getParameter("totalChapter"));
        String[] genreList = request.getParameterValues("genreList");
//        String genres = (genreNames != null) ? String.join(", ", genreNames) : "";
        String imageURL = request.getParameter("file_hidden");
        Part filePart = request.getPart("imageURL");

        NovelDAO nDAO = new NovelDAO();
        NovelSubmissionDAO subDAO = new NovelSubmissionDAO();
        GenreDAO genDAO = new GenreDAO();

        int novelID;
        String message;
        try {
            HttpSession session = request.getSession(false);
            UserAccount ua = (UserAccount) session.getAttribute("user");

            if (filePart != null && filePart.getSize() > 0) {
                imageURL = getImg(filePart);
            }

            Novel nl = new Novel(novelName, ua.getUserID(), imageURL, novelDescription, totalChapter, "pending");

            novelID = nDAO.addNovel(nl);
            if (novelID != -1) {
                if (genreList != null) {
                    for (int i = 0; i < genreList.length; i++) {
                        genDAO.addGenreNovel(Integer.parseInt(genreList[i]), novelID);
                    }
                }
                if (subDAO.addPostingSubmission(novelID, ua.getUserID(), "post")) {
                    message = "Create novel and send posting requirement successfully!";
                } else {
                    message = "Error in sending posting requirement!!!!";
                }
            } else {
                message = "Error in creating novel!!!!";
            }

            request.setAttribute("message", message);
            showPostingForm(request, response);
//            response.sendRedirect("mynovel?action=post");
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int novelID = Integer.parseInt(request.getParameter("novelID"));

        String novelName = request.getParameter("novelName");
        String novelDescription = request.getParameter("novelDescription");
        int totalChapter = Integer.parseInt(request.getParameter("totalChapter"));
        String[] genreList = request.getParameterValues("genreList");
//        String genres = (genreNames != null) ? String.join(", ", genreNames) : "";
        String imageURL = request.getParameter("file_hidden");
        Part filePart = request.getPart("imageURL");

        NovelDAO nDAO = new NovelDAO();
        NovelSubmissionDAO subDAO = new NovelSubmissionDAO();
        GenreDAO genDAO = new GenreDAO();

        int novelDraftID;
        String message;
        try {
            HttpSession session = request.getSession(false);
            UserAccount ua = (UserAccount) session.getAttribute("user");

            if (filePart != null && filePart.getSize() > 0) {
                imageURL = getImg(filePart);
            }

            Novel nl = new Novel(novelName, ua.getUserID(), imageURL, novelDescription, totalChapter, "draft");

            novelDraftID = nDAO.addNovel(nl);
            if (novelDraftID != -1) {
                if (genreList != null) {
                    for (int i = 0; i < genreList.length; i++) {
                        genDAO.addGenreNovel(Integer.parseInt(genreList[i]), novelDraftID);
                    }
                }
                if (subDAO.addUpdatingSubmission(novelID, ua.getUserID(), "update", novelDraftID)) {
                    message = "Send update requirement successfully!";
                } else {
                    message = "Error in sending update requirement!!!!";
                }
            } else {
                message = "Error in creating novel!!!!";
            }
//            Novel nv = new Novel();
//            nv.setNovelID(novelID);
//            nv.setNovelName(novelName);
//            nv.setImageURL(imageURL);
//            nv.setNovelDescription(novelDescription);
//            nv.setTotalChapter(totalChapter);

//            if (nDAO.updateNovel(nv) && genDAO.deleteGenreNovel(novelID)) {
//                if (genreList != null) {
//                    for (int i = 0; i < genreList.length; i++) {
//                        genDAO.addGenreNovel(Integer.parseInt(genreList[i]), novelID);
//                    }
//                }
//                message = "Create novel and send posting requirement successfully!";
//            } else {
//                message = "Error in creating novel!!!!";
//            }

            request.setAttribute("message", message);
            viewMyNovels(request, response);
//            response.sendRedirect("mynovel?action=post");
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getImg(Part filePart) throws IOException {
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
        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

        // Xóa file tạm
        tempFile.delete();

        // Lấy URL ảnh từ Cloudinary
        String imageUrl = (String) uploadResult.get("secure_url");
        return imageUrl;
    }

//    public String getFile(Part filePart) throws IOException {
//        String fileName = filePart.getSubmittedFileName();
//
//        // Lưu file tạm thời
//        File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
//        try ( InputStream input = filePart.getInputStream();  FileOutputStream output = new FileOutputStream(tempFile)) {
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = input.read(buffer)) != -1) {
//                output.write(buffer, 0, bytesRead);
//            }
//        }
//
//        // Kết nối Cloudinary và upload ảnh
//        Cloudinary cloudinary = CloudinaryUtils.getInstance();
//        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
//            "resource_type", "auto")); // Tự động xác định loại file (hình ảnh, PDF, v.v.)
//
//        // Xóa file tạm
//        tempFile.delete();
//
//         // Lấy URL file từ Cloudinary
//        String fileUrl = (String) uploadResult.get("secure_url");
//        return fileUrl;
//    }
    private void deleteNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int novelID = Integer.parseInt(request.getParameter("novelID"));

        NovelDAO nDAO = new NovelDAO();

        String message;
        try {
            if (nDAO.deleteNovel(novelID)) {
                message = "Delete novel successfully!";
            } else {
                message = "Error in delete novel!!!!";
            }

            request.setAttribute("message", message);
            viewMyNovels(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
