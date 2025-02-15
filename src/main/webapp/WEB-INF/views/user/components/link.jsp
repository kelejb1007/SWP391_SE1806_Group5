<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
 

    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

     <link rel="stylesheet" href="css/home/link.css">

   
</head>
<body>

    <div class = "link-container">
        <div class="breadcrumb">
             <!-- Sử dụng Font Awesome -->
             <a href="homepage" class="breadcrumb-item home-link">
                <i class="fas fa-home"></i>
            </a>
            <!-- Sử dụng Bootstrap Icons (nếu bạn chọn Bootstrap Icons ở trên, hãy comment dòng Font Awesome ở trên và bỏ comment dòng này) -->
            <!--  <a href="homepage" class="breadcrumb-item home-link">
                <i class="bi bi-house-fill"></i>
            </a> -->
            <span class="separator">/</span>
            <c:if test="${not empty genreName}">
                <a href="novels?genre=${genreName}" class="breadcrumb-item genre-link">${genreName}</a>
                <span class="separator">/</span>
            </c:if>
            <c:if test="${empty genreName}">
                <a href="<c:url value='/novels' />" class="breadcrumb-item genre-link">Novel List</a>

                
            </c:if>
                <span class="breadcrumb-item current-novel">${novelName}</span>
        </div>
    </div>

  


</body>
</html>