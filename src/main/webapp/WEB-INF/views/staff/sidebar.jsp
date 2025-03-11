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

        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <style>
            ul.nav.fix li{
                width: 100%;
            }
        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <aside class="sidebar navbar-default" role="navigation">
            <div class="sidebar-nav navbar-collapse">



                <ul class="nav fix" id="side-menu">


                    <!--Search-->
                    <li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input type="text" class="form-control" placeholder="Search...">

                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>

                        </div>
                    </li>



                    <!-- Home -->
                    <li>
                        <a href="${pageContext.request.contextPath}/dashboard" >
                            <i class="fa fa-home fa-fw"></i> 
                            Dashboard
                        </a>
                    </li>



                    <!-- Manage novel -->
                    <li>
                        <a href="#">
                            <i class="fa fa-sitemap fa-fw"></i>
                            Manage novel
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="${pageContext.request.contextPath}/managenovel">View all novels</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/managenovel?action=viewlockedlist">View locked novels</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/managenovel?action=viewsubmission">Approve novel</a>
                            </li>
                        </ul>
                    </li>



                    <!-- Manage chapter -->
                    <li>
                        <a href="#">
                            <i class="fa fa-sitemap fa-fw"></i>
                            Manage chapter
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="${pageContext.request.contextPath}/managechapter">View all chapters of a novel</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/managechapter?action=viewlocked">View locked chapters</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/managechapter?action=allnovelforlockedchapter">View locked chapters of a novel</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/managechapter?action=viewsubmisson">Approve chapter</a>
                            </li>
                        </ul>
                    </li>


                    <!-- Manage comment -->
                    <li>
                        <a href="${pageContext.request.contextPath}/staff/comments">
                            <i class="fa fa-sitemap fa-fw"></i>
                            Manage comment
                        </a>
                    </li>


                    <li>
                        <a href="#">
                            <i class="fa fa-sitemap fa-fw"></i>
                            Manage Account
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="${pageContext.request.contextPath}/manageaccount">View all accounts</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/manageaccount?action=viewLocked">View locked accounts</a>
                            </li>
                        </ul>

                    </li>


                    <!-- tui them phan nay LIENXUANTHINH -->      

                    <li>
                        <a href="#">
                            <i class="fa fa-sitemap fa-fw"></i>
                            Manage Genre
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="${pageContext.request.contextPath}/managegenre">View list of genres</a>
                            </li>
                        </ul>
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
