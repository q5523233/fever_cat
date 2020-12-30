package com.nepo.fevercat.ui.alert.bean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.bean
 * 文件名:  AlertMedicineBean
 * 作者 :   <sen>
 * 时间 :  下午8:35 2017/6/29.
 * 描述 :
 */

public class AlertMedicineBean {

    private String name;//显示的数据
    private String sortLetters;//显示数据拼音的首字母


    public String getName() {
        return name;
    }

    public AlertMedicineBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public AlertMedicineBean setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
        return this;
    }
}
