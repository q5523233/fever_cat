package com.nepo.fevercat.ui.main.bean;

import com.nepo.fevercat.common.utils.EncryptUtils;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.bean
 * 文件名:  MainLoginReqBean
 * 作者 :   <sen>
 * 时间 :  下午4:01 2017/6/20.
 * 描述 :
 */

public class MainLoginReqBean extends BaseReqBean {

    private String userName;
    private String userPwd;
    private String sysType;
    private String sysVersion;
    private String deviceToken;
    private String uuid;

    public MainLoginReqBean setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public MainLoginReqBean setUserPwd(String userPwd) {
        this.userPwd = EncryptUtils.encryptMD5ToString(userPwd);
        return this;
    }

    public MainLoginReqBean setSysType(String sysType) {
        this.sysType = sysType;
        return this;
    }

    public MainLoginReqBean setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
        return this;
    }

    public MainLoginReqBean setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
        return this;
    }

    public MainLoginReqBean setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
