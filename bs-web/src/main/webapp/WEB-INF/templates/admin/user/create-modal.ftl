<@override name="modal-title"><#if !user.id??>添加<#else>编辑</#if>用户</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
    <#if !user.id??>
      action="${ctx}admin/user/save"
    <#else>
      action="${ctx}admin/user/${user.id}/update"
    </#if>>
    <div class="row">

        <#if !user.id??>
            <@c.input id="username" label="用户名" value="${user.username!''}"/>
            <@c.input id="password" label="密码" value="${user.password!''}"/>
        <#else>
            <@c.input id="username" label="用户名" value="${user.username!''}" readonly="true"/>
        </#if>

        <@c.input id="realname" label="真实姓名" value="${user.realname!''}"/>

        <@c.input id="mobile" label="手机号" value="${user.mobile!''}"/>

        <@c.input id="email" label="电子邮箱" value="${user.email!''}"/>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/admin/user/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>