<#assign parent_title="浦发网关支付" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/spdb2/config">
        <div class="row">
            <@c.selectWithDft id="pay" label="强制改变申购响应码" items=codeMap.pay val="k" text="val" selected="${selectMap.pay[0].k!''}"/>
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
<script src="${ctx}static/app/js/dashboard/spdb2/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
