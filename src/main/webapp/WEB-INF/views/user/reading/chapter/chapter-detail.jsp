<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Novel Chapter</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Macondo+Swash+Caps&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.5/font/bootstrap-icons.min.css">
        <style>
            /* General Styling */
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                background-color: #f0f0f0;
                line-height: 1.6;
                overflow-x: hidden;
                transition: all 0.3s ease;
            }

            .main-container {
                display: flex;
                width: 100%;
                min-height: 100vh;
                padding-left: 200px;
                padding-right: 200px;
                box-sizing: border-box;
                position: relative;
            }

            .sidebar-left, .sidebar-right {
                width: 200px;
                background-color: #ddd;
                padding: 15px;
                display: flex;
                align-items: center;
                height: 100vh;
                position: fixed;
                top: 0;
                box-sizing: border-box;
                justify-content: center;
            }

            .sidebar-left {
                left: 0;
            }

            .sidebar-right {
                right: 0;
            }

            .content-container {
                flex-grow: 1;
                max-width: 1200px;
                background-color: #fff;
                padding: 20px;
                box-sizing: border-box;
            }

            /* First Block: Image and Meta Info */
            .content-header {
                text-align: center;
                margin-bottom: 20px;
                padding-bottom: 20px;
            }

            /* Khung ảnh */
            .image-container {
                background-color: #eee; /* Màu xám */
                padding: 5px; /* Khoảng cách giữa ảnh và khung */
                border-radius: 5px; /* Bo góc (tùy chọn) */
                display: inline-block; /* Để container vừa với kích thước ảnh */
                margin-bottom: 10px; /* Thêm khoảng cách bên dưới khung */
            }

            .content-header img {
                max-width: 200px;
                height: auto;
                border-radius: 5px;
                transition: transform 0.2s ease-in-out;
                display: block; /* Loại bỏ khoảng trắng thừa dưới ảnh */
                margin: 0 auto; /* Căn giữa ảnh trong khung */
            }

            .content-header img:hover {
                transform: scale(1.05);
            }

            .content-header a{
                margin: 5px 0;
                font-size: 1.5em; /* Giảm kích thước tên tiểu thuyết */
                font-family: 'Macondo Swash Caps', cursive; /* Áp dụng font chữ */
                text-decoration: none;
                color: activeborder;
            }

            .content-header h2 {
                color: #777;
                font-size: 16px;
            }

            .content-header p {
                color: #777;
                font-size: 12px;
            }

            /* Second Block: Decorative Element */
            .decorative-line {
                display: flex;
                align-items: center;
                justify-content: center;
                margin-bottom: 30px;
                border-bottom: 2px solid #ccc;
            }

            .decorative-line i {
                padding: 0 10px;
                color: #aaa;
            }

            /* Third Block: Chapter Content */
            .chapter-content {
                min-height: 100vh;
                padding-top: 30px;
                text-align: justify; /* Căn đều nội dung tiểu thuyết */
                padding-left: 50px; /* Thêm padding bên trái */
                padding-right: 50px; /* Thêm padding bên phải */
            }


            .chapter-content h1 {
                font-size: 2em; /* Tăng kích thước tên chương */
                margin-bottom: 15px;
                display: inline-block;
                font-family: 'Macondo Swash Caps', cursive; /* Áp dụng font chữ */
                text-align: center; /* Căn giữa tên chương */
                padding: 0 20px; /* Thêm padding 2 bên tên chương */
                box-sizing: border-box; /* Đảm bảo padding không làm tăng kích thước phần tử */
            }

            /* Sidebar Content Styles */
            .sidebar-content {
                text-align: center;
            }

            .sidebar-left .bi-chevron-compact-left,
            .sidebar-right .bi-chevron-compact-right {
                font-size: 4em;
                color: rgba(0, 0, 0, 0.1);
                cursor: pointer;
                transition: color 0.3s;
            }

            .sidebar-left .bi-chevron-compact-left:hover,
            .sidebar-right .bi-chevron-compact-right:hover {
                color: rgba(0, 0, 0, 0.5);
            }

            /* White mode styles */
            body.white-mode {
                background-color: #f0f0f0;
                color: #333;
            }

            body.white-mode .content-container {
                background-color: #fff;
            }

            body.white-mode .sidebar-left, body.white-mode .sidebar-right {
                background-color: #f0f0f0;
            }

            body.white-mode .content-header h1,
            body.white-mode .content-header h2,
            body.white-mode .chapter-content,
            body.white-mode .chapter-content h1,
            body.white-mode .chapter-content div,
            body.white-mode .content-header p {
                color: #333;
            }

            /* Pastel blue mode styles */
            body.pastel-blue-mode {
                background-color: #e6f3ff;
                color: #333;
            }

            body.pastel-blue-mode .content-container {
                background-color: #f0f8ff;
            }

            body.pastel-blue-mode .sidebar-left, body.pastel-blue-mode .sidebar-right {
                background-color: #cce5ff;
            }

            body.pastel-blue-mode .content-header h1,
            body.pastel-blue-mode .content-header h2,
            body.pastel-blue-mode .chapter-content,
            body.pastel-blue-mode .chapter-content h1,
            body.pastel-blue-mode .chapter-content div,
            body.pastel-blue-mode .content-header p {
                color: #333;
            }

            /* Dark mode styles */
            body.dark-mode {
                background-color: #444;
                color: #fff;
            }

            body.dark-mode .content-container {
                background-color: #333;
            }

            body.dark-mode .sidebar-left, body.dark-mode .sidebar-right {
                background-color: #777;
            }

            body.dark-mode .decorative-line {
                border-bottom: 1px solid #666;
            }

            body.dark-mode .decorative-line i {
                color: #aaa;
            }

            body.dark-mode .sidebar-left .bi-chevron-compact-left,
            body.dark-mode .sidebar-right .bi-chevron-compact-right {
                color: rgba(255, 255, 255, 0.1);
            }

            body.dark-mode .content-header h1,
            body.dark-mode .content-header h2,
            body.dark-mode .chapter-content,
            body.dark-mode .chapter-content h1,
            body.dark-mode .chapter-content div,
            body.dark-mode .content-header p {
                color: #fff;
            }

            /* Chapter Select Styles */
            .chapter-select-container {
                position: relative;
                display: inline-block;
            }

            .chapter-select {
                padding: 10px 15px;
                border: 1px solid;
                border-radius: 5px;
                font-size: 16px;
                font-family: 'Marcellus', serif;
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                cursor: pointer;
                transition: border-color 0.3s ease;
            }

            .chapter-select:hover {
                border-color: #ffc107;
            }

            .chapter-select::-ms-expand {
                display: none;
            }

            .chapter-select-container::after {
                content: '\f0d7';
                font-family: 'Font Awesome 6 Free';
                font-weight: 900;
                position: absolute;
                top: 50%;
                right: 10px;
                transform: translateY(-50%);
                pointer-events: none;
                color: #9c8455;
            }

            /* White mode styles */
            body.white-mode {
                background-color: #f0f0f0;
                color: #333;
            }

            body.white-mode .content-container {
                background-color: #fff;
            }

            body.white-mode .sidebar-left, body.white-mode .sidebar-right {
                background-color: #ddd;
            }

            body.white-mode .content-header h1,
            body.white-mode .content-header h2,
            body.white-mode .chapter-content,
            body.white-mode .chapter-content h1,
            body.white-mode .chapter-content div,
            body.white-mode .content-header p {
                color: #333;
            }

            body.white-mode .chapter-select {
                background-color: #fff;
                color: #333;
                border-color: #ccc;
            }

            body.white-mode .chapter-select-container::after {
                color: #ccc;
            }

            body.white-mode .chapter-novel-info a {
                color: #333;
            }

            /* Pastel blue mode styles */
            body.pastel-blue-mode {
                background-color: #e6f3ff;
                color: #333;
            }

            body.pastel-blue-mode .content-container {
                background-color: #f0f8ff;
            }

            body.pastel-blue-mode .sidebar-left, body.pastel-blue-mode .sidebar-right {
                background-color: #cce5ff;
            }

            body.pastel-blue-mode .content-header h1,
            body.pastel-blue-mode .content-header h2,
            body.pastel-blue-mode .chapter-content,
            body.pastel-blue-mode .chapter-content h1,
            body.pastel-blue-mode .chapter-content div,
            body.pastel-blue-mode .content-header p {
                color: #333;
            }

            body.pastel-blue-mode .chapter-select {
                background-color: #f0f8ff;
                color: #333;
                border-color: #cce5ff;
            }

            body.pastel-blue-mode .chapter-select-container::after {
                color: #cce5ff;
            }

            body.pastel-blue-mode .chapter-novel-info a {
                color: #333;
            }

            /* Dark mode styles */
            body.dark-mode {
                background-color: #444;
                color: #fff;
            }

            body.dark-mode .content-container {
                background-color: #333;
            }

            body.dark-mode .sidebar-left, body.dark-mode .sidebar-right {
                background-color: #2d2d2d;
            }

            body.dark-mode .decorative-line {
                border-bottom: 1px solid #666;
            }

            body.dark-mode .decorative-line i {
                color: #aaa;
            }

            body.dark-mode .sidebar-left .bi-chevron-compact-left,
            body.dark-mode .sidebar-right .bi-chevron-compact-right {
                color: rgba(255, 255, 255, 0.1);
            }

            body.dark-mode .content-header h1,
            body.dark-mode .content-header h2,
            body.dark-mode .chapter-content,
            body.dark-mode .chapter-content h1,
            body.dark-mode .chapter-content div,
            body.dark-mode .content-header p {
                color: #d4c7b0;
            }

            body.dark-mode .chapter-select {
                background-color: rgba(255, 255, 255, 0.01);
                color: #d4c7b0;
                border-color: #9c8455;
            }

            body.dark-mode .chapter-select-container::after {
                color: #9c8455;
            }
            body.dark-mode .chapter-select-container::after {
                color: #9c8455;
            }
            body.dark-mode .chapter-novel-info a {
                color: #d4c7b0;
            }

        </style>
    </head>
    <body>

        <div class="main-container">
            <div class="content-container">
                <div class="content-header">
                    <div class="image-container">
                        <img src="${novel.imageURL}" class="rounded" alt="${novel.novelName}">
                    </div>
                    <div class="chapter-novel-info">
                        <a href="novel-detail?id=${novel.novelID}">${novel.novelName}</a>
                    </div>
                    <h2>Author: ${novel.author}</h2>
                    <p>© 2025 NovelReadingwebsite</p>

                    <c:if test="${not empty chapters and not empty chapter}">
                        <div class="chapter-select-container">
                            <select class="chapter-select" onchange="window.location.href = this.value;">
                                <c:forEach var="chap" items="${chapters}">
                                    <c:choose>
                                        <c:when test="${not empty user and chap.chapterNumber > 3}">
                                            <option value="chapter?id=${chap.chapterID}" ${chap.chapterID == chapter.chapterID ? 'selected' : ''}>
                                                Chapter ${chap.chapterNumber}: ${chap.chapterName}
                                            </option>
                                        </c:when>
                                        <c:when test="${not canViewContent && chap.chapterNumber > 3}">
                                            <option value="#" disabled class="locked-chapter">
                                                Chapter ${chap.chapterNumber}: ${chap.chapterName}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="chapter?id=${chap.chapterID}" ${chap.chapterID == chapter.chapterID ? 'selected' : ''}>
                                                Chapter ${chap.chapterNumber}: ${chap.chapterName}
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </c:if>
                </div>

                <div class="decorative-line">
                    <i class="bi bi-book-half"></i>
                </div>

                <div class="chapter-content">
                    <h1>Chapter ${chapter.chapterNumber}: ${chapter.chapterName}</h1>
                    <div>
                        <c:choose>
                            <c:when test="${canViewContent}">
                                <p>${chapterContent}</p>
                            </c:when>
                            <c:otherwise>
                                <p>Bạn cần đăng nhập hoặc chương này yêu cầu tài khoản trả phí để đọc.</p>
                                <a href="login">Đăng nhập</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="sidebar-left">
                <div class="sidebar-content">
                    <c:if test="${not empty previousChapter}">
                        <a href="chapter?id=${previousChapter.chapterID}">
                            <i class="bi bi-chevron-compact-left"></i>
                        </a>
                    </c:if>
                </div>
            </div>

            <div class="sidebar-right">
                <div class="sidebar-content">
                    <c:if test="${not empty nextChapter}">
                        <a href="chapter?id=${nextChapter.chapterID}">
                            <i class="bi bi-chevron-compact-right"></i>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>

    </body>
</html>
<script>
function handleChapterChange(selectElement) {
    const selectedValue = selectElement.value;
    if (selectedValue === "#") {
        alert("Bạn cần đăng nhập hoặc nâng cấp tài khoản để đọc chương này.");
        selectElement.selectedIndex = selectElement.options.selectedIndex; // Reset về option hiện tại.
    } else {
        window.location.href = selectedValue;
    }
}
</script>