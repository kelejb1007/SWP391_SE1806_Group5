<%-- 
    Document   : lockChapter
    Created on : Mar 9, 2025, 8:25:45 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lock Chapter</title>
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="css/startmin/bootstrap.css">
    <link rel="stylesheet" href="css/startmin/startmin.css">
    <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/startmin/metisMenu.min.css">
</head>
<body>
    <div class="modal fade" id="lockModal" tabindex="-1" role="dialog" aria-labelledby="lockModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form action="managechapter" method="post" style="display: inline">
                    <div class="modal-header">
                        <h5 class="modal-title" id="lockModalLabel">Lock Chapter</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="chapterId" value="${chapterId}">
                        <div class="form-group">
                            <label for="lockReason">Reason for Locking:</label>
                            <textarea class="form-control" id="lockReason" name="lockReason" rows="3" required></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <input type="hidden" name="action" value="processLock">
                        <button type="submit" class="btn btn-danger">Lock Chapter</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="js/startmin/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="js/startmin/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#lockModal").modal("show");
        });
    </script>
</body>
</html>
