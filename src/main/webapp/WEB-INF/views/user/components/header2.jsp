<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Novel Reader</title>
        <link rel="stylesheet" href="css/homepage2/home2.css">
        <link rel="stylesheet" href="css/homepage2/root.css?v=1">
        <link rel="stylesheet" href="css/homepage2/header2.css?v=2">
        <link rel="stylesheet" href="css/homepage2/layout2.css?v=3">
        <link rel="stylesheet" href="css/homepage2/iconfont.css?v=1">
        <link rel="stylesheet" href="css/homepage2/index.css?v=1">


        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/awe-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="shortcut icon" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <link rel="Bookmark" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <style>
            .head-shelf.fw800, .right-nav.fr.mr12 a{
                font-family: 'Macondo Swash Caps', cursive;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <div class="top-head box-center cf">
                <div class="logo">
                    <img src="img/b3.png" alt="Logo Icon">
                    <a href="homepage">NovelReader</a>
                </div>


                <div class="search-wrap">

                    <form class="cf" id="formUrl" action="novels" method="get" target="_blank">
                        <input class="search-box" id="s-box" name="kw" type="text" placeholder="Search" autocomplete="off" value="">
                        <input type="hidden" name="action" value="search">
                        <input class="submit-input" type="submit" id="searchSubmit" data-eid="">
                        <a href="novels" id="search-a-btn">
                            <label id="search-btn" class="search-btn" for="searchSubmit"><em class="iconfont" data-eid=""><i class="fa-solid fa-magnifying-glass"></i></em></label></a>

                    </form>
                </div>


                <div class="user-wrap">
                    <div class="avatar" id="j-userWrap">
                        <a class="link" href="javascript:" id="j-avatar"><img src="//yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/account.1e031.png"></a>

                        <div class="down-drop" id="j-userDownDrop">
                            <dl>
                                <dd><a href="javascript:" >View Profile</a></dd>
                                <dd><a href="javascript:" >Change Password</a></dd>
                                <dd><a href="javascript:" id="sign-out">Logout</a></dd>

                            </dl>
                        </div>
                    </div>
                    <ul> 
                        <li><a class="head-shelf fw800" href=""><em class="iconfont">
                                    <i class="fa-solid fa-poo"></i></em>Favorite</a></li>
                    </ul>
                </div>

            </div>

            <div class="top-nav-wrap ml12">
                <div class="box-center cf">
                    <div class="left-nav fl">
                        <ul class="cf">
                            <li class="type " id="j-navType"><a href="novels?filter=&genre="><em class="iconfont"><i class="fa fa-list-ul"></i></em>Genres</a>

                                <div class="type-list" id="j-typeList" style="display: none;">
                                    <cite></cite>
                                    <dl>
                                        
                                        <c:forEach var="genre" items="${genres}">
                                           
                                                <dd> <a class="dropdown-item" href="novels?genre=${genre.genreName}">${genre.genreName}</a></dd>
                                           
                                        </c:forEach>
                                   
                                       
                                    </dl>
                                </div>
                            </li>
                            <li class=""><a href="/rank">aa</a></li>
                            <li class=""><a href="/free">i</a></li>
                            <li class=""><a href="/finish">完本</a></li>
                            <li><a href="/gdyq">古言</a></li>
                            <li><a href="/xdyq">现言</a></li>
                            <li><a href="/xhxx">玄幻仙侠</a></li>
                            <li><a href="/lykh">悬疑科幻</a></li>
                            <li><a href="/qcyx">青春游戏</a></li>
                            <li><a href="/fsg">风尚阁</a></li>


                        </ul>
                    </div>
                    <div class="right-nav fr mr12">
                        <a href="//write.qq.com?siteid=6" target="_blank"><em class="iconfont">
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

