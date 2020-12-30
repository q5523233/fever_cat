package com.nepo.fevercat.event;

import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.event
 * 文件名:  AlertMedicineEvent
 * 作者 :   <sen>
 * 时间 :  下午5:13 2017/8/1.
 * 描述 :
 */

public class AlertMedicineEvent {

    private boolean isCancelAlert;

    private AlertLowBean mAlertLowBean;

    public boolean isCancelAlert() {
        return isCancelAlert;
    }

    public AlertMedicineEvent setCancelAlert(boolean cancelAlert) {
        isCancelAlert = cancelAlert;
        return this;
    }

    public AlertLowBean getAlertLowBean() {
        return mAlertLowBean;
    }

    public AlertMedicineEvent setAlertLowBean(AlertLowBean alertLowBean) {
        mAlertLowBean = alertLowBean;
        return this;
    }
}
