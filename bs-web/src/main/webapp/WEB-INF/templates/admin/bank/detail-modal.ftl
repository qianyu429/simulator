<@override name="modal-title">银行详情</@override>

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tr>
        <th width="30%">ID</th>
        <td>${bank.id}</td>
    </tr>
    <tr>
        <th>银行编号</th>
        <td>${bank.bankNo}</td>
    </tr>
    <tr>
        <th>银行名称</th>
        <td>${bank.bankName!''}</td>
    </tr>
    <tr>
        <th>商户编号</th>
        <td>${bank.merId!''}</td>
    </tr>
    <tr>
        <th>银行代码</th>
        <td>${bank.code!''}</td>
    </tr>
    <tr>
        <th>代扣首行模板</th>
        <td>
            <@c.substring str="${bank.payHead}" end=40/>
        </td>
    </tr>
    <tr>
        <th>代扣行模板</th>
        <td title="${bank.payTemplate}">
            <@c.substring str="${bank.payTemplate}" end=40/>
        </td>
    </tr>
    <tr>
        <th>代扣回调地址</th>
        <td title="${bank.payUrl}">
            <@c.substring str="${bank.payUrl}" end=40/>
        </td>
    </tr>
    <tr>
        <th>代付首行模板</th>
        <td>
            <@c.substring str="${bank.redeemHead}" end=40/>
        </td>
    </tr>
    <tr>
        <th>代付行模板</th>
        <td title="${bank.redeemTemplate}">
            <@c.substring str="${bank.redeemTemplate}" end=40/>
        </td>
    </tr>
    <tr>
        <th>代付回调地址</th>
        <td title="${bank.redeemUrl}">
            <@c.substring str="${bank.redeemUrl}" end=40/>
        </td>
    </tr>
    <tr>
        <th>状态</th>
        <td><#include "status.ftl"></td>
    </tr>
    <tr>
        <th>管理员</th>
        <td>${bank.adminRealname!''}</td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td>${bank.createdTime?datetime}</td>
    </tr>
    <tr>
        <th>最后更新时间</th>
        <td>${bank.updatedTime?datetime}</td>
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