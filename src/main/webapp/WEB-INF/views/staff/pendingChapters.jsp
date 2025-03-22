<%-- 
    Document   : pendingChapters
    Created on : Mar 10, 2025, 3:12:01 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Submission</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css">

        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet">


        <script>
            function openLockModal(chapterID, chapterName, type, submissionCID) {
                document.getElementById('type').value = type;
                document.getElementById('submissionCID').value = submissionCID;
                document.getElementById('chapterID').value = chapterID;
                document.getElementById('lockReason').value = '';
                document.getElementById('chapterName').innerHTML = chapterName + " (ID=" + chapterID + ")";
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


            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">APPROVE CHAPTER</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    List of submissions
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Chapter Name</th>
                                                    <th>UserName</th>
                                                    <th>Submission Date</th>
                                                    <th>Type</th>
                                                    <th>Status</th>
                                                    <th>Draft</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="c" items="${requestScope.list}" varStatus="status">
                                                    <tr>
                                                        <td>${status.index + 1}</td>
                                                        <td><a href="managechapter?action=viewchapter&id=${c.chapterID}" title="${c.chapterName}">${c.chapterName}</a></td>
                                                        <td>${c.userName}</td>
                                                        <td>${c.submissionDate}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test = "${c.type == 'update'}">
                                                                    <a href="managechapter?action=viewchapter&id=${c.draftID}" title="View Draft">${c.type}</a>
                                                                </c:when>

                                                                <c:when test = "${c.type == 'post'}">
                                                                    ${c.type}
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                        <td>${c.status}</td>

                                                        <td>
                                                            <form action="managechapter" method="post" style="display: inline">
                                                                <input type="hidden" name="action" value="approve">
                                                                <input type="hidden" name="type" value="${c.type}">
                                                                <input type="hidden" name="submissionCID" value="${c.submissionCID}">
                                                                <input type="hidden" name="chapterID" value="${c.chapterID}">
                                                                <input type="hidden" name="draftID" value="${c.draftID}">
                                                                <button type="submit" class="btn btn-info" onclick="return confirm('Confirm to APPROVE the chapter: ${c.chapterName} (ID=${c.chapterID})')">Approve</button>
                                                            </form>
                                                            <button type="button" id="open" class="btn btn-danger" onclick="openLockModal('${c.chapterID}', '${c.chapterName}', '${c.type}', '${c.submissionCID}')">Reject</button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>  
                                            </tbody>
                                        </table>

                                        <!-- Modal (Popup) -->
                                        <div id="lockModal" class="modal fade" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <form action="managechapter" method="post" style="display: inline">
                                                        <div class="modal-header" style="border-bottom: none; height: 45px">
                                                            <h4 class="modal-title"  style="float: left; margin-right: 10px; width: 95%;
                                                                margin-bottom: 10px;display: -webkit-box; overflow: hidden;
                                                                /*                                                                white-space: nowrap; text-overflow: ellipsis; */
                                                                word-wrap: break-word; -webkit-box-orient: vertical; -webkit-line-clamp: 2;
                                                                ">Reject the chapter: <strong><span id="chapterName"></span></strong></h4>

                                                            <button type="button" class="close" style="float: right" data-dismiss="modal">&times;</button>
                                                        </div>

                                                        <div class="modal-body">
                                                            <textarea id="lockReason" name="rejectReason" class="form-control" placeholder="Enter reject reason" rows="3"></textarea>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                            <!--<button type="button" class="btn btn-danger" onclick="submitLock()">Confirm</button>-->

                                                            <input type="hidden" name="action" value="reject">
                                                            <input type="hidden" name="type" id="type">
                                                            <input type="hidden" name="submissionCID" id="submissionCID">
                                                            <input type="hidden" name="chapterID" id="chapterID">
                                                            <button type="submit" class="btn btn-danger">Confirm</button>
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

        <c:if test="${not empty message}">
            <script>
                window.onload = function () {
                    alert("${message}");
                };
            </script>
        </c:if>


        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>

        <script src="js/startmin/dataTables/jquery.dataTables.min.js"></script>
        <script src="js/startmin/dataTables/dataTables.bootstrap.min.js"></script>

        <script>
                $(document).ready(function () {
                    $('#dataTables-example').DataTable({
                        responsive: true
                    });
                });
        </script>

        <script src="js/startmin/raphael.min.js"></script>
        <script src="js/startmin/morris.min.js"></script>
        <script src="js/startmin/morris-data.js"></script>
    </body>
</html>
