<#assign bodyClass="skin-2">
<#assign title="找回密码">

<@override name="page_main">
<div class="row">
    <div class="col-sm-4 col-sm-offset-4">

        <div class="space-30"></div>

        <div class="space-30"></div>

        <div class="position-relative">
            <div id="forgot-box" class="forgot-box widget-box fa-border">
                <div class="widget-body">
                    <div class="widget-main">
                        <h4 class="header red lighter bigger">
                            <i class="ace-icon fa fa-key"></i>
                            找回密码
                        </h4>

                        <div class="space-14"></div>

                        <p>
                            输入电子邮箱找回密码
                        </p>

                        <form>
                            <fieldset>
                                <label class="block clearfix">
                                    <span class="block input-icon input-icon-right">
                                        <input type="email" class="form-control" placeholder="请输入电子邮箱" />
                                        <i class="ace-icon fa fa-envelope"></i>
                                    </span>
                                </label>

                                <div class="space-14"></div>

                                <div class="clearfix">
                                    <button id="send" type="button" class="width-35 pull-right btn btn-sm btn-danger">
                                        <i class="ace-icon fa fa-lightbulb-o"></i>
                                        <span class="bigger-110">发送</span>
                                    </button>
                                </div>

                                <div class="space-14"></div>

                            </fieldset>
                        </form>
                    </div><!-- /.widget-main -->

                    <div class="toolbar center">
                        <a href="${ctx}login" data-target="#login-box" class="back-to-login-link">
                            去登录
                            <i class="ace-icon fa fa-arrow-right"></i>
                        </a>
                    </div>
                </div><!-- /.widget-body -->
            </div><!-- /.forgot-box -->
        </div>
    </div>
</div>
</@override>

<@override name="page_script">
<script src="${ctx}static/app/js/web/login/forget.js"></script>
</@override>

<@extends name="../../layout.ftl"/>
