<%-- 
    Document   : postingHistory
    Created on : Mar 3, 2025, 3:01:19 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Novels</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/mynovel/myNovell.css">
        <link rel="stylesheet" href="css/mynovel/postingHistory.css">
        

        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    </head>
    <body>

            <div class="g_sub_hd">
                <div class="g_wrap pr" data-report-l1="2"> 
                    <Strong><h2 class="">My Novels</h2> </Strong> 

                    <p class="_tab _slide" data-report-l1="1">
                        <a href="mynovel" title="My Novels" >My Novels</a>
                        <a href="mynovel?action=viewposthistory" title="Poting History" class="_on">Submission History</a>
                    </p>

                    <div class="add-filter"> 
                        <button type="button" class="btn btn-info" onclick="window.location.href = 'mynovel?action=post';">
                            <i class="bi bi-plus-circle faa"></i>  Create a novel
                        </button>
                    </div>
                </div>
            </div>
            <div>
                <ul class="row list">


                    <!-- /.panel-heading -->
                    <div class="panel-body" style="display: block; width: 100%; font-size: 15px">

                    <c:if test="${empty list}">
                        <div class="ant-empty">
                            <div class="ant-empty-image" style="margin-top: 100px; height: 64px; margin-bottom: 12px">
                                <img alt="" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGkAAACFCAMAAABv2ibxAAAAPFBMVEUAAADFxs3Hx8/Dw8vFxc3Fxc/FxczExc3Fxs3FxcrDxMzExszFxs3ExMzFxszGxs3Exs3Hx8/ExMzFxs10R7mIAAAAE3RSTlMA3yBAvxBv788wUICgkK+vkF9gQHXkXQAAAfxJREFUaN7t2smOwkAMBNDesvQCA+T//3U6E4aScCMuLjQauQ4ByYcnO87N7jfT/OU3YsLdCUmUONIsCiSpbfwEQGnOk+PE36W6O0txCEkKO+SDQ1hSo0KQjpauokiQbntLosaQzuSWIK39mUWNIW09T4V/IU2ixpKiqLGkKmosaWGODxK9KUjiTVGlJTuEJ4kBcqXNn+aIzSBJ9cGtXxUcQXK3/h9Z0qlkkuRCW7anrA3T1JN6pljPKxxMs2RlyR3cfEnoDtO8RkUJyaUmP2gP09SR0B6mKZZFT0J7mCaS8OnpSOBeLUtQlTDNlsbT1JTkNOWyaEnvl8WnWrKihLz89LQlLIt/6o0jYZpcSS4LWUKCSSaZZJJJJplkkkkmmWSSSSaZZJJJJplkkkkmmfSXpOkT0vEkS7g3Kh+RWn/OZAl3YekjUsDFDFc6fuaPSBXjI0vT1hMVpSiyHJJLe1lNGueQ8tZz4Us9J8yPLE1+3/SgJaVhjg8p4iRWd/dk6g+V2dLjVW2VLYHygSphgD0t0qXHxZZvZaJISGi4PMAhmqaExCSuU7QlWG0BBU5TQsoFnQlOSULyra0jTlVC4jzgGjglCdx5wF2zmvT+kimdwA0kfa7kscTgFnBaErh69mMOEptrkHQTShVHaJBoHF/CURhVkhxdAhdfFL4Bk3+cWERw0jEAAAAASUVORK5CYII=">
                            </div>
                            <div class="ant-empty-footer">
                                <div class="clearfix">
                                    <p class="message">${requestScope.message}</p>
                                    <p class="message_sub">Click the button below to create your first novel now.</p>
                                </div>
                                <div style="margin-top: 16px;">
                                    <button data-report-uiname="create" data-report-pn="work_novel" type="button" class="ant-btn ant-btn-primary ant-btn-background-ghost  button--4vWlZ"
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
                                            <td>${c.submissionDate}</td>
                                            <td>${c.approvalDate}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test = "${c.type == 'update'}">
                                                        <a href="mynovel?action=viewdetail&novelID=${c.draftID}" title="View Draft">${c.type}</a>
                                                    </c:when>

                                                    <c:when test = "${c.type == 'post'}">
                                                        ${c.type}
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td 
                                                <c:choose>
                                                    <c:when test = "${c.status == 'rejected'}">
                                                        style="color: red"
                                                    </c:when>

                                                    <c:when test = "${c.status == 'approved'}">
                                                        style="color: black"
                                                    </c:when>

                                                    <c:when test = "${c.status == 'pending'}">
                                                        style="color: green"
                                                    </c:when>
                                                </c:choose>
                                                >${c.status}</td>
                                            <td>${c.reasonRejected}</td>
                                            <!--                                    <td>
                                                                                    <button type="button" class="btn btn-info"
                                                                                            onclick="window.location.href = 'managenovel?action=viewdetail&novelID=${c.novelID}';">View detail
                                                                                    </button>
                                                                                    <button type="button" id="open" class="btn btn-danger" onclick="openLockModal('${c.novelID}', '${c.novelName}')">Lock</button>
                                                                                </td>-->
                                        </tr>
                                    </c:forEach>  
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </ul>
        </div>

    </body>
</html>
