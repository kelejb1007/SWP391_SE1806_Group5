<%-- 
    Document   : addcomment
    Created on : 25 thg 2, 2025, 18:59:30
    Author     : ASUS
--%>

<!-- addcomment.jsp -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="model.UserAccount" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm bình luận</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment/usercomment.css">
</head>
<body>
    <div class="comments-section">
        <h3>Thêm bình luận</h3>

        <%-- Nếu người dùng đã đăng nhập, hiển thị form bình luận --%>
        <c:if test="${not empty sessionScope.user}">
            <form action="comments" method="post">
    <input type="hidden" name="novelID" value="${novel.novelID}">
    <textarea name="commentContent"></textarea>
    <button type="submit" name="action" value="add">Gửi</button>
</form>
   
        </c:if>

        <%-- Nếu chưa đăng nhập, hiển thị thông báo và link đăng nhập --%>
        <c:if test="${empty sessionScope.user}">
            <p>Bạn cần <a href="${pageContext.request.contextPath}/Login">đăng nhập</a> để bình luận.</p>
        </c:if>
    </div>
</body>
</html>
