package com.nepo.fevercat.ui.alert.receiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.PowerManager;

import com.nepo.fevercat.R;
import com.nepo.fevercat.common.utils.GreenDaoAlertMedicineUtils;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.event.AlertDialogEvent;
import com.nepo.fevercat.ui.ContainerActivity;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import static android.app.Notification.DEFAULT_ALL;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alarm
 * 文件名:  AlarmReceiver
 * 作者 :   <sen>
 * 时间 :  下午4:21 2017/7/11.
 * 描述 :  闹钟广播
 */

public class AlarmReceiver extends BroadcastReceiver {


    public static final String ID_FLAG = "flag";
    private static final int ONE_WEEK_TIME = 1000 * 60 * 60 * 24 * 7;
    private AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        int alarmId = Integer.parseInt(intent.getStringExtra(ID_FLAG));
        LogUtils.logd("--------------------------- 闹钟响了-------------------" + alarmId);
        AlertLowBean alertLowBean = GreenDaoAlertMedicineUtils.getInstance().getAlertLowBeanByID(alarmId);
        // 提示吃药闹钟
        EventBus.getDefault().post(new AlertDialogEvent().setAlertHeight(false).setAlertLowBean(alertLowBean));
        showNotification(context, alertLowBean);
        // 再次设置对应闹钟(重复)
        setRepeatAlarm(context, alarmId);

    }


    private void setRepeatAlarm(Context mContext, int id) {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 0);
        long mTimeInfo = c.getTimeInMillis() + ONE_WEEK_TIME;
        int currentWeekOfDay = c.get(Calendar.DAY_OF_WEEK);

        Intent i = new Intent(mContext, AlarmReceiver.class);
        i.putExtra(AlarmReceiver.ID_FLAG, Integer.toString(id));
        PendingIntent pi = PendingIntent.getBroadcast(mContext, id + currentWeekOfDay, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 4.4及以上
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mTimeInfo, pi);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4以下
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mTimeInfo,
                    pi);

        }
    }


    public void showNotification(Context mContext, AlertLowBean alertLowBean) {
        //管理锁屏的一个服务
        KeyguardManager km = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        if (km.inKeyguardRestrictedInputMode()) {
            //获取电源管理器对象
            PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            if (!pm.isScreenOn()) {
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                wl.acquire();  //点亮屏幕
                wl.release();  //任务结束后释放
            }
            createNotification(mContext, alertLowBean);
        } else {
            Intent intent = new Intent(mContext, ContainerActivity.class);
            if (!(mContext instanceof Activity))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(alertLowBean.getMAlertStatus(), alertLowBean);
            mContext.startActivity(intent);
        }
    }


    /*创建通知*/
    private void createNotification(Context mContext, AlertLowBean data) {
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(mContext);
        //先解锁系统自带锁屏服务，放在锁屏界面里面
        KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        keyguardManager.newKeyguardLock("").disableKeyguard(); //解锁
        Intent intent = new Intent(mContext, ContainerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.fever_cat_logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.fever_cat_logo));
        builder.setAutoCancel(true);
        builder.setContentTitle("发烧猫");
        builder.setContentText("闹钟提醒");
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setDefaults(DEFAULT_ALL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        mNotificationManager.notify(2, builder.build());
    }
}
