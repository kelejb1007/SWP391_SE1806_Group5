<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="model.UserAccount" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chỉnh sửa</title>
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment/usercomment.css">
    </head>
    <body>
        <div class="comments-section">
            <h3>Chỉnh sửa</h3>

            <%-- Nếu người dùng đã đăng nhập, hiển thị form chỉnh sửa --%>
            <c:if test="${not empty sessionScope.user}">
                <c:if test="${not empty comment}">
                    <form action="comments" method="post">
                        <input type="hidden" name="commentID" value="${comment.commentID}">
                        <input type="hidden" name="novelID" value="${novelID}"> 
                        <textarea name="commentContent"><c:out value="${comment.content}"/></textarea>


                        <div class="action-buttons">
                            <button type="submit" name="action" value="edit"> Chỉnh sửa</button>
                            
                        </div>
                    </form>
                </c:if>
            </c:if>

            <%-- Nếu chưa đăng nhập, hiển thị thông báo và link đăng nhập --%>
            <c:if test="${empty sessionScope.user}">
                <p>Bạn cần <a href="${pageContext.request.contextPath}/Login">đăng nhập</a> để chỉnh sửa hoặc xóa bình luận.</p>
            </c:if>
        </div>
    </body>
</html>
