<#assign parent_title="工行网银" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/icbc1/config">
        <div class="row">
            <@c.selectWithDft id="sign" label="强制改变签约响应码" items=codeMap.sign val="k" text="val" selected="${selectMap.sign[0].k!''}"/>
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
<script src="${ctx}static/app/js/dashboard/icbc1/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
