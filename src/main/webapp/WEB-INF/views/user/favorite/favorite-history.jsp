<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Favorite</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="stylesheet" href="css/favorite/favorite(d).css?v=4">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />


    </head>
    <body class="g_site_readnovel">
        <div class="wrap home">
            <jsp:include page="/WEB-INF/views/user/components/header2.jsp" /> 
            <div>
                <div class="tab-list">
                    <a class="tab-link active" href="#favorites" data-tab="#favorites">
                        <span class="chapter-content-title">Favorite</span>
                    </a>
                    <i class="_hr"></i>
                    <a class="tab-link" href="#history" data-tab="#history">
                        <span class="chapter-content-title">History</span>
                    </a>
                </div>

                <div id="favorites" class="tab-content active">
                    <div class="favorites-container">
                        <h1><i class="fa-solid fa-heart" style="color: #e4798e; font-size: 30px;"></i> Favorite List</h1>
                        <div id="favorites-list">
                            <c:choose>
                                <c:when test="${empty favoriteNovels}">
                                    <div class="no-favorites">
                                        <p>No favorites yet. Go to the <a href="<c:url value='/novels' />">List</a> to find something you love!</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="search-form-container">
                                        <form action="favorite" method="get" id="searchForm" class="favorite-search-form">
                                            <input type="text" name="searchQuery" id="searchQuery" placeholder="Enter a novel name">
                                            <input type="hidden" name="action" value="search">
                                            <button type="submit" class="search-button"><i class="fas fa-search"></i></button>

                                            <c:if test="${not empty errorMessage}">
                                                <p style="color: red;">${errorMessage}</p>
                                            </c:if>
                                        </form>
                                    </div>
                                    <c:forEach var="novel" items="${favoriteNovels}">
                                        <div class="favorite-item">
                                            <img src="${novel.imageURL}" alt="${novel.novelName}">
                                            <div class="item-info">
                                                <a href="novel-detail?id=${novel.novelID}" class="item-name">${novel.novelName}</a>
                                                <p class="item-description">${novel.novelDescription}</p>
                                            </div>
                                            <button class="remove-button favoriteButton" data-novel-id="${novel.novelID}">
                                                <i class="fa-solid fa-heart" style="color: #e4798e;"></i>
                                            </button>
                                        </div>
                                    </c:forEach>

                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <!-- Confirmation popup -->
                <div id="confirmationPopup" class="confirmation-popup">
                    <div class="popup-content">
                        <p>Are you sure you want to remove this item from your favorites?</p>
                        <button class="confirm-button" id="confirmDelete">Remove</button>
                        <button class="cancel-button" id="cancelDelete">Cancel</button>
                    </div>
                </div>
            </div>

            <div id="history" class="tab-content">
                <jsp:include page="/WEB-INF/views/user/reading/history-list.jsp"></jsp:include>
                </div>
            </div>
            <script>

                // Tab management
                document.addEventListener("DOMContentLoaded", function () {
                    const tabLinks = document.querySelectorAll(".tab-link");
                    const tabContents = document.querySelectorAll(".tab-content");

                    tabLinks.forEach(tabLink => {
                        tabLink.addEventListener("click", function (event) {
                            event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết

                            const tabId = this.getAttribute("data-tab");

                            // Nếu là tab History, chuyển hướng đến /history
                            if (tabId === "#history") {
                                window.location.href = "<c:url value='/history'/>";
                                return; // Dừng thực hiện các dòng code tiếp theo
                            }

                            tabLinks.forEach(link => link.classList.remove("active"));
                            tabContents.forEach(content => content.classList.remove("active"));

                            this.classList.add("active");
                            document.getElementById(tabId).classList.add("active");
                        });
                    });
                });


        </script>

        <script src="js/favorite/favorite(d).js?v=2"></script>
    </body>
</html>