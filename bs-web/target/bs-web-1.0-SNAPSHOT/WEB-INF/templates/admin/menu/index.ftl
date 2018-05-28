<#assign parent_title="用户" />
<#assign title="菜单管理" />

<@override name="style">
<link rel="stylesheet" href="${ctx}static/libs/ztree/css/zTreeStyle.css"/>
</@override>

<@override name="main">
<div class="col-sm-6">
    <div class="widget-box widget-color-blue2">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">后台权限</h4>
        </div>

        <div class="widget-body">
            <div class="widget-main padding-8">
                <div id="admin_tree" class="ztree"></div>
            </div>
        </div>
    </div>
</div>
<div class="col-sm-6">
    <div class="widget-box widget-color-green2">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">工作台权限</h4>
        </div>

        <div class="widget-body">
            <div class="widget-main padding-8">
                <div id="dashboard_tree" class="ztree"></div>
            </div>
        </div>
    </div>
</div>
</@override>

<@override name="script">
<script>
    var admin_zNodes = [
        {id: 1, pId: 0, name: "后台管理", open: true},
        <#list all_admin_menus as menu>
            {id:${menu.id}, pId:${menu.pid}, name: "${menu.description}", open: true},
        </#list>];
    var dashboard_zNodes = [
        {id: 2, pId: 0, name: "工作台", open: true},
        <#list all_dashboard_menus as menu>
            {id:${menu.id}, pId:${menu.pid}, name: "${menu.description}", open: true},
        </#list>];
</script>
<script src="${ctx}static/libs/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script src="${ctx}static/libs/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
<script src="${ctx}static/app/js/admin/menu/index.js"></script>
</@override>

<@extends name="../layout.ftl"/>
