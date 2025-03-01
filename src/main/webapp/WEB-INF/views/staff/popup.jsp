<%-- 
    Document   : popup
    Created on : Feb 26, 2025, 4:48:22 PM
    Author     : Nguyen Thanh Trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Popup</title>
        <!-- jQuery (quan trọng, phải trước Bootstrap JS) -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <!-- Bootstrap JS (phải có) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </head>
    <body>
        <!-- Modal -->
        <div class="modal" id="lockModal" tabindex="-1" aria-labelledby="lockModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">

                    <div class="modal-header" style="justify-content: initial; display: block;">
                        <h4 class="modal-title" id="lockModalLabel" style="float: left">Lock Reason</h4>
                        <button type="button" class="close" style="float: right" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="managenovel" method="post" style="display: inline">
                        <div class="modal-body">
                            <input type="hidden" name="novelID" id="novelID">
                            <textarea id="lockReason" name="lockReason" class="form-control" placeholder="Enter lock reason" rows="3"></textarea>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <!--                                                                    <button type="button" class="btn btn-danger" onclick="submitLock()">Confirm</button>-->

                            <input type="hidden" name="action" value="lock">
                            <button type="submit" class="btn btn-danger">Confirm</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="js/startmin/popup.js"></script>
    </body>
</html>
