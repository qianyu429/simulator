$(function(){
    $('#admin-user').addClass('active open');
    $('#admin-role-manage').addClass('active');

    $('#role-table').on('click', 'a[data-role=status-role]', function () {
        var $this = $(this);
        var url = $this.data('url');
        $.get(url, function (html) {
            var $tr = $(html);
            $('#' + $tr.attr('id')).replaceWith($tr);
        })
    });
});