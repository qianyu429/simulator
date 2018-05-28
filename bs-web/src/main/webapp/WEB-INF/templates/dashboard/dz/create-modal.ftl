<@override name="modal-title">手动生成对账文件</@override>

<@override name="modal-body">
<link rel="stylesheet" href="${ctx}static/ace/assets/css/bootstrap-datetimepicker.min.css" />

<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}dashboard/dz/save">
    <div class="row">
        <@c.select id="bankNo" label="银行名称" items=banks val="bankNo" text="bankName"/>

        <div class="form-group" id="transTypeGroup">
            <label for="transType" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">交易类型</label>
            <div class="col-xs-12 col-sm-6">
                <span class="block input-icon input-icon-right">
                    <select id="transType" name="transType" class="form-control">
                        <option value="">不需要交易类型的对账</option>
                        <option value="pay">申购</option>
                        <option value="redeem">赎回</option>
                    </select>
                    <i class="ace-icon fa fa-times-circle hide"></i>
                    <i class="ace-icon fa fa-check-circle hide"></i>
                </span>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline"></div>
        </div>

        <div class="form-group" id="workDayGroup">
            <label for="transType" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">对账日期</label>
            <div class="col-xs-12 col-sm-6">
                <span class="block input-icon input-icon-right">
                    <input type="datetime" id="workDay" name="workDay" class="form-control" />
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
<script src="${ctx}static/app/js/dashboard/dz/create-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>