<@override name="modal-title">用户详情</@override>

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tr>
        <th width="30%">ID</th>
        <td>${user.id}</td>
    </tr>
    <tr>
        <th>用户名</th>
        <td>${user.username}</td>
    </tr>
    <tr>
        <th>真实姓名</th>
        <td>${user.realname!''}</td>
    </tr>
    <tr>
        <th>状态</th>
        <td><#include "status.ftl"></td>
    </tr>
    <tr>
        <th>手机号</th>
        <td>${user.mobile!''}</td>
    </tr>
    <tr>
        <th>电子邮箱</th>
        <td>${user.email!''}</td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td>${user.createdTime?datetime}</td>
    </tr>
    <tr>
        <th>最后更新时间</th>
        <td>${user.updatedTime?datetime}</td>
    </tr>
</table>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    ${cancel}
</button>
</@override>

<@extends name="../../modal-layout.ftl"/>