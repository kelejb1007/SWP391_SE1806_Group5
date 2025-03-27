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
            // Function to toggle the visibility of the reason input field
            function toggleLockReason(userID) {
                var reasonInput = document.getElementById('lockReasonInput' + userID);
                var lockButton = document.getElementById('lockButton' + userID);

                // Toggle visibility of the reason input and button
                if (reasonInput.style.display === 'none' || reasonInput.style.display === '') {
                    reasonInput.style.display = 'block';
                    lockButton.textContent = 'Submit Lock';
                } else {
                    reasonInput.style.display = 'none';
                    lockButton.textContent = 'Lock';
                }
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
                    </div>

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
                                                                <td><fmt:formatDate value="${acc.dateOfBirth}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${acc.status == 0}">
                                                                            <!-- Lock button that toggles the reason input -->
                                                                            <a href="javascript:void(0);" id="lockButton${acc.userID}" 
                                                                               onclick="toggleLockReason(${acc.userID})" 
                                                                               class="btn btn-danger">Lock</a>

                                                                            <!-- Reason input field -->
                                                                            <div id="lockReasonInput${acc.userID}" style="display: none;">
                                                                                <form action="manageaccount?action=lock" method="post">
                                                                                    <input type="hidden" name="userID" value="${acc.userID}">
                                                                                    <div class="form-group">
                                                                                        <textarea name="lockReason" class="form-control" placeholder="Enter the reason for locking" required></textarea>
                                                                                    </div>
                                                                                    <button type="submit" class="btn btn-warning">Submit</button>
                                                                                </form>

                                                                            </div>
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
                                                                                           responsive: true
                                                                                       });
                                                                                   });
        </script>
    </body>
</html>
