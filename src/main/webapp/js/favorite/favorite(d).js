
const maxHearts = 10;
const hearts = [];

function createHeart() {
    let heart;

    // Lấy chiều cao của header
    const header = document.querySelector('.header'); // Thay '.header' bằng class của header của bạn
    const headerHeight = header ? header.offsetHeight : 60; // Mặc định là 60px nếu không tìm thấy header

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

    // Đặt vị trí bắt đầu của tim ngay dưới header
    heart.style.top = headerHeight + 'px'; // Bắt đầu từ dưới header

    heart.style.animationDuration = Math.random() * 5 + 5 + "s";

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
    xhr.withCredentials = true;

    xhr.onload = function () {
        if (xhr.status === 200) {
            const itemToDelete = document.querySelector(`.favorite-item .remove-button[data-novel-id="${novelId}"]`).closest('.favorite-item');
            if (itemToDelete) {
                itemToDelete.remove();
            }

            // Kiểm tra xem danh sách còn mục yêu thích nào không
            const favoriteItems = document.querySelectorAll('.favorite-item');
            if (favoriteItems.length === 0) {
                // Thay vì chỉ thao tác DOM, làm mới trang để đảm bảo hiển thị đúng
                window.location.reload();
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