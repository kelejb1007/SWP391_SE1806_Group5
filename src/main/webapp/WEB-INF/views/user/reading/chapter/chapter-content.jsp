<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Chapter Content</title>
  <link rel="stylesheet" href="css/home/header.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

  <style>
    .sidebar {
      width: 40px;
      background-color: #f8f9fa;
      position: fixed;
      top: 0;
      right: 0;
      height: 100vh;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      padding-top: 20px;
      z-index: 1000;
    }

    .sidebar-icon {
      margin-bottom: 20px;
      cursor: pointer;
      color: #555;
      transition: color 0.3s ease;
    }

    .sidebar-icon:hover {
      color: #007bff;
    }

    .offcanvas {
      width: 300px !important;
    }

    .offcanvas-end {
      transform: translateX(100%);
      left: auto;
      right: 0;
    }

    .offcanvas.showing,
    .offcanvas.show {
      transform: translateX(0);
    }

    .offcanvas-block {
      padding: 10px;
      margin-bottom: 10px;
      border: 1px solid #ddd;
      background-color: #f9f9f9;
      width: 100%;
     
    }
 .offcanvas-block-large {
        max-height: 400px; /* Adjust as needed, smaller to ensure content doesn't overflow */
        overflow-y: auto; /* Enable vertical scrolling */
    }

    .offcanvas-blocks-container {
        display: flex;
        flex-direction: column;
    }

    .offcanvas-block-small {
    }

    

  </style>
</head>
<body>
 
  <%-- Include the sidebar --%>
  <jsp:include page="/WEB-INF/views/user/reading/chapter/chapter-setting.jsp"></jsp:include>

  <%-- Include the offcanvas menus --%>
  
  
     <jsp:include page="/WEB-INF/views/user/reading/chapter/chapter-detail.jsp"></jsp:include>

  

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
 document.addEventListener("DOMContentLoaded", function() {
    // Khởi tạo giá trị từ Local Storage nếu có
    let currentColor = localStorage.getItem('backgroundColor') || 'white';
    let currentSize = parseInt(localStorage.getItem('fontSize')) || 18;

    // Áp dụng giá trị ban đầu
    if (currentColor === 'black') {
        document.body.classList.add("black-mode");
        document.querySelector(".sidebar-left").classList.add("black-mode");
        document.querySelector(".sidebar-right").classList.add("black-mode");
        document.querySelector(".content-container").classList.add("black-mode");
    } else if (currentColor === 'pastel-blue') {
        document.body.classList.add("pastel-blue-mode");
        document.querySelector(".sidebar-left").classList.add("pastel-blue-mode");
        document.querySelector(".sidebar-right").classList.add("pastel-blue-mode");
        document.querySelector(".content-container").classList.add("pastel-blue-mode");
    }

    document.body.style.fontSize = currentSize + "px";
    document.querySelector(".content-container").style.fontSize = currentSize + "px";
    document.querySelector(".chapter-content").style.fontSize = currentSize + "px";
    document.getElementById("font-size-value").textContent = currentSize;

    // Background toggle (Click directly on circles: White, Black, Pastel Blue)
    const bgCircles = document.querySelectorAll('.bg-circle');
    bgCircles.forEach(circle => {
        circle.addEventListener("click", function() {
            // Remove active class from all circles
            bgCircles.forEach(c => c.classList.remove('active'));
            // Add active class to clicked circle
            this.classList.add('active');

            // Remove all mode classes first
            document.body.classList.remove("black-mode", "pastel-blue-mode");
            document.querySelector(".sidebar-left").classList.remove("black-mode", "pastel-blue-mode");
            document.querySelector(".sidebar-right").classList.remove("black-mode", "pastel-blue-mode");
            document.querySelector(".content-container").classList.remove("black-mode", "pastel-blue-mode");

            // Add the selected mode class based on data-color attribute
            const color = this.getAttribute('data-color');
            if (color === "black") {
                document.body.classList.add("black-mode");
                document.querySelector(".sidebar-left").classList.add("black-mode");
                document.querySelector(".sidebar-right").classList.add("black-mode");
                document.querySelector(".content-container").classList.add("black-mode");
            } else if (color === "pastel-blue") {
                document.body.classList.add("pastel-blue-mode");
                document.querySelector(".sidebar-left").classList.add("pastel-blue-mode");
                document.querySelector(".sidebar-right").classList.add("pastel-blue-mode");
                document.querySelector(".content-container").classList.add("pastel-blue-mode");
            } else { // white (default)
                // No additional class needed, default styles apply
            }

            // Lưu màu nền vào Local Storage
            localStorage.setItem('backgroundColor', color);
        });
    });

    // Font size adjustment
    const decreaseBtn = document.getElementById("decrease-size");
    const increaseBtn = document.getElementById("increase-size");
    const fontSizeValue = document.getElementById("font-size-value");

    decreaseBtn.addEventListener("click", function() {
        if (currentSize > 12) {
            currentSize--;
            fontSizeValue.textContent = currentSize;
            document.body.style.fontSize = currentSize + "px";
            document.querySelector(".content-container").style.fontSize = currentSize + "px";
            document.querySelector(".chapter-content").style.fontSize = currentSize + "px";
            // Lưu kích thước chữ vào Local Storage
            localStorage.setItem('fontSize', currentSize);
        }
    });

    increaseBtn.addEventListener("click", function() {
        if (currentSize < 30) {
            currentSize++;
            fontSizeValue.textContent = currentSize;
            document.body.style.fontSize = currentSize + "px";
            document.querySelector(".content-container").style.fontSize = currentSize + "px";
            document.querySelector(".chapter-content").style.fontSize = currentSize + "px";
            // Lưu kích thước chữ vào Local Storage
            localStorage.setItem('fontSize', currentSize);
        }
    });
});

  </script>
  
</body>
</html>