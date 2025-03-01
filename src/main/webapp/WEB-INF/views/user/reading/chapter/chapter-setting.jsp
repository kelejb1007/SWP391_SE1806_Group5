<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans&family=Merriweather&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            /* Base styles for display options */
            .display-options {
                padding: 10px;
            }

            .form-group {
                margin-bottom: 15px;
            }

            /* Circle background samples (clickable directly) */
            .bg-circle {
                display: inline-block;
                width: 20px;
                height: 20px;
                border-radius: 50%;
                margin-right: 10px;
                cursor: pointer;
                position: relative;
                transition: all 0.3s ease;
            }

            .bg-circle::after {
                content: '✔';
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                color: #007bff; /* Blue checkmark */
                font-size: 12px;
                opacity: 0; /* Hidden by default */
            }

            .bg-circle.active::after {
                opacity: 1; /* Show checkmark when active */
            }

            .bg-circle:hover {
                transform: scale(1.1); /* Slightly enlarge on hover */
            }

            .white {
                background-color: #ffffff; /* White background */
                border: 2px solid transparent; /* Optional: border for visibility */
            }

            .black {
                background-color: #000000; /* Black background */
                border: 2px solid transparent;
            }

            .pastel-blue {
                background-color: #e6f3ff; /* Pastel blue background */
                border: 2px solid transparent;
            }

            /* Default styles for the page */
            body {
                font-family: Arial, sans-serif; /* Default font, no font selection */
                background-color: #ffffff; /* Default white background */

                font-size: 18px;
                transition: all 0.3s ease;
            }

            /* Color modes */
            body.black-mode {
                background-color: #333; /* Black background */
                color: #fff; /* Default to black text first */
            }

            body.pastel-blue-mode {
                background-color: #e6f3ff; /* Pastel blue background */
                color: #2d2d2d; /* Black text for contrast */
            }

            /* Ensure offcanvas content inherits styles */
            .offcanvas-body, .offcanvas-block {
                transition: all 0.3s ease;
            }

            /* Apply to novel chapter content (from content.jsp) */
            body.black-mode .content-container {
                background-color: #333; /* Darker black for content */
            }

            body.pastel-blue-mode .content-container {
                background-color: #f0f8ff; /* Slightly darker pastel blue for content */
            }

            /* Adjust sidebars for new modes */
            body.black-mode .sidebar-left, body.black-mode .sidebar-right, body.black-mode .offcanvas, body.black-mode .sidebar{
                background-color: #2d2d2d; /* Darker black for sidebars */
                color: #fff;
            }

            body.pastel-blue-mode .sidebar-left, body.pastel-blue-mode .sidebar-right {
                background-color: #cce5ff; /* Lighter pastel blue for sidebars */
                color: #333;
            }

            /* Size button styles (unchanged) */
            .size-btn {
                padding: 5px 10px;
                margin: 0 5px;
                border: 1px solid #ccc;
                background: none;
                cursor: pointer;
            }

            /* Highlight active circle */
            .bg-circle.active {
                border: 2px solid #007bff; /* Blue border for selected circle */
            }
            /* Apply to offcanvas components */
            body.black-mode .offcanvas,
            body.black-mode .offcanvas-body,
            body.black-mode .offcanvas-header,
            body.black-mode .offcanvas-block,
            body.black-mode .chapter-list,
            body.black-mode .chapter-item,
            body.black-mode .offcanvas-blocks-container,
            body.black-mode .display-options {
                background-color: #2d2d2d; /* Dark background */
                color: #fff; /* White text */
            }

            body.black-mode .offcanvas-body h5, /* Offcanvas title */
            body.black-mode .offcanvas-block-small h6 {
                color: #fff; /* White text for titles */
            }

            body.black-mode .btn-close {
                filter: invert(1); /* Invert the close icon color */
            }

            body.black-mode .size-btn {
                border-color: #666;
                color: #fff;
            }

            body.black-mode .size-btn:hover {
                background-color: #444;
            }

            body.black-mode .bg-circle::after {
                color: #00ffff; /* Cyan checkmark */
            }

            body.black-mode .sidebar-icon {
                color: #fff;
            }

            /* Pastel Blue Mode Adjustments */
            body.pastel-blue-mode .offcanvas,
            body.pastel-blue-mode .offcanvas-body,
            body.pastel-blue-mode .offcanvas-header,
            body.pastel-blue-mode .offcanvas-block,
            body.pastel-blue-mode .chapter-list,
            body.pastel-blue-mode .chapter-item,
            body.pastel-blue-mode .offcanvas-blocks-container,
            body.pastel-blue-mode .display-options{
                background-color: #cce5ff; /* Lighter pastel blue */
                color: #333; /* Dark text */
            }

            body.pastel-blue-mode .offcanvas-body h5, /* Offcanvas title */
            body.pastel-blue-mode .offcanvas-block-small h6 {
                color: #333; /* Dark text for titles */
            }

            body.pastel-blue-mode .btn-close {
                /* No invert needed, might work well with default */
            }

            body.pastel-blue-mode .size-btn {
                border-color: #99c2ff;
                color: #333;
            }

            body.pastel-blue-mode .size-btn:hover {
                background-color: #b3d9ff;
            }

            body.pastel-blue-mode .bg-circle::after {
                color: #007bff; /* Keep blue checkmark */
            }

            body.pastel-blue-mode .sidebar-icon{
                color:#333;
            }


            /* General styling for chapter list */
            .chapter-list {
                overflow-y: auto; /* Enable vertical scrolling */
                max-height: 300px; /* Adjust as needed */
                padding-right: 5px; /* Space for the scrollbar */
            }

            .chapter-list-ul {
                list-style: none; /* Remove bullet points */
                padding: 0;
                margin: 0;
            }

            .chapter-item {
                padding: 10px;
                border-bottom: 1px solid #eee; /* Add a subtle separator */
                transition: background-color 0.3s ease; /* Smooth hover effect */
            }

            .chapter-item:last-child {
                border-bottom: none; /* Remove border on the last item */
            }

            .chapter-item:hover {
                background-color: #f9f9f9; /* Light background on hover */
            }

            .chapter-item-container {
                display: flex;
                justify-content: space-between; /* Space between chapter name and time */
                align-items: center; /* Vertically align items */
            }

            .chapter-name {
                text-decoration: none;
                color: inherit; /* Inherit text color from parent */
                display: block; /* Make it fill available space */
                flex-grow: 1; /* Allow chapter name to grow */
                overflow: hidden; /* Hide overflowing text */
                text-overflow: ellipsis; /* Add ellipsis (...) if text overflows */
                white-space: nowrap; /* Prevent text from wrapping */
            }

            .chapter-name:hover {
                text-decoration: underline; /* Underline on hover */
            }

            .chapter-number {
                font-weight: bold;
                margin-right: 5px;
            }

            .chapter-time {
                font-size: 0.8em; /* Smaller font size for the time */
                color: #888;
                white-space: nowrap; /* Prevent time from wrapping */
            }

            /* Locked Chapter Styling */
            .chapter-name.locked {
                color: #aaa; /* Grayed out locked chapter name */
                cursor: not-allowed; /* Indicate not clickable */
            }

            .chapter-name.locked:hover {
                text-decoration: none; /* Remove underline on hover for locked chapters */
            }

            .fa-lock {
                margin-left: 5px;
                color: #aaa;
            }

            /* === Scrollbar Theming === */
            /* ... (Scrollbar CSS from previous response) ... */


            /* === Dark Mode Chapter List === */
            body.black-mode .chapter-item {
                border-bottom-color: #444;
            }

            body.black-mode .chapter-item:hover {
                background-color: #444;
            }

            body.black-mode .chapter-time,
            body.black-mode .chapter-name.locked,
            body.black-mode .fa-lock {
                color: #888;
            }


            /* === Pastel Blue Mode Chapter List === */
            body.pastel-blue-mode .chapter-item {
                border-bottom-color: #b3d9ff;
            }

            body.pastel-blue-mode .chapter-item:hover {
                background-color: #d9e6f2;
            }

            body.pastel-blue-mode .chapter-time,
            body.pastel-blue-mode .chapter-name.locked,
            body.pastel-blue-mode .fa-lock {
                color: #777;
            }
        </style>
    <div class="sidebar">
        <i class="bi bi-palette2 sidebar-icon" data-bs-toggle="offcanvas" data-bs-target="#offcanvasChapter" aria-controls="offcanvasChapter" title="Chapter"></i>
        <i class="bi bi-gear sidebar-icon" data-bs-toggle="offcanvas" data-bs-target="#offcanvasSettings" aria-controls="offcanvasSettings" title="Settings"></i>
    </div>


    <!-- Offcanvas Chapter -->
    <div class="offcanvas offcanvas-end" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasChapter" aria-labelledby="offcanvasChapterLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasChapterLabel">Chapter Details</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <div class="offcanvas-blocks-container">

                <div class="offcanvas-block offcanvas-block-large">

                    <div class = "chapter-item-container">
                        <div class="chapter-list">
                            <ul class="chapter-list-ul">
                                <c:if test="${not empty chapters}">
                                    <c:forEach var="chapter" items="${chapters}" varStatus = "status">
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
        </div>
    </div>


    <!-- Offcanvas Settings -->
    <div class="offcanvas offcanvas-end" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasSettings" aria-labelledby="offcanvasSettingsLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasSettingsLabel">Settings</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <div class="offcanvas-blocks-container">
                <div class="offcanvas-block offcanvas-block-small">
                    <h6>Display Options</h6>
                    <div class="display-options">
                        <!-- Background Options (Click directly on circles) -->
                        <div class="form-group">
                            <label>Background</label><br>
                            <span class="bg-circle white" data-color="white"></span>
                            <span class="bg-circle black" data-color="black"></span>
                            <span class="bg-circle pastel-blue" data-color="pastel-blue"></span>
                        </div>

                        <!-- Font Size Options -->
                        <div class="form-group">
                            <label>Size</label><br>
                            <button id="decrease-size" class="size-btn">-</button>
                            <span id="font-size-value">18</span>
                            <button id="increase-size" class="size-btn">+</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <script>document.addEventListener('DOMContentLoaded', function() {
  const lockedChapters = document.querySelectorAll('.chapter-name.locked');

  lockedChapters.forEach(chapter => {
    chapter.addEventListener('click', function(event) {
      event.preventDefault(); // Prevent the default action (following the link)
      // Optionally, you can display a message to the user
      alert('Bạn cần đăng nhập hoặc nâng cấp tài khoản để đọc chương này.');
    });
  });
});</script> 
