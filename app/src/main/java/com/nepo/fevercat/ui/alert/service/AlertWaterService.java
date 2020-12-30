package com.nepo.fevercat.ui.alert.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nepo.fevercat.common.utils.GreenDaoAlertWaterUtils;
import com.nepo.fevercat.event.AlertDialogEvent;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.service
 * 文件名:  AlertWaterService
 * 作者 :   <sen>
 * 时间 :  下午6:08 2017/8/2.
 * 描述 :  喝水提醒
 */

public class AlertWaterService extends Service {


    Subscription mIntervalSubscription;
    // 上传时间间隔 秒
    private long TimeInterval = 5;
    Context mContext;

    SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
    SimpleDateFormat sdfSecond = new SimpleDateFormat("mm");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = AlertWaterService.this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIntervalSubscription != null) {
            if (!mIntervalSubscription.isUnsubscribed()) {
                mIntervalSubscription.unsubscribe();
                mIntervalSubscription = null;
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 启动每秒定时轮询
        intervalAlertWater();
        return super.onStartCommand(intent, flags, startId);

    }


    /**
     * 喝水提醒
     */
    private void intervalAlertWater() {
        mIntervalSubscription = Observable.interval(TimeInterval, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        String time = sdfHour.format(new Date())+sdfSecond.format(new Date());
                        // 获取当前时间段内的喝水提醒
                        List<AlertLowBean> alertLowBeanListByTime = GreenDaoAlertWaterUtils.getInstance().getAlertLowBeanListByTime(time);
                        for (AlertLowBean alertLowBean : alertLowBeanListByTime) {
                            if (TextUtils.isEmpty(alertLowBean.getMWaterLastIntervalMilliSecond())) {
                                // 更新最后一次提醒时间
                                alertLowBean.setMWaterLastIntervalMilliSecond(String.valueOf(System.currentTimeMillis()));
                                GreenDaoAlertWaterUtils.getInstance().updateAlertLowBean(alertLowBean);
                                // 通知界面更新
                                EventBus.getDefault().post(new AlertDialogEvent().setAlertHeight(false).setAlertLowBean(alertLowBean));
                                break;
                            }
                            Long intervalRingMill = Long.valueOf(alertLowBean.getMWaterIntervalMilliSecond());
                            Long reduceRingMill = System.currentTimeMillis() - Long.valueOf(alertLowBean.getMWaterLastIntervalMilliSecond());
                            // 超过时间间隔
                            if (reduceRingMill > intervalRingMill){
                                // 更新最后一次提醒时间
                                alertLowBean.setMWaterLastIntervalMilliSecond(String.valueOf(System.currentTimeMillis()));
                                GreenDaoAlertWaterUtils.getInstance().updateAlertLowBean(alertLowBean);
                                // 通知界面更新
                                EventBus.getDefault().post(new AlertDialogEvent().setAlertHeight(false).setAlertLowBean(alertLowBean));
                                break;
                            }

                        }
                    }
                });
    }



}
