<#assign parent_title="民生T+0" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/cmbct0/config">
        <div class="row">
            <@c.selectWithDft id="redeem" label="强制改变赎回响应码" items=codeMap.redeem val="k" text="val" selected="${selectMap.redeem[0].k!''}"/>

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
<script src="${ctx}static/app/js/dashboard/cmbct0/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
