package com.nepo.fevercat.event;

import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.event
 * 文件名:  AlertDialogEvent
 * 作者 :   <sen>
 * 时间 :  上午11:13 2017/8/1.
 * 描述 :  高温、喝水、用药 提醒
 */

public class AlertDialogEvent {

    private boolean isAlertHeight;

    private AlertLowBean mAlertLowBean;

    public boolean isAlertHeight() {
        return isAlertHeight;
    }

    public AlertDialogEvent setAlertHeight(boolean alertHeight) {
        isAlertHeight = alertHeight;
        return this;
    }

    public AlertLowBean getAlertLowBean() {
        return mAlertLowBean;
    }

    public AlertDialogEvent setAlertLowBean(AlertLowBean alertLowBean) {
        mAlertLowBean = alertLowBean;
        return this;
    }
}
