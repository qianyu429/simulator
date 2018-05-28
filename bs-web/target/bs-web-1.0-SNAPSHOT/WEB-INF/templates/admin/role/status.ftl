<#if role.status == "able">
<a href="javascript:" data-role="status-role"
   data-url="${ctx}admin/role/${role.id}/disable">
    <span class="label label-success arrowed-in">已启用</span>
</a>
<#elseif role.status == "disable">
<a href="javascript:" data-role="status-role"
   data-url="${ctx}admin/role/${role.id}/able">
    <span class="label label-danger arrowed-in">已禁用</span>
</a>
</#if>