<#assign title="登录">

<@override name="page_main">
<div class="row">
    <div class="col-sm-4 col-sm-offset-4">

        <div class="space-30"></div>

        <div class="space-30"></div>

        <div class="position-relative">
            <div id="login-box" class="login-box visible widget-box fa-border">
                <div class="widget-body">
                    <div class="widget-main" style="width: 70%;margin: 0 auto;">
                        <h4 class="header blue lighter bigger">
                            <i class="ace-icon fa fa-coffee green"></i>
                            登录
                        </h4>

                        <div class="space-14"></div>

                        <form id="login-form" action="${ctx}login" method="post">
                            <fieldset>
                                <label class="block clearfix">
                                    <label>用户名</label>
                                    <span class="block input-icon input-icon-right">
                                        <input type="text" id="username" name="username" class="form-control"
                                               placeholder="请输入用户名">
                                        <i class="ace-icon fa fa-user"></i>
                                    </span>
                                </label>

                                <div class="space-14"></div>

                                <label class="block clearfix">
                                    <label>密码</label>
                                    <span class="block input-icon input-icon-right">
                                        <input type="password" id="password" name="password" class="form-control"
                                               placeholder="请输入密码">
                                        <i class="ace-icon fa fa-lock"></i>
                                    </span>
                                </label>

                                <div class="space-14"></div>

                                <div class="red">
                                ${message!''}
                                </div>

                                <div class="space-10"></div>

                                <div class="clearfix">
                                    <label class="inline">
                                        <input type="checkbox" class="ace">
                                        <span class="lbl"> 记住密码</span>
                                    </label>

                                    <button id="login" type="button" class="width-35 pull-right btn btn-sm btn-primary">
                                        <i class="ace-icon fa fa-key"></i>
                                        <span class="bigger-110">登录</span>
                                    </button>
                                </div>

                                <div class="space-4"></div>
                            </fieldset>
                        </form>
                    </div>

                    <div class="toolbar clearfix">
                        <div>
                            <a href="${ctx}forget" data-target="#forgot-box" class="forgot-password-link">
                                <i class="ace-icon fa fa-arrow-left"></i>
                                忘记密码?
                            </a>
                        </div>

                        <div>
                            <a href="${ctx}register" data-target="#signup-box" class="user-signup-link">
                                注册
                                <i class="ace-icon fa fa-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@override>

<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>

<@extends name="../../layout.ftl"/>
