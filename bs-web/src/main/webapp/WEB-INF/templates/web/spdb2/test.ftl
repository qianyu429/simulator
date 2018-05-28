<#assign title="登录">


<body onload="document.getElementById('form').submit();>
<@override name="page_main">

<form action="http://localhost:8080/payment/main" method="POST">
    <input type="hidden" name="transName" value="XYQY" \>
    <input type="hidden" name="Plain" value="TranAbbr=XYQY|Merc_id=2017030300113364|MercDtTm=20170303172701|CheckFlag=1|IdType=1|IdNo=31010219850101234X|Account=6217922450500740|PayCardName=%B2%E2%CA%D4%B4%F3%C9%B3%B7%A2%B5%C4%D4%B12|MercCode=983708160001401|MercUrl=http://10.199.101.212:8090/bankEngine/authenticate/spdb2AuthGetResponsion.service" \>
    <input type="hidden" name="Signature" value="420ff071b25f47cbb77d8da6e4aeaa33cc0dfb84c1d0ae8de01888722f6fb577e05f8026560e6be05dedb4ca544d37cb510d5f31d3c9789f4d7806f82ccb7a471ad966c0af47af42b9941123cc6991d1af4dfb6bac83af902012a2d83a481ecce252671c1518c4469d69eb9a09fccdcd04b435cbde6aaba70f7be2f6750d22e5" \>
    <input type="submit" />
</form>


</@override>


<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>
</body>
<@extends name="../../layout.ftl"/>
