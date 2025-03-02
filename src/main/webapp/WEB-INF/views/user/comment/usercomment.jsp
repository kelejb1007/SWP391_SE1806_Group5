<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Bình luận</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment/usercomment.css"> 
    </head>
    <body>
        <div class="comments-section">
             <h3>Bình luận</h3>
        
        <c:choose>
            <c:when test="${not empty comments}">
                <div class="comment-list">
                    <c:forEach var="comment" items="${comments}">
                        <div class="comment-item">
                            <p class="comment-user">Người dùng: ${comment.userID}</p>
                            <p class="comment-content">${comment.content}</p>
                            <p class="comment-date">${comment.commentDate}</p>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-comments">Không có bình luận nào.</p>
            </c:otherwise>
        </c:choose>
        
        <div class="comment-action">
            <c:choose>
                <c:when test="${not empty user}">
                    <a href="${pageContext.request.contextPath}/comment/addcomment.jsp?novelID=${novelID}" class="btn-add-comment">Thêm bình luận</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/Login" class="btn-login">Đăng nhập để bình luận</a>
                </c:otherwise>
            </c:choose>
        </div>
        </div>

    </body>
</html>
