package com.nepo.fevercat.ui.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.LogUtils;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui
 * 文件名:  AlarmActivity
 * 作者 :   <sen>
 * 时间 :  上午9:34 2017/7/11.
 * 描述 :
 */

public class AlarmActivity extends BaseActivity {


    AlarmManager alarmManager;


    @Override
    public int getLayoutId() {
        return R.layout.act_alarm;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

//        String[] shortWeekdays = new DateFormatSymbols().getShortWeekdays();
//
//        for (String shortWeekday : shortWeekdays) {
//            LogUtils.logd("-- short:"+shortWeekday+","+shortWeekdays.length);
//        }

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


    }


    /* 设置闹钟 */
    public void setClock(View view){

        //创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
        Intent intent = new Intent("ELITOR_CLOCK");
        intent.putExtra("msg","你该打酱油了");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,0,intent,0);
        // pendingIntent 为发送广播
        // 6.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.logd("-- if");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            // 4.4及以上
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LogUtils.logd("-- else if");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
            // 4.4以下
        } else {
            LogUtils.logd("-- else");
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),1500,pendingIntent);
        }
    }


    /*取消闹钟*/
    public void cancelClock(View view){

    }










}
