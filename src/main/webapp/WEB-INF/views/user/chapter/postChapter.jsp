<%-- 
    Document   : postChapter.jsp
    Created on : [Original creation date]
    Author     : [Original author]
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Chapter</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut") %>">

        <!-- CSS từ postNovel.jsp -->
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="shortcut icon" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <link rel="stylesheet" type="text/css" href="css/mynovel/mynovel2.css" crossorigin="anonymous">
        <script charset="utf-8" src="js/mynovel/mynovel2.js?v=1" crossorigin="anonymous"></script>

        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

        <!-- CSS cần thiết cho header2.jsp -->
        <link rel="stylesheet" href="css/homepage2/header2.css?v=1">
        <link rel="stylesheet" href="css/homepage2/iconfont.css?v=1">

        <style>
            .form-container {
                margin: auto;
                padding: 20px;
                border-radius: 8px;
                background-color: #fff;
            }
            .ant-form-item {
                margin-bottom: 24px;
            }
            .ant-input, .ant-input-lg {
                border-radius: 8px;
                font-size: 16px;
            }
            textarea.ant-input {
                height: 130px;
                resize: none;
                min-height: 53.6px;
            }
            h3 {
                text-align: center;
                margin-bottom: 32px;
                font-size: 20px;
                font-weight: 700;
                line-height: 24px;
                color: rgba(18,18,23,.6);
                font-variant: small-caps;
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
            .back-button {
                margin-top: 10px;
                display: inline-block;
                margin-left: 10px;
            }
            .back-button a {
                display: inline-block;
                padding: 10px 20px;
                background-color: #d9e0e7;
                color: #000;
                text-decoration: none;
                border-radius: 5px;
                font-weight: 600;
                border: 1px solid #d9d9d9;
            }  
        </style>
    </head>
    <body>
        <header>
            <jsp:include page="/WEB-INF/views/user/components/header2.jsp" /> 
        </header>

        <div class="g_main_wrap f1 pr" style="zoom: 1.1">
            <div style="">
                <div id="main_scroll_container" class="scroller--dBDRL pr">
                    <div class="main_content--0x57a">
                        <div class="default--zRToH bc_fff">
                            <div class="form-container">
                                <%
                                    Integer novelId = (Integer) request.getAttribute("novelId");
                                    Integer chapterNumber = (Integer) request.getAttribute("chapterNumber");
                                    Integer chapterId = (Integer) request.getAttribute("chapterId");
                                    String novelName = (String) request.getAttribute("novelName");
                                    String chapterName = (String) request.getAttribute("chapterName");
                                    String chapterContent = (String) request.getAttribute("chapterContent");
                                    String message = (String) request.getAttribute("message");
                                    String messageType = (request.getAttribute("messageType") != null) ? request.getAttribute("messageType").toString() : "success";
                                %>

                                <% if (novelId == null || chapterNumber == null) { %>
                                    <p class="message error"><%= message != null ? message : "Invalid novel or chapter information." %></p>
                                <% } else { %>

                                <form class="ant-form ant-form-vertical setting_form--R6kRQ" action="<%= request.getContextPath()%>/postChapter" method="post" enctype="multipart/form-data">
                                    <h3>
                                        <span role="img" class="anticon vam mr8">
                                            <svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                <use xlink:href="#i-files"></use>
                                            </svg>
                                        </span>
                                        POST CHAPTER
                                    </h3>

                                    <!-- Novel ID (Hidden) -->
                                    <input type="hidden" id="novelId" name="novelId" value="<%= novelId%>" readonly>

                                    <!-- Chapter ID (Hidden) -->
                                    <input type="hidden" id="chapterId" name="chapterId" value="<%= chapterId != null ? chapterId : "" %>">

                                    <!-- Novel Name -->
                                    <div class="ant-form-item item--1wVCg">
                                        <div class="ant-row ant-form-item-row">
                                            <div class="ant-col ant-form-item-label">
                                                <label for="novelName" class="ant-form-item-required" title="">
                                                    <div class="dib"><span>Novel Name</span></div>
                                                </label>
                                            </div>
                                            <div class="ant-form-item-control-input-content">
                                                <span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                    <input name="novelName" id="novelName" class="ant-input ant-input-lg" 
                                                           type="text" value="<%= novelName != null ? novelName : "Unknown"%>" readonly>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Chapter Number -->
                                    <div class="ant-form-item item--1wVCg">
                                        <div class="ant-row ant-form-item-row">
                                            <div class="ant-col ant-form-item-label">
                                                <label for="chapterNumber" class="ant-form-item-required" title="">
                                                    <div class="dib"><span>Chapter Number</span></div>
                                                </label>
                                            </div>
                                            <div class="ant-form-item-control-input-content">
                                                <span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                    <input name="chapterNumber" id="chapterNumber" class="ant-input ant-input-lg" 
                                                           type="number" value="<%= chapterNumber%>" min="1">
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Chapter Title -->
                                    <div class="ant-form-item item--1wVCg">
                                        <div class="ant-row ant-form-item-row">
                                            <div class="ant-col ant-form-item-label">
                                                <label for="chapterName" class="ant-form-item-required" title="">
                                                    <div class="dib"><span>Chapter Title</span></div>
                                                </label>
                                            </div>
                                            <div class="ant-form-item-control-input-content">
                                                <span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                    <input name="chapterName" id="chapterName" class="ant-input ant-input-lg" 
                                                           type="text" placeholder="Enter chapter title" 
                                                           value="<%= chapterName != null ? chapterName : "" %>" required>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Input Method -->
                                    <div class="ant-form-item item--1wVCg">
                                        <div class="ant-row ant-form-item-row">
                                            <div class="ant-col ant-form-item-label">
                                                <label for="inputMethod" class="ant-form-item-required" title="">Choose Input Method</label>
                                            </div>
                                            <div class="ant-form-item-control-input-content">
                                                <input type="radio" id="manual" name="inputMethod" value="manual" checked onclick="toggleInputMethod()">
                                                <label for="manual" style="color: rgba(18, 18, 23, .9) !important; font-weight: 600; margin: 0px 7px 3px">Enter Manually</label>
                                                <input type="radio" id="fileUpload" name="inputMethod" value="file" onclick="toggleInputMethod()">
                                                <label for="fileUpload" style="color: rgba(18, 18, 23, .9) !important; font-weight: 600; margin: 0px 7px 3px">Upload File</label>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Chapter Content (Manual Input) -->
                                    <div class="ant-form-item item--1wVCg" id="textInput">
                                        <div class="ant-row ant-form-item-row">
                                            <div class="ant-col ant-form-item-label">
                                                <label for="chapterContent" class="ant-form-item-required" title="">Chapter Content</label>
                                            </div>
                                            <div class="ant-form-item-control-input-content">
                                                <span class="ant-input-affix-wrapper">
                                                    <textarea name="chapterContent" id="chapterContent" class="ant-input" 
                                                              placeholder="Type the chapter content here" 
                                                              required><%= chapterContent != null ? chapterContent : "" %></textarea>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Chapter Content (File Upload) -->
                                    <div class="ant-form-item item--1wVCg" id="fileInput" style="display: none;">
                                        <div class="ant-row ant-form-item-row">
                                            <div class="ant-col ant-form-item-label">
                                                <label for="file" title="">Upload Chapter File (.txt)</label>
                                            </div>
                                            <div class="ant-form-item-control-input-content">
                                                <input type="file" id="file" name="file" accept=".txt">
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Hidden Field for Chapter Status -->
                                    <input type="hidden" name="chapterStatus" value="pending">

                                    <!-- Submit and Reset Buttons -->
                                    <button type="submit" class="ant-btn ant-btn-primary ant-btn-lg button--4vWlZ"><span>Post Chapter</span></button>
                                    <button type="reset" class="ant-btn ant-btn-primary ant-btn-lg button--4vWlZ"><span>Reset</span></button>
                                    <div class="back-button">
                                        <a href="<%= request.getContextPath()%>/mynovel?action=viewdetail&novelID=<%= novelId%>">Back to Novel Details</a>
                                    </div>
                                </form>

                                <% if (message != null) { %>
                                    <p class="message <%= messageType%>"><%= message%></p>
                                <% } %>

                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer>
            <jsp:include page="/WEB-INF/views/user/components/footer.jsp" /> 
        </footer>

        <script>
            function toggleInputMethod() {
                const manualInput = document.getElementById("textInput");
                const fileInput = document.getElementById("fileInput");
                const textArea = document.getElementById("chapterContent");
                const fileField = document.getElementById("file");

                if (document.getElementById("manual").checked) {
                    manualInput.style.display = "block";
                    fileInput.style.display = "none";
                    textArea.required = true;
                    fileField.required = false;
                    fileField.value = "";
                } else {
                    manualInput.style.display = "none";
                    fileInput.style.display = "block";
                    textArea.required = false;
                    fileField.required = true;
                }
            }
        </script>
    </body>
</html>