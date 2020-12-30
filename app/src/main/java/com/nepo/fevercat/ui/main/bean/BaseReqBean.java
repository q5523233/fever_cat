package com.nepo.fevercat.ui.main.bean;

import java.io.Serializable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.bean
 * 文件名:  BaseReqBean
 * 作者 :   <sen>
 * 时间 :  下午2:36 2017/6/20.
 * 描述 :
 */

public class BaseReqBean implements Serializable {

    private String TRANSID;

    public BaseReqBean setTRANSID(String TRANSID) {
        this.TRANSID = TRANSID;
        return this;
    }
}
