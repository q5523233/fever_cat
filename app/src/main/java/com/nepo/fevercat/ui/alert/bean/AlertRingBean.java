package com.nepo.fevercat.ui.alert.bean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.bean
 * 文件名:  AlertRingBean
 * 作者 :   <sen>
 * 时间 :  下午4:00 2017/6/30.
 * 描述 :  提醒铃声
 */

public class AlertRingBean {


    private String ringName;
    private String ringUrl;
    private boolean isCheck;

    public String getRingName() {
        return ringName;
    }

    public AlertRingBean setRingName(String ringName) {
        this.ringName = ringName;
        return this;
    }

    public String getRingUrl() {
        return ringUrl;
    }

    public AlertRingBean setRingUrl(String ringUrl) {
        this.ringUrl = ringUrl;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public AlertRingBean setCheck(boolean check) {
        isCheck = check;
        return this;
    }
}
