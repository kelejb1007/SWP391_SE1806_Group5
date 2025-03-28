<%-- 
    Document   : viewNovelDetail
    Created on : Feb 16, 2025, 5:48:52 PM
    Author     : Nguyen Thanh Trung
--%>
<%@page import="model.Novel" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Novel</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="stylesheet" href="css/home/header.css">
        <link rel="stylesheet" href="css/novel-detail/novel-detail(d).css">
        <link rel="stylesheet" href="css/novel-detail/rating(d).css">
        <link rel="stylesheet" href="css/novel-detail/chapter-list(d).css">
        <link rel="stylesheet" href="css/novel-detail/top-of-novel(d).css">
        <link rel="stylesheet" href="css/novel-detail/comment(d).css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Macondo+Swash+Caps&display=swap">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="css/home/link.css">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="header.jsp" />
            <jsp:include page="sidebar.jsp" />

            <div id="page-wrapper">
                <main class="novel-detail-page">

                    <div class = "link-container">
                        <div class="breadcrumb">
                            <a href="dashboard" class="breadcrumb-item home-link">
                                <i class="fas fa-home"></i>
                            </a>
                            <span cla<ss="separator">/</span>
                            <c:if test="${not empty genreName}">
                                <a href="novels?genre=${genreName}" class="breadcrumb-item genre-link">${genreName}</a>
                                <span class="separator">/</span>
                            </c:if>
                            <c:if test="${empty genreName}">
                                <a href="<c:url value='/managenovel' />" class="breadcrumb-item genre-link">Novel List</a>
                            </c:if>
                            <span cla<ss="separator">/</span>
                            <a class="current-novel">${novel.novelName}</a>
                        </div>
                    </div>


                    <div class="novel-header">
                        <div class="novel-detail-container">
                            <div class="novel-cover">
                                <div class="original-tag">ORIGINAL</div>
                                <img src="${novel.imageURL}" alt="${novel.novelName}">
                            </div>
                            <div class="novel-info">
                                <h1 class = "novel-title">${novel.novelName}</h1>
                                <div class="novel-meta">
                                    <span class="novel-genre">
                                        <i class="fas fa-book"></i>

                                        <c:if test="${not empty novel.genreNames}">
                                            <c:forEach var="genre" items="${novel.genreNames}" varStatus="status">
                                                <a href="${pageContext.request.contextPath}/novels?genre=${genre}"
                                                   style="color: inherit; text-decoration: none;">
                                                    ${genre}
                                                </a>
                                                <c:if test="${!status.last}">, </c:if>
                                            </c:forEach>
                                        </c:if>

                                    </span>


                                    <span class="novel-chapters"><i class="fas fa-list-ol"></i> ${novel.totalChapter} Chapters</span>
                                    <span class="novel-views"><i class="fas fa-eye"></i>${views}</span>
                                </div>
                                <div class="novel-author">
                                    Author: <span class="author-name">${novel.author}</span>
                                </div>

                                <jsp:include page="/WEB-INF/views/user/reading/rating.jsp" /> 

                                <div class="ranking-award">
                                    <div class="ranking">#3 Originals' Power Ranking</div>
                                    <div class="award"><i class = "fas fa-trophy"></i> WebNovel Spirity Awards 2024 Gold Winner</div>
                                </div>
                                <div class="novel-description">
                                    <p>${novel.novelDescription}</p>
                                </div>
                                <div class="novel-buttons">
                                    <a href="chapter?id=${chapters[0].chapterID}" class="read-button">READ</a>
                                    <jsp:include page="/WEB-INF/views/user/reading/favorite.jsp" /> 
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class = "novel-content">
                        <div class = "chapter-content-section">
                            <div class="g_wrap det-tab-nav mb48 _slide" data-report-l1="14">
                                <a class="_on tab-link" href="#about" data-tab="#about">
                                    <span class="chapter-content-title">About</span>
                                </a>
                                <i class="_hr"></i>
                                <a class="tab-link" href="#chapters" data-tab="#chapters">
                                    <span class="chapter-content-title">Table of Contents</span>
                                </a>
                                <i class="_line" style="width: 87px; left: 0px;"></i>
                            </div>

                            <div id="about" class="tab-content">
                                <p>${novel.novelDescription}</p>
                            </div>

                            <div id="chapters" class="tab-content" style="display: none;">
                                <div class = "chapter-content-header">
                                    <div style = "display: flex; align-items:center;">
                                        <div class="chapter-content-latest-release">
                                            Latest Release:
                                            <c:if test="${not empty chapters}">
                                                <c:set var="lastChapterIndex" value="${chapters.size() - 1}" />
                                                <c:set var="lastChapter" value="${chapters[lastChapterIndex]}" />
                                                <a href="${pageContext.request.contextPath}/chapter?id=${lastChapter.chapterID}">${lastChapter.chapterName}</a> <span class="time-string">${lastChapter.timeString}</span>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="chapter-sort-options">
                                        <button 
                                            class="chapter-sort-option"
                                            data-novel-id="${novel.novelID}"
                                            data-sort="${sort}"
                                            onclick="sortChapters(${novel.novelID}, '${sort == 'asc' ? 'desc' : 'asc'}')"
                                            >
                                            <c:choose>
                                                <c:when test="${sort == 'desc'}">
                                                    <i class="fas fa-sort-numeric-down-alt"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-sort-numeric-up"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </button>
                                    </div>
                                </div>
                                <div class="chapter-list">
                                    <ul class="chapter-list-ul">
                                        <c:if test="${not empty sortedChapters}">
                                            <c:forEach var="chapter" items="${sortedChapters}" varStatus = "status">
                                                <li class="chapter-item">
                                                    <div class = "chapter-item-container">
                                                        <c:choose>
                                                            <c:when test="${not empty user and chapter.chapterNumber > 3}">
                                                                <a href="chapter?id=${chapter.chapterID}" class="chapter-name">
                                                                    <span class="chapter-number">${chapter.chapterNumber}. </span>
                                                                    ${chapter.chapterName}
                                                                </a>
                                                            </c:when>
                                                            <c:when test="${not canViewContent && chapter.chapterNumber > 3}">
                                                                <span class="chapter-name locked">
                                                                    <span class="chapter-number">${chapter.chapterNumber}. </span>
                                                                    ${chapter.chapterName}
                                                                </span>
                                                                <i class="fas fa-lock"></i>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="chapter?id=${chapter.chapterID}" class="chapter-name">
                                                                    <span class="chapter-number">${chapter.chapterNumber}. </span>
                                                                    ${chapter.chapterName}
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <span class ="chapter-time"> ${chapter.timeString} </span>
                                                </li>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${empty chapters}">
                                            <p>No Chapters Available yet</p>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="comment-top-novel-section">
                        <div class="row">
                            <div class="col-lg-8 col-md-7">
                                <section class="comment-section">

                                </section>
                            </div>
                            <div class="col-lg-4 col-md-5">
                                <section class="top-novels">
                                    <jsp:include page="/WEB-INF/views/user/reading/top-of-novel.jsp"></jsp:include>
                                </section>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
        <script src="js/novel-detail/top-of-novel(d).js"></script>
        <script src="js/novel-detail/chapter-list(d).js"></script>
        <script src="js/home/header.js"></script>
        <script src="js/lock.js"></script>
    </body>
</html>
