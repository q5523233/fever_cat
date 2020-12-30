package com.nepo.fevercat.ui.real.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.nepo.fevercat.app.AppManager;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.real.service
 * 文件名:  ProcessMonitorService
 * 作者 :   <sen>
 * 时间 :  上午9:39 2017/8/15.
 * 描述 :  应用是否可见
 */

public class ProcessMonitorService extends Service {

    private long mVisibleTime = 0l;

    private long maxTime = 2400; // x/2= 20分钟

    private Subscription processSub;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (processSub != null) {
            if (!processSub.isUnsubscribed()) {
                processSub.unsubscribe();
            }
        }
        processSub = Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        boolean b = ConstantUtils.IsTopActivity("com.nepo.fevercat");
                        if (mVisibleTime > maxTime) {
                            // 退出程序
                            exit();
                        }
                        if (!b) {
                            mVisibleTime += 2;
                        }
                    }
                });


        return super.onStartCommand(intent, flags, startId);
    }


    private void exit(){
        LogUtils.logd("-- 退出APP");
        mVisibleTime = 0l;
        if (processSub != null) {
            if (!processSub.isUnsubscribed()) {
                processSub.unsubscribe();
            }
        }
        stopSelf();
        // 结束所有Activity
        AppManager.getAppManager().finishAllActivity();
        // 退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (processSub != null) {
            if (!processSub.isUnsubscribed()) {
                processSub.unsubscribe();
            }
        }

    }
}
