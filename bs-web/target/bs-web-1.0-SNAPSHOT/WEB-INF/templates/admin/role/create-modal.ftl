<@override name="modal-title"><#if !role.id??>添加新<#else>编辑</#if>角色</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
    <#if !role.id??>
      action="${ctx}admin/role/save"
    <#else>
      action="${ctx}admin/role/${role.id}/update"
    </#if>>
    <div class="row">
        <#if !role.id??>
            <@c.input id="name" label="角色名" value="${role.name!''}"/>
        </#if>

        <@c.input id="description" label="描述" value="${role.description!''}"/>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/admin/role/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>