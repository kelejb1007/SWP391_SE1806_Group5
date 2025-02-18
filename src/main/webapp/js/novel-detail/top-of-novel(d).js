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
