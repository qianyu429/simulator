<#assign parent_title="工行" />
<#assign title="错误码配置" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/icbc/code">
        <div class="row">
            <@c.tagInput id="sms" label="短信响应码" items=codeMap.sms key="k" val="val"/>
            <@c.tagInput id="verify" label="鉴权响应码" items=codeMap.verify key="k" val="val"/>
            <@c.tagInput id="pay" label="申购响应码" items=codeMap.pay key="k" val="val"/>
            <@c.tagInput id="redeem" label="赎回响应码" items=codeMap.redeem key="k" val="val"/>
            <@c.tagInput id="cancel" label="解约响应码" items=codeMap.cancel key="k" val="val"/>
            <@c.tagInput id="predis" label="银企批扣响应码" items=codeMap.predis key="k" val="val"/>
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
<script src="${ctx}static/app/js/dashboard/icbc/code.js"></script>
</@override>

<@extends name="../layout.ftl"/>
