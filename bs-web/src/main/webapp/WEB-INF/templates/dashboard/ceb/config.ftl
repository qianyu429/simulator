<#assign parent_title="光大银行" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/ceb/config">
        <div class="row">
            <@c.selectWithDft id="pay" label="强制改变申购状态码" items=codeMap.pay val="k" text="val" selected="${selectMap.pay[0].k!''}"/>
            <@c.selectWithDft id="redeem" label="强制改变赎回状态码" items=codeMap.redeem val="k" text="val" selected="${selectMap.redeem[0].k!''}"/>
            <@c.selectWithDft id="cancel" label="强制改变解约状态码" items=codeMap.cancel val="k" text="val" selected="${selectMap.cancel[0].k!''}"/>
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
<script src="${ctx}static/app/js/dashboard/ceb/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>