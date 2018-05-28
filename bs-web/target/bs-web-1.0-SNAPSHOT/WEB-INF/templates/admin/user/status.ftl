<#if user.status == "able">
<a href="javascript:" data-role="status-user"
   data-url="${ctx}admin/user/${user.id}/disable">
    <span class="label label-success arrowed-in">已启用</span>
</a>
<#elseif user.status == "disable">
<a href="javascript:" data-role="status-user"
   data-url="${ctx}admin/user/${user.id}/able">
    <span class="label label-danger arrowed-in">已禁用</span>
</a>
</#if>