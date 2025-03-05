
//Header
document.addEventListener('DOMContentLoaded', function() {
    const novelButton = document.querySelector('.dropdown-type-button[data-type="novel"]');
    const genreButton = document.querySelector('.dropdown-type-button[data-type="genre"]');
    const novelContent = document.getElementById('novel-content');
    const genreContent = document.getElementById('genre-content');
    const dropdownButtons = document.querySelectorAll('.dropdown-type-button');

    // Mặc định hiển thị Novel
    novelContent.style.display = 'block';
    genreContent.style.display = 'none';

    dropdownButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Remove active class from all buttons
            dropdownButtons.forEach(btn => btn.classList.remove('active'));

            // Add active class to the clicked button
            this.classList.add('active');

            // Hiển thị nội dung tương ứng
            if (this.dataset.type === 'novel') {
                novelContent.style.display = 'block';
                genreContent.style.display = 'none';
            } else {
                novelContent.style.display = 'none';
                genreContent.style.display = 'block';
            }
        });
    });
});

function toggleAvatarDropdown() {
    const dropdownMenu = document.getElementById("avatarDropdownMenu");
    if (dropdownMenu) {
        dropdownMenu.classList.toggle("show");
    } else {
        console.error("Avatar dropdown menu element not found!");
    }
}
window.addEventListener('click', function(event) {
    if (!event.target.closest(".avatar-dropdown1")) {
        const dropdownMenu = document.getElementById("avatarDropdownMenu");
        if (dropdownMenu) {
            dropdownMenu.classList.remove("show");
        }
    }
});



///

