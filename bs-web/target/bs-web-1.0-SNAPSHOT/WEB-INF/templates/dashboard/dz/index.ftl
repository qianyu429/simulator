<#assign parent_title="维护" />
<#assign title="对账文件管理">

<#assign bankNo = RequestParameters.bankNo!'' />
<#assign transType = RequestParameters.transType!'' />
<#assign startDate = RequestParameters.startDate!'' />
<#assign endDate = RequestParameters.endDate!'' />

<@override name="style">
<link rel="stylesheet" href="${ctx}static/ace/assets/css/bootstrap-datetimepicker.min.css" />
</@override>

<@override name="button">
<a class="btn btn-sm btn-primary" href="${ctx}dashboard/dz/create" data-backdrop="static" data-toggle="modal"
   data-target="#myModal">手动生成对账、账单文件</a>
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
            <input type="datetime" name="startDate" id="startDate" value="${startDate}" class="form-control" placeholder="开始日期"/>
        </div>
        <div class="form-group">
            <input type="datetime" name="endDate" id="endDate" value="${endDate}" class="form-control" placeholder="结束日期"/>
        </div>

        <button class="btn btn-purple btn-sm">
            搜索
            <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
        </button>
    </form>
</div>

<div class="space-24"></div>

<div class="col-xs-12">
    <table id="dz-table" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>银行名称</th>
            <th>交易类型</th>
            <th>对账文件路径</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#if (page.list)?size gt 0>
                <#list page.list as dz>
                    <#include "dz-tr.ftl"/>
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
    <@c.pagination url="${ctx}dashboard/dz" param="bankNo=${bankNo}&transType=${transType}&startDate=${startDate}&endDate=${endDate}"/>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/libs/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}static/libs/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}static/app/js/dashboard/dz/index.js"></script>
</@override>

<@extends name="../layout.ftl"/>