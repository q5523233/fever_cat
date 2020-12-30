package com.nepo.fevercat.ui.data.bean;

import com.nepo.fevercat.ui.main.bean.BaseReqBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.bean
 * 文件名:  TempHistoryDataReqBean
 * 作者 :   <sen>
 * 时间 :  上午9:40 2017/7/4.
 * 描述 :
 */

public class TempHistoryDataReqBean extends BaseReqBean {

    public String accountId;
    public String babyId;
    public String queryTime;
    public String type;
    public String localId;

    public TempHistoryDataReqBean setLocalId(String localId) {
        this.localId = localId;
        return this;
    }

    public TempHistoryDataReqBean setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public TempHistoryDataReqBean setBabyId(String babyId) {
        this.babyId = babyId;
        return this;
    }

    public TempHistoryDataReqBean setQueryTime(String queryTime) {
        this.queryTime = queryTime;
        return this;
    }

    public TempHistoryDataReqBean setType(String type) {
        this.type = type;
        return this;
    }
}
