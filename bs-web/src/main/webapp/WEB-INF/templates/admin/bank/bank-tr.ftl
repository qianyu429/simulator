<tr id="bank-${bank.id}">
    <td>${bank.id}</td>
    <td>${bank.bankNo!''}</td>
    <td>${bank.bankName!''}</td>
    <td>${bank.merId!''}</td>
    <td>${bank.code!''}</td>
    <td><#include "status.ftl"></td>
    <td>${bank.adminRealname!''}</td>
    <td>${bank.createdTime?datetime}</td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}admin/bank/${bank.id}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}admin/bank/${bank.id}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑银行信息</a>
                </li>
                <li>
                    <a title="${bank.bankName}" data-role="delete-bank" data-url="${ctx}admin/bank/${bank.id}/delete">
                        删除银行
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>