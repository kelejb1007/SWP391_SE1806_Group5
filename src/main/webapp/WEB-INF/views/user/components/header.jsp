<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="css/home/avatar.css?v=1">
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Popper.js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.3/umd/popper.min.js"></script>

<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
    .header {
        margin-right: auto;
        margin-left: auto;
    }
</style>

<div class="header">
    <div class="logo">
        <img src="img/b3.png" alt="Logo Icon">
        <a href="<c:url value='/homepage' />">NovelReader</a>
    </div>
    <nav>
        <ul>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    All novels
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <div class="dropdown-container">
                        <div class="dropdown-type-selector">
                            <button class="dropdown-type-button active" data-type="novel">Novel</button>
                            <button class="dropdown-type-button" data-type="genre">Genre</button>
                        </div>
                        <div class="dropdown-content-wrapper">
                            <div class="dropdown-menu-content" id="novel-content">
                                <div class="column">
                                    <h6>Male Lead</h6>
                                    <a class="dropdown-item" href="#">Urban</a>
                                    <a class="dropdown-item" href="#">Eastern</a>
                                </div>

                                <div class="column">
                                    <h6>Female Lead</h6>
                                    <a class="dropdown-item" href="#">Urban</a>
                                    <a class="dropdown-item" href="#">Fantasy</a>
                                </div>
                            </div>
                            <div class="dropdown-menu-content" id="genre-content" style="display: none;">
                                <div class="column">
                                    <h6>Genre Options</h6>
                                    <ul>  <!-- Thêm <ul> -->
                                        <c:forEach var="genre" items="${genres}">
                                            <li class="category-item ${genre.genreName.equals(selectedGenre) ? 'active' : ''}">
                                                <a class="dropdown-item" href="novels?genre=${genre.genreName}">${genre.genreName}</a>
                                            </li>
                                        </c:forEach>
                                    </ul> <!-- Đóng </ul> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

            <li class="search-li">
                <jsp:include page="/WEB-INF/views/user/components/search.jsp" /> 
            </li>

            <li class="spacer"></li>
            <!-- Nếu chưa đăng nhập -->
            <c:if test="${empty sessionScope.user}">
                <li><a href="<c:url value='/Login' />">Log in</a></li>
                <!-- Khoa thêm link vào khi ấn Sign up sẽ đưa đến Register -->
                <li><a href="<c:url value='/Register' />">Sign up</a></li>
                </c:if>

            <!-- Nếu đã đăng nhập -->
            <c:if test="${not empty sessionScope.user}">
                <li><a href="#"> Post</a><i class="fas fa-plus"></i></li>

                <li class="avatar-dropdown1">
                    <div class="avatar-container" onclick="toggleAvatarDropdown()">
                        <img src="<c:if test="${not empty user.imageUML}">${user.imageUML}</c:if> 
                                  <c:if test="${empty user.imageUML}">//yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/account.1e031.png</c:if>" 
                             alt="User Avatar" class="avatar">
                    </div>

                    <div class="dropdown-menu1" id="avatarDropdownMenu">
                        <a href="<c:url value='/favorite' />">Favorite List</a>
                        <a href="mynovel">My Novels</a>
                        <a href="<c:url value='/viewprofile' />">View Profile</a>
                        <form id="logoutForm" action="<c:url value='/Logout' />" method="post">
                            <a href="#" onclick="document.getElementById('logoutForm').submit(); return false;">Logout</a>
                        </form>
                    </div>
                </li>
            </c:if>


        </ul>
    </nav>
</div>
<script>

</script>