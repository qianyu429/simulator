(function (factory) {
    if (typeof define === "function" && define.amd) {
        define(["", "jquery-validator"], factory);
    } else {
        factory(jQuery);
    }
}(function ($) {

    /*
     * Translated default messages for the jQuery validation plugin.
     * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
     */
    $.extend($.validator.messages, {
        required: "必须填写",
        remote: "已使用",
        email: "电子邮件无效",
        url: "网址无效",
        date: "日期无效",
        dateISO: "格式为 (YYYY-MM-DD)",
        number: "数字无效",
        digits: "只可输入数字",
        equalTo: "你的输入不相同",
        extension: "后缀无效",
        maxlength: $.validator.format("最多{0}个字符"),
        minlength: $.validator.format("最少{0}个字符"),
        rangelength: $.validator.format("长度为{0}~{1}"),
        range: $.validator.format("大小为{0}~{1}"),
        max: $.validator.format("请输入不大于{0}的数值"),
        min: $.validator.format("请输入不小于{0}的数值")
    });
}));
