package com.chingteach.chartlibrary.listener;


import com.chingteach.chartlibrary.model.BubbleValue;

public interface BubbleChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int bubbleIndex, BubbleValue value);

}
