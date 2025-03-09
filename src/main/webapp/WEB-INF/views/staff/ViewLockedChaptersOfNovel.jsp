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
        <link rel="stylesheet" href="css/startmin/bootstrap.css" />
        <link rel="stylesheet" href="css/startmin/startmin.css" />
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css" />
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css" />

        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet" />

        <script>
            function checkResource(resource) {
                if (!document.querySelector(resource)) {
                    console.error("Resource not found: " + resource);
                }
            }
            window.onload = function () {
                checkResource("link[href='css/startmin/bootstrap.css']");
                checkResource("link[href='css/startmin/dataTables/dataTables.bootstrap.css']");
            };

            function openUnlockModal(chapterID, chapterName) {
                document.getElementById('chapterID').value = chapterID;
                document.getElementById('unlockChapterName').innerHTML = chapterName + " (ID=" + chapterID + ")";
                const message = "${message}";
                if (message) {
                    document.getElementById('unlockMessage').innerHTML = message;
                    document.getElementById('unlockMessage').style.display = "block";
                }
                $("#unlockModal").modal("show");
            }

            function clearModalMessage() {
                document.getElementById('unlockMessage').style.display = "none";
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
                                    List of Locked Chapters 
                                    <c:choose>
                                        <c:when test="${not empty novelId and novelId > 0}">
                                            ${novelId}
                                        </c:when>
                                        <c:otherwise>
                                            N/A
                                        </c:otherwise>
                                    </c:choose>
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
                                                                <td>${chapter.chapterName}</td>
                                                                <td>
                                                                    <fmt:formatDate value="${chapter.publishedDate}" pattern="dd/MM/yyyy HH:mm" />
                                                                </td>
                                                                <td>${chapter.chapterStatus}</td>
                                                                <td>
                                                                    <button type="button" class="btn btn-success"
                                                                            onclick="openUnlockModal('${chapter.chapterID}', '${chapter.chapterName}')">
                                                                        Unlock
                                                                    </button>
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

                                        <!-- Unlock Modal -->
                                        <div id="unlockModal" class="modal fade" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <form action="managechapter" method="post" style="display: inline">
                                                        <div class="modal-header" style="border-bottom: none; height: 45px">
                                                            <h4 class="modal-title" style="float: left; margin-right: 10px; width: 95%;
                                                                margin-bottom: 10px; display: -webkit-box; overflow: hidden; 
                                                                word-wrap: break-word; -webkit-box-orient: vertical; -webkit-line-clamp: 2;">
                                                                You are unlocking the chapter: <strong><span id="unlockChapterName"></span></strong>
                                                            </h4>
                                                            <button type="button" class="close" style="float: right" data-dismiss="modal" onclick="clearModalMessage()">×</button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <input type="hidden" name="chapterId" id="chapterID">
                                                            <p id="unlockMessage" style="color: red; display: none;"></p>
                                                            <p>Are you sure you want to unlock this chapter?</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="clearModalMessage()">Cancel</button>
                                                            <input type="hidden" name="action" value="unlock">
                                                            <button type="submit" class="btn btn-success">Confirm</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
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