<%-- 
    Document   : rejectChapter
    Created on : Mar 10, 2025, 3:17:10 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Reject Chapter</title>
</head>
<body>
    <h1>Reject Chapter</h1>
    <form action="managechapter" method="post">
        <input type="hidden" name="action" value="processReject">
        <input type="hidden" name="chapterId" value="${chapterId}">
        <label for="rejectReason">Reason for Rejection:</label><br>
        <textarea id="rejectReason" name="rejectReason" rows="4" cols="50" required></textarea><br>
        <input type="submit" value="Reject Chapter">
    </form>
    <a href="managechapter?action=viewpending">Cancel</a>
</body>
</html>
