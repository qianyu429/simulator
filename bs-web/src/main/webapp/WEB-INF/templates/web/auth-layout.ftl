<@override name="page_main">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-offset-4 col-sm-4 col-sm-offset-4">
                <div class="space-26"></div>
                <@block name="content"/>
            </div>
        </div>
    </div>
</div>
</@override>

<@override name="page_script">
    <@block name="script"/>
</@override>
<@extends name="../layout.ftl"/>
