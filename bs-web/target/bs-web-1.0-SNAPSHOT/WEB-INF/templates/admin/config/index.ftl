<#assign parent_title="系统" />
<#assign title="配置管理">

<#assign status = RequestParameters.status!'' />
<#assign grp = RequestParameters.grp!'' />
<#assign k = RequestParameters.k!'' />
<#assign val = RequestParameters.val!'' />

<@override name="button">
<a class="btn btn-sm btn-primary" href="${ctx}admin/config/create" data-backdrop="static" data-toggle="modal" data-target="#myModal">添加</a>
</@override>

<@override name="main">
<div class="col-sm-12">
    <form class="form-inline" method="get" novalidate>
        <div class="form-group">
            <select name="status" class="form-control">
                <option value="">-- 全部状态 --</option>
                <#list enums["com.shhxzq.bs.constants.StatusEnum"]?values as enum>
                    <option value="${enum.status}" <#if status=='${enum.status}'>selected</#if>>${enum.value}</option>
                </#list>
            </select>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="grp" value="${grp}" placeholder="组"/>
        </div>

        <div class="form-group">
            <input type="text" class="form-control" name="k" value="${k}" placeholder="键"/>
        </div>

        <div class="form-group">
            <input type="text" class="form-control" name="val" value="${val}" placeholder="值"/>
        </div>

        <button class="btn btn-purple btn-sm">
            搜索
            <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
        </button>
    </form>
</div>

<div class="space-24"></div>

<div class="col-xs-12">
    <table id="config-table" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>用户ID</th>
            <th>组</th>
            <th>键</th>
            <th>值</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#if (page.list)?size gt 0>
                <#list page.list as config>
                    <#include "config-tr.ftl"/>
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
    <@c.pagination url="${ctx}admin/config" param="status=${status}&grp=${grp}&k=${k}&val=${val}"/>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/admin/config/index.js"></script>
</@override>

<@extends name="../layout.ftl"/>