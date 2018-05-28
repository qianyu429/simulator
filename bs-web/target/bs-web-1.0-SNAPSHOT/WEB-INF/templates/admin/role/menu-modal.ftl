<@override name="modal-title">设置角色菜单</@override>

<link rel="stylesheet" href="${ctx}static/libs/ztree/css/zTreeStyle.css"/>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}admin/role/${id}/menus">
    <input type="hidden" name="menus"/>
    <input type="hidden" name="root" value="${root}"/>
    <div class="control-group">
        <div>
            <ul id="tree" class="ztree"></ul>
        </div>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script>
    var zNodes = [
        <#list all_menus as menu>
            { id:${menu.id}, pId:${menu.pid}, name:"${menu.description}", open:true ${(role_menus?? && role_menus?seq_contains(menu.name))?string(", checked:true", "")}},
        </#list>
    ];
</script>
<script src="${ctx}static/libs/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script src="${ctx}static/libs/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="${ctx}static/app/js/admin/role/menu-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>