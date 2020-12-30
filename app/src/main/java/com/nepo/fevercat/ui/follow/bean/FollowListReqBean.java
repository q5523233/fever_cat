package com.nepo.fevercat.ui.follow.bean;

import com.nepo.fevercat.ui.main.bean.BaseReqBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.bean
 * 文件名:  FollowListReqBean
 * 作者 :   <sen>
 * 时间 :  上午11:56 2017/6/24.
 * 描述 :
 */

public class FollowListReqBean extends BaseReqBean {

    private String accountId;
    private String bindId;


    public FollowListReqBean setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public FollowListReqBean setBindId(String bindId) {
        this.bindId = bindId;
        return this;
    }
}
