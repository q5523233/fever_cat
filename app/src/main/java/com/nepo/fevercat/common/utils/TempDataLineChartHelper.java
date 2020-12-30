package com.nepo.fevercat.common.utils;

import android.graphics.Color;

import com.chingteach.chartlibrary.gesture.ZoomType;
import com.chingteach.chartlibrary.model.Axis;
import com.chingteach.chartlibrary.model.AxisValue;
import com.chingteach.chartlibrary.model.Line;
import com.chingteach.chartlibrary.model.LineChartData;
import com.chingteach.chartlibrary.model.PointValue;
import com.chingteach.chartlibrary.model.ValueShape;
import com.chingteach.chartlibrary.model.Viewport;
import com.chingteach.chartlibrary.view.LineChartView;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.BaseApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by sm on 2019/3/19.
 * 用于体温数据页面绘制历史温度曲线
 */

public class TempDataLineChartHelper {
    private LineChartData lineChartData;
    private LineChartView lineChartView;
    private List<Line> linesList;
    private Viewport viewport;
    private Axis axisX;
    private Axis axisY;
    private int lineColor;//第一路温度颜色
    private int line2Color;//第二路温度颜色
    private int textColor;

    public TempDataLineChartHelper(LineChartView lineChartView) {
        this.lineChartView = lineChartView;
        //        lineChartView.setMaximumViewport(initViewPort(42.1f, 35, 0, 10));
        initView();
    }

    private void initView() {
        lineChartView.setZoomType(ZoomType.HORIZONTAL);
        lineChartView.setMaxZoom(1000);
        lineColor = BaseApplication.getAppResources().getColor(R.color.color_02);
        line2Color = BaseApplication.getAppResources().getColor(R.color.color_07);
        textColor = BaseApplication.getAppResources().getColor(R.color.tv_right_gray);
        linesList = new ArrayList<>();
        lineChartData = new LineChartData(linesList);
        initAxis();
    }

    private void initAxis() {
        axisY = new Axis();
        axisX = new Axis();
        //设置隐藏X轴 默认: true
        axisX.setHasSeparationLine(false);
        //设置隐藏Y轴 默认: true
        axisY.setHasSeparationLine(false);
        //设置根据坐标轴上的标注画相应的参考线的方法[原hellocharts就有的]
        //默认：false
        axisX.setHasLines(true);
        axisY.setHasLines(true);
        //还可以设置参考线的颜色
        axisX.setLineColor(Color.parseColor("#dee5eb"));
        axisY.setLineColor(Color.parseColor("#dee5eb"));
        axisY.setTextColor(textColor);
        axisX.setTextColor(textColor);
    }

    //设置某个时间段的曲线图 type 0-5
    public void setDayChart(int type) {
        lineChartView.setMaximumViewport(initViewPort(42.3f, 34.7f, 0, 3600 * 24));
        if (type < 5) {
            type = type * 6;
            viewport = initViewPort(42.3f, 34.7f, 3600 * type, 3600 * (type + 6) + 500);
        } else {
            viewport = initViewPort(42.3f, 34.7f, 0, 3600 * 24 + 500);
        }
        //设置坐标轴的标注
        List<AxisValue> axisXValues = new ArrayList<>();
        if (type != 5) {
            //非全天段 比如00：00-06：00坐标由0-24*3600;
            for (int i = type; i < type + 5; i++) {
                axisXValues.add(new AxisValue(i * 3600 + (i - type) * 1800).setLabel(date2Str(i * 3600 + (i - type) * 1800f)));
            }
        } else {
            //全天段 坐标由0-24*3600
            for (int i = 0; i < 5; i++) {
                axisXValues.add(new AxisValue(i * 3600 * 6).setLabel(date2Str(i * 3600 * 6)));
            }
        }
        axisX.setValues(axisXValues);
        List<AxisValue> axisYValues = new ArrayList<>();
        for (int i = 35; i < 43; i++) {
            axisYValues.add(new AxisValue(i).setLabel(i + ""));
        }
        axisY.setValues(axisYValues);
        //将设置好的坐标轴添加上
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);

    }

    /**
     * 画37.5摄氏度基线
     */
    private void drawBaseLine() {
        linesList.clear();
        Line baseLine = new Line();
        List<PointValue> baseValues = new ArrayList<>();
        baseValues.add(new PointValue(0, 37.5f).setLabel(""));
        baseValues.add(new PointValue(axisX.getValues().get(axisX.getValues().size() - 1).getValue(), 37.5f).setLabel("37.5℃"));
        baseLine.setValues(baseValues);
        baseLine.setHasPoints(true).setPointRadius(0);
        baseLine.setHasLabels(true);
        baseLine.setStrokeWidth(1);
        baseLine.setColor(BaseApplication.getAppResources().getColor(R.color.color_13));
        linesList.add(baseLine);
    }

    private void drawLines(List<List<PointValue>> points, List<List<PointValue>> points2, Viewport max, Viewport cur) {
        addLine(points, lineColor);
        addLine(points2, line2Color);
        lineChartData.setValueLabelBackgroundEnabled(false);
        //设置标注中字体的颜色
        lineChartData.setValueLabelsTextColor(Color.parseColor("#93b9df"));
        //给折线图添加线的集合
        lineChartView.setLineChartData(lineChartData);
        lineChartView.setMaximumViewport(max);
        lineChartView.setCurrentViewport(cur);
        //折线图横纵轴坐标是否按照所给折线数据进行收缩，默认：true
        lineChartView.setViewportCalculationEnabled(false);
    }

    private void addLine(List<List<PointValue>> points, int lineColor) {
        if (points == null || points.size() == 0) {
            return;
        }
        for (int i = 0; i < points.size(); i++) {
            Line line = new Line(points.get(i));
            //        line.setPointColor(Color.parseColor("#123456"));
            //        设置线的颜色,设置线的粗细
            line.setColor(lineColor).setPointRadius(0);
            //设置线上点的形状,设置点的大小
            line.setShape(ValueShape.CIRCLE).setPointRadius(0);
            line.setHasPoints(false);
            //设置是否在点上显示标注
            line.setHasLabels(true);
            line.setStrokeWidth(1);
            //设置曲线是否平滑，即是曲线还是折线
            line.setCubic(true);
            linesList.add(line);
        }
    }

    public void showDayLineChart(List<List<PointValue>> values, List<List<PointValue>> values2) {
        //是否可滚动
        lineChartView.setScrollEnabled(true);
        //是否这线可触摸，显示值
        lineChartView.setValueTouchEnabled(false);
        lineChartView.setFocusableInTouchMode(false);
        lineChartView.setViewportCalculationEnabled(false);
        drawBaseLine();
        drawLines(values, values2, viewport, viewport);
    }

    private Viewport initViewPort(float top, float bottom, float left, float right) {
        Viewport port = new Viewport();
        port.top = top;
        port.bottom = bottom;
        port.left = left;
        port.right = right;
        return port;
    }


    /**
     * @param leftData  全天第一路温度的数据
     * @param rightData 全天第二路温度的数据
     * @param baseValue 起始时间
     */
    public void setHoleDayData(List<PointValue> leftData, List<PointValue> rightData, String baseValue) {
        //        lineChartView.setInteractive(true);
        //设置坐标轴的标注
//        leftData = getSampleData(leftData);
//        rightData = getSampleData(rightData);
        List<AxisValue> axisXValues = new ArrayList<>();
        for (int i = 0; i < leftData.size(); i++) {
            //防止太挤
            //            if (leftData.size() > 100 && i % 5 != 0) {
            //                continue;
            //            } else if (leftData.size() > 500 && i % (leftData.size()/100) != 0) {
            //                continue;
            //            }
            axisXValues.add(new AxisValue(i).setLabel(String.valueOf(leftData.get(i).getLabel())));
        }
        axisX.setValues(axisXValues);

        List<AxisValue> axisYValues = new ArrayList<>();
        for (int i = 35; i < 43; i++) {
            axisYValues.add(new AxisValue(i).setLabel(i + ""));
        }
        axisY.setValues(axisYValues);
        //将设置好的坐标轴添加上
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);
        linesList.clear();
        List<List<PointValue>> points = new ArrayList<>();
        List<List<PointValue>> points2 = new ArrayList<>();
        points.add(leftData);
        points2.add(rightData);
        drawBaseLine();
        drawLines(points, points2, initViewPort(42.2f, 34.8f, 0, leftData.size()), initViewPort(42.2f, 34.8f, 0, 7));

    }

    private List<PointValue> getSampleData(List<PointValue> leftData) {
        if (leftData.size() <= 20) {
            return leftData;
        }
        ArrayList<PointValue> list = new ArrayList<>();
        int sample = leftData.size() / 20;
        if (leftData.size()>20&&leftData.size()<40)
            sample=2;
        for (int i = 0; i < leftData.size(); i++) {
            if (i % sample == 0) {
                list.add(leftData.get(i));
            }
        }
        return list;
    }

    private String getLable(String baseValue, float x) {
        long time = (long) (date2long(baseValue) + 5 * x);
        return date2Str(time, true);
    }

    public String date2Str(float duration, boolean hasSec) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, (int) (duration / 3600));
        calendar.add(Calendar.MINUTE, (int) (duration % 3600 / 60));
        calendar.add(Calendar.SECOND, (int) (duration % 60));
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        String result;
        if (h < 10) {
            if (m < 10) {
                result = "0" + h + ":" + "0" + m;
            } else {
                result = "0" + h + ":" + m;
            }
        } else {
            if (m < 10) {
                result = h + ":" + "0" + m;
            } else {
                result = h + ":" + m;
            }
        }
        if (hasSec) {
            if (sec >= 10) {
                result += ":" + sec;
            } else {
                result += ":0" + sec;
            }
        }
        return result;
    }

    public String date2Str(float duration) {
        return date2Str(duration, false);
    }

    public long date2long(String dateStr) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            return calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
