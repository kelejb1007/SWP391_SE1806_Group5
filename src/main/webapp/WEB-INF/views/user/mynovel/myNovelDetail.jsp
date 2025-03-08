<%-- 
    Document   : viewMyNovelDetail
    Created on : Feb 23, 2025, 2:13:43 PM
    Author     : Nguyen Thanh Trung
--%>

<%@page import="model.Novel" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${novel.novelName}</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/mynovel/myNovelDetail.css" type="text/css">
        <link rel="stylesheet" href="css/novel-detail/chapter-list(d).css">
        <link rel="stylesheet" href="css/novel-detail/novel-detail(d).css">

        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/home/header.css">

    </head>
    <body>
        <jsp:include page="/WEB-INF/views/user/components/header.jsp"></jsp:include>

            <div class="det-hd " data-report-l1="1">
                <div class="g_wrap">
                    <p class="link"> 
                        <a class="a1" href="homepage" class=""> 
                            <i class="fa fa-home fa-fw"></i>
                        </a> 

                        <span>/</span> 
                        <a class="a2" href="mynovel"  title="My novels"> My novels </a>
                        <span>/</span> 
                        <span>${novel.novelName}</span> 
                </p>

                <div class="row info" style="padding: 0">
                    <div class="col-md-4 _sd" style="margin-left: 0; padding: 0;"> 
                        <i class="g_thumb"> 
                            <img cross-origin="anonymous" 
                                 src="${novel.imageURL}" 
                                 alt="${novel.novelName}" 
                                 title="${novel.novelName}"> 
                            <img cross-origin="anonymous" 
                                 src="${novel.imageURL}" 
                                 alt="${novel.novelName}" 
                                 title="${novel.novelName}"> </i> 
                    </div>
                    <div class="col-md-8 _mn" style="padding: 0">
                        <h1>${novel.novelName}</h1>
                        <div class="detail"> 
                            <strong class="sub"><i class="fa fa-file-text fa-fw"></i> ${novel.totalChapter} Chapters</strong>  
                            <strong class="sub"><i class="fa fa-eye fa-fw faa"></i> ${novel.viewCount} Views</strong> 
                        </div>


                        <div class="author"> <strong class="c_s">Author: </strong> ${novel.author} </div>


                        <address class="">
                            <div class=""> </div>
                        </address>


                        <div class="bts">
                            <div class="novel-buttons">
                                <a href="" class="update-button">Update</a>
                                <a href="" class="remove-button"></a>
                                <button class="remove-btn">Delete</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--        <div class="g_wrap _slide">
                    <a class="_on" href="#about" title="About">
                        <span>About</span></a> 
                    <i class="_hr"></i> 
                    <a href="/comic/11609847806441001/catalog" title="Table of Contents" data-for="#contents" class="j_show_contents" data-report-eid="qi_CC02" data-report-bid="11609847806441001">
                        <span>Table of Contents</span></a> 
                    <i class="_line" style="width: 87px; left: 0px;"></i> 
                </div>-->

        <div class="g_wrap det-tab-nav mb48 _slide" data-report-l1="14">
            <a class="_on tab-link" href="#about" data-tab="#about">
                <span class="chapter-content-title">About</span>
            </a>
            <i class="_hr"></i>
            <a class="tab-link" href="#chapters" data-tab="#chapters">
                <span class="chapter-content-title">Chapters</span>
            </a>
            <i class="_line" style="width: 87px; left: 0px;"></i>
        </div>

        <div class="tab-content g_wrap">
            <h3>Description</h3>
            <p>${novel.novelDescription}</p>
        </div>
        
        





        <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>
        <script src="js/home/header.js"></script>
    </body>
</html>

