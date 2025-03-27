<%-- 
    Document   : ViewAllStaff
    Created on : Feb 27, 2025, 7:09:49 AM
    Author     : default
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Staff</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut") %>">
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
                    <h1 class="page-header">Manage Staff</h1>

                    <form action="managestaff" method="get" class="form-inline">
                        <input type="hidden" name="action" value="search">
                        <input type="text" name="keyword" class="form-control" placeholder="Search staff..." value="${keyword}">
                        <button type="submit" class="btn btn-primary">Search</button>
                        <a href="managestaff" class="btn btn-default">Reset</a>
                    </form>

                    <div class="panel panel-default">
                        <div class="panel-heading">List of Staff Accounts</div>
                        <div class="panel-body">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Staff ID</th>
                                        <th>Username</th>
                                        <th>Full Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Gender</th>
                                        <th>Role</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="staff" items="${listStaff}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td>${staff.managerID}</td>
                                            <td>${staff.username}</td>
                                            <td>${staff.fullName}</td>
                                            <td>${staff.email}</td>
                                            <td>${staff.numberPhone}</td>
                                            <td>${staff.gender}</td>
                                            <td>${staff.role}</td>
                                            <td>
                                                <button type="button" class="btn btn-info" 
                                                        onclick="window.location.href = '${pageContext.request.contextPath}/viewStaff?managerID=${staff.managerID}';">
                                                    View Detail
                                                </button>
                                                    <!-- tui sua nut phan nay -->
                                                <button type="button" class="btn btn-warning" 
                                                        onclick="window.location.href = '${pageContext.request.contextPath}/EditStaff?managerID=${staff.managerID}';">
                                                    <a href="../../../../java/DAO/ManagerAccountDAO.java"></a>
                                                    Edit
                                                </button>
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
    </body>
</html>
