<%-- 
    Document   : updateChapter
    Created on : Mar 8, 2025, 9:31:44 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Chapter" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/home/header(d).css">
        <title>Update Chapter</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            .form-container {
                margin: auto;
                padding: 20px;
                border-radius: 8px;
                background-color: #f9f9f9;
            }
            .form-group {
                margin-bottom: 15px;
            }
            label {
                font-weight: bold;
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
            button {
                padding: 8px 12px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            button:hover {
                background-color: #0056b3;
            }
            textarea {
                width: 100%;
                height: 230px;
                resize: none;
                font-size: 18px;
                padding: 8px;
            }
            h2 {
                text-align: center;
            }
            
            .areaInput {
                font-family: 'Open Sans', sans-serif;
                font-size: 1.1rem;
                /*color: #d9534f; */
                font-weight: 600;
                text-indent: 0.5rem;
                opacity: 0.7;
            }
        </style>       
    </head>
    <body>
        <header>
            <jsp:include page="/WEB-INF/views/user/components/header.jsp" /> 
        </header>

        <h2>Update Chapter</h2>
        <div class="form-container">
            <%
                Chapter chapter = (Chapter) request.getAttribute("chapter");
                Integer novelId = (Integer) request.getAttribute("novelId");
                String novelName = (String) request.getAttribute("novelName");
                String message = (String) request.getAttribute("message");
                String messageType = (request.getAttribute("messageType") != null) ? request.getAttribute("messageType").toString() : "success";
            %>

            <% if (chapter == null || novelId == null) { %>
            <p class="message error">Error: Chapter or Novel information is missing.</p>
            <% } else {%>

            <form action="<%= request.getContextPath()%>/updateChapter" method="post" enctype="multipart/form-data">

                <div class="form-group">
                    <label for="novelName">Novel Name:</label>
                    <input type="text" id="novelName" name="novelName" value="<%= novelName != null ? novelName : "Unknown"%>" readonly>
                </div>

                <div class="form-group">
                    <label for="chapterNumber">Chapter Number:</label>
                    <input type="number" id="chapterNumber" name="chapterNumber" value="<%= chapter.getChapterNumber()%>" readonly>
                </div>

                <div class="form-group">
                    <label for="chapterName">Chapter Title:</label>
                    <input type="text" id="chapterName" name="chapterName" value="<%= chapter.getChapterName()%>" required>
                </div>

                <div class="form-group">
                    <label>Choose Input Method:</label><br>
                    <input type="radio" id="manual" name="inputMethod" value="manual" checked onclick="toggleInputMethod()">
                    <label for="manual">Enter Manually</label>
                    <input type="radio" id="fileUpload" name="inputMethod" value="file" onclick="toggleInputMethod()">
                    <label for="fileUpload">Upload File</label>
                </div>

                <div class="form-group" id="textInput">
                    <label for="chapterContent">Chapter Content:</label>
                    <textarea class="areaInput" id="chapterContent" name="chapterContent" >${chapterContent}</textarea>
                </div>

                <div class="form-group" id="fileInput" style="display: none;">
                    <label for="file">Upload Chapter File (.txt):</label>
                    <input type="file" id="file" name="file" accept=".txt">
                </div>

                <button type="submit">Update Chapter</button>
            </form>

            <% if (message != null) {%>
            <p class="message <%= messageType%>"><%= message%></p>
            <% } %>

            <% }%>
        </div>

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
    <footer>
        <jsp:include page="/WEB-INF/views/user/components/footer.jsp" /> 
    </footer>
</html>
