<#assign parent_title="三方金融平台" />
<#assign title="利和" />

<@override name="button">
<a id="0CIF000004-btn" class="btn btn-sm btn-primary" data-backdrop="static">绑卡短信验证码发送</a>
<a id="0CIF000005-btn" class="btn btn-sm btn-primary" data-backdrop="static">绑卡</a>
<a id="0CIF000006-btn" class="btn btn-sm btn-primary" data-backdrop="static">绑卡结果查询</a>
<a id="0CTSTRD001-btn" class="btn btn-sm btn-primary" data-backdrop="static">现金宝交易</a>
<a id="0CTSTRD003-btn" class="btn btn-sm btn-primary" data-backdrop="static">高端购买</a>
<a id="0CTSTRD006-btn" class="btn btn-sm btn-primary" data-backdrop="static">高端赎回</a>
<a id="1CTSTRQ001-btn" class="btn btn-sm btn-primary" data-backdrop="static">单笔交易查询</a>
<a id="1CTSPRD002-btn" class="btn btn-sm btn-primary" data-backdrop="static">产品状态查询</a>
<a id="1CTSSHA003-btn" class="btn btn-sm btn-primary" data-backdrop="static">份额查询</a>
<a id="1CTSTRQ004-btn" class="btn btn-sm btn-primary" data-backdrop="static">交易查询</a>
<a id="1CTSPRD005-btn" class="btn btn-sm btn-primary" data-backdrop="static">产品行情信息查询</a>
<a id="1CTSSHA006-btn" class="btn btn-sm btn-primary" data-backdrop="static">份额批量查询</a>
<a id="1CTSPRO001-btn" class="btn btn-sm btn-primary" data-backdrop="static">收益查询</a>


</@override>

<@override name="main">
<div id="0CIF000004" class="col-xs-12">
    <form class="form-horizontal" role="form" id="user-profile-form" method="post"
          action="${ctx}dashboard/order/post2liheHop">
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
<script src="${ctx}static/app/js/dashboard/hop/lihe.js"></script>
</@override>

<@extends name="../layout.ftl"/>
