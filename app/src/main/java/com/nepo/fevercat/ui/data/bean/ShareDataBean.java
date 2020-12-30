package com.nepo.fevercat.ui.data.bean;

import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import java.io.Serializable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.bean
 * 文件名:  ShareDataBean
 * 作者 :   <sen>
 * 时间 :  下午4:25 2017/8/9.
 * 描述 :  分享数据
 */

public class ShareDataBean implements Serializable {


    BabyInfosBean mBabyInfosBean;
    TempHistoryDataResBean mTempHistoryDataResBean;
    TempOneDayDataResBean mTempOneDayDataResBean;
    String time;


    public BabyInfosBean getBabyInfosBean() {
        return mBabyInfosBean;
    }

    public ShareDataBean setBabyInfosBean(BabyInfosBean babyInfosBean) {
        mBabyInfosBean = babyInfosBean;
        return this;
    }

    public TempHistoryDataResBean getTempHistoryDataResBean() {
        return mTempHistoryDataResBean;
    }

    public ShareDataBean setTempHistoryDataResBean(TempHistoryDataResBean tempHistoryDataResBean) {
        mTempHistoryDataResBean = tempHistoryDataResBean;
        return this;
    }

    public TempOneDayDataResBean getTempOneDayDataResBean() {
        return mTempOneDayDataResBean;
    }

    public ShareDataBean setTempOneDayDataResBean(TempOneDayDataResBean tempOneDayDataResBean) {
        mTempOneDayDataResBean = tempOneDayDataResBean;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ShareDataBean setTime(String time) {
        this.time = time;
        return this;
    }

    @Override
    public String toString() {
        return "ShareDataBean{" +
                "mBabyInfosBean=" + mBabyInfosBean +
                ", mTempHistoryDataResBean=" + mTempHistoryDataResBean +
                ", mTempOneDayDataResBean=" + mTempOneDayDataResBean +
                ", time='" + time + '\'' +
                '}';
    }
}
