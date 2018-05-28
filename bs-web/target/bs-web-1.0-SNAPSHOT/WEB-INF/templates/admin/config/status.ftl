<#if config.status == "able">
<a href="javascript:" data-role="status-config"
   data-url="${ctx}admin/config/${config.id}/disable">
    <span class="label label-success arrowed-in">已启用</span>
</a>
<#elseif config.status == "disable">
<a href="javascript:" data-role="status-config"
   data-url="${ctx}admin/config/${config.id}/able">
    <span class="label label-danger arrowed-in">已禁用</span>
</a>
</#if>