document.addEventListener('DOMContentLoaded', function() {
    setupTabs();
    initializeSortButton();
});

// 🚀 Xử lý Tab UI
function setupTabs() {
    const tabLinks = document.querySelectorAll('.tab-link');
    const tabContents = document.querySelectorAll('.tab-content');
    const commentTopNovelSection = document.getElementById('comment-top-novel-section');
    const line = document.querySelector('._line');  

    function moveLine(tab) {
        tabLinks.forEach(link => link.classList.remove('_on'));
        tabContents.forEach(content => content.style.display = 'none');
        commentTopNovelSection.style.display = (tab.dataset.tab === '#about') ? 'block' : 'none';

        tab.classList.add('_on');
        document.querySelector(tab.dataset.tab).style.display = 'block';

        // Di chuyển line underline
        if (line) {
            line.style.left = tab.offsetLeft + 'px';
            line.style.width = tab.offsetWidth + 'px';
        }
    }

    // Mặc định chọn tab "About"
    const defaultTab = document.querySelector('a[data-tab="#about"]');
    if (defaultTab) moveLine(defaultTab);

    tabLinks.forEach(tab => {
        tab.addEventListener('click', function(e) {
            e.preventDefault();
            moveLine(this);
        });
    });
}

// 🚀 Xử lý Sort
function initializeSortButton() {
    const sortButton = document.querySelector('.chapter-sort-option');
    if (!sortButton) return;

    sortButton.addEventListener('click', function(e) {
        e.preventDefault();

        const novelId = this.getAttribute('data-novel-id');
        const currentSort = this.getAttribute('data-sort');
        const newSort = currentSort === 'asc' ? 'desc' : 'asc';

        sortChapters(novelId, newSort);
    });
}

function sortChapters(novelId, sortOrder) {
    const url = `/NovelWeb/novel-detail?id=${novelId}&sort=${sortOrder}`;

    fetch(url, { method: 'GET', headers: { 'Accept': 'text/html' } })
        .then(response => {
            if (!response.ok) throw new Error(`HTTP status ${response.status}`);
            return response.text();
        })
        .then(html => {
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = html;

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
            }

            // Giữ tab "Table of Contents" active
            const tocTab = document.querySelector('[data-tab="#chapters"]');
            if (tocTab) {
                document.querySelectorAll('.tab-link').forEach(tab => tab.classList.remove('_on'));
                tocTab.classList.add('_on');

                document.querySelectorAll('.tab-content').forEach(content => {
                    content.style.display = 'none';
                });
                document.querySelector('#chapters').style.display = 'block';

                const commentSection = document.getElementById('comment-top-novel-section');
                if (commentSection) commentSection.style.display = 'none';

                // Cập nhật vị trí underline
                const line = document.querySelector('._line');
                if (line) {
                    line.style.left = tocTab.offsetLeft + 'px';
                    line.style.width = tocTab.offsetWidth + 'px';
                }
            }
        })
        .catch(error => console.error('Sort error:', error));
}
