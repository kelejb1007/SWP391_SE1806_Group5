<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="header">
    <div class="top-head box-center cf">
        <div class="logo">
            <img src="img/b3.png" alt="Logo Icon">
            <a href="homepage">NovelReader</a>
        </div>


        <div class="search-wrap">

            <form class="cf" id="formUrl" action="" method="get" target="_blank">
                <input class="search-box" id="s-box" name="kw" type="text" placeholder="Search" autocomplete="off" value="">

                <input class="submit-input" type="submit" id="searchSubmit" data-eid="">
                <a href="/search" id="search-a-btn">
                    <label id="search-btn" class="search-btn" for="searchSubmit"><em class="iconfont" data-eid=""><i class="fa fa-search"></i></em></label></a>

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
                <li><a class="head-shelf fw800" href=""><em class="iconfont"><i class="fa fa-hand-o-right"></i></em>Favorite</a></li>
            </ul>
        </div>

    </div>

    <div class="top-nav-wrap ml12">
        <div class="box-center cf">
            <div class="left-nav fl">
                <ul class="cf">
                    <li class="type " id="j-navType"><a href="/category"><em class="iconfont"><i class="fa fa-list-ul"></i></em>Genres</a>

                        <div class="type-list" id="j-typeList" style="display: none;">
                            <cite></cite>
                            <dl>
                                <dd><a href="/category/30020_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>现代言情</i></a></dd>
                                <dd><a href="/category/30013_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>古代言情</i></a></dd>
                                <dd><a href="/category/30031_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>浪漫青春</i></a></dd>
                                <dd><a href="/category/30001_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>玄幻言情</i></a></dd>
                                <dd><a href="/category/30008_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>仙侠奇缘</i></a></dd>
                                <dd><a href="/category/30036_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>悬疑</i></a></dd>
                                <dd><a href="/category/30042_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>科幻空间</i></a></dd>
                                <dd><a href="/category/30050_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>游戏竞技</i></a></dd>
                                <dd><a href="/category/30083_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em>短篇小说</a></dd>
                                <dd><a href="/category/30055_f1_f1_f1_f1_f1_0_1"><em class="iconfont"></em><i>轻小说</i></a></dd>
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
                <a href="//write.qq.com?siteid=6" target="_blank"><em class="iconfont"><i class="fa fa-diamond fw800"></i></em>My Novel</a>
            </div>
        </div>
    </div>
    <!-- end 顶部导航 -->


</div>
