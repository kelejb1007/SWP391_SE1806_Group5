<%-- 
    Document   : viewAllGenres.jsp
    Created on : 23-Feb-2025, 21:10:31
    Author     : LienXuanThinh_ce182117
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>View List of Genres</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut") %>">
        <!-- Bootstrap Core CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/startmin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/startmin/font-awesome.min.css" type="text/css">
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
                    <!-- Page Heading -->
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">List of Genres</h1>
                            <!-- Nút thêm genre -->
                            <a href="${pageContext.request.contextPath}/managegenre?action=add" class="btn btn-success">Add Genre</a>
                        </div>
                    </div>
                    <!-- /.row -->
                    
                    <!-- Table to display genres -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Genre List
                                </div>
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-genre">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Genre ID</th>
                                                    <th>Genre Name</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${not empty listGenre}">
                                                        <c:forEach var="genre" items="${listGenre}" varStatus="status">
                                                            <tr>
                                                                <td>${status.index + 1}</td>
                                                                <td>${genre.genreID}</td>
                                                                <td>${genre.genreName}</td>
                                                                <td>
                                                                    <!-- Nút xóa, có thể dùng thêm xác nhận xóa -->
                                                                    <a href="${pageContext.request.contextPath}/managegenre?action=delete&id=${genre.genreID}" 
                                                                       class="btn btn-danger btn-sm" 
                                                                       onclick="return confirm('Are you sure you want to delete this genre?');">
                                                                        Delete
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td colspan="4" class="text-center">No genres found.</td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- /.table-responsive -->
                                </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.container-fluid -->
            </div>
            <!-- /#page-wrapper -->
        </div>
        <!-- /#wrapper -->

        <!-- jQuery -->
        <script src="${pageContext.request.contextPath}/js/startmin/jquery.min.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="${pageContext.request.contextPath}/js/startmin/bootstrap.min.js"></script>
        <!-- Metis Menu Plugin JavaScript -->
        <script src="${pageContext.request.contextPath}/js/startmin/metisMenu.min.js"></script>
        <!-- Custom Theme JavaScript -->
        <script src="${pageContext.request.contextPath}/js/startmin/startmin.js"></script>
        <!-- DataTables JavaScript -->
        <script src="${pageContext.request.contextPath}/js/startmin/dataTables/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/startmin/dataTables/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#dataTables-genre').DataTable({
                    responsive: true
                });
            });
        </script>
    </body>
</html>
