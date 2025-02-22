<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Bar Rating CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/themes/fontawesome-stars.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/themes/fontawesome-stars-o.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/themes/bars-1to10.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/themes/bars-movie.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/themes/bars-pill.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/themes/bars-reversed.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/themes/bars-square.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/themes/css3.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- Bar Rating JS -->
<script src="https://cdn.jsdelivr.net/npm/jquery-bar-rating@1.2.2/dist/jquery.barrating.min.js"></script>

<c:set var="isUserLoggedIn" value="${not empty sessionScope.user}" />

<style>
    .hidden {
        display: none;
    }
</style>

<div class="novel-rating">
    <!-- Thay đổi id để phù hợp với Bar Rating -->
    <select id="novelRating">
        <option value=""></option> <!-- Cần có một option rỗng -->
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
    </select>
    <span class="rating-score">
        ${novel.averageRating != null ? String.format("%.2f", novel.averageRating) : "0.00"}
    </span>
   <span class="rating-count">(${novel.ratingCount} ratings)</span>
</div>

<!-- Login Popup -->
<div id="overlay" class="hidden" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); z-index: 1000; justify-content: center; align-items: center;">
    <div id="loginPopup" style="background-color: white; padding: 20px; border-radius: 5px;">
        <h2>Login Required</h2>
        <p>You need to be logged in to rate this novel.</p>
        <button id="closeBtn">Close</button>
        <!-- Thêm form login của bạn vào đây -->
    </div>
</div>

<script>
    // Context Path
    const contextPath = "${pageContext.request.contextPath}";
    console.log("Context Path:", contextPath);

    // Novel ID
    const novelId = "${novel.novelID}";
    const isLoggedIn = "${isUserLoggedIn}" === "true"; // Chú ý: Chuyển đổi thành boolean!

    console.log("Novel ID:", novelId);
    console.log("Is Logged In (boolean):", isLoggedIn);

    $(document).ready(function () {

        let barRatingInstance;

        // Hàm hiển thị form login
        function showLoginForm() {
            console.log("Showing login form");
            $("#overlay").removeClass("hidden");
        }

        // Hàm ẩn form login
        function hideLoginForm() {
            console.log("Hiding login form");
            $("#overlay").addClass("hidden");
        }

        // Xử lý sự kiện click nút đóng form
        $("#closeBtn").on("click", function () {
            hideLoginForm();
        });

        // Đảm bảo overlay đóng khi click ra ngoài form
        $("#overlay").on("click", function (e) {
            if (e.target === this) {
                hideLoginForm();
            }
        });

        // Khởi tạo Bar Rating plugin
        function initBarRating(rating) {
            console.log("Initializing Bar Rating with rating:", rating, "and isLoggedIn:", isLoggedIn);

            barRatingInstance = $('#novelRating').barrating({ // Bỏ readOnly
                theme: 'fontawesome-stars', // Chọn theme phù hợp
                initialRating: rating,
                //readonly: readOnly,  // Loại bỏ readonly
                allowEmpty: false, // Không cho phép bỏ đánh giá
                onSelect: function(value, text, event) {
                    console.log("onSelect triggered. Rating:", value);
                    if (isLoggedIn) { // Kiểm tra isLoggedIn là boolean
                        rateNovel(novelId, value);
                    } else {
                        showLoginForm();
                         // Đặt lại giá trị đã chọn nếu chưa đăng nhập
                         $('#novelRating').barrating('clear');
                    }
                }
            });

             $('#novelRating').barrating('show');  // Hiển thị sau khi khởi tạo
        }

        // Hàm đánh giá novel
        function rateNovel(novelId, score) {
            console.log("Rating novel:", novelId, score);
            submitRating(novelId, score);
        }

        // Ngăn chặn gửi nhiều đánh giá cùng lúc
        let isRating = false;

        // Gửi đánh giá lên server
        function submitRating(novelId, score) {
            if (isRating) {
                console.log("Already submitting rating, ignoring request.");
                return;
            }

            isRating = true;
            console.log("Submitting rating for novel", novelId, "with score", score);
            $.ajax({
                url: contextPath + '/rating', // Sử dụng đúng URL mapping
                type: 'POST',
                data: {novelId: novelId, score: score},
                dataType: 'text', // Dự kiến phản hồi là text
                success: function (response) {
                    console.log("AJAX success: Response from server:", response);
                    if (response === "not_logged_in") {
                        showLoginForm(); // Hiển thị form login
                    } else if (response === "missing_parameters" || response === "invalid_parameters" || response === "invalid_score") {
                        alert("Invalid rating parameters.");
                    } else if (response === "rating_failed") {
                        alert("Rating failed. Please try again.");
                    } else {
                        // Phân tích chuỗi phản hồi
                        const values = response.split(",");
                        if (values.length === 3) {
                            const averageRating = parseFloat(values[0]);
                            const ratingCount = parseInt(values[1]);
                             const userScore = parseInt(values[2]);

                            console.log("Parsed values: averageRating =", averageRating, "ratingCount =", ratingCount, "userScore =", userScore);

                            // Cập nhật giao diện với giá trị mới
                            updateAverageRatingUI(averageRating);
                            updateRatingCountUI(ratingCount);
                            setUserRating(userScore)
                        } else {
                            console.error("Unexpected response format:", response);
                            alert("Unexpected response from server.");
                        }
                    }
                },
                error: function (xhr, status, error) {
                    console.error("AJAX error: Status code:", xhr.status);
                    console.error("AJAX error: Error message:", error);
                    console.error("AJAX error: Full XHR object:", xhr);

                    alert("Error submitting rating. Check the console for details.");
                },
                complete: function () {
                    isRating = false; // Reset flag khi hoàn thành (thành công hoặc lỗi)
                }
            });
        }

        // Cập nhật UI average rating
        function updateAverageRatingUI(averageRating) {
            $('.rating-score').text(averageRating.toFixed(2));
        }

        // Cập nhật UI rating count
        function updateRatingCountUI(ratingCount) {
            $('.rating-count').text(`(${novel.ratingCount} ratings)`); // Sửa lại để lấy từ tham số
        }

        function setUserRating(userScore) {
           //$('#novelRating').barrating('set', userScore);
           $('#novelRating').barrating('destroy'); // Hủy widget
            initBarRating(userScore); // Khởi tạo lại

        }

        // Logic khởi tạo
        let initialRating = parseFloat($('.rating-score').text());
        if (isNaN(initialRating)) {
            initialRating = 0; // Hoặc giá trị mặc định khác nếu cần
        }

        initBarRating(Math.round(initialRating)); // Khởi tạo với rating hiện tại

    });
</script>