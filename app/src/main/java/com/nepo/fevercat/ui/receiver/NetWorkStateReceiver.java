package com.nepo.fevercat.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nepo.fevercat.common.utils.NetWorkUtils;
import com.nepo.fevercat.event.NetStatusEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.receiver
 * 文件名:  NetWorkStateReceiver
 * 作者 :   <sen>
 * 时间 :  下午6:57 2017/7/20.
 * 描述 :
 */

public class NetWorkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean netConnected = NetWorkUtils.isNetConnected(context);
        EventBus.getDefault().post(new NetStatusEvent().setNetConn(netConnected));

    }
}
