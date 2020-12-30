package com.chingteach.chartlibrary.formatter;


import com.chingteach.chartlibrary.model.BubbleValue;

public interface BubbleChartValueFormatter {

    public int formatChartValue(char[] formattedValue, BubbleValue value);
}
