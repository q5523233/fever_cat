package com.nepo.fevercat.ui.main.bean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.bean
 * 文件名:  MainLoginResBean
 * 作者 :   <sen>
 * 时间 :  下午4:03 2017/6/20.
 * 描述 :
 */

public class MainLoginResBean extends BaseResBean {


    private String userId;
    private String nickName;
    private String headImageUrl;
    private String phone;

    public String getUserId() {
        return userId;
    }

    public MainLoginResBean setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public MainLoginResBean setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public MainLoginResBean setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public MainLoginResBean setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public String toString() {
        return "MainLoginResBean{" +
                "userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headImageUrl='" + headImageUrl + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
