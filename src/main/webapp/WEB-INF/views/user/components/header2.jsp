<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Novel Reader</title>
        <link rel="stylesheet" href="css/homepage2/home2.css">
        <link rel="stylesheet" href="css/homepage2/root.css?v=2">
        <link rel="stylesheet" href="css/homepage2/header2.css?v=2">
        <link rel="stylesheet" href="css/homepage2/layout2.css?v=3">
        <link rel="stylesheet" href="css/homepage2/index.css?v=1">


        <script src="https://kit.fontawesome.com/73b63a545d.js" crossorigin="anonymous"></script>
        <link rel="shortcut icon" type="image/x-icon" href="img/b3.bng">
        <link rel="Bookmark" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <style>
            .head-shelf.fw800, .right-nav.fr.mr12 a{
                font-family: 'Macondo Swash Caps', cursive;
            }
        </style>
    </head>
    <body>
        <div class="header" style="zoom: 1.1;">
            <div class="top-head box-center cf">


                <div class="logo">
                    <img src="img/b3.png" alt="Logo Icon">
                    <a href="homepage">NovelReader</a>
                </div>


                <div class="search-wrap">
                    <form class="cf" id="formUrl" action="novels" method="get">
                        <input class="search-box" id="s-box" name="kw" type="text" placeholder="Edge of Tomorrow" autocomplete="off" value="">
                        <input type="hidden" name="action" value="search">

                        <input class="submit-input" type="submit" id="searchSubmit" data-eid="">
                        <a href="/search" id="search-a-btn">
                            <label id="search-btn" class="search-btn" for="searchSubmit"><em class="iconfont" data-eid=""><i class="fa-solid fa-magnifying-glass"></i></em></label></a>

                    </form>
                </div>


                <div class="user-wrap">
                    <c:if test="${empty sessionScope.user}">
                        <div class="avatar" id="j-userWrap">
                            <a class="link" href="Login" id="j-avatar"><img src="//yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/account.1e031.png"></a>
                        </div>
                        <ul> 
                            <li><a class="head-shelf fw800" href="Register"><em class="iconfont"></em>Sign up</a></li>
                            <li><a class="head-shelf fw800" href="Login"><em class="iconfont"></em>Log in</a></li>
                        </ul>
                    </c:if>


                    <c:if test="${not empty sessionScope.user}">
                        <div class="avatar" id="j-userWrap">
                            <a class="link" href="#" id="j-avatar"><img src="<c:if test="${not empty user.imageUML}">${user.imageUML}</c:if> 
                                                                        <c:if test="${empty user.imageUML}">//yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/account.1e031.png</c:if>"></a>

                                <div class="down-drop" id="j-userDownDrop">
                                    <dl>
                                        <dd><a href="viewprofile" >View Profile</a></dd>
                                        <dd><a href="changePassword" >Change Password</a></dd>
                                        <dd><form id="logoutForm" action="<c:url value='/Logout' />" method="post">
                                            <a href="#" onclick="document.getElementById('logoutForm').submit(); return false;">Logout</a>
                                        </form></dd>

                                </dl>
                            </div>
                        </div>


                        <ul> 
                            <li><a class="head-shelf fw800" href="favorite"><em class="iconfont">
                                        <i class="fa-solid fa-fire"></i></em>Favorite</a></li>
                        </ul>
                    </c:if>
                </div>

            </div>

            <div class="top-nav-wrap ml12">
                <div class="box-center cf">
                    <div class="left-nav fl">
                        <ul class="cf">
                            <li class="type " id="j-navType"><a href="novels"><em class="iconfont"><i class="fa fa-list-ul"></i></em>Genres</a>

                                <div class="type-list" id="j-typeList" style="display: none;">
                                    <cite></cite>
                                    <dl>
                                        <c:forEach var="genre" items="${genres}">
                                            <dd>
                                                <a class="dropdown-item" href="novels?genre=${genre.genreName}">${genre.genreName}</a>
                                            </dd>
                                        </c:forEach>
                                    </dl>
                                </div>
                            </li>
<!--                            <li class=""><a href="/rank">aa</a></li>
                            <li class=""><a href="/free">i</a></li>
                            <li class=""><a href="/finish">完本</a></li>
                            <li><a href="/gdyq">古言</a></li>
                            <li><a href="/xdyq">现言</a></li>
                            <li><a href="/xhxx">玄幻仙侠</a></li>
                            <li><a href="/lykh">悬疑科幻</a></li>
                            <li><a href="/qcyx">青春游戏</a></li>
                            <li><a href="/fsg">风尚阁</a></li>-->


                        </ul>
                    </div>
                    <div class="right-nav fr mr12">
                        <a href="mynovel" target="_blank"><em class="iconfont">
                                <i class="fa-solid fa-pen-to-square"></i>
                            </em>My Novel</a>
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
                            $("#j-navType").showTypeList({
                            });

                            $("#j-userWrap").userDropDown({
                            });

                            $("#formUrl").enterSearchBox({
                            });

                        });
        </script>
    </body>
</html>

