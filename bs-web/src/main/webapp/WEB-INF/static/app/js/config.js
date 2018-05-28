$(function () {

    /**
     * 加载皮肤
     */
    $.get(ctx + "config/skin", function (data, status) {
        if (status == "success") {
            $("#bodySkin").attr("class", data.k);
            $(".dropdown-caret li a").removeClass("selected");
            $(".dropdown-caret li a[data-color='" + data.val + "']").addClass("selected");
            $(".btn-colorpicker").css("background-color", data.val);
        } else {
            $("#bodySkin").attr("class", "no-skin");
        }
    });

    /**
     * 加载其他配置
     */
    $.get(ctx + "config", function (data, status) {
        if (status == "success") {
            for (var i = 0; i < data.length; i++) {
                var config = data[i];
                var key = config.k;
                var val = config.val;
                if (val == "1") {
                    $(".ace-settings-item input[name='" + key + "']").prop("checked", true);
                    if (key == "navbar") {
                        $("#navbar").addClass("navbar-fixed-top");
                    } else if (key == "sidebar") {
                        $("#sidebar").addClass("sidebar-fixed");
                    } else if (key == "breadcrumbs") {
                        $("#breadcrumbs").addClass("breadcrumbs-fixed");
                    } else if (key == "container") {
                        $("#main-container").addClass("container");
                    } else if (key == "hover") {
                        $(".nav li").addClass("hover");
                    } else if (key == "highlight") {
                        $(".nav > li").addClass("highlight");
                    } else if (key == "compact") {
                        $("#sidebar").addClass("compact");
                    }
                } else {
                    $(".ace-settings-item input[name='" + key + "']").prop("checked", false);
                }
            }
        }
    });

    /**
     * 更新皮肤
     */
    $(".colorpick-btn").click(function () {
        var $this = $(this);
        var $parent = $this.parents("ul.dropdown-caret");
        $parent.children().children().removeClass("selected");
        $this.addClass("selected");

        var color = $this.data("color");
        var $option = $("#skin-colorpicker option[value='" + color + "']");
        var skinClass = $option.data("skin");

        $.post(ctx + "config/skin", {"k": skinClass, "val": color});
    });

    /**
     * 更新其他配置
     */
    $(".ace-settings-item input").click(function () {
        var name = $(this).attr("name");
        var val = $(".ace-settings-item input[name='" + name + "']:checked").val();
        val = val ? val : 0;
        $.get(ctx + "config/" + name + "?val=" + val);
        if (name == "navbar" && val == 0) {
            $.get(ctx + "config/sidebar?val=0");
            $.get(ctx + "config/breadcrumbs?val=0");
        } else if (name == "sidebar" && val == 1) {
            $.get(ctx + "config/navbar?val=1");
        } else if (name == "breadcrumbs" && val == 1) {
            $.get(ctx + "config/navbar?val=1");
            $.get(ctx + "config/sidebar?val=1");
        } else if (name == "compact" && val == 1) {
            $.get(ctx + "config/hover?val=1");
        } else if (name == "hover" && val == 0) {
            $.get(ctx + "config/compact?val=0");
        }
    });
});
