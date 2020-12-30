package com.nepo.fevercat.ui.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nepo.fevercat.event.BluetoothStatusEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.receiver
 * 文件名:  BlueToothStatusReceiver
 * 作者 :   <sen>
 * 时间 :  下午6:49 2017/7/12.
 * 描述 :  蓝牙开关监听
 */

public class BlueToothStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch(intent.getAction()){
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch(blueState){
                    case BluetoothAdapter.STATE_ON:
                        // 开
                        EventBus.getDefault().post(new BluetoothStatusEvent().setStatusOpen(true));
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        // 关
                        EventBus.getDefault().post(new BluetoothStatusEvent().setStatusOpen(false));
                        break;
                }
                break;
        }
    }
}
