<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>NovelReader</title>
        <link rel="stylesheet" href="css/home/home.css">
         <link rel="stylesheet" href="css/home/header.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Macondo+Swash+Caps&display=swap">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    </head>

    <body>
        <div class="container">
            <header>


                <jsp:include page="/WEB-INF/views/user/components/header.jsp" /> 
            </header>

            <main>
                <!-- Slider Section -->
                <div id="hotNovelsCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <div class="novel-card">
                                <div class="novel-details">
                                    <h3>Novel Title 1</h3>
                                    <p class="author">By Author Name</p>
                                    <p class="description">A brief description of the novel. A brief description of the novel.  A brief description of the novel. </p>
                                </div>
                                <img src="img/a1.jpg" class="novel-image" alt="Novel 1">

                            </div>
                        </div>
                        <div class="carousel-item">
                            <div class="novel-card">
                                <div class="novel-details">
                                    <h3>Novel Title 1</h3>
                                    <p class="author">By Author Name</p>
                                    <p class="description">A brief description of the novel. A brief description of the novel.  A brief description of the novel. </p>
                                </div>
                                <img class="novel-image" src="img/a11.jpg" alt="slider_010">
                            </div>
                        </div>
                        <div class="carousel-item">
                            <div class="novel-card">
                                <div class="novel-details">
                                    <h3>Novel Title 1</h3>
                                    <p class="author">By Author Name</p>
                                    <p class="description">A brief description of the novel. A brief description of the novel.  A brief description of the novel. </p>
                                </div>
                                <img class="novel-image" src="img/a3.jpg" alt="slider_03">
                            </div>
                        </div>
                        <div class="carousel-item">
                            <div class="novel-card">
                                <div class="novel-details">
                                    <h3>Novel Title 1</h3>
                                    <p class="author">By Author Name</p>
                                    <p class="description">A brief description of the novel. A brief description of the novel.  A brief description of the novel. </p>
                                </div>
                                <img class="novel-image" src="img/a9.jpg" alt="slider_04">
                            </div>
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#hotNovelsCarousel" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden"></span>
                    </a>
                    <a class="carousel-control-next" href="#hotNovelsCarousel" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden"></span>
                    </a>
                </div>


                <!-- Last Update and Top Novel Sections (in a row) -->
                <div class="row">
                    <div class="col-lg-8 col-md-7"> <!-- Check if col-lg-8 is applied -->
                        <!-- Last Update Section -->
                        <section class="last-update">
                            <h2>Last Update</h2>
                            <div class="novels">
                                <div class="novel-item">
                                    <div class="novel-cover"></div>
                                    <p>Name novel</p>
                                </div>
                                <div class="novel-item">
                                    <div class="novel-cover"></div>
                                    <p>Name novel</p>
                                </div>
                                <div class="novel-item">
                                    <div class="novel-cover"></div>
                                    <p>Name novel</p>
                                </div>
                                <div class="novel-item">
                                    <div class="novel-cover"></div>
                                    <p>Name novel</p>
                                </div>
                                <div class="novel-item">
                                    <div class="novel-cover"></div>
                                    <p>Name novel</p>
                                </div>
                                <div class="novel-item">
                                    <div class="novel-cover"></div>
                                    <p>Name novel</p>
                                </div>
                            </div>
                        </section>
                    </div>  <!-- Closing col-lg-8 -->

                    <div class="col-lg-4 col-md-5"> <!-- Check if col-lg-4 is applied -->
                        <!-- Top of Novel Section -->
                        <section class="top-novels">
                            <h2>Top of Novels</h2>
                            <div class="top-novel-tabs">
                                <button class="tab-button active" data-tab="week">Week</button>
                                <button class="tab-button" data-tab="month">Month</button>
                            </div>
                            <div class="top-novel-list" id="week">
                                <div class="novel-rank">
                                    <span class="rank">1</span>
                                    <p>Novel 1</p>
                                </div>
                                <div class="novel-rank">
                                    <span class="rank">2</span>
                                    <p>Novel 2</p>
                                </div>
                                <div class="novel-rank">
                                    <span class="rank">3</span>
                                    <p>Novel 3</p>
                                </div>
                                <div class="novel-rank">
                                    <span class="rank">4</span>
                                    <p>Novel 4</p>
                                </div>
                                <div class="novel-rank">
                                    <span class="rank">5</span>
                                    <p>Novel 5</p>
                                </div>
                            </div>
                            <div class="top-novel-list" id="month" style="display: none;">
                                <div class="novel-rank">
                                    <span class="rank">1</span>
                                    <p>Novel 1 - Month</p>
                                </div>
                                <div class="novel-rank">
                                    <span class="rank">2</span>
                                    <p>Novel 2 - Month</p>
                                </div>
                            </div>
                            <!-- Additional Image Section -->
                            <div class="top-novel-image">
                                <img src="img/b2.png" alt="Logo Icon"  style="width:100%; height:380px; object-fit: contain;">
                            </div>


                        </section>
                    </div>  <!-- Closing col-lg-4 -->
                </div> <!-- Closing row -->

            </main>


            <footer>
                <jsp:include page="/WEB-INF/views/user//components/footer.jsp" /> 
            </footer>
        </div>

        <script src="js/home/home.js"></script>
        <script src="js/home/header.js"></script>
    </body>
</html>