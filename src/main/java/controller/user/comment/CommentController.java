/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.comment;

import DAO.CommentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Comment;
import model.UserAccount;

@WebServlet(name = "CommentController", urlPatterns = {"/comments"})
public class CommentController extends HttpServlet {

    private CommentDAO commentDAO;
    private String commentContent;

    @Override
    public void init() throws ServletException {
        commentDAO = new CommentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String novelIDStr = request.getParameter("id");
        if (novelIDStr == null) {
            response.sendRedirect("homepage"); // Nếu thiếu novelID, quay về trang chính
            return;
        }

        int novelID;
        try {
            novelID = Integer.parseInt(novelIDStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("homepage"); // Nếu có lỗi, quay về trang chính
            return;
        }

        // Lấy danh sách bình luận theo novelID
        List<Comment> comments = commentDAO.getCommentsByNovelId(novelID);
        request.setAttribute("comments", comments);
        request.setAttribute("novelID", novelID);
        System.out.println("List comment:");
        for (Comment comment : comments) {
            System.out.println(comment.toString()); // Giả sử class Comment có phương thức toString()
        }
        request.getRequestDispatcher("/WEB-INF/views/user/comment/usercomment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserAccount user = (session != null) ? (UserAccount) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String commentIDStr = request.getParameter("commentID");
            if (commentIDStr != null) {
                try {
                    int commentID = Integer.parseInt(commentIDStr);
                    commentDAO.deleteComment(commentID);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else if ("add".equals(action)) {
            String novelIDStr = request.getParameter("novelID"); // "id" từ URL
            if (novelIDStr != null && !novelIDStr.trim().isEmpty()) {
                request.setAttribute("novelID", novelIDStr);
            } else {
                request.setAttribute("novelID", "0"); // Tránh null
            }
            
            String content = request.getParameter("commentContent");

            if (novelIDStr == null || novelIDStr.trim().isEmpty()) {
                response.sendRedirect("errorPage.jsp"); // Hoặc trang thông báo lỗi
                return;
            }

            if (content != null && !content.trim().isEmpty()) {
                try {
                    int novelID = Integer.parseInt(novelIDStr);
                    Comment newComment = new Comment();
                    newComment.setUserID(user.getUserID());
                    newComment.setNovelID(novelID);
                    newComment.setContent(content);
                    newComment.setCommentDate(new java.util.Date());

                    commentDAO.addComment(newComment);
                    response.sendRedirect("novel-detail?id=" + novelID);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
