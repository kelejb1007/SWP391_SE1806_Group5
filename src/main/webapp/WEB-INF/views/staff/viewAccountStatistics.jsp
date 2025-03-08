<%-- 
    Document   : viewAccountStatistics
    Created on : Mar 08, 2025, 4:47:10 PM
    Author     : Admin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Statistics</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
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
                            <h1 class="page-header">Account Statistics</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Account Statistics
                                </div>
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Year</th>
                                                    <th>Month</th>
                                                    <th>Day</th>
                                                    <th>Total Accounts</th>
                                                    <th>Active Accounts</th>
                                                    <th>Accounts This Month</th>
                                                    <th>Accounts Last Month</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="account" items="${accountStatistics}">
                                                    <tr>
                                                        <td>${account.year}</td>
                                                        <td>${account.month}</td>
                                                        <td>${account.day}</td>
                                                        <td>${account.totalAccounts}</td>
                                                        <td>${account.totalActiveAccounts}</td>
                                                        <td>${account.totalAccountsThisMonth}</td>
                                                        <td>${account.totalAccountsLastMonth}</td>
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
                $('.table').DataTable({
                    responsive: true
                });
            });
        </script>
    </body>
</html>