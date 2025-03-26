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
        <title>${novel.novelName}</title>

        <!-- CSS hiện có -->
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/mynovel/myNovelDetail(d).css?v=1" type="text/css">
        <link rel="stylesheet" href="css/novel-detail/chapter-list(d).css?v=1">
        <link rel="stylesheet" href="css/novel-detail/novel-detail(d).css">

        <link rel="shortcut icon" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <link rel="stylesheet" type="text/css" href="css/mynovel/mynovel2.css" crossorigin="anonymous">
        <script charset="utf-8" src="js/mynovel/mynovel2.js?v=1" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">



        <!-- Thêm CSS và JS cần thiết từ novelDetail.jsp -->
        <!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
                <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </head>-->
    <body>
        <c:if test="${not empty popup}">
            <script>
                window.onload = function () {
                    alert("${popup}");
                };
            </script>
        </c:if>
        <jsp:include page="/WEB-INF/views/user/mynovel/icon.jsp" /> 
        <div id="root">
            <div class="df"><input class="pf t0 l0 vh" id="foldSwitch" type="checkbox"><input class="pf t0 l0 vh" id="sideSwitch" type="checkbox">
                <jsp:include page="/WEB-INF/views/user/mynovel/sidebar.jsp" />
                <div class="g_main_wrap f1 pr">
                    <div style="">

                        <div class="pf t0 l0 header--Unk0j df jcsb">
                            <div class="undefined g_header df fg1 oh">
                                <div class="df aic g_header_title">
                                    <h2 class="header_title--gwRuS ell dib mw100p t_title_large mb0 vam">
                                        <span class="ttc" style="font-family: Archivo !important; zoom: 1.1">Novels Information</span>
                                    </h2>
                                </div>
                            </div>
                        </div>


                        <div id="main_scroll_container" class="scroller--dBDRL pr">
                            <div class="header_ph--kHZzY"></div>
                            <div class="main_content--0x57a ">
                                <div class="default--zRToH bc_fff ">

                                    <div class="det-hd" data-report-l1="1">
                                        <div class="g_wrap">

                                            <div class="row info" style="padding: 40px 0 30px">
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
                                                    <div class="author"> <strong class="c_s">Genre: </strong> ${novel.genres} </div>
                                                    <div class="author"> <strong class="c_s">Author: </strong> ${novel.author} </div>
                                                    <address class="">
                                                        <div class=""></div>
                                                    </address>
                                                    <div class="bts">
                                                        <div class="novel-buttons">
                                                            <a href="mynovel?action=update&novelID=${novel.novelID}" class="update-button">Update</a>
                                                            <form action="mynovel" method="post" style="display: inline">
                                                                <input type="hidden" name="action" value="delete">
                                                                <input type="hidden" name="novelID" value="${novel.novelID}">
                                                                <button type="submit" class="remove-btn" onclick="return confirm('Are you sure to delete the novel: ${novel.novelName} (ID=${novel.novelID})')">
                                                                    Delete
                                                                </button> 
                                                            </form>
                                                            <a href="${pageContext.request.contextPath}/postChapter?novelId=${novel.novelID}" class="create-button">Post chapter</a>
                                                        </div>
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

                                            <div id="about" class=" tab-content g_wrap">
                                                <div class="mb48">
                                                    <div class="fans-bar-wrap mb32" data-report-l1="11">
                                                        <div class="clearfix mb16">
                                                            <h3 class="g_h2 fs24 lh32 fw700 pl1 ell fl">Fans</h3>
                                                            <div class="fr pt8"> <a class="fs16" href="/ranking/fans/21542016806826305" rel="nofollow" title="See all" data-report-eid="qi_C21"> See all </a> </div>
                                                        </div>
                                                        <div class="fans-list fs0">
                                                            <ol>
                                                                <li class="pl24 pr24 vam"> <i class="dib vam mr24 _badge _badge1"></i> 
                                                                    <a href="/profile/4315703195" rel="nofollow" data-report-eid="qi_C20"> 
                                                                        <i style="border-radius: 100%" class="dib vam mr16 _avatar _avatar1"> 
                                                                            <img cross-origin="anonymous" style="border-radius: 100%" class="db" data-original="//user-pic.webnovel.com/userheadimg/4315703195/200.jpg?uut=1705057760565&amp;imageMogr2/quality/80" alt="Feruru" src="//user-pic.webnovel.com/userheadimg/4315703195/200.jpg?uut=1705057760565&amp;imageMogr2/quality/80" style="display: block;"> 
                                                                        </i> 
                                                                    </a>
                                                                    <div class="dib vam _text"> <a href="/profile/4315703195" class="c_000" rel="nofollow" data-report-eid="qi_C20"> <strong class="db fs16 lh24 ell fw600">Feruru</strong> </a> <small class="db fs12 c_s lh16 ell">Contributed 2265</small> </div>
                                                                </li>
                                                                <li class="pl24 pr24 vam"> 
                                                                    <i class="dib vam mr24 _badge _badge2"></i> 
                                                                    <a href="/profile/4305146262" rel="nofollow" data-report-eid="qi_C20"> 
                                                                        <i style="border-radius: 100%" class="dib vam mr16 _avatar _avatar2"> 
                                                                            <img cross-origin="anonymous" style="border-radius: 100%" class="db" data-original="//user-pic.webnovel.com/userheadimg/4305146262/200.jpg?uut=1678003446842&amp;imageMogr2/quality/80" alt="ryanrayn" src="//user-pic.webnovel.com/userheadimg/4305146262/200.jpg?uut=1678003446842&amp;imageMogr2/quality/80" style="display: block;">
                                                                        </i> 
                                                                    </a>
                                                                    <div class="dib vam _text"> <a href="/profile/4305146262" class="c_000" rel="nofollow" data-report-eid="qi_C20"> <strong class="db fs16 lh24 ell fw600">ryanrayn</strong> </a> <small class="db fs12 c_s lh16 ell">Contributed 2241</small> </div>
                                                                </li>
                                                                <li class="pl24 pr24 vam"> 
                                                                    <i class="dib vam mr24 _badge _badge3"></i>
                                                                    <a href="/profile/4318182466" rel="nofollow" data-report-eid="qi_C20"> 
                                                                        <i style="border-radius: 100%" class="dib vam mr16 _avatar _avatar3"> 
                                                                            <img style="border-radius: 100%" cross-origin="anonymous" class="db" data-original="//user-pic.webnovel.com/userheadimg/4318182466/200.jpg?uut=1628717450420&amp;imageMogr2/quality/80" alt="TheLordOfTheSea1" src="//user-pic.webnovel.com/userheadimg/4318182466/200.jpg?uut=1628717450420&amp;imageMogr2/quality/80" style="display: block;"> 
                                                                        </i>
                                                                    </a>
                                                                    <div class="dib vam _text"> <a href="/profile/4318182466" class="c_000" rel="nofollow" data-report-eid="qi_C20"> <strong class="db fs16 lh24 ell fw600">TheLordOfTheSea1</strong> </a> <small class="db fs12 c_s lh16 ell">Contributed 2219</small> </div>
                                                                </li>
                                                            </ol>
                                                        </div>
                                                    </div>
                                                    <div class="j_tagWrap tm"> </div>
                                                </div>
                                                <div class="mb48"> </div>
                                                <div class="mb48">
                                                    <div class="power-bar-wrap j_power_rank_wrap" data-report-l1="13">
                                                        <div class="power-bar-wrap j_power_rank_wrap" data-report-l1="13">
                                                            <div class="clearfix mb16">
                                                                <p class="g_h2 fs24 lh32 fw700 pl1 ell fl">Weekly Power Status</p>
                                                            </div>
                                                            <div class="power-bar clearfix pl24 pt24 pr24 pb24 mb8" data-report-l1="4">
                                                                <div class="fl fs0 mr24 pt8 pb8 power-rank"> <img cross-origin="anonymous" width="24" height="24" class="div vam mr12 w24 h24" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwBAMAAAClLOS0AAAAJFBMVEX////1Mmb2MWX2N231MWX1MWX/////wtL8nbf+9vj+vM38mLS/meY4AAAABXRSTlMB5qYc7ekm068AAABrSURBVDjLYyADMCuGYgAhA6CESSgW4AyUUMUmEQSUEMUmEQiUCMUKqC8xLQ0JZCJJpKEAKkoQtBwD0FlieTkUVKFJlMMBbSQwLR8sQbKjAwi6sUh0gAHtJBCW09vnODMnzuyMswDAXWSQDgDUBFQyX50DMwAAAABJRU5ErkJggg==" alt="Rank"> <strong class="dib vam fs32 fw700 mr8 lh32"> NO.24 </strong> <small class="dib vam fs12 lh16 c_s" style="white-space:pre">Power
                                                                        Ranking</small> </div>
                                                                <div class="fl fs0 mr24 pt8 pb8"> <img cross-origin="anonymous" width="24" height="24" class="div vam mr12 w24 h24" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAMAAABg3Am1AAAAWlBMVEU8ZvX1+P9vj/vC0P8/afVoi/o/aPU+a/pFbv9LeP9Gb/Y8ZvU8Z/U8aPddgvg7ZvVRePe5yv+uwf+Kpv2asv59mvxYfPh3lflaffZMc/mkuP5nifmXrftfg/fPnuCZAAAAHnRSTlPmAebm5uZkQSUR5tbChOal5ubm5ubm5t2viubLy8rr6OWiAAABEElEQVRIx+2Wy7KCMBBE+5LwVCPi4/r8/99UI+U4aRxw4c7ewilOV5GZ4G841XlfDT4YBuqFd259qKcCxQxwzq3+i0lAOQcAd093KkeBaokY98gmrQKShwKoCkheA1QFJK8BqgIlzwBVgZJngKqA5GOasF4lRF8FIi/xu+yWjUvSXcoeSGzaPIvZHpNvBN8D2ia+3iPda4sGYMCHTOVZpQuAANpGIlW2HgpgG+V1bIEhIHsX4DMAP+AHfAuwf++8IcA6QHkLyYQjGjwMQLzERscaM2Ij8eYg23kkmZfGqAypDWaFOYzJZlHzuDeyrMyFwvL2ymJ5eymyvL12SX58sZP8+NWB5McvJ1qeAa4S5TlXHPcQ906aF1YAAAAASUVORK5CYII=" alt="Stone"> <strong class="dib vam fs32 fw700 mr8 lh32 j_total_power" data-total="372"> 372 </strong> <small class="dib vam fs12 lh16 c_s" style="white-space:pre">Power
                                                                        stone</small> </div>
                                                                <div class="fr fs0 bt_box j_power_btn_area"> <a href="###" rel="nofollow" title="Vote Power Stone" class="bt _warning _2row _vote j_vote_power _on" data-bookid="21542016806826305" data-bookname="A Nascent Kaleidoscope." data-report-eid="qi_C10" data-report-bid="21542016806826305" data-report-cid=""> <strong>Vote</strong> <small class="j_ps_num">(1 power stone left)</small> </a> <a href="###" rel="nofollow" title="Vote Power Stone" class="bt _warning _2row _dis_vote " disabled="disabled" data-bookid="21542016806826305" data-bookname="A Nascent Kaleidoscope." data-report-eid="qi_C10" data-report-bid="21542016806826305" data-report-cid=""> <strong>Vote</strong> <small class="j_ps_num">(0 power stone left)</small> </a> <a href="###" rel="nofollow" title="Vote Power Stone" class="bt _login _warning j_login " data-report-cid="" data-report-eid="qi_C10" data-report-bid="21542016806826305"> <strong> Vote </strong> </a> </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Tab Chapters -->
                                            <div id="chapters" class="tab-content" style="display: none;">
                                                <div class="chapter-content-header">
                                                    <div style="display: flex; align-items:center;">
                                                        <div class="chapter-content-latest-release">
                                                            Latest Release:
                                                            <c:if test="${not empty sortedChapters}">
                                                                <c:set var="lastChapterIndex" value="${sortedChapters.size() - 1}" />
                                                                <c:set var="lastChapter" value="${sortedChapters[lastChapterIndex]}" />
                                                                <a href="${pageContext.request.contextPath}/chapter?id=${lastChapter.chapterID}">${lastChapter.chapterName}</a> 
                                                                <span class="time-string">${lastChapter.timeString}</span>
                                                            </c:if>
                                                        </div>
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
                                                                        if (!sortButton)
                                                                            return;

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
                                                                        if (!chapterList)
                                                                            return;

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
                                                                        if (!chapterList)
                                                                            return;

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