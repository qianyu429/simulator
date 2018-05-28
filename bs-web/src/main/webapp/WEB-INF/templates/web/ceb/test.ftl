<#assign title="登录">

<@override name="page_main">


<form action="http://localhost:8080/pwap/AgreeEpaySignPre.do" method="POST">
    <input type="hidden" name="certNo" value="310101199004100013" \>
    <input type="hidden" name="certType" value="1" \>
    <input type="hidden" name="Plain" value="Y2VydFR5cGU9MQpjZXJ0Tm89MzEwMTAxMTk5MDA0MTAwMDEzCm5hbWU9MTY4MDMxMzc5MQplbWFpbD0zMTAxMDExOTkwMDQxMDAwMTMKbWVySWQ9MzcwMzEwMDAwMDk0CmNlbGw9CnVybD1odHRwOi8vMTIxLjQwLjY2LjE3Njo4ODg4L2VjYlBheS9jY25vdGlmeS5zZXJ2aWNl" \>
    <input type="hidden" name="TransName" value="OSReq" \>
    <input type="hidden" name="Signature" value="5DMxWIL1Tx93B0INvNnvYDX6RDuVowG7GfikQ4SjGz+YcAZchOpExQHSv/GFh08oqP1xTt9qJp6dzua0fzWGebg6UDCh+kpEwF+VykgzNxy5c1Xe1PEnYbYRehom8v/b83ivlUpJfchxDJtR6X58HBsb8rpewYiYxcRjdy1a3+Q=" \>
    <input type="hidden" name="name" value="1680313791" \>
    <input type="hidden" name="merId" value="370310000094" \>
    <input type="hidden" name="cell" value="" \>
    <input type="hidden" name="email" value="310101199004100013" \>
    <input type="hidden" name="url" value="http://10.199.101.211:8088/V1/pages/account/web_binding_result.html" \>
    <input type="submit" />
</form>


</@override>

<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>

<@extends name="../../layout.ftl"/>
