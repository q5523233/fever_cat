package com.nepo.fevercat.common.utils;

import java.util.regex.Pattern;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  VerificationUtils
 * 作者 :   <sen>
 * 时间 :  上午11:10 2017/4/25.
 * 描述 :
 */

public class VerificationUtils {

    /**
     * 验证手机号
     * @param value
     * @return
     */
    public static boolean matcherPhoneNum(String value) {
        String regex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        return testRegex(regex, value);
    }

    public static boolean testRegex(String regex, String inputValue) {
        return Pattern.compile(regex).matcher(inputValue).matches();
    }



}
