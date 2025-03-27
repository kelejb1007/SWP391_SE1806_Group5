<%-- 
    Document   : PostChapterHistory.jsp
    Created on : Mar 1, 2025, 5:18:10 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chapter Submission</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.css">
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
                background-color: rgba(255, 255, 255, 0.1);
                box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
                font-family: 'Macondo Swash Caps', cursive;
                background-size: cover
            }
            a:hover{
                color: #ff3955;
                text-decoration: underline
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
                                    <h2 class="header_title--gwRuS ell dib mw100p t_title_large mb0 vam"><span class="ttc">Submission</span></h2>
                                    <div class="df aic">
                                        <div class="g_top_tab">
                                            <div class="g_top_tab_container">
                                                <button class="tab_button" onclick="showPage(this, 'page1')" style="all:unset">
                                                    <span class="g_top_tab_item fvsc">Novel</span></button>
                                                <button class="tab_button" onclick="showPage(this, 'page2')" style="all:unset">
                                                    <span class="g_top_tab_item _on fvsc">Chapter</span></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div id="main_scroll_container" class="scroller--dBDRL pr">
                            <div class="header_ph_nav--0QeBW"></div>
                            <div class="main_content--0x57a with_navigator--ZJqIM">
                                <div class="ant-spin-container">

                                    <div class="default--zRToH bc_fff">
                                        <div>
                                            <ul class="row list" style="margin-left: auto; margin-right: auto; padding: 0;">
                                                <!-- /.panel-heading -->
                                                <div class="panel-body" style="display: block; width: 100%; font-size: 15px; padding: 20px 0">

                                                    <c:if test="${empty list}">
                                                        <div class="ant-empty">
                                                            <div class="ant-empty-image" style="margin-top: 100px; height: 64px; margin-bottom: 12px">
                                                                <img alt="" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGkAAACFCAMAAABv2ibxAAAAPFBMVEUAAADFxs3Hx8/Dw8vFxc3Fxc/FxczExc3Fxs3FxcrDxMzExszFxs3ExMzFxszGxs3Exs3Hx8/ExMzFxs10R7mIAAAAE3RSTlMA3yBAvxBv788wUICgkK+vkF9gQHXkXQAAAfxJREFUaN7t2smOwkAMBNDesvQCA+T//3U6E4aScCMuLjQauQ4ByYcnO87N7jfT/OU3YsLdCUmUONIsCiSpbfwEQGnOk+PE36W6O0txCEkKO+SDQ1hSo0KQjpauokiQbntLosaQzuSWIK39mUWNIW09T4V/IU2ixpKiqLGkKmosaWGODxK9KUjiTVGlJTuEJ4kBcqXNn+aIzSBJ9cGtXxUcQXK3/h9Z0qlkkuRCW7anrA3T1JN6pljPKxxMs2RlyR3cfEnoDtO8RkUJyaUmP2gP09SR0B6mKZZFT0J7mCaS8OnpSOBeLUtQlTDNlsbT1JTkNOWyaEnvl8WnWrKihLz89LQlLIt/6o0jYZpcSS4LWUKCSSaZZJJJJplkkkkmmWSSSSaZZJJJJplkkkkmmfSXpOkT0vEkS7g3Kh+RWn/OZAl3YekjUsDFDFc6fuaPSBXjI0vT1hMVpSiyHJJLe1lNGueQ8tZz4Us9J8yPLE1+3/SgJaVhjg8p4iRWd/dk6g+V2dLjVW2VLYHygSphgD0t0qXHxZZvZaJISGi4PMAhmqaExCSuU7QlWG0BBU5TQsoFnQlOSULyra0jTlVC4jzgGjglCdx5wF2zmvT+kimdwA0kfa7kscTgFnBaErh69mMOEptrkHQTShVHaJBoHF/CURhVkhxdAhdfFL4Bk3+cWERw0jEAAAAASUVORK5CYII=">
                                                            </div>
                                                            <div class="ant-empty-footer">
                                                                <div class="clearfix">
                                                                    <p class="message">${requestScope.message}</p>
                                                                    <p class="message_sub">Click the button below to create your first chapter now.</p>
                                                                </div>
                                                                <div style="margin-top: 16px;">

                                                                    <button data-report-uiname="create" data-report-pn="work_novel" type="button" class="ant-btn ant-btn-primary ant-btn-background-ghost button--4vWlZ"

                                                                            onclick="window.location.href = 'mynovel?action=post';">
                                                                        <span>create now</span>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </c:if>
                                                    <c:if test="${not empty list}">
                                                        <div class="table-responsive">
                                                            <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                                                <thead>
                                                                    <tr>
                                                                        <th>#</th>
                                                                        <th>Novel Name</th>
                                                                        <th>Chapter Number</th>
                                                                        <th>Chapter Name</th>
                                                                        <th>Submission Date</th>
                                                                        <th>Approval Date</th>
                                                                        <th>Type</th>
                                                                        <th>Status</th>
                                                                        <th>Rejected Reason</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach var="c" items="${requestScope.list}" varStatus="status">
                                                                        <tr>
                                                                            <td>${status.index + 1}</td>
                                                                            <td><a href="mynovel?action=viewdetail&novelID=${c.novelID}" title="${c.novelName}">${c.novelName}</a></td>
                                                                            <td>${c.chapterNumber}</td>
                                                                            <td><a href="${pageContext.request.contextPath}/chapter?id=${chapterID}" title="${c.novelName}">${c.chapterName}</a></td>
                                                                            <td>${c.submissionDate}</td>
                                                                            <td>${c.approvalDate}</td>
                                                                            <td>
                                                                                <c:choose>

                                                                                    <c:when test="${c.type == 'update'}">
                                                                                        <a href="${pageContext.request.contextPath}/chapter?id=${c.draftID}" title="View Draft">${c.type}</a>
                                                                                    </c:when>
                                                                                    <c:when test="${c.type == 'post'}">

                                                                                        ${c.type}
                                                                                    </c:when>
                                                                                </c:choose>
                                                                            </td>
                                                                            <td <c:choose>
                                                                                    <c:when test="${c.status == 'rejected'}">
                                                                                        style="color: red"
                                                                                    </c:when>
                                                                                    <c:when test="${c.status == 'approved'}">
                                                                                        style="color: black"
                                                                                    </c:when>
                                                                                    <c:when test="${c.status == 'pending'}">
                                                                                        >>>>>>> Phat_stage7
                                                                                        style="color: green"
                                                                                    </c:when>
                                                                                </c:choose>
                                                                                >${c.status}</td>
                                                                            <td>${c.reasonRejected}</td>
                                                                        </tr>
                                                                    </c:forEach>  
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </c:if>
                                                </div>
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
    </body>
</html>