/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staff.managecomment;

import DAO.CommentDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Comment;
/**
 *
 * @author ASUS
 */
@WebServlet(name="StaffCommentController", urlPatterns={"/staff/comments"})
public class StaffCommentController extends HttpServlet {
    private CommentDAO commentDAO;

    @Override
    public void init() throws ServletException {
        commentDAO = new CommentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("ManagerLogin");
            return;
        }

        List<Comment> comments = commentDAO.getAllComments();
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("/WEB-INF/views/staff/commentList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String commentIDStr = request.getParameter("commentID");
            if (commentIDStr != null) {
                try {
                    int commentID = Integer.parseInt(commentIDStr);
                    commentDAO.deleteCommentByStaff(commentID);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        // Sau khi xóa, load lại danh sách bình luận và hiển thị trên trang
        List<Comment> comments = commentDAO.getAllComments();
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("/WEB-INF/views/staff/commentList.jsp").forward(request, response);
    }
}
