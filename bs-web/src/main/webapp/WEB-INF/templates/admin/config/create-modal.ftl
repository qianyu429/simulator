<@override name="modal-title"><#if !config.id??>添加<#else>编辑</#if>配置</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
    <#if !config.id??>
      action="${ctx}admin/config/save"
    <#else>
      action="${ctx}admin/config/${config.id}/update"
    </#if>>
    <div class="row">
        <@c.input id="userId" label="用户ID" value="${config.userId!''}"/>
        <@c.input id="grp" label="组" value="${config.grp!''}"/>
        <@c.input id="k" label="键" value="${config.k!''}"/>
        <@c.input id="val" label="值" value="${config.val!''}"/>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/admin/config/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>