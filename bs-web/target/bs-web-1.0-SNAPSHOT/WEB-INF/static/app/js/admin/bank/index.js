$(function () {
    $('#admin-bank').addClass('active open');
    $('#admin-bank-manage').addClass('active');

    var showNotify = function (response) {
        Notify.success("删除银行成功");
    };

    var $table = $('#bank-table');
    $table.on('click', 'a[data-role=delete-bank]', function () {
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

    $table.on('click', 'a[data-role=status-bank]', function () {
        var $this = $(this);
        var url = $this.data('url');
        $.get(url, function (html) {
            var $tr = $(html);
            $('#' + $tr.attr('id')).replaceWith($tr);
        })
    });
});