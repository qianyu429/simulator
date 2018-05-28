package com.shhxzq.bs.util;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Order;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 16/5/27
 */
@Log4j2
public class FileUtil {

    public static boolean genDZFile(String path, String fileName, String head, String template, List<Transaction> transactions) {
        log.info("template：" + template);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(path + fileName));
            if (StringUtil.isNotEmpty(head)) {
                out.write(head);
                out.newLine();
            }
            for (Transaction trans : transactions) {
                out.write(replaceWithTrans(template, trans));
                out.newLine();
            }

            if (!transactions.isEmpty()) {
                String bankNo = transactions.get(0).getBankNo();
                if (AppConstants.BANK_NO_CMBCT0.equals(bankNo)) {
                    // 民生T+0对账文件的最后要多加一行
                    out.write("###########- -!");
                    out.newLine();
                }
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public static boolean genFile(String path, String fileName, String head, String template, List<Order> orders) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(path + fileName));
            if (StringUtil.isNotEmpty(head)) {
                out.write(head);
                out.newLine();
            }
            for (Order order : orders) {
                out.write(replaceWithOrders(order));
                out.newLine();
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    private static String replaceWithOrders(Order order) {
        StringBuffer temp = new StringBuffer();
        if (order.getOrderType().equals("1")) {
            temp.append(order.getCompanyNo()).append("|");
            temp.append(order.getSubCompanyNo()).append("|");
            temp.append(order.getPartnerCustNo()).append("|");
            temp.append(order.getCustType()).append("|");
            temp.append(order.getName()).append("|");
            temp.append(order.getCertType()).append("|");
            temp.append(order.getCertNo()).append("|");
            temp.append(order.getMobile()).append("|");
            temp.append(order.getBankCardNo()).append("|");
            temp.append(order.getRemark());
        } else {
            temp.append(order.getCompanyNo()).append("|");
            temp.append(order.getSubCompanyNo()).append("|");
            temp.append(order.getSerialNo()).append("|");
            temp.append(order.getPartnerCustNo()).append("|");
            temp.append(order.getTradeAcct()).append("|");
            temp.append(order.getProdId()).append("|");
            temp.append(order.getAmount()).append("|");
            temp.append(order.getShare()).append("|");
            temp.append(order.getPaymentType()).append("|");
            temp.append(order.getBankCardNo()).append("|");
            temp.append(order.getChargeType()).append("|");
            temp.append(order.getApkind());
        }
        return temp.toString();


    }

    private static String replaceWithTrans(String template, Transaction transaction) throws Exception {
        Class clazz = transaction.getClass();
        String bankNo = transaction.getBankNo();
        String transType = transaction.getTransType();

        String fields[] = template.split("\\|", -1);
        String values[] = new String[fields.length];

        int i = 0;
        for (String field : fields) {
            String val;
            try {
                Method method = clazz.getMethod("get" + StringUtil.capitalize(field));
                val = String.valueOf(method.invoke(transaction));
            } catch (Exception e) {
                val = field;
            }
            if (AppConstants.BANK_NO_ECT.equals(bankNo)) {
                if ("transType".equals(field)) {
                    if ("pay".equals(val)) {
                        val = "102";
                    } else if ("redeem".equals(val)) {
                        val = "101";
                    }
                } else if ("respCode".equals(field)) {
                    if ("50050000".equals(val)) {
                        val = "00";
                    } else if ("50050001".equals(val)) {
                        val = "01";
                    } else {
                        val = "02";
                    }
                }
            } else if (AppConstants.BANK_NO_CP.equals(bankNo)) {
                if ("transStat".equals(field)) {
                    if (transType.equals(TransTypeEnum.REDEEM.getType())) {
                        String respCode = transaction.getRespCode();
                        if (StringUtil.in(respCode, "0100", "0101", "0102", "0103", "0104")) {
                            val = "6";
                        } else if ("0105".equals(respCode)) {
                            val = "5";
                        }
                    }
                }
            } else if (AppConstants.BANK_NO_GDNY.equals(bankNo)) {
                if ("transType".equals(field)) {
                    if ("pay".equals(val)) {
                        val = "1";
                    } else if ("redeem".equals(val)) {
                        val = "4";
                    }
                } else if ("stat".equals(field)) {
                    if ("Y".equals(val)) {
                        val = "S";
                    }
                }

            } else if (AppConstants.BANK_NO_CMBCT0.equals(bankNo)) {
                if ("transType".equals(field)) {
                    val = "1002";
                } else if ("transStat".equals(field)) {
                    if ("Y".equals(val)) {
                        val = "S";
                    } else {
                        val = "E";
                    }
                }
            }


            values[i++] = val;
        }

        while (template.indexOf("||") != -1) {
            template = template.replaceAll("\\|\\|", "|temp|");
        }
        if (template.startsWith("|")) {
            template = "temp" + template;
        }
        if (template.endsWith("|")) {
            template += "temp";
        }
        template = template.replaceAll("\\w+", "%s");
        log.info("transId:{}, template:{}", transaction.getId(), template);
        log.info("transId:{}, values:{}", transaction.getId(), values);
        return String.format(template, values);
    }

    /**
     * 生成中信信用卡账单CSV文件
     * @param path
     * @param fileName
     * @param transactions
     * @param bankCode
     * @param configService
     * @return
     */
    public static boolean genZXDZFile(String path, String fileName, List<Transaction> transactions, String bankCode, ConfigService configService) {
        boolean flag = false;
        log.info("start to generate DZ file.");
        List<String> head = new ArrayList<>();
        head.add("协议号");
        head.add("还款金额");
        head.add("还款日期");

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(path + File.separator + fileName + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GB2312"), 1024);
            // 写入文件头部
//            writeRow(head, csvWtriter);

            // 写入文件内容
            for (Transaction tr : transactions) {
                String protocolNo = tr.getProtocolNo();
                String amtId = tr.getMemo1();
                String amount = null;

                if (amtId.equals("0")) {
                    Config conf = configService.findConfigByGrpAndK(bankCode + "-verify-minAmt", "minAmt");
                    amount = conf.getVal();

                } else if (amtId.equals("1")) {
                    Config conf = configService.findConfigByGrpAndK(bankCode + "-verify-payAmt", "payAmt");
                    amount = conf.getVal();
                }
                if(StringUtils.isEmpty(amount)){
                    log.error("还款金额为空");
                    return false;
                }
                String[] amt = StringUtils.split(amount, ".");
                if (amt.length == 0 || StringUtil.isEmpty(amt[0])) {
                    log.error("输入金额不对: {}" + amount);
                    return false;
                } else {
                    String tmp = null;
                    if (amt.length == 2) {
                        if (amt[1].length() >= 3) {
                            tmp = amt[1].substring(0, 1);
                            amt[1] = tmp;
                        }
                        amount = amt[0] + amt[1];
                    }
                    else{
                        amount = amt[0];
                    }

                    amount = StringUtils.leftPad(amount, 8, "0");
                }
                log.info("还款金额: {}", amount);

                Config config = configService.findConfigByGrpAndK(bankCode + "-verify-date", "date");
                String billDate = config.getVal();
                log.info("还款日期: {}", billDate);

                //信用卡账号币种
                String currency = "";
                //扣款户名
                String accName = "";
                //扣款类型
                String ddFlag = "";
                //扣款金额指示
                String index = "";
                //保留字段
                String buffer = "";

                List<String> dataList = new ArrayList<>();
                dataList.add(protocolNo);
                dataList.add(currency);
                dataList.add(accName);
                dataList.add(ddFlag);
                dataList.add(index);
                dataList.add(amount);
                dataList.add(billDate);
                dataList.add(buffer);

                writeRow(dataList, csvWtriter);
            }
            flag = true;
            csvWtriter.flush();
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
        return flag;
    }

    /**
     * 写一行数据方法进CSV
     *
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<String> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (String data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append(data).append(",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    public static void deleteFile(String fileName) {
        File file = new File(AppConstants.APP_ROOT + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

}
