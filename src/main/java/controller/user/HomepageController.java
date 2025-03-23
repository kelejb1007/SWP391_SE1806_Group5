/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.NovelDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Novel;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
public class HomepageController extends HttpServlet {

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
            out.println("<title>Servlet HomepageController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomepageController at " + request.getContextPath() + "</h1>");
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

        viewNovels(request, response);

    }

    private void viewNovels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NovelDAO nd = new NovelDAO();
        List<Novel> listNovel3;
        List<Novel> listNovel;
        List<Novel> listrank;
        List<Novel> listHot;
        try {

            listNovel = nd.getNovelsByTimeUpdate();
            for (Novel n : listNovel) {
                String timeString = getTimeElapsed(n.getLatestChapterDate());
                n.setTimeString(timeString);
            }
            listNovel3 = nd.getNovelsByPopularity();
            listrank = nd.getTop10NovelsByMonthlyRating();
            listHot = nd.getTop12NovelsByMonthlyViews();
            request.setAttribute("listrank", listrank);
            request.setAttribute("listNovel3", listNovel3);
            request.setAttribute("listNovel", listNovel);
            request.setAttribute("listHot", listHot);
            request.getRequestDispatcher("/getGenre?target=/WEB-INF/views/user/home2.jsp").include(request, response);

        } catch (Exception ex) {
            Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getTimeElapsed(LocalDateTime chapterCreatedDate) {
        if (chapterCreatedDate == null) {
            return "";
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
        return "Short description";
    }// </editor-fold>

}
