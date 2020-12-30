package com.chingteach.chartlibrary.formatter;


import com.chingteach.chartlibrary.model.SliceValue;

public interface PieChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SliceValue value);
}
