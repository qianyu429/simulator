<#assign parent_title="用户" />
<#assign title="角色管理" />

<@override name="button">
<a data-toggle="modal" class="btn btn-xs btn-primary" href="${ctx}admin/role/create" data-target="#myModal"
   data-backdrop="static">添加新角色</a>
</@override>

<@override name="main">
<div class="col-xs-12">
    <table id="role-table" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>角色名称</th>
            <th>描述</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>最后更新时间</th>
            <th>操作</th>
        </tr>
        </thead>

        <tbody>
            <#if roles?size gt 0>
                <#list roles as role>
                    <#include "role-tr.ftl"/>
                </#list>
            <#else>
            <tr>
                <td colspan="20">
                    <div class="empty">暂无角色记录</div>
                </td>
            </tr>
            </#if>
        </tbody>
    </table>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/admin/role/list.js"></script>
</@override>

<@extends name="../layout.ftl"/>
