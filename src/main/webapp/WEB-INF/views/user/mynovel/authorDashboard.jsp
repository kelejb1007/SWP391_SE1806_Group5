<%-- 
    Document   : myNovels2
    Created on : Mar 16, 2025, 5:50:44 PM
    Author     : Nguyen Thanh Trung
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <style data-rc-order="prepend" rc-util-key="@ant-design-icons">
            .anticon {
                display: inline-flex;
                alignItems: center;
                color: inherit;
                font-style: normal;
                line-height: 0;
                text-align: center;
                text-transform: none;
                vertical-align: -0.125em;
                text-rendering: optimizeLegibility;
                -webkit-font-smoothing: antialiased;
                -moz-osx-font-smoothing: grayscale;
            }

            .anticon>* {
                line-height: 1;
            }

            .anticon svg {
                display: inline-block;
            }

            .anticon::before {
                display: none;
            }

            .anticon .anticon-icon {
                display: block;
            }

            .anticon[tabindex] {
                cursor: pointer;
            }

            .anticon-spin::before,
            .anticon-spin {
                display: inline-block;
                -webkit-animation: loadingCircle 1s infinite linear;
                animation: loadingCircle 1s infinite linear;
            }

            @-webkit-keyframes loadingCircle {
                100% {
                    -webkit-transform: rotate(360deg);
                    transform: rotate(360deg);
                }
            }

            @keyframes loadingCircle {
                100% {
                    -webkit-transform: rotate(360deg);
                    transform: rotate(360deg);
                }
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author Dashboard</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="stylesheet" type="text/css" href="css/mynovel/mynovel2.css" crossorigin="anonymous">
        <script charset="utf-8" src="js/mynovel/mynovel2.js" crossorigin="anonymous"></script>

    </head>

    <body>
        <jsp:include page="/WEB-INF/views/user/mynovel/icon.jsp" /> 
        <div id="root">
            <div class="df"><input class="pf t0 l0 vh" id="foldSwitch" type="checkbox"><input class="pf t0 l0 vh" id="sideSwitch" type="checkbox">
                <jsp:include page="/WEB-INF/views/user/mynovel/sidebar.jsp" />


                <div class="g_main_wrap f1 pr">
                    <div style="">
                        <div class="pf t0 l0 header--Unk0j df jcsb">
                            <div class="undefined g_header df fg1 oh">
                                <div class="df aic g_header_title"><label for="foldSwitch" class="collapse_menu--QStMM mr8">
                                        <div class="df g_sd_close collapse_menu_btn--f8c2W"><i></i></div>
                                    </label>
                                    <h2 class="header_title--gwRuS ell dib mw100p t_title_large mb0 vam">Dashboard</h2>

                                </div>
                            </div>
                            <div class="df aic oh"><button type="button" class="ant-btn ant-btn-ghost ant-dropdown-trigger guide_button--wdkUP button--4vWlZ"><span>Support</span><span role="img" class="anticon ml4 fs16 lh24"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                        <use xlink:href="#i-chevron-down"></use>
                                        </svg></span></button></div>
                        </div>
                        <div id="main_scroll_container" class="scroller--dBDRL pr">
                            <div class="header_ph_nav--0QeBW"></div>
                            <div class="main_content--0x57a with_navigator--ZJqIM">
                                <div class="dash-novel-page" id="dash_novel_page">
                                    <div class="mb12 pb8 banner_wrap--EMcr5">
                                        <div class="wall pt20 pb20 pl24 pr24 ad_box--27Sex banner_half--r1Iwf">
                                            <div class="ant-carousel">
                                                <div class="slick-slider slick-initialized" dir="ltr">
                                                    <div class="slick-list">
                                                        <div class="slick-track" style="opacity: 1; transform: translate3d(-972px, 0px, 0px); width: 3564px;">
                                                            <div data-index="-1" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/287439652" target="_blank" data-report-pos="4" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/287439652" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/16974606013813.png" alt="Webnovel Content Creation Guideline">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">Webnovel Content Creation Guideline</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">These guidelines provide clear boundaries and subsequent measures for controversial content in novels.</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="0" class="slick-slide" tabindex="-1" aria-hidden="true" style="outline: none; width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/386379212?tabIndex=0" target="_blank" data-report-pos="0" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/386379212?tabIndex=0" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/17419448981672.jpg" alt="WSA Quarter 1 Outstanding Works Showcase">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">WSA Quarter 1 Outstanding Works Showcase</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">With features and cashes!</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="1" class="slick-slide" tabindex="-1" aria-hidden="true" style="outline: none; width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://docs.google.com/spreadsheets/d/1lAtqDPqpTdDUpQn5G4t666SMpZezW935JfVPutQTrl0/edit?usp=sharing" target="_blank" data-report-pos="1" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://docs.google.com/spreadsheets/d/1lAtqDPqpTdDUpQn5G4t666SMpZezW935JfVPutQTrl0/edit?usp=sharing" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/17396912182911.png" alt="Winter Sprinting Party Starts!">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">Winter Sprinting Party Starts!</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">Start from February, Update for Features and LOAs!</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="2" class="slick-slide slick-active slick-current" tabindex="-1" aria-hidden="false" style="outline: none; width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/883909518" target="_blank" data-report-pos="2" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/883909518" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/17315726841302.png" alt="Join WN AE team and get generous bonuses！">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">Join WN AE team and get generous bonuses！</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">Help discover new talented authors and earn rewards!</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="3" class="slick-slide" tabindex="-1" aria-hidden="true" style="outline: none; width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/844556559" target="_blank" data-report-pos="3" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/844556559" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/17085748556961.png" alt="WebNovel Author Benefits &amp; Policies">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">WebNovel Author Benefits &amp; Policies</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">Why should you choose WebNovel as your creation starting line?</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="4" class="slick-slide" tabindex="-1" aria-hidden="true" style="outline: none; width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/287439652" target="_blank" data-report-pos="4" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/287439652" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/16974606013813.png" alt="Webnovel Content Creation Guideline">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">Webnovel Content Creation Guideline</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">These guidelines provide clear boundaries and subsequent measures for controversial content in novels.</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="5" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/386379212?tabIndex=0" target="_blank" data-report-pos="0" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/386379212?tabIndex=0" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/17419448981672.jpg" alt="WSA Quarter 1 Outstanding Works Showcase">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">WSA Quarter 1 Outstanding Works Showcase</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">With features and cashes!</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="6" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://docs.google.com/spreadsheets/d/1lAtqDPqpTdDUpQn5G4t666SMpZezW935JfVPutQTrl0/edit?usp=sharing" target="_blank" data-report-pos="1" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://docs.google.com/spreadsheets/d/1lAtqDPqpTdDUpQn5G4t666SMpZezW935JfVPutQTrl0/edit?usp=sharing" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/17396912182911.png" alt="Winter Sprinting Party Starts!">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">Winter Sprinting Party Starts!</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">Start from February, Update for Features and LOAs!</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="7" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/883909518" target="_blank" data-report-pos="2" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/883909518" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/17315726841302.png" alt="Join WN AE team and get generous bonuses！">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">Join WN AE team and get generous bonuses！</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">Help discover new talented authors and earn rewards!</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="8" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/844556559" target="_blank" data-report-pos="3" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/844556559" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/17085748556961.png" alt="WebNovel Author Benefits &amp; Policies">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">WebNovel Author Benefits &amp; Policies</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">Why should you choose WebNovel as your creation starting line?</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                            <div data-index="9" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 324px;">
                                                                <div><a class="oh ad_item--0ubkX" href="https://activity.webnovel.com/noah/287439652" target="_blank" data-report-pos="4" data-report-pn="dashboard_novels" data-report-uiname="banner" data-report-dt="linkurl" data-report-did="https://activity.webnovel.com/noah/287439652" tabindex="-1" style="width: 100%; display: inline-block;">
                                                                        <div class="df fdr"><img class="ad_img--I3ePc mr16" src="https://cc-cdnintserviceimg.webnovel.com/om_images/16974606013813.png" alt="Webnovel Content Creation Guideline">
                                                                            <div class="f1"><strong class="c_m t_title_medium title_ell--e3Mmw">Webnovel Content Creation Guideline</strong>
                                                                                <p class="c_xs t_body_medium mt8 content_ell--8e-kZ">These guidelines provide clear boundaries and subsequent measures for controversial content in novels.</p>
                                                                            </div>
                                                                        </div>
                                                                    </a></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <ul class="slick-dots slick-dots-bottom" style="display: block;">
                                                        <li class=""><button>1</button></li>
                                                        <li class=""><button>2</button></li>
                                                        <li class="slick-active"><button>3</button></li>
                                                        <li class=""><button>4</button></li>
                                                        <li class=""><button>5</button></li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="wall pt20 pb20 pl24 pr24 banner_half--r1Iwf container--u9T1w">
                                            <div class="ant-spin-nested-loading">
                                                <div class="ant-spin-container">
                                                    <div class="df jcsb">
                                                        <div class="carousel_container--W6X4F">
                                                            <div class="ant-carousel">
                                                                <div class="slick-slider slick-initialized" dir="ltr">
                                                                    <div class="slick-list">

                                                                    </div>
                                                                    <ul class="slick-dots slick-dots-bottom" style="display: block;">
                                                                        <li class="slick-active"><button>1</button></li>
                                                                        <li class=""><button>2</button></li>
                                                                        <li class=""><button>3</button></li>
                                                                        <li class=""><button>4</button></li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div><a class="t_label_medium ad_link--W58fo" data-report-pn="dashboard_novels" data-report-uiname="morearticleactivity" href="/essaylist">SEE ALL <span role="img" class="anticon"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                                <use xlink:href="#i-chevron-right"></use>
                                                                </svg></span></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="ant-spin-nested-loading">
                                        <div class="ant-spin-container">

                                            <div class="wall extra_box--bnd+c">
                                                <div class="ant-tabs ant-tabs-top tab--vEz5t ">

                                                    <c:if test="${not empty listNovel}">
                                                        <div id="page1" class="page"style="display: block">
                                                            <c:forEach var="c" items="${requestScope.listNovel}" >
                                                                <li class="pr col-md-2 list-item ">
                                                                    <div class="book"> 
                                                                        <a href="mynovel?action=viewdetail&novelID=${c.novelID}" title="${c.novelName}"> 
                                                                            <i class="detail">
                                                                                <!-- dung canvas API -->
                                                                                <img cross-origin="anonymous"  
                                                                                     data-original="${c.imageURL}" 
                                                                                     width="140" 
                                                                                     height="186" 
                                                                                     alt="${c.novelName}" 
                                                                                     src="${c.imageURL}" 
                                                                                     style="display: block;"> 


                                                                                <span class="_tag_sub" 
                                                                                      <c:if test="${c.novelStatus == 'inactive'}">
                                                                                    style="background-color: red"
                                                                                    </c:if>>${c.novelStatus}
                                                                                </span> 
                                                                                <span class="_tag_sub">
                                                                                    ACTIVE
                                                                                </span>  
                                                                            </i> 
                                                                            <h3>${c.novelName}</h3> 
                                                                        </a> 
                                                                        <strong style="margin: 0" class="sub"><i class="fa fa-file-text fa-fw"></i> ${c.totalChapter} Chapters</strong> 




                                                                        <ul class="novel_nav_sub">
                                                                            <span class="title">

                                                                            </span>

                                                                            <li class="list2">
                                                                                <a class="g_sd_sub_option" href="mynovel?action=update&novelID=${c.novelID}">
                                                                                    <strong class="str">Update</strong>
                                                                                </a>
                                                                            </li>

                                                                            <li class="list2">
                                                                                <form action="mynovel" method="post" style="display: inline">
                                                                                    <input type="hidden" name="action" value="delete">
                                                                                    <input type="hidden" name="novelID" value="${c.novelID}">
                                                                                    <button type="submit" class="g_sd_sub_option" onclick="return confirm('Are you sure to delete the novel: ${c.novelName} (ID=${c.novelID})')">
                                                                                        <strong class="str">Delete</strong>
                                                                                    </button>
                                                                                </form>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                            </c:forEach>
                                                        </div>

                                                        <div id="page2" class="page"style="display: none">
                                                            <div class="table-responsive">
                                                                <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>#</th>
                                                                            <th>Novel Name</th>
                                                                            <th>Total of chapter</th>
                                                                            <th>Genres</th>
                                                                            <th>Published Date</th>
                                                                            <th>Rating Average</th>
                                                                            <th>View</th>
                                                                            <th>Action</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:forEach var="c" items="${requestScope.listNovel}" varStatus="status">
                                                                        <tr>
                                                                            <td>${status.index + 1}</td>
                                                                            <td><a href="mynovel?action=viewdetail&novelID=${c.novelID}" title="${c.novelName}">${c.novelName}</a></td>
                                                                            <td>${c.totalChapter}</td>
                                                                            <td>${c.genres}</td>
                                                                            <td>
                                                                        <fmt:formatDate value="${c.publishDate2}" pattern="yyyy-MM-dd HH:mm:ss" />
                                                                        </td>
                                                                        <td>${c.averageRating}</td>
                                                                        <td>${c.viewCount}</td>
                                                                        <td>
                                                                            <a href="mynovel?action=update&novelID=${c.novelID}">Update</a>
                                                                            <form action="mynovel" method="post" style="display: inline;">
                                                                                <input type="hidden" name="action" value="delete">
                                                                                <input type="hidden" name="novelID" value="${c.novelID}">
                                                                                <button style="" type="submit" class="tablebtn" onclick="return confirm('Are you sure to delete the novel: ${c.novelName} (ID=${c.novelID})')">
                                                                                    <strong class="str">Delete</strong>
                                                                                </button>
                                                                            </form>
                                                                        </td>
                                                                        </tr>
                                                                    </c:forEach>  
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </c:if>
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
    </body>

</html>
