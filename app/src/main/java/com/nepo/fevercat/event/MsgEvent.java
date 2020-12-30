package com.nepo.fevercat.event;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.event
 * 文件名:  MsgEvent
 * 作者 :   <sen>
 * 时间 :  上午10:58 2017/2/15.
 * 描述 :  EventBus 事件通信类
 */

public class MsgEvent {

    private String msg;

    private String action;

    public MsgEvent(String action, String msg) {
        this.action = action;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
