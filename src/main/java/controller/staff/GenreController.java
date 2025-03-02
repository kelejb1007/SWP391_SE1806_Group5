/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staff;


import DAO.GenreDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import model.Genre;

/**
 *
 * @author Admin LienXuanThinh_ce182117
 */
@WebServlet(name = "ManageGenreController", urlPatterns = {"/managegenre"})
public class GenreController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GenreController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GenreController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         GenreDAO genreDAO = new GenreDAO();
        String action = request.getParameter("action");
        if (action == null) {
            // Mặc định: hiển thị danh sách Genre
            try {
                List<Genre> listGenre = genreDAO.getAllGenres();
                request.setAttribute("listGenre", listGenre);
                request.getRequestDispatcher("/WEB-INF/views/staff/viewAllGenres.jsp").forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading genres: " + ex.getMessage());
            }
        } else if (action.equals("add")) {
            // Hiển thị form thêm genre
            request.getRequestDispatcher("/WEB-INF/views/staff/addGenre.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            // Xử lý xóa Genre theo id truyền vào
            String idStr = request.getParameter("id");
            try {
                int genreId = Integer.parseInt(idStr);
                genreDAO.deleteGenre(genreId);
                // Sau khi xóa, chuyển về trang danh sách
                response.sendRedirect(request.getContextPath() + "/managegenre");
            } catch (NumberFormatException ex) {
                Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Genre ID");
            }
        }
    }


    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
    GenreDAO genreDAO = new GenreDAO();
    if (action != null && action.equals("add")) {
        // Lấy dữ liệu từ form
        String genreName = request.getParameter("genreName");
        
        // Kiểm tra đầu vào: không được null, không rỗng và chỉ chứa chữ và khoảng trắng
        if (genreName == null || genreName.trim().isEmpty() || !genreName.matches("[A-Za-z\\s]+")) {
            request.setAttribute("errorMessage", "Invalid genre name. Only letters and spaces are allowed.");
            request.getRequestDispatcher("/WEB-INF/views/staff/addGenre.jsp").forward(request, response);
            return;
        }
        
        Genre genre = new Genre();
        genre.setGenreName(genreName.trim());
        genreDAO.addGenre(genre);
        // Sau khi thêm, chuyển về trang danh sách
        response.sendRedirect(request.getContextPath() + "/managegenre");
    } else {
        doGet(request, response);
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
