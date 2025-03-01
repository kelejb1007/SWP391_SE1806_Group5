<%-- 
    Document   : lockedChapters.jsp
    Created on : Mar 1, 2025, 6:26:16 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Locked Chapters</title>

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

            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">MANAGE LOCKED CHAPTERS</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    List of Locked Chapters
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Chapter ID</th>
                                                    <th>Novel Name</th>
                                                    <th>Chapter Name</th>
                                                    <th>Published Date</th>
                                                    <th>Status</th>
                                                    <th>Lock Reason</th>
                                                    <th>Lock Date</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="lockChapterLog" items="${lockedChapters}" varStatus="status">
                                                <tr>
                                                    <td>${status.index + 1}</td>
                                                    <td>${lockChapterLog.chapterID}</td>
                                                    <td>${lockChapterLog.novelName}</td>
                                                    <td>${lockChapterLog.chapterName}</td>
                                                    <td><fmt:formatDate value="${lockChapterLog.chapterCreatedDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                                <td>${lockChapterLog.chapterStatus}</td>
                                                <td>${lockChapterLog.lockReason}</td>
                                                <td><fmt:formatDate value="${lockChapterLog.lockDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                                <td>
                                                    <form action="unlockchapter" method="post" style="display: inline">
                                                        <input type="hidden" name="action" value="unlock">
                                                        <input type="hidden" name="chapterID" value="${lockChapterLog.chapterID}">
                                                        <button type="submit" class="btn btn-success">Unlock</button>
                                                    </form>
                                                </td>
                                                </tr>
                                            </c:forEach>  
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- Phân trang -->
                                    <div class="pagination" style="text-align: center; margin-top: 20px;">
                                        <c:if test="${currentPage > 1}">
                                            <a href="?action=${action}&${type == 'Novel' ? 'novelID' : type == 'User' ? 'userID' : ''}=${type == 'Novel' ? novelID : type == 'User' ? userID : ''}&page=${currentPage - 1}" class="btn btn-default">Previous</a>
                                        </c:if>
                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                            <a href="?action=${action}&${type == 'Novel' ? 'novelID' : type == 'User' ? 'userID' : ''}=${type == 'Novel' ? novelID : type == 'User' ? userID : ''}&page=${i}" 
                                               class="btn btn-default ${i == currentPage ? 'btn-primary' : ''}">${i}</a>
                                        </c:forEach>
                                        <c:if test="${currentPage < totalPages}">
                                            <a href="?action=${action}&${type == 'Novel' ? 'novelID' : type == 'User' ? 'userID' : ''}=${type == 'Novel' ? novelID : type == 'User' ? userID : ''}&page=${currentPage + 1}" class="btn btn-default">Next</a>
                                        </c:if>
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

        <!-- Page-Level Demo Scripts - Tables - Use for reference -->
        <script>
            $(document).ready(function () {
                $('#dataTables-example').DataTable({
                    responsive: true,
                    "pageLength": 10, // Số bản ghi hiển thị mặc định trên mỗi trang
                    "order": [[1, "desc"]] // Sắp xếp theo Chapter ID giảm dần (cột 1)
                });
            });
        </script>
    </body>
</html>