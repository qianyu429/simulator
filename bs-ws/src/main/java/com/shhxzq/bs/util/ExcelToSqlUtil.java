package com.shhxzq.bs.util;

import java.util.List;

/**
 * @author kangyonggan
 * @since 16/6/6
 */
public class ExcelToSqlUtil {

    public static void main(String s[]) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO be.be_simulator_config (user_id, grp, k, val, status, created_at, updated_at)\n\tVALUES\n");

        List<String[]> list = Excel.excelToList("/Users/kyg/code.xlsx", 2, true);

        for (int i = 0; i < list.size(); i++) {
            if (StringUtil.isNotEmpty(list.get(i)[0])) {
                String k = "'" + list.get(i)[0] + "'";
                String val = "'" + list.get(i)[1] + "'";
                val = val.replaceAll(",", "，");
                sb.append("('0', 'ect-code-sms', ").append(k).append(", ").append(val).append(", 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08'),\n");
                sb.append("('0', 'ect-code-verify', ").append(k).append(", ").append(val).append(", 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08'),\n");
                sb.append("('0', 'ect-code-sign', ").append(k).append(", ").append(val).append(", 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08'),\n");
                sb.append("('0', 'ect-code-pay', ").append(k).append(", ").append(val).append(", 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08'),\n");
                sb.append("('0', 'ect-code-redeem', ").append(k).append(", ").append(val).append(", 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08'),\n");
            }
        }

        sb.deleteCharAt(sb.lastIndexOf("\n"));
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(";");
        System.out.println(sb.toString());
    }

}
