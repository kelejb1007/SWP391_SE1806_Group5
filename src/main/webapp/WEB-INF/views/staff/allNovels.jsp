<%-- 
    Document   : viewAllNovel
    Created on : Feb 15, 2025, 3:28:07 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Novel</title>


        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css">

        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet">

        <script>
            function openLockModal(novelID, novelName) {
                document.getElementById('novelID').value = novelID;
                document.getElementById('lockReason').value = '';
                document.getElementById('novelName').innerHTML = novelName + " (ID=" + novelID + ")";
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
                            <h1 class="page-header">MANAGE NOVEL</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    List of novels
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Title</th>
                                                    <th>Author</th>
                                                    <th>Total of chapter</th>
                                                    <th>Published Date</th>
                                                    <th>Rating Average</th>
                                                    <th>View</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="c" items="${requestScope.listNovel}" varStatus="status">
                                                    <tr>
                                                        <td>${status.index + 1}</td>
                                                        <td>${c.novelName}</td>
                                                        <td>${c.author}</td>
                                                        <td>${c.totalChapter}</td>
                                                        <td>${c.publishedDate}</td>
                                                        <td>${c.averageRating}</td>
                                                        <td>${c.viewCount}</td>
                                                        <td>
                                                            <button type="button" class="btn btn-info"
                                                                    onclick="window.location.href = 'managenovel?action=viewdetail&novelID=${c.novelID}';">View detail
                                                            </button>
                                                            <button type="button" id="open" class="btn btn-danger" onclick="openLockModal('${c.novelID}', '${c.novelName}')">Lock</button>
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
                                                                ">You are locking the novel: <strong><span id="novelName"></span></strong></h4>
                                                                
                                                            <button type="button" class="close" style="float: right" data-dismiss="modal">&times;</button>
                                                        </div>

                                                        <div class="modal-body">
                                                            <input type="hidden" name="novelID" id="novelID">
                                                            <textarea id="lockReason" name="lockReason" class="form-control" placeholder="Enter lock reason" rows="3"></textarea>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                            <!--<button type="button" class="btn btn-danger" onclick="submitLock()">Confirm</button>-->

                                                            <input type="hidden" name="action" value="lock">
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
                                                                        responsive: true
                                                                    });
                                                                });
        </script>

        <script src="js/startmin/raphael.min.js"></script>
        <script src="js/startmin/morris.min.js"></script>
        <script src="js/startmin/morris-data.js"></script>
    </body>
</html>
