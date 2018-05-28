<@override name="modal-title">角色详细信息</@override>

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tbody>
    <tr>
        <th width="25%">角色名</th>
        <td width="75%">${role.name}</td>
    </tr>
    <tr>
        <th>描述</th>
        <td>${role.description}</td>
    </tr>
    <tr>
        <th>状态</th>
        <td><#include "status.ftl"></td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td>${role.createdTime?datetime}</td>
    </tr>
    <tr>
        <th>更新时间</th>
        <td>${role.updatedTime?datetime}</td>
    </tr>
    </tbody>
</table>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="icon-remove"></i>
${cancel}
</button>
</@override>

<@extends name="../../modal-layout.ftl"/>