$(function () {
    $('#dashboard-transaction').addClass('active open');
    $('#dashboard-transaction-manage').addClass('active');
    $("#bankmock").addClass("active open");

    var showNotify = function (response) {
        Notify.success("删除交易成功");
    };

    var $table = $('#transaction-table');
    $table.on('click', 'a[data-role=delete-transaction]', function () {
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

    $table.on('click', 'a[data-role=status-transaction]', function () {
        var $this = $(this);
        var url = $this.data('url');
        $.get(url, function (html) {
            var $tr = $(html);
            $('#' + $tr.attr('id')).replaceWith($tr);
        })
    });
});