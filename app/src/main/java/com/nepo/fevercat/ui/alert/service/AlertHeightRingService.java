package com.nepo.fevercat.ui.alert.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.nepo.fevercat.BuildConfig;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.common.utils.GreenDaoAlertHeightUtils;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.event.AlertDialogEvent;
import com.nepo.fevercat.event.AlertHeightRingEvent;
import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.service
 * 文件名:  AlertHeightRingService
 * 作者 :   <sen>
 * 时间 :  下午5:56 2017/7/31.
 * 描述 :  高温提醒服务
 */

public class AlertHeightRingService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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


    /**
     * 高温提醒
     *
     * @param alertHeightRingEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRingGetMsg(AlertHeightRingEvent alertHeightRingEvent) {
        if (alertHeightRingEvent.getTemp()!=null) {
            String temp = alertHeightRingEvent.getTemp();
            getAlertHeightTempList(temp);
        }else{
            // (首页显示报警如果有多个显示最小的那个值)
            List<AlertHeightBean> alertHeightListByTemp = GreenDaoAlertHeightUtils.getInstance().getAllAlertHeightListByCheckStatus();
            if (alertHeightListByTemp!=null && alertHeightListByTemp.size() > 0) {
                AlertHeightBean alertHeightBean = alertHeightListByTemp.get(0);
                // 保存高温报警温度
                AppConstant.mCurrentAlertHeightTemp = alertHeightBean.getSTemp();
            }else {
                AppConstant.mCurrentAlertHeightTemp = "";
            }
        }

    }


    /**
     * 获取与当前温度差值最小的温度并检测该温度是否满足提醒间隔时长
     *
     * @param temp
     */
    private void getAlertHeightTempList(String temp) {

        List<AlertHeightBean> alertHeightListByTemp = GreenDaoAlertHeightUtils.getInstance().getAlertHeightListByTemp(temp);
        // 如果大于 0
        if (alertHeightListByTemp.size() > 0) {
            AlertHeightBean alertHeightBean = alertHeightListByTemp.get(0);
            // 高温数据
            AppConstant.mAlertHeightBean = alertHeightBean;
            // 保存高温报警温度
            AppConstant.mCurrentAlertHeightTemp = alertHeightBean.getsLeTemp();
            Log.e("tag", "getAlertHeightTempList: "+alertHeightBean.getSTemp() );
            Long reduceRingMill = 0l;
            Long intervalRingMill = Long.valueOf(alertHeightBean.getSMillisecond());
            if (BuildConfig.DEBUG)
            {
                intervalRingMill=0l;
            }
            if (!TextUtils.isEmpty(alertHeightBean.getSLastRingMillisecond())) {
                reduceRingMill = System.currentTimeMillis() - Long.valueOf(alertHeightBean.getSLastRingMillisecond());
            }
            // 满足提醒间隔 或 未响过铃声 或体温下降为低温状态
            if (reduceRingMill > intervalRingMill || reduceRingMill == 0||AppConstant.isLowTemp) {
                // 弹出高温弹窗并更新最后提醒时间
                alertHeightBean.setSLastRingMillisecond(String.valueOf(System.currentTimeMillis()));
                //GreenDaoAlertHeightUtils.getInstance().updateAlertHeight(alertHeightBean);
                // 低温状态
                AppConstant.isLowTemp = false;
                // 提醒
                LogUtils.logd("----------------------- 高温警报 --------------------");
                EventBus.getDefault().post(new AlertDialogEvent().setAlertHeight(true));
            }


        }else{
            AppConstant.isLowTemp = true;
        }


        // 2017年09月14日15:16:20 修复Bug添加
        // 如果一个高温都没有 就清空当前高温
        List<AlertHeightBean> allAlertHeightList = GreenDaoAlertHeightUtils.getInstance().getAllAlertHeightList();
        if (allAlertHeightList.size()==0) {
            AppConstant.mCurrentAlertHeightTemp="";
            AppConstant.mAlertHeightBean = null;
        }

    }


}
