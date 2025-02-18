function showLoginPopup() {
    document.getElementById('loginPopup').style.display = 'block';
}
function hideLoginPopup(){
    document.getElementById('loginPopup').style.display = 'none';
}



function sortChapters(novelId, sortOrder) {
    fetch(`/NovelWeb/novel?id=${novelId}&sort=${sortOrder}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            const chapterSection = document.querySelector('.chapter-content-section');
            if (chapterSection) {
                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = html;
                const newChapterContentSection = tempDiv.querySelector('.chapter-content-section');
                if (newChapterContentSection) {
                    chapterSection.innerHTML = newChapterContentSection.innerHTML;
                      // Update the sort button class
                    const sortButton = chapterSection.querySelector('.chapter-sort-option');
                    if(sortButton){
                        if(sortOrder === 'asc'){
                           sortButton.innerHTML = '<i class="fas fa-sort-numeric-up"></i>';
                         } else {
                           sortButton.innerHTML = '<i class="fas fa-sort-numeric-down-alt"></i>';
                         }
                          sortButton.setAttribute('onclick', `sortChapters(${novelId}, '${sortOrder === 'asc' ? 'desc' : 'asc'}')`);
                       }
                }
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}

//chặn copy
document.addEventListener('DOMContentLoaded', function () {
    document.addEventListener('copy', function (event) {
        event.preventDefault();
        alert('Bạn không thể sao chép nội dung trên trang này!');
    });

    document.addEventListener('contextmenu', function (event) {
        event.preventDefault();
        alert('Chuột phải đã bị vô hiệu hóa!');
    });

    document.addEventListener('keydown', function (event) {
        if (event.ctrlKey && (event.key === 'c' || event.key === 'u')) {
            event.preventDefault();
            alert('Bạn không thể sao chép hoặc xem mã nguồn!');
        }
    });
});


// favorite


// Toggle yêu thích
   window.toggleFavorite = function (novelId) {
    const favButton = document.querySelector(`.favorite-btn[data-novel-id="${novelId}"]`);
    if (!favButton) {
        console.error("Favorite button not found for novel ID:", novelId);
        return;
    }

    const isFavorite = favButton.classList.contains("active");
    if (isFavorite && !confirm("Are you sure you want to remove this from favorites?")) return;

    fetch("/NovelWeb/favorite", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `novelId=${novelId}`,
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(`HTTP error! Status: ${response.status}, Message: ${text}`); });
        }
        return response.text();
    })
    .then(data => {
        if (data === "success") {
            favButton.classList.toggle("active");
            const favText = favButton.querySelector(".fav-text");
            favText.textContent = favButton.classList.contains("active") ? "Remove from Favorite" : "Add to Favorite";
            // Use a standard alert if customAlert is not defined
            alert(isFavorite ? "Removed from favorites!" : "Added to favorites!");
        } else if (data === "not_logged_in") {
            alert("You must be logged in to add to favorites."); // More specific message
            showLoginForm(); // Assuming you have a function to show the login form
        }
         else if (data === "missing_novelId") {
            alert("Missing novel ID. Please try again.");
        }
        else {
            alert("Failed to update favorite: " + data); // Show error message from server
        }
    })
    .catch(error => {
        console.error("Error", error);
        alert("Error updating favorite: " + error.message); // Show a more informative error
    });
};






document.addEventListener('DOMContentLoaded', function() {
    const ratingLinks = document.querySelectorAll('.rating-link');

    ratingLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();

            const starSpan = this.parentNode;
            const novelId = starSpan.getAttribute('data-novel-id');
            const score = starSpan.getAttribute('data-score');

            if (novelId && score) {
                checkLoginStatus(function(isLoggedIn) { // Gọi hàm kiểm tra trạng thái đăng nhập
                    if (isLoggedIn) {
                        rateNovel(novelId, score);
                    } else {
                        showLoginForm();
                    }
                });
            } else {
                console.error("Could not get novelId or score from rating star.");
            }
        });
    });
});

function checkLoginStatus(callback) {
    fetch('/NovelWeb/checkLogin') // Tạo một endpoint trên server để kiểm tra trạng thái đăng nhập
        .then(response => response.text())
        .then(data => {
            callback(data === "logged_in"); // Giả sử server trả về "logged_in" nếu đã đăng nhập
        })
        .catch(error => {
            console.error("Error checking login status:", error);
            callback(false); // Coi như chưa đăng nhập nếu có lỗi
        });
}

//Rating
// Context Path (very important!)
    const contextPath = "${pageContext.request.contextPath}";
    console.log("Context Path:", contextPath);

    // Novel ID and User Rating from JSP
    const novelId = "${novel.novelID}";
    const userRating = "${userRate != null ? userRate.score : 0}";
    const isLoggedIn = "${not empty user}"; //This is a String, convert to boolean carefully!

    console.log("Novel ID:", novelId);
    console.log("User Rating:", userRating);
    console.log("Is Logged In (string):", isLoggedIn);


    $(document).ready(function(){

        let rateYoInstance;

        // Function to show the login form
        function showLoginForm() {
            console.log("Showing login form");
            $("#overlay").css("display", "flex"); // Use jQuery to show the overlay
        }

        // Function to hide the login form
        function hideLoginForm() {
            console.log("Hiding login form");
             $("#overlay").css("display", "none"); // Use jQuery to hide the overlay
        }

        // Close button event
        $("#closeBtn").on("click", function() {
            hideLoginForm();
        });

        // Ensure overlay closes when clicking outside the form
        $("#overlay").on("click", function(e) {
            if (e.target === this) {
                hideLoginForm();
            }
        });

        // Initialize RateYo plugin
        function initRateYo(rating) {
            console.log("Initializing RateYo with rating:", rating, "and isLoggedIn:", isLoggedIn);

            const readOnly = isLoggedIn !== "true"; //Important: Convert string to boolean!
            console.log("RateYo readOnly:", readOnly);

            rateYoInstance = $("#rateYo").rateYo({
                rating: rating,
                fullStar: true,
                starWidth: "20px",
                readOnly: readOnly,
                onSet: function (rating, rateYoInstance) {
                    console.log("onSet triggered. Rating:", rating);
                    if (isLoggedIn === "true") { // Also convert here!
                        rateNovel(novelId, rating);
                    } else {
                        showLoginForm();
                    }
                }
            }).get(0);
        }

        // Rate the novel
        function rateNovel(novelId, score) {
            console.log("Rating novel:", novelId, score);
            submitRating(novelId, score);
        }

        // Submit the rating to the server
        function submitRating(novelId, score) {
            console.log("Submitting rating for novel", novelId, "with score", score);
            $.ajax({
                url: contextPath + '/submitrate',
                type: 'POST',
                data: { novelId: novelId, score: score },
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
                            setUserRating(userScore);
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
                }
            });
        }

        // Update the average rating UI
        function updateAverageRatingUI(averageRating) {
            $('.rating-score').text(averageRating.toFixed(2));
        }

        // Update the rating count UI
        function updateRatingCountUI(ratingCount) {
            $('.rating-count').text(`(${ratingCount} ratings)`);
        }

        // Set the user's rating
        function setUserRating(userScore) {
            $("#rateYo").rateYo("rating", userScore);
        }

        // Initialization logic
        if (isLoggedIn === "true") { // Convert to boolean here too!
            initRateYo(userRating);
        } else {
            initRateYo(0); // Initialize with rating = 0 if not logged in.
            //If not logged in then display default star rating form
        }

    });
    
    
    //
    