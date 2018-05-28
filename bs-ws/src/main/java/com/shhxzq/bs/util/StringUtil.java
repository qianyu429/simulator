package com.shhxzq.bs.util;

/**
 * @author kangyonggan
 * @since 16/5/10
 */
public class StringUtil {

    /**
     * <pre>
     * StringUtil.isEmpty(null) = true
     * StringUtil.isEmpty("") = true
     * StringUtil.isEmpty("    ") = true
     * StringUtil.isEmpty("abc") = false
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * To See StringUtil.isEmpty(String str)
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * <pre>
     * StringUtil.hasEmpty(null, "asd", "qwe") = true
     * StringUtil.hasEmpty("", "asd") = true
     * StringUtil.hasEmpty("    ", "asd") = true
     * StringUtil.hasEmpty("abc", "asd") = false
     * </pre>
     *
     * @param arr
     * @return
     */
    public static boolean hasEmpty(String... arr) {
        for (String str : arr) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <pre>
     * StringUtil.capitalize(null)  = null
     * StringUtil.capitalize("")    = ""
     * StringUtil.capitalize("cat") = "Cat"
     * StringUtil.capitalize("cAt") = "CAt"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char firstChar = str.charAt(0);
        if (Character.isTitleCase(firstChar)) {
            return str;
        }

        return new StringBuilder(str.length())
                .append(Character.toTitleCase(firstChar))
                .append(str.substring(1))
                .toString();
    }

    /**
     * 判断str是否在arr中
     *
     * @param str
     * @param arr
     * @return
     */
    public static boolean in(String str, String... arr) {
        for (String s : arr) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

}
