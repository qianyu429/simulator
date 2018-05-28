<#assign parent_title="用户" />
<#assign title="用户管理">

<#assign status = RequestParameters.status!'' />
<#assign username = RequestParameters.username!'' />
<#assign realname = RequestParameters.realname!'' />
<#assign mobile = RequestParameters.mobile!'' />
<#assign email = RequestParameters.email!'' />

<@override name="button">
<a class="btn btn-sm btn-primary" href="${ctx}admin/user/create" data-backdrop="static" data-toggle="modal" data-target="#myModal">添加</a>
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
            <input type="text" class="form-control" name="username" value="${username}" placeholder="用户名"/>
        </div>

        <div class="form-group">
            <input type="text" class="form-control" name="realname" value="${realname}" placeholder="真实姓名"/>
        </div>

        <div class="form-group">
            <input type="text" class="form-control" name="mobile" value="${mobile}" placeholder="手机"/>
        </div>

        <div class="form-group">
            <input type="text" class="form-control" name="email" value="${email}" placeholder="邮箱"/>
        </div>

        <button class="btn btn-purple btn-sm">
            搜索
            <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
        </button>
    </form>
</div>

<div class="space-24"></div>

<div class="col-xs-12">
    <table id="user-table" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>真实姓名</th>
            <th>状态</th>
            <th>手机号</th>
            <th>邮箱</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#if (page.list)?size gt 0>
                <#list page.list as user>
                    <#include "user-tr.ftl"/>
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
    <@c.pagination url="${ctx}admin/user" param="status=${status}&username=${username}&realname=${realname}&mobile=${mobile}&email=${email}"/>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/admin/user/index.js"></script>
</@override>

<@extends name="../layout.ftl"/>