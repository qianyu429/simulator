<#assign title="提交">


<body>
<@override name="page_main">
<form id="form" action="http://localhost:8080/boscartoon/pagesign.do" method="POST">
<input type="hidden" name="cell" value="2017051100001362" \>
<input type="submit" />
</form>


</@override>


<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>
</body>
<@extends name="../../layout.ftl"/>
