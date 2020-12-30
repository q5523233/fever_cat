package com.nepo.fevercat.ui.data.bean;

import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.bean
 * 文件名:  TempOneDayDataResBean
 * 作者 :   <sen>
 * 时间 :  下午3:34 2017/7/6.
 * 描述 :
 */

public class TempOneDayDataResBean extends BaseResBean {


    private String maxTemperature;
    private String maxTempTime;

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMaxTempTime(String maxTempTime) {
        this.maxTempTime = maxTempTime;
    }

    private List<com.nepo.fevercat.ui.real.bean.TemperaturesBean> temperatures;

    public List<TemperaturesBean> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<TemperaturesBean> temperatures) {
        this.temperatures = temperatures;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public String getMaxTempTime() {
        return maxTempTime;
    }

//    /**
//     * 体温
//     */
//    public static class TemperaturesBean {
//
//        private int id;
//        private String tempTime;
//        private String temperature;
//        private String temperature2;
//
//        public String getTemperature2() {
//            if (temperature2==null)
//            {
//                return "0";
//            }
//            return temperature2;
//        }
//
//        public void setTemperature2(String temperature2) {
//            this.temperature2 = temperature2;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getTempTime() {
//
//            return tempTime;
//        }
//
//        public void setTempTime(String tempTime) {
//            this.tempTime = tempTime;
//        }
//
//        public String getTemperature() {
//            return temperature;
//        }
//
//        public void setTemperature(String temperature) {
//            this.temperature = temperature;
//        }
//
//        @Override
//        public String toString() {
//            return "TemperaturesBean{" +
//                    "id=" + id +
//                    ", tempTime='" + tempTime + '\'' +
//                    ", temperature='" + temperature + '\'' +
//                    '}';
//        }
//    }
}
