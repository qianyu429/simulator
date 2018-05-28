<#assign title="提交">


<body onload="document.getElementById('form').submit();>
<@override name="page_main">

<form action="http://localhost:8080/epay/fundAccess.do" method="POST" >
    <input  name="cryptograph" value="0002|9901001|1|123456789012345678|6554019209387168|张三|http://localhost:8080/cmbcgw/test|123456789" style="width:800px;"\><br/>
    <input type="submit" name="submit" value="提交"  style="width:800px;"/>
</form>


</@override>


<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>
</body>
<@extends name="../../layout.ftl"/>
