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


        <link rel="stylesheet" href="css/home/header.css">
        <link rel="stylesheet" href="css/mynovel/myNovel.css">


        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">



    </head>

    <body>
        <jsp:include page="/WEB-INF/views/user/components/header.jsp"></jsp:include>

        <c:if test="${not empty message}">
            <script>
                window.onload = function () {
                    alert("${message}");
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
            <ul class="row list">
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

                                <li class="list">
                                    <a class="g_sd_sub_option" href="mynovel?action=update&novelID=${c.novelID}">
                                        <strong class="str">Update</strong>
                                    </a>
                                </li>

                                <li class="list">
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
            </ul>
        </div>


        <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>
        <script src="js/home/header(d).js"></script>
    </body>
</html>