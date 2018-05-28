<#assign parent_title="上海银行" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/sh4/config">
        <div class="row">
            <@c.selectWithDft id="calsignZL" label="强制改变直联解约响应码" items=codeMap.calsignZL val="k" text="val" selected="${selectMap.calsignZL[0].k!''}"/>
            <@c.selectWithDft id="payZL" label="强制改变直联支付响应码" items=codeMap.payZL val="k" text="val" selected="${selectMap.payZL[0].k!''}"/>

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
<script src="${ctx}static/app/js/dashboard/sh4/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
