<#assign parent_title="个人" />
<#assign title="修改密码" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="user-password-form" method="post"
          action="${ctx}dashboard/user/${id}/password">
        <div class="row">
            <@c.input id="password" label="新密码" value=""/>
        </div>

        <div class="clearfix form-actions">
            <div class="col-md-offset-4 col-md-8">
                <@c.actions />
            </div>
        </div>
    </form>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/dashboard/user/password.js"></script>
</@override>

<@extends name="../layout.ftl"/>
