package com.nepo.fevercat.common.utils;

import com.chingteach.chartlibrary.gesture.ContainerScrollType;
import com.chingteach.chartlibrary.model.Line;
import com.chingteach.chartlibrary.model.LineChartData;
import com.chingteach.chartlibrary.model.PointValue;
import com.chingteach.chartlibrary.model.Viewport;
import com.chingteach.chartlibrary.view.LineChartView;
import com.nepo.fevercat.app.BaseApplication;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sm on 2019/3/15.
 */

public class LineChartHelper {
    private LineChartData lineChartData;
    private LineChartView lineChartView;
    private List<Line> linesList;
    private int numMaxshow = 5;//每行显示的最多数据
    private float maxX;
    private float top;
    private float bottom;
    private List<Line> linesListHolder;//默认画线
    private LineChartData lineChartDataHolder;//默认画线
    public LineChartHelper(LineChartView chartView) {
        this.lineChartView = chartView;
    }

    public void init(int lineNum, float top, float bottom, int... lineColors) {
        this.top = top;
        this.bottom = bottom;
        linesList = new ArrayList<>();
        for (int i = 0; i < lineNum; i++) {
            Line e = new Line();
            initLineConfig(e, lineColors[i]);
            linesList.add(e);
        }
        numMaxshow = BaseApplication.getAppResources().getDisplayMetrics().widthPixels / 100;
        lineChartData = new LineChartData(linesList);
        lineChartView.setLineChartData(lineChartData);
        lineChartView.setInteractive(false);
        lineChartView.setScrollEnabled(false);
        lineChartView.setValueTouchEnabled(false);
        lineChartView.setFocusableInTouchMode(false);
        lineChartView.setViewportCalculationEnabled(false);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        initHolder((top+bottom)/2,lineColors);
    }

    private void initHolder(float v, int[] lineColors) {
        linesListHolder=new ArrayList<>();
        Line e = new Line();
        initLineConfig(e,lineColors[0]);
        List<PointValue> values=new ArrayList<>();
        values.add(new PointValue(0,v));
        values.add(new PointValue(numMaxshow,v));
        e.setValues(values);
        linesListHolder.add(e);
        Viewport port=initViewPort(0,numMaxshow,top,bottom);
        lineChartView.setMaximumViewport(port);
        lineChartView.setCurrentViewport(port);
        lineChartDataHolder=new LineChartData();
        lineChartDataHolder.setLines(linesListHolder);
        lineChartView.setLineChartData(lineChartDataHolder);
    }

    public void addPoint(int lineIndex, float value) {
        Line line = linesList.get(lineIndex);
        List<PointValue> values = line.getValues();
        if (values.size() == 0) {
            values.add(new PointValue(0, value));
        } else {
            if (values.size() > numMaxshow) {
                values.remove(0);
            }
            float lastX = values.get(values.size() - 1).getX();
            values.add(new PointValue(Math.max(maxX, lastX + 1), value));
            maxX = Math.max(maxX, lastX + 1);
        }
        LogUtils.logd(lineIndex + "----" + line.getValues().size() + "hhhhh");
        LogUtils.logd(lineIndex + "----" + line.getValues().size() + "values");
    }

    public void refresh() {
        lineChartData = new LineChartData(linesList);
        lineChartData.setValueLabelBackgroundEnabled(false);
        //        initAxis();
        Viewport port;
        if (maxX > numMaxshow) {
            port = initViewPort(maxX - numMaxshow, maxX, top, bottom);
        } else {
            port = initViewPort(0, numMaxshow, top, bottom);
        }
        lineChartView.setMaximumViewport(port);
        lineChartView.setCurrentViewport(port);
        lineChartView.setLineChartData(lineChartData);
    }
    //    public void addPoint(float tmp, float tmp2) {
    //
    //        hideDelta();
    //        if (tmp2 == 0) {
    //            hideRight();
    //        } else {
    //            if (pointValueList2.size() == 0) {
    //                pointValueList2.add(new PointValue(0, tmp2));
    //            }
    //            PointValue value2 = new PointValue(pointValueList2.get(pointValueList2.size() - 1).getX() + 1, tmp2);
    //            if (pointValueList2.size() > numMaxshow) {
    //                pointValueList2.remove(0);
    //            }
    //            pointValueList2.add(value2);
    //        }
    //        if (pointValueList.size() == 0) {
    //            pointValueList.add(new PointValue(0, tmp));
    //        }
    //        if (pointValueList.size() > numMaxshow) {
    //            pointValueList.remove(0);
    //        }
    //        PointValue value = new PointValue(pointValueList.get(pointValueList.size() - 1).getX() + 1, tmp);
    //        pointValueList.add(value);
    //        float x = value.getX();
    //        lineChartData = initDatas(linesList);
    //        lineChartData.setValueLabelBackgroundEnabled(false);
    ////        initAxis();
    //        Viewport port;
    //        float top, bottom;
    //        top = 50.2f;
    //        bottom = 24.8f;
    //        if (x > numMaxshow) {
    //            port = initViewPort(x - numMaxshow, x, top, bottom);
    //        } else {
    //            port = initViewPort(0, numMaxshow, top, bottom);
    //        }
    //        lineChartView.setMaximumViewport(port);
    //        lineChartView.setCurrentViewport(port);
    //        lineChartView.setLineChartData(lineChartData);
    //    }


    private Viewport initViewPort(float left, float right, float top, float bottom) {
        Viewport port = new Viewport();
        port.top = top;
        port.bottom = bottom;
        port.left = left;
        port.right = right;
        return port;
    }


    private void initLineConfig(Line line, int lineColor) {
        float density = BaseApplication.getAppResources().getDisplayMetrics().density;
        line.setColor(lineColor);
        //        lineLeft.setStrokeWidth(ChartUtils.dp2px(density, 1));
        line.setStrokeWidth(1);
        //        lineLeft.setLastPointRadius(ChartUtils.dp2px(density, 10));
        line.setHasLabels(false);
//        line.setHasPoints(true).setPointRadius(0);
        line.setHasPoints(true).setPointRadius(1);
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
    }

    //    private void hideRight() {
    //        if (linesList != null)
    //            linesList.remove(lineRight);
    //    }
    //
    //    private void hideDelta() {
    //        if (linesList != null)
    //            linesList.remove(lineDelta);
    //    }

    //    public void reset() {
    //        linesList.clear();
    //        linesList.add(lineLeft);
    //        linesList.add(lineRight);
    //        linesList.add(lineDelta);
    //    }
}
