<#assign parent_title="中信信用卡" />
<#assign title="模拟器配置">


<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/citicb/config">
        <div class="row">
            <@c.selectWithDft id="sign" label="强制改变签约状态码" items=codeMap.sign val="k" text="val" selected="${selectMap.sign[0].k!''}"/>
            <@c.selectWithDft id="verify" label="强制改变查询状态码" items=codeMap.verify val="k" text="val" selected="${selectMap.verify[0].k!''}"/>
            <@c.input id="date" label="账单日"/>
            <@c.input id="payAmt" label="应还金额"/>
            <@c.input id="minAmt" label="最低应还金额"/>
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
<script src="${ctx}static/app/js/dashboard/citicb/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
