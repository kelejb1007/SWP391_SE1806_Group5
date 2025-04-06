<%-- 
    Document   : viewLockedStaff
    Created on : 06-Apr-2025, 17:59:09
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>View Locked Staff</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css">
        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="../admin/header.jsp" />
            <jsp:include page="../admin/sidebar.jsp" />

            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">VIEW LOCKED STAFF</h1>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            List of Locked Staff Accounts
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped" id="dataTables-locked-staff">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Staff ID</th>
                                            <th>Username</th>
                                            <th>Full Name</th>
                                            <th>Email</th>
                                            <th>Gender</th>
                                            <th>Reason</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${not empty listLockedStaff}">
                                                <c:forEach var="staff" items="${listLockedStaff}" varStatus="status">
                                                    <tr>
                                                        <td>${status.index + 1}</td>
                                                        <td>${staff.managerID}</td>
                                                        <td>${staff.username}</td>
                                                        <td>${staff.fullName}</td>
                                                        <td>${staff.email}</td>
                                                        <td>${staff.gender}</td>
                                                       <td>${staff.lockReason}</td>
                                                        <td><span class="label label-danger">Locked</span></td>
                                                        <td>|
                                                            <c:choose>
                                                                <c:when test="${staff.status == 1 }">
                                                                    <a href="managestaff?action=unlock&managerID=${staff.managerID}" class="btn btn-success">Unlock</a>
                                                                </c:when>

                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td colspan="11" class="text-center">No locked staff accounts found.</td>
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

        <!-- Scripts -->
        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>
        <script src="js/startmin/dataTables/jquery.dataTables.min.js"></script>
        <script src="js/startmin/dataTables/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#dataTables-locked-staff').DataTable({
                    responsive: true
                });
            });
        </script>
    </body>
</html>
