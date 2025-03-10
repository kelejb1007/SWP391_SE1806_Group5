<%-- 
    Document   : PostChapterHistory.jsp
    Created on : Mar 1, 2025, 5:18:10 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/home/header.css">
        <title>Chapter Posting History</title>
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <header>
            <jsp:include page="/WEB-INF/views/user/components/header.jsp" /> 
        </header>
        <h2>Chapter Posting History for ${type}</h2>
        <c:if test="${not empty historyList}">
            <table>
                <thead>
                    <tr>
                        <th>Chapter ID</th>
                        <th>Novel ID</th>
                        <th>Novel Name</th>
                        <th>Chapter Number</th>
                        <th>Chapter Name</th>
                        <th>File URL</th>
                        <th>Published Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="chapter" items="${historyList}">
                        <tr>
                            <td>${chapter.chapterID}</td>
                            <td>${chapter.novelID}</td>
                            <td>${chapter.novelName}</td>
                            <td>${chapter.chapterNumber}</td>
                            <td>${chapter.chapterName}</td>
                            <td>${chapter.fileURL}</td>
                            <td>${chapter.chapterCreatedDate}</td>
                            <td>${chapter.chapterStatus}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty historyList}">
            <p>Không có lịch sử đăng chapter nào được tìm thấy.</p>
        </c:if>
    </body>
    <footer>
        <jsp:include page="/WEB-INF/views/user/components/footer.jsp" /> 
    </footer>
</html>
