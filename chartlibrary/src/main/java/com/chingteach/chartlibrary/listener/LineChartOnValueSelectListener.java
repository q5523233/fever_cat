package com.chingteach.chartlibrary.listener;


import com.chingteach.chartlibrary.model.PointValue;

public interface LineChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int lineIndex, int pointIndex, PointValue value);

}
