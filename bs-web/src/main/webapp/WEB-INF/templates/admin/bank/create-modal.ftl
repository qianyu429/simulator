<@override name="modal-title"><#if !bank.id??>添加<#else>编辑</#if>银行</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
    <#if !bank.id??>
      action="${ctx}admin/bank/save"
    <#else>
      action="${ctx}admin/bank/${bank.id}/update"
    </#if>>
    <div class="row">
        <#if !bank.id??>
            <@c.input id="bankNo" label="银行编号" value="${bank.bankNo!''}"/>
        <#else>
            <@c.input id="bankNo" label="银行编号" value="${bank.bankNo!''}" readonly="true"/>
        </#if>
        <@c.input id="bankName" label="银行名称" value="${bank.bankName!''}"/>
        <@c.input id="merId" label="商户号" value="${bank.merId!''}"/>
        <@c.input id="code" label="银行代码" value="${bank.code!''}"/>
        <@c.select id="adminUserId" label="银行管理员" items=users val="id" text="realname" selected="${bank.adminUserId!0}"/>
        <@c.input id="payHead" label="代扣首行模板" value="${bank.payHead!''}"/>
        <@c.input id="payTemplate" label="代扣行模板" value="${bank.payTemplate!''}"/>
        <@c.input id="payUrl" label="代扣回调地址" value="${bank.payUrl!''}"/>
        <@c.input id="redeemHead" label="代付首行模板" value="${bank.redeemHead!''}"/>
        <@c.input id="redeemTemplate" label="代付行模板" value="${bank.redeemTemplate!''}"/>
        <@c.input id="redeemUrl" label="代付回调地址" value="${bank.redeemUrl!''}"/>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/admin/bank/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>