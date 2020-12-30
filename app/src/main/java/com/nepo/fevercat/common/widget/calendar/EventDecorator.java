package com.nepo.fevercat.common.widget.calendar;

import com.nepo.fevercat.ui.data.bean.CalendarDecoratorBean;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget.calendar
 * 文件名:  EventDecorator
 * 作者 :   <sen>
 * 时间 :  下午3:12 2017/8/11.
 * 描述 :  每日指示点
 */

public class EventDecorator implements DayViewDecorator {


    private int color;
    private HashSet<CalendarDay> dates;
    List<CalendarDay> mCalendarDays;

    public EventDecorator(int color, List<CalendarDecoratorBean> calendarDays) {
        this.color = color;
        mCalendarDays = new ArrayList<>();
        for (CalendarDecoratorBean calendarDay : calendarDays) {
            mCalendarDays.add(calendarDay.getCalendarDay());
        }
        this.dates = new HashSet<>(mCalendarDays);

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new DotSpan(6, color));
    }
}
