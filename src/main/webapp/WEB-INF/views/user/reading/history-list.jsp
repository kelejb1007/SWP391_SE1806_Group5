<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>

        <link rel="stylesheet" href="css/favorite/history.css?v=3">

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <meta charset="UTF-8">
        <title>Reading History</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
    </head>
    <body class="g_site_readnovel">
        <div class="wrap home">
            <jsp:include page="/WEB-INF/views/user/components/header2.jsp" /> 

            <div class="tab-list">
                <a class="tab-link" href="<c:url value='/favorite'/>">
                    <span class="chapter-content-title">Favorite</span>
                </a>
                <i class="_hr"></i>
                <a class="tab-link active" href="<c:url value='history'/>">
                    <span class="chapter-content-title">History</span>
                </a>
            </div>
            <div class="history-container">
                <h1>
                    
                    Reading History
                </h1>
                <div>
                <c:choose>
                    <c:when test="${not empty historyNovels}">
                        <c:forEach var="novel" items="${historyNovels}">
                            <div class="history-item">

                                <div class="image-container">
                                    <img src="${novel.imageURL}" alt="${novel.novelName}">
                                    <span class="novel-rating">${novel.averageRating != null ? String.format("%.2f", novel.averageRating) : "N/A"}</span>
                                </div>

                                <div class="novel-info">
                                    <a href="novel-detail?id=${novel.novelID}" class="novel-name">${novel.novelName}</a>

                                    <c:choose>
                                        <c:when test="${not empty novel.lastChapterName}">
                                            <a href="chapter?id=${novel.chapterID}" class="last-chapter-link">
                                                Chapter ${novel.lastChapterNumber}: ${novel.lastChapterName}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="chapter?is${novel.chapterID}"  class="last-chapter-link">
                                                Go to Chapter ${novel.lastChapterName}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="chapter-info">
                                        Progress: <strong>${novel.process}</strong> / ${novel.totalChapter}
                                    </div>

                                    <a href="chapter?id=${novel.chapterID}" class="continue-reading-button">Continue Reading<i class="fas fa-arrow-right"></i></a>
                                </div>

                                <span class="timestamp">Last read: ${novel.lastReadDateFormatted}</span>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-history">
                            <p>No reading history yet. Find a novel you love!</p>
                            <a href="<c:url value='/novels' />">Explore Novels</a>
                        </div>
                    </c:otherwise>
                </c:choose>
                    </div>
            </div>
        </div>
    </body>
</html>