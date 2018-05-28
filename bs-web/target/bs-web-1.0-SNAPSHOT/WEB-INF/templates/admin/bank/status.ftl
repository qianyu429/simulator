<#if bank.status == "able">
<a href="javascript:" data-role="status-bank"
   data-url="${ctx}admin/bank/${bank.id}/disable">
    <span class="label label-success arrowed-in">已启用</span>
</a>
<#elseif bank.status == "disable">
<a href="javascript:" data-role="status-bank"
   data-url="${ctx}admin/bank/${bank.id}/able">
    <span class="label label-danger arrowed-in">已禁用</span>
</a>
</#if>