<#assign title="服务器内部错误">

<@override name="error">

<h3 class="lighter smaller"><#if exception??>${exception.message!'发生未知错误，请联系网站管理员或稍后再次尝试！'}<#else>发生未知错误，请联系网站管理员或稍后再次尝试！</#if></h3>

</@override>

<@extends name="error-layout.ftl"/>