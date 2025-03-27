<%-- 
    Document   : adminSidebar
    Created on : Dec 26, 2024, 3:45:34 PM
    Author     : Nguyen Thanh Trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Sidebar</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
        <style>
            .g_logo {
                line-height: 47px;
                font-size: 2em;
                color: #005ac5;
                transition: .3s;
                z-index: 3;
            }
            .g_logo img {
                margin: 10px 32px;
                width: 60px;
                height: 65px;
            }
        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <aside class="sidebar navbar-default" role="navigation">
            <div class="sidebar-nav navbar-collapse">



                <ul class="nav" id="side-menu">

                    <a class="g_logo" href="dashboard" title="NovelReader">
                        <h1><img src="img/b3.png" alt="NovelReader"></h1>

                        <!--  thu gọn sidebar                          -->
                    </a>



                    <!-- Home -->
                    <li>
                        <a href="${pageContext.request.contextPath}/admindashboard" >
                            <i class="fa fa-home fa-fw"></i> 
                            Dashboard
                        </a>
                    </li>



                    <!-- tui them phan nay Khoa Manage Staff -->    
                    <!-- tui them phan nay Khoa -->    

                    <li>
                        <a href="${pageContext.request.contextPath}/managestaff"><i class="fa fa-sitemap fa-fw"></i>View list of staff</a>
                    </li>
                    <!-- tui sua nut phan nay -->
                    <li>
                        <a href="${pageContext.request.contextPath}/RegisterStaff">
                            <i class="fa fa-user-plus"></i> Create Staff Account
                        </a>
                    </li>

                    <li>
                        <a href="${pageContext.request.contextPath}/viewnovelstatisticscontroller?action=viewNovelStatistics">View Statistics On Novel</a>
                    </li>

                    <li>
                        <a href="${pageContext.request.contextPath}/viewnovelstatisticscontroller?action=viewUserStatistics">View Statistics On Account</a>
                    </li>

                    </li> 
                    <li>
                        <a href="${pageContext.request.contextPath}/change-password">
                            <i class="fa fa-key fa-fw"></i> Change Admin Password
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/AssignPermissionController">
                            <i class="fa fa-key fa-fw"></i> Assign Permission 
                        </a>
                    </li>



                </ul>


            </div>
        </aside>


        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>




    </body>
</html>
