$(function () {
    $('#admin-sys').addClass('active open');
    $('#admin-config-manage').addClass('active');

    var showNotify = function (response) {
        Notify.success("删除银行成功");
    };

    var $table = $('#config-table');
    $table.on('click', 'a[data-role=delete-config]', function () {
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

    $table.on('click', 'a[data-role=status-config]', function () {
        var $this = $(this);
        var url = $this.data('url');
        $.get(url, function (html) {
            var $tr = $(html);
            $('#' + $tr.attr('id')).replaceWith($tr);
        })
    });
});