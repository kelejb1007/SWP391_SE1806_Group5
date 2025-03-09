<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View All Chapters</title>
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="css/startmin/bootstrap.css">
    <link rel="stylesheet" href="css/startmin/startmin.css">
    <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/startmin/metisMenu.min.css">
    <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet">
</head>
<body>
    <div id="wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="sidebar.jsp" />

        <c:if test="${not empty message}">
            <script>
                window.onload = function() {
                    alert("${message}");
                };
            </script>
        </c:if>

        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">MANAGE CHAPTERS</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                List of Chapters for Novel ID: ${novelId}
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Chapter ID</th>
                                                <th>Chapter Name</th>
                                                <th>Published Date</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>
                                                <c:when test="${not empty chapters}">
                                                    <c:forEach var="chapter" items="${chapters}" varStatus="status">
                                                        <tr>
                                                            <td>${status.index + 1}</td>
                                                            <td>${chapter.chapterID}</td>
                                                            <td>${chapter.chapterName}</td>
                                                            <td><fmt:formatDate value="${chapter.publishedDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                                            <td>${chapter.chapterStatus}</td>
                                                            <td>
                                                                <!-- Nút View Content -->
                                                                <a href="chapter?id=${chapter.chapterID}" class="btn btn-info">View Content</a>
                                                                <!-- Nút Lock (chỉ hiển thị khi chapterStatus là active) -->
                                                                <c:if test="${chapter.chapterStatus == 'active'}">
                                                                    <a href="managechapter?action=lock&chapterId=${chapter.chapterID}" class="btn btn-danger">Lock</a>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td colspan="6">No chapters found for this novel</td>
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

    <!-- jQuery -->
    <script src="js/startmin/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="js/startmin/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="js/startmin/metisMenu.min.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="js/startmin/startmin.js"></script>
    <!-- DataTables JavaScript -->
    <script src="js/startmin/dataTables/jquery.dataTables.min.js"></script>
    <script src="js/startmin/dataTables/dataTables.bootstrap.min.js"></script>

    <script>
        $(document).ready(function() {
            $('#dataTables-example').DataTable({
                responsive: true,
                "pageLength": 10,
                "order": [[1, "desc"]]
            });
        });
    </script>
</body>
</html>