<%--
    Document   : viewNovelStatistics
    Created on : 25-Mar-2025, 23:17:28
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Novel" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title> Novel Statistics</title>
    <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut") %>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            color: white;
            font-family: 'Arial', sans-serif;
        }
        .container {
            background: rgba(255, 255, 255, 0.1);
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3);
        }
        .table {
            background: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            overflow: hidden;
        }
        th {
            background: #343a40;
            color: white;
        }
        .icon {
            font-size: 1.2rem;
            color: #007bff;
            margin-right: 5px;
        }
        .pagination {
            justify-content: center;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4 text-center"><i class="bi bi-bar-chart-line-fill"></i> Novel Statistics</h2>
        <div class="table-responsive">
            <table class="table table-hover table-bordered text-center" id="novelTable">
                <thead>
                    <tr>
                        <th>#</th>
                        <th><i class="bi bi-book"></i> Name</th>
                        <th><i class="bi bi-info-circle"></i> Status</th>
                        <th><i class="bi bi-eye"></i> Views</th>
                        <th><i class="bi bi-book-half"></i> Reads</th>
                        <th><i class="bi bi-chat-dots"></i> Comments</th>
                        <th><i class="bi bi-heart"></i> Favorites</th>
                        <th><i class="bi bi-star-fill"></i> Avg. Rating</th>
                        <th><i class="bi bi-person-check"></i> Ratings</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Novel> statistics = (List<Novel>) request.getAttribute("statistics");
                        if (statistics != null && !statistics.isEmpty()) {
                            for (Novel novel : statistics) {
                    %>
                    <tr>
                        <td><i class="bi bi-hash"></i> <%= novel.getNovelID() %></td>
                        <td class="text-start"><strong><%= novel.getNovelName() %></strong></td>
                        <td><span class="badge bg-primary"><%= novel.getNovelStatus() %></span></td>
                        <td><i class="bi bi-eye icon"></i> <%= novel.getTotalViews() %></td>
                        <td><i class="bi bi-book icon"></i> <%= novel.getTotalReads() %></td>
                        <td><i class="bi bi-chat icon"></i> <%= novel.getTotalComments() %></td>
                        <td><i class="bi bi-heart icon"></i> <%= novel.getTotalFavorites() %></td>
                        <td>
                            <i class="bi bi-star icon"></i> 
                            <%= novel.getAvgRating() != null ? String.format("%.2f", novel.getAvgRating()) : "N/A" %>
                        </td>
                        <td><i class="bi bi-person icon"></i> <%= novel.getRatingCount() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="9" class="text-center text-muted">No data available</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <nav>
                <ul class="pagination" id="pagination"></ul>
            </nav>
            <button class="btn btn-light mt-3" onclick="goBack()"><i class="bi bi-arrow-left"></i> Back</button>
        </div>
    </div>

    <script>
        function goBack() {
            window.history.back();
        }

        document.addEventListener("DOMContentLoaded", function () {
            let itemsPerPage = 5;
            let table = document.getElementById("novelTable");
            let tbody = table.getElementsByTagName("tbody")[0];
            let rows = Array.from(tbody.getElementsByTagName("tr"));
            let totalPages = Math.ceil(rows.length / itemsPerPage);
            let pagination = document.getElementById("pagination");

            function showPage(page) {
                let start = (page - 1) * itemsPerPage;
                let end = start + itemsPerPage;
                rows.forEach((row, index) => {
                    row.style.display = (index >= start && index < end) ? "" : "none";
                });
            }

            function createPagination() {
                pagination.innerHTML = "";
                for (let i = 1; i <= totalPages; i++) {
                    let li = document.createElement("li");
                    li.className = "page-item";
                    let a = document.createElement("a");
                    a.className = "page-link";
                    a.href = "#";
                    a.textContent = i;
                    a.onclick = function () {
                        showPage(i);
                        document.querySelectorAll(".page-item").forEach(el => el.classList.remove("active"));
                        li.classList.add("active");
                    };
                    li.appendChild(a);
                    pagination.appendChild(li);
                }
                showPage(1);
                pagination.firstChild.classList.add("active");
            }

            if (rows.length > itemsPerPage) {
                createPagination();
            }
        });
    </script>
</body>
</html>