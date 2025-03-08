<%-- 
    Document   : viewAllAccounts
    Created on : 23-Feb-2025, 21:09:29
    Author     : LienXuanThinh_ce182117
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Accounts</title>

        <!-- Bootstrap Core CSS -->
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css">

        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet">

        <script type="text/javascript">
            // Ví dụ: chức năng xử lý sự kiện (nếu cần)
            function viewDetail(managerID) {
                window.location.href = "manageaccount?action=viewdetail&managerID=" + managerID;
            }
        </script>

        <script>
            function showLockModal(userID) {
                $("#lockUserID").val(userID);
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
                    <!-- Page Heading -->
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">MANAGE ACCOUNTS</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->

                    <!-- Form Tìm Kiếm -->
                    <div class="row" style="margin-bottom: 20px;">
                        <div class="col-lg-12">
                            <form action="manageaccount" method="get" class="form-inline">
                                <input type="hidden" name="action" value="search">
                                <div class="form-group">
                                    <input type="text" name="keyword" class="form-control" 
                                           placeholder="Search by username, email, or full name"
                                           value="${keyword != null ? keyword : ''}">
                                </div>
                                <button type="submit" class="btn btn-primary">Search</button>
                                <a href="manageaccount" class="btn btn-default">Reset</a>
                            </form>
                        </div>
                    </div>

                    <!-- Bảng Hiển Thị Danh Sách Tài Khoản -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    List of Manager Accounts
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>User ID</th>
                                                    <th>User Name</th>
                                                    <th>Creation Date</th>
                                                    <th>Full Name</th>
                                                    <th>Email</th>
                                                    <th>Number Phone</th>                                                  
                                                    <th>Date Of Birth</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${not empty listAccount}">
                                                        <c:forEach var="acc" items="${listAccount}" varStatus="status">
                                                            <tr>
                                                                <td>${status.index + 1}</td>
                                                                <td>${acc.userID}</td>
                                                                <td>${acc.userName}</td>
                                                                <td>
                                                                    <fmt:formatDate value="${acc.creationDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                                                                </td>
                                                                <td>${acc.fullName}</td>
                                                                <td>${acc.email}</td>
                                                                <td>${acc.numberPhone}</td>

                                                                <td> <fmt:formatDate value="${acc.dateOfBirth}" pattern="yyyy-MM-dd HH:mm:ss" /></td>




                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${acc.status == 1}">
                                                                            <button type="button" class="btn btn-danger" onclick="showLockModal(${acc.userID})">Lock</button>
                                                                        </c:when>
                                                                    </c:choose>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td colspan="12" class="text-center">No accounts found.</td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- /.table-responsive -->
                                </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.container-fluid -->
            </div>
            <!-- /#page-wrapper -->
        </div>
        <!-- /#wrapper -->
        <!-- Modal nhập lý do khóa -->
        <div id="lockModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Lock Account</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <label>Reason for Lock:</label>
                        <textarea class="form-control"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger">Confirm Lock</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
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
                                                                                        responsive: true
                                                                                    });
                                                                                });
        </script>


    </body>
</html>