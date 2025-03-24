<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Novel List</title>

        <link rel="stylesheet" href="css/home/novel-list.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/awe-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="css/home/filter.css">
    <body class="g_site_readnovel" style="zoom: 1;">
        <div class="wrap home">
            <jsp:include page="/WEB-INF/views/user/components/header2.jsp" /> 


            <div id="wrapper">
                <div id="page-wrapper">
                    <div class="container-fluid">
                        <div class="row">
                            <main class="novel-list-page">
                                <jsp:include page="/WEB-INF/views/user/components/link.jsp"></jsp:include>
                                    <div class="content-area">
                                    <jsp:include page="/WEB-INF/views/user/components/sidebar.jsp"></jsp:include>
                                        <div class="novel-list-container">

                                        <jsp:include page="/WEB-INF/views/user/components/fitter.jsp"></jsp:include>
                                        <c:if test="${not empty searchQuery && not empty novels}">
                                            <h2>Search Results for: <c:out value="${searchQuery}" /></h2>
                                        </c:if>

                                        <c:if test="${empty novels && not empty searchQuery}">
                                            <h2>No result found for query "${searchQuery}"</h2>
                                        </c:if>
                                        <div class="novel-grid">
                                            <c:if test="${empty novels}">
                                                <p>No novels found.</p>
                                            </c:if>
                                            <c:forEach var="novel" items="${novels}">
                                                <div class="novel-item">

                                                    <span class="novel-rating">${novel.averageRating != null ? String.format("%.2f", novel.averageRating) : "N/A"}</span>
                                                    <a href="novel-detail?id=${novel.novelID}">
                                                        <div class="novel-cover">
                                                            <img src="${novel.imageURL}" alt="${novel.novelName}">
                                                        </div>
                                                        <h3 class = "novel-title">${novel.novelName}</h3>
                                                    </a>
                                                    <p class = "novel-author">By: ${novel.author}</p>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </main>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>
        <script src="js/home/sidebar.js"></script>

    </body>
</html>