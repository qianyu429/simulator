package com.shhxzq.bs.util;

import com.shhxzq.bs.constants.PayCodeStatEnum;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 银联工具类
 *
 * @author kangyonggan
 * @since 16/5/18
 */
public class CpUtil {

    /**
     * 拼接鉴权消息主体
     *
     * @param cpVerifyRequest
     * @param encMsg
     * @param sign
     * @return
     */
    public static String buildVerifyMsg(CpVerifyRequest cpVerifyRequest, String encMsg, String sign) {
        StringBuilder msg = new StringBuilder();

        msg.append("version=").append(cpVerifyRequest.getVersion());
        msg.append("&fundTransTime=").append(cpVerifyRequest.getFundTransTime());
        msg.append("&instuId=").append(cpVerifyRequest.getInstuId());
        msg.append("&fundMerId=").append(cpVerifyRequest.getFundMerId());
        msg.append("&transType=").append(cpVerifyRequest.getTransType());
        msg.append("&encMsg=").append(encMsg);
        msg.append("&signMsg=").append(sign);
        msg.append("&resv1=").append(cpVerifyRequest.getResv1());
        msg.append("&resv2=").append(cpVerifyRequest.getResv2());
        msg.append("&resv3=").append(cpVerifyRequest.getResv3());
        msg.append("&resv4=").append(cpVerifyRequest.getResv4());

        return msg.toString();
    }

    /**
     * 拼接鉴权返回主体
     *
     * @param cpVerifyRequest
     * @param respCode
     * @param serNo           银联通的流水号
     * @return
     */
    public static String buildVerifyRespCodeMsg(CpVerifyRequest cpVerifyRequest, String respCode, String respCodeMsg, String serNo) {
        StringBuilder sb = new StringBuilder();
        // 上送来的报文
        String arr[] = cpVerifyRequest.getEncMsg().split("\\|");

        // 银联通交易时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = format.format(new Date());

        sb.append(cpVerifyRequest.getInstuId()).append("|");// 接入机构号
        sb.append(cpVerifyRequest.getFundMerId()).append("|");// 基金公司商户号
        sb.append(cpVerifyRequest.getTransType()).append("|");// 交易类型
        sb.append(arr[3]).append("|");// 基金发起交易的系统日期，其格式为YYYYMMDD
        sb.append(arr[4]).append("|");// 基金发送给ChinaPay的时间戳，格式HHMMSS
        sb.append(arr[5]).append("|");// 基金公司流水号。需要保证在一个交易日中该流水号的唯一性，由底下的ecBankvalueDelegate.getECBankvalueByBnknum获取
        sb.append(date.substring(0, 8)).append("|");// 银联通交易日期，其格式为YYYYMMDD
        sb.append(date.substring(8)).append("|");// 银联通交易时间，其格式为HHmmss
        sb.append(serNo).append("|");// 银联通流水号 6位

        if (respCode.startsWith("000_")) {
            respCode = respCode.substring(0, 3);
        }

        sb.append(respCode).append("|");// 返回码,000表示下单成功，其他表示下单失败
        sb.append(respCodeMsg).append("|");// 返回码描述
        sb.append("").append("|");// 订单特征码 太复杂
        sb.append(cpVerifyRequest.getResv1()).append("|");// 保留字段
        sb.append(cpVerifyRequest.getResv2()).append("|");// 保留字段
        sb.append(cpVerifyRequest.getResv3()).append("|");// 保留字段
        sb.append(cpVerifyRequest.getResv4()).append("|");// 保留字段

        return sb.toString();
    }

    /**
     * 构建异步通知be的参数
     *
     * @param cpVerifyRequest
     * @param code
     * @return
     */
    public static String buildVerifyParam(CpVerifyRequest cpVerifyRequest, String code) {
        // |||||be流水号||||||||||||||||||||||||||返回码|||||
        StringBuilder param = new StringBuilder();
        param.append("transType=").append(cpVerifyRequest.getTransType()).append("&");
        param.append("fundTransTime=").append(cpVerifyRequest.getFundTransTime()).append("&");
        param.append("encMsg=").append("|||||" + cpVerifyRequest.getEncMsg().split("\\|")[5] + "||||||||||||||||||||||||||" + code + "|||||").append("&");
        param.append("fundMerId=").append(cpVerifyRequest.getFundMerId()).append("&");
        param.append("signMsg=").append(cpVerifyRequest.getSignMsg()).append("&");
        param.append("instuId=").append(cpVerifyRequest.getInstuId()).append("&");
        param.append("resv1=").append(cpVerifyRequest.getResv1()).append("&");
        param.append("resv2=").append(cpVerifyRequest.getResv2()).append("&");
        param.append("resv3=").append(cpVerifyRequest.getResv3()).append("&");
        param.append("resv4=").append(cpVerifyRequest.getResv4()).append("&");
        param.append("version=").append(cpVerifyRequest.getVersion());

        return param.toString();
    }

    public static String buildPayBody(CpPayRequest cpPayRequest, String code, String stat, String valStat) {
        StringBuilder msg = new StringBuilder();
        msg.append("responseCode=").append(code);
        msg.append("&merId=").append(cpPayRequest.getMerId());
        msg.append("&transDate=").append(cpPayRequest.getTransDate());
        msg.append("&orderNo=").append(cpPayRequest.getOrderNo());
        msg.append("&transAmt=").append(cpPayRequest.getTransAmt());
        msg.append("&curyId=").append(cpPayRequest.getCuryId());
        msg.append("&transType=").append(cpPayRequest.getTransType());
        msg.append("&priv1=").append(cpPayRequest.getPriv1());
        msg.append("&transStat=").append(stat);
        msg.append("&gateId=").append(cpPayRequest.getGateId());
        msg.append("&cardType=").append(cpPayRequest.getCardType());
        msg.append("&cardNo=").append(cpPayRequest.getCardNo());
        msg.append("&userNme=").append(cpPayRequest.getUsrName());
        msg.append("&certType=").append(cpPayRequest.getCertType());
        msg.append("&certId=").append(cpPayRequest.getCertId());
        msg.append("&message=").append(valStat);
        msg.append("&chkValue=").append("");

        return msg.toString();
    }

    public static String buildPayMsg(String body, String code, String stat) {
        StringBuilder msg = new StringBuilder();
        msg.append("responseCode=").append(code);
        msg.append("&transStat=").append(stat);
        msg.append("&message=").append(body);

        return msg.toString();
    }

    public static String buildRedeemMsg(CpRedeemRequest cpRedeemRequest, String code, String serNo, String stat) {
        StringBuilder msg = new StringBuilder();
        msg.append("responseCode=").append(code);
        msg.append("&merId=").append(cpRedeemRequest.getMerId());
        msg.append("&merDate=").append(cpRedeemRequest.getMerDate());
        msg.append("&merSeqId=").append(cpRedeemRequest.getMerSeqId());
        msg.append("&cpDate=").append(DateUtil.format8Date(new Date()));
        msg.append("&cpSeqId=").append(serNo);
        msg.append("&transAmt=").append(cpRedeemRequest.getTransAmt());
        msg.append("&stat=").append(stat);
        msg.append("&cardNo=").append(cpRedeemRequest.getCardNo());
        msg.append("&chkValue=").append("");

        return msg.toString();
    }

    public static Transaction verifyRequestToTransaction(CpVerifyRequest cpVerifyRequest, Bank bank, String code, String stat, String serNo) {
        String encMsgs[] = cpVerifyRequest.getEncMsg().split("\\|");

        Transaction transaction = new Transaction();
        transaction.setBeSer(encMsgs[5]);// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(encMsgs[7]);// 交易账户(卡号)
        transaction.setTransType(TransTypeEnum.VERIFY.getType());// 交易类型
        transaction.setAmount("0");// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "000"
        transaction.setTransStat(stat);// 状态码: e.g. "s"

        // 临时写的
        String s = "Y";
        if (!"000".equals(code)) {
            s = "I";
        }

        transaction.setStat(s);// TODO 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(encMsgs[3]);// 工作日
        transaction.setSendTime(cpVerifyRequest.getFundTransTime());// 基金公司交易时间

        return transaction;
    }

    public static Transaction payRequestToTransaction(CpPayRequest cpPayRequest, Bank bank, String code, String stat, String serNo) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(cpPayRequest.getOrderNo());// 基金公司流水号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(cpPayRequest.getCardNo());// 交易账户(卡号)
        transaction.setTransType(TransTypeEnum.PAY.getType());// 交易类型
        transaction.setAmount(cpPayRequest.getTransAmt());// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "00"
        transaction.setTransStat(stat);// 状态码: e.g. "1001"
        transaction.setStat(PayCodeStatEnum.findStatByRespCodeAndTransStat(code, stat));// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(cpPayRequest.getTransDate());// 工作日
        transaction.setSendTime("");// 基金公司交易时间

        return transaction;
    }

    public static Transaction redeemRequestToTransaction(CpRedeemRequest cpRedeemRequest, Bank bank, String code, String stat, String serNo) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(cpRedeemRequest.getMerSeqId());// 基金公司流水号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(cpRedeemRequest.getCardNo());// 交易账户(卡号)
        transaction.setTransType(TransTypeEnum.REDEEM.getType());// 交易类型
        transaction.setAmount(cpRedeemRequest.getTransAmt());// 交易金额
        transaction.setRespCode(code);// 返回码: e.g. "000"
        transaction.setTransStat(stat);// 状态码: e.g. "s"

        String s = "I";
        if ("0000".equals(code) && "s".equals(stat)) {
            s = "Y";
        } else if ("0000".equals(code) && StringUtil.in(stat, "6", "9")) {
            s = "F";
        } else if (StringUtil.in(code, "0100", "0101", "0102", "0103", "0104")) {
            s = "F";
        }

        transaction.setStat(s);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(cpRedeemRequest.getMerDate());// 工作日
        transaction.setSendTime("");// 基金公司交易时间

        return transaction;
    }

    public static String buildQueryMsg(CpQueryRequest cpQueryRequest, Transaction transaction, String serNo) {
        // 000: 表示成功, 001: 表示没有记录, 002: 查询错误/超频查询
        // 判断单查是否成功
        if (transaction == null) {
            transaction = new Transaction();
            transaction.setRespCode("001");
        } else {
            transaction.setRespCode("000");
        }

        StringBuilder msg = new StringBuilder();
        msg.append(filterNullStr(transaction.getRespCode())).append("|");// code
        msg.append(cpQueryRequest.getMerId()).append("|");// merId
        msg.append(cpQueryRequest.getMerDate()).append("|");// merDate
        msg.append(cpQueryRequest.getMerSeqId()).append("|");// merSeqId
        msg.append(DateUtil.format8Date(new Date())).append("|");// cpDate
        msg.append(serNo).append("|");// cpSeqId
        msg.append(filterNullStr(transaction.getBankNo())).append("|");// bankName
        msg.append(filterNullStr(transaction.getAccoNo())).append("|");// cardNo
        msg.append("测试用户").append("|");// usrName
        msg.append(filterNullStr(transaction.getAmount())).append("|");// transAmt
        msg.append("0").append("|");// feeAmt
        msg.append("上海").append("|");// prov
        msg.append("上海").append("|");// city
        msg.append("purpose").append("|");// purpose
        msg.append(filterNullStr(transaction.getTransStat())).append("|");// stat
        msg.append("").append("|");// backDate
        msg.append("");// chkValue

        return msg.toString();
    }

    public static String buildQuery2Msg(CpQuery2Request cpQuery2Request, Transaction transaction, String serNo) {

        String message = "交易成功";
        // 判断单查是否成功
        if (transaction == null) {
            message = "查无记录";
            transaction = new Transaction();
            transaction.setRespCode("PZ");
        }

        StringBuilder msg = new StringBuilder();
        msg.append("responseCode=").append(filterNullStr(transaction.getRespCode()));
        msg.append("&transStat=").append(filterNullStr(transaction.getTransStat()));
        msg.append("&merId=").append(cpQuery2Request.getMerId());
        msg.append("&orderNo=").append(cpQuery2Request.getOrderNo());
        msg.append("&transAmt=").append(filterNullStr(transaction.getAmount()));
        msg.append("&curyId=").append("000");
        msg.append("&transDate=").append(cpQuery2Request.getTransDate());
        msg.append("&transType=").append(cpQuery2Request.getTransType());
        msg.append("&priv1=").append(cpQuery2Request.getPriv1());
        msg.append("&openBankId=").append(filterNullStr(transaction.getBankNo()));
        msg.append("&cardType=").append("0");
        msg.append("&cardNo=").append(filterNullStr(transaction.getAccoNo()));
        msg.append("&usrNme=").append("测试用户");
        msg.append("&certType=").append("");
        msg.append("&certId=").append("");
        msg.append("&message=").append(message);
        msg.append("&chkValue=").append("");

        return msg.toString();
    }

    private static String filterNullStr(String str) {
        return StringUtil.isEmpty(str) ? "" : str;
    }
}
