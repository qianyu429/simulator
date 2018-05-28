<tr id="transaction-${transaction.id}">
    <td>${transaction.id}</td>
    <td>${transaction.beSer!''}</td>
    <td>${transaction.merId!''}</td>
    <td><a data-toggle="modal" data-target="#myModal" href="${ctx}admin/bank/no/${transaction.bankNo}"><#include "bank-no.ftl"></a></td>
    <td>${transaction.accoNo!''}</td>
    <td><#include "trans-type.ftl"></td>
    <td><#include "status.ftl"></td>
    <td>${transaction.workDay!''}</td>
    <td>${transaction.createdTime?datetime}</td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}dashboard/transaction/${transaction.id}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}dashboard/transaction/${transaction.id}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑交易信息</a>
                </li>
                <li>
                    <a title="${transaction.beSer}" data-role="delete-transaction" data-url="${ctx}dashboard/transaction/${transaction.id}/delete">
                        删除交易
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>