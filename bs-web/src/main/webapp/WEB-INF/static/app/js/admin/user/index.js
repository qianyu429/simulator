$(function () {
    $('#admin-user').addClass('active open');
    $('#admin-user-manage').addClass('active');

    var showNotify = function (response) {
        Notify.success("删除用户成功");
    };

    var $table = $('#user-table');
    $table.on('click', 'a[data-role=delete-user]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");
        $.messager.confirm("警告", "确定删除" + title + "吗?", function () {
            $.get(url, function () {
                    $trigger.parents('tr').remove();
                })
                .success(showNotify)
        });
    });

    $table.on('click', 'a[data-role=status-user]', function () {
        var $this = $(this);
        var url = $this.data('url');
        $.get(url, function (html) {
            var $tr = $(html);
            $('#' + $tr.attr('id')).replaceWith($tr);
        })
    });
});