document.addEventListener('DOMContentLoaded', function() {
    const tabLinks = document.querySelectorAll('.tab-link');
    const tabContents = document.querySelectorAll('.tab-content');
    const commentTopNovelSection = document.getElementById('comment-top-novel-section');
    const line = document.querySelector('._line');  // Get the underline line

    // Function to move the line and handle tab display
    function moveLine(tab) {
        tabLinks.forEach(link => link.classList.remove('_on'));
        tabContents.forEach(content => content.style.display = 'none');
        commentTopNovelSection.style.display = (tab.dataset.tab === '#about') ? 'block' : 'none'; // Toggle based on tab

        tab.classList.add('_on');
        document.querySelector(tab.dataset.tab).style.display = 'block';

        // Calculate the left position and width of the line based on the tab
        line.style.left = tab.offsetLeft + 'px';
        line.style.width = tab.offsetWidth + 'px';
    }

    // Initial setup: Show About and move the line to the About tab
    moveLine(document.querySelector('a[data-tab="#about"]'));

    // Attach click event listeners to the tab links
    tabLinks.forEach(tab => {
        tab.addEventListener('click', function(e) {
            e.preventDefault();
            moveLine(this);  // "this" is the clicked tab
        });
    });
});



 document.addEventListener('DOMContentLoaded', function() {
        const tabLinks = document.querySelectorAll('.tab-link');
        const tabContents = document.querySelectorAll('.tab-content');
        const commentTopNovelSection = document.getElementById('comment-top-novel-section');

        tabContents.forEach(content => {
            content.style.display = 'none';
        });

        document.querySelector('#about').style.display = 'block';
        commentTopNovelSection.style.display = 'block';

        tabLinks.forEach(link => {
            link.addEventListener('click', function(event) {
                event.preventDefault();

                tabLinks.forEach(link => {
                    link.classList.remove('_on');
                });

                tabContents.forEach(content => {
                    content.style.display = 'none';
                });

                this.classList.add('_on');

                const tabId = this.dataset.tab;
                document.querySelector(tabId).style.display = 'block';

                if (tabId === '#about') {
                    commentTopNovelSection.style.display = 'block';
                } else {
                    commentTopNovelSection.style.display = 'none';
                }
            });
        });
    });
    
    //Sort 
  


document.addEventListener('DOMContentLoaded', function() {
    initializeSortButton();
});

function initializeSortButton() {
    const sortButton = document.querySelector('.chapter-sort-option');
    if (sortButton) {
        sortButton.addEventListener('click', function(e) {
            e.preventDefault();
            
            const novelId = this.getAttribute('data-novel-id');
            const currentSort = this.getAttribute('data-sort');
            const newSort = currentSort === 'asc' ? 'desc' : 'asc';
            
            // Lấy context path từ URL hiện tại
            const contextPath = window.location.pathname.split('/')[1];
            sortChapters(novelId, newSort, contextPath);
        });
    }
}

function sortChapters(novelId, sortOrder, contextPath) {
    // Tạo URL với context path
    const url = `/NovelRead/novel-detail?id=${novelId}&sort=${sortOrder}`;
    
    fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP status ${response.status}`);
        }
        return response.text();
    })
    .then(html => {
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;

        // Cập nhật danh sách chapter
        const newChapterList = tempDiv.querySelector('.chapter-list-ul');
        const currentChapterList = document.querySelector('.chapter-list-ul');
        if (newChapterList && currentChapterList) {
            currentChapterList.innerHTML = newChapterList.innerHTML;
        }

        // Cập nhật nút sort
        const sortButton = document.querySelector('.chapter-sort-option');
        if (sortButton) {
            sortButton.innerHTML = sortOrder === 'asc' 
                ? '<i class="fas fa-sort-numeric-up"></i>' 
                : '<i class="fas fa-sort-numeric-down-alt"></i>';
            
            sortButton.setAttribute('data-sort', sortOrder);
            sortButton.setAttribute('data-novel-id', novelId);
        }

        // Giữ tab Table of Contents active
        const tocTab = document.querySelector('[data-tab="#chapters"]');
        if (tocTab) {
            document.querySelectorAll('.tab-link').forEach(tab => tab.classList.remove('_on'));
            tocTab.classList.add('_on');
            
            document.querySelectorAll('.tab-content').forEach(content => {
                content.style.display = 'none';
            });
            
            document.querySelector('#chapters').style.display = 'block';
            
            const commentSection = document.getElementById('comment-top-novel-section');
            if (commentSection) {
                commentSection.style.display = 'none';
            }

            // Cập nhật vị trí line
            const line = document.querySelector('._line');
            if (line) {
                line.style.left = tocTab.offsetLeft + 'px';
                line.style.width = tocTab.offsetWidth + 'px';
            }
        }
    })
    .catch(error => {
        console.error('Sort error:', error);
    });
}