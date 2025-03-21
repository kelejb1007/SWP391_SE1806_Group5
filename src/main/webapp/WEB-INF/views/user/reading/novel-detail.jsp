<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${novel.novelName} - Novel Detail</title>
        <link rel="stylesheet" href="css/home/header(d).css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

        
        <link rel="stylesheet" href="css/novel-detail/novel-detail(d).css">
        <link rel="stylesheet" href="css/novel-detail/rating(d).css">
        <link rel="stylesheet" href="css/novel-detail/chapter-list(d).css">
        <link rel="stylesheet" href="css/novel-detail/top-of-novel(d).css">
        <link rel="stylesheet" href="css/novel-detail/comment(d).css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Macondo+Swash+Caps&display=swap">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/user/components/header.jsp"></jsp:include>

            <main class="novel-detail-page">
            <jsp:include page="/WEB-INF/views/user/components/link.jsp"></jsp:include>





                <div class="novel-header">
                    <div class="novel-detail-container">
                        <div class="novel-cover">
                            <div class="original-tag">ORIGINAL</div>
                            <img src="${novel.imageURL}" alt="${novel.novelName}">
                    </div>
                    <div class="novel-info">
                        <h1 class = "novel-title">${novel.novelName}</h1>
                        <div class="novel-meta">
                            <span class="novel-genre">
                                <i class="fas fa-book"></i>

                                <c:if test="${not empty novel.genreNames}">
                                    <c:forEach var="genre" items="${novel.genreNames}" varStatus="status">
                                        <a href="${pageContext.request.contextPath}/novels?genre=${genre}"
                                           style="color: inherit; text-decoration: none;">
                                            ${genre}
                                        </a>
                                        <c:if test="${!status.last}">, </c:if>
                                    </c:forEach>
                                </c:if>

                            </span>


                            <span class="novel-chapters"><i class="fas fa-list-ol"></i> ${novel.totalChapter} Chapters</span>
                            <span class="novel-views"><i class="fas fa-eye"></i>${views}</span>
                        </div>
                        <div class="novel-author">
                            Author: <span class="author-name">${novel.author}</span>
                        </div>

                        <jsp:include page="/WEB-INF/views/user/reading/rating.jsp" /> 

                        <div class="ranking-award">
                            <div class="ranking">#3 Originals' Power Ranking</div>
                            <div class="award"><i class = "fas fa-trophy"></i> WebNovel Spirity Awards 2024 Gold Winner</div>
                        </div>
                        <div class="novel-description">
                            <p>${novel.novelDescription}</p>
                        </div>
                        <div class="novel-buttons">
                            <a href="chapter?id=${chapters[0].chapterID}" class="read-button">READ</a>
                            <jsp:include page="/WEB-INF/views/user/reading/favorite.jsp" /> 
                        </div>
                    </div>
                </div>
            </div>


            <div class = "novel-content">
                <div class = "chapter-content-section">
                    <div class="g_wrap det-tab-nav mb48 _slide" data-report-l1="14">
                        <a class="_on tab-link" href="#about" data-tab="#about">
                            <span class="chapter-content-title">About</span>
                        </a>
                        <i class="_hr"></i>
                        <a class="tab-link" href="#chapters" data-tab="#chapters">
                            <span class="chapter-content-title">Table of Contents</span>
                        </a>
                        <i class="_line" style="width: 87px; left: 0px;"></i>
                    </div>

                    <div id="about" class="tab-content">
                        <p>${novel.novelDescription}</p>
                    </div>

                    <div id="chapters" class="tab-content" style="display: none;">
                        <div class = "chapter-content-header">
                            <div style = "display: flex; align-items:center;">
                                <div class="chapter-content-latest-release">
                                    Latest Release:
                                    <c:if test="${not empty chapters}">
                                        <c:set var="lastChapterIndex" value="${chapters.size() - 1}" />
                                        <c:set var="lastChapter" value="${chapters[lastChapterIndex]}" />
                                        <a href="${pageContext.request.contextPath}/chapter?id=${lastChapter.chapterID}">${lastChapter.chapterName}</a> <span class="time-string">${lastChapter.timeString}</span>
                                    </c:if>
                                </div>
                            </div>
                            <div class="chapter-sort-options">
                                <c:if test="${not empty chapters and fn:length(chapters) >= 2}">
                                    <button
                                        class="chapter-sort-option"
                                        data-novel-id="${novel.novelID}"
                                        data-sort="${empty sort ? 'asc' : sort}"
                                        onclick="sortChapters('${novel.novelID}', this.getAttribute('data-sort') === 'asc' ? 'desc' : 'asc')"

                                        >
                                        <c:choose>
                                            <c:when test="${empty sort or sort == 'desc'}">
                                                <i class="fas fa-sort-numeric-down-alt"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-sort-numeric-up"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </button>
                                </c:if>
                            </div>
                        </div>
                        <div class="chapter-list">
                            <ul class="chapter-list-ul">
                                <c:if test="${not empty sortedChapters}">
                                    <c:forEach var="chapter" items="${sortedChapters}" varStatus = "status">
                                        <li class="chapter-item">
                                            <div class = "chapter-item-container">
                                                <c:choose>
                                                    <c:when test="${not empty user and chapter.chapterNumber > 3}">
                                                        <a href="chapter?id=${chapter.chapterID}" class="chapter-name">
                                                            <span class="chapter-number">${chapter.chapterNumber}. </span>
                                                            ${chapter.chapterName}
                                                        </a>
                                                    </c:when>
                                                    <c:when test="${not canViewContent && chapter.chapterNumber > 3}">
                                                        <span class="chapter-name locked">
                                                            <span class="chapter-number">${chapter.chapterNumber}. </span>
                                                            ${chapter.chapterName}
                                                        </span>
                                                        <i class="fas fa-lock"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="chapter?id=${chapter.chapterID}" class="chapter-name">
                                                            <span class="chapter-number">${chapter.chapterNumber}. </span>
                                                            ${chapter.chapterName}
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <span class ="chapter-time"> ${chapter.timeString} </span>
                                        </li>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty chapters}">
                                    <p>No Chapters Available yet</p>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div id="comment-top-novel-section">
                <div class="row">
                    <div class="col-lg-8 col-md-7">
                        <section class="comment-section">
                            <jsp:include page="/WEB-INF/views/user/comment/addcomment.jsp"></jsp:include>
                                <h3>Comments</h3>

<c:choose>
    <c:when test="${not empty comments}">
        <div class="comment-list">
            <c:forEach var="comment" items="${comments}">
                <div class="comment-item" id="comment-${comment.commentID}">
                    <div class="comment-header">
                        <span class="comment-user">${comment.fullName}</span>
                        <span class="comment-date">(${comment.commentDate.toString().replace('T', ' ')})</span>
                    </div>
                    <div class="comment-content">
                        <p id="content-${comment.commentID}">${comment.content}</p>
                    </div>
                    <div class="dropdown">
                        <button class="btn icon-button dropdown-toggle" type="button" id="dropdownMenuButton"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-three-dots-vertical"></i>  
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <c:if test="${comment.userID == sessionScope.user.userID}">
                                <li>
                                    <button class="dropdown-item" type="button" onclick="editComment(${comment.commentID})">
                                        <i class="bi bi-pencil"></i> Edit
                                    </button>
                                </li>
                                <li>
                                    <form action="comments" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="novelID" value="${comment.novelID}">
                                        <input type="hidden" name="commentID" value="${comment.commentID}">
                                        <button type="submit" class="dropdown-item" style="border: none; background: none; padding: 0; display: flex; align-items: center;" 
                                            onclick="return confirm('Are you sure you want to delete this comment?');">
                                            <i class="bi bi-trash"></i> Delete
                                        </button>
                                    </form>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                    <!-- Khu vực chỉnh sửa -->
                    <div class="edit-area" id="edit-area-${comment.commentID}" style="display: none;">
                        <textarea id="edit-content-${comment.commentID}" class="form-control">${comment.content}</textarea>
                        <button class="btn btn-primary mt-2" onclick="saveComment(${comment.commentID}, ${comment.novelID})">
                            Save
                        </button>
                        <button class="btn btn-secondary mt-2" onclick="cancelEdit(${comment.commentID})">
                            Cancel
                        </button>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <p class="no-comments">No comments available.</p>
    </c:otherwise>
</c:choose>

<script>
    function editComment(commentID) {
        document.getElementById("content-" + commentID).style.display = "none";
        document.getElementById("edit-area-" + commentID).style.display = "block";
    }

    function cancelEdit(commentID) {
        document.getElementById("edit-area-" + commentID).style.display = "none";
        document.getElementById("content-" + commentID).style.display = "block";
    }

    function saveComment(commentID, novelID) {
        var newContent = document.getElementById("edit-content-" + commentID).value;
        if (newContent.trim() === "") {
            alert("Comment content cannot be empty!");
            return;
        }

        var form = document.createElement("form");
        form.method = "POST";
        form.action = "comments";

        var actionInput = document.createElement("input");
        actionInput.type = "hidden";
        actionInput.name = "action";
        actionInput.value = "update";

        var commentIDInput = document.createElement("input");
        commentIDInput.type = "hidden";
        commentIDInput.name = "commentID";
        commentIDInput.value = commentID;

        var novelIDInput = document.createElement("input");
        novelIDInput.type = "hidden";
        novelIDInput.name = "novelID";
        novelIDInput.value = novelID;

        var contentInput = document.createElement("input");
        contentInput.type = "hidden";
        contentInput.name = "commentContent";
        contentInput.value = newContent;

        form.appendChild(actionInput);
        form.appendChild(commentIDInput);
        form.appendChild(novelIDInput);
        form.appendChild(contentInput);
        document.body.appendChild(form);
        form.submit();
    }
</script>


                        </section>
                    </div>
                    <div class="col-lg-4 col-md-5">
                        <section class="top-novels">
                            <jsp:include page="/WEB-INF/views/user/reading/top-of-novel.jsp"></jsp:include>
                            </section>
                        </div>
                    </div>
                </div>

            <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>



                <script src="js/novel-detail/top-of-novel(d).js"></script>
                <script src="js/novel-detail/chapter-list(d).js"></script>
                <script src="js/novel-detail/rate(d).js"></script>

                <script src="js/novel-detail/favorite.js"></script>
                <script src="js/home/header(d).js"></script>
                <script src="js/lock7.js"></script>
                <script>
                                                            // chapter-list(d).js
                                                            document.addEventListener('DOMContentLoaded', function () {
                                                                setupTabs();
                                                                initializeSortButton();
                                                                initializeChapterList();
                                                            });

                                                            function setupTabs() {
                                                                const tabLinks = document.querySelectorAll('.tab-link');
                                                                const tabContents = document.querySelectorAll('.tab-content');
                                                                const commentTopNovelSection = document.getElementById('comment-top-novel-section');
                                                                const line = document.querySelector('._line');
                                                                const novelContainer = document.querySelector('.novel-detail-container');
                                                                const novelId = novelContainer ? novelContainer.dataset.novelId : null;

                                                                function moveLine(tab) {
                                                                    // Remove active class from all tabs and hide content
                                                                    tabLinks.forEach(link => link.classList.remove('_on'));
                                                                    tabContents.forEach(content => content.style.display = 'none');

                                                                    // Toggle comment section based on active tab
                                                                    if (commentTopNovelSection) {
                                                                        commentTopNovelSection.style.display = (tab.dataset.tab === '#about') ? 'block' : 'none';
                                                                    }

                                                                    // Activate selected tab and show content
                                                                    tab.classList.add('_on');
                                                                    const targetContent = document.querySelector(tab.dataset.tab);
                                                                    if (targetContent) {
                                                                        targetContent.style.display = 'block';
                                                                    }

                                                                    // Move underline
                                                                    if (line) {
                                                                        line.style.left = tab.offsetLeft + 'px';
                                                                        line.style.width = tab.offsetWidth + 'px';
                                                                    }

                                                                    // Save active tab state
                                                                    if (novelId) {
                                                                        localStorage.setItem(`activeTab_${novelId}`, tab.dataset.tab);
                                                                    }
                                                                }

                                                                // Load saved tab state or default to About
                                                                if (novelId) {
                                                                    const activeTab = localStorage.getItem(`activeTab_${novelId}`);
                                                                    if (activeTab) {
                                                                        const savedTab = document.querySelector(`a[data-tab="${activeTab}"]`);
                                                                        if (savedTab) {
                                                                            moveLine(savedTab);
                                                                        }
                                                                    }
                                                                }

                                                                // Set default tab if no saved state
                                                                if (!document.querySelector('.tab-link._on')) {
                                                                    const defaultTab = document.querySelector('a[data-tab="#about"]');
                                                                    if (defaultTab) {
                                                                        moveLine(defaultTab);
                                                                    }
                                                                }

                                                                // Add click handlers
                                                                tabLinks.forEach(tab => {
                                                                    tab.addEventListener('click', function (e) {
                                                                        e.preventDefault();
                                                                        moveLine(this);
                                                                    });
                                                                });
                                                            }

                                                            function initializeSortButton() {
                                                                const sortButton = document.querySelector('.chapter-sort-option');
                                                                if (!sortButton)
                                                                    return;

                                                                const novelId = sortButton.getAttribute("data-novel-id");

                                                                // Get initial sort order from the button's data attribute (set by server)
                                                                // This ensures we always respect the server-side default (asc)
                                                                let currentSort = sortButton.getAttribute("data-sort");

                                                                // Sort chapters immediately on page load using the initial sort
                                                                sortChapters(currentSort);

                                                                // Update initial icon based on current sort
                                                                updateSortIcon(sortButton, currentSort);

                                                                sortButton.addEventListener('click', function (e) {
                                                                    e.preventDefault();

                                                                    // Toggle sort order
                                                                    const newSortOrder = currentSort === "asc" ? "desc" : "asc";

                                                                    try {
                                                                        // Update URL without page reload
                                                                        const url = new URL(window.location.href);
                                                                        url.searchParams.set("sort", newSortOrder);
                                                                        history.pushState({}, '', url.toString());

                                                                        // Sort chapters
                                                                        sortChapters(newSortOrder);

                                                                        // Update sort button icon and state
                                                                        updateSortIcon(sortButton, newSortOrder);

                                                                        // Update the data-sort attribute on the button
                                                                        sortButton.setAttribute('data-sort', newSortOrder);

                                                                        // Update current sort state
                                                                        currentSort = newSortOrder;

                                                                    } catch (error) {
                                                                        console.error('Error during sorting:', error);
                                                                    }
                                                                });
                                                            }


                                                            function updateSortIcon(button, sortOrder) {
                                                                const icon = button.querySelector('i');
                                                                if (icon) {
                                                                    if (sortOrder === 'asc') {
                                                                        icon.className = 'fas fa-sort-numeric-up';
                                                                    } else {
                                                                        icon.className = 'fas fa-sort-numeric-down-alt';
                                                                    }
                                                                }
                                                            }

                                                            function sortChapters(sortOrder) {
                                                                const chapterList = document.querySelector('.chapter-list-ul');
                                                                if (!chapterList)
                                                                    return;

                                                                const chapters = Array.from(chapterList.getElementsByClassName('chapter-item'));

                                                                chapters.sort((a, b) => {
                                                                    const aNum = parseInt(a.querySelector('.chapter-number').textContent);
                                                                    const bNum = parseInt(b.querySelector('.chapter-number').textContent);
                                                                    return sortOrder === 'asc' ? aNum - bNum : bNum - aNum;
                                                                });

                                                                // Clear and repopulate chapter list
                                                                chapterList.innerHTML = '';
                                                                chapters.forEach(chapter => chapterList.appendChild(chapter));
                                                            }

                                                            function updateSortIcon(button, sortOrder) {
                                                                button.innerHTML = sortOrder === "desc"
                                                                        ? '<i class="fas fa-sort-numeric-down-alt"></i>'
                                                                        : '<i class="fas fa-sort-numeric-up"></i>';
                                                            }

                                                            function initializeChapterList() {
                                                                const chapterList = document.querySelector('.chapter-list');
                                                                if (!chapterList)
                                                                    return;

                                                                // Add smooth hover effects
                                                                const chapters = chapterList.getElementsByClassName('chapter-item');
                                                                Array.from(chapters).forEach(chapter => {
                                                                    chapter.addEventListener('mouseenter', function () {
                                                                        this.style.backgroundColor = '#f5f5f5';
                                                                    });

                                                                    chapter.addEventListener('mouseleave', function () {
                                                                        this.style.backgroundColor = '';
                                                                    });
                                                                });

                                                                // Handle locked chapters
                                                                const lockedChapters = chapterList.getElementsByClassName('locked');
                                                                Array.from(lockedChapters).forEach(chapter => {
                                                                    chapter.addEventListener('click', function (e) {
                                                                        e.preventDefault();
                                                                        alert('Please login to read this chapter');
                                                                    });
                                                                });
                                                            }

                                                            // Handle browser back/forward buttons
                                                            window.addEventListener('popstate', function (event) {
                                                                const url = new URL(window.location.href);
                                                                const novelId = url.searchParams.get("id");
                                                                const newSort = url.searchParams.get("sort") || 'asc';

                                                                // Update sort if needed
                                                                const savedSort = localStorage.getItem(`sortOrder_${novelId}`);
                                                                if (savedSort !== newSort) {
                                                                    localStorage.setItem(`sortOrder_${novelId}`, newSort);

                                                                    const sortButton = document.querySelector('.chapter-sort-option');
                                                                    if (sortButton) {
                                                                        updateSortIcon(sortButton, newSort);

                                                                        // Re-sort the chapter list
                                                                        const chapterList = document.querySelector('.chapter-list-ul');
                                                                        if (chapterList) {
                                                                            const chapters = Array.from(chapterList.getElementsByClassName('chapter-item'));
                                                                            chapters.sort((a, b) => {
                                                                                const aNum = parseInt(a.querySelector('.chapter-number').textContent);
                                                                                const bNum = parseInt(b.querySelector('.chapter-number').textContent);
                                                                                return newSort === 'asc' ? aNum - bNum : bNum - aNum;
                                                                            });

                                                                            chapterList.innerHTML = '';
                                                                            chapters.forEach(chapter => chapterList.appendChild(chapter));
                                                                        }
                                                                    }
                                                                }

                                                                // Restore active tab if needed
                                                                const activeTab = localStorage.getItem(`activeTab_${novelId}`);
                                                                if (activeTab) {
                                                                    const savedTab = document.querySelector(`a[data-tab="${activeTab}"]`);
                                                                    if (savedTab) {
                                                                        const event = new Event('click');
                                                                        savedTab.dispatchEvent(event);
                                                                    }
                                                                }
                                                            });
            </script>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
