<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Locking Activity List</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <!-- Bootstrap Core CSS -->
        <link rel="stylesheet" href="css/startmin/bootstrap.min.css" />
        <link rel="stylesheet" href="css/startmin/startmin.css" />
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" />
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css" />
        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet" />
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="header.jsp" />
            <jsp:include page="sidebar.jsp" />

            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">LOCKING ACTIVITY MANAGEMENT</h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">Locking Activity List</div>
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>ID</th>
                                                    <th>Novel</th>
                                                    <th>Manager</th>
                                                    <th>Action</th>
                                                    <th>Time</th>
                                                    <th>Reason</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${not empty lockingActivities}">
                                                        <c:forEach var="activity" items="${lockingActivities}" varStatus="status">
                                                            <tr>
                                                                <td>${status.index + 1}</td>
                                                                <td>${activity.logNID}</td>
                                                                <td>${activity.novelName}</td>
                                                                <td>${activity.managerName}</td>
                                                                <td>${activity.action}</td>
                                                                <td>
                                                                    <c:set var="datetime" value="${activity.datetime}" />
                                                                    ${fn:substring(datetime, 8, 10)}/${fn:substring(datetime, 5, 7)}/${fn:substring(datetime, 0, 4)}
                                                                    ${fn:substring(datetime, 11, 19)}
                                                                </td>

                                                                <td>${activity.lockReason}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td colspan="7" style="text-align: center;">No locking activity available.</td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>
                                        </table>

                                        <c:if test="${sessionScope.role == 'Admin'}">
                                            <a href="addLockingActivity.jsp" class="btn btn-primary">Add New Locking Activity</a>
                                        </c:if>
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
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>
        <script src="js/startmin/dataTables/jquery.dataTables.min.js"></script>
        <script src="js/startmin/dataTables/dataTables.bootstrap.min.js"></script>

        <script>
            $(document).ready(function () {
                $('#dataTables-example').DataTable({
                    responsive: true,
                    "pageLength": 10,
                    "order": [[1, "desc"]],
                    "language": {
                        "emptyTable": "No data available",
                        "info": "Showing _START_ to _END_ of _TOTAL_ entries",
                        "infoEmpty": "Showing 0 to 0 of 0 entries",
                        "infoFiltered": "(filtered from _MAX_ total entries)"
                    }
                });
            });
        </script>
    </body>
</html>
