<%-- 
    Document   : DetailStaff
    Created on : Mar 9, 2025, 5:46:20 PM
    Author     : default
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Staff Details</title>
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css">
        <style>
            .panel {
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            .btn-back {
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="../admin/header.jsp" />
            <jsp:include page="../admin/sidebar.jsp" />

            <div id="page-wrapper">
                <div class="container-fluid">
                    <h1 class="page-header">Staff Details</h1>
                    <div class="panel panel-default">
                        <div class="panel-heading">Staff Information</div>
                        <div class="panel-body">
                            <table class="table table-bordered">
                                <tr><th>Staff ID:</th><td>${staff.managerID}</td></tr>
                                <tr><th>Username:</th><td>${staff.username}</td></tr>
                                <tr><th>Full Name:</th><td>${staff.fullName}</td></tr>
                                <tr><th>Email:</th><td>${staff.email}</td></tr>
                                <tr><th>Phone:</th><td>${staff.numberPhone}</td></tr>
                                <tr><th>Gender:</th><td>${staff.gender}</td></tr>
                                <tr><th>Role:</th><td>${staff.role}</td></tr>
                                <tr><th>Creation Date:</th><td>${staff.creationDate}</td></tr>
                                <tr><th>Status:</th><td>${staff.status == 1 ? "Active" : "Inactive"}</td></tr>
                            </table>
                            <button type="button" class="btn btn-default btn-back" 
                                    onclick="window.location.href='<%= request.getContextPath() %>/managestaff'">
                                <i class="fa fa-arrow-left"></i> Back to Staff List
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
