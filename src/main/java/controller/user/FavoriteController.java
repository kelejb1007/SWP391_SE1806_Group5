/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import DAO.FavoriteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Favorite;
import model.Novel;
import model.UserAccount;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
@WebServlet(name="FavoriteController", urlPatterns={"/favorite"})
public class FavoriteController extends HttpServlet {
   private FavoriteDAO favoriteDAO;

    @Override
    public void init() throws ServletException {
        favoriteDAO = new FavoriteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            // Hiển thị danh sách yêu thích
            viewFavoriteList(request, response);
        } else {
            // Xử lý AJAX request để kiểm tra trạng thái yêu thích
            handleFavoriteStatus(request, response);
        }
    }

    private void viewFavoriteList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        if (user == null) {
            // Chưa đăng nhập, chuyển hướng đến trang đăng nhập
            response.sendRedirect("login"); // Thay "login" bằng URL trang đăng nhập của bạn
            return;
        }

        // Lấy danh sách tiểu thuyết yêu thích từ DAO
        List<Novel> favoriteNovels = favoriteDAO.getFavoriteNovelsByUserId(user.getUserID());

        // Đặt danh sách vào request attribute
        request.setAttribute("favoriteNovels", favoriteNovels);

        // Chuyển hướng đến trang JSP để hiển thị danh sách
        request.getRequestDispatcher("/getGenre?target=/WEB-INF/views/user/favorite/favorite-history.jsp").forward(request, response);
    }

    private void handleFavoriteStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            UserAccount user = (UserAccount) session.getAttribute("user");

            // Kiểm tra user đăng nhập
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("not_logged_in");
                return;
            }

            // Lấy và kiểm tra novelId
            String novelIdParam = request.getParameter("novelId");
            if (novelIdParam == null || novelIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("missing_novelId");
                return;
            }

            int novelId = Integer.parseInt(novelIdParam);
            Favorite favorite = favoriteDAO.getFavoriteByNovelIdAndUserId(novelId, user.getUserID());

            // Trả về trạng thái favorite
            if (favorite != null) {
                out.print("favorite");
            } else {
                out.print("not_favorite");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("invalid_novelId");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            UserAccount user = (UserAccount) session.getAttribute("user");

            // Kiểm tra user đăng nhập
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("not_logged_in");
                return;
            }

            // Lấy và kiểm tra novelId
            String novelIdParam = request.getParameter("novelId");
            if (novelIdParam == null || novelIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("missing_novelId");
                return;
            }

            int novelId = Integer.parseInt(novelIdParam);
            Favorite favorite = favoriteDAO.getFavoriteByNovelIdAndUserId(novelId, user.getUserID());
            boolean success;

            if (favorite == null) {
                // Thêm vào yêu thích
                Favorite newFavorite = new Favorite();
                newFavorite.setUserID(user.getUserID());
                newFavorite.setNovelID(novelId);
                success = favoriteDAO.addFavorite(newFavorite);
            } else {
                // Xóa khỏi yêu thích
                success = favoriteDAO.removeFavorite(favorite);
            }

            if (success) {
                out.print("success");
            } else {
                out.print("fail");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("invalid_novelId");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("error: " + e.getMessage());
        }
    }
}
