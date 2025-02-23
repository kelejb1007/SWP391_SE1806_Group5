 // Context Path
    const contextPath = "${pageContext.request.contextPath}";
    console.log("Context Path:", contextPath);

    // Novel ID
    const novelId = "${novel.novelID}";
    const isLoggedIn = "${isUserLoggedIn}"; // Get the boolean value directly from JSP

    console.log("Novel ID:", novelId);
    console.log("Is Logged In (boolean):", isLoggedIn);

    $(document).ready(function () {

        let barRatingInstance;

        // Function to show the login form
        function showLoginForm() {
            console.log("Showing login form");
            $("#overlay").css("display", "flex");
        }

        // Function to hide the login form
        function hideLoginForm() {
            console.log("Hiding login form");
            $("#overlay").css("display", "none");
        }

        // Close button event
        $("#closeBtn").on("click", function () {
            hideLoginForm();
        });

        // Ensure overlay closes when clicking outside the form
        $("#overlay").on("click", function (e) {
            if (e.target === this) {
                hideLoginForm();
            }
        });

        // Initialize Bar Rating plugin
        function initBarRating(rating) {
            console.log("Initializing Bar Rating with rating:", rating, "and isLoggedIn:", isLoggedIn);

            // Mặc định là chỉ đọc nếu chưa đăng nhập
            let readOnly = isLoggedIn !== "true";
            console.log("Bar Rating readOnly:", readOnly);

            barRatingInstance = $('#novelRating').barrating({
                theme: 'fontawesome-stars', // Chọn theme phù hợp
                initialRating: rating,
                readonly: readOnly,
                allowEmpty: false, // Không cho phép bỏ đánh giá
                onSelect: function(value, text, event) {
                    console.log("onSelect triggered. Rating:", value);
                    if (isLoggedIn === "true") {
                        rateNovel(novelId, value);
                    } else {
                        showLoginForm();
                         // Đặt lại giá trị đã chọn nếu chưa đăng nhập
                         $('#novelRating').barrating('clear');
                    }
                }
            });

             $('#novelRating').barrating('show');  // Khởi tạo sau khi định nghĩa
        }

        // Rate the novel
        function rateNovel(novelId, score) {
            console.log("Rating novel:", novelId, score);
            submitRating(novelId, score);
        }

        // Prevent multiple submissions
        let isRating = false;

        // Submit the rating to the server
        function submitRating(novelId, score) {
            if (isRating) {
                console.log("Already submitting rating, ignoring request.");
                return;
            }

            isRating = true;
            console.log("Submitting rating for novel", novelId, "with score", score);
            $.ajax({
                url: contextPath +'/rating', // Use the correct URL mapping
                type: 'POST',
                data: {novelId: novelId, score: score},
                dataType: 'text', // Expecting plain text response
                success: function (response) {
                    console.log("AJAX success: Response from server:", response);
                    if (response === "not_logged_in") {
                        showLoginForm(); // Show the login form
                    } else if (response === "missing_parameters" || response === "invalid_parameters" || response === "invalid_score") {
                        alert("Invalid rating parameters.");
                    } else if (response === "rating_failed") {
                        alert("Rating failed. Please try again.");
                    } else {
                        // Parse the comma-separated values
                        const values = response.split(",");
                        if (values.length === 3) {
                            const averageRating = parseFloat(values[0]);
                            const ratingCount = parseInt(values[1]);
                             const userScore = parseInt(values[2]);


                            console.log("Parsed values: averageRating =", averageRating, "ratingCount =", ratingCount, "userScore =", userScore);

                            // Update the UI with the new values
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
                    isRating = false; // Reset flag on completion (success or error)
                }
            });
        }

        // Update the average rating UI
        function updateAverageRatingUI(averageRating) {
            $('.rating-score').text(averageRating.toFixed(2));
        }

        // Update the rating count UI
        function updateRatingCountUI(ratingCount) {
            $('.rating-count').text(`(${novel.ratingCount} ratings)`); // Sửa lại để lấy từ tham số
        }

        function setUserRating(userScore) {
           //$('#novelRating').barrating('set', userScore);
           $('#novelRating').barrating('destroy'); // Destroy the widget
            initBarRating(userScore); // Reinitialize it

        }

        // Initialization logic
        let initialRating = parseFloat($('.rating-score').text());
        if (isNaN(initialRating)) {
            initialRating = 0; // Hoặc giá trị mặc định khác nếu cần
        }

        initBarRating(Math.round(initialRating)); // Khởi tạo với rating hiện tại

        // Disable rating if not logged in
         if (isLoggedIn === "true") {
            $('#novelRating').barrating('readonly', false);
        } else {
            $('#novelRating').barrating('readonly', true);
        }
    });