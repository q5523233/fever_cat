package com.nepo.fevercat.ui.data.bean;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.Serializable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.bean
 * 文件名:  CalendarDecoratorBean
 * 作者 :   <sen>
 * 时间 :  下午3:21 2017/8/11.
 * 描述 :  日历指示点
 */

public class CalendarDecoratorBean implements Serializable {


    private CalendarDay mCalendarDay;
    private boolean isHeightTemp; // 超过37.5℃


    public CalendarDay getCalendarDay() {
        return mCalendarDay;
    }

    public CalendarDecoratorBean setCalendarDay(CalendarDay calendarDay) {
        mCalendarDay = calendarDay;
        return this;
    }

    public boolean isHeightTemp() {
        return isHeightTemp;
    }

    public CalendarDecoratorBean setHeightTemp(boolean heightTemp) {
        isHeightTemp = heightTemp;
        return this;
    }
}
