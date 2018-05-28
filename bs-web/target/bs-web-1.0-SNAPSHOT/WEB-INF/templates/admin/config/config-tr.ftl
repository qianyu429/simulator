<tr id="config-${config.id}">
    <td>${config.id}</td>
    <td>${config.userId!''}</td>
    <td>${config.grp!''}</td>
    <td>${config.k!''}</td>
    <td>${config.val!''}</td>
    <td><#include "status.ftl"></td>
    <td>${config.createdTime?datetime}</td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}admin/config/${config.id}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}admin/config/${config.id}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑配置信息</a>
                </li>
                <li>
                    <a title="配置信息" data-role="delete-config" data-url="${ctx}admin/config/${config.id}/delete">
                        删除配置
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>