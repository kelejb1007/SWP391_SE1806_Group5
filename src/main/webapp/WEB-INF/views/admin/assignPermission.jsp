<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Assign Permissions</title>
        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css">
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
                            <h1 class="page-header">MANAGE PERMISSIONS</h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    List of Accounts
                                </div>
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Username</th>
                                                    <th>Email</th>
                                                    <th>Role</th>
                                                    <th>Lock Permission</th>
                                                    <th>Approve Permission</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%@ page import="java.util.List, model.ManagerAccount" %>
                                                <% List<ManagerAccount> accounts = (List<ManagerAccount>) request.getAttribute("accounts");
                                                    if (accounts != null) {
                                                        for (ManagerAccount acc : accounts) { %>
                                                            <tr>
                                                                <td><%= acc.getManagerID() %></td>
                                                                <td><%= acc.getUsername() %></td>
                                                                <td><%= acc.getEmail() %></td>
                                                                <td>
                                                                    <select name="role" form="updateForm_<%= acc.getManagerID() %>">
                                                                        <option value="Admin" <%= "Admin".equals(acc.getRole()) ? "selected" : "" %>>Admin</option>
                                                                        <option value="Staff" <%= "Staff".equals(acc.getRole()) ? "selected" : "" %>>Staff</option>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <input type="checkbox" name="canLock" form="updateForm_<%= acc.getManagerID() %>"
                                                                           <%= acc.isCanLock() ? "checked" : "" %>>
                                                                </td>
                                                                <td>
                                                                    <input type="checkbox" name="canApprove" form="updateForm_<%= acc.getManagerID() %>"
                                                                           <%= acc.isCanApprove() ? "checked" : "" %>>
                                                                </td>
                                                                <td>
                                                                    <form id="updateForm_<%= acc.getManagerID() %>" action="AssignPermissionController" method="post">
                                                                        <input type="hidden" name="managerID" value="<%= acc.getManagerID() %>">
                                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                                    </form>
                                                                </td>
                                                            </tr>
                                                        <% }
                                                    } else { %>
                                                    <tr>
                                                        <td colspan="7" class="text-center">No accounts found.</td>
                                                    </tr>
                                                <% } %>
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
                $('#dataTables-example').DataTable({
                    responsive: true
                });
            });
        </script>
    </body>
</html>