<@override name="modal-title">编辑</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}dashboard/order/${order.id}/update">
    <div class="row">
    <#if order.orderType == "1">
        <@c.input id="id" label="id" value="${order.id!''}" readonly="true"/>
        <@c.input id="companyNo" label="机构号" value="${order.companyNo!''}" readonly="true"/>
        <@c.input id="subCompanyNo" label="子机构号" value="${order.subCompanyNo!''}" readonly="true"/>
        <@c.input id="partnerCustNo" label="合作方客户编号" value="${order.partnerCustNo!''}"/>
        <@c.input id="custType" label="客户类型" value="${order.custType!''}"/>
        <@c.input id="name" label="客户姓名" value="${order.name!''}"/>
        <@c.input id="certType" label="证件类型" value="${order.certType!''}"/>
        <@c.input id="certNo" label="证件号码" value="${order.certNo!''}"/>
        <@c.input id="mobile" label="手机号码" value="${order.mobile!''}"/>
        <@c.input id="bankCardNo" label="银行卡号" value="${order.bankCardNo!''}"/>
        <@c.input id="riskLevel" label="风险测评等级" value="${order.riskLevel!''}"/>
        <@c.input id="remark" label="附加信息" value="${order.remark!''}"/>
    <#elseif order.orderType == "2">
        <@c.input id="id" label="id" value="${order.id!''}" readonly="true"/>
        <@c.input id="companyNo" label="机构号" value="${order.companyNo!''}" readonly="true"/>
        <@c.input id="subCompanyNo" label="子机构号" value="${order.subCompanyNo!''}" readonly="true"/>
        <@c.input id="serialNo" label="订单号" value="${order.serialNo!''}"/>
        <@c.input id="partnerCustNo" label="合作方客户编号" value="${order.partnerCustNo!''}"/>
        <@c.input id="tradeAcct" label="交易帐号" value="${order.tradeAcct!''}"/>
        <@c.input id="prodId" label="产品代码" value="${order.prodId!''}"/>
        <@c.input id="amount" label="申请金额" value="${order.amount!''}"/>
        <@c.input id="share" label="申请份额" value="${order.share!''}"/>
        <@c.input id="paymentType" label="支付方式" value="${order.paymentType!''}"/>
        <@c.input id="bankCardNo" label="银行卡号" value="${order.bankCardNo!''}"/>
        <@c.input id="chargeType" label="收费方式" value="${order.chargeType!''}"/>
        <@c.input id="apkind" label="业务码" value="${order.apkind!''}"/>
    </#if>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/dashboard/hop/update-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>