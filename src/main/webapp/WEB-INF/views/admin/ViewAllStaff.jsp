<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Staff</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css">
        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

        <script type="text/javascript">
            // Hiển thị / Ẩn ô nhập lý do khóa
            function toggleLockReason(managerID) {
                var reasonInput = document.getElementById('lockReasonInput' + managerID);
                var lockButton = document.getElementById('lockButton' + managerID);

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
                                    <!-- Duyệt qua danh sách staff và loại bỏ tài khoản bị khóa -->
                                    <c:forEach var="staff" items="${listStaff}" varStatus="status">
                                        <c:if test="${staff.status != 1}"> <!-- Chỉ hiển thị các tài khoản chưa bị khóa -->
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
                                                    <button type="button" class="btn btn-warning" 
                                                            onclick="window.location.href = '${pageContext.request.contextPath}/EditStaff?managerID=${staff.managerID}';">
                                                        Edit
                                                    </button>
                                                    <c:choose>
                                                        <c:when test="${staff.status == 0}">
                                                            <!-- Lock button that toggles the reason input -->
                                                            <a href="javascript:void(0);" id="lockButton${staff.managerID}" 
                                                               onclick="toggleLockReason(${staff.managerID})" 
                                                               class="btn btn-danger">Lock</a>

                                                            <!-- Reason input field -->
                                                            <div id="lockReasonInput${staff.managerID}" style="display: none;">
                                                                <form action="managestaff?action=lock" method="post">
                                                                    <input type="hidden" name="managerID" value="${staff.managerID}">
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
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
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
