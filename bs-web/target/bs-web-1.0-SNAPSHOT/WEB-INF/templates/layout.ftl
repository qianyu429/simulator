<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>${appName} - ${title!''}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="renderer" content="webkit">

    <link rel="icon" type="image/ico" href="${ctx}static/app/favicon.ico">

    <!-- basic styles -->
    <link rel="stylesheet" href="${ctx}static/ace/assets/css/bootstrap.css"/>
    <link rel="stylesheet" href="${ctx}static/ace/assets/css/font-awesome.css"/>
    <link rel="stylesheet" href="${ctx}static/ace/assets/css/jquery.gritter.css"/>

    <!-- fonts -->
    <link rel="stylesheet" href="${ctx}static/ace/assets/css/ace-fonts.css"/>

    <!-- skins -->
    <#--<link rel="stylesheet" href="${ctx}static/ace/assets/css/ace-skins.css"/>-->

    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx}static/ace/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${ctx}static/ace/assets/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

    <link rel="stylesheet" href="${ctx}static/ace/assets/css/ace-rtl.css"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${ctx}static/ace/assets/css/ace-ie.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}static/app/css/app.css"/>

<@block name="page_style"/>

    <!--[if lte IE 8]>
    <script src="${ctx}static/libs/html5shiv.min.js"></script>
    <script src="${ctx}static/libs/respond.min.js"></script>
    <![endif]-->
</head>

<body class="no-skin">

<#include "navbar.ftl">

<div class="main-container" id="main-container">

<@block name="page_main"/>

<#include "footer.ftl">

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="message-loading-overlay"><i class="ace-icon fa fa-spinner fa-spin orange bigger-125"></i></div>
        </div>
    </div>
</div>

<div class="modal fade" id="myLargeModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="message-loading-overlay"><i class="ace-icon fa fa-spinner fa-spin orange bigger-125"></i></div>
        </div>
    </div>
</div>

<script src="${ctx}static/libs/jquery/jquery.min.js"></script>
<script src="${ctx}static/libs/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}static/libs/jquery/jquery.bootstrap.min.js"></script>
<script src="${ctx}static/libs/jquery/jquery.form.min.js"></script>
<script src="${ctx}static/libs/jquery/jquery.validate.min.js"></script>
<script src="${ctx}static/libs/jquery/jquery.validate.extends.js"></script>
<script src="${ctx}static/libs/jquery/jquery.gritter.min.js"></script>
<script src="${ctx}static/libs/jquery/jquery.serialize.js"></script>
<script src="${ctx}static/ace/assets/js/ace-extra.min.js"></script>
<script src="${ctx}static/ace/assets/js/ace-elements.min.js"></script>
<script src="${ctx}static/ace/assets/js/ace.min.js"></script>
<script>var ctx = '${ctx}';</script>
<#--<script src="${ctx}static/app/js/config.js"></script>-->
<script src="${ctx}static/app/js/app.js"></script>
<@block name="page_script"/>
</body>
</html>