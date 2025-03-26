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
         body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 0;
        }

        .comments-section {
            width: 50%;
            background: #fff;
            padding: 20px;
            margin: 30px auto;
            border-radius: 8px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }

        h3 {
            text-align: center;
            color: #333;
        }

        #commentContent {
            width: 100%;
            height: 100px;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            resize: none;
            outline: none;
            transition: 0.3s;
        }

        #commentContent:focus {
            border-color: #007bff;
            box-shadow: 0px 0px 8px rgba(0, 123, 255, 0.2);
        }

        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 10px;
            text-align: center;
        }

        button[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
        }

        .login-message {
            text-align: center;
            margin-top: 15px;
        }

        .login-message a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }

        .login-message a:hover {
            text-decoration: underline;
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
        <%
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
        session.removeAttribute("errorMessage"); // Xóa ngay sau khi lấy
%>
    <script>
        window.onload = function () {
            alert("<%= errorMessage %>"); // Hiển thị thông báo lỗi
        };
    </script>
<%
    }
%>

    </script>
</head>
<body>
    <div class="comments-section">
        <h3>Add Comment</h3>
        <c:if test="${not empty sessionScope.user}">
            <form action="comments" method="post" onsubmit="return validateComment(event)">
                <input type="hidden" name="novelID" value="${novel.novelID}">
                <p id="error-message" class="error-message"></p>
                <textarea id="commentContent" name="commentContent" placeholder="Write your comment here...">${param.commentContent}</textarea>
                <button type="submit" name="action" value="add">Submit</button>
            </form>
        </c:if>
        <c:if test="${empty sessionScope.user}">
            <p class="login-message">You need <a href="${pageContext.request.contextPath}/Login">to log in</a> to comment.</p>
        </c:if>
    </div>
</body>
</html>