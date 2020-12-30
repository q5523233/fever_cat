package com.nepo.fevercat.ui.alert.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.event.AlertMedicineEvent;
import com.nepo.fevercat.ui.alert.receiver.AlarmReceiver;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.service
 * 文件名:  AlertMedicineService
 * 作者 :   <sen>
 * 时间 :  下午5:05 2017/8/1.
 * 描述 :  吃药提醒
 */

public class AlertMedicineService extends Service {


    private static AlarmManager alarmManager;
    private static final int ONE_DAY_TIME = 1000 * 60 * 60 * 24;

    private String[] mTimeSplit;
    private int mHour;
    private int mMinute;
    private Context mContext;


    public AlertMedicineService() {
        mContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 设置用药提醒
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSetAlertMedicineEvent(AlertMedicineEvent alertMedicineEvent) {
        AlertLowBean alertLowBean = alertMedicineEvent.getAlertLowBean();

        if (alertMedicineEvent.isCancelAlert()) {
            // 取消闹钟
            cancelAlarm(alertLowBean);
        } else {
            // 设定闹钟
            List<Integer> dayOfWeekList = loadDayOfWeek(alertLowBean.getMRepeatCode());

            mTimeSplit = alertLowBean.getMTime().split(":");
            mHour = Integer.parseInt(mTimeSplit[0]);
            mMinute = Integer.parseInt(mTimeSplit[1]);

            Calendar c = Calendar.getInstance();
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH), mHour, mMinute, 0);

            long mTimeInfo = c.getTimeInMillis();
            long actualTime = mTimeInfo > System.currentTimeMillis()
                    ? mTimeInfo : mTimeInfo + ONE_DAY_TIME;
            int currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);    //今天是星期几
            for (int j = 0; j < dayOfWeekList.size(); j++) {
                int flagDayOfWeek = dayOfWeekList.get(j);
                if (currentDayOfWeek == flagDayOfWeek) {
                    setAlarm(alertLowBean.getId(), actualTime, flagDayOfWeek);

                } else if (currentDayOfWeek < flagDayOfWeek) {
                    int gapDay = flagDayOfWeek - currentDayOfWeek;
                    long realTime = actualTime + gapDay * ONE_DAY_TIME;
                    setAlarm(alertLowBean.getId(), realTime, flagDayOfWeek);
                } else if (currentDayOfWeek > flagDayOfWeek) {
                    int gapDay = 7 - currentDayOfWeek + flagDayOfWeek;
                    long realTime = gapDay * ONE_DAY_TIME + actualTime;
                    setAlarm(alertLowBean.getId(), realTime, flagDayOfWeek);
                }
            }
            dayOfWeekList.clear();

        }


    }


    /**
     * 转换日期
     *
     * @param repeatCode
     * @return
     */
    private List<Integer> loadDayOfWeek(String repeatCode) {
        List<Integer> dayOfWeekList = new ArrayList<>();
        if (!TextUtils.isEmpty(repeatCode)) {
            String[] splitCode = repeatCode.split(",");
            for (String s : splitCode) {
                dayOfWeekList.add(Integer.parseInt(s));
            }
        }
        return dayOfWeekList;
    }


    /**
     * 设定闹钟
     */
    private void setAlarm(long id, long realTime, int flagDayOfWeek) {
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, realTime, getIntent(id, flagDayOfWeek));
            // 4.4及以上
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, realTime,
                    getIntent(id, flagDayOfWeek));
            // 4.4以下
        }


    }


    /**
     * 取消闹钟
     */
    private void cancelAlarm(AlertLowBean alertLowBean) {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        List<Integer> flagDayOfWeek = loadDayOfWeek(alertLowBean.getMRepeatCode());
        for (int i = 0;i<flagDayOfWeek.size();i++){
            PendingIntent pi = getIntent(alertLowBean.getId(),flagDayOfWeek.get(i));
            alarmManager.cancel(pi);
            LogUtils.logd("-- 取消 ID="+alertLowBean.getId()+","+"星期"+flagDayOfWeek+"的闹钟……");
        }

    }


    /**
     * 获取闹钟通知Intent
     *
     * @return
     */
    private PendingIntent getIntent(long id, int flagDayOfWeek) {

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.ID_FLAG, String.valueOf(id));

        return PendingIntent.getBroadcast(mContext,
                Integer.valueOf(String.valueOf(id)) + flagDayOfWeek, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
