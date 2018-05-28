<#assign parent_title="受托支付" />
<#assign title="错误码配置" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/spdb6/code">
        <div class="row">
            <@c.tagInput id="verify" label="跨行鉴权响应码" items=codeMap.verify key="k" val="val"/>
            <@c.tagInput id="pay" label="跨行代扣响应码" items=codeMap.pay key="k" val="val"/>
            <@c.tagInput id="redeem" label="跨行代付响应码" items=codeMap.redeem key="k" val="val"/>
            <@c.tagInput id="payTongHang" label="同行代扣响应码" items=codeMap.payTongHang key="k" val="val"/>
            <@c.tagInput id="redeemTongHang" label="同行代付响应码" items=codeMap.redeemTongHang key="k" val="val"/>
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
<script src="${ctx}static/libs/bootstrap/js/bootstrap-tag.min.js"></script>
<script src="${ctx}static/app/js/dashboard/spdb6/code.js"></script>
</@override>

<@extends name="../layout.ftl"/>
