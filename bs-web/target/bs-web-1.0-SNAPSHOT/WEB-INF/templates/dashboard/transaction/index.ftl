<#assign parent_title="维护" />
<#assign title="交易管理">

<#assign transType = RequestParameters.transType!'' />
<#assign bankNo = RequestParameters.bankNo!'' />
<#assign beSer = RequestParameters.beSer!'' />

<@override name="button">
<a class="btn btn-sm btn-primary" href="${ctx}dashboard/transaction/create" data-backdrop="static" data-toggle="modal" data-target="#myModal">添加</a>
</@override>

<@override name="main">
<div class="col-sm-12">
    <form class="form-inline" method="get" novalidate>
        <div class="form-group">
            <select name="bankNo" class="form-control">
                <option value="">-- 全部银行 --</option>
                <#list banks as bank>
                    <option value="${bank.bankNo}" <#if bankNo==bank.bankNo>selected</#if>>${bank.bankName}</option>
                </#list>
            </select>
        </div>
        <div class="form-group">
            <select name="transType" class="form-control">
                <option value="">-- 全部交易 --</option>
                <#list enums["com.shhxzq.bs.constants.TransTypeEnum"]?values as enum>
                    <option value="${enum.type}" <#if transType==enum.type>selected</#if>>${enum.value}</option>
                </#list>
            </select>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="beSer" value="${beSer}" placeholder="基金方流水号"/>
        </div>

        <button class="btn btn-purple btn-sm">
            搜索
            <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
        </button>
    </form>
</div>

<div class="space-24"></div>

<div class="col-xs-12">
    <table id="transaction-table" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>基金方流水号</th>
            <th>商户号</th>
            <th>银行名称</th>
            <th>交易账号</th>
            <th>交易类型</th>
            <th>状态</th>
            <th>工作日</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#if (page.list)?size gt 0>
                <#list page.list as transaction>
                    <#include "transaction-tr.ftl"/>
                </#list>
            <#else>
            <tr>
                <td colspan="20">
                    <div class="empty">暂无查询记录</div>
                </td>
            </tr>
            </#if>
        </tbody>
    </table>
    <@c.pagination url="${ctx}dashboard/transaction" param="transType=${transType}&bankNo=${bankNo}&beSer=${beSer}"/>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/dashboard/transaction/index.js"></script>
</@override>

<@extends name="../layout.ftl"/>