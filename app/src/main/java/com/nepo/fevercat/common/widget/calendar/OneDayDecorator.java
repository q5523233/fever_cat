package com.nepo.fevercat.common.widget.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.nepo.fevercat.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;

/**
 * Decorate a day by making the text big and bold
 */
public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;
    private Drawable backgroundDrawable;
    private Context mContext;

    public OneDayDecorator(Context mActivity) {
        date = CalendarDay.today();
        mContext = mActivity;
        //backgroundDrawable = ContextCompat.getDrawable(mActivity, R.drawable.calendar_oneday_circle_selector);
        backgroundDrawable = ContextCompat.getDrawable(mActivity, R.drawable.calendar_today_circle_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.NORMAL));
        view.addSpan(new RelativeSizeSpan(1.4f));
//        view.setBackgroundDrawable(backgroundDrawable);
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
