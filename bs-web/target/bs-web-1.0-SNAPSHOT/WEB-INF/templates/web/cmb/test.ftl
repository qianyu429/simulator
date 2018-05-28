<#assign title="登录">

<@override name="page_main">


<form action="http://localhost:8080/cmb/BindPayFeeFdmApply" method="POST">
    <input type="hidden" name="McAccountNo" value="6225885740201595" \="" />
    <input type="hidden" name="CheckSum" value="57D50C09D326757C00D6021917E7BE5F" \="" />
    <input type="hidden" name="reserve1" value="A" \="" />
    <input type="hidden" name="merchant" value="HXZQ" \="" />
    <input type="hidden" name="uin" value="2016102800051535" \="" />
    <input type="hidden" name="reserve2" value="120101198911114283" \="" />
    <input type="hidden" name="url" value="http://10.199.101.211:18088/V1/pages/account/web_binding_result.html" \="" />
    <input type="hidden" name="TimeStamp" value="1028160811" \="" />
    <input type="hidden" name="username" value="糖二" \="" />
    <input type="submit" />
</form>


</@override>

<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>

<@extends name="../../layout.ftl"/>
