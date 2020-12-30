package com.nepo.fevercat.ui.main.bean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.bean
 * 文件名:  MainValidateReqBean
 * 作者 :   <sen>
 * 时间 :  下午2:35 2017/6/20.
 * 描述 :
 */

public class MainValidateReqBean extends BaseReqBean {

    private String toNumber;

    public MainValidateReqBean setToNumber(String toNumber) {
        this.toNumber = toNumber;
        return this;
    }
}
