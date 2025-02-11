document.addEventListener('DOMContentLoaded', function() {
    // Existing search button code (if you have it)

    // Tab functionality for Top Novels
    const tabButtons = document.querySelectorAll('.top-novel-tabs button');
    const novelLists = document.querySelectorAll('.top-novel-list');

    tabButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Deactivate all buttons and hide all lists
            tabButtons.forEach(btn => btn.classList.remove('active'));
            novelLists.forEach(list => list.style.display = 'none');

            // Activate the clicked button and show the corresponding list
            this.classList.add('active');
            const tabId = this.dataset.tab;
            document.getElementById(tabId).style.display = 'block';
        });
    });
});





//Nền trang
document.addEventListener('DOMContentLoaded', function() {
    // Existing slider code (if you have it)

    // Background Image Cycling
    const body = document.querySelector('body');
    const backgroundImages = [
        'img/a6.jpg',
        'img/a6.jpg',
        'img/a6.jpg'
    ]; // Array of background images
    let currentBgIndex = 0;
    let bgIntervalId;

    function updateBackground() {
        body.style.backgroundImage = `url('${backgroundImages[currentBgIndex]}')`;
    }

    function startBackgroundCycling() {
        bgIntervalId = setInterval(function() {
            currentBgIndex++;
            if (currentBgIndex >= backgroundImages.length) {
                currentBgIndex = 0;
            }
            updateBackground();
        }, 5000); // Change every 5 seconds
    }

    // Start background cycling
    updateBackground();
    startBackgroundCycling();
});



//
document.addEventListener('DOMContentLoaded', function() {
    const novelButton = document.querySelector('.dropdown-type-button[data-type="novel"]');
    const genreButton = document.querySelector('.dropdown-type-button[data-type="genre"]');
    const novelContent = document.getElementById('novel-content');
    const genreContent = document.getElementById('genre-content');
    const dropdownButtons = document.querySelectorAll('.dropdown-type-button');

    // Mặc định hiển thị Novel, ẩn Genre
    novelContent.classList.add('active'); // Sử dụng class active để hiển thị
    genreContent.classList.remove('active');

    dropdownButtons.forEach(button => {
        button.addEventListener('click', function() {
            const type = this.dataset.type;

            // Remove active class from all buttons
            dropdownButtons.forEach(btn => btn.classList.remove('active'));

            // Add active class to the clicked button
            this.classList.add('active');

            // Ẩn tất cả các content, sau đó hiển thị cái active
            novelContent.classList.remove('active');
            genreContent.classList.remove('active');

            if (type === 'novel') {
                novelContent.classList.add('active');
            } else if (type === 'genre') {
                genreContent.classList.add('active');
            }
        });
    });
});




///
