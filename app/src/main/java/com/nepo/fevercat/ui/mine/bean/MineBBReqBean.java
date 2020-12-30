package com.nepo.fevercat.ui.mine.bean;

import android.text.TextUtils;

import com.nepo.fevercat.ui.main.bean.BaseReqBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.bean
 * 文件名:  MineBBReqBean
 * 作者 :   <sen>
 * 时间 :  下午4:11 2017/6/27.
 * 描述 :  宝宝信息
 */

public class MineBBReqBean extends BaseReqBean {

    private String accountId;
    private String operateID;

    // 新增
    private String nickname;
    private String sex;
    private String birthday;
    private String headImageId;
    private String localImgUrl; // 本地图片路径

    // 修改/删除
    private String babyId;

    // 宝宝本地Id
    private String localUUID;

    // 宝宝数据库ID
    private long id;


    public MineBBReqBean setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public MineBBReqBean setOperateID(String operateID) {
        this.operateID = operateID;
        return this;
    }

    public MineBBReqBean setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MineBBReqBean setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public MineBBReqBean setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public MineBBReqBean setHeadImageId(String headImageId) {
        if (TextUtils.equals(headImageId,"None")) {
            headImageId = "";
        }
        this.headImageId = headImageId;
        return this;
    }

    public MineBBReqBean setBabyId(String babyId) {
        if (TextUtils.equals(babyId,"None")) {
            babyId = "";
        }
        this.babyId = babyId;
        return this;
    }

    public String getOperateID() {
        return operateID;
    }

    public String getLocalImgUrl() {
        return localImgUrl;
    }

    public MineBBReqBean setLocalImgUrl(String localImgUrl) {
        this.localImgUrl = localImgUrl;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHeadImageId() {
        return headImageId;
    }

    public String getBabyId() {
        return babyId;
    }

    public String getLocalUUID() {
        return localUUID;
    }

    public MineBBReqBean setLocalUUID(String localUUID) {
        this.localUUID = localUUID;
        return this;
    }

    public long getId() {
        return id;
    }

    public MineBBReqBean setId(long id) {
        this.id = id;
        return this;
    }
}
