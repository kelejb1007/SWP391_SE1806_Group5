<%-- 
    Document   : submission
    Created on : Mar 3, 2025, 6:09:11 PM
    Author     : Nguyen Thanh Trung
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
            function openLockModal(novelID, novelName, type, submissionNID) {
                document.getElementById('type').value = type;
                document.getElementById('submissionNID').value = submissionNID;
                document.getElementById('novelID').value = novelID;
                document.getElementById('lockReason').value = '';
                document.getElementById('novelName').innerHTML = novelName + " (ID=" + novelID + ")";
//                var modal = new bootstrap.Modal(document.getElementById('lockModal'));
//                modal.show();
                $("#lockModal").modal("show");
            }

            function viewDetail(event, novelID) {
                if (event.target.tagName.toLowerCase() !== 'button' || event.target.tagName.toLowerCase() !== 'a') {
                    window.location.href = 'managenovel?action=viewdetail&novelID=' + novelID;
                }
            }
        </script>
    </head>
    <body>


        <div id="wrapper">
            <jsp:include page="header.jsp" />
            <jsp:include page="sidebar.jsp" />
            <c:if test="${not empty popup}">
                <script>
                    window.onload = function () {
                        alert("${popup}");
                    };
                </script>
            </c:if>

            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">APPROVE NOVEL</h1>
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
                                                    <th>Novel Name</th>
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
                                                    <tr onclick ="viewDetail(event, ${c.novelID});" style="cursor: pointer">
                                                        <td>${status.index + 1}</td>
                                                        <td><a href="managenovel?action=viewdetail&novelID=${c.novelID}" title="${c.novelName}">${c.novelName}</a></td>
                                                        <td>${c.userName}</td>
                                                        <td>${c.submissionDate}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test = "${c.type == 'update'}">
                                                                    <a href="managenovel?action=viewdetail&novelID=${c.draftID}" title="View Draft">${c.type}</a>
                                                                </c:when>

                                                                <c:when test = "${c.type == 'post'}">
                                                                    ${c.type}
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                        <td>${c.status}</td>

                                                        <td>
                                                            <form action="managenovel" method="post" style="display: inline">
                                                                <input type="hidden" name="action" value="approve">
                                                                <input type="hidden" name="type" value="${c.type}">
                                                                <input type="hidden" name="submissionNID" value="${c.submissionNID}">
                                                                <input type="hidden" name="novelID" value="${c.novelID}">
                                                                <input type="hidden" name="draftID" value="${c.draftID}">
                                                                <button type="submit" class="btn btn-info" onclick="return confirm('Confirm to APPROVE the novel: ${c.novelName} (ID=${c.novelID})')">Approve</button>
                                                            </form>
                                                            <button type="button" id="open" class="btn btn-danger" onclick="openLockModal('${c.novelID}', '${c.novelName}', '${c.type}', '${c.submissionNID}')">Reject</button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>  
                                            </tbody>
                                        </table>

                                        <!-- Modal (Popup) -->
                                        <div id="lockModal" class="modal fade" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <form action="managenovel" method="post" style="display: inline">
                                                        <div class="modal-header" style="border-bottom: none; height: 45px">
                                                            <h4 class="modal-title"  style="float: left; margin-right: 10px; width: 95%;
                                                                margin-bottom: 10px;display: -webkit-box; overflow: hidden;
                                                                /*                                                                white-space: nowrap; text-overflow: ellipsis; */
                                                                word-wrap: break-word; -webkit-box-orient: vertical; -webkit-line-clamp: 2;
                                                                ">Reject the novel: <strong><span id="novelName"></span></strong></h4>

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
                                                            <input type="hidden" name="submissionNID" id="submissionNID">
                                                            <input type="hidden" name="novelID" id="novelID">
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



        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>

        <script src="js/startmin/dataTables/jquery.dataTables.min.js"></script>
        <script src="js/startmin/dataTables/dataTables.bootstrap.min.js"></script>

        <script>
                                                                $(document).ready(function () {
                                                                    $('#dataTables-example').DataTable({
                                                                        responsive: true,
                                                                        "autoWidth": false,
                                                                        language: {
                                                                            info: 'Showing page _PAGE_ of _PAGES_',
                                                                            infoEmpty: '${listnull}',
                                                                            infoFiltered: '(filtered from _MAX_ total submissions)',
                                                                            lengthMenu: 'Display _MENU_ submissions per page',
                                                                            zeroRecords: 'Nothing found - sorry'
                                                                        }
                                                                    });
                                                                });
        </script>

        <script src="js/startmin/raphael.min.js"></script>
        <script src="js/startmin/morris.min.js"></script>
        <script src="js/startmin/morris-data.js"></script>
    </body>
</html>
