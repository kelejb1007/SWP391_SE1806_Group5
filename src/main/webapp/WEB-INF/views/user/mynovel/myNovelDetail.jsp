<%-- 
    Document   : viewMyNovelDetail
    Created on : Feb 23, 2025, 2:13:43 PM
    Author     : Nguyen Thanh Trung
--%>

<%@page import="model.Novel" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${novel.novelName} - My Novel Detail</title>

        <!-- CSS hiện có -->
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/mynovel/myNovelDetail(d).css" type="text/css">
        <link rel="stylesheet" href="css/novel-detail/chapter-list(d).css">
        <link rel="stylesheet" href="css/novel-detail/novel-detail(d).css">

        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/home/header(d).css">

        <!-- Thêm CSS và JS cần thiết từ novelDetail.jsp -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <style>
            .chapter-actions {
                display: none;
                margin-left: 10px;
            }
            .chapter-item:hover .chapter-actions {
                display: inline-block;
            }
            .chapter-actions a, .chapter-actions button {
                padding: 5px 10px;
                margin: 0 5px;
                border-radius: 50px;
                text-decoration: none;
                font-size: 14px;
                cursor: pointer;
                border: 1px solid #000;
                background-color: transparent;
                color: #000;
                transition: all 0.3s ease;
            }
            .chapter-actions a.update-btn:hover {
                background-color: #007bff;
                color: white;
                border-color: #007bff;
            }
            .chapter-actions button.delete-btn:hover {
                background-color: #dc3545;
                color: white;
                border-color: #dc3545;
            }
            /* Đảm bảo layout của chapter-item */
            .chapter-item-container {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .chapter-name {
                flex-grow: 1;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/user/components/header.jsp"></jsp:include>

        <div class="det-hd" data-report-l1="1">
            <div class="g_wrap">
                <p class="link"> 
                    <a class="a1" href="homepage" class=""> 
                        <i class="fa fa-home fa-fw"></i>
                    </a> 
                    <span>/</span> 
                    <a class="a2" href="mynovel" title="My novels"> My novels </a>
                    <span>/</span> 
                    <span>${novel.novelName}</span> 
                </p>

                <div class="row info" style="padding: 0">
                    <div class="col-md-4 _sd" style="margin-left: 0; padding: 0;"> 
                        <i class="g_thumb"> 
                            <img cross-origin="anonymous" 
                                 src="${novel.imageURL}" 
                                 alt="${novel.novelName}" 
                                 title="${novel.novelName}"> 
                        </i> 
                    </div>
                    <div class="col-md-8 _mn" style="padding: 0">
                        <h1>${novel.novelName}</h1>
                        <div class="detail"> 
                            <strong class="sub"><i class="fa fa-file-text fa-fw"></i> ${novel.totalChapter} Chapters</strong>  
                            <strong class="sub"><i class="fa fa-eye fa-fw faa"></i> ${novel.viewCount} Views</strong> 
                        </div>
                        <div class="author"> <strong class="c_s">Author: </strong> ${novel.author} </div>
                        <address class="">
                            <div class=""></div>
                        </address>
                        <div class="bts">
                            <div class="novel-buttons">
                                <a href="mynovel?action=update&novelID=${novel.novelID}" class="update-button">Update</a>
                                <form action="mynovel" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="novelID" value="${novel.novelID}">
                                    <button type="submit" class="remove-btn" onclick="return confirm('Are you sure to delete the novel: ${novel.novelName} (ID=${novel.novelID})')">Delete</button>
                                </form>
                                <a href="${pageContext.request.contextPath}/postChapter?novelId=${novel.novelID}" class="create-button">Post chapter</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        <!-- Nội dung tab -->
        <div class="novel-content">
            <div class="chapter-content-section">
                <!-- Tab điều hướng -->
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
                <!-- Tab About -->
                <div id="about" class="tab-content g_wrap">
                    <h3>Description</h3>
                    <p>${novel.novelDescription}</p>
                </div>

                <!-- Tab Chapters -->
                <div id="chapters" class="tab-content" style="display: none;">
                    <div class="chapter-content-header">
                        <div style="display: flex; align-items:center;">
                           
                        </div>
                        <div class="chapter-sort-options">
                            <c:if test="${not empty sortedChapters and fn:length(sortedChapters) >= 2}">
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
                                <c:forEach var="chapter" items="${sortedChapters}" varStatus="status">
                                    <li class="chapter-item">
                                        <div class="chapter-item-container">
                                            <a href="${pageContext.request.contextPath}/chapter?id=${chapter.chapterID}" class="chapter-name">
                                                <span class="chapter-number">${chapter.chapterNumber}. </span>
                                                ${chapter.chapterName}
                                            </a>
                                            <div class="chapter-actions">
                                                <a href="${pageContext.request.contextPath}/updateChapter?chapterId=${chapter.chapterID}&novelId=${novel.novelID}" class="update-btn">Update</a>
                                                <form action="${pageContext.request.contextPath}/deleteChapter?novelId=${novel.novelID}&chapterId=${chapter.chapterID}" method="post" style="display: inline;">
                                                    <input type="hidden" name="chapterId" value="${chapter.chapterID}">
                                                    <input type="hidden" name="novelId" value="${novel.novelID}">
                                                    <button type="submit" class="delete-btn" onclick="return confirm('Are you sure to delete the chapter: ${chapter.chapterName} from novel: ${novel.novelName} ?')">Delete</button>
                                                </form>
                                            </div>
                                        </div>
                                        <span class="chapter-time">${chapter.timeString}</span>
                                    </li>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty sortedChapters}">
                                <p>No Chapters Available yet</p>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
                        </div>


        <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>

        <script src="js/home/header(d).js"></script>
        <script src="js/novel-detail/chapter-list(d).js"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                setupTabs();
                initializeSortButton();
                initializeChapterList();
            });

            function setupTabs() {
                const tabLinks = document.querySelectorAll('.tab-link');
                const tabContents = document.querySelectorAll('.tab-content');
                const line = document.querySelector('._line');
                const novelContainer = document.querySelector('.det-hd');
                const novelId = novelContainer ? novelContainer.dataset.novelId : null;

                function moveLine(tab) {
                    tabLinks.forEach(link => link.classList.remove('_on'));
                    tabContents.forEach(content => content.style.display = 'none');

                    tab.classList.add('_on');
                    const targetContent = document.querySelector(tab.dataset.tab);
                    if (targetContent) {
                        targetContent.style.display = 'block';
                    }

                    if (line) {
                        line.style.left = tab.offsetLeft + 'px';
                        line.style.width = tab.offsetWidth + 'px';
                    }

                    if (novelId) {
                        localStorage.setItem(`activeTab_${novelId}`, tab.dataset.tab);
                    }
                }

                if (novelId) {
                    const activeTab = localStorage.getItem(`activeTab_${novelId}`);
                    if (activeTab) {
                        const savedTab = document.querySelector(`a[data-tab="${activeTab}"]`);
                        if (savedTab) {
                            moveLine(savedTab);
                        }
                    }
                }

                if (!document.querySelector('.tab-link._on')) {
                    const defaultTab = document.querySelector('a[data-tab="#about"]');
                    if (defaultTab) {
                        moveLine(defaultTab);
                    }
                }

                tabLinks.forEach(tab => {
                    tab.addEventListener('click', function (e) {
                        e.preventDefault();
                        moveLine(this);
                    });
                });
            }

            function initializeSortButton() {
                const sortButton = document.querySelector('.chapter-sort-option');
                if (!sortButton) return;

                const novelId = sortButton.getAttribute("data-novel-id");
                let currentSort = sortButton.getAttribute("data-sort");

                sortChapters(currentSort);
                updateSortIcon(sortButton, currentSort);

                sortButton.addEventListener('click', function (e) {
                    e.preventDefault();
                    const newSortOrder = currentSort === "asc" ? "desc" : "asc";

                    try {
                        const url = new URL(window.location.href);
                        url.searchParams.set("sort", newSortOrder);
                        history.pushState({}, '', url.toString());

                        sortChapters(newSortOrder);
                        updateSortIcon(sortButton, newSortOrder);
                        sortButton.setAttribute('data-sort', newSortOrder);
                        currentSort = newSortOrder;
                    } catch (error) {
                        console.error('Error during sorting:', error);
                    }
                });
            }

            function updateSortIcon(button, sortOrder) {
                button.innerHTML = sortOrder === "desc"
                    ? '<i class="fas fa-sort-numeric-down-alt"></i>'
                    : '<i class="fas fa-sort-numeric-up"></i>';
            }

            function sortChapters(sortOrder) {
                const chapterList = document.querySelector('.chapter-list-ul');
                if (!chapterList) return;

                const chapters = Array.from(chapterList.getElementsByClassName('chapter-item'));

                chapters.sort((a, b) => {
                    const aNum = parseInt(a.querySelector('.chapter-number').textContent);
                    const bNum = parseInt(b.querySelector('.chapter-number').textContent);
                    return sortOrder === 'asc' ? aNum - bNum : bNum - aNum;
                });

                chapterList.innerHTML = '';
                chapters.forEach(chapter => chapterList.appendChild(chapter));
            }

            function initializeChapterList() {
                const chapterList = document.querySelector('.chapter-list');
                if (!chapterList) return;

                const chapters = chapterList.getElementsByClassName('chapter-item');
                Array.from(chapters).forEach(chapter => {
                    chapter.addEventListener('mouseenter', function () {
                        this.style.backgroundColor = '#f5f5f5';
                    });

                    chapter.addEventListener('mouseleave', function () {
                        this.style.backgroundColor = '';
                    });
                });
            }

            window.addEventListener('popstate', function (event) {
                const url = new URL(window.location.href);
                const novelId = url.searchParams.get("id");
                const newSort = url.searchParams.get("sort") || 'asc';

                const savedSort = localStorage.getItem(`sortOrder_${novelId}`);
                if (savedSort !== newSort) {
                    localStorage.setItem(`sortOrder_${novelId}`, newSort);

                    const sortButton = document.querySelector('.chapter-sort-option');
                    if (sortButton) {
                        updateSortIcon(sortButton, newSort);

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
    </body>
</html>