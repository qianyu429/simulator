<#assign parent_title="光大银行" />
<#assign title="错误码配置" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/ceb/code">
        <div class="row">
            <@c.tagInput id="pay" label="申购状态码" items=codeMap.pay key="k" val="val"/>
            <@c.tagInput id="redeem" label="赎回状态码" items=codeMap.redeem key="k" val="val"/>
            <@c.tagInput id="cancel" label="解约状态码" items=codeMap.cancel key="k" val="val"/>
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
<script src="${ctx}static/app/js/dashboard/ceb/code.js"></script>
</@override>

<@extends name="../layout.ftl"/>
