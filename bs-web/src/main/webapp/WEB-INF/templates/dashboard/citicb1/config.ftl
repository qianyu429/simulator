<#assign parent_title="中信联名卡" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/citicb1/config">
        <div class="row">

            <@c.selectWithDft id="verify" label="基础校验" items=codeMap.verify val="k" text="val" selected="${selectMap.verify[0].k!''}"/>
            <@c.selectWithDft id="pay" label="新卡申请" items=codeMap.pay val="k" text="val" selected="${selectMap.pay[0].k!''}"/>
            <@c.selectWithDft id="redeem" label="二卡申请" items=codeMap.redeem val="k" text="val" selected="${selectMap.redeem[0].k!''}"/>

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
<script src="${ctx}static/app/js/dashboard/citicb1/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
