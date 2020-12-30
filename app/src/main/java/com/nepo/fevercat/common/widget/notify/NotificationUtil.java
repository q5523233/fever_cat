package com.nepo.fevercat.common.widget.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * 通知栏工具
 */
public class NotificationUtil {

    /**传送上下文*/
    private Context mContext;
    /**窗口管理器*/
    private static NotificationManager mNotificationManager;

    /** 响铃2秒内不重复 */
    private static long mIntervalRingTime = 2*1000;
    private static long mLongLastTime;

    /**
     * 是否自动区分收到通知时app前后台处理：
     * 当处于后台时：设置状态栏显示信息
     * 当处于前台时：设置信息0.8秒后信息自动消失
     */
    private static boolean mAppIsForeGround = true;

    public NotificationUtil(Context context){
        this.mContext = context;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 设置间隔时间
     * @param intervalRingTime
     */
    public void setmIntervalRingTime(long intervalRingTime){
        mIntervalRingTime = intervalRingTime;
    }

    /**
     * 是否自动区分收到通知时app前后台处理：
     * 当处于后台时：设置状态栏显示信息
     * 当处于前台时：设置信息0.8秒后信息自动消失
     */
    public void setmIntervalRingTime(boolean appIsForeGround){
        mAppIsForeGround = appIsForeGround;
    }

    /** 设置状态栏显示信息
     * @param resId 图标（本地）
     * @param tickerText 消息内容(状态栏顶部自动显示)
     * @param title 消息来源
     * @param text 消息内容(下拉菜单显示)
     * @param mIntentClass 在状态栏点击消息后跳转的页面
     * @param type 推送消息类型*/
    public void setNotfiy(int resId, String tickerText, String title, String text, @NonNull Class mIntentClass, int type) {
        if (mAppIsForeGround && AppStateUtil.isAppIsForeGround()) {
            myClearNotfiy(resId,tickerText,title,text,mIntentClass);
        } else {
            myNormalNotfiy(resId,tickerText,title,text,mIntentClass,type);
        }
    }

    /** 设置状态栏显示信息
     * @param resId 图标（本地）
     * @param tickerText 消息内容(状态栏顶部自动显示)
     * @param title 消息来源
     * @param text 消息内容(下拉菜单显示)
     * @param mIntentClass 在状态栏点击消息后跳转的页面
     * @param type 推送消息类型*/
    public void myNormalNotfiy(int resId, String tickerText, String title, String text, @NonNull Class mIntentClass, int type) {
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, type, new Intent(mContext, mIntentClass), 0);
        long[] vibrate = { 0, 100, 200, 300 };
        Notification.Builder builder = new Notification.Builder(mContext)
                .setSmallIcon(resId)
                .setTicker(tickerText)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent);
        if (System.currentTimeMillis() - mLongLastTime > mIntervalRingTime){
            builder.setVibrate(vibrate);
            builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
            mLongLastTime = System.currentTimeMillis();
        }
        Notification notify = builder.getNotification();
        notify.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        mNotificationManager.notify(type, notify);
    }

    /**设置信息0.8秒后信息自动消失
     * @param tickerText 消息内容(状态栏顶部自动显示)
     * @param title 消息来源
     * @param text 消息内容(下拉菜单显示)
     * @param mIntentClass 在状态栏点击消息后跳转的页面
     * */
    public void myClearNotfiy(int resId, String tickerText, String title, String text, @NonNull Class mIntentClass){
        String time = System.currentTimeMillis() + "";
        final int notifyId = Integer.parseInt(time.substring(time.length() - 6, time.length()));
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, new Intent(mContext, mIntentClass), 0);
        long[] vibrate = { 0, 100, 200, 300 };
        Notification.Builder builder = new Notification.Builder(mContext)
                .setSmallIcon(resId)
                .setTicker(tickerText)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent);
        if (System.currentTimeMillis() - mLongLastTime > mIntervalRingTime){
            builder.setVibrate(vibrate);
            builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
            mLongLastTime = System.currentTimeMillis();
        }
        Notification notify = builder.getNotification();
        notify.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        mNotificationManager.notify(notifyId, notify);
        /** 设置1秒后自动消失信息 */
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    mNotificationManager.cancel(notifyId);// 取消显示该条信息
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    /**清除所有通知*/
    public void clearAllNotify(){
        if (mNotificationManager != null){
            mNotificationManager.cancelAll();
        }
    }
}
