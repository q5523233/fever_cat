package com.nepo.fevercat.ui.main.bean;

import com.nepo.fevercat.common.utils.EncryptUtils;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.bean
 * 文件名:  MainForgetReqBean
 * 作者 :   <sen>
 * 时间 :  下午4:52 2017/6/20.
 * 描述 :
 */

public class MainForgetReqBean extends BaseReqBean {
    private String userName;
    private String validateCode;
    private String newPwd;
    private String msgSid;

    public MainForgetReqBean setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public MainForgetReqBean setValidateCode(String validateCode) {
        this.validateCode = validateCode;
        return this;
    }

    public MainForgetReqBean setNewPwd(String newPwd) {
        this.newPwd = EncryptUtils.encryptMD5ToString(newPwd);
        return this;
    }

    public MainForgetReqBean setMsgSid(String msgSid) {
        this.msgSid = msgSid;
        return this;
    }
}
