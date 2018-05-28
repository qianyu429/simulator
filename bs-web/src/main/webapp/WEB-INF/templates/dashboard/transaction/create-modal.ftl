<@override name="modal-title"><#if !transaction.id??>添加<#else>编辑</#if>交易</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
    <#if !transaction.id??>
      action="${ctx}dashboard/transaction/save"
    <#else>
      action="${ctx}dashboard/transaction/${transaction.id}/update"
    </#if>>
    <div class="row">
        <#if !transaction.id??>
            <@c.input id="beSer" label="基金方流水号" value="${transaction.beSer!''}"/>
        <#else>
            <@c.input id="beSer" label="基金方流水号" value="${transaction.beSer!''}" readonly="true"/>
        </#if>
        <@c.input id="merId" label="商户号" value="${transaction.merId!''}"/>
        <@c.input id="bsSer" label="银联方流水号" value="${transaction.bsSer!''}"/>
        <@c.select id="bankNo" label="银行名称" items=banks val="bankNo" text="bankName" selected="${transaction.bankNo!''}"/>
        <@c.input id="accoNo" label="交易账号" value="${transaction.accoNo!''}"/>
        <div class="form-group">
            <label for="transType" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">交易类型</label>
            <div class="col-xs-12 col-sm-6">
            <span class="block input-icon input-icon-right">
                <select id="transType" name="transType" class="form-control">
                    <#list enums["com.shhxzq.bs.constants.TransTypeEnum"]?values as enum>
                        <option value="${enum.type}" <#if transaction.transType?? && transaction.transType==enum.type>selected</#if>>${enum.value}</option>
                    </#list>
                </select>
                <i class="ace-icon fa fa-check-circle hide"></i>
            </span>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline"></div>
        </div>
        <@c.input id="amount" label="交易金额" value="${transaction.amount!''}"/>
        <@c.input id="respCode" label="响应码" value="${transaction.respCode!''}"/>
        <@c.input id="transStat" label="状态码" value="${transaction.transStat!''}"/>
        <@c.input id="stat" label="内部码" value="${transaction.stat!''}"/>
        <div class="form-group">
            <label for="status" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">状态</label>
            <div class="col-xs-12 col-sm-6">
            <span class="block input-icon input-icon-right">
                <select id="status" name="status" class="form-control">
                    <#list enums["com.shhxzq.bs.constants.StatusEnum"]?values as enum>
                        <option value="${enum.status}" <#if transaction.status?? && transaction.status==enum.status>selected</#if>>${enum.value}</option>
                    </#list>
                </select>
                <i class="ace-icon fa fa-check-circle hide"></i>
            </span>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline"></div>
        </div>
        <@c.input id="workDay" label="工作日" value="${transaction.workDay!''}"/>
        <@c.input id="sendTime" label="交易时间" value="${transaction.sendTime!''}"/>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/dashboard/transaction/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>