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
<!--        <style>
    /* Tổng quan */
.sidebar {
    background: linear-gradient(to bottom, #ff3955, #1a1a1a); /* Chuyển màu đỏ - đen */
    color: white;
    width: 260px; /* Điều chỉnh độ rộng nếu cần */
    height: 100%;
    overflow-y: auto; /* Cho phép cuộn nếu nội dung quá dài */
    box-shadow: 5px 0 20px rgba(0, 0, 0, 0.4); /* Đổ bóng */
    font-family: 'Roboto', sans-serif; /* Font chữ dễ đọc */
}

.sidebar-nav {
    padding: 0;
}

.nav.fix {
    margin-bottom: 0;
}

.nav.fix li {
    width: 100%;
}

/* Tìm kiếm */
.sidebar-search {
    padding: 15px;
}

.custom-search-form .form-control {
    background-color: rgba(0, 0, 0, 0.3); /* Đen trong suốt */
    border: none;
    color: white;
    border-radius: 4px; /* Góc bo tròn nhẹ */
    padding: 8px 12px;
    font-size: 14px;
}

.custom-search-form .form-control:focus {
    border-color: #fff; /* Viền trắng khi focus */
    box-shadow: none;
    outline: none;
    background-color: rgba(0, 0, 0, 0.5); /* Đen trong suốt hơn */
}

.custom-search-form .btn-primary {
    background-color: transparent;
    border: none;
    padding: 0;
    margin-left: 8px;
    color: white; /* Màu trắng cho biểu tượng tìm kiếm */
}

.custom-search-form .btn-primary:hover {
    color: #ddd; /* Trắng nhạt hơn khi hover */
}

/* Các mục menu */


.nav.fix li a:hover {
    background-color: rgba(0, 0, 0, 0.3); /* Đen trong suốt */
    color: #ddd; /* Chữ màu xám nhạt khi hover */
    text-decoration: none;
    border-left-color: #fff;
}

.nav.fix li a {
    color: black;
    padding: 14px 20px;
    display: block;
    transition: background-color 0.3s ease; /* Thêm transition */
    position: relative;
    overflow: hidden;

}

/* Menu cấp hai */
.nav-second-level {
    background-color: rgba(0, 0, 0, 0.2); /* Đen trong suốt */
    padding-left: 10px;
    
}

.nav-second-level li a {
    border-bottom-left-radius: 2px;
    padding: 12px 20px;
    font-size: 14px;
}

.nav-second-level li a:hover {
    border-left-radius: 2px;
    background-color: rgba(0, 0, 0, 0.3); /* Thêm màu nền đen nhạt khi hover */
}

.nav.fix li.active > a {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); /* Đổ bóng nhẹ */
    background-color: rgba(255, 0, 0, 0.3);
    color: white;
}

/* Loại bỏ gạch chân ở liên kết */
a {
    text-decoration: none;
}


        </style>-->
    </head>
    <body>
        <!-- Sidebar -->
        <aside class="sidebar navbar-default" role="navigation" style="position: fixed">
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
