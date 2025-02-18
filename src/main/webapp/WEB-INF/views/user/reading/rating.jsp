<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>

<div class="novel-rating">
    <c:choose>
        <c:when test="${not empty user}">
            <%-- User is logged in --%>
            <div id="rateYo"></div>
            <span class="rating-score">
                ${novel.averageRating != null ? String.format("%.2f", novel.averageRating) : "0.00"}
            </span>
            <span class="rating-count">(${ratingCount} ratings)</span>
        </c:when>
        <c:otherwise>
            <%-- User is NOT logged in --%>
            <c:forEach begin="1" end="5" var="i">
                <span class="star rating-star"
                      data-novel-id="${novel.novelID}"
                      data-score="${i}">
                    <a href="#" class="rating-link">
                        <c:choose>
                            <c:when test="${i <= novel.averageRating}">
                                <i class="fas fa-star active"></i>
                            </c:when>
                            <c:when test="${(i - 0.5) <= novel.averageRating}">
                                <i class="fas fa-star-half-alt active"></i>
                            </c:when>
                            <c:otherwise>
                                <i class="far fa-star"></i>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </span>
            </c:forEach>
            <span class="rating-score">
                ${novel.averageRating != null ? String.format("%.2f", novel.averageRating) : "0.00"}
            </span>
            <span class="rating-count">(${ratingCount} ratings)</span>
        </c:otherwise>
    </c:choose>
</div>

<!-- Login Popup (make sure this exists in your page) -->
<div id="overlay" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); z-index: 1000; display: none; justify-content: center; align-items: center;">
    <div id="loginPopup" style="background-color: white; padding: 20px; border-radius: 5px;">
        <h2>Login Required</h2>
        <p>You need to be logged in to rate this novel.</p>
        <button id="closeBtn">Close</button>
        <!-- Add your login form here -->
    </div>
</div>


<script>
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
                },
                 complete: function() {
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
</script>