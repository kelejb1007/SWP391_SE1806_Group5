<%-- 
    Document   : postNovel
    Created on : Feb 27, 2025, 11:32:16 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="String" class="java.lang.String" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Novel</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="shortcut icon" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <link rel="stylesheet" type="text/css" href="css/mynovel/mynovel2.css" crossorigin="anonymous">
        <script charset="utf-8" src="js/mynovel/mynovel2.js?v=1" crossorigin="anonymous"></script>
        <style>

            .book_cover_wrap--xZYzr:before {
                background-color: #ff3955;
                width: 180px;
                height: 240px
            }
        </style>

        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
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

                <div class="g_main_wrap f1 pr" style="zoom: 1.1">
                    <div style="">
                        <div class="pf t0 l0 header--Unk0j df jcsb">\
                            <div class="undefined g_header df fg1 oh">
                                <div class="df aic g_header_title"><label for="foldSwitch" class="collapse_menu--QStMM mr8">
                                        <div class="df g_sd_close collapse_menu_btn--f8c2W"><i></i></div>
                                    </label>
                                    <h2 class="header_title--gwRuS ell dib mw100p t_title_large mb0 vam"><span class="ttc">Create Novel</span></h2>

                                </div>
                            </div>
                        </div>]
                        
                        
                        <div id="main_scroll_container" class="scroller--dBDRL pr">
                            <div class="header_ph--kHZzY"></div>
                            <div class="main_content--0x57a ">
                                <div class="default--zRToH bc_fff ">
                                    <form class="ant-form ant-form-vertical setting_form--R6kRQ" action="mynovel" method="post" enctype="multipart/form-data">


                                        <h3 class="mb32 fvsc fw400 fs18 ell lh24"><span role="img" class="anticon vam mr8"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                <use xlink:href="#i-files"></use>
                                                </svg></span>novel information</h3>

                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="bookTitle" class="ant-form-item-required" title="">
                                                        <div class="dib"><span>Novel name</span></div>
                                                    </label></div>
                                                <div class="ant-form-item-control-input-content"><span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                        <input name="novelName" maxlength="70" placeholder="Within 70 characters" id="bookTitle" aria-required="true" class="ant-input ant-input-lg" 
                                                               type="text" value="${novel.novelName}" required>
                                                    </span></div>
                                            </div>
                                        </div>


                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="bookTitle" class="ant-form-item-required" title="">
                                                        <div class="dib"><span>Total Of Chapter</span></div>
                                                    </label></div>
                                                <div class="ant-form-item-control-input-content"><span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                        <input name="totalChapter" placeholder="Predict total of chapter (1 - 100000)" id="bookTitle" aria-required="true" class="ant-input ant-input-lg" 
                                                               type="number" value="${novel.totalChapter}" min="1" max="100000" required >
                                                    </span></div>
                                            </div>
                                        </div>


                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="bookTitle" class="ant-form-item-required" title="">Description</label></div>

                                                <div class="ant-form-item-control-input-content"><span class="ant-input-affix-wrapper ">
                                                        <textarea name="novelDescription" placeholder="Type something seriously, wonderful description can attract more readers" id="synopsis" class="ant-input" 
                                                                  style="height: 120px; resize: none; min-height: 53.6px;" required>${novel.novelDescription}</textarea>
                                                    </span></div>
                                            </div>
                                        </div>



                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="freeType" class="ant-form-item-required" title="">GENRE</label></div>
                                                <div class="checkbox" style="margin-left: 20px">
                                                    <c:forEach var="c" items="${requestScope.genreList}" >
                                                        <label style="color: rgba(18, 18, 23, .9) !important; font-weight: 600; margin: 0px 7px 3px">
                                                            <input type="checkbox" name="genreList" value="${c.genreID}"
                                                                   <c:if test="${not empty genreOfNovel and genreOfNovel.contains(String.valueOf(c.genreID))}">checked</c:if> 
                                                                       >
                                                            ${c.genreName}
                                                        </label>
                                                    </c:forEach>  
                                                </div>
                                            </div>
                                        </div>







                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label">
                                                    <label for="freeType" class="ant-form-item-required" title="">Novel Image (180x240)</label>
                                                </div>

                                                <div class="ant-form-item-control-input-content">
                                                    <div id="clickImage" class="book_cover_wrap--xZYzr pr dib" name="imageURL" style="width: 180px; height: 240px;">
                                                        <span role="img" class="anticon fs24 book_cover_plus--rs178 pa" style="color: rgb(246, 247, 252);">
                                                            <svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                            <use xlink:href="#i-plus"></use>
                                                            </svg>
                                                        </span>

                                                        <img id="image-preview" class="br4" width="180" height="240" alt="Novel Image" style=""
                                                             src="<c:if test="${not empty novel.imageURL}">${novel.imageURL}</c:if> 
                                                             <c:if test="${empty novel.imageURL}">img/b3.png</c:if>" >



                                                             <input id="file-input" type="file" name="imageURL" accept="image/*" style="display: none;"
                                                             <c:if test="${novel.imageURL == null}"> required </c:if>>
                                                        <input type="hidden" name="file_hidden" value="${novel.imageURL}">


                                                        <button id="pushImageButton" type="button" class="ant-btn ant-btn-primary ant-btn-lg mt8 button--4vWlZ" style="display: block; min-width: 134px;" ant-click-animating-without-extra-node="false">
                                                            <span role="img" class="anticon">
                                                                <svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                                <use xlink:href="#i-upload"></use>
                                                                </svg>
                                                            </span><span>Upload</span>
                                                        </button>
                                                    </div>
                                                        <p class="c_danger fs14 mt16"></p>
                                                        <br>
                                                </div>
                                            </div>
                                        </div>


                                        <input type="hidden" name="action" value="post">
                                        <button type="submit" value="Submit" class="ant-btn ant-btn-primary ant-btn-lg  button--4vWlZ"><span>Create</span></button>
                                        <button id="reset" type="reset" class="ant-btn ant-btn-primary ant-btn-lg  button--4vWlZ"><span>Reset</span></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script>
//                document.getElementById("pushImageButton").addEventListener("click", function () {
//                    document.getElementById("file-input").click(); // Kích hoạt input file
//                });

                document.getElementById("clickImage").addEventListener("click", function () {
                    document.getElementById("file-input").click(); // Kích hoạt input file
                });


                
                var url = "${novel.imageURL}";
                const resetBtn = document.getElementById('reset');
                resetBtn.addEventListener("click", function () {
                    // Reset input file
                    document.getElementById('file-input').value = "";
                    // Reset ảnh xem trước (đổi thành ảnh mặc định)
                    document.getElementById('image-preview').src = (!url ? 'img/b3.png' : url);
                    ;
                });
            </script>
            <script src="js/mynovel/novelform.js"></script>
    </body>
</html>
