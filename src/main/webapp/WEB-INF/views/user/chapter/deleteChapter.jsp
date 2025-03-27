<%-- 
    Document   : deleteChapter
    Created on : [Original creation date]
    Author     : [Original author]
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Chapter" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Delete Chapter</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <!-- CSS từ postNovel.jsp -->
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="shortcut icon" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <link rel="stylesheet" type="text/css" href="css/mynovel/mynovel2.css" crossorigin="anonymous">
        <script charset="utf-8" src="js/mynovel/mynovel2.js?v=1" crossorigin="anonymous"></script>

        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

        <!-- CSS cần thiết cho header.jsp -->
        <link rel="stylesheet" href="css/home/header.css">

        <style>
            /* Điều chỉnh style để giống postNovel.jsp */
            .form-container {
                margin: auto;
                padding: 20px;
                border-radius: 8px;
                background-color: #fff; /* Trắng giống postNovel.jsp */
            }
            .ant-form-item {
                margin-bottom: 24px; /* Khoảng cách giữa các field giống postNovel.jsp */
            }
            .ant-input, .ant-input-lg {
                border-radius: 8px; /* Bo góc giống postNovel.jsp */
                font-size: 16px;
            }
            h3 {
                margin-bottom: 32px;
                font-size: 18px;
                font-weight: 400;
                line-height: 24px;
            }
            .message {
                font-weight: bold;
                margin-top: 15px;
                padding: 10px;
                border-radius: 5px;
            }
            .success {
                color: green;
                background-color: #e6ffe6;
                border: 1px solid green;
            }
            .error {
                color: red;
                background-color: #ffe6e6;
                border: 1px solid red;
            }
            .confirmation-text {
                text-align: center;
                font-size: 18px;
                color: #2c3e50;
                margin-bottom: 25px;
                font-weight: 500;
            }
            .button-group {
                display: flex;
                justify-content: center;
                gap: 15px;
                margin-top: 25px;
            }
        </style>
    </head>
    <body>
        <header>
            <jsp:include page="/WEB-INF/views/user/components/header.jsp" /> 
        </header>

        <div class="g_main_wrap f1 pr" style="zoom: 1.1">
            <div style="">
                <div id="main_scroll_container" class="scroller--dBDRL pr">
                    <div class="header_ph--kHZzY"></div>
                    <div class="main_content--0x57a">
                        <div class="default--zRToH bc_fff">
                            <div class="form-container">
                                <%
                                    Chapter chapter = (Chapter) request.getAttribute("chapter");
                                    String message = (String) request.getAttribute("message");
                                    String messageType = (request.getAttribute("messageType") != null) ? request.getAttribute("messageType").toString() : "success";
                                %>

                                <h3 class="mb32 fvsc fw400 fs18 ell lh24">
                                    <span role="img" class="anticon vam mr8">
                                        <svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                        <use xlink:href="#i-files"></use>
                                        </svg>
                                    </span>
                                    Delete Chapter Information
                                </h3>

                                <% if (message != null) {%>
                                <p class="message <%= messageType%>"><%= message%></p>
                                <% } else if (chapter == null) { %>
                                <p class="message error">Error: Chapter information is missing.</p>
                                <% } else {%>

                                <p class="confirmation-text">Are you sure you want to delete the following chapter?</p>

                                <!-- Novel ID -->
                                <div class="ant-form-item item--1wVCg">
                                    <div class="ant-row ant-form-item-row">
                                        <div class="ant-col ant-form-item-label">
                                            <label title="">
                                                <div class="dib"><span>Novel ID</span></div>
                                            </label>
                                        </div>
                                        <div class="ant-form-item-control-input-content">
                                            <span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                <input class="ant-input ant-input-lg" type="text" value="<%= chapter.getNovelID()%>" readonly>
                                            </span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Novel Name -->
                                <div class="ant-form-item item--1wVCg">
                                    <div class="ant-row ant-form-item-row">
                                        <div class="ant-col ant-form-item-label">
                                            <label title="">
                                                <div class="dib"><span>Novel Name</span></div>
                                            </label>
                                        </div>
                                        <div class="ant-form-item-control-input-content">
                                            <span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                <input class="ant-input ant-input-lg" type="text" value="<%= chapter.getNovelName() != null ? chapter.getNovelName() : "Unknown"%>" readonly>
                                            </span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Chapter Number -->
                                <div class="ant-form-item item--1wVCg">
                                    <div class="ant-row ant-form-item-row">
                                        <div class="ant-col ant-form-item-label">
                                            <label title="">
                                                <div class="dib"><span>Chapter Number</span></div>
                                            </label>
                                        </div>
                                        <div class="ant-form-item-control-input-content">
                                            <span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                <input class="ant-input ant-input-lg" type="text" value="<%= chapter.getChapterNumber()%>" readonly>
                                            </span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Chapter Title -->
                                <div class="ant-form-item item--1wVCg">
                                    <div class="ant-row ant-form-item-row">
                                        <div class="ant-col ant-form-item-label">
                                            <label title="">
                                                <div class="dib"><span>Chapter Title</span></div>
                                            </label>
                                        </div>
                                        <div class="ant-form-item-control-input-content">
                                            <span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                <input class="ant-input ant-input-lg" type="text" value="<%= chapter.getChapterName()%>" readonly>
                                            </span>
                                        </div>
                                    </div>
                                </div>

                                <form action="<%= request.getContextPath()%>/deleteChapter" method="post">
                                    <input type="hidden" name="novelId" value="<%= chapter.getNovelID()%>">
                                    <input type="hidden" name="chapterId" value="<%= chapter.getChapterID()%>">
                                    <div class="button-group">
                                        <button type="submit" class="ant-btn ant-btn-primary ant-btn-lg button--4vWlZ btn-delete"><span>Delete Chapter</span></button>
                                        <a href="<%= request.getContextPath()%>/mynovel?action=viewdetail&novelID=<%= chapter.getNovelID()%>">
                                            <button type="button" class="ant-btn ant-btn-primary ant-btn-lg button--4vWlZ btn-cancel"><span>Cancel</span></button>
                                        </a>
                                    </div>
                                </form>

                                <% }%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer>
            <jsp:include page="/WEB-INF/views/user/components/footer.jsp" /> 
        </footer>
    </body>
</html>