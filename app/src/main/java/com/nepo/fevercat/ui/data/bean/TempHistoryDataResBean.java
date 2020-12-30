package com.nepo.fevercat.ui.data.bean;

import android.text.TextUtils;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.DateUtil;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.bean
 * 文件名:  TempHistoryDataResBean
 * 作者 :   <sen>
 * 时间 :  下午6:47 2017/7/3.
 * 描述 :
 */

public class TempHistoryDataResBean extends BaseResBean {


    public List<RecordBean> record;


    public List<RecordBean> getRecord() {
        return record;
    }

    @Override
    public String toString() {
        return "TempHistoryDataResBean{" +
                ", record=" + record +
                '}';
    }

    public static class RecordBean implements Serializable {
        public String year;
        public String hightTemperature="0";
        public String feverDay="0";
        public List<MonthBean> month;

        public String getYear() {
            return year;
        }

        public String getHightTemperature() {
            if (AppConstant.IS_OFFLINEMODE)
            {
                float maxTmp=0;
                for (MonthBean monthBean : month) {
                    maxTmp=Math.max(Float.valueOf(monthBean.getHightTemperature()),maxTmp);
                }
                return maxTmp+"";
            }
            return hightTemperature;
        }

        public String getFeverDay() {
            if (AppConstant.IS_OFFLINEMODE)
            {
                int sumFerverDay=0;
                for (MonthBean monthBean : month) {
                    sumFerverDay+=Integer.parseInt(monthBean.getFeverDay());
                }
                return sumFerverDay+"";
            }
            return feverDay;
        }

        public List<MonthBean> getMonth() {
            return month;
        }

        @Override
        public String toString() {
            return "RecordBean{" +
                    "year='" + year + '\'' +
                    ", hightTemperature='" + hightTemperature + '\'' +
                    ", feverDay='" + feverDay + '\'' +
                    ", month=" + month +
                    '}';
        }

        public MonthBean containsMonthData(int month, TemperaturesBean temperature, RecordBean recordBean) {
            MonthBean monthBean = new MonthBean();
            float temp = Float.parseFloat(temperature.getTemperature());
            if (this.month == null) {
                this.month = new ArrayList<>();
            } else {
                for (MonthBean bean : this.month) {
                    if (bean.monthNumber.equals(month + "")) {
                        bean.hightTemperature = Math.max(Float.parseFloat(bean.hightTemperature), temp) + "";
                        if (Integer.parseInt(bean.getFeverDay())>0)
                        {
                            this.feverDay=Integer.parseInt(this.feverDay)+1+"";
                        }
                        return bean;
                    }
                }
            }
            monthBean.monthNumber = month + "";
            monthBean.hightTemperature = Math.max(Float.parseFloat(monthBean.hightTemperature), temp) + "";
            recordBean.hightTemperature=Math.max(Float.parseFloat(monthBean.hightTemperature),Float.parseFloat(recordBean.hightTemperature))+"";
            this.month.add(monthBean);
            return monthBean;
        }

        public static class MonthBean implements Serializable {
            public String monthNumber = "0";
            public String hightTemperature = "0";
            public String feverDay = "0";
            public List<RecordDetailsBean> recordDetails;


            // 是否展开
            private boolean isExtendParent;

            public String getMonthNumber() {
                return monthNumber;
            }

            public String getHightTemperature() {
                return hightTemperature;
            }

            public String getFeverDay() {
                if (AppConstant.IS_OFFLINEMODE)
                {
                    int sumFerverDay=0;
                    for (RecordDetailsBean recordDetail : recordDetails) {
                        if (recordDetail.isFever)
                        sumFerverDay++;
                    }
                    return sumFerverDay+"";
                }
                return feverDay;
            }

            public List<RecordDetailsBean> getRecordDetails() {
                return recordDetails;
            }

            public boolean isExtendParent() {
                return isExtendParent;
            }

            public MonthBean setExtendParent(boolean extendParent) {
                isExtendParent = extendParent;
                return this;
            }

            @Override
            public String toString() {
                return "MonthBean{" +
                        "monthNumber='" + monthNumber + '\'' +
                        ", hightTemperature='" + hightTemperature + '\'' +
                        ", feverDay='" + feverDay + '\'' +
                        ", recordDetails=" + recordDetails +
                        '}';
            }

            public RecordDetailsBean containsRecordDetailsData(TemperaturesBean data, int day) {
                RecordDetailsBean detailsBean = new RecordDetailsBean();
                if (recordDetails == null) {
                    recordDetails = new ArrayList<>();
                } else {
                    for (RecordDetailsBean recordDetail : recordDetails) {
                        if (data.getTempTime().split(" ")[0].equals(recordDetail.recordDay)) {
                            //该天纪录存在
                            if (recordDetail.startTime == null) {
                                recordDetail.startTime = data.getTempTime();
                            }
                            //计算发烧时间
                            if (ConstantUtils.IsFever(data.getTemperature())||ConstantUtils.IsFever(data.getTemperatureRight()))
                            {
                                recordDetail.isFever=true;
                                if (recordDetail.startFeverTime==null)
                                {
                                    recordDetail.startFeverTime=data.getTempTime();
                                }else {
                                    long feverDel = DateUtil.parse(data.getTempTime()) - DateUtil.parse(recordDetail.startFeverTime);
                                    if (feverDel <=10000)//如果两条数据之间时间差超过10秒，认为是两次测量
                                    {
                                        recordDetail.totalFeverTime+=feverDel;
                                    }else {
                                        recordDetail.startFeverTime=data.getTempTime();
                                    }
                                }
                            }
                            //计算总纪录时间
                            long del= DateUtil.parse(data.getTempTime())-DateUtil.parse(recordDetail.startTime);
                            if (del<=10000)
                            {
                                recordDetail.totalTime+=del;
                            }else {
                                recordDetail.startTime=data.getTempTime();
                            }
                            recordDetail.recordTime = DateUtil.parseDateToString(recordDetail.totalTime);
                            recordDetail.recordSustain = DateUtil.parseDateToString(recordDetail.totalFeverTime);
                            return recordDetail;
                        }
                    }
                }
                detailsBean.recordTemperatureValue = data.getTemperature();
                detailsBean.recordTime = data.getTempTime();
                detailsBean.isExtend = true;
                detailsBean.recordDay = data.getTempTime().split(" ")[0];
                recordDetails.add(detailsBean);
                return null;
            }

            public static class RecordDetailsBean implements Serializable {
                private String recordDay;
                private String recordTemperatureValue;
                private String recordSustain;
                private String recordTime;
                private String yearMonthDay;
                private String startTime;//开始时间
                private String startFeverTime;//开始时间
                private boolean isFever;
                private long totalTime;
                private long totalFeverTime;
                // 是否展开
                private boolean isExtend;

                public String getRecordDay() {
                    return recordDay;
                }

                public String getRecordTemperatureValue() {
                    return recordTemperatureValue;
                }

                public String getRecordSustain() {
                    String language = SharedPreferencesUtil.getString(AppConstant.Language_set, "zh");
                    if (!TextUtils.equals(language, "zh")) {
                        recordSustain = recordSustain.replace("时", "h");
                        recordSustain = recordSustain.replace("分", "m");
                        recordSustain = recordSustain.replace("秒", "s");
                        return recordSustain;
                    }
                    return recordSustain;
                }

                public String getRecordTime() {
                    String language = SharedPreferencesUtil.getString(AppConstant.Language_set, "zh");
                    if (!TextUtils.equals(language, "zh")) {
                        recordTime = recordTime.replace("时", "h");
                        recordTime = recordTime.replace("分", "m");
                        recordTime = recordTime.replace("秒", "s");
                        return recordTime;
                    }
                    return recordTime;
                }

                public boolean isExtend() {
                    return isExtend;
                }

                public RecordDetailsBean setExtend(boolean extend) {
                    isExtend = extend;
                    return this;
                }

                public String getYearMonthDay() {
                    return yearMonthDay;
                }

                @Override
                public String toString() {
                    return "RecordDetailsBean{" +
                            "recordDay='" + recordDay + '\'' +
                            ", recordTemperatureValue='" + recordTemperatureValue + '\'' +
                            ", recordSustain='" + recordSustain + '\'' +
                            ", recordTime='" + recordTime + '\'' +
                            ", yearMonthDay='" + yearMonthDay + '\'' +
                            ", isExtend=" + isExtend +
                            '}';
                }
            }
        }
    }


    /**
     * 获取整体记录列表
     *
     * @return
     */
    public List<RecordBean.MonthBean.RecordDetailsBean> getRecordList() {
        List<RecordBean.MonthBean.RecordDetailsBean> recordDetailsBeanList = new ArrayList<>();

        for (RecordBean recordBean : getRecord()) {
            for (RecordBean.MonthBean monthBean : recordBean.getMonth()) {
                for (RecordBean.MonthBean.RecordDetailsBean recordDetailsBean : monthBean.getRecordDetails()) {
                    recordDetailsBeanList.add(recordDetailsBean);
                }
            }
        }


        return recordDetailsBeanList;
    }

    public RecordBean containsYearData(int year) {
        RecordBean recordBean;
        if (record == null) {
            record = new ArrayList<>();
            recordBean = new RecordBean();
            recordBean.year = year + "";
            record.add(recordBean);
            return recordBean;
        } else {
            for (RecordBean bean : record) {
                if (bean.year.equals(String.valueOf(year))) {
                    //已经存在了该年纪录
                    return bean;
                }
            }
        }
        //都没有
        recordBean = new RecordBean();
        recordBean.year = year + "";
        record.add(recordBean);
        return recordBean;
    }
}
