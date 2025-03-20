<%-- 
    Document   : viewMyNovels
    Created on : Feb 19, 2025, 11:21:26 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Novels</title>
        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/mynovel/myNovell.css">
        <link rel="stylesheet" href="css/mynovel/postingHistory.css">

        <link rel="shortcut icon" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <link rel="stylesheet" type="text/css" href="css/mynovel/mynovel2.css" crossorigin="anonymous">
        <script charset="utf-8" src="js/mynovel/mynovel2.js" crossorigin="anonymous"></script>


        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

        <style>
            .footer {
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

    </head>


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
                                <div class="df aic g_header_title"><label for="foldSwitch" class="collapse_menu--QStMM mr8">
                                        <div class="df g_sd_close collapse_menu_btn--f8c2W"><i></i></div>
                                    </label>
                                    <h2 class="header_title--gwRuS ell dib mw100p t_title_large mb0 vam"><span class="ttc">stories</span></h2>
                                    <div class="df aic">
                                        <div class="g_top_tab">
                                            <div class="g_top_tab_container"><span class="g_top_tab_item c_s _on fvsc">Novels</span><span class="g_top_tab_item  fvsc">FANFICS</span></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="df aic oh"><button data-report-uiname="cab" type="button" class="ant-btn ant-btn-primary ant-btn-lg df ell g_header_btn button--4vWlZ"><span role="img" aria-label="plus-circle" class="anticon anticon-plus-circle"><svg viewBox="64 64 896 896" focusable="false" data-icon="plus-circle" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                        <path d="M696 480H544V328c0-4.4-3.6-8-8-8h-48c-4.4 0-8 3.6-8 8v152H328c-4.4 0-8 3.6-8 8v48c0 4.4 3.6 8 8 8h152v152c0 4.4 3.6 8 8 8h48c4.4 0 8-3.6 8-8V544h152c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8z"></path>
                                        <path d="M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64zm0 820c-205.4 0-372-166.6-372-372s166.6-372 372-372 372 166.6 372 372-166.6 372-372 372z"></path>
                                        </svg></span><span class="g_header_btn_title">CREATE A STORY</span></button></div>
                        </div>
                        <div id="main_scroll_container" class="scroller--dBDRL pr">
                            <div class="header_ph_nav--0QeBW"></div>
                            <div class="main_content--0x57a with_navigator--ZJqIM">
                                <div class="ant-spin-nested-loading">
                                    <div class="ant-spin-container">
                                        <div class="default--zRToH bc_fff ">



                                            <div>
                                                <ul class="row list" style="display: block;margin-left: auto; margin-right: auto; padding: 30px 0;">
                                                    <c:if test="${empty listNovel}">
                                                        <div class="ant-empty">
                                                            <div class="ant-empty-image" style="margin-top: 100px; height: 64px;"><img alt="" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGkAAACFCAMAAABv2ibxAAAAPFBMVEUAAADFxs3Hx8/Dw8vFxc3Fxc/FxczExc3Fxs3FxcrDxMzExszFxs3ExMzFxszGxs3Exs3Hx8/ExMzFxs10R7mIAAAAE3RSTlMA3yBAvxBv788wUICgkK+vkF9gQHXkXQAAAfxJREFUaN7t2smOwkAMBNDesvQCA+T//3U6E4aScCMuLjQauQ4ByYcnO87N7jfT/OU3YsLdCUmUONIsCiSpbfwEQGnOk+PE36W6O0txCEkKO+SDQ1hSo0KQjpauokiQbntLosaQzuSWIK39mUWNIW09T4V/IU2ixpKiqLGkKmosaWGODxK9KUjiTVGlJTuEJ4kBcqXNn+aIzSBJ9cGtXxUcQXK3/h9Z0qlkkuRCW7anrA3T1JN6pljPKxxMs2RlyR3cfEnoDtO8RkUJyaUmP2gP09SR0B6mKZZFT0J7mCaS8OnpSOBeLUtQlTDNlsbT1JTkNOWyaEnvl8WnWrKihLz89LQlLIt/6o0jYZpcSS4LWUKCSSaZZJJJJplkkkkmmWSSSSaZZJJJJplkkkkmmfSXpOkT0vEkS7g3Kh+RWn/OZAl3YekjUsDFDFc6fuaPSBXjI0vT1hMVpSiyHJJLe1lNGueQ8tZz4Us9J8yPLE1+3/SgJaVhjg8p4iRWd/dk6g+V2dLjVW2VLYHygSphgD0t0qXHxZZvZaJISGi4PMAhmqaExCSuU7QlWG0BBU5TQsoFnQlOSULyra0jTlVC4jzgGjglCdx5wF2zmvT+kimdwA0kfa7kscTgFnBaErh69mMOEptrkHQTShVHaJBoHF/CURhVkhxdAhdfFL4Bk3+cWERw0jEAAAAASUVORK5CYII="></div>
                                                            <div class="ant-empty-footer">
                                                                <div class="clearfix">
                                                                    <p class="fw600 fs18 lh18 mb16 tac">No Novels!</p>
                                                                    <p class="fs14 lh20 tac c_xs mb16">Click the button below to create your first fiction now.</p>
                                                                </div>
                                                                <div class="mt16"><button data-report-uiname="create" type="button" class="ant-btn ant-btn-primary ant-btn-background-ghost  button--4vWlZ"><span>create now</span></button></div>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${not empty listNovel}">
                                                        <div class="ant-table-wrapper table--fDOzf">
                                                            <div class="ant-spin-nested-loading">
                                                                <div class="ant-spin-container">
                                                                    <div class="ant-table ant-table-empty">
                                                                        <div class="ant-table-container">
                                                                            <div class="ant-table-content">
                                                                                <table style="table-layout: auto;">
                                                                                    <colgroup></colgroup>
                                                                                    <thead class="ant-table-thead">
                                                                                        <tr>
                                                                                            <th class="ant-table-cell">
                                                                                                <div class="ttu" style="min-width: 220px;">stories</div>
                                                                                            </th>
                                                                                            <th class="ant-table-cell"><span class="ttu">STATE</span></th>
                                                                                            <th class="ant-table-cell"><span class="ttu">Chapters</span></th>
                                                                                            <th class="ant-table-cell"><span class="ttu">words</span></th>
                                                                                            <th class="ant-table-cell"><span class="ttu">views</span></th>
                                                                                            <th class="ant-table-cell"><span class="ttu">collections</span></th>
                                                                                            <th class="ant-table-cell">
                                                                                                <div class="ttu" style="min-width: 110px;">OPERATION</div>
                                                                                            </th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody class="ant-table-tbody">
                                                                                        <tr class="ant-table-placeholder">
                                                                                            <td colspan="7" class="ant-table-cell">
                                                                                                <div class="ant-empty ant-empty-normal">
                                                                                                    <div class="ant-empty-image"><svg class="ant-empty-img-simple" width="64" height="41" viewBox="0 0 64 41" xmlns="http://www.w3.org/2000/svg">
                                                                                                        <g transform="translate(0 1)" fill="none" fill-rule="evenodd">
                                                                                                        <ellipse class="ant-empty-img-simple-ellipse" cx="32" cy="33" rx="32" ry="7"></ellipse>
                                                                                                        <g class="ant-empty-img-simple-g" fill-rule="nonzero">
                                                                                                        <path d="M55 12.76L44.854 1.258C44.367.474 43.656 0 42.907 0H21.093c-.749 0-1.46.474-1.947 1.257L9 12.761V22h46v-9.24z"></path>
                                                                                                        <path d="M41.613 15.931c0-1.605.994-2.93 2.227-2.931H55v18.137C55 33.26 53.68 35 52.05 35h-40.1C10.32 35 9 33.259 9 31.137V13h11.16c1.233 0 2.227 1.323 2.227 2.928v.022c0 1.605 1.005 2.901 2.237 2.901h14.752c1.232 0 2.237-1.308 2.237-2.913v-.007z" class="ant-empty-img-simple-path"></path>
                                                                                                        </g>
                                                                                                        </g>
                                                                                                        </svg></div>
                                                                                                    <div class="ant-empty-description">No data</div>
                                                                                                </div>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </tbody>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
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
    </body>
</html>

