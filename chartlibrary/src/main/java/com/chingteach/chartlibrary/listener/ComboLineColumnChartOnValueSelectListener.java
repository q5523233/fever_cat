package com.chingteach.chartlibrary.listener;


import com.chingteach.chartlibrary.model.PointValue;
import com.chingteach.chartlibrary.model.SubcolumnValue;

public interface ComboLineColumnChartOnValueSelectListener extends OnValueDeselectListener {

    public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value);

    public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value);

}
