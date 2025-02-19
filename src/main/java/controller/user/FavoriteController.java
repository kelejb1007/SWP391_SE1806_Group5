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
import model.Favorite;
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
