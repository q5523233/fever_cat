package com.chingteach.chartlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.chingteach.chartlibrary.BuildConfig;
import com.chingteach.chartlibrary.listener.DummyLineChartOnValueSelectListener;
import com.chingteach.chartlibrary.listener.LineChartOnValueSelectListener;
import com.chingteach.chartlibrary.model.ChartData;
import com.chingteach.chartlibrary.model.LineChartData;
import com.chingteach.chartlibrary.model.PointValue;
import com.chingteach.chartlibrary.model.SelectedValue;
import com.chingteach.chartlibrary.provider.LineChartDataProvider;
import com.chingteach.chartlibrary.renderer.LineChartRenderer;

//import lecho.lib.hellocharts.BuildConfig;
//import lecho.lib.hellocharts.listener.DummyLineChartOnValueSelectListener;
//import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
//import lecho.lib.hellocharts.model.ChartData;
//import lecho.lib.hellocharts.model.LineChartData;
//import lecho.lib.hellocharts.model.PointValue;
//import lecho.lib.hellocharts.model.SelectedValue;
//import lecho.lib.hellocharts.provider.LineChartDataProvider;
//import lecho.lib.hellocharts.renderer.LineChartRenderer;

/**
 * LineChart, supports cubic lines, filled lines, circle and square points. Point radius and stroke width can be
 * adjusted using LineChartData attributes.
 *
 * @author Leszek Wach
 */
public class LineChartView extends AbstractChartView implements LineChartDataProvider {
    private static final String TAG = "LineChartView";
    protected LineChartData data;
    protected LineChartOnValueSelectListener onValueTouchListener = new DummyLineChartOnValueSelectListener();

    public LineChartView(Context context) {
        this(context, null, 0);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setChartRenderer(new LineChartRenderer(context, this, this));
        setLineChartData(LineChartData.generateDummyData());
    }

    @Override
    public LineChartData getLineChartData() {
        return data;
    }

    @Override
    public void setLineChartData(LineChartData data) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Setting data for LineChartView");
        }

        if (null == data) {
            this.data = LineChartData.generateDummyData();
        } else {
            this.data = data;
        }

        super.onChartDataChange();
    }

    @Override
    public ChartData getChartData() {
        return data;
    }

    @Override
    public void callTouchListener() {
        SelectedValue selectedValue = chartRenderer.getSelectedValue();

        if (selectedValue.isSet()) {
            PointValue point = data.getLines().get(selectedValue.getFirstIndex()).getValues()
                    .get(selectedValue.getSecondIndex());
            onValueTouchListener.onValueSelected(selectedValue.getFirstIndex(), selectedValue.getSecondIndex(), point);
        } else {
            onValueTouchListener.onValueDeselected();
        }
    }

    public LineChartOnValueSelectListener getOnValueTouchListener() {
        return onValueTouchListener;
    }

    public void setOnValueTouchListener(LineChartOnValueSelectListener touchListener) {
        if (null != touchListener) {
            this.onValueTouchListener = touchListener;
        }
    }
}
