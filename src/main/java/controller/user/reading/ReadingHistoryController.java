/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user.reading;

import DAO.ReadingHistoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Novel;
import model.UserAccount;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
@WebServlet(name="ReadingHistoryController", urlPatterns={"/history"})
public class ReadingHistoryController extends HttpServlet {
   
private ReadingHistoryDAO historyDAO;

    @Override
    public void init() throws ServletException {
        historyDAO = new ReadingHistoryDAO();
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
        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            // Hiển thị danh sách yêu thích
            viewHistoryList(request, response);
        } else {
            // Xử lý AJAX request để kiểm tra trạng thái yêu thích
            
        }
    } 
    private void viewHistoryList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        if (user == null) {
            // Chưa đăng nhập, chuyển hướng đến trang đăng nhập
            response.sendRedirect("login"); // Thay "login" bằng URL trang đăng nhập của bạn
            return;
        }

        // Lấy danh sách tiểu thuyết yêu thích từ DAO
        List<Novel> historyNovels = historyDAO.getReadingHistoryNovels(user.getUserID());
        

        // Đặt danh sách vào request attribute
        request.setAttribute("historyNovels", historyNovels);

        // Chuyển hướng đến trang JSP để hiển thị danh sách
        request.getRequestDispatcher("/getGenre?target=/WEB-INF/views/user/reading/history-list.jsp").forward(request, response);
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
