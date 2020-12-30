package com.nepo.fevercat.event;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.event
 * 文件名:  BluetoothStatusEvent
 * 作者 :   <sen>
 * 时间 :  下午6:56 2017/7/12.
 * 描述 :  蓝牙状态msg
 */

public class BluetoothStatusEvent {

    // 蓝牙是否开启
    private boolean isStatusOpen;

    public boolean isStatusOpen() {
        return isStatusOpen;
    }

    public BluetoothStatusEvent setStatusOpen(boolean statusOpen) {
        isStatusOpen = statusOpen;
        return this;
    }
}
