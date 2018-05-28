<#if transaction.bankNo == "999">
中国银联
<#elseif transaction.bankNo == "599">
上海证通
<#elseif transaction.bankNo == "027">
民生超网
<#else>
${transaction.bankNo!''}
</#if>