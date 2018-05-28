<@override name="modal-title">导入错误码</@override>

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" enctype="multipart/form-data" action="${ctx}dashboard/cmbc/import">
<div class="row">
    <div class="row form-group">
        <div class="col-md-3 control-label">
            <label for="file">请选择文件</label>
        </div>
        <div class="col-md-7 controls">
            <input type="file" name="excel" id="excel" class="form-control"/>
        </div>
    </div>
</div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/app/js/dashboard/cmbc/import-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>