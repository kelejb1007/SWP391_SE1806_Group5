<%-- 
    Document   : ViewAllChapters
    Created on : Feb 22, 2025, 7:20:53 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="chapterList" value="${requestScope.chapterList}" />
<c:set var="novelId" value="${requestScope.novelId != null ? requestScope.novelId : 0}" />
<c:set var="sortOrder" value="${requestScope.sortOrder != null ? requestScope.sortOrder : 'asc'}" />
<c:set var="errorMessage" value="${requestScope.errorMessage}" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Chapters</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            function openLockModal(chapterId, action) {
                document.getElementById("chapterIdInput").value = chapterId;
                document.getElementById("lockActionInput").value = action;
                document.getElementById("lockReasonInput").value = ""; // Reset reason
                let modal = new bootstrap.Modal(document.getElementById('lockReasonModal'));
                modal.show();
            }
        </script>
    </head>
    <body class="container mt-4">

        <h2 class="text-center mb-4">Manage Chapters</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>

        <form action="ViewAllChapters" method="GET" class="mb-3">
            <input type="hidden" name="novelId" value="${novelId}">
            <label class="me-2">Sort by Chapter Number:</label>
            <select name="sortOrder" class="form-select w-auto d-inline" onchange="this.form.submit()">
                <option value="asc" ${sortOrder eq 'asc' ? 'selected' : ''}>Ascending</option>
                <option value="desc" ${sortOrder eq 'desc' ? 'selected' : ''}>Descending</option>
            </select>
        </form>

        <table class="table table-bordered table-hover text-center">
            <thead class="table-light">
                <tr>
                    <th>Chapter Number</th>
                    <th>Chapter Name</th>
                    <th>File</th>
                    <th>Published Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty chapterList}">
                        <c:forEach var="chapter" items="${chapterList}">
                            <tr>
                                <td>${chapter.chapterNumber}</td>
                                <td>${chapter.chapterName}</td>
                                <td><a href="${chapter.fileURL}" target="_blank" class="btn btn-sm btn-primary">View</a></td>
                                <td>${not empty chapter.publishedDate ? chapter.publishedDate : "N/A"}</td>
                                <td>${chapter.locked ? "Locked" : "Unlocked"}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${chapter.locked}">
                                            <button type="button" class="btn btn-success btn-sm"
                                                    onclick="openLockModal('${chapter.chapterID}', 'unlock')">Unlock</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button" class="btn btn-danger btn-sm"
                                                    onclick="openLockModal('${chapter.chapterID}', 'lock')">Lock</button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="text-center">No chapters found.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <!-- Lock Reason Modal -->
        <div class="modal fade" id="lockReasonModal" tabindex="-1" aria-labelledby="lockReasonModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="lockReasonModalLabel">Enter Lock Reason</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Form Lock/Unlock -->
                        <form id="lockForm" action="ViewAllChapters" method="POST">
                            <input type="hidden" name="action" value="updateLock">
                            <input type="hidden" name="chapterId" id="chapterIdInput">
                            <input type="hidden" name="lockAction" id="lockActionInput">
                            <input type="hidden" name="novelId" value="${novelId}"> <!-- Giữ lại novelId -->
                            <div class="mb-3">
                                <label for="lockReasonInput" class="form-label">Reason:</label>
                                <textarea class="form-control" id="lockReasonInput" name="lockReason" required></textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-primary">Confirm</button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
