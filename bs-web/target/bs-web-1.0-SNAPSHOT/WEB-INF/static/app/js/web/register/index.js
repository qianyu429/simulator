$(function () {

    $("#username").focus(function() {
        $("#err-msg").hide();
    });

    $("#register").click(function() {
        var username = $("#username").val();
        var password = $("#password").val();
        var repassword = $("#repassword").val();
        var email = $("#email").val();
        var accept = $("#accept:checked").val();

        if (!username) {
            Notify.error("请输入用户名");
            return false;
        }
        if (!password) {
            Notify.error("请输入密码");
            return false;
        }
        if (!repassword) {
            Notify.error("请输入确认密码");
            return false;
        }
        if (repassword != password) {
            Notify.error("两次密码不一致");
            return false;
        }
        if (!email) {
            Notify.error("请输入电子邮箱");
            return false;
        }
        if (!accept || accept != 1) {
            Notify.error("请阅读并同意注册协议");
            return false;
        }
        $("#register-form").submit();
    });
});