<#assign parent_title="首页" />
<#assign title="工作台">

<@override name="main">
<div class="alert alert-block alert-success">
    <button type="button" class="close" data-dismiss="alert">
        <i class="ace-icon fa fa-times"></i>
    </button>

    <i class="ace-icon fa fa-check green"></i>

    欢迎使用
    <strong class="green">
        ${appName}
        <small>(${version})</small>
    </strong>,
    祝您工作愉快!
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/dashboard/index.js"></script>
</@override>

<@extends name="layout.ftl"/>
