package com.chingteach.chartlibrary.listener;


import com.chingteach.chartlibrary.model.SliceValue;

public interface PieChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int arcIndex, SliceValue value);

}
