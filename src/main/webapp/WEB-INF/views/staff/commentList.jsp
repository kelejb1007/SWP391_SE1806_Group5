<%-- 
    Document   : commentList
    Created on : 23 thg 2, 2025, 21:15:40
    Author     : ASUS
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comment list</title>
    <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut") %>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/startmin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/metisMenu.min.css">
    <link href="${pageContext.request.contextPath}/css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet">
</head>
<body>
    <div id="wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="sidebar.jsp" />

        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Comment list</h1>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Comments</div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover" id="dataTables-comments">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>User ID</th>
                                                <th>Novel ID</th>
                                                <th>Content</th>
                                                <th>Comment Date</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>
                                                <c:when test="${not empty comments}">
                                                    <c:forEach var="comment" items="${comments}">
                                                        <tr>
                                                            <td>${comment.commentID}</td>
                                                            <td>${comment.userID}</td>
                                                            <td>${comment.novelID}</td>
                                                            <td>${comment.content}</td>
                                                            <td>${comment.commentDate.toString().replace('T', ' ')}</td>
                                                            <td>
                                                                <form action="${pageContext.request.contextPath}/staff/comments" method="post" style="display:inline;">
                                                                    <input type="hidden" name="action" value="delete">
                                                                    <input type="hidden" name="commentID" value="${comment.commentID}">
                                                                    <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this comment?');">Delete</button>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td colspan="6" class="text-center">No comment available.</td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/startmin/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/startmin/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/startmin/metisMenu.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/startmin/startmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/startmin/dataTables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/startmin/dataTables/dataTables.bootstrap.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#dataTables-comments').DataTable({
                responsive: true
            });
        });
    </script>
</body>
</html>
