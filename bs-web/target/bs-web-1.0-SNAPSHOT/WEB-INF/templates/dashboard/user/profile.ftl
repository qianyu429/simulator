<#assign parent_title="个人" />
<#assign title="修改资料" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="user-profile-form" method="post"
          action="${ctx}dashboard/user/${user.id}/profile">
        <div class="row">
            <@c.input id="username" label="用户名" value="${user.username!''}"/>

            <@c.input id="realname" label="真实姓名" value="${user.realname!''}"/>

            <@c.input id="mobile" label="手机号码" value="${user.mobile!''}"/>

            <@c.input id="email" label="电子邮箱" value="${user.email!''}"/>
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
<script src="${ctx}static/app/js/dashboard/user/profile.js"></script>
</@override>

<@extends name="../layout.ftl"/>
