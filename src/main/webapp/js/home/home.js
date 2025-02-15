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




///

