$(function () {
    $('#admin-user').addClass('active open');
    $('#admin-menu-manage').addClass('active');

    var showRemoveNotify = function (response) {
        if (!response) {
            Notify.error("菜单删除失败。");
        } else {
            Notify.success("菜单删除成功。");
        }
    };

    var beforeEditName = function (treeId, treeNode) {
        return false;
    };

    var beforeRemove = function (treeId, treeNode) {
        if (confirm("确认删除节点 " + treeNode.name + " 吗？")) {
            $.post(ctx + "admin/menu/" + treeNode.id + "/delete", function (data, status) {
                if (status == "success") {
                    return true;
                } else {
                    return false;
                }
            });
        } else {
            return false;
        }
    };

    var addHoverDom = function (treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) {
            return;
        }
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='添加菜单' onfocus='this.blur();'></span>";
        sObj.after(addStr);

        var addBtn = $("#addBtn_" + treeNode.tId);
        if (addBtn) {
            addBtn.bind("click", function () {
                $("#myModal").modal({
                    remote: ctx + 'admin/menu/create?pid=' + treeNode.id
                });
            });
        }

        var editBtn = $("#" + treeNode.tId + "_edit");
        if (editBtn) {
            editBtn.bind("click", function () {
                $("#myModal").modal({
                    remote: ctx + 'admin/menu/' + treeNode.id + '/edit'
                });
            });
        }
    };

    var removeHoverDom = function (treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
    };

    var setting = {
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom
        },
        edit: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeEditName: beforeEditName,
            beforeRemove: beforeRemove,
            onRemove: showRemoveNotify
        }
    };

    $.fn.zTree.init($("#admin_tree"), setting, admin_zNodes);
    $.fn.zTree.init($("#dashboard_tree"), setting, dashboard_zNodes);

});