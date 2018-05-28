<#if dz.status == "able">
<a href="javascript:" data-role="status-dz"
   data-url="${ctx}dashboard/dz/${dz.id}/disable">
    <span class="label label-success arrowed-in">已启用</span>
</a>
<#elseif dz.status == "disable">
<a href="javascript:" data-role="status-dz"
   data-url="${ctx}dashboard/dz/${dz.id}/able">
    <span class="label label-danger arrowed-in">已禁用</span>
</a>
</#if>