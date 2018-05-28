<#assign parent_title="民生" />
<#assign title="错误码配置" />

<@override name="button">
<div class="help-button pull-left" data-trigger="hover" data-rel="import"
     data-placement="left" data-content="请先下载导入模板, 把错误码按格式填写到模板中, 然后再导入错误码, 导入后会删除原本的所有错误码!!">?
</div>

<a class="btn btn-sm btn-purple" href="${ctx}download/template/cmbc.xlsx">下载导入模板</a>
<a data-backdrop="static" data-toggle="modal" class="btn btn-xs btn-primary" href="${ctx}dashboard/cmbc/import"
   data-target="#myModal">导入错误码</a>
</@override>

<@override name="main">
<div class="col-xs-12">
    <form class="form-horizontal" role="form" id="form" method="post"
          action="${ctx}dashboard/cmbc/code">
        <div class="row">
            <@c.tagInput id="sms" label="短信响应码" items=codeMap.sms key="k" val="val"/>
            <@c.tagInput id="verify" label="鉴权响应码" items=codeMap.verify key="k" val="val"/>
            <@c.tagInput id="pay" label="申购响应码" items=codeMap.pay key="k" val="val"/>
            <@c.tagInput id="redeem" label="赎回响应码" items=codeMap.redeem key="k" val="val"/>
            <@c.tagInput id="cancel" label="解约响应码" items=codeMap.cancel key="k" val="val"/>
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
<script src="${ctx}static/app/js/dashboard/cmbc/code.js"></script>
</@override>

<@extends name="../layout.ftl"/>
