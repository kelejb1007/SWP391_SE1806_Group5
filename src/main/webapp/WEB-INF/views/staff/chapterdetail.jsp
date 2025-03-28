<%-- 
    Document   : chapterdetail
    Created on : Mar 11, 2025, 10:09:06 AM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Novel Chapter</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="stylesheet" href="css/chapter-content/chapter-content.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Satisfy&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@400;700&family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Macondo+Swash+Caps&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.5/font/bootstrap-icons.min.css">
        <style>
            /* General Styling */
            body {
                font-family: 'Merriweather', serif;
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
                background: #f2f2f2 url(https://noah-image.webnovel.com/noah/img/c72400dd98494ddd8ae42069efbd6eb0.png) 50% no-repeat;
                background-size: cover;

                overflow: hidden;
                position: relative;
                width: 200px;
                height: 300px;
                border-radius: 4px;
                margin-bottom: 8px;
                display: inline-block; /* Để container vừa với kích thước ảnh */
                margin-bottom: 10px; /* Thêm khoảng cách bên dưới khung */
            }
            .image-container:after {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                box-sizing: border-box;
                border: 3px solid #3b66f5;
                box-shadow: inset 0 0 0 3px #fff;
                visibility: hidden;
                opacity: 0;
                transition: .2s;
            }
            .content-header img {

                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;
                transition: transform .3s ease-out, -webkit-transform .3s ease-out;
            }

            .content-header img:hover {
                transform: scale(1.1); /* Phóng to hình ảnh 1.1 lần khi hover */
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
                font-weight: 600;
                font-style: italic;
                opacity: 0.6;
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
                background-color: #269abc;
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
                background-color: #269abc;
                color: #333;
            }

            body.white-mode .content-container {
                background-color: #fff;
            }

            body.white-mode .sidebar-left, body.white-mode .sidebar-right {
                background-color: #d9edff; /* Nhạt hơn */
            }
            body.white-mode .sidebar-left{
                box-shadow: inset -4px 0 0 rgba(0, 0, 0, 0.05); /* Hiệu ứng đường kẻ mờ tinh tế bên trái */
            }
            body.white-mode .sidebar-right {
                box-shadow: inset 4px 0 0 rgba(0, 0, 0, 0.05); /* Hiệu ứng đường kẻ mờ tinh tế */
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
                background-color: #f5faff; /* Nhạt hơn */
                color: #444; /* Mềm mại hơn */
            }

            body.pastel-blue-mode .content-container {
                background-color: #f0f8ff;

                box-shadow: 0 0 10px rgba(0, 0, 0, 0.05); /* Thêm đổ bóng nhẹ */
                padding: 20px; /* Thêm padding */
            }

            body.pastel-blue-mode .sidebar-left, body.pastel-blue-mode .sidebar-right {
                background-color: #d9edff; /* Nhạt hơn */
            }
            body.pastel-blue-mode .sidebar-left{
                box-shadow: inset -4px 0 0 rgba(0, 0, 0, 0.05); /* Hiệu ứng đường kẻ mờ tinh tế bên trái */
            }
            body.pastel-blue-mode .sidebar-right {
                box-shadow: inset 4px 0 0 rgba(0, 0, 0, 0.05); /* Hiệu ứng đường kẻ mờ tinh tế */
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
                color: #269abc;
                border-color: #2e6da4;
            }

            body.pastel-blue-mode .chapter-select-container::after {
                color: #2e6da4; /* Tương phản hơn */
            }

            body.pastel-blue-mode .chapter-novel-info a {
                color: #23527c;
                text-shadow: 0.5px 0.2px 0 #23527c;

            }

            body.pastel-blue-mode .decorative-line {
                display: flex;
                align-items: center;
                justify-content: center;
                margin-bottom: 30px;
                border-bottom: 2px solid #ccc;
                border-bottom: 2px solid rgba(0, 0, 0, 0.1); /* Sử dụng màu shadow cho đường kẻ */
            }

            body.pastel-blue-mode .decorative-line i {
                padding: 0 10px;
                color: #ddd;
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
                background-color: #269abc;
            }
            body.dark-mode .sidebar-left{
                box-shadow: inset -4px 0 0 rgba(0, 0, 0, 0.05); /* Hiệu ứng đường kẻ mờ tinh tế bên trái */
            }
            body.dark-mode .sidebar-right {
                box-shadow: inset 4px 0 0 rgba(0, 0, 0, 0.05); /* Hiệu ứng đường kẻ mờ tinh tế */
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

            .chapter-content p {
                font-family: 'Open Sans', sans-serif;
                letter-spacing: 0.02em;
                text-indent: 0.8rem;
                line-height: 1.7;
                margin-bottom: 20px;
                opacity: 0.7;
                text-align: justify-all;
                -webkit-hyphens: auto;
                -moz-hyphens: auto;
                -ms-hyphens: auto;
                hyphens: auto;

            }
            .chapter-content p strong {
                font-weight: bold;
            }


            .chapter-content .in-nghieng {
                font-family: 'Open Sans', sans-serif;
                font-style: italic;
                font-size: 1.1rem;
                /*color: #d9534f; */
                font-weight: 600;
                text-indent: 0.5rem;
                opacity: 0.7;
            }

        </style>
    </head>
    <body>

        <div class="main-container">
            <div class="content-container">
                <div class="chapter-content">
                    <h1>Chapter ${chapter.chapterNumber}: ${chapter.chapterName}</h1>    
                    <p>${chapterContent}</p>
                </div>
            </div>

        </div>

    </body>
</html>
<script>
    function handleChapterChange(selectElement) {
        const selectedValue = selectElement.value;
        if (selectedValue === "#") {
            alert("You need to login this chapter requires an account to read.");
            selectElement.selectedIndex = selectElement.options.selectedIndex; // Reset về option hiện tại.
        } else {
            window.location.href = selectedValue;
        }
    }
</script>

