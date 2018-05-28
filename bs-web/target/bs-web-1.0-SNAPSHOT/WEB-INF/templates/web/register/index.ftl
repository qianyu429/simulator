<#assign title="注册">

<@override name="page_main">
<div class="row">
    <div class="col-sm-4 col-sm-offset-4">

        <div class="space-30"></div>

        <div class="position-relative">
            <div id="signup-box" class="signup-box widget-box fa-border">
                <div class="widget-body">
                    <div class="widget-main">
                        <h4 class="header green lighter bigger">
                            <i class="ace-icon fa fa-users blue"></i>
                            新用户注册
                        </h4>

                        <div class="space-6"></div>

                        <form id="register-form" action="${ctx}register" method="post">
                            <fieldset>
                                <label class="block clearfix">
                                    <label>用户名</label>
                                    <span class="block input-icon input-icon-right">
                                        <input type="text" id="username" name="username" class="form-control" placeholder="请输入用户名" />
                                        <i class="ace-icon fa fa-user"></i>
                                    </span>
                                </label>

                                <label class="block clearfix">
                                    <label>密码</label>
                                    <span class="block input-icon input-icon-right">
                                        <input type="password" id="password" name="password" class="form-control" placeholder="请输入密码" />
                                        <i class="ace-icon fa fa-lock"></i>
                                    </span>
                                </label>

                                <label class="block clearfix">
                                    <label>确认密码</label>
                                    <span class="block input-icon input-icon-right">
                                        <input type="password" id="repassword" class="form-control" placeholder="请重复输入密码" />
                                        <i class="ace-icon fa fa-retweet"></i>
                                    </span>
                                </label>

                                <label class="block clearfix">
                                    <label>电子邮箱<span class="red">(可用于找回密码)</span></label>
                                    <span class="block input-icon input-icon-right">
                                        <input type="email" id="email" name="email" class="form-control" placeholder="请输入电子邮箱" />
                                        <i class="ace-icon fa fa-envelope"></i>
                                    </span>
                                </label>

                                <label class="block">
                                    <input type="checkbox" id="accept" value="1" class="ace" />
                                        <span class="lbl">
                                            我接受并阅读
                                            <a href="${ctx}register/protocol" target="_blank">注册协议</a>
                                        </span>
                                </label>
                                <div class="red" id="err-msg">${message!''}</div>
                                <div class="space-24"></div>

                                <div class="clearfix">
                                    <button type="reset" class="width-30 pull-left btn btn-sm">
                                        <i class="ace-icon fa fa-refresh"></i>
                                        <span class="bigger-110">清空</span>
                                    </button>

                                    <button id="register" type="button" class="width-65 pull-right btn btn-sm btn-success">
                                        <span class="bigger-110">注册</span>

                                        <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                                    </button>
                                </div>
                            </fieldset>
                        </form>
                    </div>

                    <div class="toolbar center">
                        <a href="${ctx}login" data-target="#login-box" class="back-to-login-link">
                            <i class="ace-icon fa fa-arrow-left"></i>
                            已有账号?去登录
                        </a>
                    </div>
                </div><!-- /.widget-body -->
            </div><!-- /.signup-box -->
        </div>
    </div>
</div>
</@override>

<@override name="page_script">
<script src="${ctx}static/app/js/web/register/index.js"></script>
</@override>

<@extends name="../../layout.ftl"/>
