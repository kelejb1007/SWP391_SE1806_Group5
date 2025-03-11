<%-- 
    Document   : ViewLockedChaptersByNovel.jsp
    Created on : Mar 8, 2025
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Locked Chapters of Novel</title>

        <!-- Bootstrap Core CSS -->
        <link rel="stylesheet" href="css/startmin/bootstrap.min.css" />
        <link rel="stylesheet" href="css/startmin/startmin.css" />
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css" />
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css" />

        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet" />

        <script>
            function openLockModal(chapterID) {
                document.getElementById('chapterID').value = chapterID;
                document.getElementById('lockReason').value = '';
//                var modal = new bootstrap.Modal(document.getElementById('lockModal'));
//                modal.show();
                $("#lockModal").modal("show");
            }
        </script>
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="header.jsp" />
            <jsp:include page="sidebar.jsp" />

            <c:if test="${not empty message}">
                <script>
                    window.onload = function () {
                        alert("${message}");
                    };
                </script>
            </c:if>
            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">
                                MANAGE CHAPTERS
                            </h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    List of Locked Chapters for ${chapter.novelName}
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
                                                    <c:when test="${not empty lockedChapters}">
                                                        <c:forEach var="chapter" items="${lockedChapters}" varStatus="status">
                                                            <tr>
                                                                <td>${status.index + 1}</td>
                                                                <td>${chapter.chapterID}</td>
                                                                <td><a href="managechapter?action=viewchapter&id=${chapter.chapterID}" title="${chapter.chapterName}">${chapter.chapterName}</a></td>
                                                                <td>
                                                                    <fmt:formatDate value="${chapter.publishedDate}" pattern="dd/MM/yyyy HH:mm" />
                                                                </td>
                                                                <td>${chapter.chapterStatus}</td>
                                                                <td>
                                                                    <form action="managechapter" method="post" style="display: inline">
                                                                        <input type="hidden" name="action" value="unlock">
                                                                        <input type="hidden" name="chapterID" value="${chapter.chapterID}">
                                                                        <button type="submit" class="btn btn-info" onclick="return confirm('Are you sure to unclock the chapter: ${chapter.chapterName} (ID=${chapter.chapterID})')">Unlock</button>
                                                                    </form>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td colspan="6">No locked chapters found for this novel</td>
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
                                                                            $(document).ready(function () {
                                                                                $('#dataTables-example').DataTable({
                                                                                    responsive: true,
                                                                                    "pageLength": 10,
                                                                                    "order": [[1, "desc"]],
                                                                                    "language": {
                                                                                        "emptyTable": "No data available in table",
                                                                                        "info": "Showing _START_ to _END_ of _TOTAL_ entries",
                                                                                        "infoEmpty": "Showing 0 to 0 of 0 entries",
                                                                                        "infoFiltered": "(filtered from _MAX_ total entries)"
                                                                                    }
                                                                                });
                                                                            });
        </script>
    </body>
</html>