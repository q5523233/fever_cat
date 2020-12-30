package com.nepo.fevercat.ui.main.bean;

import com.nepo.fevercat.common.utils.EncryptUtils;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.bean
 * 文件名:  MainRegisterReqBean
 * 作者 :   <sen>
 * 时间 :  下午3:35 2017/6/20.
 * 描述 :
 */

public class MainRegisterReqBean extends BaseReqBean {

    private String userPwd;
    private String userName;
    private String msgSid;
    private String validateCode;

    public MainRegisterReqBean setUserPwd(String userPwd) {
        this.userPwd = EncryptUtils.encryptMD5ToString(userPwd);
        return this;
    }

    public MainRegisterReqBean setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public MainRegisterReqBean setMsgSid(String msgSid) {
        this.msgSid = msgSid;
        return this;
    }

    public MainRegisterReqBean setValidateCode(String validateCode) {
        this.validateCode = validateCode;
        return this;
    }
}
