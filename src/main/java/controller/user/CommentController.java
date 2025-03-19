/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

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
import java.time.LocalDateTime;
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
        
        if (session != null) {
        session.removeAttribute("errorMessage"); // Xóa lỗi cũ trước khi xử lý yêu cầu mới
    }

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String commentIDStr = request.getParameter("commentID");
            String novelIDStr = request.getParameter("novelID");

            if (commentIDStr != null && novelIDStr != null) {
                try {
                    int novelID = Integer.parseInt(novelIDStr);
                    int commentID = Integer.parseInt(commentIDStr);
                    int userID = user.getUserID();
                    if (commentDAO.deleteComment(commentID, userID)) { // Kiểm tra quyền xóa
                        response.sendRedirect("novel-detail?id=" + novelID);
                    } else {
                        response.sendRedirect("errorPage.jsp"); // Handle deletion failure
                    }
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
                    // Chống spam: kiểm tra thời gian comment gần nhất trong session
                    Long lastCommentTime = (Long) session.getAttribute("lastCommentTime");
                    long currentTime = System.currentTimeMillis();

                    if (lastCommentTime != null && (currentTime - lastCommentTime) < 60000) { // 10 giây cooldown
                        session.setAttribute("errorMessage", "You are commenting too fast! Please wait a moment.");
                        response.sendRedirect("novel-detail?id=" + novelID);
                        return;
                    }

                    // Lưu thời gian bình luận gần nhất
                    session.setAttribute("lastCommentTime", currentTime);
                    
                    // Add comment
                    Comment newComment = new Comment();
                    newComment.setUserID(user.getUserID());
                    newComment.setNovelID(novelID);
                    newComment.setContent(content);
                    newComment.setCommentDate(LocalDateTime.now()); // Cập nhật kiểu dữ liệu

                    boolean success = commentDAO.addComment(newComment);
                    if (!success) {
                        session.setAttribute("errorMessage", "Error adding comment. Please try again.");
                    }

                    response.sendRedirect("novel-detail?id=" + novelID);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else if ("update".equals(action)) { // command: cập nhật bình luận
            String commentIDStr = request.getParameter("commentID");
            String novelIDStr = request.getParameter("novelID");
            String newContent = request.getParameter("commentContent");

            if (commentIDStr != null && novelIDStr != null && newContent != null && !newContent.trim().isEmpty()) {
                try {
                    int commentID = Integer.parseInt(commentIDStr);
                    int novelID = Integer.parseInt(novelIDStr);

                    boolean updated = commentDAO.updateComment(commentID, user.getUserID(), newContent);
                    if (updated) {
                        response.sendRedirect("novel-detail?id=" + novelID);
                    } else {
                        response.sendRedirect("errorPage.jsp"); // Trường hợp lỗi cập nhật
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
