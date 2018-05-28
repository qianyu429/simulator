<#assign parent_title="三方金融平台" />
<#assign title="拉卡拉" />

<@override name="button">
<a id="0CIF000007-btn" class="btn btn-sm btn-primary" data-backdrop="static">开户</a>
<a id="0CTSTRD001-btn" class="btn btn-sm btn-primary" data-backdrop="static">现金宝交易</a>
<a id="1CTSTRQ001-btn" class="btn btn-sm btn-primary" data-backdrop="static">单笔订单查询</a>
<a id="1CTSPRD002-btn" class="btn btn-sm btn-primary" data-backdrop="static">产品状态查询</a>
<a id="1CTSSHA005-btn" class="btn btn-sm btn-primary" data-backdrop="static">份额查询接口</a>
<a id="1CTSTRQ004-btn" class="btn btn-sm btn-primary" data-backdrop="static">交易查询接口</a>
<a id="1CTSSHA007-btn" class="btn btn-sm btn-primary" data-backdrop="static">现金宝快取、普取额度查询</a>


</@override>

<@override name="main">
<div id="0CIF000004" class="col-xs-12">
    <form class="form-horizontal" role="form" id="user-profile-form" method="post"
          action="${ctx}dashboard/order/post2lakalaHop">
        <div id="hjg">
            <div class="row">
                <div class="form-group">
                    <label for="" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">Request:</label>
                    <div class="col-xs-12 col-sm-6">
                                <span class="block input-icon input-icon-right">
                                    <textarea id="request" name="request" class="autosize-transition form-control"
                                              style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 300px;">${request}</textarea>

                                 </span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">Response:</label>
                    <div class="col-xs-12 col-sm-6">
                                <span class="block input-icon input-icon-right">

                                    <textarea id="response" name="response" class="autosize-transition form-control"
                                              style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 300px;">${response}</textarea>

                                 </span>
                    </div>
                </div>
            </div>


        </div>

        <div class="clearfix form-actions">
            <div class="col-md-offset-4 col-md-8">
                <@c.actions />
            </div>
        </div>
    </form>

</div>

</@override>

<@override name="script">
<script src="${ctx}static/app/js/dashboard/hop/lakala.js"></script>
</@override>

<@extends name="../layout.ftl"/>
