<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Novel List</title>
   
     <link rel="stylesheet" href="css/home/header1.css?v=2">
    <link rel="stylesheet" href="css/home/novel-list.css">
   
    <link rel="stylesheet" href="css/home/filter.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
   
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Macondo+Swash+Caps&display=swap">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/user/components/header.jsp"></jsp:include>
    
    
    <div id="wrapper">
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                      <main class="novel-list-page">
                           <jsp:include page="/WEB-INF/views/user/components/link.jsp"></jsp:include>
                            <div class="content-area">
                                  <jsp:include page="/WEB-INF/views/user/components/sidebar.jsp"></jsp:include>
                                 <div class="novel-list-container">
                                    
                                        <jsp:include page="/WEB-INF/views/user/components/fitter.jsp"></jsp:include>
                                      <c:if test="${not empty searchQuery}">
                                         <h2>Search Results for: "${searchQuery}"</h2>
                                       </c:if>
                                     <c:if test="${empty novels && empty searchQuery}">
                                         <p>No novels to show. </p>
                                     </c:if>
                                     <c:if test="${empty novels && not empty searchQuery}">
                                          <p>No result found for query "${searchQuery}"</p>
                                      </c:if>
                                     <div class="novel-grid">
                                          <c:forEach var="novel" items="${novels}">
                                               <div class="novel-item">
                                                   
                                                    <span class="novel-rating">${novel.averageRating != null ? String.format("%.2f", novel.averageRating) : "N/A"}</span>
                                                  <a href="novel?id=${novel.novelID}">
                                                       <div class="novel-cover">
                                                           <img src="${novel.imageURL}" alt="${novel.novelName}">
                                                        </div>
                                                        <h3 class = "novel-title">${novel.novelName}</h3>
                                                    </a>
                                                     <p class = "novel-author">By: ${novel.author}</p>
                                                 </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                              </div>
                        </main>
                    </div>
              </div>
        </div>
    </div>
   
                                      
    <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>
     <script src="js/home/header.js"></script>
   <script src="js/home/sidebar.js"></script>
     <script src="js/lock.js"></script>
</body>
</html>