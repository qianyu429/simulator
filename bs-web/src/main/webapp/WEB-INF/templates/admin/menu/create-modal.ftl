<@override name="modal-title"><#if !menu.id??>添加<#else>编辑</#if>菜单</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
    <#if !menu.id??>
      action="${ctx}admin/menu/save"
    <#else>
      action="${ctx}admin/menu/${menu.id}/update"
    </#if>>
    <input type="hidden" name="pid" value="${(parent_menu.id)!0}">
    <div class="row">
        <#if parent_menu.id??>
            <@c.readonlyInput id="parent_name" label="上级菜单" value="${parent_menu.description}"/>
        </#if>

        <@c.input id="description" label="菜单名称" value="${menu.description!''}"/>

        <@c.input id="name" label="菜单代码" value="${menu.name!''}"/>

        <input type="hidden" id="old_name" name="old_name" value="${menu.name!''}">

        <@c.input id="url" label="地址" value="${menu.url!''}"/>

        <@c.input id="icon" label="图标" value="${menu.icon!''}"/>

        <@c.input id="sort" label="排序" value="${menu.sort!'1'}"/>

    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/admin/menu/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>