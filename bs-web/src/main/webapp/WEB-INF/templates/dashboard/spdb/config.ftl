<#assign parent_title="上海浦发" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/spdb/config">
        <div class="row">
            <@c.selectWithDft id="sms" label="强制改变短信响应码" items=codeMap.sms val="k" text="val" selected="${selectMap.sms[0].k!''}"/>
            <@c.selectWithDft id="verify" label="强制改变鉴权响应码" items=codeMap.verify val="k" text="val" selected="${selectMap.verify[0].k!''}"/>
            <@c.selectWithDft id="pay" label="强制改变响应申购码" items=codeMap.pay val="k" text="val" selected="${selectMap.pay[0].k!''}"/>
            <@c.selectWithDft id="redeem" label="强制改变赎回(同行-8801)响应码" items=codeMap.redeem val="k" text="val" selected="${selectMap.redeem[0].k!''}"/>
            <@c.selectWithDft id="interRedeem" label="强制改变赎回(跨行-EG01)响应码" items=codeMap.interRedeem val="k" text="val" selected="${selectMap.interRedeem[0].k!''}"/>

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
<script src="${ctx}static/app/js/dashboard/spdb/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
