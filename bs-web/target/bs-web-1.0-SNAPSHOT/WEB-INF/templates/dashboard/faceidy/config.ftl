<#assign parent_title="人脸识别" />
<#assign title="模拟器配置">

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/faceidy/config">
        <div class="row">
            <@c.selectWithDft id="score" label="强制改变分数" items=codeMap.score val="k" text="val" selected="${selectMap.score[0].k!''}"/>
            <@c.selectWithDft id="citizenresult" label="强制改变身份核查结果" items=codeMap.citizenresult val="k" text="val" selected="${selectMap.citizenresult[0].k!''}"/>
            <@c.selectWithDft id="faceresult" label="强制改变人像比对结果" items=codeMap.faceresult val="k" text="val" selected="${selectMap.faceresult[0].k!''}"/>

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
<script src="${ctx}static/app/js/dashboard/faceidy/config.js"></script>
</@override>

<@extends name="../layout.ftl"/>
