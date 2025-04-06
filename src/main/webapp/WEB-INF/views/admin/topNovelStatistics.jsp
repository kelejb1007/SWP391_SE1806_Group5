<%-- 
    Document   : topNovelStatistics
    Created on : 06-Apr-2025, 11:54:14
    Author     : Admin
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Novel" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Top Novel Statistics</title>
        <% String baseUrl = request.getContextPath();%>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css">
        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="<%= baseUrl%>/css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="<%= baseUrl%>/css/startmin/metisMenu.min.css">
        <link rel="stylesheet" href="<%= baseUrl%>/css/startmin/startmin.css">
        <link rel="stylesheet" href="<%= baseUrl%>/css/startmin/font-awesome.min.css">

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.3/xlsx.full.min.js"></script> <!-- Added xlsx.js library -->

        <style>
            .panel-body canvas {
                max-width: 100%;
                height: auto !important;
            }
        </style>
    </head>
    <body>

        <%
            List<Novel> topCurrent = (List<Novel>) request.getAttribute("topCurrent");
            List<Novel> topLast = (List<Novel>) request.getAttribute("topLast");
        %>

        <div id="wrapper">
            <!-- Header -->
            <jsp:include page="header.jsp" />

            <!-- Sidebar -->
            <jsp:include page="sidebar.jsp" />

            <!-- Page Content -->
            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">TOP NOVEL STATISTICS</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Compare Top 7 Novels of This Month and Last Month (By Views)
                                </div>
                                <div class="panel-body">
                                    <canvas id="novelChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Export to Excel button -->
                    <button id="exportBtn" class="btn btn-primary">Export to Excel</button>

                </div>
            </div>
        </div>

        <script src="<%= baseUrl%>/js/startmin/jquery.min.js"></script>
        <script src="<%= baseUrl%>/js/startmin/bootstrap.min.js"></script>
        <script src="<%= baseUrl%>/js/startmin/metisMenu.min.js"></script>
        <script src="<%= baseUrl%>/js/startmin/startmin.js"></script>

        <script>
            $(document).ready(function () {
                const topCurrentData = <%= (topCurrent != null) ? new com.google.gson.Gson().toJson(topCurrent) : "[]"%>;
                const topLastData = <%= (topLast != null) ? new com.google.gson.Gson().toJson(topLast) : "[]"%>;

                const allNovelsMap = new Map();

                topCurrentData.forEach(novel => {
                    if (!allNovelsMap.has(novel.novelID)) {
                        allNovelsMap.set(novel.novelID, {name: novel.novelName, currentViews: 0, lastViews: 0});
                    }
                    allNovelsMap.get(novel.novelID).currentViews = novel.viewCount;
                });

                topLastData.forEach(novel => {
                    if (!allNovelsMap.has(novel.novelID)) {
                        allNovelsMap.set(novel.novelID, {name: novel.novelName, currentViews: 0, lastViews: 0});
                    }
                    allNovelsMap.get(novel.novelID).lastViews = novel.viewCount;
                });

                const chartLabels = [];
                const currentMonthViews = [];
                const lastMonthViews = [];

                const sortedNovels = Array.from(allNovelsMap.values()).sort((a, b) => b.currentViews - a.currentViews);
                const topSortedNovels = sortedNovels.slice(0, 7);

                topSortedNovels.forEach(novelData => {
                    chartLabels.push(novelData.name.replace(/"/g, '\\"'));
                    currentMonthViews.push(novelData.currentViews);
                    lastMonthViews.push(novelData.lastViews);
                });

                const ctx = document.getElementById('novelChart').getContext('2d');
                if (ctx) {
                    const novelChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: chartLabels,
                            datasets: [{
                                    label: 'Views This Month',
                                    data: currentMonthViews,
                                    backgroundColor: 'rgba(54, 162, 235, 0.7)',
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 1
                                }, {
                                    label: 'Views Last Month',
                                    data: lastMonthViews,
                                    backgroundColor: 'rgba(255, 99, 132, 0.7)',
                                    borderColor: 'rgba(255, 99, 132, 1)',
                                    borderWidth: 1
                                }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: true,
                            indexAxis: 'x',
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: 'Views'
                                    }
                                },
                                x: {
                                    title: {
                                        display: true,
                                        text: 'Novel'
                                    }
                                }
                            },
                            plugins: {
                                legend: {
                                    position: 'top',
                                },
                                title: {
                                    display: true,
                                    text: 'Comparison of Top 7 Novels in Two Months'
                                },
                                tooltip: {
                                    mode: 'index',
                                    intersect: false,
                                }
                            }
                        }
                    });
                }

                // Export to Excel functionality
                $('#exportBtn').click(function () {
                    const exportData = [];
                    topSortedNovels.forEach(novel => {
                        exportData.push([novel.name, novel.currentViews, novel.lastViews]);
                    });
                    exportData.unshift(['Novel Name', 'Views This Month', 'Views Last Month']); // Column titles

                    const wb = XLSX.utils.book_new();
                    const ws = XLSX.utils.aoa_to_sheet(exportData);
                    XLSX.utils.book_append_sheet(wb, ws, 'Top Novels');
                    XLSX.writeFile(wb, 'Top_Novel_Statistics.xlsx');
                });
            });
        </script>

    </body>
</html>
