<%-- 
    Document   : myNovels2
    Created on : Mar 16, 2025, 5:50:44 PM
    Author     : Nguyen Thanh Trung
--%>

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
                                                                    <div class="slick-track" style="opacity: 1; transform: translate3d(-272px, 0px, 0px); width: 2448px;">
                                                                        <div data-index="-1" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://wsa.webnovel.com/" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="622" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17350312801341.jpg" alt="WebNovel Spirity Awards 2025">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">WebNovel Spirity Awards 2025</div>
                                                                                    <div class="c_xs t_body_medium mt8">in 6 months</div>
                                                                                </a></div>
                                                                        </div>
                                                                        <div data-index="0" class="slick-slide slick-active slick-current" tabindex="-1" aria-hidden="false" style="outline: none; width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://activity.webnovel.com/noah/217839622?nestedTab=" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="628" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17409826614055.png" alt="Cupids Quill MAR 2025">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">Cupids Quill MAR 2025</div>
                                                                                    <div class="c_xs t_body_medium mt8">in a month</div>
                                                                                </a></div>
                                                                        </div>
                                                                        <div data-index="1" class="slick-slide" tabindex="-1" aria-hidden="true" style="outline: none; width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://inkstone.webnovel.com/academy/article/764864518404112384" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="627" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17409716283282.jpg" alt="Weekly Love Tales: Swapped">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">Weekly Love Tales: Swapped</div>
                                                                                    <div class="c_xs t_body_medium mt8">in 17 hours</div>
                                                                                </a></div>
                                                                        </div>
                                                                        <div data-index="2" class="slick-slide" tabindex="-1" aria-hidden="true" style="outline: none; width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://activity.webnovel.com/noah/919237452?nestedTab=" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="626" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17406441162119.jpg" alt="WPC MAR 2025">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">WPC MAR 2025</div>
                                                                                    <div class="c_xs t_body_medium mt8">in a month</div>
                                                                                </a></div>
                                                                        </div>
                                                                        <div data-index="3" class="slick-slide" tabindex="-1" aria-hidden="true" style="outline: none; width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://wsa.webnovel.com/" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="622" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17350312801341.jpg" alt="WebNovel Spirity Awards 2025">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">WebNovel Spirity Awards 2025</div>
                                                                                    <div class="c_xs t_body_medium mt8">in 6 months</div>
                                                                                </a></div>
                                                                        </div>
                                                                        <div data-index="4" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://activity.webnovel.com/noah/217839622?nestedTab=" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="628" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17409826614055.png" alt="Cupids Quill MAR 2025">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">Cupids Quill MAR 2025</div>
                                                                                    <div class="c_xs t_body_medium mt8">in a month</div>
                                                                                </a></div>
                                                                        </div>
                                                                        <div data-index="5" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://inkstone.webnovel.com/academy/article/764864518404112384" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="627" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17409716283282.jpg" alt="Weekly Love Tales: Swapped">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">Weekly Love Tales: Swapped</div>
                                                                                    <div class="c_xs t_body_medium mt8">in 17 hours</div>
                                                                                </a></div>
                                                                        </div>
                                                                        <div data-index="6" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://activity.webnovel.com/noah/919237452?nestedTab=" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="626" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17406441162119.jpg" alt="WPC MAR 2025">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">WPC MAR 2025</div>
                                                                                    <div class="c_xs t_body_medium mt8">in a month</div>
                                                                                </a></div>
                                                                        </div>
                                                                        <div data-index="7" tabindex="-1" class="slick-slide slick-cloned" aria-hidden="true" style="width: 272px;">
                                                                            <div><a class="db pr ad_item--5jdx5" href="https://wsa.webnovel.com/" target="_blank" rel="noreferrer" data-report-pn="dashboard_novels" data-report-uiname="articleactivity" data-report-dt="contentid" data-report-did="622" tabindex="-1" style="width: 100%; display: inline-block;"><img class="ad_img--0HZC5" src="https://cc-cdnintserviceimg.webnovel.com/article/17350312801341.jpg" alt="WebNovel Spirity Awards 2025">
                                                                                    <div class="df jcsb aic mb6 fww"><span class="t_label_small ell ad_tag--1lsbq">Writing contests</span></div>
                                                                                    <div class="c_m t_title_medium title_ell--afTVl">WebNovel Spirity Awards 2025</div>
                                                                                    <div class="c_xs t_body_medium mt8">in 6 months</div>
                                                                                </a></div>
                                                                        </div>
                                                                    </div>
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
                                        <div class="pr oh mb20 wall pt24 pr24 pl24 pb24">
                                            <div class="h100p auto df aic jcc fdc fs16 lh24 tac c_s" style="width: 26em; max-width: 90%;">
                                                <div><img class="mb16" src="data:image/png;base64,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" width="51" height="64" alt="No book yet!">
                                                    <h3 class="fs18 c_m mb4 fw600 ttc">No Books Yet!</h3>
                                                    <div>
                                                        <p> <strong class="fw500 c_danger mr4" id="authorNum">59,034</strong> authors are writing here!</p>
                                                        <p>We will help your novels reach out <strong class="fw500 c_danger ml4 mr4" id="readerNum">500,000</strong> readers.</p>
                                                    </div><button data-report-pn="dashboard_novels" data-report-uiname="create" type="button" class="ant-btn ant-btn-primary ant-btn-sm  button--4vWlZ"><span>create new</span></button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="wall extra_box--bnd+c">
                                            <div class="ant-tabs ant-tabs-top tab--vEz5t ">
                                                <div role="tablist" class="ant-tabs-nav">
                                                    <div class="ant-tabs-nav-wrap">
                                                        <div class="ant-tabs-nav-list" style="transform: translate(0px, 0px);">
                                                            <div data-node-key="News" class="ant-tabs-tab ant-tabs-tab-active">
                                                                <div role="tab" aria-selected="true" class="ant-tabs-tab-btn" tabindex="0" id="rc-tabs-0-tab-News" aria-controls="rc-tabs-0-panel-News">News</div>
                                                            </div>
                                                            <div data-node-key="Inbox" class="ant-tabs-tab">
                                                                <div role="tab" aria-selected="false" class="ant-tabs-tab-btn" tabindex="0" id="rc-tabs-0-tab-Inbox" aria-controls="rc-tabs-0-panel-Inbox">Inbox</div>
                                                            </div>
                                                            <div class="ant-tabs-ink-bar ant-tabs-ink-bar-animated" style="left: 0px; width: 33px;"></div>
                                                        </div>
                                                    </div>
                                                    <div class="ant-tabs-nav-operations ant-tabs-nav-operations-hidden"><button type="button" class="ant-tabs-nav-more" tabindex="-1" aria-hidden="true" aria-haspopup="listbox" aria-controls="rc-tabs-0-more-popup" id="rc-tabs-0-more" aria-expanded="false" style="visibility: hidden; order: 1;"><span role="img" aria-label="ellipsis" class="anticon anticon-ellipsis"><svg viewBox="64 64 896 896" focusable="false" data-icon="ellipsis" width="1em" height="1em" fill="currentColor" aria-hidden="true">
                                                                <path d="M176 511a56 56 0 10112 0 56 56 0 10-112 0zm280 0a56 56 0 10112 0 56 56 0 10-112 0zm280 0a56 56 0 10112 0 56 56 0 10-112 0z"></path>
                                                                </svg></span></button></div>
                                                    <div class="ant-tabs-extra-content"><a title="news" class="t_label_medium see_more--4GGtX" data-report-pn="dashboard_novels" data-report-col="newsblock" data-report-uiname="more" href="/news/index">SEE ALL <span role="img" class="anticon"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                                                <use xlink:href="#i-chevron-right"></use>
                                                                </svg></span></a></div>
                                                </div>
                                                <div class="ant-tabs-content-holder">
                                                    <div class="ant-tabs-content ant-tabs-content-top">
                                                        <div id="rc-tabs-0-panel-News" role="tabpanel" tabindex="0" aria-labelledby="rc-tabs-0-tab-News" aria-hidden="false" class="ant-tabs-tabpane ant-tabs-tabpane-active">
                                                            <div class="pr df fdc jcc f1 tabs_item--i5q3h">
                                                                <div class="df jcsb aic"><span class="t_label_large c_m" style="width: 70%;">Plagiarism &amp; Use of AI &amp; Abusing MGS/win-win</span><span class="t_body_small c_s">14 Mar 2025</span></div><a title="Plagiarism &amp; Use of AI &amp; Abusing MGS/win-win" class="pa w100p h100p l0 t0" data-report-pn="dashboard_novels" data-report-col="newsblock" data-report-uiname="news" data-report-dt="newid" data-report-did="86410727945992832" href="/news/detail/nid/86410727945992832"><span class="dn">Plagiarism &amp; Use of AI &amp; Abusing MGS/win-win</span></a>
                                                            </div>
                                                            <div class="pr df fdc jcc f1 tabs_item--i5q3h">
                                                                <div class="df jcsb aic"><span class="t_label_large c_m" style="width: 70%;">Notes in Inkstone and WebNovel app Available</span><span class="t_body_small c_s">09 Jan 2025</span></div><a title="Notes in Inkstone and WebNovel app Available" class="pa w100p h100p l0 t0" data-report-pn="dashboard_novels" data-report-col="newsblock" data-report-uiname="news" data-report-dt="newid" data-report-did="84919274648436573" href="/news/detail/nid/84919274648436573"><span class="dn">Notes in Inkstone and WebNovel app Available</span></a>
                                                            </div>
                                                            <div class="pr df fdc jcc f1 tabs_item--i5q3h">
                                                                <div class="df jcsb aic"><span class="t_label_large c_m" style="width: 70%;">These AI-created works are not welcomed!</span><span class="t_body_small c_s">22 Oct 2024</span></div><a title="These AI-created works are not welcomed!" class="pa w100p h100p l0 t0" data-report-pn="dashboard_novels" data-report-col="newsblock" data-report-uiname="news" data-report-dt="newid" data-report-did="83091961753435522" href="/news/detail/nid/83091961753435522"><span class="dn">These AI-created works are not welcomed!</span></a>
                                                            </div>
                                                            <div class="pr df fdc jcc f1 tabs_item--i5q3h">
                                                                <div class="df jcsb aic"><span class="t_label_large c_m" style="width: 70%;">"Free""99% off","Hot" labels are not allowed on book covers</span><span class="t_body_small c_s">08 Aug 2024</span></div><a title="&quot;Free&quot;&quot;99% off&quot;,&quot;Hot&quot; labels are not allowed on book covers" class="pa w100p h100p l0 t0" data-report-pn="dashboard_novels" data-report-col="newsblock" data-report-uiname="news" data-report-dt="newid" data-report-did="81347810246586267" href="/news/detail/nid/81347810246586267"><span class="dn">"Free""99% off","Hot" labels are not allowed on book covers</span></a>
                                                            </div>
                                                            <div class="pr df fdc jcc f1 tabs_item--i5q3h">
                                                                <div class="df jcsb aic"><span class="t_label_large c_m" style="width: 70%;">Progress in the Fight Against Piracy</span><span class="t_body_small c_s">24 Jul 2024</span></div><a title="Progress in the Fight Against Piracy" class="pa w100p h100p l0 t0" data-report-pn="dashboard_novels" data-report-col="newsblock" data-report-uiname="news" data-report-dt="newid" data-report-did="81003186365720025" href="/news/detail/nid/81003186365720025"><span class="dn">Progress in the Fight Against Piracy</span></a>
                                                            </div>
                                                            <div class="pr df fdc jcc f1 tabs_item--i5q3h">
                                                                <div class="df jcsb aic"><span class="t_label_large c_m" style="width: 70%;">Premium will get passed automatically</span><span class="t_body_small c_s">03 Jul 2024</span></div><a title="Premium will get passed automatically" class="pa w100p h100p l0 t0" data-report-pn="dashboard_novels" data-report-col="newsblock" data-report-uiname="news" data-report-dt="newid" data-report-did="80515535560827209" href="/news/detail/nid/80515535560827209"><span class="dn">Premium will get passed automatically</span></a>
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
                </div>
            </div>
        </div>
    </div>
</body>

</html>
