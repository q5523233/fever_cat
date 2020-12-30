package com.nepo.fevercat.ui.alert.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.bean
 * 文件名:  AlertHeightBean
 * 作者 :   <sen>
 * 时间 :  下午5:11 2017/6/28.
 * 描述 :  高温
 */
@Entity
public class AlertHeightBean implements Serializable{


    private static final long serialVersionUID = 1L; //这个是缺省的

    @Id
    private Long id;
    @Property
    private String sTemp; // 温度
    @Property
    private String sMillisecond; // 间隔时长 毫秒
    @Property
    private String sTimeTip;// 间隔时长 提示
    @Property
    private boolean sCheckStatus;// 是否选中
    @Property
    private String sLastRingMillisecond;// 最后一次响铃时间 毫秒 [判断下次响铃使用]
    @Property
    private String sGtTemp;// 最低温度 低温<x<高温
    @Property
    private String sLeTemp;// 最高温度 低温<x<高温
    @Property
    private String sSelectType;// 低热 0、中热1、高热2;

    private String belongBaby;//指定宝宝id




    @Generated(hash = 1603997088)
    public AlertHeightBean(Long id, String sTemp, String sMillisecond,
            String sTimeTip, boolean sCheckStatus, String sLastRingMillisecond,
            String sGtTemp, String sLeTemp, String sSelectType, String belongBaby) {
        this.id = id;
        this.sTemp = sTemp;
        this.sMillisecond = sMillisecond;
        this.sTimeTip = sTimeTip;
        this.sCheckStatus = sCheckStatus;
        this.sLastRingMillisecond = sLastRingMillisecond;
        this.sGtTemp = sGtTemp;
        this.sLeTemp = sLeTemp;
        this.sSelectType = sSelectType;
        this.belongBaby = belongBaby;
    }





    @Generated(hash = 1898534792)
    public AlertHeightBean() {
    }


    @Override
    public String toString() {
        return "AlertHeightBean{" +
                "id=" + id +
                ", sTemp='" + sTemp + '\'' +
                ", sMillisecond='" + sMillisecond + '\'' +
                ", sTimeTip='" + sTimeTip + '\'' +
                ", sCheckStatus=" + sCheckStatus +
                ", sLastRingMillisecond='" + sLastRingMillisecond + '\'' +
                ", sGtTemp='" + sGtTemp + '\'' +
                ", sLeTemp='" + sLeTemp + '\'' +
                ", sSelectType='" + sSelectType + '\'' +
                ", belongBaby='" + belongBaby + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }





    public void setId(Long id) {
        this.id = id;
    }





    public String getSTemp() {
        return this.sTemp;
    }





    public void setSTemp(String sTemp) {
        this.sTemp = sTemp;
    }





    public String getSMillisecond() {
        return this.sMillisecond;
    }





    public void setSMillisecond(String sMillisecond) {
        this.sMillisecond = sMillisecond;
    }





    public String getSTimeTip() {
        return this.sTimeTip;
    }





    public void setSTimeTip(String sTimeTip) {
        this.sTimeTip = sTimeTip;
    }





    public boolean getSCheckStatus() {
        return this.sCheckStatus;
    }





    public void setSCheckStatus(boolean sCheckStatus) {
        this.sCheckStatus = sCheckStatus;
    }





    public String getSLastRingMillisecond() {
        return this.sLastRingMillisecond;
    }





    public void setSLastRingMillisecond(String sLastRingMillisecond) {
        this.sLastRingMillisecond = sLastRingMillisecond;
    }

    public String getsGtTemp() {
        return sGtTemp;
    }


    public String getsLeTemp() {
        return sLeTemp;
    }






    public String getSGtTemp() {
        return this.sGtTemp;
    }





    public void setSGtTemp(String sGtTemp) {
        this.sGtTemp = sGtTemp;
    }





    public String getSLeTemp() {
        return this.sLeTemp;
    }





    public void setSLeTemp(String sLeTemp) {
        this.sLeTemp = sLeTemp;
    }





    public String getSSelectType() {
        return this.sSelectType;
    }





    public void setSSelectType(String sSelectType) {
        this.sSelectType = sSelectType;
    }





    public String getBelongBaby() {
        return this.belongBaby;
    }





    public void setBelongBaby(String belongBaby) {
        this.belongBaby = belongBaby;
    }
}
