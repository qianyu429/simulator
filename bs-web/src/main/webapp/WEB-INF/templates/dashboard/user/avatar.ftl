<#assign parent_title="个人" />
<#assign title="修改头像" />

<@override name="main">
<div class="col-xs-12">
    <form id="user-avatar-form" class="form-horizontal" method="post" enctype="multipart/form-data"
          action="${ctx}dashboard/user/${user.id}/avatar">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="title"> 当前Logo </label>

            <div class="col-xs-12 col-sm-5">
                <span class="profile-picture">
                    <img class="editable img-responsive" src="
                    <#if user.largeAvatar?has_content>
                         ${ctx}${user.largeAvatar}"
                    <#else>
                         ${ctx}static/ace/assets/avatars/profile-pic.jpg"
                    </#if>>
                        </img>
                </span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="title"> 新Logo </label>

            <div class="col-xs-12 col-sm-5">
                <input type="file" name="avatar" id="avatar">
                <div class="help-block">
                    请上传 png、gif、jpg、jpeg 格式的图片文件，文件大小不能超过2MB。<br>
                    建议上传一张 200*200 像素或等比例的图片。
                </div>
            </div>
        </div>

        <div class="clearfix form-actions">
            <div class="col-md-offset-4 col-md-8">
                <button class="btn btn-info" data-toggle="form-submit" data-loading-text="正在上传...">
                    <i class="ace-icon fa fa-check bigger-110"></i>
                    上传
                </button>
            </div>
        </div>

    </form>
</div>
</@override>

<@override name="script">
<script src="${ctx}static/app/js/dashboard/user/avatar.js"></script>
</@override>

<@extends name="../layout.ftl"/>
