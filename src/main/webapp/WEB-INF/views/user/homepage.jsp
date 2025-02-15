<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Online Novel Reader</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous"> 
        <link rel="stylesheet" href="css/style.css">

    </head>
    <body>



        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#"><i class="bi bi-flower1"></i>NovelReader</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="novelListDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi bi-moon-stars-fill"></i>
                                Novel List
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="novelListDropdown">
                                <li><a class="dropdown-item" href="#"><i class="bi bi-stars"></i>Novel Full</a></li>
                                <li><a class="dropdown-item" href="#"><i class="bi bi-stars"></i>Novel Hot </a></li>
                                <li><a class="dropdown-item" href="#"><i class="bi bi-stars"></i>Fantasy</a></li>
                                <li><a class="dropdown-item" href="#"><i class="bi bi-stars"></i>Horror</a></li>
                                <li><a class="dropdown-item" href="#"><i class="bi bi-stars"></i>Adventure</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">

                            <a class="nav-link dropdown-toggle" href="#" id="genreDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi bi-moon-stars-fill"></i>Genres
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="genreDropdown">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Romance</a></li>
                                <li><a class="dropdown-item" href="#">Fantasy</a></li>
                                <li><a class="dropdown-item" href="#">Horror</a></li>
                                <li><a class="dropdown-item" href="#">Adventure</a></li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="javascript:void(0)" onclick="showLoginForm()"><i class="bi bi-person-fill"></i>Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#register">Register</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- Overlay Background -->
        <div class="overlay" id="overlay">
            <!-- Login Form -->
            <div class="login-form" id="loginForm">
                <button class="close-btn" id="closeBtn">&times;</button>
                <h2>Login</h2>
                <form action="processLogin" method="post">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn">Login</button>
                </form>
            </div>
        </div>


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


        <footer class="bg-light text-center py-4 mt-4">
            <p>&copy; 2025 NovelReader. All rights reserved.</p>
        </footer>
        <script src="js/script.js"></script>
    </body>
</html>
