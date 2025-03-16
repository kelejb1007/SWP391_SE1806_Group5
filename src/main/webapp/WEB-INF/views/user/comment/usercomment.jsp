<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Comments</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment/usercomment.css"> 
    </head>
    <body>
        <div class="comments-section">
             <h3>Comments</h3>
        
        <c:choose>
            <c:when test="${not empty comments}">
                <div class="comment-list">
                    <c:forEach var="comment" items="${comments}">
                        <div class="comment-item">
                            <p class="comment-user">${comment.fullName}</p>
                            <p class="comment-content">${comment.content}</p>
                            <p class="comment-date">${comment.commentDate.toString().replace('T', ' ')}</p>

                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-comments">No comments available.</p>
            </c:otherwise>
        </c:choose>
        
        <div class="comment-action">
            <c:choose>
                <c:when test="${not empty user}">
                    <a href="${pageContext.request.contextPath}/comment/addcomment.jsp?novelID=${novelID}" class="btn-add-comment">Add comment</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/Login" class="btn-login">Log in to comment</a>
                </c:otherwise>
            </c:choose>
        </div>
        </div>

    </body>
</html>
