<#assign parent_title="工行网银" />
<#assign title="错误码配置" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/icbc1/code">
        <div class="row">
            <@c.tagInput id="sign" label="签约响应码" items=codeMap.sign key="k" val="val"/>
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
<script src="${ctx}static/app/js/dashboard/icbc1/code.js"></script>
</@override>

<@extends name="../layout.ftl"/>
