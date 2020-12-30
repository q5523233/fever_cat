package com.nepo.fevercat.ui.main.bean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.bean
 * 文件名:  MainValidateResBean
 * 作者 :   <sen>
 * 时间 :  下午3:03 2017/6/20.
 * 描述 :
 */

public class MainValidateResBean extends BaseResBean {

    private String yzm;
    private String msgSid;

    public String getYzm() {
        return yzm;
    }

    public MainValidateResBean setYzm(String yzm) {
        this.yzm = yzm;
        return this;
    }

    public String getMsgSid() {
        return msgSid;
    }

    public MainValidateResBean setMsgSid(String msgSid) {
        this.msgSid = msgSid;
        return this;
    }
}
