$(function () {
    $("#dashboard-cp").addClass("active open");
    $("#dashboard-cp-code").addClass("active");
    $("#bankmock").addClass("active open");

    var $form = $('#form');

    $form.validate({
        ignore: '',
        rules: {}
    });

    $('[data-rel=verify]').popover({
        html: true,
        content: '<p>000:交易成功<br/>001:查发卡方<br/>002:查发卡方的特殊条件<br/>003:无效商户<br/>004:无效卡<br/>005:不予承兑<br/>006:交易出错<br/>007:特殊条件下没收卡<br/>008:交易处理出错<br/>009:请求正在处理中<br/>010:交易失败<br/>011:交易处理出错<br/>012:无效交易<br/>013:无效金额<br/>014:无效卡号<br/>015:无此发卡方<br/>016:交易处理出错<br/>017:客户撤消交易<br/>018:客户出错<br/>019:重新送入交易<br/>020:无效应答<br/>021:不作任何处理<br/>022:怀疑操作有误<br/>023:不可接受的交易费<br/>024:文件更新出错<br/>025:未能找到文件上的记录<br/>026:文件更新出错<br/>027:文件更新出错<br/>028:文件更新出错<br/>029:文件更新出错<br/>030:格式错误<br/>031:交换中心不支持的银行<br/>032:交易失败<br/>033:过期的卡（没收卡）<br/>034:有作弊嫌疑（没收卡）<br/>035:受卡方与安全保密部门联系（没收卡）<br/>036:受限制的卡（没收卡）<br/>037:受卡方呼受理方安全部门（没收卡）<br/>038:超过允许的PIN试输入（没收卡）<br/>039:无此信用卡账户<br/>040:请求的功能尚不支持<br/>041:挂失卡（没收卡）<br/>042:无此账户<br/>043:被窃卡（没收卡）<br/>044:无此投资账户<br/>045:证件号不符<br/>051:无足够的存款<br/>052:无此支票账户<br/>053:无此储蓄卡账户<br/>054:过期卡:请核对卡的有效期<br/>055:密码错误<br/>056:无此卡记录<br/>057:不允许持卡人进行的交易<br/>058:不允许终端进行的交易<br/>059:有作弊嫌疑<br/>060:受卡方与安全保密部门联系<br/>061:超出取款金额限制<br/>062:受限制的卡<br/>063:违反安全保密规定<br/>064:原始金额不正确<br/>065:超出取款次数限制<br/>066:受卡方呼受理方安全保密部门<br/>067:捕捉（没收卡）<br/>068:收到的回答太迟<br/>075:允许的输入PIN次数超限 <br/>090:正在处理日期切换<br/>091:发卡方或交换中心不能操作<br/>092:金融机构或中间网络设施找不到或无法达到<br/>093:交易违法、不能完成<br/>094:交易重复<br/>095:调节控制错<br/>096:系统异常、失效<br/>097:ATM/POS终端号找不到<br/>098:交换中心收不到发卡方应答<br/>099: PIN格式错/密码错误<br/>0A0:MAC鉴别失败<br/>0A2:转入行超时，请查询转入卡余额<br/>101:网关出错<br/>102:密码加密出错<br/>103:网关产生MAC错<br/>111:消息时间太新<br/>112:消息时间太旧<br/>113:信息不符<br/>114:商户验证出错或商户密码错<br/>115:商户消息格式出错<br/>116:撤消交易信息出错<br/>117:查询个数为零<br/>118:查询个数大于一百<br/>124:无效卡号<br/>141:系统出错<br/>142:撤消交易出错<br/>143:撤消交易已处理<br/>144:交易已被冲正<br/>145:撤消交易出错<br/>146:交换中心无应答<br/>147:网关未收到该交易<br/>148:重复交易<br/>150:银行通讯线路故障<br/>151:当日累计金额超限<br/>155:密码输错两次<br/>157:不允许持卡人进行的交易<br/>158:该卡网上支付功能已暂停，具体恢复时间，将等候银行进一步通知<br/>199:系统出错<br/>255:密码输错三次<br/>E09:接口错误<br/>E43:交易重复<br/>E46:金额超限: 超过单笔交易限额<br/>E51:服务未开通<br/>F00:商户签名错误<br/>F01:商户号错误<br/>F02:时间戳错误<br/>F03:交易类型错误<br/>F04:用户不存在<br/>F05:该CD卡用户未开通<br/>F06:用户已经被关闭<br/>F07:用户证件号码无效<br/>F08:银行卡未登记<br/>F09:银行卡未开通转账服务<br/>F10:原申购交易不存在<br/>F11:原申购交易已撤单<br/>F12:数据格式错误<br/>F13:重复交易<br/>F14:服务未开通<br/>F15:此卡被多个用户注册<br/>F16:暂不支持该基金产品交易<br/>XXX:交易待处理<br/></p>'
    });
    $('[data-rel=pay]').popover({
        html: true,
        content: '<p>00:处理完成或接收成功<br/>45:系统正在对数据处理<br/>还有很多很多...以后再贴进来</p>'
    });
    $('[data-rel=payStat]').popover({
        html: true,
        content: '<p>1001:处理完成或接收成功<br/>2000:系统正在对数据处理<br/>还有很多很多...以后再贴进来</p>'
    });
    $('[data-rel=redeem]').popover({
        html: true,
        content: '<p>0000:提交成功<br/>0100:商户提交的字段长度、格式错误<br/>0101:商户验签错误<br/>0102:手续费计算出错<br/>0103:商户备付金帐户金额不足<br/>0104:操作拒绝<br/>0105:重复交易<br/></p>'
    });
    $('[data-rel=redeemStat]').popover({
        html: true,
        content: '<p>s:交易成功<br/>2:交易已接受<br/>3:财务已确认<br/>4:财务处理中<br/>5:已发往银行<br/>6:银行已退单<br/>7:重汇已提交<br/>8:重汇已发送<br/>9:重汇已退单<br/></p>'
    });
});