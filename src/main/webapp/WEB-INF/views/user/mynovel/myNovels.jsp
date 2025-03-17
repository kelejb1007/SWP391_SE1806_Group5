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
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/mynovel/myNovell.css">
        <link rel="stylesheet" href="css/mynovel/postingHistory.css">

        <link rel="stylesheet" href="css/home/header(d).css">

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

    <body style="background-color: #fff;">
        <jsp:include page="/WEB-INF/views/user/components/header.jsp" /> 
        <c:if test="${not empty popup}">
            <script>
                window.onload = function () {
                    alert("${popup}");
                };
            </script>
        </c:if>


        <div class="g_sub_hd">
            <div class="g_wrap pr" data-report-l1="2"> 
                <Strong><h2 class="">My Novels</h2> </Strong> 

                <p class="_tab _slide" data-report-l1="1">
                    <a href="mynovel" title="My Novels" class="_on">My Novels</a>
                    <a href="mynovel?action=viewposthistory" title="Poting History">Submission History</a>
                </p>

                <div class="add-filter"> 
                    <button type="button" class="btn btn-info" onclick="window.location.href = 'mynovel?action=post';">
                        <i class="bi bi-plus-circle faa"></i>  Create a novel
                    </button>
                </div>

            </div>
        </div>

        <div>
            <ul class="row list" style="display: block;margin-left: auto; margin-right: auto; padding: 30px 0;">
                <c:if test="${empty listNovel}">
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
                <c:if test="${not empty listNovel}">
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
                                            <strong style="color: white" class="sub"><i style="color: white" class="fa fa-eye fa-fw faa"></i> ${c.viewCount}</strong>
                                            <strong style="color: white" class="sub"><i style="color: white" class="fa fa-star fa-fw faa"></i> ${c.averageRating}</strong>
                                        </span>  
                                    </i> 
                                    <h3>${c.novelName}</h3> 
                                </a> 
                                <strong style="margin: 0" class="sub"><i class="fa fa-file-text fa-fw"></i> ${c.totalChapter} Chapters</strong> 




                                <ul class="novel_nav_sub">
                                    <span class="title">
                                        <strong class="sub"><i class="fa fa-eye fa-fw faa"></i> ${c.viewCount}</strong>
                                        <strong class="sub"><i class="fa fa-star fa-fw faa"></i> ${c.averageRating}</strong> 
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
                </c:if>
            </ul>
        </div>


        <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>
        <script src="js/home/header(d).js"></script>
    </body>
</html>