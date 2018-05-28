<@override name="modal-title">生成文件</@override>

<@override name="modal-body">
<link rel="stylesheet" href="${ctx}static/ace/assets/css/bootstrap-datetimepicker.min.css" />

<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}dashboard/order/genFile">
    <div class="row">

        <div class="form-group" id="orderTypeGroup">
            <label for="orderType" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">交易类型</label>
            <div class="col-xs-12 col-sm-6">
                <span class="block input-icon input-icon-right">
                    <select id="orderType" name="orderType" class="form-control">
                        <option value="1">开户</option>
                        <option value="2">交易</option>
                    </select>
                    <i class="ace-icon fa fa-times-circle hide"></i>
                    <i class="ace-icon fa fa-check-circle hide"></i>
                </span>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline"></div>
        </div>

    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modalButton />

<script src="${ctx}static/libs/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}static/libs/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<#--<script src="${ctx}static/app/js/dashboard/dz/create-modal.js"></script>-->
</@override>

<@extends name="../../modal-layout.ftl"/>