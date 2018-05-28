<#assign parent_title="银行" />
<#assign title="银行配置">

<@override name="button">
<a class="btn btn-sm btn-primary" href="${ctx}admin/bank/create" data-backdrop="static" data-toggle="modal" data-target="#myModal">添加</a>
</@override>

<@override name="main">
<div class="col-xs-12">
    <table id="bank-table" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>银行编号</th>
            <th>银行名称</th>
            <th>商户号</th>
            <th>银行代码</th>
            <th>状态</th>
            <th>管理员</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#if (page.list)?size gt 0>
                <#list page.list as bank>
                    <#include "bank-tr.ftl"/>
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
    <@c.pagination url="${ctx}admin/bank"/>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/admin/bank/index.js"></script>
</@override>

<@extends name="../layout.ftl"/>