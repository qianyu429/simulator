<#assign parent_title="民生" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/cmbc/config">
        <div class="row">
            <@c.selectWithDft id="sms" label="强制改变短信响应码" items=codeMap.sms val="k" text="val" selected="${selectMap.sms[0].k!''}"/>
            <@c.selectWithDft id="verify" label="强制改变鉴权响应码" items=codeMap.verify val="k" text="val" selected="${selectMap.verify[0].k!''}"/>
            <@c.selectWithDft id="pay" label="强制改变申购响应码" items=codeMap.pay val="k" text="val" selected="${selectMap.pay[0].k!''}"/>
            <@c.selectWithDft id="redeem" label="强制改变赎回响应码" items=codeMap.redeem val="k" text="val" selected="${selectMap.redeem[0].k!''}"/>
            <@c.selectWithDft id="cancel" label="强制改变解约响应码" items=codeMap.cancel val="k" text="val" selected="${selectMap.cancel[0].k!''}"/>

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
<script src="${ctx}static/app/js/dashboard/cmbc/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
