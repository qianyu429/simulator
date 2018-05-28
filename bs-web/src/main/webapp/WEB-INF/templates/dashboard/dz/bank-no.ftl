<#if dz.bankNo == "999">
中国银联
<#elseif dz.bankNo == "599">
上海证通
<#elseif dz.bankNo == "027">
民生超网
<#elseif dz.bankNo == "888">
广东南粤
<#else>
${dz.bankNo!''}
</#if>