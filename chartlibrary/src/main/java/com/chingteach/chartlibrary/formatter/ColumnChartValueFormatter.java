package com.chingteach.chartlibrary.formatter;


import com.chingteach.chartlibrary.model.SubcolumnValue;

public interface ColumnChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SubcolumnValue value);

}
