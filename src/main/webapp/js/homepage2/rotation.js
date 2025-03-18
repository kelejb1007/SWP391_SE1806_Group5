/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


(function ($) {
    $.fn.extend({
        yx_rotaion: function (options) {

            //默认参数
            var defaults = {
                /**轮换间隔时间，单位毫秒*/
                during: 3000,
                /**是否显示左右按钮*/
                btn: true,
                /**是否显示焦点按钮*/
                focus: true,
                /**是否显示标题*/
                title: true,
                /**是否自动播放*/
                auto: true
            }

            var options = $.extend(defaults, options);

            return this.each(function () {

                var timer = null;
                var o = options;
                var curr_index = 0;
                var $this = $(this);
                var $li = $this.find("li");
                var li_count = $li.length;

                $this.css({
                    position: 'relative',
                    overflow: 'hidden',
                    width: $li.find("img").width(),
                    height: $li.find("img").height()
                });

                $this.find("li").css({position: 'absolute', left: 0, top: 0}).hide();
                $li.first().show();

                if (!o.btn)
                    $(".yx-rotaion-btn").css({visibility: 'hidden'});

                var $btn = $(".yx-rotaion-btn a"),
                        $title = $(".yx-rotation-t"),
                        $focus = $(".yx-rotation-focus");

                //如果自动播放，设置定时器
                if (o.auto) {
                    var t = setInterval(function () {
                        nextBanner();
                    }, o.during);
                }

                $title.text($li.first().find("img").attr("alt"));
                $title.attr("href", $li.first().find("a").attr("href"));

                var $f = $focus.children("a");
                $f.first().addClass("hover");

                // 鼠标覆盖左右按钮设置透明度
                $btn.hover(function () {
                    $(this).addClass("hover");
                }, function () {
                    $(this).removeClass("hover");
                });

                //鼠标覆盖元素，清除计时器
                $btn.add($li).add($f).hover(function () {
                    if (t)
                        clearInterval(t);
                }, function () {
                    if (o.auto)
                        t = setInterval(function () {
                            nextBanner();
                        }, o.during);
                });

                //鼠标覆盖焦点按钮效果
                $f.bind("mouseover", function () {

                    clearTimeout(timer);
                    var that = $(this);
                    timer = setTimeout(function () {
                        var i = that.index();
                        that.addClass("hover");
                        $focus.children("a").not(that).removeClass("hover");
                        $li.eq(i).fadeIn(300);
                        $li.not($li.eq(i)).stop().fadeOut(300);
                        $title.text($li.eq(i).find("img").attr("alt"));
                        curr_index = i;
                    }, 100);

                });

                //下一张显示
                function nextBanner() {
                    curr_index++;
                    if (curr_index >= li_count)
                        curr_index = 0;
                    if (curr_index < 0)
                        curr_index = li_count - 1;
                    $li.eq(curr_index).fadeIn(300);
                    $li.not($li.eq(curr_index)).stop().fadeOut(300);
                    $f.eq(curr_index).addClass("hover");
                    $f.not($f.eq(curr_index)).removeClass("hover");
                    $title.text($li.eq(curr_index).find("img").attr("alt"));
                    $title.attr("href", $li.eq(curr_index).find("a").attr("href"));
                }


            });
        }
    });

})(jQuery);

