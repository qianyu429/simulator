<tr id="user-${user.id}">
    <td>${user.id}</td>
    <td>
        <a href="${ctx}admin/user/${user.id}" data-toggle="modal" data-target="#myModal">${user.username}</a>
    </td>
    <td>${user.realname!''}</td>
    <td><#include "status.ftl"></td>
    <td>${user.mobile!''}</td>
    <td>${user.email!''}</td>
    <td>${user.createdTime?datetime}</td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}admin/user/${user.id}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}admin/user/${user.id}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑用户信息</a>
                </li>
            <@shiro.hasRole name="ROLE_SYS_ADMIN">
                <li>
                    <a title="${user.username}" data-role="delete-user" data-url="${ctx}admin/user/${user.id}/delete">
                        删除用户
                    </a>
                </li>

                <li>
                    <a href="${ctx}admin/user/${user.id}/roles" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">设置角色</a>
                </li>
            </@shiro.hasRole>
            </ul>
        </div>
    </td>
</tr>