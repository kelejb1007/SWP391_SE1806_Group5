package controller.staff;

import DAO.ManageChapterDAO;
import DAO.NovelDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try (PrintWriter out = response.getWriter()) {
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
            case "approvechapter":
                approveChapter(request, response);
                break;
            case "lock":
                lockChapter(request, response);
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
            listNovel = nd.getAllActiveNovels("active");
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewNovelsForChapters.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in viewAllNovels: " + ex.getMessage(), ex);
        }
    }

    private void viewAllNovelsForLockedChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        try {
            listNovel = nd.getAllActiveNovels("active");
            request.setAttribute("listNovel", listNovel);
            request.getRequestDispatcher("/WEB-INF/views/staff/ViewNovelsForLockedChapters.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in viewAllNovelsForLockedChapter: " + ex.getMessage(), ex);
        }
    }

    private void viewAllChapters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ManageChapterDAO chapterDAO = new ManageChapterDAO();
        int novelId = getNovelIdFromRequest(request);
        List<ManageChapter> chapters;
        try {
            chapters = chapterDAO.getChaptersByNovelId(novelId); // Chỉ lấy chapter có chapterStatus = 'active'
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
        List<ManageChapter> lockedChapters;
        try {
            lockedChapters = chapterDAO.getAllLockedChapters();
            request.setAttribute("lockedChapters", lockedChapters);
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

    private void approveChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int chapterId = Integer.parseInt(request.getParameter("chapterId"));
        ManageChapterDAO chapterDAO = new ManageChapterDAO();
        HttpSession session = request.getSession(false);
        ManagerAccount manager = (ManagerAccount) session.getAttribute("manager");
        int managerId = manager.getManagerID();
        String message;

        try {
            boolean approveResult = chapterDAO.approveChapter(chapterId, managerId);
            if (approveResult) {
                message = "Chapter approved successfully!";
            } else {
                message = "Failed to approve chapter! (Check if 'approved' is a valid status in the database constraint)";
            }
            request.setAttribute("message", message);
            int novelId = getNovelIdFromRequest(request);
            request.setAttribute("novelId", novelId);
            viewAllChapters(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in approveChapter: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error approving chapter: " + ex.getMessage());
            viewAllChapters(request, response);
        }
    }

    private void lockChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int chapterId;
        try {
            chapterId = Integer.parseInt(request.getParameter("chapterId"));
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid chapter ID.");
            viewAllChapters(request, response);
            return;
        }

        // Mở modal để nhập lockReason
        request.setAttribute("chapterId", chapterId);
        request.getRequestDispatcher("/WEB-INF/views/staff/lockChapter.jsp").forward(request, response);
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
            boolean lockResult = chapterDAO.lockChapter(chapterId, managerId, lockReason);
            if (lockResult) {
                message = "Chapter locked successfully!";
            } else {
                message = "Failed to lock chapter! Chapter may not exist or is already locked.";
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

    private void unlockChapter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int chapterId;
        try {
            chapterId = Integer.parseInt(request.getParameter("chapterId"));
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid chapter ID.");
            viewAllLockedChapters(request, response);
            return;
        }

        ManageChapterDAO chapterDAO = new ManageChapterDAO();
        HttpSession session = request.getSession(false);
        ManagerAccount manager = (ManagerAccount) session.getAttribute("manager");
        int managerId = manager.getManagerID();
        String message;

        try {
            // Kiểm tra trạng thái hiện tại
            String currentStatus = chapterDAO.getCurrentChapterStatus(chapterId);
            if (currentStatus == null) {
                message = "Failed to unlock chapter! Chapter with ID " + chapterId + " does not exist.";
            } else if (!currentStatus.equalsIgnoreCase("inactive")) {
                message = "Failed to unlock chapter! Chapter with ID " + chapterId + " is not in 'inactive' status (current status: " + currentStatus + ").";
            } else {
                boolean unlockResult = chapterDAO.unlockChapter(chapterId, managerId);
                if (unlockResult) {
                    message = "Chapter unlocked successfully!";
                } else {
                    message = "Failed to unlock chapter! An error occurred.";
                }
            }
            request.setAttribute("message", message);
            viewAllLockedChapters(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChapterController.class.getName()).log(Level.SEVERE, "Error in unlockChapter: " + ex.getMessage(), ex);
            request.setAttribute("message", "Error unlocking chapter: " + ex.getMessage());
            viewAllLockedChapters(request, response);
        }
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
            case "processLock":
                processLockChapter(request, response);
                break;
            case "unlock":
                unlockChapter(request, response);
                break;
            default:
                viewAllNovels(request, response);
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