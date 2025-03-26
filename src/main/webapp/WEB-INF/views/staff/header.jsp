<%-- 
    Document   : adminHeader
    Created on : Dec 26, 2024, 3:37:24 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Header</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="icon" type="image/png" href="img/logo.jpg">
        <style>
            .navbar.navbar-inverse.navbar-fixed-top{
                justify-content: initial;
                display: block;
                white-space: nowrap;
                align-items: initial;
            }
            .dropdown-toggle::after{
                content: none;
            }
            button:disabled{
                cursor: not-allowed;          
            }

        </style>
    </head>
    <body>
        <!-- Top Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">NovelReader</a>
            </div>
            <ul class="nav navbar-nav navbar-left navbar-top-links">
                <li><a href="${pageContext.request.contextPath}/dashboard"><i class="fa fa-home fa-fw"></i> Dashboard</a></li>
            </ul>


            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            
            <!-- Right Menu -->
            <ul class="nav navbar-right navbar-top-links">
                <li class="dropdown navbar-inverse" style="color: white">
                    <input type="checkbox" name="lockCheckbox"
                           <c:if test="${sessionScope.manager.canLock}">checked</c:if>  disabled>
                    <label for="lockCheckbox">Can lock</label>
                    
                    <input type="checkbox" name="lockCheckbox"
                           <c:if test="${sessionScope.manager.canApprove}">checked</c:if>  disabled>
                    <label for="lockCheckbox">Can approve</label>
                </li>




                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> Staff <b class="caret"></b>
                    </a>

                    <ul class="dropdown-menu dropdown-user">
                        <li class="divider"></li>
                        <li>
                            <a href="${pageContext.request.contextPath}/ManagerLogout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </nav>

        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>

    </body>
</html>
