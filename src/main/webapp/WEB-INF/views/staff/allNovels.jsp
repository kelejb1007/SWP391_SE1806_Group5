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

        <!--         Bootstrap Core CSS -->
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css">

        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet">

        <script type="text/javascript">
            function lock(novelID, novelName) {
                if (confirm("Are you sure to lock the novel : '" + novelName + "'")) {
                    window.location.href = "managenovel?action=lock&novelID=" + novelID;
                }
            }
            //onclick="lock(${c.novelID}, '${c.novelName}')"
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
                                                            <form action="managenovel" method="post" style="display: inline">
                                                                <input type="hidden" name="action" value="lock">
                                                                <input type="hidden" name="novelID" value="${c.novelID}">
                                                                <button type="submit" class="btn btn-danger">Lock</button>
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
    </div>
    <!-- jQuery -->
    <!-- Bootstrap Core JavaScript -->
    <!-- Metis Menu Plugin JavaScript -->
    <!-- Custom Theme JavaScript -->
    <script src="js/startmin/jquery.min.js"></script>
    <script src="js/startmin/bootstrap.min.js"></script>
    <script src="js/startmin/metisMenu.min.js"></script>
    <script src="js/startmin/startmin.js"></script>

    <!-- DataTables JavaScript -->
    <script src="js/startmin/dataTables/jquery.dataTables.min.js"></script>
    <script src="js/startmin/dataTables/dataTables.bootstrap.min.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
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
