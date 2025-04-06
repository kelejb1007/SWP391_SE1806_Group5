<%-- 
    Document   : notification
    Created on : 06-Apr-2025, 07:23:34
    Author     : Admin
--%>

<%-- 
    Document   : notification
    Created on : 06-Apr-2025, 07:23:34
    Author     : Admin
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="css/favorite/history.css?v=3">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <meta charset="UTF-8">
        <title>Reading History</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 900px;
            margin: 50px auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 25px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .notification {
            padding: 20px;
            margin: 15px 0;
            border-radius: 8px;
            background-color: #eef7fa;
            border-left: 5px solid #007bff;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.08);
        }
        .notification-header {
            font-weight: bold;
            color: #007bff;
            font-size: 18px;
        }
        .notification-time {
            font-size: 14px;
            color: #777;
            margin-top: 8px;
        }
        .notification.read {
            background-color: #e0f7fa;
            border-left: 5px solid #80d6ff;
        }
        .alert-info {
            background-color: #d1ecf1;
            border-color: #bee5eb;
            color: #0c5460;
            padding: 15px;
            font-size: 16px;
            border-radius: 10px;
            text-align: center;
        }
        footer {
            background-color: #ffffff;
            text-align: center;
            padding: 20px 0;
            margin-top: 40px;
            border-top: 1px solid #e0e0e0;
        }
        footer p {
            color: #777;
            margin: 0;
        }
        .btn {
            margin-right: 10px;
        }
    </style>
    <body class="g_site_readnovel">
        <div class="wrap home">
            <jsp:include page="/WEB-INF/views/user/components/header2.jsp" /> 

            <!-- Main content -->
            <div class="container">
                <h2>Notification List</h2>

                <!-- Check if there are any notifications -->
                <c:if test="${not empty notifications}">
                    <c:forEach var="notification" items="${notifications}">
                        <div class="notification ${notification.isRead ? 'read' : ''}">
                            <div class="notification-header">
                                The novel : ${notification.novelName}                           
                            </div>
                            <div class="notification-header">
                                
                                <a href="novel-detail?id=${notification.novelID}" class="novel-name">  New chapter available :${notification.chapterName} </a>
                            </div>
                            <div class="notification-time">
                                Time: ${notification.createdAt}
                            </div>
                            
                          
                            <div class="notification-actions">


                                <!-- Delete Button -->
                                <form action="NotificationController" method="post">
                                    <input type="hidden" name="notificationId" value="${notification.id}">
                                    <input type="hidden" name="action" value="delete">
                                    <button type="submit" class="btn" style="background-color: red; color: white;">Delete</button>
                                </form>



                            </div>
                        </div>
                    </c:forEach>
                </c:if>

                <!-- If there are no notifications -->
                <c:if test="${empty notifications}">
                    <div class="alert alert-info" role="alert">
                        No notifications available.
                    </div>
                </c:if>
            </div>

            <footer>
                <p>&copy; 2025 NovelReader. All rights reserved.</p>
            </footer>
    </body>
</html>
