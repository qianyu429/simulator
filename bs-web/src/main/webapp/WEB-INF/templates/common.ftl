<#macro base_menu>
    <#if menus??>
        <#list menus as menu>
        <li id="${menu.name}">
            <a <#if menu.leaf?? && menu.leaf?size gt 0>href="#" class="dropdown-toggle"
               <#else>href="${menu.url}"</#if>>
                <i class="${menu.icon}"></i>
                <span class="menu-text"> ${menu.description} </span>
                <#if menu.leaf?? && menu.leaf?size gt 0><b class="arrow fa fa-angle-down"></b></#if>
            </a>
            <b class="arrow"></b>

            <#if menu.leaf?? && menu.leaf?size gt 0>
                <ul class="submenu">
                    <#list menu.leaf as menuleaf>
                        <li id="${menuleaf.name}">
                            <a href="${menuleaf.url}">
                                <i class="menu-icon fa fa-caret-right"></i>
                            ${menuleaf.description}
                            </a>
                        </li>
                    </#list>
                </ul>
            </#if>
        </li>
        </#list>
    </#if>
</#macro>

<#macro dashboard_menu>
    <@app_dashboard_menus>
        <@base_menu />
    </@app_dashboard_menus>
</#macro>

<#macro admin_menu>
    <@app_admin_menus>
        <@base_menu />
    </@app_admin_menus>
</#macro>

<#--分页组件-->
<#macro pagination url param="">
    <#if (page.list)?? && page.total gt 0>
    <div class="pull-right">
        <ul class="pagination">
            <li><a href="javascript:" class="page-info">第 ${page.startRow}~${page.endRow} 条, 共 ${page.total} 条,
                第 ${page.pageNum} 页,
                共 ${page.pages} 页</a></li>
        </ul>
    </div>
    <div class="pull-left">
        <ul class="pagination">
            <#if page.hasPreviousPage>
                <li>
                    <a href="${url}?p=1<#if param?has_content>&${param}</#if>">
                        <i class="ace-icon fa fa-angle-double-left"></i>
                    </a>
                </li>
                <li>
                    <a href="${url}?p=${page.prePage}<#if param?has_content>&${param}</#if>">
                        <i class="ace-icon fa fa-angle-left"></i>
                    </a>
                </li>
            <#else>
                <li>
                    <a href="javascript:">
                        <i class="ace-icon fa fa-angle-double-left"></i>
                    </a>
                </li>
                <li>
                    <a href="javascript:">
                        <i class="ace-icon fa fa-angle-left"></i>
                    </a>
                </li>
            </#if>

            <#list page.navigatepageNums as nav>
                <#if nav == page.pageNum>
                    <li class="active">
                        <a href="javascript:">${nav}</a>
                    </li>
                <#else>
                    <li>
                        <a href="${url}?p=${nav}<#if param?has_content>&${param}</#if>">${nav}</a>
                    </li>
                </#if>
            </#list>

            <#if page.hasNextPage>
                <li>
                    <a href="${url}?p=${page.nextPage}<#if param?has_content>&${param}</#if>">
                        <i class="ace-icon fa fa-angle-right"></i>
                    </a>
                </li>

                <li>
                    <a href="${url}?p=${page.pages}<#if param?has_content>&${param}</#if>">
                        <i class="ace-icon fa fa-angle-double-right"></i>
                    </a>
                </li>
            <#else>
                <li>
                    <a href="javascript:">
                        <i class="ace-icon fa fa-angle-right"></i>
                    </a>
                </li>

                <li>
                    <a href="javascript:">
                        <i class="ace-icon fa fa-angle-double-right"></i>
                    </a>
                </li>
            </#if>
        </ul>
    </div>
    </#if>
</#macro>

<#macro startInput id label>
<div class="form-group">
    <label for="${id}" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">${label}</label>

<div class="col-xs-12 col-sm-6">
<span class="block input-icon input-icon-right">
</#macro>

<#macro endInput>
    <i class="ace-icon fa fa-times-circle hide"></i>
    <i class="ace-icon fa fa-check-circle hide"></i>
</span>
</div>
    <div class="help-block col-xs-12 col-sm-reset inline"></div>
</div>
</#macro>

<#macro modalButton>
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
${cancel}
</button>

<button class="btn btn-sm btn-primary" id="modal-btn"
        data-loading-text="正在保存..." data-toggle="form-submit" data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
${submit}
</button>
</#macro>

<#macro input id label value="" readonly="false">
    <@startInput id label />
        <input type="text" id="${id}" name="${id}" value="${value}" class="form-control" <#if readonly=="true">readonly</#if>
    <@endInput />
</#macro>

<#macro readonlyInput id label value="">
    <@startInput id label />
<input type="text" id="${id}" name="${id}" value="${value}" class="form-control" readonly>
    <@endInput />
</#macro>

<#macro numInput id label value="">
    <@startInput id label />
<input type="number" id="${id}" name="${id}" value="${value}" class="form-control">
    <@endInput />
</#macro>

<#macro tagInput id label items key val>
<div class="form-group">
    <label for="${id}" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">${label}
        <div class="help-button pull-right" data-container="body" data-rel="${id}" data-toggle="popover"
             data-placement="left">?
        </div>
    </label>
<div class="col-xs-12 col-sm-6">
<span class="block input-icon input-icon-right">
    <input id="${id}" name="${id}"
           value="<#list items as item>${item[key]}:${item[val]}<#if item_has_next>,</#if></#list>"
           class="form-control my-tag">
    <@endInput />
</#macro>

<#macro select id label items val text selected="">
    <@startInput id label />
    <select id="${id}" name="${id}" class="form-control">
        <#list items as item>s
            <option value="${item[val]}"<#if selected==item[val]?string> selected</#if>>${item[text]}<#if item[text]!="全部"?string>(${item[val]})</#if></option>
        </#list>
    </select>
    <@endInput />
</#macro>

<#macro selectWithDft id label items val text selected>
    <@startInput id label />
    <select id="${id}" name="${id}" class="form-control">
        <option value="默认">不强制改变</option>
        <#list items as item>
            <option value="${item[val]}"<#if selected==item[val]?string> selected</#if>>${item[text]}(${item[val]})</option>
        </#list>
    </select>
    <@endInput />
</#macro>

<#macro actions>
    <button class="btn btn-info" data-toggle="form-submit" data-loading-text="正在保存...">
        <i class="ace-icon fa fa-check bigger-110"></i>
    ${submit}
    </button>

    &nbsp; &nbsp; &nbsp;
    <button class="btn" type="reset">
        <i class="ace-icon fa fa-undo bigger-110"></i>
    ${cancel}
    </button>
</#macro>

<#macro substring str end>
    <#if str??>
        <#if str?length gt end>
        ${str?substring(0, end)}...
        <#else>
        ${str}
        </#if>
    </#if>
</#macro>