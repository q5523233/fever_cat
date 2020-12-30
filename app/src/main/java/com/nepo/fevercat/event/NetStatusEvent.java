package com.nepo.fevercat.event;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.event
 * 文件名:  NetStatusEvent
 * 作者 :   <sen>
 * 时间 :  下午6:57 2017/7/20.
 * 描述 :
 */

public class NetStatusEvent {

    private boolean isNetConn;

    public boolean isNetConn() {
        return isNetConn;
    }

    public NetStatusEvent setNetConn(boolean netConn) {
        isNetConn = netConn;
        return this;
    }
}
