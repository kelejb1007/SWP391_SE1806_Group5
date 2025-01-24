<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="components/header.jsp" />

    <body>
       
<jsp:include page="components/login.jsp" />
    <!-- Search Bar -->
    <div class="container h-100">
        <div class="d-flex justify-content-center h-100">
            <div class="searchbar">
                <input class="search_input" type="text" placeholder="Search...">
                <a href="#" class="search_icon"><i class="fas fa-search"></i></a>
                
            </div>
        </div>
    </div>
    


    <!-- Hot Novels Carousel -->
    <div class="container mt-4">
        <div id="hotNovelsCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="https://via.placeholder.com/300x300" class="d-block" alt="Novel 1">
                    <div class="carousel-content">
                        <h5>New Eden: Live to Play, Play to Live</h5>
                        <p>In a world of constant competition, one man aims for the top. In this new genre VRMMORPG, he plans on becoming the strongest at all costs.</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="https://via.placeholder.com/300x300" class="d-block" alt="Novel 2">
                    <div class="carousel-content">
                        <h5>Adventure Awaits</h5>
                        <p>Join the thrilling journey of a young hero in a magical realm filled with mysteries and challenges.</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="https://via.placeholder.com/300x300" class="d-block" alt="Novel 3">
                    <div class="carousel-content">
                        <h5>Romance in Paris</h5>
                        <p>A heartwarming tale of love, loss, and redemption set in the beautiful streets of Paris.</p>
                    </div>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#hotNovelsCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#hotNovelsCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>


   <!-- Latest Updates and Rankings -->
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-8">
                <h4>Latest Updates</h4>
                <div class="latest-updates">
                    <c:forEach var="novel" items="${latestUpdates}">
                        <div class="novel">
                            <h5>${novel.name}</h5>
                            <span>Genre: ${novel.genreOrRating} | Latest Chapter: ${novel.latestChapterOrImage} | Updated: ${novel.updatedOrTime}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-4">
                <div class="ranking-list">
                    <h4>Top Rated Novels</h4>
                    <c:forEach var="novel" items="${topRatedNovels}">
                        <div class="item">
                            <img src="${novel.latestChapterOrImage}" alt="${novel.name}">
                            <div class="info">
                                <strong>${novel.name}</strong>
                                <span>Rating: ${novel.genreOrRating}</span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    


    <jsp:include page="components/footer.jsp" />