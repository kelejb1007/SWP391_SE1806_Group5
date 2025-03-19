/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


/**
 * @fileOverview
 * @author
 * Created: 16-04-07
 */
(function ($) {
    $.fn.extend({
        showTypeList: function () {
            var timer,
                    timer2 = null,
                    $typeList = $("#j-typeList"),
                    $navType = $("#j-navType");
            //移入分类时触发显示列表事件，用户快速划过鼠标时不触发
            $navType.mouseover(function () {
                //鼠标停留200毫秒才会赋值触发事件
                timer = setTimeout(function () {
                    $typeList.show();
                }, 300);
                clearTimeout(timer2);
            });
            $typeList.mouseover(function () {
                clearTimeout(timer2);
                $typeList.show();
            });
            //移开分类和列表时，列表隐藏
            $navType.mouseout(function () {
                clearTimeout(timer);
                timer2 = setTimeout(function () {
                    $typeList.hide();
                }, 200);
            });
        },
        /**
         * 用户信息下拉菜单
         * @method userDropDown
         */
        userDropDown: function () {
            var $userWrap = $("#j-userWrap");
            var $dropDown = $("#j-userDownDrop");
            var timer,
                    timer2 = null;
            $userWrap.mouseover(function () {
                timer = setTimeout(function () {
                    $dropDown.show();
                }, 100);
                clearTimeout(timer2);
            });
            //hover到列表时仍然保持显示123
            $dropDown.mouseover(function () {
                clearTimeout(timer2);
                $dropDown.show();
            });
            //移开作品分类和列表时，列表隐藏
            $userWrap.mouseout(function () {
                clearTimeout(timer);
                timer2 = setTimeout(function () {
                    $dropDown.hide();
                }, 200);
            });
        },
        /**
         * 搜索栏里按回车的跳转逻辑
         * @method enterSearchBox
         */
        enterSearchBox: function () {
            var $searchBox = $("#s-box");
            var newValue = $searchBox.attr("placeholder");
            // 支持enter键搜索
            $searchBox.on("keydown", function (evt) {
                if (evt.keyCode == 13) {
                    //判断值是否是空，是空去取placeholder值后带着值传给搜索页
                    if ($(this).val() == "") {
                        $(this).val($(this).attr("placeholder"));
                    }

                }
            });
        }
    });

})(jQuery);
