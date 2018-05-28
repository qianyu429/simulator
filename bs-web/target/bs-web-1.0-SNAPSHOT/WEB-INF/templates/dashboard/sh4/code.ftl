<#assign parent_title="上海银行" />
<#assign title="错误码配置" />

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/sh4/code">
        <div class="row">
            <@c.tagInput id="calsignZL" label="直联解约" items=codeMap.calsignZL key="k" val="val"/>
           <@c.tagInput id="payZL" label="直联支付" items=codeMap.payZL key="k" val="val"/>
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
<script src="${ctx}static/app/js/dashboard/sh4/code.js"></script>
</@override>

<@extends name="../layout.ftl"/>
