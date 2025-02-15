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



    <script src="js/raphael.min.js"></script>
    <script src="js/morris.min.js"></script>
    <script src="js/morris-data.js"></script>

</body>
</html>
