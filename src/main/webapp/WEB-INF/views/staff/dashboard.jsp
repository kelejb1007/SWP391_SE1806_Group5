<%-- 
    Document   : adminMain
    Created on : Dec 23, 2024, 10:26:44 PM
    Author     : Nguyen Thanh Trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Staff Dashboard</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <!--         Bootstrap Core CSS -->
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/startmin/metisMenu.min.css">
        <link rel="stylesheet" href="css/startmin/morris.css">
        <link rel="stylesheet" href="css/startmin/timeline.css">

        <link rel="icon" type="image/png" href="img/logo.jpg">
    </head>
    <body>

        <div id="wrapper">

            <jsp:include page="header.jsp" />
            <jsp:include page="sidebar.jsp" />


            <!-- Page Content -->
            <div id="page-wrapper">
                <div class="container-fluid">

                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">Statistics</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-user fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge">26</div>
                                            <div>Accounts Registrations!</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <span class="pull-left">View Details</span>
                                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>

                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-green">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-tasks fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge">12</div>
                                            <div>Novels</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <span class="pull-left">View Details</span>
                                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-yellow">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-book fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge">124</div>
                                            <div>Chapters</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <span class="pull-left">View Details</span>
                                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-red">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-support fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge">13</div>
                                            <div>Top Favorite!</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <span class="pull-left">View Details</span>
                                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>



                    <div class="row">
                        <div class="col-lg-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Multiple Axes Line Chart Example
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <div class="flot-chart">
                                        <div class="flot-chart-content" id="flot-line-chart-multi" style="padding: 0px; position: relative;">
                                            <canvas class="flot-base" width="925" height="500" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 740px; height: 400px;"></canvas>
                                            <div class="flot-text" style="position: absolute; inset: 0px; font-size: smaller; color: rgb(84, 84, 84);"><div class="flot-x-axis flot-x1-axis xAxis x1Axis" style="position: absolute; inset: 0px; display: block;">
                                                    <div class="flot-tick-label tickLabel" style="position: absolute; max-width: 61px; top: 381px; left: 2px; text-align: center;">
                                                        Jan 2007
                                                    </div>
                                                    <div class="flot-tick-label tickLabel" style="position: absolute; max-width: 61px; top: 381px; left: 100px; text-align: center;">Apr 2007</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 61px; top: 381px; left: 199px; text-align: center;">Jul 2007</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 61px; top: 381px; left: 297px; text-align: center;">Oct 2007</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 61px; top: 381px; left: 396px; text-align: center;">Jan 2008</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 61px; top: 381px; left: 495px; text-align: center;">Apr 2008</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 61px; top: 381px; left: 595px; text-align: center;">Jul 2008</div></div><div class="flot-y-axis flot-y1-axis yAxis y1Axis" style="position: absolute; inset: 0px; display: block;"><div class="flot-tick-label tickLabel" style="position: absolute; top: 368px; left: 15px; text-align: right;">0</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 306px; left: 8px; text-align: right;">25</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 245px; left: 8px; text-align: right;">50</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 184px; left: 8px; text-align: right;">75</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 123px; left: 2px; text-align: right;">100</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 62px; left: 2px; text-align: right;">125</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 1px; left: 2px; text-align: right;">150</div></div><div class="flot-y-axis flot-y2-axis yAxis y2Axis" style="position: absolute; inset: 0px; display: block;"><div class="flot-tick-label tickLabel" style="position: absolute; top: 368px; left: 702px;">0.624€</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 306px; left: 702px;">0.650€</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 245px; left: 702px;">0.675€</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 184px; left: 702px;">0.701€</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 123px; left: 702px;">0.727€</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 62px; left: 702px;">0.752€</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 1px; left: 702px;">0.778€</div></div></div><canvas class="flot-overlay" width="925" height="500" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 740px; height: 400px;"></canvas><div class="legend"><div style="position: absolute; width: 143px; height: 33px; bottom: 29px; left: 31px; background-color: rgb(255, 255, 255); opacity: 0.85;"> </div><table style="position:absolute;bottom:29px;left:31px;;font-size:smaller;color:#545454"><tbody><tr><td class="legendColorBox"><div style="border:1px solid #ccc;padding:1px"><div style="width:4px;height:0;border:5px solid rgb(237,194,64);overflow:hidden"></div></div></td><td class="legendLabel">Oil price ($)</td></tr><tr><td class="legendColorBox"><div style="border:1px solid #ccc;padding:1px"><div style="width:4px;height:0;border:5px solid rgb(175,216,248);overflow:hidden"></div></div></td><td class="legendLabel">USD/EUR exchange rate</td></tr></tbody></table></div></div>
                                    </div>
                                </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                    </div>



                </div>
            </div>
        </div>


        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>
        <script src="js/startmin/raphael.min.js"></script>
        <script src="js/startmin/morris.min.js"></script>
        <script src="js/startmin/morris-data.js"></script>

        <script src="js/startmin/flot/excanvas.min.js"></script>
        <script src="js/startmin/flot/jquery.flot.js"></script>
        <script src="js/startmin/flot/jquery.flot.pie.js"></script>
        <script src="js/startmin/flot/jquery.flot.resize.js"></script>
        <script src="js/startmin/flot/jquery.flot.time.js"></script>
        <script src="js/startmin/flot/jquery.flot.tooltip.min.js"></script>
        <script src="js/startmin/flot-data.js"></script>

        <div id="flotTip" style="display: none; position: absolute; background: rgb(255, 255, 255); z-index: 1040; padding: 0.4em 0.6em; border-radius: 0.5em; font-size: 0.8em; border: 1px solid rgb(17, 17, 17); white-space: nowrap; left: 807px; top: 1316px;"></div>
    </body>
</html>
