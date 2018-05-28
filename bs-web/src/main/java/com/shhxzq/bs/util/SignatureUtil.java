package com.shhxzq.bs.util;

import lombok.extern.log4j.Log4j2;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 算法简介如下，如$(var)表示取参数var对应的参数值操作，“+”表示字符串连接操作，则计算步骤如下：
 * 1） value是按照参数名称字典顺序排序，然后取对应的参数值拼接在一起（去掉sign参数）。
 * 假设有如下的参数需要传递，如value1, value2, value3, value4，
 * value = $(value1)+$(value2)+$(value3)+$(value4)
 * <p>
 * 2） 加密key1、key2的提供和约定。
 * 正式的key1和key2合作双方进行系统联合调试的时候由接口人通过正式邮件的形式提供给合作方指定接口人。
 * <p>
 * 3） 生成sign:
 * sign = md5(md5(value+key1)+key2)
 */

@Log4j2
public class SignatureUtil {

    /**
     * 密钥1
     */
    private String key1;

    /**
     * 密钥2
     */
    private String key2;

//	{
//	 // 读取配置文件，获取验签Key
//      key1 = "wz3z0tiuduaid0bf2mh614tj62bokzml";
//      key2 = "x2r991zdq2nblz3na58mspt248kdq90i";
//	}

    public void setKey(String key1, String key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    /**
     * 生成签名
     *
     * @param params 传递的参数组成的数组
     * @return
     */
    public String getSignature(HashMap params) {
        log.info("key1:" + key1);
        log.info("key2:" + key2);

        Object[] keySet = params.keySet().toArray();
        Arrays.sort(keySet);
        StringBuilder sb = new StringBuilder();
        log.info("签名字段列表开始-----");
        for (int i = 0; i < keySet.length; i++) {
            if (!"sign".equals(keySet[i])) {
                log.info(keySet[i] + ":" + params.get(keySet[i]).toString());
                sb.append(params.get(keySet[i]));
            }
        }
        System.out.println("签名字段列表结束-----:" + sb);
        log.info("签名字段列表结束-----:" + sb);
        String tmpStr = sb.toString();
        try {
            return Md5Util.md5(Md5Util.md5(tmpStr + key1) + key2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证签名是否正确
     *
     * @param sign   带比较的前面
     * @param params 传递的参数组成的数组
     * @return
     */
    public boolean compareSignature(HashMap params) {
        String sign = (String) params.get("sign");
        String currentSignature = getSignature(params);
        log.info("客户传入的签名：" + sign);
        System.out.println("客户传入的签名：" + sign);
        log.info("本地构造的签名：" + currentSignature);
        System.out.println("本地构造的签名：" + currentSignature);
        return currentSignature.equals(sign);
    }


    public boolean isSigntrue(final HashMap params) {
        boolean bl = compareSignature(params);

        if (bl) {
            //验签成功
            return true;
        } else {
            //验签失败
            return false;
        }
    }

//    public static void main(String[] args) {
//        SignatureUtil util = new SignatureUtil();
//        util.setKey("wz3z0tiuduaid0bf2mh614tj62bokzml", "x2r991zdq2nblz3na58mspt248kdq90i");
//        final HashMap params = new HashMap();
////		params.put("PARTNER_ID","0004");
////		params.put("PARTNER_APPLY_ID", "CNJTUHJLPYWLQZRIYQUOAEBTJYMEZFYPIVIDDPFWLMQVMDXDDQKKXIWRSGWMPWYM");
////		params.put("PARTNER_USER_ID", "CNJTUHJLPYWLQZRIYQUOAEBTJYMEZFYPIVIDDPFWLMQVMDXDDQKKXIWRSGWMPWYM");
////		params.put("SUBMIT_DATE", "20140126151148");
////		params.put("sign", "37b69941a9755a0ad4355feaa0c71a11");
//
//        //合作方id
//        params.put("partner_id", "JD00");
//        params.put("partner_apply_id", "5482780000038913");
//        params.put("partner_user_id", "5482780000038912");
//        params.put("apply_date", "20170329113833");
//        params.put("result_code", "3001");
//        params.put("sign", "6f892f87fd9801b279e7d285065fb4b2");
//        System.out.println("" + util.compareSignature(params));
//    }

    /**
     * 设置密钥1
     *
     * @param key1 密钥1
     */
    public void setKey1(String key1) {
        this.key1 = key1;
    }

    /**
     * 设置密钥2
     *
     * @param key2 密钥2
     */
    public void setKey2(String key2) {
        this.key2 = key2;
    }
}
