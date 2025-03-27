<%-- 
    Document   : postNovel
    Created on : Feb 27, 2025, 11:32:16 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Novel</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">

        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="stylesheet" href="css/home/header(d).css">
        <style>
            label{
                font-size: 18px;
            }
            .page-header{
                margin-top: 0;
            }

            input.form-control{

            }
        </style>

        <link href="https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/user/components/header.jsp"></jsp:include>
        <c:if test="${not empty popup}">
            <script>
                window.onload = function () {
                    alert("${popup}");
                };
            </script>
        </c:if>
            <div class="container-fluid">

                <div class="row">
                    <div class="col-sm-12">
                        <h1 class="page-header">Update Novel</h1>
                    </div>
                    <div class="col-sm-12">
                        <div class="panel panel-info">
                            <div class="panel-heading" style="font-size: 18px">
                                Novel Information
                            </div>
                            <div class="panel-body">
                                <form action="mynovel" method="post" enctype="multipart/form-data">
                                    <div class="row">

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>ID</label>
                                            <input class="form-control" type="text" name="novelID" placeholder="Enter here" required readonly
                                                   value="${novel.novelID}">
                                        </div>
                                        <div class="form-group">
                                            <label>Novel Name</label>
                                            <input class="form-control" type="text" name="novelName" placeholder="Enter here" required
                                                   value="${novel.novelName}" oninput="this.value = this.value.trimStart()">
                                        </div>
                                        <div class="form-group">
                                            <label>Total Of Chapter</label>
                                            <input class="form-control" type="number" name="totalChapter" placeholder="Enter here" required
                                                   value="${novel.totalChapter}">
                                        </div>

                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea class="form-control" rows="5" name="novelDescription" placeholder="Enter Description" required
                                                      oninput="this.value = this.value.trimStart()">${novel.novelDescription}</textarea>
                                        </div>

                                        <div class="form-group">
                                            <label>Genres</label>
                                            <c:forEach var="c" items="${requestScope.genreList}" >
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox" name="genreList" value="${c.genreID}"
                                                               <c:if test="${not empty genreOfNovel and genreOfNovel.contains(c.genreName)}">checked</c:if> 
                                                                   >
                                                        ${c.genreName}
                                                    </label>
                                                </div>
                                            </c:forEach>  
                                        </div>

                                        <div class="form-group">
                                            <label>Status</label>
                                            <p class="form-control-static">
                                                <c:choose>
                                                    <c:when test="${novel.novelStatus == null}">
                                                        Pending
                                                    </c:when>

                                                    <c:when test="${novel.novelStatus != null}">
                                                        ${novel.novelStatus}
                                                    </c:when>
                                                </c:choose>
                                            </p>
                                        </div>
                                    </div>



                                    <!-- /.col-md-6 (nested) -->
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Novel Image</label>
                                            <input id="file-input" type="file" name="imageURL" accept="image/*" 
                                                   <c:if test="${novel.imageURL == null}"> required </c:if>>
                                            </div>

                                            <div class="form-group">
                                                <input type="hidden" name="file_hidden" value="${novel.imageURL}">
                                            <img id="image-preview" src="${novel.imageURL}" alt="Image" style="max-height: 300px; max-width: 100%"> 
                                        </div>

                                        <div class="form-group">
                                            <h3 style="color: red; word-wrap: break-word"> ${requestScope.error}</h3>
                                            <h3> ${message}</h3>
                                        </div>
                                    </div>

                                    <!-- /.col-lg-6 (nested) -->
                                </div>
                                <div>
                                    <input type="hidden" name="action" value="update">
                                    <button type="submit" value="Submit" class="btn btn-success">Submit</button>
                                    <button type="reset" class="btn btn-danger">Reset</button>
                                </div>
                            </form>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-md-12 -->
            </div>
            <!-- /.row -->
        </div>


        <jsp:include page="/WEB-INF/views/user/components/footer.jsp"></jsp:include>
        <script src="js/home/header(d).js"></script>
        <script src="js/mynovel/novelform.js"></script>
    </body>
</html>
