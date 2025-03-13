/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff.managenovel;

import DAO.GenreDAO;
import DAO.LockNovelLogDAO;
import DAO.NovelDAO;
import DAO.NovelSubmissionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Genre;
import model.LockNovelLog;
import model.ManagerAccount;
import model.Novel;
import model.NovelSubmission;
import model.UserAccount;

/**
 *
 * @author Nguyen Thanh Trung
 */
@WebServlet(name = "ManageNovelController", urlPatterns = {"/managenovel"})
public class ManageNovelController extends HttpServlet {

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
            out.println("<title>Servlet NovelController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NovelController at " + request.getContextPath() + "</h1>");
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
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("ManagerLogin");
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
            case "viewlockedlist":
                viewLockedNovels(request, response);
                break;
            case "viewsubmission":
                viewSubmission(request, response);
                break;
            default:
                viewAllNovels(request, response);
        }
    }

    private void viewAllNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        GenreDAO genDAO = new GenreDAO();
        List<Novel> listNovel = new ArrayList<>();
        List<String> listGenreName;
        String[] genCheckedArr = request.getParameterValues("genChecked");

        try {
            listGenreName = genDAO.getListGenreName();
            if (genCheckedArr != null) {
                List<Novel> listAllNovel = nd.getNovelByStatus("active");

                for (int i = 0; i < listAllNovel.size(); i++) {
                    Novel nl = listAllNovel.get(i);
                    int id = nl.getNovelID();
                    String genreOfNovel = genDAO.getGenreByNovelID(id);

                    for (int j = 0; j < genCheckedArr.length; j++) {
                        if (genreOfNovel.contains(genCheckedArr[j])) {
                            listNovel.add(nl);
                            break;
                        }
                    }
                }
            } else {
                listNovel = nd.getNovelByStatus("active");
            }
            
            String genChecked = (genCheckedArr != null) ? String.join(", ", genCheckedArr) : "";

            if (listNovel.isEmpty()) {
                request.setAttribute("message", "No novels available");
                request.setAttribute("listGenreName", listGenreName);
                request.setAttribute("genChecked", genChecked);
                request.getRequestDispatcher("/WEB-INF/views/staff/allNovels.jsp").forward(request, response);
            } else {
                request.setAttribute("listGenreName", listGenreName);
                request.setAttribute("genChecked", genChecked);
                request.setAttribute("listNovel", listNovel);
                request.getRequestDispatcher("/WEB-INF/views/staff/allNovels.jsp").forward(request, response);
            }
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in Servlet");
            request.getRequestDispatcher("/WEB-INF/views/staff/allNovels.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in SQL");
            request.getRequestDispatcher("/WEB-INF/views/staff/allNovels.jsp").forward(request, response);
        }
    }

    private void viewNovelDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        String novelID_raw = request.getParameter("novelID");
        int novelID;
        Novel nl;
        try {
            if (novelID_raw == null) {
                request.setAttribute("popup", "No Novel ID provided");
                viewAllNovels(request, response);
            } else {
                novelID = Integer.parseInt(novelID_raw);
                nl = nd.getNovelByID(novelID);
                if (nl == null) {
                    request.setAttribute("popup", "Novel not found");
                    viewAllNovels(request, response);
                } else {
                    request.setAttribute("novel", nl);
                    request.getRequestDispatcher("/WEB-INF/views/staff/novelDetail.jsp").forward(request, response);
                }
            }
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in Servlet");
            request.getRequestDispatcher("/WEB-INF/views/staff/novelDetail.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Invalid number format for NovelID");
            request.getRequestDispatcher("/WEB-INF/views/staff/novelDetail.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in SQL");
            request.getRequestDispatcher("/WEB-INF/views/staff/novelDetail.jsp").forward(request, response);
        }
    }

    private void viewLockedNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel;
        String popup = request.getParameter("popup");
        try {
            listNovel = nd.getLockedNovels();
            if (listNovel.isEmpty()) {
                request.setAttribute("message", "No novels available");
                request.getRequestDispatcher("/WEB-INF/views/staff/lockedNovels.jsp").forward(request, response);
            } else {
                request.setAttribute("popup", popup);
                request.setAttribute("listNovel", listNovel);
                request.getRequestDispatcher("/WEB-INF/views/staff/lockedNovels.jsp").forward(request, response);
            }

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in Servlet");
            request.getRequestDispatcher("/WEB-INF/views/staff/lockedNovels.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in SQL");
            request.getRequestDispatcher("/WEB-INF/views/staff/lockedNovels.jsp").forward(request, response);
        }
    }

    private void viewSubmission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelSubmissionDAO ns = new NovelSubmissionDAO();
        List<NovelSubmission> list;
        try {
            list = ns.getAllSubmisstion();
            if (list.isEmpty()) {
                request.setAttribute("message", "No novels available");
                request.getRequestDispatcher("/WEB-INF/views/staff/submission.jsp").forward(request, response);
            } else {
                request.setAttribute("list", list);
                request.getRequestDispatcher("/WEB-INF/views/staff/submission.jsp").forward(request, response);
            }
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in Servlet");
            request.getRequestDispatcher("/WEB-INF/views/staff/submission.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in SQL");
            request.getRequestDispatcher("/WEB-INF/views/staff/submission.jsp").forward(request, response);
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
                lockNovel(request, response);
                break;
            case "unlock":
                unlockNovel(request, response);
                break;
            case "approve":
                approveNovel(request, response);
                break;
            case "reject":
                rejectNovel(request, response);
                break;
            default:
                viewAllNovels(request, response);
        }
    }

    private void lockNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String novelID_raw = request.getParameter("novelID");
        String lockReason = request.getParameter("lockReason");

        NovelDAO nDAO = new NovelDAO();
        LockNovelLogDAO lDAO = new LockNovelLogDAO();

        try {
            int novelID = Integer.parseInt(novelID_raw);

            if (!nDAO.changeNovelStatus(novelID, "locked")) {
                request.setAttribute("popup", "Error in transfering status");
                viewAllNovels(request, response);
            } else {
                HttpSession session = request.getSession(false);
                ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

                LockNovelLog ll = new LockNovelLog(ma.getManagerID(), novelID, "lock", lockReason);
                if (!lDAO.addLockLog(ll)) {
                    nDAO.changeNovelStatus(novelID, "active");
                    request.setAttribute("popup", "Error in adding to Locking Log");
                    viewAllNovels(request, response);
                } else {
                    request.setAttribute("popup", "Lock successfully!");
                    viewAllNovels(request, response);
                }
            }

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in Servlet");
            request.getRequestDispatcher("/WEB-INF/views/staff/allNovels.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Invalid number format for NovelID");
            request.getRequestDispatcher("/WEB-INF/views/staff/allNovels.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in SQL");
            request.getRequestDispatcher("/WEB-INF/views/staff/allNovels.jsp").forward(request, response);
        }
    }

    private void unlockNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String novelID_raw = request.getParameter("novelID");

        NovelDAO nDAO = new NovelDAO();
        LockNovelLogDAO lDAO = new LockNovelLogDAO();
        try {
            int novelID = Integer.parseInt(novelID_raw);

            if (!nDAO.changeNovelStatus(novelID, "active")) {
                request.setAttribute("popup", "Error in transfering status");
                viewLockedNovels(request, response);
            } else {
                HttpSession session = request.getSession(false);
                ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

                LockNovelLog ll = new LockNovelLog(ma.getManagerID(), novelID, "unlock", null);
                if (!lDAO.addLockLog(ll)) {
                    nDAO.changeNovelStatus(novelID, "locked");
                    request.setAttribute("popup", "Error in adding to Locking Log");
                    viewLockedNovels(request, response);
                } else {
//                    viewLockedNovels(request, response);
                    response.sendRedirect("managenovel?action=viewlockedlist&popup=Unlock successfully!");
                }
            }

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in Servlet");
            request.getRequestDispatcher("/WEB-INF/views/staff/lockedNovels.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Invalid number format for NovelID");
            request.getRequestDispatcher("/WEB-INF/views/staff/lockedNovels.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "There are some error in SQL");
            request.getRequestDispatcher("/WEB-INF/views/staff/lockedNovels.jsp").forward(request, response);
        }
    }

    private void approveNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int submissionNID = Integer.parseInt(request.getParameter("submissionNID"));
        int novelID = Integer.parseInt(request.getParameter("novelID"));
        int draftID = Integer.parseInt(request.getParameter("draftID"));
        String type = request.getParameter("type");
        NovelSubmissionDAO nSubDAO = new NovelSubmissionDAO();
        NovelDAO nDAO = new NovelDAO();
        GenreDAO genDAO = new GenreDAO();
        String message;
        try {
            HttpSession session = request.getSession(false);
            ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

            NovelSubmission ns = new NovelSubmission();
            ns.setSubmissionNID(submissionNID);
            ns.setManagerID(ma.getManagerID());
            ns.setStatus("approved");

            if (type.equals("post")) {
                nSubDAO.updateSubmission(ns);
                nDAO.changeNovelStatus(novelID, "active");
                nDAO.updatePublicDate(novelID);
            } else {
                nSubDAO.updateSubmission(ns);
                nDAO.updateNovel(nDAO.getNovelByID(draftID), novelID);
                genDAO.deleteGenreNovel(novelID);
                List<Integer> genres = genDAO.getListGenreIDByNovelID(draftID);
                for (int i = 0; i < genres.size(); i++) {
                    genDAO.addGenreNovel(genres.get(i), novelID);
                }

            }

//            request.setAttribute("message", message);
            viewSubmission(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ManageNovelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rejectNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int submissionNID = Integer.parseInt(request.getParameter("submissionNID"));
        int novelID = Integer.parseInt(request.getParameter("novelID"));
        String type = request.getParameter("type");
        String rejectReason = request.getParameter("rejectReason");
        NovelSubmissionDAO nSubDAO = new NovelSubmissionDAO();
        NovelDAO nDAO = new NovelDAO();
        GenreDAO genDAO = new GenreDAO();
        String message;
        try {
            HttpSession session = request.getSession(false);
            ManagerAccount ma = (ManagerAccount) session.getAttribute("manager");

            NovelSubmission ns = new NovelSubmission();
            ns.setSubmissionNID(submissionNID);
            ns.setManagerID(ma.getManagerID());
            ns.setStatus("rejected");
            ns.setReasonRejected(rejectReason);

            if (type.equals("post")) {
                nSubDAO.updateSubmission(ns);
                nDAO.changeNovelStatus(novelID, "rejected");
            } else {
                nSubDAO.updateSubmission(ns);
            }

//            request.setAttribute("message", message);
            viewSubmission(request, response);

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
