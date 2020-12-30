package com.nepo.fevercat.ui.alert.bean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.bean
 * 文件名:  AlertMedicineTimeBean
 * 作者 :   <sen>
 * 时间 :  下午5:50 2017/6/29.
 * 描述 :  吃药时间
 */

public class AlertMedicineTimeBean {

    // 是否是默认添加按钮
    private boolean isDefaultAdd;
    private String timeStr;



    public boolean isDefaultAdd() {
        return isDefaultAdd;
    }

    public AlertMedicineTimeBean setDefaultAdd(boolean defaultAdd) {
        isDefaultAdd = defaultAdd;
        return this;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public AlertMedicineTimeBean setTimeStr(String timeStr) {
        this.timeStr = timeStr;
        return this;
    }

    @Override
    public String toString() {
        return "AlertMedicineTimeBean{" +
                "isDefaultAdd=" + isDefaultAdd +
                '}';
    }
}
