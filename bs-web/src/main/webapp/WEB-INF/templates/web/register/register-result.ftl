<#assign title="${message}">

<@override name="content">
<div class="signup-box visible widget-box no-border">
    <div class="widget-body">
        <div class="widget-main">
            <h4 class="header green lighter bigger">
                <i class="ace-icon fa fa-users blue"></i>
            ${message}
            </h4>

            <div class="row">
                <div class="col-xs-12">
                    <p class="text-success">正在跳转到登录页...<a id="jump-btn" href="${ctx}login"> &raquo; 点此直接进入</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
</@override>
<@override name="script">
<script>
    setTimeout(function() {
        window.location.href= $("#jump-btn").attr('href');
    }, 3000);
</script>
</@override>
<@extends name="../auth-layout.ftl"/>
