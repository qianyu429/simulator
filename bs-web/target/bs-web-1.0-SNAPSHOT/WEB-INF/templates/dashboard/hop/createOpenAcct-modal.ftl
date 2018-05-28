<@override name="modal-title">添加</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}dashboard/order/save">

    <div class="row">
        <@c.input id="companyNo" label="机构号" value="${order.companyNo!''}"/>
        <@c.input id="subCompanyNo" label="子机构号" value="${order.subCompanyNo!''}"/>
        <@c.input id="partnerCustNo" label="合作方客户编号" value="${order.partnerCustNo!''}"/>
        <@c.input id="custType" label="客户类型" value="${order.custType!''}"/>
        <@c.input id="name" label="客户姓名" value="${order.name!''}"/>
        <@c.input id="certType" label="证件类型" value="${order.certType!''}"/>
        <@c.input id="certNo" label="证件号码" value="${order.certNo!''}"/>
        <@c.input id="mobile" label="手机号码" value="${order.mobile!''}"/>
        <@c.input id="bankCardNo" label="银行卡号" value="${order.bankCardNo!''}"/>
        <@c.input id="riskLevel" label="风险测评等级" value="${order.riskLevel!''}"/>
        <@c.input id="remark" label="附加信息" value="${order.remark!''}"/>
        <input type="hidden" id = "orderType" name = "orderType" value="1">
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/dashboard/hop/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>