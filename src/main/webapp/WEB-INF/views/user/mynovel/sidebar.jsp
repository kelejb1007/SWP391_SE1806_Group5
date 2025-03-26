<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="g_side_wrap">
    <div class="f00a pr g_pc_warp">
        <div class="g_sd"><a class="g_logo" href="mynovel" title="Inkstone">
                <h1><img src="img/b3.png" alt="inkstone"></h1>

                <!--  thu gọn sidebar                          -->
            </a><label for="sideSwitch" class="g_sd_close i_close g_sd_close_icon"><i></i></label>

            <div class="g_nav">
                <h3>main</h3>


                <!-- List-------------------------- -->
                <ul class="g_nav_list">
                    <li class=""><a class="g_sd_option" title="Dashboard" href="mynovel"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                <use xlink:href="#i-dashboard"></use>
                                </svg></span><strong class="t_body_large ml16">Dashboard</strong></a><span class="g_side_tooltips t_label_medium">Dashboard</span></li>


                    <li class=" "><a class="g_sd_option   " title="My Novels" href="mynovel?action=mynovel"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                <use xlink:href="#i-book-3d"></use>
                                </svg></span><strong class="t_body_large ml16">My Novels</strong></a><span class="g_side_tooltips t_label_medium">My Novels</span></li>


                    <li class=" "><a class="g_sd_option   " title="Create novel" href="mynovel?action=post"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                <use xlink:href="#i-add"></use>
                                </svg></span><strong class="t_body_large ml16">Create novel</strong></a><span class="g_side_tooltips t_label_medium">Create novel</span></li>




                    <li class=" ">
                        <div class="g_sd_option" title="Submisssion"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                <use xlink:href="#i-preview"></use>
                                </svg></span><strong class="t_body_large ml16">Submisssion</strong><span role="img" class="anticon g_sd_i_sub"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                <use xlink:href="#i-chevron-right"></use>
                                </svg></span></div>

                        <ul class="g_nav_sub g_extra_workspace"><span class="g_nav_sub_title t_label_small">Submisssion</span>
                            <li class=" "><a class="g_sd_sub_option   " title="Novel Submission" href="mynovel?action=viewposthistory"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                        <use xlink:href="#i-book-3d"></use>
                                        </svg></span><strong class="t_label_medium lh24">Novel</strong></a></li>

                            <!-- Chapter Submission ở đây nè -->        
                            <li class=" "><a class="g_sd_sub_option   " title="Chapter Submission" href="mynovel?action=viewchapterposthistory"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                        <use xlink:href="#i-book-3d"></use>
                                        </svg></span><strong class="t_label_medium lh24">Chapter</strong></a></li>
                        </ul>
                    </li>


                    <li class=" "><a class="g_sd_option   " title="Deleted" href="mynovel?action=post"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                <use xlink:href="#i-history"></use>
                                </svg></span><strong class="t_body_large ml16">Deleted</strong></a><span class="g_side_tooltips t_label_medium">Deleted</span></li>


                </ul>
            </div>

            <!-- Account-------------------- -->
            <div class="g_account">
                <ul class="g_nav_list">
                    <li class=" ">
                        <div class="g_sd_option   " title="Profile" style="text-transform: unset;">
                            <div class="_img_container"><img class="g_sd_i" src="<c:if test="${not empty user.imageUML}">${user.imageUML}</c:if> 
                                                             <c:if test="${empty user.imageUML}">//yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/account.1e031.png</c:if>" alt="img"></div>
                                <strong class="t_body_large ml16">Profile</strong><span role="img" class="anticon g_sd_i_sub"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                    <use xlink:href="#i-chevron-right"></use>
                                    </svg></span>
                            </div>
                            <ul class="g_nav_sub g_extra_account"><span class="g_nav_sub_title t_label_small">Profile</span>
                                <li class=" "><a class="g_sd_sub_option   " title="View Profile" href="viewprofile"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                            <use xlink:href="#i-settings"></use>
                                            </svg></span><strong class="t_label_medium lh24">View Profile</strong></a></li>
                                <li class=" ">
                                    <form id="logoutForm" action="<c:url value='/Logout' />" method="post">
                                    <a class="g_sd_sub_option   " title="Sign out" href=""
                                       onclick="document.getElementById('logoutForm').submit(); return false;"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                            <use xlink:href="#i-logout"></use>
                                            </svg></span><strong class="t_label_medium lh24">Sign out</strong></a>
                                </form>
                            </li>
                        </ul>
                    </li>
                    <li class=" "><a class="g_sd_option   " title="Homepage" href="homepage"><span role="img" class="anticon g_sd_i"><svg width="1em" height="1em" fill="currentColor" aria-hidden="true" focusable="false" class="">
                                <div class="_img_container"><img class="g_sd_i" src="//yuxseocdn.yuewen.com/favicon/readnovel.ico" alt="img"></div>
                                </svg></span><strong class="t_body_large ml16">Homepage</strong></a><span class="g_side_tooltips t_label_medium">Homepage</span></li>

                </ul>
            </div>


        </div>
        <div class="g_sd_ph"></div>
    </div>
</div>
