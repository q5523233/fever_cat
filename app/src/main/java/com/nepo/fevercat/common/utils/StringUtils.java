package com.nepo.fevercat.common.utils;

import android.text.TextUtils;

import com.nepo.fevercat.app.AppConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 项目名: Bluetooth
 * 包名 :  com.nepo.bluetooth.utils
 * 文件名:  StringUtils
 * 作者 :   <sen>
 * 时间 :  上午11:22 2017/2/8.
 * 描述 :
 */

public class StringUtils {


    /**
     * 生成UUID
     */
    public static String getGenerateUUID(){
        return UUID.randomUUID().toString();
    }


    /**
     * 计算包体数据校验位（10进制内容）
     * <p>
     * param strSrc
     *
     * @return
     */
    public static byte getCheckBitDemo(byte[] bytes) {
        long ucSum = 0;
        int uiCnt;
        for (uiCnt = 0; uiCnt < bytes.length - 1; uiCnt++) {
            ucSum += bytes[uiCnt];
        }
        return (byte) (ucSum & 0xFF);
    }


    /**
     * 计算包体数据校验位（10进制内容）
     * 从下标2开始,(流水号+命令字+数据长度+数据内容)
     * 连续异或值 1^2^3^4
     * param strSrc
     *
     * @return
     */
    public static byte[] getCheckBit(byte[] bytes) {

        //        LogUtils.logd("-- index:"+bytes[1]+","+bytes[2]+","+bytes[3]+","+bytes[4]);

        int checkBit = bytes[1] ^ bytes[2] ^ bytes[3] ^ bytes[4];
        byte[] hexStr = stringTo16Byte2(String.valueOf(checkBit));
        return hexStr;
    }

    /**
     * 数组转16进制字符串
     *
     * @param b param length
     * @return
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 16进制转10进制
     *
     * @param msg
     * @return
     */
    public static Long getValue16To10(String msg) {
        return Long.parseLong(msg, 16);
    }

    /**
     * 字符串转16进制
     *
     * @param str
     * @return
     */
    public static byte[] stringTo16Byte2(String str) {
        String hex = str;
        int len = hex.length() / 2;
        if (hex.length() % 2 == 1) {
            len = len + 1;
            hex = "0" + hex;
        }
        char[] chars = hex.toCharArray();
        String[] hexs = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i = i + 2, j++) {
            hexs[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) (Integer.parseInt(hexs[j], 16)); // Convert.ToInt16(hexes[j],
            // 16)
        }
        return bytes;
    }

    /**
     * 把byte转化成2进制字符串
     *
     * @param b
     * @return
     */
    public static String getBinaryStrFromByte(byte b) {
        String result = "";
        byte a = b;
        ;
        for (int i = 0; i < 8; i++) {
            byte c = a;
            a = (byte) (a >> 1);//每移一位如同将10进制数除以2并去掉余数。
            a = (byte) (a << 1);
            if (a == c) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }
            a = (byte) (a >> 1);
        }
        return result;
    }

    /**
     * 字符串转换成十六进制字符串
     * param String str 待转换的ASCII字符串
     *
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }


    /**
     * ASCII 转16进制
     * @param str
     * @return
     */
    public String convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }


    /**
     * 16进制转ASCII码
     * @param hex
     * @return
     */
    private static String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }


    /////////////////////////////////////////  截取响应命令字符串 返回界面 /////////////////////////////////////////////////

    /**
     * 截取命令字
     */
    private static String subFlagStr(String flag) {

        if (!TextUtils.isEmpty(flag)) {
            flag = flag.substring(4, 6);
        }

        return flag;
    }

    /**
     * 根据响应命令类型,截取数值
     *
     * @param flag
     * @return
     */
    public static String checkFlagType(String flag) {
        String tempFlag;
        String flagStr = subFlagStr(flag);
        switch (flagStr) {
            // 截取温度值
            case AppConstant.SYS_BLUETOOTH_TEMP_FLAG_READ:
                // 高位整数温度
                String intTemp = flag.substring(8, 10);
                // 低位小数温度
                String pointTemp = flag.substring(10, 12);
                // 合并为一个温度值
                tempFlag = intTemp + "." + pointTemp;
                break;
            default:
                // 默认为原值
                tempFlag = flag;
                break;
        }


        return tempFlag;
    }


    /**
     * 判断通信数据 类型
     *
     * @param flag
     * @return
     */
    private static String checkFlagTypeHex(String flag) {
        //        if (flag.contains(AppConstant.DEVICES_BATTERY_FLAG)) {
        //            // 电量
        //            return subBatteryConvert10(flag);
        //        } else if (flag.contains(AppConstant.DEVICES_TEMP_FLAG)) {
        //            // 温度
        //            return subTempConvert10(flag);
        //        }
        return "";
    }


    /**
     * 截取电量头部 并转换为10进制
     */
    public static String subBatteryConvert10(String flag) {
        if (AppConstant.DEVICES_BATTERY_FLAG.length() + 2 < flag.length()
                && AppConstant.DEVICES_BATTERY_FLAG.length() + 4 < flag.length()
                ) {
            String subHex = flag.substring(AppConstant.DEVICES_BATTERY_FLAG.length() + 2, AppConstant.DEVICES_BATTERY_FLAG.length() + 4);
            LogUtils.logd("-- 电量:" + subHex);
            return String.valueOf(getValue16To10(subHex));
            //            return String.valueOf(subHex);
        }

        return "";
    }

    /**
     * 截取温度头部 并转换为十进制
     * 硬件已转为10进制 无需再转
     */
    public static List<String> subTempConvert10(String flag) {
        List<String> tempData = new ArrayList<>();
        if (AppConstant.DEVICES_TEMP_FLAG.length() + 2 < flag.length()
                && AppConstant.DEVICES_TEMP_FLAG.length() + 4 < flag.length()
                && AppConstant.DEVICES_TEMP_FLAG.length() + 10 < flag.length()
                ) {
            // 整数位
            String subHexInt = flag.substring(AppConstant.DEVICES_TEMP_FLAG.length() + 2, AppConstant.DEVICES_TEMP_FLAG.length() + 4);
            // 小数位
            String subHexDecimal = flag.substring(AppConstant.DEVICES_TEMP_FLAG.length() + 4, AppConstant.DEVICES_TEMP_FLAG.length() + 6);
            String subHexInt2 = flag.substring(AppConstant.DEVICES_TEMP_FLAG.length() + 6, AppConstant.DEVICES_TEMP_FLAG.length() + 8);
            String subHexDecimal2 = flag.substring(AppConstant.DEVICES_TEMP_FLAG.length() + 8, AppConstant.DEVICES_TEMP_FLAG.length() + 10);
            //            String subHexDecimalStr = String.valueOf(getValue16To10(subHexDecimal));
            String subHexDecimalStr = String.valueOf(subHexDecimal);
            String subHexDecimalStr2 = String.valueOf(subHexDecimal2);
            if (subHexDecimalStr.length()>=2) {
                subHexDecimalStr = subHexDecimalStr.substring(0,2);
            }else{
                subHexDecimalStr+="0";
            }
            //            tempData.add(String.valueOf(getValue16To10(subHexInt)));
            tempData.add(String.valueOf(subHexInt));
            tempData.add("."+subHexDecimalStr);
            tempData.add(String.valueOf(subHexInt2));
            tempData.add("."+subHexDecimalStr2);

            //            DecimalFormat df = new DecimalFormat("#.00");
            //            Double dTemp = Double.valueOf(String.valueOf(getValue16To10(subHexInt)) + "." + String.valueOf(getValue16To10(subHexDecimal)));
            //            String format = df.format(dTemp);
            //
            //            LogUtils.logd("-- 温度整数:" + subHexInt + "," + getValue16To10(subHexInt));
            //            LogUtils.logd("-- 温度小数:" + format + "," + getValue16To10(subHexDecimal));
            return tempData;
        }

        // 默认数据
        tempData.add("00");
        tempData.add(".00");

        return tempData;
    }

    /**
     * 截取 设备序列号
     * 默认直接显示
     */
    public static String subProductSequence(String hexFlag){
        if (AppConstant.PRODUCT_SEQUENCE_FLAG.length() < hexFlag.length()
                ) {
            String subHexStr = hexFlag.substring(AppConstant.MANUFACTURER_NAME_FLAG.length(),hexFlag.length()-2);

            return subHexStr;

        }
        return "";
    }




    /**
     * 截取 厂商名称 hex头部 转为ASCII码
     */
    public static String subManufacturerNameToASCII(String hexFlag){
        if (AppConstant.MANUFACTURER_NAME_FLAG.length() < hexFlag.length()
                ) {
            String subHexStr = hexFlag.substring(AppConstant.MANUFACTURER_NAME_FLAG.length(),hexFlag.length()-2);
            // 53756E6D696E644D65646963616C
            String str = convertHexToString(subHexStr);

            return str;
        }

        return "";
    }

    /**
     * 截取 设备型号 hex头部 转为ASCII码
     */
    public static String subProductModelsToSACII(String hexFlag){
        if (AppConstant.PRODUCT_MODELS_FLAG.length() < hexFlag.length()
                ) {
            String subHexStr = hexFlag.substring(AppConstant.PRODUCT_MODELS_FLAG.length(),hexFlag.length()-2);
            String str = convertHexToString(subHexStr);

            return str;
        }
        return "";
    }

    /**
     * 版本头部 转为ASCII码
     */
    public static String subVersionToSACII(String subTitle,String hexFlag){
        if (subTitle.length()<hexFlag.length()) {
            String subHexStr = hexFlag.substring(subTitle.length(),hexFlag.length()-2);
            String str = convertHexToString(subHexStr);
            return str;
        }

        return "";
    }

    /**
     * 截取校准头部
     * 判断是否等于06
     */
    public static boolean subAdjustHex(String subTitle,String hexFlag){
        if (subTitle.length()<hexFlag.length()) {
            String subHexStr = hexFlag.substring(subTitle.length(),hexFlag.length()-2);
            if (TextUtils.equals(subHexStr,"06")) {
                return true;
            }
        }

        return false;
    }




}

