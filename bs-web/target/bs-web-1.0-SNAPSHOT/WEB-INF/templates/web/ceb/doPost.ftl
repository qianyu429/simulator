<!DOCTYPE html>
<html lang="zh-cn">
<style>
    @charset "utf-8";

    /* 重置(reset) */
    html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, em, img, strong, dl, dt, dd, ol, ul, li, form, label, table, tbody, tfoot, thead, tr, th, td, footer, header, nav, section{
        margin:0px;
        padding:0px;
    }
    html,body {height:100%;-webkit-touch-callout: none;}
    table {border-collapse:collapse;border-spacing:0;}
    ul, ol, li {list-style:none}
    em, i {font-style:normal;}
    h1, h2, h3, h4{ font-weight:normal}
    img {border:0 none;}
    a {text-decoration:none;}


    *{
        -webkit-tap-highlight-color:rgba(0,0,0,0);
        -webkit-text-size-adjust:none;
    }
    input[type="button"],
    input[type="submit"],
    input[type="text"],
    input[type="password"],
    input[type="tel"],
    button {
        -webkit-appearance:none; /*去除iphone下input默认样式*/
        border-radius: 0; /*去除iphone下默认圆角*/
        -webkit-box-sizing:border-box
    }
    input, button, input:focus, button:focus{
        outline:none;
    }

    /***** 公用样式(common) *****/
    /*clear float*/
    .clearfix:after {
        content:'\20';
        display:block;
        height:0;
        clear:both;
    }
    .clearfix {*zoom:1;}
    .fl {float:left;}
    .fr {float:right;}
    .pb30{ padding-bottom:30px}
    body {
        background:#f4f4f4;
        font-family:"微软雅黑";
        color:#4d4d4d;
        min-width:320px;
    }

    .warp{
        margin:0 auto;
        margin-left:2%;
        margin-right:2%;
        margin-top:15px;
    }

    ::-webkit-input-placeholder { /* WebKit browsers */
        color:    #adadad;
    }
    :-moz-placeholder { /* Mozilla Firefox 4 to 18 */
        color:    #adadad;
    }
    ::-moz-placeholder { /* Mozilla Firefox 19+ */
        color:    #adadad;
    }
    :-ms-input-placeholder { /* Internet Explorer 10+ */
        color:    #adadad;
    }

    /* iconfont*/
    @font-face {font-family: 'iconfont';
        src: url('../font/iconfont.eot'); /* IE9*/
        src: url('../font/iconfont.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
        url('../font/iconfont.woff') format('woff'), /* chrome、firefox */
        url('../font/iconfont.ttf') format('truetype'), /* chrome、firefox、opera、Safari, Android, iOS 4.2+*/
        url('../font/iconfont.svg#iconfont') format('svg'); /* iOS 4.1- */
    }
    .iconfont{
        font-family:"iconfont" !important;
        font-style:normal;
        -webkit-font-smoothing: antialiased;
        -webkit-text-stroke-width: 0.2px;
        -moz-osx-font-smoothing: grayscale;
    }


    /* header */

    .header {
        width:100%;
        overflow:hidden;
        background:#ff5001;
        text-align:center;
        height:2.625em;
        line-height:2.625em;
        -webkit-box-sizing:border-box;
    }
    .back,
    .about{
        /*background:url(../images/icon.png) no-repeat;*/
        height:2.625em;
        width:3em;
        overflow:hidden;
        text-indent:-9999px;
        background-size:50%;
        -webkit-box-sizing:border-box;
    }
    .back{
        background-position:5px -24px;
        float:left;
    }
    .about{
        background-position:18px -70px;
        float:right;
    }

    .logo{
        font-size:1.125em;
        color:#fff;
        overflow:hidden;
        font-weight:normal;
        display:inline-block;
        /*background:url(../images/icon.png) 0 13px no-repeat;*/
        background-size:28%;
        /*padding:1px 0 0 36px;*/
        -webkit-box-sizing:border-box;
    }

    .box{
        overflow:hidden;
        margin:0 auto;
        background:#fff;
        border-bottom:1px solid #d7d7d7;
        padding:15px 2%;
    }
    .table_ui{
        border-collapse:collapse;
    }
    .table_ui th{
        text-align:left;
        font-size:1em;
        font-family:"微软雅黑";
        border-bottom:1px solid #d7d7d7;
        padding:20px 0 10px 15px;
    }
    .table_ui td{
        padding:10px 15px;
    }
    .table_ui td.tr{
        text-align:right;
    }
    .table_ui td strong,
    .table_ui td span{
        display:block;
    }
    .table_ui td span{
        color:#999;
    }

    /*************************** 订单信息 *******************/
    .info {
        padding:10px 8px 20px 8px;
        background:#fff;
        border-bottom:1px solid #d7d7d7;
        position:relative;
        -webkit-box-sizing:border-box;
    }

    .money{
        color:#333;
        font-family:Arial, Helvetica, sans-serif;
        font-size:1.75em;
        overflow:hidden;
        font-weight:700
    }
    .merchant{
        display:none;
    }

    .arrow{
        width:1em;
        position:absolute;
        left:50%;
        margin-left:-0.5em;
        bottom:8px;
        color:#cdcdcd;
        -webkit-animation: move 1.5s infinite;
    }
    .arrow:after{
        content:"\e608";
    }
    .up:after{
        content:"\e611";
    }
    @-webkit-keyframes move {
        from {-webkit-transform:none;}
        50% { -webkit-transform: translateY(5px); }
        to {-webkit-transform:none;}
    }

    .cardholder{
        margin-bottom:20px;
        padding-bottom:10px;
        overflow:hidden;
        border-bottom:1px solid #cdcdcd
    }
    .cardholder span{
        float:left;
        font-size: 1.125em;
        color:#333
    }
    .cardholder strong{
        float:right;
        color:#333;
        font-size: 1.125em;
        font-weight:normal
    }
    .cardholder i{
        float:right;
        font-size:1.4em;
        color:#999;
        margin-left:10px;
    }
    .txt{
        font-size: 1.125em;
        margin-bottom:20px
    }
    .support_bank{
        color:#09f;
        display:inline-block;
        margin:0 0  30px 0;
    }
    /*************************** 表单(form) *************************/
    .form_warp{
        overflow:hidden;
        margin:0 auto;
        margin-top:1em;
        margin-left:2%;
        margin-right:2%;
        -webkit-box-sizing:border-box;
    }
    .form_warp ul{
        margin-bottom:30px;
    }
    .form_warp li{
        position:relative;
    }
    .form_item{
        border:1px solid #d7d7d7;
        background:#fff;
        position:relative;
        height:48px;
        line-height:48px;
        overflow:hidden;
        margin-bottom:10px;
        -webkit-box-sizing:border-box;
    }
    .form_item label{
        position:absolute;
        height:50px;
        line-height:50px;
        display:block;
        width:50px;
        z-index:99;
        font-size:1.125em;
        padding-left:10px;
        -webkit-box-sizing:border-box;
    }
    .form_item label.w70{
        width:70px;
    }
    .form_item label.w120{
        width:120px;
    }
    .form_item input[type='text'],
    .form_item input[type='password'],
    .form_item input[type='tel']{
        position:absolute;
        top:0;
        border:none;
        height:48px;
        width:100%;
        font-size:1.125em;
        font-family:"微软雅黑";
        margin-left:55px;
        background:#fff;
        color:#174d83;
        -webkit-box-sizing:border-box;
    }
    .form_item input[type='text'].ml5,
    .form_item input[type='password'].ml5,
    .form_item input[type='tel'].ml5{
        margin-left:5px;
    }
    .form_item input[type='text'].ml70,
    .form_item input[type='password'].ml70,
    .form_item input[type='tel'].ml70{
        margin-left:70px;
    }
    .form_item input[type='text'].ml125,
    .form_item input[type='password'].ml125,
    .form_item input[type='tel'].ml125{
        margin-left:125px;
    }
    .form_item input[type='text']:focus,
    .form_item input[type='password']:focus,
    .form_item input[type='tel']:focus{
        border:none;
    }

    .form_item.err{
        border:1px solid #f00;
    }
    .err_tips{
        background:#f00;
        width:100%;
        position:absolute;
        top:60px;
        z-index:999;
        color:#fff;
        font-size:1em;
        padding:0 0 0 10px;
    }
    .err_tips:after{
        content:" ";
        position:absolute;
        top:-8px;
        left:20px;
        border-width:0px 8px 8px 8px;
        border-color:#f00 #f4f4f4;
        border-style:solid;
    }
    .err_tips span{
        padding:6px 0;
        display:inline-block;
    }
    .err_tips i{
        font-size:1.2em;
        font-weight:bold;
        margin-right:5px;
    }

    /******************* button *************************/
    .btn{
        height:48px;
        line-height:48px;
        border:none;
        color:#fff;
        font-size:1.25em;
        background:#fe5000;
        width:100%;
        font-family:"微软雅黑";
        display:block;
        text-align:center;
        padding:0;
        position:relative;
    }
    .btn.disabled{
        background:#d6d6d6;
    }

    .btn.gray{
        background:#fff;
        border:1px solid #d6d6d6;
        color:#999;
    }

    /*验证码按钮*/
    .veri{
        position:absolute;
        right:0;
        width:70px;
        z-index:999;
        font-size:1em;
        height:50px;
    }

    .alert_btn{
        border:none;
        padding:5px 20px;
        border-radius:3px;
        font-size:1.2em
    }
    .confirm{
        background:#fe5000;
        color:#fff
    }
    .cancel{
        background:#fff;
        border:1px solid #d6d6d6;
        color:#000;
    }


    /****************** 借记卡 信用卡 ***************/
    .card_num{
        font-size:1.125em;
    }
    .card_num span{
        width:60px;
        display:inline-block;
        text-align:center
    }

    .card_type{
        padding:5px 0 10px 65px;
        color:#999;
    }
    .card_info_tip{
        margin-bottom: 10px;
        color:#fe5000;
    }

    .agreement{
        padding-bottom:15px;
    }
    .agreement a{
        color:#2991ef;
        text-decoration:underline
    }
    .card_list{
        margin:0 auto;
        margin-top:1em;
        margin-left:2%;
        margin-right:2%;
        overflow:hidden;
        -webkit-box-sizing:border-box;
    }
    .card_list h2{
        padding:0 0 0.5em 0;
        font-size:1em;
        -webkit-box-sizing:border-box;
    }
    .card_list ul{
        border:1px solid #C6E7FF;
    }
    .card_list li{
        height:60px;
        background:#edf8fd;
        border-bottom:1px solid #c6e7ff;
        position: relative;
        -webkit-box-sizing:border-box;
    }
    .card_list li:last-child{
        border:none;
    }

    .delete {
        display: block;
        position: absolute;
        height:60px;
        line-height:60px;
        right: 0%;
        top: 0%;
        background: #ff0000;
        color: #fff;
        width: 0%;
        text-align:center;
        -webkit-transition: width 0.5s;
    }
    .card_info{
        padding:6px 0 0 15px;
        -webkit-box-sizing:border-box;
    }
    .card_info h3{
        font-weight:normal;
        font-size:1.2em
    }
    .card_info h3 em{
        font-size:0.8em;
        color:#999;
        padding-left:10px;
    }
    .card_info span{
        float:right;
        color:#999;
        margin:4px 15px 0 0;
        font-size:0.8em
    }
    .card_info p{
        color:#4399d1;
    }
    .tips_txt{
        padding:5px 0 0 0;
        color:#999;
        font-size:0.8em
    }
    .tips_txt1{
        text-align:center;
        padding:0 0 15px 0;
    }


    /******** 失败页面 *******/
    .result{
        margin:0 auto;
        margin-left:2%;
        margin-right:2%;
        margin-top:15px;
        border:1px solid #d7d7d7;
        background:#fff;
    }
    .result h3{
        text-align:center;
        font-weight:normal;
        overflow:hidden;
        font-size:1.6em;
        padding:20px 0;
        border-bottom:1px solid #d7d7d7;
    }
    .result.processing span{
        background:url(../images/icon2.png) 0 -197px no-repeat;
        background-size:40px auto;
        display:inline-block;
        height:40px;
        padding-left:50px;
        color:#fe5000
    }
    .result i{
        overflow:hidden;
        font-size:1.4em;
    }
    .result p{
        padding:20px;
        color:#999;
    }
    .result.fail h3{
        color:#f00;
    }
    .result.suc h3{
        color:#4eb521;
    }
    .txt_gray{
        color:#999;
    }
    td{
        font-size:1.125em;
        padding:2px 0;
    }

    /**************** 404 *******************/
    .err404{
        padding:50px 0;
    }
    .err404 div{
        width:280px;
        margin:0 auto;
        overflow:hidden;
    }
    .err404 h3{
        float:left;
        font-size:4em;
        width:120px;
        color:#999
    }
    .err404 p{
        float:left;
        width:160px;
    }
    .err404 p strong{
        display:block;
        font-size:1.6em;
        padding:16px 0 0 0
    }
    .err404 p span{
        font-size:1em;
        color:#999
    }
    /**************** 选择银行卡 *********************/
    .bank_list{
        overflow:hidden;
    }
    .bank_list h3{
        font-size:1.2em;
        font-weight:normal;
        margin:10px;
    }
    .bank_list dl{
        border-top:1px solid #d7d7d7;
    }
    .bank_list dt{
        padding:15px 10px;
        background:#fff;
        border-bottom:1px solid #d7d7d7;
        color:#333;
        font-size:1.0em
    }
    .bank_list dd{
        display:none;
    }
    .bank_list label{
        padding:0  10px;
        height:3em;
        line-height:3em;
        overflow:hidden;
        background:#f2f5f7;
        border-bottom:1px solid #d7d7d7;
        display:block;
        -webkit-box-sizing:border-box;
    }


    /* radio */
    .radio {
        display: block;
        cursor: pointer;
        overflow:hidden;
    }
    .radio.cur{
        color:#09F;
    }
    .radio input {
        width: 1px;
        height: 1px;
        opacity: 0;
    }
    .radio input:checked + .outer .inner {
        -webkit-transform: scale(0.6);
        -ms-transform: scale(0.6);
        transform: scale(0.6);
        opacity: 1;
    }
    .radio input:checked + .outer {
        background-color: #51b732;
        box-shadow:0 0 1px #51b732
    }

    .radio input:focus + .outer .inner {
        -webkit-transform: scale(0.6);
        -ms-transform: scale(0.6);
        transform: scale(0.6);
        opacity: 1;
        background-color: #fff;
    }
    .radio .outer {
        width: 22px;
        height: 22px;
        display: block;
        float: right;
        margin: 13px 0 0 0;
        border-radius: 11px;
        background-color: #cdcdcd;
        box-shadow:0 0 1px #cdcdcd
    }
    .radio .inner {
        -webkit-transition: all 0.25s ease-in-out;
        transition: all 0.25s ease-in-out;
        width: 18px;
        height: 18px;
        -webkit-transform: scale(0.4);
        -ms-transform: scale(0.4);
        transform: scale(0.4);
        display: block;
        margin: 2px;
        border-radius: 9px;
        background-color: #fff;
        opacity: 0.6;
        box-shadow:0 0 1px #fff
    }

    /* 自定义checkbox */
    .cb-box{
        padding:0 0 1em 0;
    }
    [type="checkbox"]:not(:checked),
    [type="checkbox"]:checked {
        position: absolute;
        left: -9999px;
    }
    [type="checkbox"]:not(:checked) + label,
    [type="checkbox"]:checked + label {
        position: relative;
        padding-left: 30px;
        cursor: pointer;
    }
    [type="checkbox"]:not(:checked) + label {
        color:#999;
    }
    [type="checkbox"]:checked + label{
        color:#fe5000
    }
    [type="checkbox"]:not(:checked) + label:before,
    [type="checkbox"]:checked + label:before {
        content: '';
        position: absolute;
        left:0; top: 0;
        width: 20px; height: 20px;
        border: 1px solid #cdcdcd;
        background:url(../images/cb-bg.png);
        background-size:100%;
        border-radius: 3px;
    }

    [type="checkbox"]:not(:checked) + label:after,
    [type="checkbox"]:checked + label:after {
        content: '';
        position: absolute;
        top:0; left: 0;
        font-size: 1em;
        color: #09ad7e;
        transition: all .2s;
        width: 22px; height: 22px;
        text-align:center;
        display:block;
        line-height:22px;
        border-radius: 3px;
        text-decoration:none;
    }


    [type="checkbox"]:checked + label:after {
        opacity: 1;
        transform: scale(1);
        -webkit- transform: scale(1);
        background:#fe5000 url(../images/cb-bg.png) 0 -30px no-repeat;
        background-size:100%;
    }


    /*************** footer **********************/
    .footer{
        margin:30px 0 10px 0;
        font-size:0.8em;
        color:#999;
        text-align:center;
        -webkit-box-sizing:border-box;
    }
    .footer span{
        display:inline-block;
        background:url(../images/logo.png) left center no-repeat;
        padding:10px 0 10px 32px;
        background-size:19%;
    }
    .footer01{
        font-size:0.8em;
        color:#999;
        text-align:center;
        width:100%;
        margin:0 0 20px 0
    }
    .footer01 span{
        display:inline-block;
        background:url(../images/logo.png) left center no-repeat;
        padding:10px 0 10px 26px;
        background-size:16%;
    }
    /*********************** 弹出层 ****************************/
    #mask{
        background:rgba(0,0,0,0.5);
        width:100%;
        position:absolute;
        top:0px;
        left:0px;
        right:0px;
        bottom:0px;
        z-index:9999;
        display:none;
    }

    #mask_win{
        background:rgba(0,0,0,0.5);
        width:100%;
        position:absolute;
        top:0px;
        left:0px;
        right:0px;
        bottom:0px;
        z-index:9000;
        display:none;
    }


    #modal-dialog{
        width:90%;
        background:rgba(255,255,255,0.95);
        position:fixed;
        left:50%;
        top: 50%;
        margin-top: 150px;
        margin: 0 0 0 -45%;
        z-index:9999;
        border-radius:6px;
        -webkit-transition:1s ease all;  /* 持续时间  变换速率  变换的属性(all表示所有发生变化的属性) */
        -moz-transition:1s ease all;
        -o-transition:1s ease all;
        box-shadow:0 0 3px rgba(0,0,0, 0.6);
    }


    .modal-header{
        padding:6px 10px 6px 20px;
        border-bottom:1px solid #cdcdcd;
        font-size:18px;
        color:#333;
    }
    .modal-body{
        padding:10px 15px;
        color:#4d4d4d;
    }
    .modal-footer{
        text-align:center;
        padding:0 0 20px 0;
    }
    @media (min-width:640px) and (max-width:1024px) {
    //TODO
    #modal-dialog{
        width:60%;
        margin: 0 0 0 -30%;
    }

        .modal-header{
            padding:15px 20px;
            border-bottom:1px solid #cdcdcd;
            font-size:18px;
            color:#333;
        }
        .modal-body{
            padding:15px 20px;
            color:#4d4d4d;
        }
        .modal-footer{
            text-align:center;
            padding:0 0 20px 0;
        }

        .header {
            width:100%;
            overflow:hidden;
            background:#ff5001;
            text-align:center;
            height:3.125em;
            line-height:3.125em;
            -webkit-box-sizing:border-box;
        }
        .back,
        .about{
            /*background:url(../images/icon.png) no-repeat;*/
            height:3.125em;
            width:3em;
            overflow:hidden;
            text-indent:-9999px;
            background-size:60%;
            -webkit-box-sizing:border-box;
        }
        .back{
            background-position:5px -24px;
            float:left;
        }
        .about{
            background-position:16px -80px;
            float:right;
        }

        .logo{
            font-size:1.375em;
            color:#fff;
            overflow:hidden;
            font-weight:normal;
            display:inline-block;
            /*background:url(../images/icon.png) 0 15px no-repeat;*/
            background-size:28%;
            /*padding:1px 0 0 40px;*/
        }

        .alert_btn{
            border:none;
            padding:10px 25px;
            border-radius:3px;
            font-size:1.2em
        }
        .confirm{
            background:#fe5000;
            color:#fff
        }
        .cancel{
            background:#fff;
            border:1px solid #d6d6d6;
            color:#000;
        }

        .bank_list dt{
            padding:20px 10px;
        }
    }

    @media (min-width:1024px) and (max-width:1440px) {
        .header {
            width:100%;
            overflow:hidden;
            background:#ff5001;
            text-align:center;
            height:3.125em;
            line-height:3.125em;
            -webkit-box-sizing:border-box;
        }
        .back,
        .about{
            /*background:url(../images/icon.png) no-repeat;*/
            height:3.125em;
            width:3em;
            overflow:hidden;
            text-indent:-9999px;
            background-size:60%;
            -webkit-box-sizing:border-box;
        }
        .back{
            background-position:5px -24px;
            float:left;
        }
        .about{
            background-position:16px -80px;
            float:right;
        }

        .logo{
            font-size:1.375em;
            color:#fff;
            overflow:hidden;
            font-weight:normal;
            display:inline-block;
            /*background:url(../images/) 0 15px no-repeat;*/
            background-size:28%;
            /*padding:1px 0 0 40px;*/
            -webkit-box-sizing:border-box;
        }
    //TODO
      #modal-dialog{
          width:50%;
          margin: 0 0 0 -25%;
      }
        .modal-header{
            padding:15px 20px;
            border-bottom:1px solid #cdcdcd;
            font-size:18px;
            color:#333;
        }
        .modal-body{
            padding:15px 20px;
            color:#4d4d4d;
        }
        .modal-footer{
            text-align:center;
            padding:0 0 20px 0;
        }

        .alert_btn{
            border:none;
            padding:10px 25px;
            border-radius:3px;
            font-size:1.2em
        }
        .confirm{
            background:#fe5000;
            color:#fff
        }
        .cancel{
            background:#fff;
            border:1px solid #d6d6d6;
            color:#000;
        }
        .bank_list dt{
            padding:20px 10px;
        }

    }


    /* loading */
    #circularG{
        position:relative;
        width:128px;
        height:128px;
        margin:0 auto;
        margin-top:150px;
    }

    .circularG{
        position:absolute;
        background-color:#FE5000;
        width:29px;
        height:29px;
        -moz-border-radius:19px;
        -moz-animation-name:bounce_circularG;
        -moz-animation-duration:1.04s;
        -moz-animation-iteration-count:infinite;
        -moz-animation-direction:linear;
        -webkit-border-radius:19px;
        -webkit-animation-name:bounce_circularG;
        -webkit-animation-duration:1.04s;
        -webkit-animation-iteration-count:infinite;
        -webkit-animation-direction:linear;
        -ms-border-radius:19px;
        -ms-animation-name:bounce_circularG;
        -ms-animation-duration:1.04s;
        -ms-animation-iteration-count:infinite;
        -ms-animation-direction:linear;
        -o-border-radius:19px;
        -o-animation-name:bounce_circularG;
        -o-animation-duration:1.04s;
        -o-animation-iteration-count:infinite;
        -o-animation-direction:linear;
        border-radius:19px;
        animation-name:bounce_circularG;
        animation-duration:1.04s;
        animation-iteration-count:infinite;
        animation-direction:linear;
    }

    #circularG_1{
        left:0;
        top:50px;
        -moz-animation-delay:0.39s;
        -webkit-animation-delay:0.39s;
        -ms-animation-delay:0.39s;
        -o-animation-delay:0.39s;
        animation-delay:0.39s;
    }

    #circularG_2{
        left:14px;
        top:14px;
        -moz-animation-delay:0.52s;
        -webkit-animation-delay:0.52s;
        -ms-animation-delay:0.52s;
        -o-animation-delay:0.52s;
        animation-delay:0.52s;
    }

    #circularG_3{
        top:0;
        left:50px;
        -moz-animation-delay:0.65s;
        -webkit-animation-delay:0.65s;
        -ms-animation-delay:0.65s;
        -o-animation-delay:0.65s;
        animation-delay:0.65s;
    }

    #circularG_4{
        right:14px;
        top:14px;
        -moz-animation-delay:0.78s;
        -webkit-animation-delay:0.78s;
        -ms-animation-delay:0.78s;
        -o-animation-delay:0.78s;
        animation-delay:0.78s;
    }

    #circularG_5{
        right:0;
        top:50px;
        -moz-animation-delay:0.91s;
        -webkit-animation-delay:0.91s;
        -ms-animation-delay:0.91s;
        -o-animation-delay:0.91s;
        animation-delay:0.91s;
    }

    #circularG_6{
        right:14px;
        bottom:14px;
        -moz-animation-delay:1.04s;
        -webkit-animation-delay:1.04s;
        -ms-animation-delay:1.04s;
        -o-animation-delay:1.04s;
        animation-delay:1.04s;
    }

    #circularG_7{
        left:50px;
        bottom:0;
        -moz-animation-delay:1.17s;
        -webkit-animation-delay:1.17s;
        -ms-animation-delay:1.17s;
        -o-animation-delay:1.17s;
        animation-delay:1.17s;
    }

    #circularG_8{
        left:14px;
        bottom:14px;
        -moz-animation-delay:1.3s;
        -webkit-animation-delay:1.3s;
        -ms-animation-delay:1.3s;
        -o-animation-delay:1.3s;
        animation-delay:1.3s;
    }

    @-moz-keyframes bounce_circularG{
        0%{
            -moz-transform:scale(1)}

        100%{
            -moz-transform:scale(.3)}

    }

    @-webkit-keyframes bounce_circularG{
        0%{
            -webkit-transform:scale(1)}

        100%{
            -webkit-transform:scale(.3)}

    }

    @-ms-keyframes bounce_circularG{
        0%{
            -ms-transform:scale(1)}

        100%{
            -ms-transform:scale(.3)}

    }

    @-o-keyframes bounce_circularG{
        0%{
            -o-transform:scale(1)}

        100%{
            -o-transform:scale(.3)}

    }

    @keyframes bounce_circularG{
        0%{
            transform:scale(1)}

        100%{
            transform:scale(.3)}

    }


    /*为了兼容解决htmlview中 文本框获取焦点遮住按钮添加的样式*/
    .form_item input[type='text'].veri_text{
        width:160px;
    }
    .form_item input[type='text'].msg_veri{
        width:230px;
    }
    @media (min-width:480px) and (max-width:800px) {
        .form_item input[type='text'].veri_text{
            width:360px;
        }
        .form_item input[type='text'].msg_veri{
            width:380px;
        }
    }
    @media (min-width:640px) and (max-width:960px) {
        .form_item input[type='text'].veri_text{
            width:580px;
        }
        .form_item input[type='text'].msg_veri{
            width:520px;
        }
    }
    @media (min-width:960px) and (max-width:1440px) {
        .form_item input[type='text'].veri_text{
            width:1024px;
        }
        .form_item input[type='text'].msg_veri{
            width:900px;
        }
    }

    .morph-button {
        position: relative;
        display: block;
        margin: 0 auto;
    }

    .morph-button>button {
        position: relative;
        padding: 0 1em;
        border: none;
        background-color: #FE5000;
        color: #f9f6e5;
        text-transform: uppercase;
        letter-spacing: 1px;
        overflow: hidden;
    }

    .morph-button.open>button {
        pointer-events: none;
    }

    .morph-content {
        pointer-events: none;
    }

    .morph-button.open .morph-content {
        pointer-events: auto;
    }

    /* Morph Button Style: In the content flow */
    .morph-button-inflow {
        overflow: hidden;
        max-width: 100%;
        height: 48px;
    }

    .morph-button-inflow>button {
        width: 100%;
        line-height: 48px;
    }

    .morph-button-inflow .morph-content {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
    }

    .morph-button-inflow .morph-content .morph-clone {
        padding: 0;
        font-size: 1.25em;
        line-height: 46px;
    }

    /* Colors and sizes for individual in flow buttons */
    .morph-button-inflow-1 {
        width: 100%;
        margin: 2em auto;
        -webkit-transition: height 0.5s cubic-bezier(0.7, 0, 0.3, 1);
        transition: height 0.5s cubic-bezier(0.7, 0, 0.3, 1);
    }

    .morph-button-inflow-1>button span {
        visibility: hidden;
    }

    .morph-button-inflow-1 .morph-content .morph-clone {
        color: #fff;
        background: #FE5000;
    }

    /* Let's add some nice easing for all cases */
    .morph-button .morph-content,.morph-button.open .morph-content {
        -webkit-transition-timing-function: cubic-bezier(0.7, 0, 0.3, 1);
        transition-timing-function: cubic-bezier(0.7, 0, 0.3, 1);
    }

    @media screen and (max-width: 400px) {
        .morph-button-inflow .morph-content .morph-clone {
            font-size: 0.9em;
        }
    }

    /* Style for form modal */
    .content-style-form {
        position: relative;
        text-align: left;
    }

    .content-style-form h2 {
        margin: 0;
        padding: 0.4em 0 0.3em;
        text-align: center;
        font-family: "微软雅黑";
        font-size: 1.25em;
    }

    .content-style-form form {
        padding: 10px 30px;
    }

    .content-style-form form p {
        margin: 0 0 5px 0;
        font-size: 0.7em;
    }

    .content-style-form label {
        /*display: block;
        padding: 10px 0 0;
        color: #d5bba4;
        text-transform: uppercase;
        letter-spacing: 1px;
        font-weight: bold;*/
    }

    .content-style-form input[type="text"]
    {
        position: absolute;
        top: 0px;
        border: medium none;
        height: 48px;
        width: 100%;
        font-size: 1.125em;
        font-family: "微软雅黑";
        margin-left: 55px;
        background: none repeat scroll 0% 0% #FFF;
        color: #174D83;
    }

    .content-style-form input[type="text"]:focus
    {
        border-color: #e75854;
        color: #e75854;
    }

    .content-style-form input:focus {
        outline: 0;
    }

    .content-style-form button {
        display: block;
        margin-top: 1.0em;
        padding: 1.5em;
        width: 100%;
        border: none;
        background: #e75854;
        color: #f9f6e5;
        text-transform: uppercase;
        letter-spacing: 1px;
        font-weight: 800;
    }

    .content-style-form-4 form {
        padding: 20px;
        background: #fff;
        color: #ccb096;
        -webkit-perspective: 500px;
        perspective: 500px;
    }

    .content-style-form-4 input[type="text"] {
        border: none;
        /*background-color: #f0f0f0;*/
    }

    .content-style-form-4 form button {
        background: #ba997b;
    }

    .content-style-form-4 form button:focus,.content-style-form-4 form button:hover
    {
        background: #fe5000;
    }

    .js .content-style-form-4 p {
        opacity: 0;
        -webkit-transition: opacity 0.3s, -webkit-transform 0.3s;
        transition: opacity 0.3s, transform 0.3s;
        -webkit-transform: rotateX(-45deg);
        transform: rotateX(-45deg);
        -webkit-transform-origin: center top;
        transform-origin: center top;
    }

    .morph-button.open .content-style-form-4 p {
        opacity: 1;
        -webkit-transition: opacity 0.4s 0.2s, -webkit-transform 0.4s 0.2s;
        transition: opacity 0.4s 0.2s, transform 0.4s 0.2s;
        -webkit-transform: rotateY(0deg);
        transform: rotateY(0deg);
    }

    .morph-button.open .content-style-form-4 p:nth-child(2) {
        -webkit-transition-delay: 0.35s;
        transition-delay: 0.35s;
    }

    @font-face {font-family: 'iconfont';
        src: url('../font/iconfont.eot'); /* IE9*/
        src: url('../font/iconfont.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
        url('../font/iconfont.woff') format('woff'), /* chrome、firefox */
        url('../font/iconfont.ttf') format('truetype'), /* chrome、firefox、opera、Safari, Android, iOS 4.2+*/
        url('../font/iconfont.svg#iconfont') format('svg'); /* iOS 4.1- */
    }

    .form_item input[type='text'].veri_text{
        width:160px;
    }

    @media (min-width:480px) and (max-width:800px) {
        .form_item input[type='text'].veri_text{
            width:360px;
        }
    }
    @media (min-width:640px) and (max-width:960px) {
        .form_item input[type='text'].veri_text{
            width:480px;
        }
    }
    @media (min-width:768px) and (max-width:1280px) {
        .form_item input[type='text'].veri_text{
            width:640px;
        }
    }

    .float_div{
        background:#fff;
        border-radius:10px;
        position:absolute;
        padding:10px 3% 0 3%;
        width:90%;
        left:50%;
        margin-left:-48%;
    }
</style>
</head>
<body>
<div class="header">
    <h1 class="logo">mockserver</h1>
</div>

<div class="result suc">
    <h3>
        <i class="iconfont"></i>
        <span>签约成功</span>
    </h3>
</div>
<div class="warp">
    <form id="form" name="form" method="post" action="${url}">
        <a href="javascript:form.submit();" class="btn color">返回</a>
    </form>
</div>
</body>
</html>

