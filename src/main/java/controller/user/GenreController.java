package controller.user;

import DAO.GenreDAO;
import model.Genre;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
@WebServlet(name = "GenreController", urlPatterns = {"/getGenre"})
public class GenreController extends HttpServlet {

    private GenreDAO genreDAO;

    @Override
    public void init() throws ServletException {
        genreDAO = new GenreDAO();
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

        getGenreList(request, response);
    }

    private void getGenreList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //Danh sách Thể Loại(header,sidebar)
            List<Genre> genres = genreDAO.getAllGenres();
            request.setAttribute("genres", genres);
            String selectedGenre = request.getParameter("genre");
            // Nếu không có genre nào được chọn, set nó là null.
            request.setAttribute("selectedGenre", (selectedGenre != null && !selectedGenre.isEmpty()) ? selectedGenre : null);

            // Kiểm tra target
            String target = request.getParameter("target");

            // Kiểm tra target có hợp lệ không để tránh lỗi
            if (target != null && !target.isEmpty()) {
                // Forward request sau khi kiểm tra target
                request.getRequestDispatcher(target).include(request, response);
            } else {
                // Xử lý trường hợp không có target (ví dụ, chuyển hướng đến trang chủ hoặc hiển thị lỗi)
                response.sendRedirect(request.getContextPath() + "/Home.jsp");
            }
        } catch (Exception ex) {
            Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, null, ex);
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
