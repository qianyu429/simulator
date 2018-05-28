<#assign parent_title="广东南粤" />
<#assign title="错误码配置" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/gdny/code">
        <div class="row">
            <@c.tagInput id="verify" label="鉴权响应吗" items=codeMap.verify key="k" val="val"/>
            <@c.tagInput id="pay" label="申购响应吗" items=codeMap.pay key="k" val="val"/>
            <@c.tagInput id="redeem" label="赎回响应吗" items=codeMap.redeem key="k" val="val"/>

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
<script src="${ctx}static/app/js/dashboard/gdny/code.js"></script>
</@override>

<@extends name="../layout.ftl"/>
