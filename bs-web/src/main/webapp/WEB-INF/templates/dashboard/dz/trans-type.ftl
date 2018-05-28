<#if dz.transType == "verify">
<span class="label label-pink arrowed-in">鉴权</span>
<#elseif dz.transType == "pay">
<span class="label label-primary arrowed-in">申购</span>
<#elseif dz.transType == "redeem">
<span class="label label-purple arrowed-in">赎回</span>
</#if>