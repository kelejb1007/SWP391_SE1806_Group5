<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="css/home/sidebar.css?v=2">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<style>
.category-item input[type="checkbox"] {
  display: inline-block !important;
  visibility: visible !important;
  opacity: 1 !important;
  position: static !important;
  margin-right: 5px !important;
}
.sidebar-title {
    display: flex;
    align-items: center;
    justify-content: space-between;
    cursor: pointer;
}
.title-wrapper {
    display: flex;
    align-items: center;
}
.title-wrapper i {
    margin-left: 5px; /* Khoảng cách giữa Genre và caret-down */
}
.find-icon {
    font-size: 20px;
    color: #00029;
    cursor: pointer;
    margin-left: 10px;
    display: none; /* Ẩn mặc định */
}
.find-icon.visible {
    display: inline-block; /* Hiện khi có genre được chọn */
}
.find-icon:hover {
    color: #ff3955;
}
</style>

<div class="sidebar">
    <div class="sidebar-menu">
        <h2 class="sidebar-title" onclick="toggleDropdown('categoryList', this)">
            <span class="title-wrapper">
                Genre <i class="fas fa-caret-down"></i>
            </span>
            <i class="bi bi-grip-vertical find-icon" onclick="submitGenres(); event.stopPropagation();"></i>
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
                               onclick="updateGenreSelection()"
                               ${selectedGenres != null && selectedGenres.contains(genre.genreName) ? 'checked' : ''}>
                        ${genre.genreName}
                    </label>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<script>
let selectedGenres = [];

function updateGenreSelection() {
    const allCheckbox = document.getElementById("genre-all");
    const genreCheckboxes = document.querySelectorAll(".individual-genre");
    const findIcon = document.querySelector(".find-icon");
    
    let anyGenreChecked = Array.from(genreCheckboxes).some(cb => cb.checked);
    if (anyGenreChecked) {
        allCheckbox.checked = false;
    }

    selectedGenres = Array.from(genreCheckboxes)
        .filter(cb => cb.checked)
        .map(cb => cb.value);
    
    if (selectedGenres.length > 0) {
        findIcon.classList.add("visible");
    } else {
        findIcon.classList.remove("visible");
    }
    
    console.log("Selected Genres: " + selectedGenres); // Debug
}

function submitGenres() {
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
    const findIcon = document.querySelector(".find-icon");

    if (allCheckbox.checked) {
        genreCheckboxes.forEach(cb => cb.checked = false);
        selectedGenres = [];
        findIcon.classList.remove("visible");
        let newUrl = new URL(window.location.href);
        const currentFilter = newUrl.searchParams.get("filter") || "all";
        newUrl.searchParams.delete("genre");
        newUrl.searchParams.set("filter", currentFilter);
        console.log("New URL for All: " + newUrl.toString()); // Debug
        window.location.href = newUrl.toString();
    }
}

function loadSelectedGenres() {
    const urlParams = new URLSearchParams(window.location.search);
    const genresFromUrl = urlParams.get("genre");
    const findIcon = document.querySelector(".find-icon");

    if (genresFromUrl) {
        selectedGenres = genresFromUrl.split(",").map(genre => genre.trim());
        selectedGenres.forEach(genre => {
            const checkbox = document.getElementById(`genre-${genre}`);
            if (checkbox) {
                checkbox.checked = true;
            } else {
                console.log(`Checkbox not found for genre: ${genre}`);
            }
        });
        document.getElementById("genre-all").checked = false;
        findIcon.classList.add("visible");
    } else {
        document.getElementById("genre-all").checked = true;
        selectedGenres = [];
        findIcon.classList.remove("visible");
    }
}

document.addEventListener("DOMContentLoaded", loadSelectedGenres);
</script>