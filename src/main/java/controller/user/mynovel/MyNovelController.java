/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.mynovel;

import DAO.ChapterDAO;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Chapter;
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

    private NovelDAO novelDAO;
    private GenreDAO genreDAO;
    private ChapterDAO chapterDAO; // Thêm ChapterDAO
    private NovelSubmissionDAO novelSubmissionDAO;

    @Override
    public void init() throws ServletException {
        novelDAO = new NovelDAO();
        genreDAO = new GenreDAO();
        chapterDAO = new ChapterDAO(); // Khởi tạo ChapterDAO
        novelSubmissionDAO = new NovelSubmissionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("user") == null) {
            String redirectURL = request.getRequestURI();
            session.setAttribute("redirectURL", redirectURL);
            response.sendRedirect("Login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "mynovel":
                viewMyNovels(request, response);
                break;
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
                request.getRequestDispatcher("/WEB-INF/views/user/mynovel/authorDashboard.jsp").forward(request, response);
        }
    }

    private void viewMyNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        String message = null;
        try {
            HttpSession session = request.getSession(false);
            UserAccount us = (UserAccount) session.getAttribute("user");
            listNovel = nd.getMyNovels(us.getUserID());

            if (listNovel.isEmpty()) {
                message = "No Novels!";
            }
            request.setAttribute("message", message);
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovels.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in Servlet");
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovels.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in SQL");
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovels.jsp").forward(request, response);
        }
    }

    private void viewNovelDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        //--------------------------------------------------------------------
        //Phat-----------------------------------------------------------
        ChapterDAO cd = new ChapterDAO(); // Sử dụng ChapterDAO
        String sortParam = request.getParameter("sort"); // Lấy tham số sort
        //---------------------------------------------------------------
        String novelID_raw = request.getParameter("novelID");
        Novel nl;
        try {
            if (novelID_raw == null) {
                request.setAttribute("popup", "No Novel ID provided");
                viewMyNovels(request, response);
            } else {
                int novelID = Integer.parseInt(novelID_raw);
                nl = nd.getNovelByID(novelID);
                if (nl == null) {
                    request.setAttribute("popup", "Novel not found");
                    viewMyNovels(request, response);
                } else {

                    //Phat-------------------------------------------
                    // Lấy danh sách chapter
                    List<Chapter> chapters = cd.getChaptersByNovelId(novelID, null); // Danh sách gốc
                    List<Chapter> sortedChapters = new ArrayList<>(chapters); // Bản sao để sắp xếp

                    // Sắp xếp danh sách chapter nếu có tham số sort
                    if (sortParam != null && !sortParam.isEmpty() && chapters.size() > 1) {
                        sortedChapters = cd.getChaptersByNovelId(novelID, sortParam);
                    }

                    // Tính toán timeString cho mỗi chapter
                    for (Chapter chapter : sortedChapters) {
                        String timeString = getTimeElapsed(chapter.getChapterCreatedDate());
                        chapter.setTimeString(timeString);
                    }
                    for (Chapter chapter : chapters) {
                        String timeString = getTimeElapsed(chapter.getChapterCreatedDate());
                        chapter.setTimeString(timeString);
                    }

                    request.setAttribute("chapters", chapters); // Danh sách gốc
                    request.setAttribute("sortedChapters", sortedChapters); // Danh sách đã sắp xếp
                    request.setAttribute("sort", sortParam != null ? sortParam : "asc");
                    //------------------------------------------------

                    request.setAttribute("novel", nl);
                    request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovelDetail.jsp").forward(request, response);
                }

            }
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in Servlet");
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovelDetail.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Invalid number format for NovelID");
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovelDetail.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in SQL");
            request.getRequestDispatcher("/WEB-INF/views/user/mynovel/myNovelDetail.jsp").forward(request, response);
        }
    }

    private String getTimeElapsed(LocalDateTime chapterCreatedDate) {
        if (chapterCreatedDate == null) {
            return "N/A";
        }
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(chapterCreatedDate, now);
        long diffMillis = duration.toMillis();
        if (diffMillis / (1000 * 60 * 60 * 24 * 365) >= 1) {
            long yearsAgo = diffMillis / (1000 * 60 * 60 * 24 * 365);
            return yearsAgo + " years ago";
        } else if (diffMillis / (1000 * 60 * 60 * 24) >= 1) {
            long daysAgo = diffMillis / (1000 * 60 * 60 * 24);
            return daysAgo + " days ago";
        } else if (diffMillis / (1000 * 60 * 60) >= 1) {
            long hoursAgo = diffMillis / (1000 * 60 * 60);
            return hoursAgo + " hours ago";
        } else {
            long minutesAgo = diffMillis / (1000 * 60);
            return minutesAgo + " minutes ago";
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
        NovelSubmissionDAO subDAO = new NovelSubmissionDAO();

        List<Genre> genreList;
        Novel nl;
        String genreOfNovel;
        int novelID = Integer.parseInt(request.getParameter("novelID"));
        try {
            if (subDAO.checkPending(novelID)) {
                request.setAttribute("popup", "You have an update waiting for approving!");
                viewMyNovels(request, response);
            } else {
                nl = nDAO.getNovelByID(novelID);
                genreOfNovel = genDAO.getGenreByNovelID(novelID);
                genreList = genDAO.getAllGenres();

                request.setAttribute("novel", nl);
                request.setAttribute("genreOfNovel", genreOfNovel);
                request.setAttribute("genreList", genreList);
                request.getRequestDispatcher("/WEB-INF/views/user/mynovel/updates.jsp").forward(request, response);
            }
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
            if (list.isEmpty()) {
                message = "No Submissions!";
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
        String[] genreChosen = request.getParameterValues("genreList");
        String imageURL = request.getParameter("file_hidden");
        Part filePart = request.getPart("imageURL");

        NovelDAO nDAO = new NovelDAO();
        NovelSubmissionDAO subDAO = new NovelSubmissionDAO();
        GenreDAO genDAO = new GenreDAO();

        int novelID;
        String message;
        String genres;
        try {
            HttpSession session = request.getSession(false);
            UserAccount ua = (UserAccount) session.getAttribute("user");
            if (filePart != null && filePart.getSize() > 0) {
                imageURL = getImg(filePart);
            }

            Novel nl = new Novel(novelName, ua.getUserID(), imageURL, novelDescription, totalChapter, "pending");

            //check novelName
            if (nDAO.checkNovelName(novelName)) {
                genres = (genreChosen != null) ? String.join(", ", genreChosen) : "";
                request.setAttribute("popup", "The novel's name has existed");
                request.setAttribute("novel", nl);
                request.setAttribute("genreOfNovel", genres);
                showPostingForm(request, response);
            } else {
                novelID = nDAO.addNovel(nl);
                if (novelID != -1) {
                    if (genreChosen != null) {
                        for (int i = 0; i < genreChosen.length; i++) {
                            genDAO.addGenreNovel(Integer.parseInt(genreChosen[i]), novelID);
                        }
                    }

                    NovelSubmission ns = new NovelSubmission();
                    ns.setNovelID(novelID);
                    ns.setUserID(ua.getUserID());
                    ns.setType("post");
                    if (subDAO.addPostingSubmission(ns)) {
                        message = "Create novel and send posting requirement successfully!";
                    } else {
                        message = "Error in sending posting requirement!!!!";
                    }
                } else {
                    message = "Error in creating novel!!!!";
                }

                request.setAttribute("popup", message);
                showPostingForm(request, response);
//            response.sendRedirect("mynovel?action=post");
            }
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

            //check novelName
            if (nDAO.checkNovelNameForUpdate(novelName, novelID)) {
                request.setAttribute("popup", "The novel's name has existed");
                showUpdateForm(request, response);
            } else {

                novelDraftID = nDAO.addNovel(nl);
                if (novelDraftID != -1) {
                    if (genreList != null) {
                        for (int i = 0; i < genreList.length; i++) {
                            genDAO.addGenreNovel(Integer.parseInt(genreList[i]), novelDraftID);
                        }
                    }

                    NovelSubmission ns = new NovelSubmission();
                    ns.setNovelID(novelID);
                    ns.setUserID(ua.getUserID());
                    ns.setType("update");
                    ns.setDraftID(novelDraftID);
                    if (subDAO.addUpdatingSubmission(ns)) {
                        message = "Send update requirement successfully!";
                    } else {
                        message = "Error in sending update requirement!!!!";
                    }
                } else {
                    message = "Error in creating novel!!!!";
                }

                request.setAttribute("popup", message);
                viewMyNovels(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getImg(Part filePart) throws IOException {
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

            request.setAttribute("popup", message);
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
