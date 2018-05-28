<#assign parent_title="人脸识别" />
<#assign title="错误码配置" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/faceidy/code">
        <div class="row">
            <@c.tagInput id="score" label="分数" items=codeMap.score key="k" val="val"/>
            <@c.tagInput id="citizenresult" label="身份核查结果" items=codeMap.citizenresult key="k" val="val"/>
            <@c.tagInput id="faceresult" label="人像比对结果" items=codeMap.faceresult key="k" val="val"/>
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
<script src="${ctx}static/libs/bootstrap/js/bootstrap-tag.min.js"></script>
<script src="${ctx}static/app/js/dashboard/faceidy/code.js"></script>
</@override>

<@extends name="../layout.ftl"/>
