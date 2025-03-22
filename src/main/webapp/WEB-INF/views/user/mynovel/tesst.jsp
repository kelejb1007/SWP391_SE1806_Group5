<%-- 
    Document   : tesst
    Created on : Mar 20, 2025, 11:59:08 PM
    Author     : Nguyen Thanh Trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container-fluid">

            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-header">Create Novel</h1>
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
                                            <label>Novel Name</label>
                                            <input class="form-control" type="text" name="novelName" placeholder="Enter here" required
                                                   value="${novel.novelName}">
                                        </div>
                                        <div class="form-group">
                                            <label>Total Of Chapter</label>
                                            <input class="form-control" type="number" name="totalChapter" placeholder="Enter here" required
                                                   value="${novel.totalChapter}">
                                        </div>

                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea class="form-control" rows="5" name="novelDescription" placeholder="Enter Description" required>${novel.novelDescription}</textarea>
                                        </div>

                                        <div class="form-group">
                                            <label>Genres</label>
                                            <c:forEach var="c" items="${requestScope.genreList}" >
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox" name="genreList" value="${c.genreID}"
                                                               <c:if test="${not empty genreOfNovel and genreOfNovel.contains(String.valueOf(c.genreID))}">checked</c:if> 
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


                                </div>
                                <div>
                                    <input type="hidden" name="action" value="post">
                                    <button type="submit" value="Submit" class="btn btn-success">Submit</button>
                                    <button type="reset" class="btn btn-danger">Reset</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
