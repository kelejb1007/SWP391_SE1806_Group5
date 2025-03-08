<%-- 
    Document   : viewLockHistory
    Created on : 04-Mar-2025, 21:22:28
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Lock History</title>

        <!-- Bootstrap Core CSS -->
        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css">
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css">
        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet">

        <script>
            function openLockModal(userID, userName) {
                document.getElementById('userID').value = userID;
                document.getElementById('lockReason').value = ''; // Reset textarea mỗi lần mở modal
                document.getElementById('userName').innerText = userName + " (ID=" + userID + ")";
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
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">MANAGE LOCK HISTORY</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    List of Lock History
                                </div>
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-lock">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>User ID</th>
                                                    <th>User Name</th>
                                                    <th>Creation Date</th>
                                                    <th>Full Name</th>
                                                    <th>Lock Reason</th>
                                                    <th>Locked Date</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="acc" items="${lockHistory}" varStatus="status">
                                                    <tr>
                                                        <td>${status.index + 1}</td>
                                                         <td>${acc.userID}</td>
                                                        <td>${acc.userName}</td>
                                                        <td><fmt:formatDate value="${acc.creationDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                                        <td>${acc.fullName}</td>
                                                        <td>${acc.reason}</td>
                                                        <td><fmt:formatDate value="${acc.lockDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty acc.unlockDate}">
                                                                    <fmt:formatDate value="${acc.unlockDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Still Locked
                                                                </c:otherwise>
                                                            </c:choose>
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
                    responsive: true
                });
            });
        </script>
    </body>
</html>
