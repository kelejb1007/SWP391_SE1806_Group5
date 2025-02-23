<%--  
    Document   : postChapter.jsp  
    Created on : Feb 23, 2025, 7:05:14 PM  
    Author     : Nguyen Ngoc Phat - CE180321  
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post a New Chapter</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            .form-group {
                margin-bottom: 10px;
            }
            .message {
                font-weight: bold;
                margin-top: 15px;
            }
            .success {
                color: green;
            }
            .error {
                color: red;
            }
        </style>
    </head>
    <body>
        <h2>Post a New Chapter</h2>

        <%-- Lấy Novel ID từ request attribute hoặc parameter --%>
        <%
            String novelId = request.getParameter("novelId");
            if (novelId == null || novelId.isEmpty()) {
                novelId = (request.getAttribute("novelId") != null) ? request.getAttribute("novelId").toString() : "";
            }

            if (novelId.isEmpty()) {
        %>
        <p class="error">Error: Novel ID is required.</p>
        <%
        } else {
        %>

        <form action="<%= request.getContextPath()%>/postChapter" method="post">
            <div class="form-group">
                <label for="novelId">Novel ID:</label>
                <input type="number" id="novelId" name="novelId" value="<%= novelId%>" readonly>
                <input type="hidden" name="novelId" value="<%= novelId%>">
            </div>

            <div class="form-group">
                <label for="chapterNumber">Chapter Number:</label>
                <input type="number" id="chapterNumber" name="chapterNumber" required>
            </div>

            <div class="form-group">
                <label for="chapterName">Chapter Title:</label>
                <input type="text" id="chapterName" name="chapterName" required>
            </div>

            <div class="form-group">
                <label for="fileURL">Chapter File (URL):</label>
                <input type="text" id="fileURL" name="fileURL" required>
            </div>

            <input type="hidden" name="chapterStatus" value="pending">

            <button type="submit">Post Chapter</button>
        </form>

        <%-- Hiển thị thông báo thành công hoặc lỗi --%>
        <%
            String message = (String) request.getAttribute("message");
            String messageType = (request.getAttribute("messageType") != null) ? request.getAttribute("messageType").toString() : "success";

            if (message != null) {
        %>
        <p class="message <%= messageType%>"><%= message%></p>
        <% } %>

        <% }%>
    </body>
</html>

