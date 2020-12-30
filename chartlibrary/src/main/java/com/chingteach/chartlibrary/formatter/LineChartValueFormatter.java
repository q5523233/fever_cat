package com.chingteach.chartlibrary.formatter;


import com.chingteach.chartlibrary.model.PointValue;

public interface LineChartValueFormatter {

    public int formatChartValue(char[] formattedValue, PointValue value);
}
