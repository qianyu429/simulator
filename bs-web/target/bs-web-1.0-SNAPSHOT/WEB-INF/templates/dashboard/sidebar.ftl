<div id="sidebar" class="sidebar responsive" xmlns="http://www.w3.org/1999/html">
    <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="ace-icon fa fa-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="ace-icon fa fa-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="ace-icon fa fa-users"></i>
            </button>

            <button class="btn btn-danger">
                <i class="ace-icon fa fa-cogs"></i>
            </button>
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div>


    <ul class="nav nav-list">
        <li id="dashboard">
            <a href="${ctx}dashboard">
                <i class="menu-icon fa fa-tachometer"></i>
                <span class="menu-text"> 工作台 </span>
            </a>

            <b class="arrow"></b>
        </li>

        <li id="bankmock">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-desktop"></i>
                            <span class="menu-text">
                                银行模拟器
                            </span>

                <b class="arrow fa fa-angle-down"></b>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <@c.dashboard_menu/>
            </ul>
        </li>

        <li id="thirdparty">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-desktop"></i>
                            <span class="menu-text">
                                三方金融平台
                            </span>
                <b class="arrow fa fa-angle-down"></b>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <li id="CFT">
                    <a href="/dashboard/order">
                        <i class="menu-icon fa fa-caret-right"></i>
                        财付通
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
            <b class="arrow"></b>
            <ul class="submenu">
                <li id="songguo">
                    <a href="/dashboard/order/songguo">
                        <i class="menu-icon fa fa-caret-right"></i>
                        松果金融
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
            <b class="arrow"></b>
            <ul class="submenu">
                <li id="lakala">
                    <a href="/dashboard/order/lakala">
                        <i class="menu-icon fa fa-caret-right"></i>
                        拉卡拉
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
            <b class="arrow"></b>
            <ul class="submenu">
                <li id="lihe">
                    <a href="/dashboard/order/lihe">
                        <i class="menu-icon fa fa-caret-right"></i>
                        利和
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>

        </li>
    </ul>


    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
        <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
           data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
</div>

