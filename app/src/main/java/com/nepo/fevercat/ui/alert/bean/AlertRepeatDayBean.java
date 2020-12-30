package com.nepo.fevercat.ui.alert.bean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.bean
 * 文件名:  AlertRepeatDayBean
 * 作者 :   <sen>
 * 时间 :  上午11:43 2017/6/30.
 * 描述 :  重复天数
 */

public class AlertRepeatDayBean {

    private String title;
    private String code;
    private boolean isCheck;

    public String getTitle() {
        return title;
    }

    public AlertRepeatDayBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public AlertRepeatDayBean setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    public String getCode() {
        return code;
    }

    public AlertRepeatDayBean setCode(String code) {
        this.code = code;
        return this;
    }
}
