<@override name="modal-title">配置详情</@override>

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tr>
        <th width="30%">ID</th>
        <td>${config.id}</td>
    </tr>
    <tr>
        <th>用户ID</th>
        <td>${config.userId!'0'}</td>
    </tr>
    <tr>
        <th>组</th>
        <td>${config.grp!''}</td>
    </tr>
    <tr>
        <th>键</th>
        <td>${config.k!''}</td>
    </tr>
    <tr>
        <th>值</th>
        <td>${config.val!''}</td>
    </tr>
    <tr>
        <th>状态</th>
        <td><#include "status.ftl"></td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td>${config.createdTime?datetime}</td>
    </tr>
    <tr>
        <th>最后更新时间</th>
        <td>${config.updatedTime?datetime}</td>
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