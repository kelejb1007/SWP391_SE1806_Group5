<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty errorMessage}">
    <script>
        alert("${errorMessage}");
    </script>
</c:if>
<form class="search-form" action="novels" method="get" onsubmit="return validateSearch()">
    <input type="text" placeholder="Value" name="search" id="searchInput">
    <input type="hidden" name="action" value="search">
    <button type="submit" value="search" class="btn btn-outline-secondary">
        <i class="fas fa-search"></i>
    </button>
</form>

<script>
    // Hàm này sẽ được gọi khi trang tải xong
    window.onload = function () {
        // Kiểm tra xem có thông báo lỗi từ controller hay không
        var errorMessage = "${errorMessage}"; // Lấy từ request scope

        if (errorMessage !== "") { // Nếu có thông báo lỗi
            alert(errorMessage); // Hiển thị thông báo
        }
    };
    function validateSearch() {
        var searchInput = document.getElementById("searchInput").value.trim();

        if (searchInput === "") {
            alert("Please enter search keywords."); // Hiện thông báo
            return false; // Ngăn chặn gửi form
        }

        if (searchInput.length > 100) {
            alert("Search keyword is too long.");
            return false;
        }

        // Kiểm tra ký tự đặc biệt: chỉ cho phép chữ cái, số, khoảng trắng, dấu chấm, dấu phẩy, dấu gạch ngang
        var specialCharRegex = /[^a-zA-Z0-9\s.,-]/;
        if (specialCharRegex.test(searchInput)) {
            alert("Search keywords must contain only letters, numbers, spaces, periods, commas, and dashes.");
            return false;
        }
        // Bạn có thể thêm kiểm tra ký tự đặc biệt ở đây nếu muốn
        if (searchInput.length < 3 && searchInput.length > 0) {
            alert("Search keyword is too short.");
            return false;
        }

        return true; // Cho phép gửi form
    }
</script>