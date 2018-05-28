<#if transaction.status == "able">
<a href="javascript:" data-role="status-transaction"
   data-url="${ctx}dashboard/transaction/${transaction.id}/disable">
    <span class="label label-success arrowed-in">已启用</span>
</a>
<#elseif transaction.status == "disable">
<a href="javascript:" data-role="status-transaction"
   data-url="${ctx}dashboard/transaction/${transaction.id}/able">
    <span class="label label-danger arrowed-in">已禁用</span>
</a>
</#if>