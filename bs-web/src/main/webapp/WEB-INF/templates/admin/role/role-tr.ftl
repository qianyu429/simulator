<tr id="role-${role.id}">
    <td>${role.id}</td>
    <td><a data-toggle="modal" href="${ctx}admin/role/${role.id}" data-target="#myModal">${role.name!''}</a></td>
    <td>${role.description!''}</td>
    <td><#include "status.ftl"></td>
    <td>${role.createdTime?datetime}</td>
    <td>${role.updatedTime?datetime}</td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}admin/role/${role.id}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}admin/role/${role.id}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑角色信息</a>
                </li>
            <@shiro.hasRole name="ROLE_SYS_ADMIN">
                <li>
                    <a href="${ctx}admin/role/${role.id}/dashboard" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">设置工作台权限</a>
                </li>
                <li>
                    <a href="${ctx}admin/role/${role.id}/admin" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">设置后台权限</a>
                </li>
            </@shiro.hasRole>
            </ul>
        </div>
    </td>
</tr>