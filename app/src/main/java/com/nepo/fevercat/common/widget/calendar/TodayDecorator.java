package com.nepo.fevercat.common.widget.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.nepo.fevercat.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget.calendar
 * 文件名:  TodayDecorator
 * 作者 :   <sen>
 * 时间 :  下午6:06 2017/7/3.
 * 描述 :  当前日期选中样式
 */

public class TodayDecorator implements DayViewDecorator {


    private final CalendarDay today;
    private final Drawable backgroundDrawable;

    public TodayDecorator(Context mActivity) {
        today = CalendarDay.today();
        backgroundDrawable = ContextCompat.getDrawable(mActivity, R.drawable.calendar_today_circle_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return today.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(backgroundDrawable);
    }

}
