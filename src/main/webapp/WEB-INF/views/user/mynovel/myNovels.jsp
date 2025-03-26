<%-- 
    Document   : viewMyNovels
    Created on : Feb 19, 2025, 11:21:26 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Novels</title>
        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/mynovel/myNovell.css?v=1">
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
            .tablebtn{
                all: unset;
                cursor: pointer;
                color: #ff3955;
            }
            .tablebtn:hover{
                text-decoration: underline;
            }
            a:hover{
                color: #ff3955;
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
                                <div class="df aic g_header_title" style="zoom: 1.1">
                                    <label for="foldSwitch" class="collapse_menu--QStMM mr8">
                                        <div class="df g_sd_close collapse_menu_btn--f8c2W"><i></i></div>
                                    </label>
                                    <h2 class="header_title--gwRuS ell dib mw100p t_title_large mb0 vam"><span class="ttc">Novels</span></h2>
                                    <div class="df aic">
                                        <div class="g_top_tab">
                                            <div class="g_top_tab_container">
                                                <button class="tab_button" onclick="showPage(this, 'page1')" style="all:unset">
                                                    <span class="g_top_tab_item  _on fvsc">Avatar</span></button>
                                                <button class="tab_button" onclick="showPage(this, 'page2')" style="all:unset">
                                                    <span class="g_top_tab_item  fvsc">Table</span></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="df aic oh">
                                <button type="button" class="ant-btn ant-btn-primary ant-btn-lg df ell g_header_btn button--4vWlZ"
                                        onclick="window.location.href = 'mynovel?action=post';">
                                    <span role="img" aria-label="plus-circle" class="anticon anticon-plus-circle">
                                        <svg viewBox="64 64 896 896" focusable="false" data-icon="plus-circle" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                        <path d="M696 480H544V328c0-4.4-3.6-8-8-8h-48c-4.4 0-8 3.6-8 8v152H328c-4.4 0-8 3.6-8 8v48c0 4.4 3.6 8 8 8h152v152c0 4.4 3.6 8 8 8h48c4.4 0 8-3.6 8-8V544h152c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8z"></path>
                                        <path d="M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64zm0 820c-205.4 0-372-166.6-372-372s166.6-372 372-372 372 166.6 372 372-166.6 372-372 372z"></path>
                                        </svg></span>
                                    <span class="g_header_btn_title">CREATE A STORY</span>
                                </button>
                            </div>
                        </div>


                        <div id="main_scroll_container" class="scroller--dBDRL pr">
                            <div class="header_ph_nav--0QeBW"></div>
                            <div class="main_content--0x57a with_navigator--ZJqIM">
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
                                                            <div class="mt16"><button data-report-uiname="create" type="button" 
                                                                                      onclick="window.location.href = 'mynovel?action=post';"
                                                                                      class="ant-btn ant-btn-primary ant-btn-background-ghost  button--4vWlZ"><span>create now</span></button></div>
                                                        </div>
                                                    </div>
                                                </c:if>


                                                <c:if test="${not empty listNovel}">
                                                    <div id="page1" class="page"style="display: block">
                                                        <c:forEach var="c" items="${requestScope.listNovel}" >
                                                            <li class="pr col-md-2 list-item ">
                                                                <div class="book"> 
                                                                    <a href="mynovel?action=viewdetail&novelID=${c.novelID}" title="${c.novelName}"> 
                                                                        <i class="detail">
                                                                            <!-- dung canvas API -->
                                                                            <img cross-origin="anonymous"  
                                                                                 data-original="${c.imageURL}" 
                                                                                 width="140" 
                                                                                 height="186" 
                                                                                 alt="${c.novelName}" 
                                                                                 src="${c.imageURL}" 
                                                                                 style="display: block;"> 


                                                                            <span class="_tag_sub" 
                                                                                  <c:if test="${c.novelStatus == 'inactive'}">
                                                                                      style="background-color: red"
                                                                                  </c:if>>${c.novelStatus}
                                                                            </span> 
                                                                            <span class="_tag_sub">
                                                                                ACTIVE
                                                                            </span>  
                                                                        </i> 
                                                                        <h3>${c.novelName}</h3> 
                                                                    </a> 
                                                                    <strong style="margin: 0" class="sub"><i class="fa fa-file-text fa-fw"></i> ${c.totalChapter} Chapters</strong> 




                                                                    <ul class="novel_nav_sub">
                                                                        <span class="title">

                                                                        </span>

                                                                        <li class="list2">
                                                                            <a class="g_sd_sub_option" href="mynovel?action=update&novelID=${c.novelID}">
                                                                                <strong class="str">Update</strong>
                                                                            </a>
                                                                        </li>

                                                                        <li class="list2">
                                                                            <form action="mynovel" method="post" style="display: inline">
                                                                                <input type="hidden" name="action" value="delete">
                                                                                <input type="hidden" name="novelID" value="${c.novelID}">
                                                                                <button type="submit" class="g_sd_sub_option" onclick="return confirm('Are you sure to delete the novel: ${c.novelName} (ID=${c.novelID})')">
                                                                                    <strong class="str">Delete</strong>
                                                                                </button>
                                                                            </form>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </li>
                                                        </c:forEach>
                                                    </div>

                                                    <div id="page2" class="page"style="display: none">
                                                        <div class="table-responsive">
                                                            <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                                                <thead>
                                                                    <tr>
                                                                        <th>#</th>
                                                                        <th>Novel Name</th>
                                                                        <th>Total of chapter</th>
                                                                        <th>Genres</th>
                                                                        <th>Published Date</th>
                                                                        <th>Rating Average</th>
                                                                        <th>View</th>
                                                                        <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach var="c" items="${requestScope.listNovel}" varStatus="status">
                                                                        <tr>
                                                                            <td>${status.index + 1}</td>
                                                                            <td><a href="mynovel?action=viewdetail&novelID=${c.novelID}" title="${c.novelName}">${c.novelName}</a></td>
                                                                            <td>${c.totalChapter}</td>
                                                                            <td>${c.genres}</td>
                                                                            <td>
                                                                                <fmt:formatDate value="${c.publishDate2}" pattern="yyyy-MM-dd HH:mm:ss" />
                                                                            </td>
                                                                            <td>${c.averageRating}</td>
                                                                            <td>${c.viewCount}</td>
                                                                            <td>
                                                                                <a href="mynovel?action=update&novelID=${c.novelID}">Update</a>
                                                                                <form action="mynovel" method="post" style="display: inline;">
                                                                                    <input type="hidden" name="action" value="delete">
                                                                                    <input type="hidden" name="novelID" value="${c.novelID}">
                                                                                    <button style="" type="submit" class="tablebtn" onclick="return confirm('Are you sure to delete the novel: ${c.novelName} (ID=${c.novelID})')">
                                                                                        <strong class="str">Delete</strong>
                                                                                    </button>
                                                                                </form>
                                                                            </td>
                                                                        </tr>
                                                                    </c:forEach>  
                                                                </tbody>
                                                            </table>
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

        <script>
            function showPage(button, pageId) {
                // Ẩn tất cả các div có class "page"
                document.querySelectorAll('.page').forEach(div => {
                    div.style.display = 'none';
                });

                // Hiện div có id tương ứng
                document.getElementById(pageId).style.display = 'block';

                document.querySelectorAll('.tab_button').forEach(btn => {
                    btn.querySelector("span").classList.remove("_on");
                });

                // Thêm class "on" vào button được nhấn
                button.querySelector("span").classList.add("_on");

            }

            $(document).ready(function () {
                var table = $('#dataTables-example').DataTable({
                    responsive: true,
                    "autoWidth": false,
                    language: {
                        info: 'Showing page _PAGE_ of _PAGES_',
                        infoEmpty: '${listnull}',
                        infoFiltered: '(filtered from _MAX_ total novels)',
                        lengthMenu: 'Display _MENU_ novels per page',
                        zeroRecords: 'Nothing found - sorry'
                    },
                    columnDefs: [
                        {width: "160px", targets: 8}
                    ]
                });
            });
        </script>
    </body>
</html>

