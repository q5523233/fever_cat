package com.nepo.fevercat.ui.follow.bean;

import com.nepo.fevercat.ui.main.bean.BaseReqBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.bean
 * 文件名:  FollowAddReqBean
 * 作者 :   <sen>
 * 时间 :  下午5:04 2017/6/23.
 * 描述 :
 */

public class FollowAddReqBean extends BaseReqBean {


    private String accountId;
    private String userName;
    private String nickName;
    private String validateCode;
    private String msgSid;


    public FollowAddReqBean setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public FollowAddReqBean setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public FollowAddReqBean setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public FollowAddReqBean setValidateCode(String validateCode) {
        this.validateCode = validateCode;
        return this;
    }

    public FollowAddReqBean setMsgSid(String msgSid) {
        this.msgSid = msgSid;
        return this;
    }
}
