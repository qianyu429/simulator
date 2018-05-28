<#if transaction.transType == "verify">
<span class="label label-pink arrowed-in">鉴权</span>
<#elseif transaction.transType == "pay">
<span class="label label-primary arrowed-in">申购</span>
<#elseif transaction.transType == "redeem">
<span class="label label-purple arrowed-in">赎回</span>
<#elseif transaction.transType == "interRedeem">
<span class="label label-purple arrowed-in">赎回(跨行)</span>
</#if>