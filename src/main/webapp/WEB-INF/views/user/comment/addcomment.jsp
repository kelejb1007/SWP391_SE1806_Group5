
<%-- 
    Document   : addcomment
    Created on : 25 thg 2, 2025, 18:59:30
    Author     : ASUS
--%>

<!-- addcomment.jsp -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Comment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment/usercomment.css">
    <style>
        .error-message {
            color: red;
            font-weight: bold;
        }
    </style>
    <script>
        function validateComment(event) {
            var commentContent = document.getElementById("commentContent").value.trim();
            var errorMessage = document.getElementById("error-message");

            if (commentContent === "") {
                errorMessage.innerText = "Comment cannot be empty!";
                event.preventDefault();
                return false;
            }

            errorMessage.innerText = "";
            return true;
        }
    </script>
</head>
<body>
    <div class="comments-section">
        <h3>Add Comment</h3>
        <c:if test="${not empty sessionScope.user}">
            <form action="comments" method="post" onsubmit="return validateComment(event)">
                <input type="hidden" name="novelID" value="${novel.novelID}">
                <p id="error-message" class="error-message"></p>
                <textarea id="commentContent" name="commentContent">${param.commentContent}</textarea>
                <button type="submit" name="action" value="add">Submit</button>
            </form>
        </c:if>
        <c:if test="${empty sessionScope.user}">
            <p>You need <a href="${pageContext.request.contextPath}/Login">to log in</a> to comment.</p>
        </c:if>
    </div>
</body>
</html>
