<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="css/home/sidebar.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<div class="sidebar">
    <div class="sidebar-menu">
        <h2 class="sidebar-title" onclick="toggleDropdown('categoryList', this)">
            Genre <i class="fas fa-caret-down"></i>
        </h2>
        <ul class="category-list" id="categoryList">
            <c:forEach var="genre" items="${genres}">
                <li class="category-item ${genre.genreName.equals(selectedGenre) ? 'active' : ''}">
                    <a href="novels?filter=${selectedFilter}&genre=${genre.genreName}">
                        ${genre.genreName}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>


