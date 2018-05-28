<@override name="modal-title">交易详情</@override>

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tr>
        <th width="30%">ID</th>
        <td>${order.id}</td>
    </tr>

    <#if order.orderType == "1">
        <tr>
            <th>机构号</th>
            <td>${order.companyNo!''}</td>
        </tr>
        <tr>
            <th>子机构号</th>
            <td>${order.subCompanyNo!''}</td>
        </tr>
        <tr>
            <th>合作方客户编号</th>
            <td>${order.partnerCustNo!''}</td>
        </tr>
        <tr>
            <th>客户类型</th>
            <td>${order.custType!''}</td>
        </tr>
        <tr>
            <th>客户姓名</th>
            <td>${order.name!''}</td>
        </tr>
        <tr>
            <th>证件类型</th>
            <td>${order.certType!''}</td>
        </tr>
        <tr>
            <th>证件号码</th>
            <td>${order.certNo!''}</td>
        </tr>
        <tr>
            <th>手机号码</th>
            <td>${order.mobile!''}</td>
        </tr>
        <tr>
            <th>银行卡号</th>
            <td>${order.bankCardNo!''}</td>
        </tr>
        <tr>
            <th>风险测评等级</th>
            <td>${order.riskLevel!''}</td>
        </tr>
        <tr>
            <th>附加信息</th>
            <td>${order.remark!''}</td>
        </tr>
    <#elseif order.orderType == "2">
        <tr>
            <th>机构号</th>
            <td>${order.companyNo!''}</td>
        </tr>
        <tr>
            <th>子机构号</th>
            <td>${order.subCompanyNo!''}</td>
        </tr>
        <tr>
            <th>订单号</th>
            <td>${order.serialNo!''}</td>
        </tr>
        <tr>
            <th>合作方客户编号</th>
            <td>${order.partnerCustNo!''}</td>
        </tr>
        <tr>
            <th>交易帐号</th>
            <td>${order.tradeAcct!''}</td>
        </tr>
        <tr>
            <th>产品代码</th>
            <td>${order.prodId!''}</td>
        </tr>
        <tr>
            <th>申请金额</th>
            <td>${order.amount!''}</td>
        </tr>
        <tr>
            <th>申请份额</th>
            <td>${order.share!''}</td>
        </tr>
        <tr>
            <th>支付方式</th>
            <td>${order.paymentType!''}</td>
        </tr>
        <tr>
            <th>银行卡号</th>
            <td>${order.bankCardNo!''}</td>
        </tr>
        <tr>
            <th>收费方式</th>
            <td>${order.chargeType!''}</td>
        </tr>
        <tr>
            <th>业务码</th>
            <td>${order.apkind!''}</td>
        </tr>

    </#if>
</table>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    ${cancel}
</button>
</@override>

<@extends name="../../modal-layout.ftl"/>