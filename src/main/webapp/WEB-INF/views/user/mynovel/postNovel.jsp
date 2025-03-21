<%-- 
    Document   : postNovel
    Created on : Feb 27, 2025, 11:32:16 PM
    Author     : Nguyen Thanh Trung
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="String" class="java.lang.String" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Novel</title>

        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">

        <link rel="shortcut icon" type="image/x-icon" href="//yuxseocdn.yuewen.com/favicon/readnovel.ico">
        <link rel="stylesheet" type="text/css" href="css/mynovel/mynovel2.css" crossorigin="anonymous">
        <script charset="utf-8" src="js/mynovel/mynovel2.js" crossorigin="anonymous"></script>
        <style>
            label{
                font-size: 18px;
                color: rgba(18, 18, 23, .9) !important;
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
        <c:if test="${not empty popup}">
            <script>
                window.onload = function () {
                    alert("${popup}");
                };
            </script>
        </c:if>
        <jsp:include page="/WEB-INF/views/user/mynovel/icon.jsp" /> 
        <div id="root">
            <div class="df"><input class="pf t0 l0 vh" id="foldSwitch" type="checkbox"><input class="pf t0 l0 vh" id="sideSwitch" type="checkbox">
                <jsp:include page="/WEB-INF/views/user/mynovel/sidebar.jsp" />

                <div class="g_main_wrap f1 pr" style="zoom: 1.1">
                    <div style="">
                        <div class="pf t0 l0 header--Unk0j df jcsb">
                            <div class="undefined g_header df fg1 oh">
                                <div class="df aic g_header_title"><label for="foldSwitch" class="collapse_menu--QStMM mr8">
                                        <div class="df g_sd_close collapse_menu_btn--f8c2W"><i></i></div>
                                    </label>
                                    <h2 class="header_title--gwRuS ell dib mw100p t_title_large mb0 vam"><span class="ttc">Create Novel</span></h2>

                                </div>
                            </div>
                        </div>
                        <div id="main_scroll_container" class="scroller--dBDRL pr">
                            <div class="header_ph--kHZzY"></div>
                            <div class="main_content--0x57a ">
                                <div class="default--zRToH bc_fff ">
                                    <form class="ant-form ant-form-vertical setting_form--R6kRQ" action="mynovel" method="post" enctype="multipart/form-data">


                                        <h3 class="mb32 fvsc fw400 fs18 ell lh24"><span role="img" class="anticon vam mr8"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                <use xlink:href="#i-files"></use>
                                                </svg></span>novel information</h3>

                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="bookTitle" class="ant-form-item-required" title="">
                                                        <div class="dib"><span>Novel name</span></div>
                                                    </label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content"><span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                                <input name="novelName" maxlength="70" placeholder="Within 70 characters" id="bookTitle" aria-required="true" class="ant-input ant-input-lg" 
                                                                       type="text" value="${novel.novelName}" required>
                                                            </span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="bookTitle" class="ant-form-item-required" title="">
                                                        <div class="dib"><span>Total Of Chapter</span></div>
                                                    </label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content"><span class="ant-input-affix-wrapper" style="border-radius: 8px;">
                                                                <input name="totalChapter" maxlength="5" placeholder="Predict total of chapter" id="bookTitle" aria-required="true" class="ant-input ant-input-lg" 
                                                                       type="number" value="${novel.totalChapter}" required >
                                                            </span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="bookTitle" class="ant-form-item-required" title="">Description</label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content"><span class="ant-input-affix-wrapper ">
                                                                <textarea name="novelDescription" placeholder="Type something seriously, wonderful description can attract more readers" id="synopsis" class="ant-input" 
                                                                          style="height: 80px; resize: none; min-height: 53.6px;" required>${novel.novelDescription}</textarea>
                                                            </span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>



                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="freeType" class="ant-form-item-required" title="TYPE">TYPE</label></div>


                                                <c:forEach var="c" items="${requestScope.genreList}" >
                                                    <div class="checkbox" style="margin-left: 20px">
                                                        <label style="color: rgba(18, 18, 23, .9) !important; font-weight: 600">
                                                            <input type="checkbox" name="genreList" value="${c.genreID}"
                                                                   <c:if test="${not empty genreOfNovel and genreOfNovel.contains(String.valueOf(c.genreID))}">checked</c:if> 
                                                                       >
                                                            ${c.genreName}
                                                        </label>
                                                    </div>
                                                </c:forEach>  
                                            </div>
                                        </div>


                            


                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label class="" title="">
                                                        <div class="df aic">book cover<a href="https://inkstone.webnovel.com/academy/article/699736885563883520" target="_blank" rel="noreferrer" style="color: rgba(18, 18, 23, 0.6);"><span role="img" class="anticon fs16 fw500 ml4"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                                    <use xlink:href="#i-help"></use>
                                                                    </svg></span></a></div>
                                                    </label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content">
                                                            <div class="ant-form-item  item--1wVCg">
                                                                <div class="ant-row ant-form-item-row">
                                                                    <div class="ant-col ant-form-item-control">
                                                                        <div class="ant-form-item-control-input">
                                                                            <div class="ant-form-item-control-input-content">
                                                                                <div class="book_cover_wrap--xZYzr pr dib" data-report-uiname="bookcover" style="width: 120px; height: 160px;"><span role="img" class="anticon fs24 book_cover_plus--rs178 pa" style="color: rgb(246, 247, 252);"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                                                        <use xlink:href="#i-plus"></use>
                                                                                        </svg></span><img class="br4" width="120" height="160" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAlgAAAMgCAYAAAD/YBzEAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAABs0SURBVHgB7d0PbhtnesdxSk6sGIGA9Q3SEzRH2Jxg9yi9wXZv0L1Je4LtEXKD5gT5A0OxLNtS9RidQCVEakj+ZuZ9h58PoCZG/IemwM53n/fh8GKz5f37939+eHj4y+PXXx9/+N0GAIDn/Fhfl5eXf3/z5s1PT//DxfAvj0H1p8e4+tvjP/9tAwDAaBcXF//x4cOHv799+/bXLz+u/1Nx9fvvv//z8V+/3wAAcIwf7+7ufqjIuqwf1eRqI64AAE7x/dXVVTXV5uIxrr67v7//nw0AACe7vLz84fIxrv62AQAg4vPnz3+tI0JHgwAAIRcXF3+5uLm5edgAABBzuQEAIEpgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhX20AiLq9vd3c3d1tXr9+vfnq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gQUr0mOsrOXO7ABPCSxYkR4nWGv6bEGAgcCCFal9pp6CpR6r/StgjS5ubm76vfUzAECDTLAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQJrAAAMIEFgBAmMACAAgTWAAAYQILACBMYAEAhAksAIAwgQUAECawAADCBBYAQJjAAgAIE1gAAGECCwAgTGABAIQJLACAMIEFABAmsAAAwgQWAECYwAIACBNYAABhAgsAIExgAQCECSwAgDCBBQAQVoH10wYAgJQfLx8eHv5rAwBAyo8X7969+/Pl5eU/NwAAnOyxq/7l8vr6+r/v7+//sQEA4CSPJ4P/ePPmzU8X9YNffvnlT69fv64p1vcbAAAOdnFx8eNjXP3w+M9fv7yL8O3bt7/e3d39YJIFAHC4/5tcfYmr+vHF9k94//79d58/f/73x5/wrxsTLQCAXX6qNws+fv1nrVw9/Q//C0Xyx0Pg2+jmAAAAAElFTkSuQmCC
                                                                                                      " alt="book cover"><button data-report-uiname="upload" type="button" class="ant-btn ant-btn-primary ant-btn-lg mt8 button--4vWlZ" style="display: block; min-width: 134px;"><span role="img" class="anticon"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                                                            <use xlink:href="#i-upload"></use>
                                                                                            </svg></span><span>Upload</span></button></div>
                                                                                <p class="c_danger fs14 mt16"></p>
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

                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="authorTagList" class="" title=""><span>tag category and tags</span></label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content">
                                                            <div class="ant-select ant-select-in-form-item ant-select-single ant-select-show-arrow ant-select-disabled">
                                                                <div class="ant-select-selector"><span class="ant-select-selection-search"><input type="search" disabled="" autocomplete="off" class="ant-select-selection-search-input" role="combobox" aria-expanded="false" aria-haspopup="listbox" aria-owns="rc_select_5_list" aria-autocomplete="list" aria-controls="rc_select_5_list" aria-activedescendant="rc_select_5_list_0" readonly="" unselectable="on" value="" id="rc_select_5" style="opacity: 0;"></span><span class="ant-select-selection-placeholder">Select category</span></div><span class="ant-select-arrow" unselectable="on" aria-hidden="true" style="user-select: none;"><span role="img" aria-label="down" class="anticon anticon-down ant-select-suffix"><svg viewBox="64 64 896 896" focusable="false" data-icon="down" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                                        <path d="M884 256h-75c-5.1 0-9.9 2.5-12.9 6.6L512 654.2 227.9 262.6c-3-4.1-7.8-6.6-12.9-6.6h-75c-6.5 0-10.3 7.4-6.5 12.7l352.6 486.1c12.8 17.6 39 17.6 51.7 0l352.6-486.1c3.9-5.3.1-12.7-6.4-12.7z"></path>
                                                                        </svg></span></span>
                                                            </div>
                                                            <div class="ant-select ant-select-in-form-item ant-select-multiple ant-select-disabled ant-select-show-search">
                                                                <div class="ant-select-selector">
                                                                    <div class="ant-select-selection-overflow">
                                                                        <div class="ant-select-selection-overflow-item ant-select-selection-overflow-item-suffix" style="opacity: 1;">
                                                                            <div class="ant-select-selection-search" style="width: 4px;"><input type="search" disabled="" autocomplete="off" class="ant-select-selection-search-input" role="combobox" aria-expanded="false" aria-haspopup="listbox" aria-owns="rc_select_6_list" aria-autocomplete="list" aria-controls="rc_select_6_list" aria-activedescendant="rc_select_6_list_0" readonly="" unselectable="on" value="" id="rc_select_6" style="opacity: 0;"><span class="ant-select-selection-search-mirror" aria-hidden="true">&nbsp;</span></div>
                                                                        </div>
                                                                    </div><span class="ant-select-selection-placeholder">Search tags</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div id="authorTagList_extra" class="ant-form-item-extra">Before selecting a tag, please choose your target audience (male/female). Accurate tags may raise the chances of readers finding your work.</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="abbreviation" class="" title="">
                                                        <div class="dib"><span>abbreviation</span><span class="author_words_length_tip--zBFfS">0/15</span></div>
                                                    </label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content"><span class="ant-input-affix-wrapper ant-input-affix-wrapper-lg" style="border-radius: 8px;"><input data-report-uiname="abbreviation" maxlength="15" placeholder="Within 15 characters" id="abbreviation" class="ant-input ant-input-lg" type="text" value=""><span class="ant-input-suffix"><span class="ant-input-clear-icon ant-input-clear-icon-hidden" role="button" tabindex="-1"><span role="img" aria-label="close-circle" class="anticon anticon-close-circle"><svg fill-rule="evenodd" viewBox="64 64 896 896" focusable="false" data-icon="close-circle" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                                            <path d="M512 64c247.4 0 448 200.6 448 448S759.4 960 512 960 64 759.4 64 512 264.6 64 512 64zm127.98 274.82h-.04l-.08.06L512 466.75 384.14 338.88c-.04-.05-.06-.06-.08-.06a.12.12 0 00-.07 0c-.03 0-.05.01-.09.05l-45.02 45.02a.2.2 0 00-.05.09.12.12 0 000 .07v.02a.27.27 0 00.06.06L466.75 512 338.88 639.86c-.05.04-.06.06-.06.08a.12.12 0 000 .07c0 .03.01.05.05.09l45.02 45.02a.2.2 0 00.09.05.12.12 0 00.07 0c.02 0 .04-.01.08-.05L512 557.25l127.86 127.87c.04.04.06.05.08.05a.12.12 0 00.07 0c.03 0 .05-.01.09-.05l45.02-45.02a.2.2 0 00.05-.09.12.12 0 000-.07v-.02a.27.27 0 00-.05-.06L557.25 512l127.87-127.86c.04-.04.05-.06.05-.08a.12.12 0 000-.07c0-.03-.01-.05-.05-.09l-45.02-45.02a.2.2 0 00-.09-.05.12.12 0 00-.07 0z"></path>
                                                                            </svg></span></span></span></span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="expectedLength" class="" title="length">length<span role="img" aria-label="question-circle" title="" class="anticon anticon-question-circle ant-form-item-tooltip"><svg viewBox="64 64 896 896" focusable="false" data-icon="question-circle" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                            <path d="M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64zm0 820c-205.4 0-372-166.6-372-372s166.6-372 372-372 372 166.6 372 372-166.6 372-372 372z"></path>
                                                            <path d="M623.6 316.7C593.6 290.4 554 276 512 276s-81.6 14.5-111.6 40.7C369.2 344 352 380.7 352 420v7.6c0 4.4 3.6 8 8 8h48c4.4 0 8-3.6 8-8V420c0-44.1 43.1-80 96-80s96 35.9 96 80c0 31.1-22 59.6-56.1 72.7-21.2 8.1-39.2 22.3-52.1 40.9-13.1 19-19.9 41.8-19.9 64.9V620c0 4.4 3.6 8 8 8h48c4.4 0 8-3.6 8-8v-22.7a48.3 48.3 0 0130.9-44.8c59-22.7 97.1-74.7 97.1-132.5.1-39.3-17.1-76-48.3-103.3zM472 732a40 40 0 1080 0 40 40 0 10-80 0z"></path>
                                                            </svg></span></label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content">
                                                            <div class="ant-select ant-select-lg ant-select-in-form-item select_border_radius---DfH7 undefined ant-select-single ant-select-show-arrow">
                                                                <div class="ant-select-selector"><span class="ant-select-selection-search"><input type="search" id="expectedLength" autocomplete="off" class="ant-select-selection-search-input" role="combobox" aria-haspopup="listbox" aria-owns="expectedLength_list" aria-autocomplete="list" aria-controls="expectedLength_list" aria-activedescendant="expectedLength_list_0" readonly="" unselectable="on" value="" style="opacity: 0;"></span><span class="ant-select-selection-item" title="Select">Select</span></div><span class="ant-select-arrow" unselectable="on" aria-hidden="true" style="user-select: none;"><span role="img" aria-label="down" class="anticon anticon-down ant-select-suffix"><svg viewBox="64 64 896 896" focusable="false" data-icon="down" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                                        <path d="M884 256h-75c-5.1 0-9.9 2.5-12.9 6.6L512 654.2 227.9 262.6c-3-4.1-7.8-6.6-12.9-6.6h-75c-6.5 0-10.3 7.4-6.5 12.7l352.6 486.1c12.8 17.6 39 17.6 51.7 0l352.6-486.1c3.9-5.3.1-12.7-6.4-12.7z"></path>
                                                                        </svg></span></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="contestId" class="" title="writing contest">writing contest</label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content">
                                                            <div class="ant-select ant-select-lg ant-select-in-form-item select_border_radius---DfH7 undefined ant-select-single ant-select-show-arrow">
                                                                <div class="ant-select-selector"><span class="ant-select-selection-search"><input type="search" id="contestId" autocomplete="off" class="ant-select-selection-search-input" role="combobox" aria-haspopup="listbox" aria-owns="contestId_list" aria-autocomplete="list" aria-controls="contestId_list" aria-activedescendant="contestId_list_0" readonly="" unselectable="on" value="" style="opacity: 0;"></span><span class="ant-select-selection-item" title="Select">Select</span></div><span class="ant-select-arrow" unselectable="on" aria-hidden="true" style="user-select: none;"><span role="img" aria-label="down" class="anticon anticon-down ant-select-suffix"><svg viewBox="64 64 896 896" focusable="false" data-icon="down" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                                        <path d="M884 256h-75c-5.1 0-9.9 2.5-12.9 6.6L512 654.2 227.9 262.6c-3-4.1-7.8-6.6-12.9-6.6h-75c-6.5 0-10.3 7.4-6.5 12.7l352.6 486.1c12.8 17.6 39 17.6 51.7 0l352.6-486.1c3.9-5.3.1-12.7-6.4-12.7z"></path>
                                                                        </svg></span></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="ageGroup" class="" title="warning notice">warning notice</label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content">
                                                            <div class="ant-select ant-select-lg ant-select-in-form-item select_border_radius---DfH7 undefined ant-select-single ant-select-show-arrow">
                                                                <div class="ant-select-selector"><span class="ant-select-selection-search"><input type="search" id="ageGroup" autocomplete="off" class="ant-select-selection-search-input" role="combobox" aria-haspopup="listbox" aria-owns="ageGroup_list" aria-autocomplete="list" aria-controls="ageGroup_list" aria-activedescendant="ageGroup_list_0" readonly="" unselectable="on" value="" style="opacity: 0;"></span><span class="ant-select-selection-item" title="Select">Select</span></div><span class="ant-select-arrow" unselectable="on" aria-hidden="true" style="user-select: none;"><span role="img" aria-label="down" class="anticon anticon-down ant-select-suffix"><svg viewBox="64 64 896 896" focusable="false" data-icon="down" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                                        <path d="M884 256h-75c-5.1 0-9.9 2.5-12.9 6.6L512 654.2 227.9 262.6c-3-4.1-7.8-6.6-12.9-6.6h-75c-6.5 0-10.3 7.4-6.5 12.7l352.6 486.1c12.8 17.6 39 17.6 51.7 0l352.6-486.1c3.9-5.3.1-12.7-6.4-12.7z"></path>
                                                                        </svg></span></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ant-form-item  item--1wVCg">
                                            <div class="ant-row ant-form-item-row">
                                                <div class="ant-col ant-form-item-label"><label for="invitationCode" class="" title="Invitation code">Invitation code</label></div>
                                                <div class="ant-col ant-form-item-control">
                                                    <div class="ant-form-item-control-input">
                                                        <div class="ant-form-item-control-input-content"><span class="ant-input-affix-wrapper ant-input-affix-wrapper-lg" style="border-radius: 8px;"><input data-report-uiname="invitationCode" placeholder="Optional" id="invitationCode" class="ant-input ant-input-lg" type="text" value=""><span class="ant-input-suffix"><span class="ant-input-clear-icon ant-input-clear-icon-hidden ant-input-clear-icon-has-suffix" role="button" tabindex="-1"><span role="img" aria-label="close-circle" class="anticon anticon-close-circle"><svg fill-rule="evenodd" viewBox="64 64 896 896" focusable="false" data-icon="close-circle" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                                            <path d="M512 64c247.4 0 448 200.6 448 448S759.4 960 512 960 64 759.4 64 512 264.6 64 512 64zm127.98 274.82h-.04l-.08.06L512 466.75 384.14 338.88c-.04-.05-.06-.06-.08-.06a.12.12 0 00-.07 0c-.03 0-.05.01-.09.05l-45.02 45.02a.2.2 0 00-.05.09.12.12 0 000 .07v.02a.27.27 0 00.06.06L466.75 512 338.88 639.86c-.05.04-.06.06-.06.08a.12.12 0 000 .07c0 .03.01.05.05.09l45.02 45.02a.2.2 0 00.09.05.12.12 0 00.07 0c.02 0 .04-.01.08-.05L512 557.25l127.86 127.87c.04.04.06.05.08.05a.12.12 0 00.07 0c.03 0 .05-.01.09-.05l45.02-45.02a.2.2 0 00.05-.09.12.12 0 000-.07v-.02a.27.27 0 00-.05-.06L557.25 512l127.87-127.86c.04-.04.05-.06.05-.08a.12.12 0 000-.07c0-.03-.01-.05-.05-.09l-45.02-45.02a.2.2 0 00-.09-.05.12.12 0 00-.07 0z"></path>
                                                                            </svg></span></span><span role="img" aria-label="loading" class="anticon anticon-loading anticon-spin dn"><svg viewBox="0 0 1024 1024" focusable="false" data-icon="loading" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                                        <path d="M988 548c-19.9 0-36-16.1-36-36 0-59.4-11.6-117-34.6-171.3a440.45 440.45 0 00-94.3-139.9 437.71 437.71 0 00-139.9-94.3C629 83.6 571.4 72 512 72c-19.9 0-36-16.1-36-36s16.1-36 36-36c69.1 0 136.2 13.5 199.3 40.3C772.3 66 827 103 874 150c47 47 83.9 101.8 109.7 162.7 26.7 63.1 40.2 130.2 40.2 199.3.1 19.9-16 36-35.9 36z"></path>
                                                                        </svg></span></span></span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div><button data-report-uiname="postcreate" type="button" class="ant-btn ant-btn-primary ant-btn-lg  button--4vWlZ"><span>create</span></button>
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <script src="js/mynovel/novelform.js"></script>
</html>
