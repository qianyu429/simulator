<#assign title="提交">


<body>
<@override name="page_main">
<form id="form" action="/boscartoon/pagesign.do" method="POST">
<input type="hidden" name="cell" value="2017051100001362" \>
<input type="hidden" name="notifyUrl" value="/boscartoon/test"\>
<input type="hidden" name="showUrl" value="/boscartoon/test"\>
<input type="submit" />
</form>


</@override>


<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>
</body>
<@extends name="../../layout.ftl"/>
