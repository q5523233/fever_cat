package com.nepo.fevercat.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.ui.data.bean.CalendarDecoratorBean;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  ConstantUtils
 * 作者 :   <sen>
 * 时间 :  下午2:25 2017/6/24.
 * 描述 :
 */

public class ConstantUtils {


    /**
     * 加载用户头像
     */
    public static void loadUserImg(String url, ImageView imageView) {
        Glide.with(Utils.getContext())
                .load(url)
                .error(R.drawable.icon_login_qq)
                .placeholder(R.drawable.icon_login_qq)
                .thumbnail(.2f)
                .into(imageView);
    }

    /**
     * 未登录用户
     */
    public static void loadLoginUserImg(String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("http")) {
                Glide.with(Utils.getContext())
                        .load(url)
                        .error(R.drawable.icon_no_login_default)
                        .placeholder(R.drawable.icon_no_login_default)
                        .thumbnail(.2f)
                        .into(imageView);
            } else {
                Glide.with(Utils.getContext())
                        .load(new File(url).getPath())
                        .error(R.drawable.icon_no_login_default)
                        .placeholder(R.drawable.icon_no_login_default)
                        .thumbnail(.2f)
                        .into(imageView);
            }
        } else {
            Glide.with(Utils.getContext())
                    .load(url)
                    .error(R.drawable.icon_no_login_default)
                    .placeholder(R.drawable.icon_no_login_default)
                    .thumbnail(.2f)
                    .into(imageView);
        }


    }


    /**
     * 加载宝宝头像
     *
     * @param url
     * @param imageView
     */
    public static void loadBBImg(String url, ImageView imageView) {
        Glide.with(Utils.getContext())
                .load(url)
                .error(R.drawable.icon_no_login_default)
                .placeholder(R.drawable.icon_no_login_default)
                .thumbnail(.2f)
                .into(imageView);
    }


    /**
     * 判断当前 单位模式
     * 摄氏度/华氏度
     */
    public static boolean IsCelsius() {
        String tempSwitch = SharedPreferencesUtil.getString(AppConstant.SP_TEMP_SWITCH, "true");
        if (Boolean.valueOf(tempSwitch)) {
            return true;
        }
        return false;
    }

    /**
     * 转换温度单位
     *
     * @return
     */
    public static String IsCelsiusUnit(String sKey) {
        try {
            String cUnitStr = Utils.getContext().getString(R.string.c_unit);
            String fUnitStr = Utils.getContext().getString(R.string.f_unit);
            if (IsCelsius()) {
                return String.format(cUnitStr, sKey);
            } else {
                // 转换为华氏度
                Double aDouble = CConvert2F(Double.valueOf(sKey));
                DecimalFormat df = new DecimalFormat("#.00");
                return String.format(fUnitStr, df.format(aDouble));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }

    }

    /**
     * 获取温度单位
     *
     * @return
     */
    public static String GetCelsiusUnit() {
        String cUnitStr = Utils.getContext().getString(R.string.c_default_unit);
        String fUnitStr = Utils.getContext().getString(R.string.f_default_unit);
        if (IsCelsius()) {
            return cUnitStr;
        } else {
            return fUnitStr;
        }
    }


    /**
     * 摄氏度转华氏度
     *
     * @param cDouble
     * @return 华氏度=摄氏度*9/5+32
     */
    public static Double CConvert2F(Double cDouble) {

        return cDouble * 9 / 5 + 32;
    }

    /**
     * 是否发烧
     * >37.5
     */
    public static boolean IsFever(String temp) {

        if (Double.valueOf(temp) > 37.5) {
            return true;
        }

        return false;
    }


    /**
     * 是否超高热
     * >= 39.00
     *
     * @return
     */
    public static boolean IsSuperHot(Double temp) {
        if (temp >= 39.00) {
            return true;
        }
        return false;
    }


    /**
     * 显示字体
     *
     * @param dTemp
     * @return
     */
    public static String CheckTempLimitToStr(Double dTemp) {
//        if (IsCelsius()) {
        if (dTemp <= 34.69) {
            // 体温过低
            return Utils.getContext().getString(R.string.temp_status_undue);
        } else if (dTemp <= 37.39) {
            // 正常
            return Utils.getContext().getString(R.string.temp_status_normal);
        } else if (37.40 <= dTemp && dTemp <= 37.99) {
            // 低热
            return Utils.getContext().getString(R.string.temp_status_low);
        } else if (38.00 <= dTemp && dTemp <= 38.99) {
            // 中热
            return Utils.getContext().getString(R.string.temp_status_middle);
        } else if (39.00 <= dTemp) {
            // 高热
            return Utils.getContext().getString(R.string.temp_status_hot);
        }
//        } else {
//            dTemp = CConvert2F(dTemp);
//            if (dTemp <= CConvert2F(38.5)) {
//                // 低热 绿色
//                return Utils.getContext().getString(R.string.temp_status_low);
//            } else if (dTemp <= CConvert2F(40d)) {
//                // 中热 橙色
//                return Utils.getContext().getString(R.string.temp_status_middle);
//            } else if (dTemp <= CConvert2F(42d)) {
//                // 超热 红色
//                return Utils.getContext().getString(R.string.temp_status_hot);
//            }
//        }
        return Utils.getContext().getString(R.string.temp_status_normal);
    }


    /**
     * 显示背景
     *
     * @param dTemp
     * @return
     */
    public static Drawable CheckTempLimitToBg(Double dTemp) {
//        if (IsCelsius()) {
        if (dTemp <= 37.39) {
            // 正常 蓝色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_normal_bg);
        } else if (37.40 <= dTemp && dTemp <= 37.99) {
            // 低热 绿色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_low_bg);
        } else if (38.00 <= dTemp && dTemp <= 38.99) {
            // 中热 橙色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_middle_bg);
        } else if (39.00 <= dTemp && dTemp <= 40.99) {
            // 高热 红色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_hot_bg);
        } else if (dTemp >= 41) {
            // 超高热  红色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_hot_bg);
        }
//        } else {
//            dTemp = CConvert2F(dTemp);
//            if (dTemp <= CConvert2F(38.5)) {
//                // 低热 绿色
//                return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_low_bg);
//            } else if (dTemp <= CConvert2F(40d)) {
//                // 中热 橙色
//                return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_middle_bg);
//            } else if (dTemp <= CConvert2F(42d)) {
//                // 超热 红色
//                return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_hot_bg);
//            }
//        }
        return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_normal_bg);
    }

    /**
     * 显示颜色
     *
     * @param dTemp
     * @return
     */
    public static int CheckTempLimitToColor(Double dTemp) {

//        if (IsCelsius()) {
        if (dTemp <= 37.39) {
            // 正常 蓝色
            return ContextCompat.getColor(Utils.getContext(), R.color.color_12);
        } else if (37.40 <= dTemp && dTemp <= 37.99) {
            // 低热 绿色
            return ContextCompat.getColor(Utils.getContext(), R.color.color_11);
        } else if (38.00 <= dTemp && dTemp <= 38.99) {
            // 中热 橙色
            return ContextCompat.getColor(Utils.getContext(), R.color.color_13);
        } else if (39.00 <= dTemp && dTemp <= 40.99) {
            // 高热 红色
            return ContextCompat.getColor(Utils.getContext(), R.color.color_14);
        } else if (dTemp >= 41) {
            // 超高热 红色
            return ContextCompat.getColor(Utils.getContext(), R.color.color_14);
        }
//        } else {
//            dTemp = CConvert2F(dTemp);
//            if (dTemp <= CConvert2F(38.5)) {
//                // 低热 绿色
//                return ContextCompat.getColor(Utils.getContext(), R.color.color_11);
//            } else if (dTemp <= CConvert2F(40d)) {
//                // 中热 橙色
//                return ContextCompat.getColor(Utils.getContext(), R.color.color_13);
//            } else if (dTemp <= CConvert2F(42d)) {
//                // 超热 红色
//                return ContextCompat.getColor(Utils.getContext(), R.color.color_14);
//            }
//        }

        return ContextCompat.getColor(Utils.getContext(), R.color.color_12);
    }

    /**
     * 显示背景
     *
     * @param dTemp
     * @return
     */
    public static Drawable CheckTempLimitFloatBg(Double dTemp) {
//        if (IsCelsius()) {
        if (dTemp <= 37.39) {
            // 正常 蓝色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_bg);
        } else if (37.40 <= dTemp && dTemp <= 37.99) {
            // 低热 绿色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_low_bg);
        } else if (38.00 <= dTemp && dTemp <= 38.99) {
            // 中热 橙色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_middle_bg);
        } else if (39.00 <= dTemp && dTemp <= 40.99) {
            // 高热 红色
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_height_bg);
        } else if (dTemp >= 41) {
            // 超高热
            return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_super_height_bg);
        }
//        } else {
//            dTemp = CConvert2F(dTemp);
//            if (dTemp <= CConvert2F(38.5)) {
//                // 低热 绿色
//                return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_low_bg);
//            } else if (dTemp <= CConvert2F(40d)) {
//                // 中热 橙色
//                return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_middle_bg);
//            } else if (dTemp <= CConvert2F(42d)) {
//                // 超热 红色
//                return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_height_bg);
//            } else if (dTemp > CConvert2F(42d)) {
//                // 超高热
//                return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_super_height_bg);
//            }
//        }
        return ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_real_time_float_bg);
    }


    /**
     * 数字转汉字
     *
     * @param string
     * @return
     */
    public static String Digits2Chinese(String string) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {
            int num = string.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        return result;
    }


    /**
     * 去掉文件扩展名
     *
     * @param fileName 文件名
     * @return 没有扩展名的文件名
     */
    public static String removeEx(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < fileName.length())) {
                return fileName.substring(0, dot);
            }
        }
        return fileName;
    }


    /**
     * 选中毫秒
     */
    public static String getSelectMilliSecond(int index) {

        String currMilliSecond = String.valueOf(System.currentTimeMillis());

        switch (index) {
            case 0:
                // 30分钟
                currMilliSecond = String.valueOf(3600000 / 2);
                break;
            case 1:
                // 1小时
                currMilliSecond = String.valueOf(3600000);
                break;
            case 2:
                // 1.5小时
                currMilliSecond = stringToInt(String.valueOf(3600000 * 1.5));
                break;
            case 3:
                // 2小时
                currMilliSecond = String.valueOf(3600000 * 2);
                break;
            case 4:
                // 2.5小时
                currMilliSecond = stringToInt(String.valueOf(3600000 * 2.5));
                break;
            case 5:
                // 3小时
                currMilliSecond = String.valueOf(3600000 * 3);
                break;
            case 6:
                // 4小时
                currMilliSecond = String.valueOf(3600000 * 4);
                break;
            case 7:
                // 5小时
                currMilliSecond = String.valueOf(3600000 * 5);
                break;
            case 8:
                // 6小时
                currMilliSecond = String.valueOf(3600000 * 6);
                break;
        }

        return currMilliSecond;
    }

    private static String stringToInt(String string) {
        String str = string.substring(0, string.lastIndexOf("."));
        return str;

    }


    /**
     * 绘制日历指示点
     */
    public static List<CalendarDecoratorBean> getCalendarDecoratorList(TempHistoryDataResBean tempHistoryDataResBean) {
        List<CalendarDecoratorBean> calendarDecoratorBeanList = new ArrayList<>();
        List<String> tempHeightDayList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<TempHistoryDataResBean.RecordBean.MonthBean.RecordDetailsBean> recordList = tempHistoryDataResBean.getRecordList();
            for (TempHistoryDataResBean.RecordBean.MonthBean.RecordDetailsBean recordDetailsBean : recordList) {
                CalendarDecoratorBean calendarDecoratorBean = new CalendarDecoratorBean();
                boolean b = IsFever(recordDetailsBean.getRecordTemperatureValue());
                if (b) {
                    // 保存超过37.5℃的日期
                    tempHeightDayList.add(recordDetailsBean.getYearMonthDay());
                }
                calendarDecoratorBean.setHeightTemp(b);
                CalendarDay fromCalendar = CalendarDay.from(simpleDateFormat.parse(recordDetailsBean.getYearMonthDay()));
                calendarDecoratorBean.setCalendarDay(fromCalendar);
                calendarDecoratorBeanList.add(calendarDecoratorBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setTempHeightList(tempHeightDayList);

        return calendarDecoratorBeanList;
    }


    /**
     * 保存超过37.5℃的数据到 SharedPreferences 供日历绘制高温点
     */
    private static void setTempHeightList(List<String> tempDayList) {

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(tempDayList);
        SharedPreferencesUtil.removeAtTag(AppConstant.HEIGHT_TEMP_FLAG);
        SharedPreferencesUtil.set(AppConstant.HEIGHT_TEMP_FLAG, strJson);
    }


    /**
     * 毫秒转化时分秒毫秒
     */
    public static String formatTimeByMillisecond(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            //sb.append(day+"天");
        }
        if (hour > 0) {
            sb.append(hour + "h");
        } else {
            sb.append("0h");
        }
        if (minute > 0) {
            sb.append(minute + "min");
        } else {
            sb.append("0min");
        }
//        if(second > 0) {
//            sb.append(second+"秒");
//        }else{
//            sb.append("0秒");
//        }
//        if(milliSecond > 0) {
//            //sb.append(milliSecond+"毫秒");
//        }
        return sb.toString();
    }


    /**
     * 返回当前的应用是否处于前台显示状态
     *
     * @param packageName
     * @return
     */
    public static boolean IsTopActivity(String packageName) {
        //_context是一个保存的上下文
        ActivityManager am = (ActivityManager) Utils.getContext().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo process : list) {
            if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    process.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 转换字符串成华氏度
     */
    public static String ConvertStrToFahrenheit(String str){

        // 摄氏度 直接返回
        if (IsCelsius()) {
            return str;
        }
        if (TextUtils.equals(str,"37.50~37.99")) {
            Double aDouble = CConvert2F(37.50d);
            Double bDouble = CConvert2F(37.99d);
            return String.valueOf(aDouble)+"~"+String.valueOf(bDouble);
        }else if(TextUtils.equals(str,"38.00~38.99")){
            Double aDouble = CConvert2F(38.00d);
            Double bDouble = CConvert2F(38.99d);
            return String.valueOf(aDouble)+"~"+String.valueOf(bDouble);
        }else if(TextUtils.equals(str,"39.00~")){
            Double aDouble = CConvert2F(39.00d);
            return String.valueOf(aDouble)+"~";
        }

        return "--";
    }

}
