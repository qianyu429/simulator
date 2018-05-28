<#assign title="登录">

<@override name="page_main">


<form action="http://localhost:8080/citiccard/cardishop/secondcard/index_wap.html" method="POST">
    <input type="hidden" name="partner_id" value="BD00" \>
    <input type="hidden" name="partner_apply_id" value="222222223" \>
    <input type="hidden" name="partner_user_id" value="5482780000038912" \>
    <input type="hidden" name="submit_date" value="20170331140126" \>
    <input type="hidden" name="sign" value="9985a9a63ccdd11003ba9128d4d66bf2+YcAZchOpExQHSv/GFh08oqP1xTt9qJp6dzua0fzWGebg6UDCh+kpEwF+VykgzNxy5c1Xe1PEnYbYRehom8v/b83ivlUpJfchxDJtR6X58HBsb8rpewYiYxcRjdy1a3+Q=" \>
    <input type="submit" />
</form>


</@override>

<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>

<@extends name="../../layout.ftl"/>
