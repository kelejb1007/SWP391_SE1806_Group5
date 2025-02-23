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
        <link rel="stylesheet" href="css/mynovel/myNovels.css">

        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">


    </head>

    <body>
        <jsp:include page="/WEB-INF/views/user/components/header.jsp"></jsp:include>

            <div class="g_sub_hd">
                <div class="g_wrap pr" data-report-l1="2"> 
                    <Strong><h2 class="">My Novels</h2> </Strong> 

                    <p class="_tab _slide" data-report-l1="1">
                        <a href="###" title="My Novels" class="_on">My Novels</a>
                        <a href="/history" title="Poting History" data-report-eid="qi_B02">Posting History</a>
                        <!--                    <i class="_line" style="width: 67px; left: 0px;"></i>-->
                    </p>

                    <div class="add-filter"> 
<!--                        <div class="g_dropdown"> 
                            <strong class="g_drop_"> 
                                <svg class="c_primary"><use xlink:href="#i-sort"></use></svg> 
                                <span class="j_sort_desc">Recently read</span> </strong> 
                                <p class="g_drop_bd _r j_sort_drop" data-report-l1="4"> 
                                    <a 
                                        data-sort="2"             
                                        title="Recently read" 
                                        class="g_drop_item _on" 
                                        href="###" data-desc="Recently read" 
                                        data-report-eid="qi_B04"> Recently read 
                                        <svg class="g_drop_icon"><use xlink:href="#i-right"></use></svg>
                                    </a> 
                                    <a 
                                        data-sort="1" 
                                        title="Time added" 
                                        class="g_drop_item " 
                                        href="###" 
                                        data-desc="Time added" 
                                        data-report-eid="qi_B04"> Time added 
                                        <svg class="g_drop_icon"><use xlink:href="#i-right"></use></svg>
                                    </a> </p> 
                        </div>-->
                        <button type="button" class="btn btn-info"><i class="bi bi-plus-circle faa"></i>  Create a novel</button>
                    </div>
                </div>
            </div>

            <div>
                <ul class="row list">
                <c:forEach var="c" items="${requestScope.listNovel}" >
                    <li class="pr col-md-2 list-item ">
                        <div class="book"> 
                            <a href="mynovel?action=viewdetail&novelID=${c.novelID}" title="${c.novelName}" > 
                                <i class="detail"> 
                                    <!-- dung canvas API -->
                                    <img cross-origin="anonymous"  
                                         data-original="${c.imageURL}" 
                                         width="140" 
                                         height="186" 
                                         alt="${c.novelName}" 
                                         src="${c.imageURL}" 
                                         style="display: block;">  
                                    <span class="_tag_sub">${c.novelStatus}</span>  
                                </i> 
                                <h3>${c.novelName}</h3> 
                            </a> 

                            <!--                            <label class="labell">
                                                            <input type="checkbox" name="" class="hide" value="28967119200447901" data-ntype="100">
                                                            <span class="pa t50% l50% br100% trans">
                                                                <svg><use xlink:href="#i-right"></use></svg>
                                                            </span>
                                                        </label> -->
                            
                            
                        

<!--                        <strong class="sub"><i class="fa fa-star fa-fw faa"></i> ${c.averageRating}</strong> -->
                            <strong style="margin: 0" class="sub"><i class="fa fa-file-text fa-fw"></i> ${c.totalChapter} Chapters</strong> 
<!--                        <strong class="sub"><i class="fa fa-eye fa-fw faa"></i> ${c.viewCount}</strong> -->
                        </div>
                    </li>
                </c:forEach>  
            </ul>
        </div>


        <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>
        <script src="js/home/header.js"></script>
    </body>

</html>