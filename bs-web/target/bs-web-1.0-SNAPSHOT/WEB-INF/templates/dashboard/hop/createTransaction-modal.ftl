<@override name="modal-title">添加</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}dashboard/order/save">

    <div class="row">
        <@c.input id="companyNo" label="机构号" value="${order.companyNo!''}"/>
        <@c.input id="subCompanyNo" label="子机构号" value="${order.subCompanyNo!''}"/>
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
        <input type="hidden" id = "orderType" name = "orderType" value="2">
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/dashboard/hop/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>