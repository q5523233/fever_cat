package com.nepo.fevercat.ui.mine.bean;

import com.nepo.fevercat.app.AppConstant;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.bean
 * 文件名:  BabyInfosBean
 * 作者 :   <sen>
 * 时间 :  上午9:56 2017/7/21.
 * 描述 :
 */
@Entity
public class BabyInfosBean implements Serializable {

    private static final long serialVersionUID = 1L; //这个是缺省的
    /********************************服务器数据***************************************/
    @Property
    private String birthday;
    @Property
    private String sex;
    @Property
    private String headImageUrl;
    @Property
    private String headImageId;
    @Property
    private String nickname;
    @Property
    private String babyId;// 服务器id

    /********************************本地数据***************************************/
    @Id
    private Long id;
    @Property
    private String accountId;// 用户Id
    @Property
    private String localId;// 本地id
    @Property
    private String controlStatus; // 宝宝状态 增、删、改、已同步
    @Property
    private boolean isAdd = false;//是否是添加按钮
    @Property
    private String isScaleBig = "0"; // 是否放大 0缩小 1放大



    public String getControlStatus() {
        return this.controlStatus;
    }
    public void setControlStatus(String controlStatus) {
        this.controlStatus = controlStatus;
    }
    public String getLocalId() {
        return this.localId;
    }
    public void setLocalId(String localId) {
        this.localId = localId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBabyId() {
        if (AppConstant.IS_OFFLINEMODE)
        {
            return this.localId;
        }
        return this.babyId;
    }
    public void setBabyId(String babyId) {
        if (AppConstant.IS_OFFLINEMODE&&this.localId==null)
        {
            this.localId=babyId;
        }
        this.babyId = babyId;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getHeadImageUrl() {
        return this.headImageUrl;
    }
    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getAccountId() {
        return this.accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    @Generated(hash = 2117789489)
    public BabyInfosBean(String birthday, String sex, String headImageUrl,
            String headImageId, String nickname, String babyId, Long id,
            String accountId, String localId, String controlStatus, boolean isAdd,
            String isScaleBig) {
        this.birthday = birthday;
        this.sex = sex;
        this.headImageUrl = headImageUrl;
        this.headImageId = headImageId;
        this.nickname = nickname;
        this.babyId = babyId;
        this.id = id;
        this.accountId = accountId;
        this.localId = localId;
        this.controlStatus = controlStatus;
        this.isAdd = isAdd;
        this.isScaleBig = isScaleBig;
    }
    @Generated(hash = 57471101)
    public BabyInfosBean() {
    }

    public String getHeadImageId() {
        return this.headImageId;
    }
    public void setHeadImageId(String headImageId) {
        this.headImageId = headImageId;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public BabyInfosBean setAdd(boolean add) {
        isAdd = add;
        return this;
    }



    public boolean getIsAdd() {
        return this.isAdd;
    }
    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }
    public String getIsScaleBig() {
        return this.isScaleBig;
    }
    public void setIsScaleBig(String isScaleBig) {
        this.isScaleBig = isScaleBig;
    }

    @Override
    public String toString() {
        return "BabyInfosBean{" +
                "birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", headImageUrl='" + headImageUrl + '\'' +
                ", headImageId='" + headImageId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", babyId='" + babyId + '\'' +
                ", id=" + id +
                ", accountId='" + accountId + '\'' +
                ", localId='" + localId + '\'' +
                ", controlStatus='" + controlStatus + '\'' +
                ", isAdd=" + isAdd +
                ", isScaleBig='" + isScaleBig + '\'' +
                '}';
    }
}
