   
       // JavaScript từ favorite.jsp (Sửa đổi để quản lý tab)
                const maxHearts = 10;
                const hearts = [];

                function createHeart() {
                    let heart;

                    if (hearts.length < maxHearts) {
                        heart = document.createElement('i');
                        heart.classList.add('fas', 'fa-heart', 'heart');
                        document.body.appendChild(heart);
                        hearts.push(heart);
                    } else {
                        heart = hearts.shift();
                        document.body.appendChild(heart);
                    }

                    const size = Math.random() * 1.5 + 0.5;
                    heart.style.fontSize = size + 'em';

                    const opacity = Math.random() * 0.5 + 0.3;
                    heart.style.opacity = opacity;

                    heart.style.left = Math.random() * 100 + "vw";

                    heart.style.animationDuration = Math.random() * 5 + 5 + "s";

                    heart.style.top = '-100px';

                    heart.addEventListener('animationend', heartEnd);
                }

                function heartEnd(event) {
                    const heart = event.target;
                    document.body.removeChild(heart);
                    hearts.push(heart);
                }

                setInterval(createHeart, 100);

                // Tab management


                // Get the confirmation popup elements
                const confirmationPopup = document.getElementById('confirmationPopup');
                const confirmDeleteButton = document.getElementById('confirmDelete');
                const cancelDeleteButton = document.getElementById('cancelDelete');

                function removeFavorite(novelId) {
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/NovelWeb/favorite", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    xhr.withCredentials = true; // 🔥 Important: Send session cookie

                    xhr.onload = function () {
                        if (xhr.status === 200) {
                            const itemToDelete = document.querySelector(`.favorite-item .remove-button[data-novel-id="${novelId}"]`).closest('.favorite-item');
                            if (itemToDelete) {
                                itemToDelete.remove();
                            }

                            // Kiểm tra xem danh sách còn phần tử nào không
                            const favoritesList = document.getElementById('favorites-list');
                            if (favoritesList.children.length === 0) {
                                // Tạo phần tử <p> để hiển thị thông báo
                                const noFavoritesMessage = document.createElement('p');
                                noFavoritesMessage.innerHTML = 'No favorites yet. Go to the <a href="<c:url value="/novels" />">List</a> to find something you love!';
                                favoritesList.appendChild(noFavoritesMessage);
                            }

                        } else {
                            alert("Failed to update favorite status.");
                        }
                    };

                    xhr.onerror = function () {
                        alert("Request failed.");
                    };

                    xhr.send("novelId=" + novelId);
                }

                // Add event listeners to the remove buttons
                document.querySelectorAll('.favoriteButton').forEach(function (button) {
                    button.addEventListener('click', function () {
                        var novelId = this.getAttribute("data-novel-id");
                        confirmationPopup.style.display = 'flex';
                        confirmDeleteButton.onclick = function () {
                            removeFavorite(novelId);
                            confirmationPopup.style.display = 'none';
                        }

                        cancelDeleteButton.onclick = function () {
                            confirmationPopup.style.display = 'none';
                        }
                    });
                });