<%-- 
    Document   : viewAllNovel
    Created on : Feb 15, 2025, 3:28:07 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Novel</title>


        <link rel="stylesheet" href="css/startmin/bootstrap.min.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/startmin/metisMenu.min.css">

        <link href="css/startmin/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <link href="css/startmin/dataTables/dataTables.responsive.css" rel="stylesheet">

        <style>
            .search-advance{
                display: inline-block;
                float: right;
                vertical-align: top;
                font-size: 14px;
                color: #333;
                border: 1px solid #333;
                border-radius: 13px;
                padding: 8px 25px 9px 15px;
                transition: all .5s;
                transform: translateY(0px);
                margin-right: 40px;
            }
            .search-advance:hover{
                background-color: #b3d9ff;
                text-decoration: none;
                color: inherit;

            }
            .hidden {
            }
            .filterForm.hidden{
                height:0;
                overflow: hidden;
                transition: height 0.4s ease-out
            }
            .filterForm{
                height: auto;
                border-bottom: 1px solid #eee;
                padding-bottom: 9px;
                margin-bottom: 20px;
            }
            .form-group{
                margin: 20px 5px
            }
            .checkboxcolumn label{
                padding: 5px 10px;
                font-weight: normal
            }
        </style>
        <script>
            function openLockModal(novelID, novelName) {
                document.getElementById('novelID').value = novelID;
                document.getElementById('lockReason').value = '';
                document.getElementById('novelName').innerHTML = novelName + " (ID=" + novelID + ")";
//                var modal = new bootstrap.Modal(document.getElementById('lockModal'));
//                modal.show();
                $("#lockModal").modal("show");
            }

            function viewDetail(event, novelID) {
                if (event.target.tagName.toLowerCase() !== 'button') {
                    window.location.href = 'managenovel?action=viewdetail&novelID=' + novelID;
                }
            }
        </script>

    </head>
    <body>
        <c:choose>
            <c:when test="${not empty error}">
                <div>
                    <span style="font-size: 20px">${error}</span>
                </div>
            </c:when>

            <c:otherwise>
                <div id="wrapper">
                    <jsp:include page="header.jsp" />
                    <jsp:include page="sidebar.jsp" />

                    <c:if test="${not empty popup}">
                        <script>
                            window.onload = function () {
                                alert("${popup}");
                            };
                        </script>
                    </c:if>


                    <div id="page-wrapper">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-sm-8" Style="padding-right: 0">
                                    <h1 class="page-header">MANAGE NOVEL</h1>
                                </div>

                                <div class="col-sm-4" Style="padding-left: 0">
                                    <div style="margin: 40px 0 20px; border-bottom: 1px solid #eee; padding-bottom: 9px; height: 49.4px">
                                        <button class="search-advance" id="toggleFilterBtn"
                                                title="Filter">
                                            <i class="fa  fa-filter fa-fw"></i> Filter</button>
                                    </div>
                                </div>



                                <div id="filterForm" class="col-sm-12 hidden filterForm">
<!--                                    <form action="/?lib=all" id="mbsearchForm-Advance" method="get">
                                        <input name="lib" type="text" value="all" hidden="true">
                                        <label>
                                            <select name="ct" id="ct">
                                                <option value="">Tất cả</option><option value="A1">Tiên Hiệp</option><option value="A2">Kiếm Hiệp</option><option value="A3">Ngôn Tình</option><option value="B8">Đô Thị</option><option value="B9">Quan Trường</option><option value="F2">Võng Du</option><option value="C1">Khoa Huyễn</option><option value="C2">Huyền huyễn</option><option value="C3">Dị Giới</option><option value="C4">Dị Năng</option><option value="C5">Quân Sự</option><option value="C6">Lịch Sử</option><option value="C7">Xuyên Không</option><option value="C8">Trọng Sinh</option><option value="C9">Trinh Thám</option><option value="F3">Thám Hiểm</option><option value="D1">Linh Dị</option><option value="D2">Truyện Sắc</option><option value="D3">Truyện Ngược</option><option value="D4">Truyện Sủng</option><option value="D5">Truyện Cung Đấu</option><option value="D6">Truyện Nữ Cường</option><option value="D7">Truyện Gia Đấu</option><option value="D8">Đông Phương</option><option value="D9">Đam Mỹ</option><option value="F4">Bách Hợp</option><option value="E1">Hài Hước</option><option value="E2">Điền Văn</option><option value="E3">Cổ Đại</option><option value="E4">Mạt Thế</option><option value="E5">Truyện Teen</option><option value="E6">Phương Tây</option><option value="E7">Nữ Phụ</option><option value="E8">Light Novel</option><option value="E9">Việt Nam</option><option value="F5">Đoản Văn</option><option value="F6">Truyện Khác</option></select></label><label><select name="order" id="filter"><option value="0">Xếp Theo</option><option value="0">Truyện Mới Update</option><option value="8">Truyện Mới</option><option style="color: red;" value="1">Đọc Nhiều</option><option value="7">Like Nhiều</option><option value="2">Top Tuần</option><option value="3">Top Tuần Trước</option><option value="4">Top Tháng</option><option value="5">Top Tháng Trước</option><option value="6">A -&gt; Z</option></select></label><label><select name="greater" id="so-chuong"><option value="0">Số Chương &gt; 0</option><option value="10">Số Chương &gt; 10 </option><option value="20">Số Chương &gt; 20 </option><option value="50">Số Chương &gt; 50 </option><option value="100">Số Chương &gt; 100 </option><option value="200">Số Chương &gt; 200 </option><option value="500">Số Chương &gt; 500 </option><option value="1000">Số Chương &gt; 1000 </option></select></label><label><select name="lesser" id="so-chuong-2"><option value="1000000000">Số Chương &lt; 1 Tỷ</option><option value="10">Số Chương &lt; 10 </option><option value="20">Số Chương &lt; 20 </option><option value="50">Số Chương &lt; 50 </option><option value="100">Số Chương &lt; 100 </option><option value="200">Số Chương &lt; 200 </option><option value="500">Số Chương &lt; 500 </option><option value="1000">Số Chương &lt; 1000 </option>
                                            </select>
                                        </label>
                                        <div class="check">
                                            <input type="checkbox" name="full" value="full">
                                            <span class="rv-sr-a">Truyện full</span>
                                        </div><div class="check">
                                            <input type="checkbox" name="hot" value="hot">
                                            <span class="rv-sr-a">Truyện hot</span>
                                        </div>
                                        <button type="submit" value="Tìm Truyện">Tìm Truyện</button>
                                    </form>-->

                                    <form action="managenovel" >
                                        <div class="form-group">
                                            <label>Display column</label>
                                            <label style="margin-left: 25px"><input type="checkbox" id="checkAll" checked>Check all</label><br>
                                            <div class="checkboxcolumn" style="display: inline-block; margin: 0px">
                                                <label><input type="checkbox" class="toggle-vis" data-column="0" name="columnCheck" value="#" 
                                                              <c:if test="${not empty columnCheck and columnCheck.contains('#')}">checked</c:if>>#</label>
                                                    <label><input type="checkbox" class="toggle-vis" data-column="2" name="columnCheck" value="Author" 
                                                        <c:if test="${not empty columnCheck and columnCheck.contains('Author')}">checked</c:if>>Author</label>
                                                    <label><input type="checkbox" class="toggle-vis" data-column="3" name="columnCheck" value="Total of chapter" 
                                                        <c:if test="${not empty columnCheck and columnCheck.contains('Total of chapter')}">checked</c:if>>Total of chapter</label>
                                                    <label><input type="checkbox" class="toggle-vis" data-column="4" name="columnCheck" value="Genres" 
                                                        <c:if test="${not empty columnCheck and columnCheck.contains('Genres')}">checked</c:if>>Genres</label>
                                                    <label><input type="checkbox" class="toggle-vis" data-column="5" name="columnCheck" value="Published Date" 
                                                        <c:if test="${not empty columnCheck and columnCheck.contains('Published Date')}">checked</c:if>>Published Date</label>
                                                    <label><input type="checkbox" class="toggle-vis" data-column="6" name="columnCheck" value="Rating Average" 
                                                        <c:if test="${not empty columnCheck and columnCheck.contains('Rating Average')}">checked</c:if>>Rating Average</label>
                                                    <label><input type="checkbox" class="toggle-vis" data-column="7" name="columnCheck" value="View" 
                                                        <c:if test="${not empty columnCheck and columnCheck.contains('View')}">checked</c:if>>View</label>

                                                </div>
                                            </div>


                                            <div class="form-group">
                                                <label>Genres</label>
                                                <label style="margin-left: 25px"><input type="checkbox" id="checkAllGenre" checked>Check all</label><br><br>
                                            <c:forEach var="c" items="${requestScope.listGenreName}" >
                                                <div class="checkbox" style="display: inline-block; padding: 5px 10px; margin: 0px">
                                                    <label>
                                                        <input type="checkbox" class="genCheck" name="genChecked" value="${c}"
                                                               <c:if test="${not empty genChecked and genChecked.contains(c)}">checked</c:if> 
                                                                   >
                                                        ${c}
                                                    </label>
                                                </div>
                                            </c:forEach>  
                                        </div>

                                        <button class="search-advance" >
                                            <i class="fa  fa-filter fa-fw"></i> Find novel</button>
                                    </form>
                                </div>



                            </div>
                            <div style="margin: 5px; font-size: 18px; color: #d58512">
                                <strong>Genre Filter: </strong>${genChecked}  
                            </div>
                            <!-- /.row -->
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            List of novels
                                        </div>
                                        <!-- /.panel-heading -->
                                        <div class="panel-body">
                                            <div class="table-responsive">
                                                <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                                    <thead>
                                                        <tr>
                                                            <th>#</th>
                                                            <th>Title</th>
                                                            <th>Author</th>
                                                            <th>Total of chapter</th>
                                                            <th>Genres</th>
                                                            <th>Published Date</th>
                                                            <th>Rating Average</th>
                                                            <th>View</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="c" items="${requestScope.listNovel}" varStatus="status">
                                                            <tr onclick ="viewDetail(event, ${c.novelID});" style="cursor: pointer">
                                                                <td>${status.index + 1}</td>
                                                                <td>
                                                                    <a href="managenovel?action=viewdetail&novelID=${c.novelID}" title="View detail">${c.novelName}</a>
                                                                </td>
                                                                <td>${c.author}</td>
                                                                <td>${c.totalChapter}</td>
                                                                <td>${c.genres}</td>
                                                                <td>
                                                                    <fmt:formatDate value="${c.publishDate2}" pattern="yyyy-MM-dd HH:mm:ss" />
                                                                </td>
                                                                <td>${c.averageRating}</td>
                                                                <td>${c.viewCount}</td>
                                                                <td>
                                                                    <button type="button" class="btn btn-info"
                                                                            onclick="window.location.href = 'managechapter?action=viewallchapters&novelId=${c.novelID}';">View Chapter
                                                                    </button>
                                                                    <button type="button" id="open" class="btn btn-danger" onclick="openLockModal('${c.novelID}', '${c.novelName}')">Lock</button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>  
                                                    </tbody>
                                                </table>

                                                <!-- Modal (Popup) -->
                                                <div id="lockModal" class="modal fade" role="dialog">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form action="managenovel" method="post" style="display: inline">
                                                                <div class="modal-header" style="border-bottom: none; height: 45px">
                                                                    <h4 class="modal-title"  style="float: left; margin-right: 10px; width: 95%;
                                                                        margin-bottom: 10px;display: -webkit-box; overflow: hidden;
                                                                        /*                                                                white-space: nowrap; text-overflow: ellipsis; */
                                                                        word-wrap: break-word; -webkit-box-orient: vertical; -webkit-line-clamp: 2;
                                                                        ">You are locking the novel: <strong><span id="novelName"></span></strong></h4>

                                                                    <button type="button" class="close" style="float: right" data-dismiss="modal">&times;</button>
                                                                </div>

                                                                <div class="modal-body">
                                                                    <input type="hidden" name="novelID" id="novelID">
                                                                    <textarea id="lockReason" name="lockReason" class="form-control" placeholder="Enter lock reason" rows="3" required></textarea>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                                    <!--<button type="button" class="btn btn-danger" onclick="submitLock()">Confirm</button>-->

                                                                    <input type="hidden" name="action" value="lock">
                                                                    <button type="submit" class="btn btn-danger">Confirm</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>

        <script src="js/startmin/dataTables/jquery.dataTables.min.js"></script>
        <script src="js/startmin/dataTables/dataTables.bootstrap.min.js"></script>

        <script>

                                                                        $(document).ready(function () {
                                                                            var table = $('#dataTables-example').DataTable({
                                                                                responsive: true,
                                                                                "autoWidth": false,
                                                                                language: {
                                                                                    info: 'Showing page _PAGE_ of _PAGES_',
                                                                                    infoEmpty: '${listnull}',
                                                                                    infoFiltered: '(filtered from _MAX_ total novels)',
                                                                                    lengthMenu: 'Display _MENU_ novels per page',
                                                                                    zeroRecords: 'Nothing found - sorry'
                                                                                },
                                                                                columnDefs: [
                                                                                    {width: "160px", targets: 8}
                                                                                ]
                                                                            });

                                                                            // load man hinh 
                                                                            $('.toggle-vis').each(function () {
                                                                                var isChecked = $(this).prop('checked');
                                                                                var column = table.column($(this).attr('data-column'));
                                                                                column.visible(isChecked);

                                                                                // Kiểm tra nếu tất cả checkbox con đều được chọn thì chọn luôn checkbox "Chọn tất cả"
                                                                                if ($('.toggle-vis:checked').length === $('.toggle-vis').length) {
                                                                                    $('#checkAll').prop('checked', true);
                                                                                } else {
                                                                                    $('#checkAll').prop('checked', false);
                                                                                }
                                                                            });
                                                                            // khi nhan checkbox
                                                                            $('.toggle-vis').on('change', function (e) {
                                                                                e.preventDefault();
                                                                                var isChecked = $(this).prop('checked');
                                                                                var column = table.column($(this).attr('data-column'));
                                                                                column.visible(isChecked);

                                                                                // Kiểm tra nếu tất cả checkbox con đều được chọn thì chọn luôn checkbox "Chọn tất cả"
                                                                                if ($('.toggle-vis:checked').length === $('.toggle-vis').length) {
                                                                                    $('#checkAll').prop('checked', true);
                                                                                } else {
                                                                                    $('#checkAll').prop('checked', false);
                                                                                }
                                                                            });

                                                                            // Xử lý khi nhấn "Chọn tất cả"
                                                                            $('#checkAll').on('change', function () {
                                                                                var isChecked = $(this).prop('checked');
                                                                                $('.toggle-vis').each(function () {
                                                                                    $(this).prop('checked', isChecked).trigger('change'); // Chọn/bỏ chọn tất cả checkbox con
                                                                                });
                                                                            });
                                                                            
                                                                            
                                                                            
                                                                            
                                                                            
                                                                                 // load man hinh 
                                                                            $('.genCheck').each(function () {
                                                                                // Kiểm tra nếu tất cả checkbox con đều được chọn thì chọn luôn checkbox "Chọn tất cả"
                                                                                if ($('.genCheck:checked').length === $('.genCheck').length) {
                                                                                    $('#checkAllGenre').prop('checked', true);
                                                                                } else {
                                                                                    $('#checkAllGenre').prop('checked', false);
                                                                                }
                                                                            });
                                                                            // khi nhan checkbox
                                                                            $('.genCheck').on('change', function (e) {
                                                                                e.preventDefault();
                                                                                // Kiểm tra nếu tất cả checkbox con đều được chọn thì chọn luôn checkbox "Chọn tất cả"
                                                                                if ($('.genCheck:checked').length === $('.genCheck').length) {
                                                                                    $('#checkAllGenre').prop('checked', true);
                                                                                } else {
                                                                                    $('#checkAllGenre').prop('checked', false);
                                                                                }
                                                                            });
                                                                            
                                                                            $('#checkAllGenre').on('change', function () {
                                                                                var isChecked = $(this).prop('checked');
                                                                                $('.genCheck').each(function () {
                                                                                    $(this).prop('checked', isChecked).trigger('change'); // Chọn/bỏ chọn tất cả checkbox con
                                                                                });
                                                                            });
                                                                        });


                                                                        document.getElementById("toggleFilterBtn").addEventListener("click", function () {
                                                                            let filterForm = document.getElementById("filterForm");
                                                                            // Toggle class 'hidden' (Ẩn/Hiện)
                                                                            if (filterForm.classList.contains("hidden")) {
                                                                                filterForm.classList.remove("hidden");
                                                                            } else {
                                                                                filterForm.classList.add("hidden");
                                                                            }
                                                                        });




        </script>

        <script src="js/startmin/raphael.min.js"></script>
        <script src="js/startmin/morris.min.js"></script>
        <script src="js/startmin/morris-data.js"></script>
    </body>
</html>
