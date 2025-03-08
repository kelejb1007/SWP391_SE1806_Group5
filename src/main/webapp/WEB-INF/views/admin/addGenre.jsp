<%-- 
    Document   : addGenre.jsp
    Created on : 23-Feb-2025
    Author     : LienXuanThinh_ce182117
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Genre</title>
        <!-- Bootstrap Core CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/startmin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/font-awesome.min.css" type="text/css">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="header.jsp" />
            <jsp:include page="sidebar.jsp" />
            <div id="page-wrapper">
                <div class="container-fluid">




                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">Add New Genre</h1>
                        </div>
                    </div>

                    <!-- Hiển thị lỗi nếu có -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>

                    <div class="row">
                        <div class="col-lg-6">
                            <form action="${pageContext.request.contextPath}/managegenre" method="post">
                                <input type="hidden" name="action" value="add">
                                <div class="form-group">
                                    <label for="genreName">Genre Name:</label>
                                    <input type="text" class="form-control" id="genreName" name="genreName" placeholder="Enter genre name" required>
                                </div>
                                <button type="submit" class="btn btn-success">Add Genre</button>
                                <a href="${pageContext.request.contextPath}/managegenre" class="btn btn-default">Back to List</a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- jQuery -->
        <script src="${pageContext.request.contextPath}/js/startmin/jquery.min.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="${pageContext.request.contextPath}/js/startmin/bootstrap.min.js"></script>
        <!-- Custom Theme JavaScript -->
        <script src="${pageContext.request.contextPath}/js/startmin/startmin.js"></script>
    </body>
</html>
