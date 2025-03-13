package controller.staff.managechapter;

import DAO.ChapterDAO;
import DAO.ChapterSubmissionDAO;
import DAO.LockChapterLogDAO;
import DAO.LockNovelLogDAO;
import DAO.ManageChapterDAO;
import DAO.NovelDAO;
import controller.staff.managenovel.ManageNovelController;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Chapter;
import model.ChapterSubmission;
import model.LockChapterLog;
import model.LockNovelLog;
import model.ManagerAccount;
import model.Novel;
import model.ManageChapter;

/**
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebServlet(name = "ManageChapterController", urlPatterns = {"/managechapter"})
public class ManageChapterController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageChapterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageChapterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("ManagerLogin");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "viewallchapters":
                viewAllChapters(request, response);
                break;
            case "viewlocked":
                viewAllLockedChapters(request, response);
                break;
            case "allnovelforlockedchapter":
                viewAllNovelsForLockedChapter(request, response);
                break;
            case "viewlockedlist":
                viewLockedChaptersOfNovel(request, response);
                break;
            case "viewsubmisson":
                viewAllSubmission(request, response);
                break;
            case "viewchapter":
                viewChapter(request, response);
                break;
            default:
                viewAllNovels(request, response);
        }
    }

    private void viewAllNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        try {
            listNovel = nd.getNovelByStatus("active");
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewNovelsForChapters.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in viewAllNovels: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error loading novels: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewNovelsForChapters.jsp").forward(request, response);
        }
    }

    private void viewAllNovelsForLockedChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        try {
            listNovel = nd.getNovelByStatus("active");
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewNovelsForLockedChapters.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in viewAllNovelsForLockedChapter: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error loading novels for locked chapters: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewNovelsForLockedChapters.jsp").forward(request, response);
        }
    }

    private void viewAllChapters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ManageChapterDAO chapterDAO = new ManageChapterDAO();
        int novelId = getNovelIdFromRequest(request);
        List<ManageChapter> chapters;
        try {
            chapters = chapterDAO.getChaptersByNovelId(novelId);
            request.setAttribute("chapters", chapters);
            request.setAttribute("novelId", novelId);
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in viewAllChapters: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error loading chapters: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewAllChapters.jsp").forward(request, response);
        }
    }

    private void viewAllLockedChapters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ManageChapterDAO chapterDAO = new ManageChapterDAO();
        int novelId = getNovelIdFromRequest(request);
        List<ManageChapter> lockedChapters;
        try {
            lockedChapters = chapterDAO.getAllLockedChapters();
            request.setAttribute("lockedChapters", lockedChapters);
            request.setAttribute("novelId", novelId);
            request.getRequestDispatcher("/WEB-INF/views/staff/viewLockedChapter.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in viewAllLockedChapters: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error loading all locked chapters: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/staff/viewLockedChapter.jsp").forward(request, response);
        }
    }

    private void viewLockedChaptersOfNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ManageChapterDAO chapterDAO = new ManageChapterDAO();
        int novelId = getNovelIdFromRequest(request);
        List<ManageChapter> lockedChapters;
        try {
            lockedChapters = chapterDAO.getLockedChaptersByNovelId(novelId);
            request.setAttribute("lockedChapters", lockedChapters);
            request.setAttribute("novelId", novelId);
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewLockedChaptersOfNovel.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in viewLockedChaptersOfNovel: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error loading locked chapters for novel ID " + novelId + ": " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewLockedChaptersOfNovel.jsp").forward(request, response);
        }
    }

    private void viewAllSubmission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChapterSubmissionDAO cs = new ChapterSubmissionDAO();
        List<ChapterSubmission> list;
        String listMes = null;
        try {
            list = cs.getAllSubmisstion();
            if (list.isEmpty()) {
                listMes = "nulllll";
            }
            request.setAttribute("listMes", listMes);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/WEB-INF/views/staff/pendingChapters.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processRejectChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int chapterId;
        try {
            chapterId = Integer.parseInt(request.getParameter("chapterId"));
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid chapter ID.");
            viewAllSubmission(request, response);
            return;
        }

        String rejectReason = request.getParameter("rejectReason");
        ManageChapterDAO chapterDAO = new ManageChapterDAO();
        HttpSession session = request.getSession(false);
        ManagerAccount manager = (ManagerAccount) session.getAttribute("manager");
        int managerId = manager.getManagerID();
        String message;

        try {
            String currentStatus = chapterDAO.getCurrentChapterStatus(chapterId);
            if (currentStatus == null) {
                message = "Failed to reject chapter! Chapter with ID " + chapterId + " does not exist.";
            } else if (!currentStatus.equalsIgnoreCase("pending")) {
                message = "Failed to reject chapter! Chapter with ID " + chapterId + " is not in 'pending' status (current status: " + currentStatus + ").";
            } else {
                boolean rejectResult = chapterDAO.rejectChapter(chapterId, managerId, rejectReason);
                if (rejectResult) {
                    message = "Chapter rejected successfully! Status set to 'rejected'.";
                } else {
                    message = "Failed to reject chapter! An error occurred.";
                }
            }
            request.setAttribute("message", message);
            viewAllSubmission(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in processRejectChapter: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error rejecting chapter: " + ex.getMessage());
            viewAllSubmission(request, response);
        }
    }

    private void processLockChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int chapterId;
        try {
            chapterId = Integer.parseInt(request.getParameter("chapterId"));
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid chapter ID.");
            viewAllChapters(request, response);
            return;
        }

        String lockReason = request.getParameter("lockReason");
        ManageChapterDAO chapterDAO = new ManageChapterDAO();
        HttpSession session = request.getSession(false);
        ManagerAccount manager = (ManagerAccount) session.getAttribute("manager");
        int managerId = manager.getManagerID();
        String message;

        try {
            String currentStatus = chapterDAO.getCurrentChapterStatus(chapterId);
            if (currentStatus == null) {
                message = "Failed to lock chapter! Chapter with ID " + chapterId + " does not exist.";
            } else if (!currentStatus.equalsIgnoreCase("active")) {
                message = "Failed to lock chapter! Chapter with ID " + chapterId + " is not in 'active' status (current status: " + currentStatus + ").";
            } else {
                boolean lockResult = chapterDAO.lockChapter(chapterId, managerId, lockReason);
                if (lockResult) {
                    message = "Chapter locked successfully! Status set to 'locked'.";
                } else {
                    message = "Failed to lock chapter! An error occurred.";
                }
            }
            request.setAttribute("message", message);
            int novelId = getNovelIdFromRequest(request);
            request.setAttribute("novelId", novelId);
            viewAllChapters(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in processLockChapter: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error locking chapter: " + ex.getMessage());
            viewAllChapters(request, response);
        }
    }

    private void viewChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChapterDAO chapterDAO = new ChapterDAO();

        String chapterIdParam = request.getParameter("id");

        if (chapterIdParam == null || chapterIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id parameter");
            return;
        }
        int chapterId = Integer.parseInt(chapterIdParam);
        Chapter chapter = chapterDAO.getChapterForStaffById(chapterId);
        String chapterContent = getChapterContent(chapter, request);
        request.setAttribute("chapter", chapter);
        request.setAttribute("chapterContent", chapterContent);
        request.getRequestDispatcher("/WEB-INF/views/staff/chapterContent.jsp").forward(request, response);

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
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("ManagerLogin");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "lock":
                lockChapter(request, response);
                break;
            case "unlock":
                unlockChapter(request, response);
                break;
            case "approve":
                approveChapter(request, response);
                break;
            case "reject":
                rejectChapter(request, response);
                break;
            default:
                viewAllNovels(request, response);
        }
    }

    private void lockChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int chapterID = Integer.parseInt(request.getParameter("chapterID"));
        String lockReason = request.getParameter("lockReason");
        ManageChapterDAO cd = new ManageChapterDAO();
        LockChapterLogDAO ld = new LockChapterLogDAO();
        Boolean checkChange;
        Boolean checkAdd;
        String message;
        try {
            HttpSession session = request.getSession(false);
            ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

            LockChapterLog ll = new LockChapterLog(ma.getManagerID(), chapterID, "lock", lockReason);

            checkChange = cd.changeChapterStatus(chapterID, "locked");
            checkAdd = ld.addLockLog(ll);

            if (checkChange && checkAdd) {
                message = "Lock successfully!";
            } else {
                message = "Error!!!!";
            }
            request.setAttribute("message", message);
            viewAllChapters(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void unlockChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //int novelID = Integer.parseInt(request.getParameter("novelID"));
        int chapterID = Integer.parseInt(request.getParameter("chapterID"));
        ManageChapterDAO cd = new ManageChapterDAO();
        LockChapterLogDAO ld = new LockChapterLogDAO();
        Boolean checkChange;
        Boolean checkAdd;
        String message;
        try {
            HttpSession session = request.getSession(false);
            ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

            LockChapterLog ll = new LockChapterLog(ma.getManagerID(), chapterID, "unlock", null);

            checkChange = cd.changeChapterStatus(chapterID, "active");
            checkAdd = ld.addLockLog(ll);

            if (checkChange && checkAdd) {
                message = "Unlock successfully!";
            } else {
                message = "Error!!!!";
            }
            request.setAttribute("message", message);
            response.sendRedirect("managechapter?action=viewlocked");
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void approveChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int submissionCID = Integer.parseInt(request.getParameter("submissionCID"));
        int chapterID = Integer.parseInt(request.getParameter("chapterID"));
        int draftID = Integer.parseInt(request.getParameter("draftID"));
        String type = request.getParameter("type");
        ChapterSubmissionDAO cSubDAO = new ChapterSubmissionDAO();
        ManageChapterDAO cDAO = new ManageChapterDAO();
        String message;
        try {
            HttpSession session = request.getSession(false);
            ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

            ChapterSubmission cs = new ChapterSubmission();
            cs.setSubmissionCID(submissionCID);
            cs.setManagerID(ma.getManagerID());
            cs.setStatus("approved");

            if (type.equals("post")) {
                cSubDAO.updateSubmission(cs);
                cDAO.changeChapterStatus(chapterID, "active");
                cDAO.updatePublicDate(chapterID);
            } else {
                cSubDAO.updateSubmission(cs);
                cDAO.updateChapter(cDAO.getChapterById(draftID), chapterID);
            }

            viewAllSubmission(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void rejectChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int submissionCID = Integer.parseInt(request.getParameter("submissionCID"));
        int chapterID = Integer.parseInt(request.getParameter("chapterID"));
        String type = request.getParameter("type");
        String rejectReason = request.getParameter("rejectReason");
        ChapterSubmissionDAO nSubDAO = new ChapterSubmissionDAO();
        ManageChapterDAO cDAO = new ManageChapterDAO();
        String message;
        try {
            HttpSession session = request.getSession(false);
            ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

            ChapterSubmission cs = new  ChapterSubmission();
            cs.setSubmissionCID(submissionCID);
            cs.setManagerID(ma.getManagerID());
            cs.setStatus("rejected");
            cs.setReasonRejected(rejectReason);

            if (type.equals("post")) {
                nSubDAO.updateSubmission(cs);
                cDAO.changeChapterStatus(chapterID, "rejected");
            } else {
                nSubDAO.updateSubmission(cs);
            }

//            request.setAttribute("message", message);
            viewAllSubmission(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getNovelIdFromRequest(HttpServletRequest request) {
        String novelIdParam = request.getParameter("novelId");
        try {
            return novelIdParam != null ? Integer.parseInt(novelIdParam) : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    @Override
    public String getServletInfo() {
        return "Controller for managing novels and chapters by staff";
    }
}
