<%-- 
    Document   : ViewProfile
    Created on : Feb 28, 2025, 5:52:24 AM
    Author     : default
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Profile</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <div class="profile-container">
        <!-- Avatar -->
        <div class="avatar">
            <img src="../../img/a1.jpg" alt="User Avatar">
        </div>

        <!-- Profile Information -->
        <form class="profile-form">
            <label>User name</label>
            <input type="text" value="${userAccount.userName}" readonly>

            <label>Full name</label>
            <input type="text" value="${userAccount.fullName}" readonly>

            <label>Password</label>
            <input type="password" value="${userAccount.password}" readonly>

            <label>Creation Date</label>
            <input type="text" value="${userAccount.creationDate}" readonly>

            <label>Email</label>
            <input type="text" value="${userAccount.email}" readonly>

            <label>Number phone</label>
            <input type="text" value="${userAccount.numberPhone}" readonly>

            <label>Date of Birth</label>
            <input type="text" value="${userAccount.dateOfBirth}" readonly>

            <label>Gender</label>
            <input type="text" value="${userAccount.gender}" readonly>

            <button type="button">Edit</button>
        </form>
    </div>
</body>
</html>

