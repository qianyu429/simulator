$(function () {
    $("#login").click(function() {
        var username = $("#username").val();
        var password = $("#password").val();

        if (!username) {
            Notify.error("请输入用户名");
            return false;
        }
        if (!password) {
            Notify.error("请输入密码");
            return false;
        }
        $("#login-form").submit();
    });
});