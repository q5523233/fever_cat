package com.nepo.fevercat.ui.real.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.real.bean
 * 文件名:  TemperaturesBean
 * 作者 :   <sen>
 * 时间 :  下午5:17 2017/7/24.
 * 描述 : 体温数据
 */
@Entity
public class TemperaturesBean {

    /********************************服务器数据***************************************/
    @Property
    private String babyId; //宝宝ID
    @Property
    private String temperature; //宝宝体温
    @Property
    private String temperatureRight; //宝宝第二路体温
    @Property
    private String tempHex; // 温度的原始数据，用于后台校验,16进制
    @Property
    private String tempTime; // 宝宝体温的时间
    /********************************本地数据***************************************/
    private long   time;  //宝宝体温的时间戳
    @Id
    private Long id;// 自增ID
    @Property
    private String localBabyId;// 本地ID 用于关联本地添加的宝宝
    @Property
    private String accountId;// 用户ID用于筛选当前用户的数据
    private String leftAdjustValue;
    private String RightAdjustValue;
    @Generated(hash = 277255294)
    public TemperaturesBean(String babyId, String temperature,
            String temperatureRight, String tempHex, String tempTime, long time,
            Long id, String localBabyId, String accountId, String leftAdjustValue,
            String RightAdjustValue) {
        this.babyId = babyId;
        this.temperature = temperature;
        this.temperatureRight = temperatureRight;
        this.tempHex = tempHex;
        this.tempTime = tempTime;
        this.time = time;
        this.id = id;
        this.localBabyId = localBabyId;
        this.accountId = accountId;
        this.leftAdjustValue = leftAdjustValue;
        this.RightAdjustValue = RightAdjustValue;
    }
    @Generated(hash = 1667647766)
    public TemperaturesBean() {
    }
    public String getBabyId() {
        return this.babyId;
    }
    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }
    public String getTemperature() {
        return this.temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String getTemperatureRight() {
        return this.temperatureRight;
    }
    public void setTemperatureRight(String temperatureRight) {
        this.temperatureRight = temperatureRight;
    }
    public String getTempHex() {
        return this.tempHex;
    }
    public void setTempHex(String tempHex) {
        this.tempHex = tempHex;
    }
    public String getTempTime() {
        return this.tempTime;
    }
    public void setTempTime(String tempTime) {
        this.tempTime = tempTime;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLocalBabyId() {
        return this.localBabyId;
    }
    public void setLocalBabyId(String localBabyId) {
        this.localBabyId = localBabyId;
    }
    public String getAccountId() {
        return this.accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getLeftAdjustValue() {
        return this.leftAdjustValue;
    }
    public void setLeftAdjustValue(String leftAdjustValue) {
        this.leftAdjustValue = leftAdjustValue;
    }
    public String getRightAdjustValue() {
        return this.RightAdjustValue;
    }
    public void setRightAdjustValue(String RightAdjustValue) {
        this.RightAdjustValue = RightAdjustValue;
    }

}

