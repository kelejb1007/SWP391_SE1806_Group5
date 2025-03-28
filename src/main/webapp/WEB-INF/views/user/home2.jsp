<%-- 
    Document   : home2
    Created on : Mar 17, 2025, 3:12:09 PM
    Author     : Nguyen Thanh Trung
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Novel Reader</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut") %>">
        <link rel="stylesheet" href="css/homepage2/home2.css">
        <link rel="stylesheet" href="css/homepage2/root.css?v=1">
        <link rel="stylesheet" href="css/homepage2/header2.css?v=1">
        <link rel="stylesheet" href="css/homepage2/layout2.css?v=2">
        <link rel="stylesheet" href="css/homepage2/index.css">
        <link rel="stylesheet" href="css/homepage2/footer.css">
        
        <script src="https://kit.fontawesome.com/73b63a545d.js" crossorigin="anonymous"></script>

        <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&display=swap" rel="stylesheet">

        <style>
            @keyframes zoom {
                0%, 100% {
                    transform: scale(1);
                }
                50% {
                    transform: scale(1.2);
                }
            }

            .hot {
                font-family: 'Bebas Neue', sans-serif;
                font-size: 30px;
                font-weight: bold;
                color: red;
                text-transform: uppercase;
                letter-spacing: 2px;
                text-shadow: 0 0 10px rgba(255, 0, 0, 0.8),
                    0 0 20px rgba(255, 0, 0, 0.6);
                display: inline-block;
                animation: zoom 1s infinite;
            }
            .footerrr {
                color: #333;
                text-align: center;
                padding: 15px 0;
                margin-top: 30px;
                /* background-color: rgba(70, 130, 180, 0.2); /* Darker Blue with 60% opacity */
                background-color: rgba(255, 255, 255, 0.1);
                box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); /* Adjusted shadow */
                font-family: 'Macondo Swash Caps', cursive;
                background-size: cover
            }

        </style>
        <!--    <style>
                .fa-solid, .fas {
                    font-family: "Font Awesome 6 Free";
                    font-weight: 900;
                }
                @keyframes zoom {
                    0%, 100% {
                        transform: scale(1);
                    }
                    50% {
                        transform: scale(1.2);
                    }
                }
                .hot {
                    font-size: 20px;
                    font-weight: bold;
                    color: red;
                    display: inline-block;
                    animation: zoom 1s infinite;
                }
            </style>-->

    </head>
    <body class="g_site_readnovel">
        <div class="wrap home">
            <jsp:include page="/WEB-INF/views/user/components/header2.jsp" /> 

            <div class="index-wrap" style="zoom: 1.1;">
                <div class="flower left"></div>
                <div class="flower right"></div>

                <div class="box-center">
                    <div class="focus-wrap mb20 cf">
                        <div class="focus-slider-wrap cf" data-l1="1">

                            <div id="j-focus-slider" class="yx-rotaion fl"">
                                <ul class="rotaion_list">

                                    <li>
                                        <a href="#">
                                            <img style="object-fit: cover; object-position: center center" src="img/bg1.jpg" 
                                                 alt=""></a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <img style="object-fit: cover; object-position: center top" src="https://i.pinimg.com/originals/c9/a7/ea/c9a7ea8c9a30007b9b5fc9de1172aff6.gif" 
                                                 alt=""></a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <img style="object-fit: cover; object-position: center center" src="img/bg2.jpg" 
                                                 alt=""></a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <img style="object-fit: cover; object-position: center center" src="img/bg4.jpg" 
                                                 alt=""></a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <img style="object-fit: cover; object-position: center top" src="https://i.pinimg.com/originals/af/fa/21/affa21b1847fc2444a5f88d081d6cd3f.gif" 
                                                 alt=""></a>
                                    </li>

                                </ul>
                                <div class="yx-rotation-focus">
                                    <a href="" class="">
                                        #1
                                    </a>

                                    <a href="" class="">
                                        #2
                                    </a>

                                    <a href="" class="">
                                        #3
                                    </a>

                                    <a href="" class="">
                                        #4
                                    </a>

                                    <a href="" class="">
                                        #5
                                    </a>

                                </div>
                            </div>


                            <!--                            <div class="focus-notice-wrap fr">
                                                            <h3><em class="iconfont"></em><span>公告</span></h3>
                            
                                                            <div class="notice-list">
                                                                <ul>
                            
                                                                    <li class="rec"><a href="/noah/320228561" rel="nofollow"><span>[资讯]</span>书启韶华，金龙贺岁</a></li>
                            
                                                                    <li class=""><a href="/noah/289201618" rel="nofollow"><span>[资讯]</span>护苗·绿书签行动</a></li>
                            
                            
                                                                </ul>
                                                            </div>
                            
                                                        </div>-->
                        </div>

                    </div>


                    <!--                HOT NOVEL --------------------------------------------------------------------------------->
                    <div class="index-book-wrap edit-rec-wrap mb20">
                        <div class="inner-wrap cf">


                            <div class="left-wrap fl hover-icon">

                                <h3 class="lang" style="color: #ff3955"><em class="icon icon-edit_rec"></em> 
                                    <span class="hot">HOT!!!</span></h3>

                                <div id="new-book-list">
                                    <div class="type-new-list cf" data-l2="1">
                                        <div class="line l1"></div>
                                        <div class="line l2"></div>
                                        <div class="line l3"></div>
                                        <ul>
                                            <c:forEach var="novel" items="${listHot}" varStatus="status">
                                                <li data-rid="${status.index + 1}">
                                                    <div class="book-img">
                                                        <a href="novel-detail?id=${novel.novelID}" data-eid="qd_F23" data-bid="${novel.novelID}">
                                                            <img src="${novel.imageURL}" alt="${novel.novelName}">
                                                            <span class="tag"><i class="fa fa-fire"></i></span> 
                                                        </a>
                                                    </div>
                                                    <div class="book-info">
                                                        <h4>
                                                            <a href="novel-detail?id=${novel.novelID}" data-eid="qd_F24" data-bid="${novel.novelID}" title="${novel.novelName}">${novel.novelName}</a>
                                                        </h4>
                                                        <p>${novel.novelDescription}</p>
                                                        <div class="state-box cf">

                                                            <a class="author default" data-eid="qd_F25" >
                                                                <img src="<c:if test="${not empty novel.authorImage}">${novel.authorImage}</c:if> 
                                                                     <c:if test="${empty novel.authorImage}">//yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/account.1e031.png</c:if>" style="border-radius: 50%;object-fit: cover;">
                                                                ${novel.author}
                                                            </a>
                                                        </div>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul> 
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!-- NEW UPDATE----------------------------------------------------------------------------------------- -->
                    <div class="index-book-wrap finish-rank mb20">
                        <div class="inner-wrap cf">



                            <div class="left-wrap fl">
                                <h3 class="wrap-title lang">Newly Updated Novels</h3>



                                <!-- ROLL----------------------------------------------------- -->
                                <div class="left-info fl" data-l2="1">
                                    <div class="slide-box">
                                        <ul id="left-slide-02" class="roundabout roundabout-holder" style="display: block; padding: 0px; position: relative;">
                                            <c:forEach var="novel" items="${listNovel3}" varStatus="status">
                                                <li class="book${status.index + 1} roundabout-moveable-item"
                                                    data-id="${status.index + 1}" data-type="1" data-height="100%" data-rid="${status.index + 1}">
                                                    <a href="novel-deatail?id=${novel.novelID}">
                                                        <img class="lazy" src="${novel.imageURL}" data-original="${novel.imageURL}" alt="${novel.novelName}" style="display: inline;">
                                                    </a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!-- ROLL INFO---------------------------------------------------------------------- -->
                                    <div class="info-text">
                                        <dl>
                                            <c:forEach var="novel" items="${listNovel3}" varStatus="status">
                                                <dd class="hidden" data-rid="${status.index + 1}">
                                                    <h3>
                                                        <a href="chapter?id=${novel.novelID}" title="${novel.novelName}">${novel.novelName}</a>
                                                    </h3>
                                                    <p class="author">
                                                        <a class="default" href="#">${novel.author}</a>
                                                    </p>
                                                    <p class="tag">

                                                    </p>
                                                    <p class="intro">${novel.novelDescription}</p>
                                                    <a class="red-btn" href="chapter?id=${novel.novelID}" data-eid="qd_A124" data-bid="">View details</a>
                                                </dd>
                                            </c:forEach>
                                        </dl>
                                    </div>
                                </div>


                                <!-- CENTER -------------------------------------------------------------- -->
                                <div class="center-book-list fl" data-l2="2">
                                    <div class="line line1"></div>
                                    <div class="line line2"></div>

                                    <ul>
                                        <c:forEach var="c" items="${listNovel}" varStatus="status">
                                            <li data-rid="${status.index + 1}">
                                                <div class="book-img">
                                                    <a href="chapter?id=${c.chapterID}" data-eid="qd_A142" data-bid="${c.chapterID}">
                                                        <img class="lazy" src="${c.imageURL}" data-original="${c.imageURL}" alt="${c.novelName}" style="display: inline;">
                                                    </a>
                                                </div>
                                                <div class="book-info">
                                                    <h3 style="white-space: nowrap; text-overflow: ellipsis;">
                                                        <a href="chapter?id=${c.chapterID}" data-eid="qd_A143" data-bid="${c.chapterID}" title="${c.novelName}">${c.novelName}</a>
                                                    </h3>
                                                    <p>
                                                        <a href="chapter?id=${c.chapterID}" data-eid="qd_A143" data-bid="${c.chapterID}" title="${c.lastChapterName}">Chapter ${c.lastChapterNumber}: ${c.lastChapterName}</a>

                                                    </p>
                                                    <div class="state-box cf">
                                                        <i style="max-width: 100px">
                                                            <strong class=""> ${c.timeString}</strong>
                                                        </i>

                                                        <a class="author default " data-eid="qd_A144" style="white-space: nowrap; text-overflow: ellipsis;">
                                                            <c:forEach var="genre" items="${c.genreNames}" varStatus="genreLoop">
                                                                <span class="genre">${genre}</span><c:if test="${!genreLoop.last}">, </c:if>
                                                            </c:forEach>
                                                        </a>

                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>




                                </div>

                            </div>


                            <!-- TOP RANK --------------------------------------------------------- -->
                            <div class="right-wrap recent-finish-wrap fr">
                                <div class="rank-list" data-l2="3"><h3 class="wrap-title lang">Top Rank This Month</h3>
                                    <div class="book-list" style="height: 396px; overflow: hidden">
                                        <ul>
                                            <c:forEach var="novel" items="${listrank}" varStatus="loop">
                                                <c:choose>
                                                    <c:when test="${loop.index == 0}">
                                                        <li class="unfold" data-rid="${loop.index + 1}">
                                                            <div class="book-wrap cf">
                                                                <div class="book-info fl">
                                                                    <h3>NO.1</h3>
                                                                    <h4>
                                                                        <a href="novel-detail?id=${novel.novelID}" data-eid="qd_A136" data-bid="${novel.novelID}" title="${novel.novelName}">${novel.novelName}</a>
                                                                    </h4>
                                                                    <p class="author">

                                                                        <c:forEach var="genre" items="${novel.genreNames}" varStatus="genreLoop">
                                                                            <a class="genre default">${genre}</a><c:if test="${!genreLoop.last}"> - </c:if>
                                                                        </c:forEach>
                                                                    </p>
                                                                </div>
                                                                <div class="book-cover">
                                                                    <a class="link" href="novel-detail?id=${novel.novelID}" data-eid="qd_A136" data-bid="${novel.novelID}"><img src="${novel.imageURL}" alt="${novel.novelName}"></a><span></span>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li data-rid="${loop.index + 1}">
                                                            <div class="num-box">
                                                                <c:choose>
                                                                    <c:when test="${loop.index == 1}"><span class="num2">${loop.index + 1}</span></c:when>
                                                                    <c:when test="${loop.index == 2}"><span class="num3">${loop.index + 1}</span></c:when>
                                                                    <c:when test="${loop.index == 3}"><span class="num4">${loop.index + 1}</span></c:when>
                                                                    <c:when test="${loop.index == 4}"><span class="num5">${loop.index + 1}</span></c:when>
                                                                    <c:when test="${loop.index == 5}"><span class="num6">${loop.index + 1}</span></c:when>
                                                                    <c:when test="${loop.index == 6}"><span class="num7">${loop.index + 1}</span></c:when>
                                                                    <c:when test="${loop.index == 7}"><span class="num8">${loop.index + 1}</span></c:when>
                                                                    <c:when test="${loop.index == 8}"><span class="num9">${loop.index + 1}</span></c:when>
                                                                    <c:otherwise><span class="num10">${loop.index + 1}</span></c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <div class="name-box">
                                                                <a class="name" href="novel-detail?id=${novel.novelID}" data-eid="qd_A117" data-bid="${novel.novelID}" title="${novel.novelName}">${novel.novelName}</a>
                                                                <i class="author">
                                                                    <c:forEach var="genre" items="${novel.genreNames}" varStatus="genreLoop">
                                                                        ${genre}<c:if test="${!genreLoop.last}">,</c:if>
                                                                    </c:forEach>
                                                                </i>
                                                            </div>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                </div>
                            </div>
                            <!--  END TOP RANK                          //-->

                        </div>
                    </div>
                </div>



                <div class="footer">
                    <div class="box-center cf">
                        <jsp:include page="/WEB-INF/views/user//components/footer.jsp" /> 
                    </div>
                </div>
            </div>
        </div>
        <script src="https://yuxseocdn.yuewen.com/sentry/sentry.7.12.1.bundle.min.js" integrity="sha384-ds3MEkwHMg1Vl0XMXl86pZU3gIntwqCNJtYn0etV2sTjuKLdqrtEKFzCwgj3uzcL" crossorigin="anonymous"></script>
        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/homepage2/rotation.js"></script>
        <script src="js/homepage2/header.js"></script>
        <script src="js/homepage2/round.js"></script>
        <script>
            $(document).ready(function () {
                $("#j-focus-slider").yx_rotaion({
                    during: 3000, // Thời gian chuyển đổi giữa các ảnh (3 giây)
                    auto: true, // Tự động chạy trình chiếu
                    btn: false, // Ẩn nút điều hướng (nếu bạn muốn)
                    focus: true, // Hiển thị nút tiêu điểm
                    title: false  // Ẩn tiêu đề (nếu bạn không cần)
                });

                $("#j-navType").showTypeList({
                });

                $("#j-userWrap").userDropDown({
                });

                $("#formUrl").enterSearchBox({
                });


                // Autoplay Callback Function (Define it!)
                function autoplayCallback() {
                    // Add any code here that you want to run after each autoplay transition.
                    console.log("Autoplay transitioned to the next item.");
                }

                // Initialize Roundabout
                var $roundabout = $("#left-slide-02");
                $roundabout.roundabout({
                    margin: 0.7,
                    autoplay: true,
                    autoplayDuration: 5000,
                    responsive: true,
                    btnNext: ".next",
                    btnPrev: ".prev",
                    triggerFocusEvents: true,
                    triggerBlurEvents: true,
                    childSelector: "li",
                    tilt: 0,
                    minOpacity: 0.5,
                    maxOpacity: 1,
                    minScale: 0.6,
                    maxScale: 1,
                    duration: 600,
                    easing: "swing",
                    focus: function (newFocus) {
                        updateContentDisplay(newFocus);
                    },
                    autoplayCallback: autoplayCallback // Pass the defined callback function.
                });

                // Update content based on focused item
                function updateContentDisplay(elem) {
                    var $inFocus = $(elem); // Use the element passed by the 'focus' event
                    if ($inFocus.length > 0) {
                        var rid = $inFocus.data('rid');
                        if (rid) {
                            $('.info-text dd').stop(true, true).addClass('hidden');
                            $('.info-text dd[data-rid="' + rid + '"]').stop(true, true).removeClass('hidden');
                            console.log("Hiển thị nội dung cho rid:", rid);
                        } else {
                            console.warn("Không tìm thấy rid cho phần tử đang focus");
                        }
                    } else {
                        console.warn("Không tìm thấy phần tử roundabout-in-focus");
                    }
                }

                // Initial content update
                setTimeout(function () {
                    updateContentDisplay($roundabout.find(".roundabout-in-focus"));
                }, 500);

                // Trigger focus on creation
                $roundabout.trigger('focus');
            });
        </script>

    </body>
</html>
