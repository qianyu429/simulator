<@override name="modal-title">设置用户角色</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}admin/user/${userId}/roles">
    <div class="control-group">
    <#list roles as role>
        <div class="checkbox">
            <label>
                <input name="roles" type="checkbox" value="${role.id}" class="ace" ${user_roles?seq_contains(role.name)?string("checked", "")} />
                <span class="lbl"> ${role.description} </span>
            </label>
        </div>
    </#list>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/admin/user/roles-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>