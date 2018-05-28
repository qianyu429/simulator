<tr id="dz-${dz.id}">
    <td>${dz.id}</td>
    <td><a data-toggle="modal" data-target="#myModal"
           href="${ctx}admin/bank/no/${dz.bankNo}"><#include "bank-no.ftl"></a></td>
    <td><#include "trans-type.ftl"></td>
    <td><a href="${ctx}${dz.path}" target="_blank">${dz.path}</a></td>
    <td><#include "status.ftl"></td>
    <td>${dz.createdTime?datetime}</td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}dashboard/dz/${dz.id}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a title="${dz.path!''}" data-role="delete-dz" data-url="${ctx}dashboard/dz/${dz.id}/delete">
                        删除对账文件
                    </a>
                </li>
            <#if dz.bankNo=="999" || dz.bankNo=="T14">
                <li>
                    <a data-role="push-dz" data-url="${ctx}dashboard/dz/${dz.id}/push">
                        推送给商户
                    </a>
                </li>
            </#if>
            </ul>
        </div>
    </td>
</tr>