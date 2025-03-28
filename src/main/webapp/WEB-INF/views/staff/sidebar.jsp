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
            .g_sd .g_logo {
                
            }

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
        <aside class="sidebar navbar-default" role="navigation" style="position: fixed">
            <div class="sidebar-nav navbar-collapse" >


                <a class="g_logo" href="dashboard" title="NovelReader">
                    <h1><img src="img/b3.png" alt="NovelReader"></h1>

                    <!--  thu gọn sidebar                          -->
                </a>
                <ul class="nav fix" id="side-menu">
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
                            <i class="fa fa-book fa-fw"></i>
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
                            <i class="fa fa-file-text fa-fw"></i>
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
                            <i class="fa fa-comment fa-fw"></i>
                            Manage comment
                        </a>
                    </li>


                    <li>
                        <a href="#">
                            <i class="fa fa-users fa-fw"></i>
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
                            <li>
                                <a href="${pageContext.request.contextPath}/manageaccount?action=viewLockingHistory">View Locking History</a>
                            </li>
                        </ul>

                    </li>






                    <!-- tui them phan nay LIENXUANTHINH -->      

                    <li>
                        <a href="${pageContext.request.contextPath}/managegenre">
                            <i class="fa fa-sitemap fa-fw"></i>
                            Manage Genre
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
