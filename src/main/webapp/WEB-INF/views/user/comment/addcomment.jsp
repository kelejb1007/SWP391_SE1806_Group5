
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

        <style>

            body {
                font-family: 'Arial', sans-serif;
                background-color: #f4f6f9;
                margin: 0;
                padding: 20px;
                min-height: 100vh;
            }

            .comments-section {
                width: 100%; /* Chiếm toàn bộ chiều rộng container cha */
                background: #ffffff;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0 2px 15px rgba(0, 0, 0, 0.05);
            }

            h3 {

                color: #2c3e50;
                margin-bottom: 25px;
                font-size: 24px;
                font-weight: 600;
            }

            #commentContent {
                width: 100%;
                min-height: 120px;
                padding: 12px;
                font-size: 15px;
                border: 1px solid #ddd;
                border-radius: 6px;
                resize: vertical;
                outline: none;
                box-sizing: border-box;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            #commentContent:focus {
                border-color: #3498db;
                box-shadow: 0 0 10px rgba(52, 152, 219, 0.1);
            }

            .error-message {
                color: #e74c3c;
                font-size: 14px;
                font-weight: 500;
                margin: 10px 0;
                text-align: left;
                min-height: 20px;
            }

            button[type="submit"] {
                width: 100%;
                padding: 12px;
                background-color: #3498db;
                color: #ffffff;
                font-size: 16px;
                font-weight: 500;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.1s ease;
            }

            button[type="submit"]:hover {
                background-color: #2980b9;
            }

            button[type="submit"]:active {
                transform: scale(0.98);
            }

            .login-message {
                text-align: center;
                margin-top: 20px;
                color: #7f8c8d;
                font-size: 14px;
            }

            .login-message a {
                color: #3498db;
                text-decoration: none;
                font-weight: 600;
                transition: color 0.3s ease;
            }

            .login-message a:hover {
                color: #2980b9;
                text-decoration: underline;
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .comments-section {
                    padding: 15px;
                }

                h3 {
                    font-size: 20px;
                }

                #commentContent {
                    min-height: 100px;
                    font-size: 14px;
                }

                button[type="submit"] {
                    font-size: 15px;
                    padding: 10px;
                }
            }


            .error-message {
                color: red;
                font-weight: bold;
            }
        </style>
        <script>
            function validateComment(event) {
                var commentContent = document.getElementById("commentContent").value.trim();
                var errorMessage = document.getElementById("error-message");
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

                    <textarea id="commentContent" name="commentContent" placeholder="Write your comment here...">${param.commentContent}</textarea>
                    <button type="submit" name="action" value="add">Send</button>

                </form>
            </c:if>
            <c:if test="${empty sessionScope.user}">
                <p>You need <a href="${pageContext.request.contextPath}/Login">to log in</a> to comment.</p>
            </c:if>
        </div>
    </body>
</html>
