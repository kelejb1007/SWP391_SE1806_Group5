<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Chapter" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/home/header.css">
        <title>Delete Chapter</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f7fa;
                margin: 0;
                padding: 0;
                color: #333;
            }
            .container {
                max-width: 700px; /* Tăng chiều rộng từ 600px lên 700px */
                margin: 40px auto;
                padding: 30px; /* Tăng padding từ 25px lên 30px để cân đối */
                background-color: #ffffff;
                border-radius: 12px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            }
            h2 {
                text-align: center;
                color: #2c3e50;
                font-size: 28px;
                margin-bottom: 20px;
                font-weight: 600;
            }
            .form-container {
                padding: 30px; /* Tăng padding từ 25px lên 30px */
                background-color: #f9f9f9;
                border-radius: 8px;
                border: none;
            }
            .form-group {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 15px;
                padding: 12px 20px; /* Tăng padding từ 12px 18px lên 12px 20px */
                background-color: #fff;
                border-radius: 6px;
                border: none;
                transition: all 0.3s ease;
                box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
            }
            .form-group:hover {
                transform: translateY(-2px);
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .form-group label {
                font-weight: 600;
                color: #34495e;
                font-size: 16px;
                flex: 1;
            }
            .form-group span {
                color: #7f8c8d;
                font-size: 16px;
                flex: 2;
                text-align: right;
            }
            .message {
                font-weight: 500;
                margin: 15px 0;
                padding: 15px;
                border-radius: 8px;
                text-align: center;
                font-size: 16px;
            }
            .success {
                color: #27ae60;
                background-color: #e8f5e9;
                border: 1px solid #27ae60;
            }
            .error {
                color: #c0392b;
                background-color: #f9ebea;
                border: 1px solid #c0392b;
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
            button {
                padding: 12px 25px;
                border: none;
                border-radius: 6px;
                font-size: 16px;
                font-weight: 500;
                cursor: pointer;
                transition: all 0.3s ease;
            }
            .btn-delete {
                background-color: #e74c3c;
                color: #fff;
            }
            .btn-delete:hover {
                background-color: #c0392b;
                transform: translateY(-2px);
                box-shadow: 0 2px 10px rgba(231, 76, 60, 0.3);
            }
            .btn-cancel {
                background-color: #95a5a6;
                color: #fff;
            }
            .btn-cancel:hover {
                background-color: #7f8c8d;
                transform: translateY(-2px);
                box-shadow: 0 2px 10px rgba(149, 165, 166, 0.3);
            }
            @media (max-width: 600px) {
                .container {
                    margin: 20px;
                    padding: 15px;
                }
                .form-container {
                    padding: 15px;
                }
                .form-group {
                    flex-direction: column;
                    align-items: flex-start;
                    padding: 8px 12px;
                }
                .form-group label, .form-group span {
                    text-align: left;
                    flex: none;
                    margin-bottom: 5px;
                    font-size: 14px;
                }
                .button-group {
                    flex-direction: column;
                    gap: 10px;
                }
                button {
                    width: 100%;
                    padding: 10px;
                    font-size: 14px;
                }
                .confirmation-text {
                    font-size: 16px;
                    margin-bottom: 20px;
                }
            }
        </style>
    </head>
    <body>
        <header>
            <jsp:include page="/WEB-INF/views/user/components/header.jsp" /> 
        </header>

        <div class="container">
            <h2>Delete Chapter</h2>
            <div class="form-container">
                <%
                    Chapter chapter = (Chapter) request.getAttribute("chapter");
                    String message = (String) request.getAttribute("message");
                    String messageType = (request.getAttribute("messageType") != null) ? request.getAttribute("messageType").toString() : "success";
                %>

                <% if (message != null) { %>
                <p class="message <%= messageType %>"><%= message %></p>
                <% } else if (chapter == null) { %>
                <p class="message error">Error: Chapter information is missing.</p>
                <% } else { %>

                <p class="confirmation-text">Are you sure you want to delete the following chapter?</p>
                <div class="form-group">
                    <label>Novel ID:</label>
                    <span><%= chapter.getNovelID() %></span>
                </div>
                <div class="form-group">
                    <label>Novel Name:</label>
                    <span><%= chapter.getNovelName() != null ? chapter.getNovelName() : "Unknown" %></span>
                </div>
                <div class="form-group">
                    <label>Chapter Number:</label>
                    <span><%= chapter.getChapterNumber() %></span>
                </div>
                <div class="form-group">
                    <label>Chapter Title:</label>
                    <span><%= chapter.getChapterName() %></span>
                </div>

                <form action="<%= request.getContextPath()%>/deleteChapter" method="post">
                    <input type="hidden" name="novelName" value="<%= chapter.getNovelName() %>">
                    <input type="hidden" name="chapterNumber" value="<%= chapter.getChapterNumber() %>">
                    <div class="button-group">
                        <button type="submit" class="btn-delete">Delete Chapter</button>
                        <a href="<%= request.getContextPath()%>/user/chapters"><button type="button" class="btn-cancel">Cancel</button></a>
                    </div>
                </form>

                <% } %>
            </div>
        </div>

        <footer>
            <jsp:include page="/WEB-INF/views/user/components/footer.jsp" /> 
        </footer>
    </body>
</html>