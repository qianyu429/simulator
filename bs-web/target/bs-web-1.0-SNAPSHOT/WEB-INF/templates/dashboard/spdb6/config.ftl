<#assign parent_title="受托支付" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/spdb6/config">
        <div class="row">
            <@c.selectWithDft id="verify" label="强制改变跨行鉴权响应码" items=codeMap.verify val="k" text="val" selected="${selectMap.verify[0].k!''}"/>
            <@c.selectWithDft id="pay" label="强制改变跨行代扣响应码" items=codeMap.pay val="k" text="val" selected="${selectMap.pay[0].k!''}"/>
            <@c.selectWithDft id="redeem" label="强制改变跨行代付响应码" items=codeMap.redeem val="k" text="val" selected="${selectMap.redeem[0].k!''}"/>
            <@c.selectWithDft id="payTongHang" label="强制改变同行代扣响应码" items=codeMap.payTongHang val="k" text="val" selected="${selectMap.payTongHang[0].k!''}"/>
<@c.selectWithDft id="redeemTongHang" label="强制改变同行代付响应码" items=codeMap.redeemTongHang val="k" text="val" selected="${selectMap.redeemTongHang[0].k!''}"/>
        </div>

        <div class="clearfix form-actions">
            <div class="col-md-offset-4 col-md-8">
                <@c.actions/>
            </div>
        </div>
    </form>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/dashboard/spdb6/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
