<#assign parent_title="三方金融平台" />
<#assign title="财富通交易管理">

<#assign orderType = RequestParameters.orderType!'' />
<#--<#assign transType = RequestParameters.transType!'' />-->
<#--<#assign bankNo = RequestParameters.bankNo!'' />-->
<#--<#assign beSer = RequestParameters.beSer!'' />-->

<@override name="button">
<a class="btn btn-sm btn-primary" href="${ctx}dashboard/order/createOpenAcct" data-backdrop="static" data-toggle="modal" data-target="#myModal">添加开户</a>
<a class="btn btn-sm btn-primary" href="${ctx}dashboard/order/createTransaction" data-backdrop="static" data-toggle="modal" data-target="#myModal">添加交易</a>
<a class="btn btn-sm btn-primary" href="${ctx}dashboard/order/file" data-backdrop="static" data-toggle="modal" data-target="#myModal">生成文件</a>
</@override>

<@override name="main">
<div class="col-sm-12">
    <form class="form-inline" method="get" novalidate>
        <div class="form-group">
            <select name="orderType" class="form-control">
                <option value="">-- 全部状态 --</option>

                    <option value="1" >开户</option>
                    <option value="2" >交易</option>

            </select>
        </div>

        <button class="btn btn-purple btn-sm">
            搜索
            <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
        </button>
    </form>
</div>

<div class="space-24"></div>

<div class="col-xs-12">
    <table id="order-table" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>客户姓名</th>
            <th>证件号码</th>
            <th>产品代码</th>
            <th>申请金额</th>
            <th>交易信息</th>
            <th>交易类型</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#if (page.list)?size gt 0>
                <#list page.list as order>
                    <#include "order-tr.ftl"/>
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
    <@c.pagination url="${ctx}dashboard/order" param="orderType=${orderType}"/>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/dashboard/hop/index.js"></script>
</@override>

<@extends name="../layout.ftl"/>