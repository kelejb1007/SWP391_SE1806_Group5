<%-- 
    Document   : viewLockedNovels
    Created on : Feb 15, 2025, 11:28:07 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
            function openLockModal(novelID) {
                document.getElementById('novelID').value = novelID;
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
                            <h1 class="page-header">MANAGE NOVEL</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    List of locked novels
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-lock">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Title</th>
                                                    <th>Author</th>
                                                    <th>Total of chapter</th>
                                                    <th>Published Date</th>
                                                    <th>Locked By</th>
                                                    <th>Lock Reason</th>
                                                    <th>Locked Date</th>
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
                                                        <td>${c.staffName}</td>
                                                        <td>${c.lockReason}</td>
                                                        <td>${c.datetime}</td>
                                                        <td>
                                                            <button type="button" class="btn btn-info"
                                                                    onclick="window.location.href = 'managenovel?action=viewdetail&novelID=${c.novelID}';">View detail
                                                            </button>
                                                            <form action="managenovel" method="post" style="display: inline">
                                                                <input type="hidden" name="action" value="unlock">
                                                                <input type="hidden" name="novelID" value="${c.novelID}">
                                                                <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure to unclock the novel: ${c.novelName} (ID=${c.novelID})')">Unlock</button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </c:forEach>  
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
        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>

        <script src="js/startmin/dataTables/jquery.dataTables.min.js"></script>
        <script src="js/startmin/dataTables/dataTables.bootstrap.min.js"></script>


        <script>
                                                                    $(document).ready(function () {
                                                                        $('#dataTables-lock').DataTable({
                                                                            responsive: true,
                                                                            "autoWidth": false,
                                                                            language: {
                                                                                info: 'Showing page _PAGE_ of _PAGES_',
                                                                                infoEmpty: '${listnull}',
                                                                                infoFiltered: '(filtered from _MAX_ total novels)',
                                                                                lengthMenu: 'Display _MENU_ novels per page',
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
