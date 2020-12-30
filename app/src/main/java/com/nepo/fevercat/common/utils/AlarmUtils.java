package com.nepo.fevercat.common.utils;

import android.app.AlarmManager;
import android.content.Context;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  AlarmUtils
 * 作者 :   <sen>
 * 时间 :  下午5:44 2017/7/11.
 * 描述 :
 */

public class AlarmUtils {


    private static AlarmUtils sAlarmUtils;
    private Context mContext;
    private AlarmManager  mAlarmManager;

    private AlarmUtils(Context context){
        mContext = context;
    }

    public static AlarmUtils getInstance(Context context){
        if (sAlarmUtils==null) {
            sAlarmUtils = new AlarmUtils(context);
        }

        return sAlarmUtils;
    }


    /**
     * 获取闹钟实例
     * @return
     */
    public AlarmManager getAlarmManager(){
        if (mAlarmManager==null) {
            mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        }
        return mAlarmManager;
    }




}
