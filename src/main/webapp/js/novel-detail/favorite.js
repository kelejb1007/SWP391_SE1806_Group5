 document.getElementById("favoriteButton").addEventListener("click", function() {
        var novelId = this.getAttribute("data-novel-id");
        var confirmationMessage = (this.classList.contains('active')) ? "Are you sure you want to remove this novel from your favorites?" : null;

        if (confirmationMessage && !confirm(confirmationMessage)) {
            return; // User canceled the action
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/NovelWeb/favorite", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.withCredentials = true; // 🔥 Quan trọng: Gửi session cookie
        xhr.onload = function() {
            if (xhr.status === 200) {
                var button = document.getElementById("favoriteButton");
                button.classList.toggle("active");
                var favTextSpan = button.querySelector(".fav-text");
                favTextSpan.textContent = (button.classList.contains("active")) ? "Remove from Favorite" : "Add to Favorite";
                alert("Favorite status updated successfully!");
            } else if (xhr.status === 401) {
                alert("You must be logged in to add/remove favorites.");
            } else {
                alert("Failed to update favorite status.");
            }
        };
        xhr.onerror = function() {
            alert("Request failed.");
        };
        xhr.send("novelId=" + novelId);
    });