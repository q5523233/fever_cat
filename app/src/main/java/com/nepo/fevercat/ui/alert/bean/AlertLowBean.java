package com.nepo.fevercat.ui.alert.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.bean
 * 文件名:  AlertLowBean
 * 作者 :   <sen>
 * 时间 :  下午5:11 2017/6/28.
 * 描述 :  低温列表
 */
@Entity
public class AlertLowBean implements Serializable{

    private static final long serialVersionUID = 1L; //这个是缺省的

    @Id
    private Long id;
    @Property
    private String mTitle;
    @Property
    private String mTime;
    @Property
    private String mRepeatType;
    @Property
    private String mRepeatCode;
    @Property
    private String mActive;
    @Property
    private String mWakeType;
    @Property
    private String mRing;
    @Property
    private String mAlertStatus;// 提醒类别 [用药、喝水]
    @Property
    private boolean mIsEnabledAlert;// 是否开启提醒
    @Property
    private String medicineName;// 药品名称

    @Property
    private String mWaterBeginTime; // 喝水开始时间
    @Property
    private String mWaterBeginTimeConvert;// 喝水开始时间 [判断使用]
    @Property
    private String mWaterEndTime;// 喝水结束时间
    @Property
    private String mWaterEndTimeConvert;// 喝水结束时间 [判断使用]
    @Property
    private String mWaterUnits;// 喝水量
    @Property
    private String mWaterInterval;// 喝水提醒间隔
    @Property
    private String mWaterIntervalMilliSecond;// 喝水提醒间隔 毫秒
    @Property
    private String mWaterLastIntervalMilliSecond;// 喝水最后提醒提醒时间 毫秒
    private boolean isSelect;
    @Property
    private String belongBaby;//指定宝宝id(用药有效)

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Generated(hash = 11361376)
    public AlertLowBean(Long id, String mTitle, String mTime, String mRepeatType,
            String mRepeatCode, String mActive, String mWakeType, String mRing,
            String mAlertStatus, boolean mIsEnabledAlert, String medicineName,
            String mWaterBeginTime, String mWaterBeginTimeConvert, String mWaterEndTime,
            String mWaterEndTimeConvert, String mWaterUnits, String mWaterInterval,
            String mWaterIntervalMilliSecond, String mWaterLastIntervalMilliSecond,
            boolean isSelect, String belongBaby) {
        this.id = id;
        this.mTitle = mTitle;
        this.mTime = mTime;
        this.mRepeatType = mRepeatType;
        this.mRepeatCode = mRepeatCode;
        this.mActive = mActive;
        this.mWakeType = mWakeType;
        this.mRing = mRing;
        this.mAlertStatus = mAlertStatus;
        this.mIsEnabledAlert = mIsEnabledAlert;
        this.medicineName = medicineName;
        this.mWaterBeginTime = mWaterBeginTime;
        this.mWaterBeginTimeConvert = mWaterBeginTimeConvert;
        this.mWaterEndTime = mWaterEndTime;
        this.mWaterEndTimeConvert = mWaterEndTimeConvert;
        this.mWaterUnits = mWaterUnits;
        this.mWaterInterval = mWaterInterval;
        this.mWaterIntervalMilliSecond = mWaterIntervalMilliSecond;
        this.mWaterLastIntervalMilliSecond = mWaterLastIntervalMilliSecond;
        this.isSelect = isSelect;
        this.belongBaby = belongBaby;
    }

    @Generated(hash = 532863234)
    public AlertLowBean() {
    }


    @Override
    public String toString() {
        return "AlertLowBean{" +
                "id=" + id +
                ", mTitle='" + mTitle + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mRepeatType='" + mRepeatType + '\'' +
                ", mRepeatCode='" + mRepeatCode + '\'' +
                ", mActive='" + mActive + '\'' +
                ", mWakeType='" + mWakeType + '\'' +
                ", mRing='" + mRing + '\'' +
                ", mAlertStatus='" + mAlertStatus + '\'' +
                ", mIsEnabledAlert=" + mIsEnabledAlert +
                ", medicineName='" + medicineName + '\'' +
                ", mWaterBeginTime='" + mWaterBeginTime + '\'' +
                ", mWaterBeginTimeConvert='" + mWaterBeginTimeConvert + '\'' +
                ", mWaterEndTime='" + mWaterEndTime + '\'' +
                ", mWaterEndTimeConvert='" + mWaterEndTimeConvert + '\'' +
                ", mWaterUnits='" + mWaterUnits + '\'' +
                ", mWaterInterval='" + mWaterInterval + '\'' +
                ", mWaterIntervalMilliSecond='" + mWaterIntervalMilliSecond + '\'' +
                ", mWaterLastIntervalMilliSecond='" + mWaterLastIntervalMilliSecond + '\'' +
                '}';
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getMTitle() {
        return this.mTitle;
    }


    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    public String getMTime() {
        return this.mTime;
    }


    public void setMTime(String mTime) {
        this.mTime = mTime;
    }


    public String getMRepeatType() {
        return this.mRepeatType;
    }


    public void setMRepeatType(String mRepeatType) {
        this.mRepeatType = mRepeatType;
    }


    public String getMRepeatCode() {
        return this.mRepeatCode;
    }


    public void setMRepeatCode(String mRepeatCode) {
        this.mRepeatCode = mRepeatCode;
    }


    public String getMActive() {
        return this.mActive;
    }


    public void setMActive(String mActive) {
        this.mActive = mActive;
    }


    public String getMWakeType() {
        return this.mWakeType;
    }


    public void setMWakeType(String mWakeType) {
        this.mWakeType = mWakeType;
    }


    public String getMRing() {
        return this.mRing;
    }


    public void setMRing(String mRing) {
        this.mRing = mRing;
    }


    public String getMAlertStatus() {
        return this.mAlertStatus;
    }


    public void setMAlertStatus(String mAlertStatus) {
        this.mAlertStatus = mAlertStatus;
    }


    public boolean getMIsEnabledAlert() {
        return this.mIsEnabledAlert;
    }


    public void setMIsEnabledAlert(boolean mIsEnabledAlert) {
        this.mIsEnabledAlert = mIsEnabledAlert;
    }


    public String getMedicineName() {
        return this.medicineName;
    }


    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }


    public String getMWaterBeginTime() {
        return this.mWaterBeginTime;
    }


    public void setMWaterBeginTime(String mWaterBeginTime) {
        this.mWaterBeginTime = mWaterBeginTime;
    }


    public String getMWaterBeginTimeConvert() {
        return this.mWaterBeginTimeConvert;
    }


    public void setMWaterBeginTimeConvert(String mWaterBeginTimeConvert) {
        this.mWaterBeginTimeConvert = mWaterBeginTimeConvert;
    }


    public String getMWaterEndTime() {
        return this.mWaterEndTime;
    }


    public void setMWaterEndTime(String mWaterEndTime) {
        this.mWaterEndTime = mWaterEndTime;
    }


    public String getMWaterEndTimeConvert() {
        return this.mWaterEndTimeConvert;
    }


    public void setMWaterEndTimeConvert(String mWaterEndTimeConvert) {
        this.mWaterEndTimeConvert = mWaterEndTimeConvert;
    }


    public String getMWaterUnits() {
        return this.mWaterUnits;
    }


    public void setMWaterUnits(String mWaterUnits) {
        this.mWaterUnits = mWaterUnits;
    }


    public String getMWaterInterval() {
        return this.mWaterInterval;
    }


    public void setMWaterInterval(String mWaterInterval) {
        this.mWaterInterval = mWaterInterval;
    }


    public String getMWaterIntervalMilliSecond() {
        return this.mWaterIntervalMilliSecond;
    }


    public void setMWaterIntervalMilliSecond(String mWaterIntervalMilliSecond) {
        this.mWaterIntervalMilliSecond = mWaterIntervalMilliSecond;
    }


    public String getMWaterLastIntervalMilliSecond() {
        return this.mWaterLastIntervalMilliSecond;
    }


    public void setMWaterLastIntervalMilliSecond(String mWaterLastIntervalMilliSecond) {
        this.mWaterLastIntervalMilliSecond = mWaterLastIntervalMilliSecond;
    }

    public boolean getIsSelect() {
        return this.isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getBelongBaby() {
        return this.belongBaby;
    }

    public void setBelongBaby(String belongBaby) {
        this.belongBaby = belongBaby;
    }
}
