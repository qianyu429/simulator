<@override name="page_style">
    <@block name="style"/>
</@override>

<@override name="page_main">
    <#include "sidebar.ftl"/>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <ul class="breadcrumb">
                <li>
                    <i class="menu-icon fa fa-dashboard"></i>
                    <a href="${ctx}dashboard">工作台</a>
                </li>
                <li class="active">
                    ${parent_title!''}
                </li>
            </ul>

            <div class="nav-search" id="nav-search">
                <form class="form-search">
                    <span class="input-icon">
                        <input type="text" name="q" placeholder="Search ..." class="nav-search-input"
                               id="nav-search-input"
                               autocomplete="off">
                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                    </span>
                </form>
            </div>
        </div>

        <div class="page-content">

            <#--<div class="ace-settings-container" id="ace-settings-container">-->
                <#--<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">-->
                    <#--<i class="ace-icon fa fa-cog bigger-130"></i>-->
                <#--</div>-->

                <#--<div class="ace-settings-box clearfix" id="ace-settings-box">-->
                    <#--<div class="pull-left width-50">-->
                        <#--<!-- #section:settings.skins &ndash;&gt;-->
                        <#--<div class="ace-settings-item">-->
                            <#--<div class="pull-left">-->
                                <#--<select id="skin-colorpicker" class="hide">-->
                                    <#--<option data-skin="no-skin" value="#438EB9">#438EB9</option>-->
                                    <#--<option data-skin="skin-1" value="#222A2D">#222A2D</option>-->
                                    <#--<option data-skin="skin-2" value="#C6487E">#C6487E</option>-->
                                    <#--<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>-->
                                <#--</select>-->
                            <#--</div>-->
                            <#--<span>&nbsp; Choose Skin</span>-->
                        <#--</div>-->

                        <#--<div class="ace-settings-item">-->
                            <#--<input type="checkbox" value="1" name="navbar" class="ace ace-checkbox-2"-->
                                   <#--id="ace-settings-navbar">-->
                            <#--<label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>-->
                        <#--</div>-->

                        <#--<div class="ace-settings-item">-->
                            <#--<input type="checkbox" value="1" name="sidebar" class="ace ace-checkbox-2"-->
                                   <#--id="ace-settings-sidebar">-->
                            <#--<label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>-->
                        <#--</div>-->

                        <#--<div class="ace-settings-item">-->
                            <#--<input type="checkbox" value="1" name="breadcrumbs" class="ace ace-checkbox-2"-->
                                   <#--id="ace-settings-breadcrumbs">-->
                            <#--<label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>-->
                        <#--</div>-->

                        <#--<div class="ace-settings-item">-->
                            <#--<input type="checkbox" value="1" name="container" class="ace ace-checkbox-2"-->
                                   <#--id="ace-settings-add-container">-->
                            <#--<label class="lbl" for="ace-settings-add-container">-->
                                <#--Inside-->
                                <#--<b>.container</b>-->
                            <#--</label>-->
                        <#--</div>-->
                    <#--</div>-->

                    <#--<div class="pull-left width-50">-->
                        <#--<div class="ace-settings-item">-->
                            <#--<input type="checkbox" value="1" name="hover" class="ace ace-checkbox-2"-->
                                   <#--id="ace-settings-hover">-->
                            <#--<label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>-->
                        <#--</div>-->

                        <#--<div class="ace-settings-item">-->
                            <#--<input type="checkbox" value="1" name="compact" class="ace ace-checkbox-2"-->
                                   <#--id="ace-settings-compact">-->
                            <#--<label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>-->
                        <#--</div>-->

                        <#--<div class="ace-settings-item">-->
                            <#--<input type="checkbox" value="1" name="highlight" class="ace ace-checkbox-2"-->
                                   <#--id="ace-settings-highlight">-->
                            <#--<label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>-->
                        <#--</div>-->
                    <#--</div>-->
                <#--</div>-->
            <#--</div>-->

            <div class="page-header">
                <h1>
                ${title!''}
                    <small class="pull-right">
                        <@block name="button"/>
                    </small>
                </h1>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <@block name="main"/>
                </div>
            </div>
        </div>
    </div>
</div>
</@override>

<@override name="page_script">
    <@block name="script"/>
</@override>

<@extends name="../layout.ftl"/>
