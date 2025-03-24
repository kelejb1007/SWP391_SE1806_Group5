<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="css/home/sidebar.css?v=2">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- Sửa lỗi CSS - thêm !important để đảm bảo hiển thị -->
<style>

.category-item input[type="checkbox"] {
  display: inline-block !important;
  visibility: visible !important;
  opacity: 1 !important;
  position: static !important;
  margin-right: 5px !important;
}
</style>

<div class="sidebar">
    <div class="sidebar-menu">
        <h2 class="sidebar-title" onclick="toggleDropdown('categoryList', this)">
            Genre <i class="fas fa-caret-down"></i>
        </h2>
        <ul class="category-list" id="categoryList">
            <li class="category-item">
                <label for="genre-all">
                    <input type="checkbox" id="genre-all" class="genre-checkbox" value="all" onclick="handleAllCheckbox()">
                    All
                </label>
            </li>
            <c:forEach var="genre" items="${genres}">
                <li class="category-item">
                    <label for="genre-${genre.genreName}">
                        <input type="checkbox" 
                               id="genre-${genre.genreName}" 
                               class="genre-checkbox individual-genre" 
                               value="${genre.genreName}" 
                               onclick="updateGenres()"
                               ${selectedGenres != null && selectedGenres.contains(genre.genreName) ? 'checked' : ''}>
                        ${genre.genreName}
                    </label>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<script>
function updateGenres() {
    const allCheckbox = document.getElementById("genre-all");
    const genreCheckboxes = document.querySelectorAll(".individual-genre");
    
    let anyGenreChecked = Array.from(genreCheckboxes).some(cb => cb.checked);
    if (anyGenreChecked) {
        allCheckbox.checked = false;
    }

    let selectedGenres = Array.from(genreCheckboxes)
        .filter(cb => cb.checked)
        .map(cb => cb.value);
    console.log("Selected Genres in JS: " + selectedGenres); // Debug
    updateUrl(selectedGenres);
}

function updateUrl(selectedGenres) {
    let newUrl = new URL(window.location.href);
    const currentFilter = newUrl.searchParams.get("filter") || "all";

    if (selectedGenres.length > 0) {
        newUrl.searchParams.set("genre", selectedGenres.join(","));
    } else {
        newUrl.searchParams.delete("genre");
    }
    newUrl.searchParams.set("filter", currentFilter);
    console.log("New URL: " + newUrl.toString()); // Debug
    window.location.href = newUrl.toString();
}

function handleAllCheckbox() {
    const allCheckbox = document.getElementById("genre-all");
    const genreCheckboxes = document.querySelectorAll(".individual-genre");

    if (allCheckbox.checked) {
        genreCheckboxes.forEach(cb => cb.checked = false);
        updateUrl([]);
    }
}

function loadSelectedGenres() {
    const urlParams = new URLSearchParams(window.location.search);
    const selectedGenres = urlParams.get("genre");

    if (selectedGenres) {
        const genreArray = selectedGenres.split(",").map(genre => genre.trim()); // Tách và loại bỏ khoảng trắng
        genreArray.forEach(genre => {
            const checkbox = document.getElementById(`genre-${genre}`);
            if (checkbox) {
                checkbox.checked = true;
            } else {
                console.log(`Checkbox not found for genre: ${genre}`);
            }
        });
        document.getElementById("genre-all").checked = false;
    } else {
        document.getElementById("genre-all").checked = true;
    }
}

document.addEventListener("DOMContentLoaded", loadSelectedGenres);
</script>