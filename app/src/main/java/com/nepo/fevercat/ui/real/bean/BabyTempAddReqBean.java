package com.nepo.fevercat.ui.real.bean;

import com.nepo.fevercat.ui.main.bean.BaseReqBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.real.bean
 * 文件名:  BabyTempAddReqBean
 * 作者 :   <sen>
 * 时间 :  下午4:51 2017/7/19.
 * 描述 :
 */

public class BabyTempAddReqBean extends BaseReqBean {

    private String accountId;

    private List<TemperaturesBean> temperatures;// 传入的温度 用于解析


    public String getAccountId() {
        return accountId;
    }

    public BabyTempAddReqBean setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public List<TemperaturesBean> getTemperatures() {
        return temperatures;
    }

    public BabyTempAddReqBean setTemperatures(List<TemperaturesBean> temperatures) {
        this.temperatures = temperatures;
        return this;
    }
}
