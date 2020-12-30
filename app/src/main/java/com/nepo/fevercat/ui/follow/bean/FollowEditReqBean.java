package com.nepo.fevercat.ui.follow.bean;

import com.nepo.fevercat.ui.main.bean.BaseReqBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.bean
 * 文件名:  FollowEditReqBean
 * 作者 :   <sen>
 * 时间 :  下午2:37 2017/8/10.
 * 描述 :
 */

public class FollowEditReqBean extends BaseReqBean {

    private String accountId;
    private String bindUserId;
    private String nickName;


    public FollowEditReqBean setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public FollowEditReqBean setBindUserId(String bindUserId) {
        this.bindUserId = bindUserId;
        return this;
    }

    public FollowEditReqBean setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
