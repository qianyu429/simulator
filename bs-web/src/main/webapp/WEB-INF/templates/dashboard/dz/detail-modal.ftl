<@override name="modal-title">对账详情</@override>

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tr>
        <th width="30%">ID</th>
        <td>${dz.id}</td>
    </tr>
    <tr>
        <th>银行名称</th>
        <td><#include "bank-no.ftl"></td>
    </tr>
    <tr>
        <th>交易类型</th>
        <td><#include "trans-type.ftl"></td>
    </tr>
    <tr>
        <th>文件路径</th>
        <td title="${dz.path}">
            <@c.substring str="${dz.path}" end=40/>
        </td>
    </tr>
    <tr>
        <th>状态</th>
        <td><#include "status.ftl"></td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td>${dz.createdTime?datetime}</td>
    </tr>
    <tr>
        <th>最后更新时间</th>
        <td>${dz.updatedTime?datetime}</td>
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