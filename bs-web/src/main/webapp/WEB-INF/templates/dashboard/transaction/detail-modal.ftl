<@override name="modal-title">交易详情</@override>

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tr>
        <th width="30%">ID</th>
        <td>${transaction.id}</td>
    </tr>
    <tr>
        <th>基金方流水号</th>
        <td>${transaction.beSer!''}</td>
    </tr>
    <tr>
        <th>银联方流水号</th>
        <td>${transaction.bsSer!''}</td>
    </tr>
    <tr>
        <th>商户号</th>
        <td>${transaction.merId!''}</td>
    </tr>
    <tr>
        <th>银行名称</th>
        <td><#include "bank-no.ftl"></td>
    </tr>
    <tr>
        <th>交易账号</th>
        <td>${transaction.accoNo!''}</td>
    </tr>
    <tr>
        <th>交易类型</th>
        <td><#include "trans-type.ftl"></td>
    </tr>
    <tr>
        <th>交易金额</th>
        <td>${transaction.amount!''}</td>
    </tr>
    <tr>
        <th>响应码</th>
        <td>${transaction.respCode!''}</td>
    </tr>
    <tr>
        <th>状态码</th>
        <td>${transaction.transStat!''}</td>
    </tr>
    <tr>
        <th>内部码</th>
        <td>${transaction.stat!''}</td>
    </tr>
    <tr>
        <th>工作日</th>
        <td>${transaction.workDay!''}</td>
    </tr>
    <tr>
        <th>交易时间</th>
        <td>${transaction.sendTime!''}</td>
    </tr>
    <tr>
        <th>状态</th>
        <td><#include "status.ftl"></td>
    </tr>
    <tr>
        <th>创建日期</th>
        <td>${transaction.createdDate!''}</td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td>${transaction.createdTime?datetime}</td>
    </tr>
    <tr>
        <th>最后更新时间</th>
        <td>${transaction.updatedTime?datetime}</td>
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