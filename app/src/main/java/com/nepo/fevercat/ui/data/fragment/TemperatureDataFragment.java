package com.nepo.fevercat.ui.data.fragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chingteach.chartlibrary.model.PointValue;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseFragment;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.common.utils.NetWorkUtils;
import com.nepo.fevercat.common.utils.TempDataLineChartHelper;
import com.nepo.fevercat.common.widget.calendar.EventDecorator;
import com.nepo.fevercat.common.widget.calendar.OneDayDecorator;
import com.nepo.fevercat.common.widget.calendar.TodayDecorator;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.common.widget.popup.ListBBPopupWindow;
import com.nepo.fevercat.event.BBInfoEvent;
import com.nepo.fevercat.event.LoginOutMsgEvent;
import com.nepo.fevercat.event.NetStatusEvent;
import com.nepo.fevercat.ui.data.ShareDataActivity;
import com.nepo.fevercat.ui.data.adapter.TempHistoryAdapter;
import com.nepo.fevercat.ui.data.bean.CalendarDecoratorBean;
import com.nepo.fevercat.ui.data.bean.ShareDataBean;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataReqBean;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;
import com.nepo.fevercat.ui.data.bean.TempOneDayDataResBean;
import com.nepo.fevercat.ui.data.contract.TempHistoryListContract;
import com.nepo.fevercat.ui.data.model.TempHistoryListModel;
import com.nepo.fevercat.ui.data.presenter.TempHistoryListPresenter;
import com.nepo.fevercat.ui.main.LoginActivity;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.real.fragment
 * 文件名:  TemperatureDataFragment
 * 作者 :   <sen>
 * 时间 :  上午11:27 2017/4/24.
 * 描述 :  体温数据
 */

public class TemperatureDataFragment extends BaseFragment<TempHistoryListPresenter, TempHistoryListModel> implements TempHistoryListContract.View, OnDateSelectedListener, SwipeRefreshLayout.OnRefreshListener, ListBBPopupWindow.OnSelectItemListen {


    @BindView(R.id.iv_temp_user_img)
    CircularImage ivTempUserImg;
    @BindView(R.id.tv_temp_user_name)
    TextView tvTempUserName;
    @BindView(R.id.rl_temp_share)
    RelativeLayout rlTempShare;
    @BindView(R.id.tv_temp_top_height_unit)
    TextView tvTempTopHeightUnit;
    @BindView(R.id.tv_temp_top_day)
    TextView tvTempTopDay;
    @BindView(R.id.tv_temp_top_day_unit)
    TextView tvTempTopDayUnit;
    @BindView(R.id.calendar_temp)
    MaterialCalendarView calendarTemp;
    @BindView(R.id.rl_temp_no_data)
    RelativeLayout rlTempNoData;
    @BindView(R.id.iv_temp_history_arrow)
    ImageView ivTempHistoryArrow;
    @BindView(R.id.recycle_temp_history_list)
    RecyclerView recycleTempHistoryList;
    @BindView(R.id.swipeLayout_temp_history)
    SwipeRefreshLayout swipeLayoutTempHistory;
    @BindView(R.id.rl_temp_history)
    RelativeLayout rlTempHistory;
    @BindView(R.id.ll_temp_user)
    LinearLayout llTempUser;
    @BindView(R.id.ll_temp_contain)
    LinearLayout llTempContain;
    @BindView(R.id.ll_temp_calendar)
    LinearLayout llTempCalendar;
    @BindView(R.id.ll_temp_chart)
    LinearLayout llTempChart;
    @BindView(R.id.rl_temp_user_nav)
    RelativeLayout rlTempUserNav;
    @BindView(R.id.rl_temp_data_chart)
    RelativeLayout rlTempDataChart;
    @BindView(R.id.rl_temp_hour_interval)
    RelativeLayout rlTempHourInterval;
    @BindView(R.id.tv_hour_interval_first)
    TextView tvHourIntervalFirst;
    @BindView(R.id.tv_hour_interval_second)
    TextView tvHourIntervalSecond;
    @BindView(R.id.tv_hour_interval_third)
    TextView tvHourIntervalThird;
    @BindView(R.id.tv_hour_interval_four)
    TextView tvHourIntervalFour;
    @BindView(R.id.tv_hour_interval_five)
    TextView tvHourIntervalFive;
    @BindView(R.id.tv_temp_height)
    TextView tvTempHeight;
    @BindView(R.id.rl_hello_line_chart)
    RelativeLayout rlHelloLineChart;
    @BindView(R.id.rl_hello_line_select_chart)
    RelativeLayout rlHelloLineSelectChart;
    @BindView(R.id.tv_calendar_bar_date)
    TextView tvCalendarBarDate;
    @BindView(R.id.iv_calendar_bar_right)
    ImageView ivCalendarBarRight;
    @BindView(R.id.iv_calendar_bar_left)
    ImageView ivCalendarBarLeft;
    @BindView(R.id.lc_temp_data)
    com.chingteach.chartlibrary.view.LineChartView lcTempData;
    @BindView(R.id.lc_view_hello_line_chart)
    com.chingteach.chartlibrary.view.LineChartView lcViewHelloLineChart;


    // 日历选中样式
    private OneDayDecorator oneDayDecorator;
    private EventDecorator eventDecorator;
    private TempHistoryAdapter mTempHistoryAdapter;
    private ListBBPopupWindow mListBBPopupWindow;
    BabyInfosBean mBabyInfosBean;

    // 选中的日期
    private Date mSelectDate;

    // 分享数据
    private ShareDataBean mShareDataBean = new ShareDataBean();

    int h_screen;


    private int selectType = 0;

    // 当前年月
    private int mYear;
    private int mMonth;
    private int selectedTimeIndex = 5;//选中的时间段

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_temp_data;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        recycleTempHistoryList.setHasFixedSize(true);
        recycleTempHistoryList.setLayoutManager(new LinearLayoutManager(mActivity));
        swipeLayoutTempHistory.setOnRefreshListener(this);
        swipeLayoutTempHistory.setColorSchemeColors(Color.rgb(47, 223, 189));
        // 屏幕高度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        h_screen = dm.heightPixels;
        initCalendarDate();
        initCalendar();
        initData();
        initTimeList();
        initHourIntervalView();
    }

    /**
     * 绘制全天详细曲线
     *
     * @param tempOneDayDataResBean
     */
    private void drawOneDayLineChart(TempOneDayDataResBean tempOneDayDataResBean) {
        TempDataLineChartHelper helper = new TempDataLineChartHelper(lcViewHelloLineChart);
        List<TemperaturesBean> temperaturesBeanList = tempOneDayDataResBean.getTemperatures();
        ArrayList<PointValue> leftDatas = new ArrayList<>();
        ArrayList<PointValue> rightDatas = new ArrayList<>();
        for (int i = 0; i < temperaturesBeanList.size(); i++) {
            TemperaturesBean bean = temperaturesBeanList.get(i);
            String[] split = bean.getTempTime().split(" ");
            String lable = bean.getTempTime();
            if (split.length > 1) {
                lable = split[1];
            }
            leftDatas.add(new PointValue(i, Math.min(42, Math.max(Float.valueOf(bean.getTemperature()), 35))).setLabel(lable));
            if (Float.valueOf(bean.getTemperatureRight()) == 0) {
                continue;
            }
            rightDatas.add(new PointValue(i, Math.min(42, Math.max(Float.valueOf(bean.getTemperatureRight()), 35))).setLabel(lable));
        }
        helper.setHoleDayData(leftDatas, rightDatas, temperaturesBeanList.get(0).getTempTime());
    }

    /**
     * 绘制某个时间段曲线
     *
     * @param values
     */
    private void testTimeSlotLineChart(List<TemperaturesBean> values) {

    }


    private void initCalendarDate() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // 请求数据
            selectType = 0;
            if (mBabyInfosBean == null)
                initData();
            if (mTempHistoryAdapter != null) {
                mTempHistoryAdapter.notifyDataSetChanged();
            }
            TempHistoryDataResBean tempHistoryDataResBean = mShareDataBean.getTempHistoryDataResBean();
            if (tempHistoryDataResBean != null) {
                if (tempHistoryDataResBean.getRecord() != null && tempHistoryDataResBean.getRecord().size() > 0) {
                    TempHistoryDataResBean.RecordBean recordBean = tempHistoryDataResBean.getRecord().get(0);
                    if (!TextUtils.isEmpty(recordBean.getHightTemperature())) {
                        tvTempTopHeightUnit.setText(ConstantUtils.IsCelsiusUnit(recordBean.getHightTemperature()));
                    }
                    if (!TextUtils.isEmpty(recordBean.getFeverDay())) {
                        tvTempTopDay.setText(recordBean.getFeverDay());
                    }
                } else {
                    tvTempTopHeightUnit.setText("0");
                    tvTempTopDay.setText("0");
                }
            }


        }
    }


    /**
     * 上一月
     */
    @OnClick(R.id.iv_calendar_bar_right)
    public void onPreviousMonthClick() {
        Calendar calendar = Calendar.getInstance();
        mMonth = mMonth - 1;
        LogUtils.logd("-- reduceMonth:" + mMonth);
        calendar.set(mYear, mMonth, 1);
        calendarTemp.setCurrentDate(calendar);
        CalendarDay currentDate2 = calendarTemp.getCurrentDate();
        //LogUtils.logd("-- currentDate2:" + currentDate2.getMonth() + "," + currentDate2.getYear() + ",");
        //        tvCalendarBarDate.setText(currentDate.getYear()+"-"+currentDate.getMonth());

    }

    /**
     * 下一月
     */
    @OnClick(R.id.iv_calendar_bar_left)
    public void onNextMonthClick() {
        Calendar calendar = Calendar.getInstance();
        mMonth = mMonth + 1;
        calendar.set(mYear, mMonth + 1, 1);
        calendarTemp.setCurrentDate(calendar);
        LogUtils.logd("-- AddMonth:" + mMonth);
        //        tvCalendarBarDate.setText(year+"-"+(month+1));
    }


    private void initTimeList() {


    }


    /**
     * 时间间隔选项
     */
    private void initHourIntervalView() {
        tvHourIntervalFirst.setSelected(false);
        tvHourIntervalSecond.setSelected(false);
        tvHourIntervalThird.setSelected(false);
        tvHourIntervalFour.setSelected(false);
        tvHourIntervalFive.setSelected(false);
    }


    /**
     * 初始化日历
     */
    private void initCalendar() {

        oneDayDecorator = new OneDayDecorator(mActivity);
        calendarTemp.setTileHeightDp(30);
        calendarTemp.setOnDateChangedListener(this);
        calendarTemp.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        Calendar calendar = Calendar.getInstance();
        calendarTemp.setSelectedDate(calendar.getTime());
        calendarTemp.state().edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        calendarTemp.addDecorator(new TodayDecorator(mActivity));
        calendarTemp.setTopbarVisible(false);
        mSelectDate = new Date();

        CalendarDay currentDate = calendarTemp.getCurrentDate();
        tvCalendarBarDate.setText(currentDate.getYear() + "-" + (currentDate.getMonth() + 1));

        calendarTemp.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                tvCalendarBarDate.setText(date.getYear() + "-" + (date.getMonth() + 1));
                mYear = date.getYear();
                mMonth = date.getMonth();
            }
        });

    }


    /**
     * 绘制每天的点
     * 是否有温度、是否是高温
     */
    private void addDayDecorator(final TempHistoryDataResBean tempHistoryDataResBean) {

        if (eventDecorator != null) {
            calendarTemp.removeDecorator(eventDecorator);
        }

        // 指示点
        Observable.create(new Observable.OnSubscribe<List<CalendarDecoratorBean>>() {
            @Override
            public void call(Subscriber<? super List<CalendarDecoratorBean>> subscriber) {
                subscriber.onNext(ConstantUtils.getCalendarDecoratorList(tempHistoryDataResBean));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CalendarDecoratorBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<CalendarDecoratorBean> calendarDays) {
                        eventDecorator = new EventDecorator(ContextCompat.getColor(mActivity, R.color.tv_talk_content), calendarDays);
                        calendarTemp.addDecorator(eventDecorator);
                    }
                });

    }


    /**
     * 初始化宝宝信息
     */
    private void initData() {
        LogUtils.logd("-- initData");
        if (NetWorkUtils.isNetConnected(mActivity) || AppConstant.IS_OFFLINEMODE) {
            // 用户信息
            if (AppConstant.isLogin() || AppConstant.IS_OFFLINEMODE) {
                // 获取宝宝列表
                MineBBReqBean mineBBReqBean = new MineBBReqBean();
                mineBBReqBean
                        .setAccountId(AppConstant.getUserInfo().getUserId())
                        .setOperateID(ApiConstants.MODIFY_BABY_LIST_5)
                        .setTRANSID(ApiConstants.MODIFY_BABY);
                mPresenter.putMineBBInfoRequest(mineBBReqBean);

            } else {
                ConstantUtils.loadLoginUserImg("", ivTempUserImg);
                tvTempUserName.setText(getString(R.string.mine_no_login));
                rlTempNoData.setVisibility(View.VISIBLE);
                rlTempDataChart.setVisibility(View.GONE);
                rlTempHourInterval.setVisibility(View.GONE);
            }
        } else {
            // 无网络
            ConstantUtils.loadLoginUserImg("", ivTempUserImg);
            tvTempUserName.setText(getString(R.string.no_net_tip));
            rlTempNoData.setVisibility(View.VISIBLE);
            rlTempDataChart.setVisibility(View.GONE);
            rlTempHourInterval.setVisibility(View.GONE);
        }


    }


    /**
     * 某一天数据
     */
    private void initOneDayData(Date date) {
        if (AppConstant.isLogin() || AppConstant.IS_OFFLINEMODE) {
            if (mBabyInfosBean != null) {
                String strDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
                mShareDataBean.setTime(strDateFormat);
                TempHistoryDataReqBean tempHistoryDataReqBean = new TempHistoryDataReqBean();
                tempHistoryDataReqBean
                        .setAccountId(AppConstant.getUserInfo().getUserId())
                        .setBabyId(mBabyInfosBean.getBabyId())
                        .setLocalId(mBabyInfosBean.getLocalId())
                        .setQueryTime(strDateFormat)
                        .setTRANSID(ApiConstants.HISTORY_TEMP_ONE);
                mPresenter.getTempOneDayInfoRequest(tempHistoryDataReqBean, true);
            }

        }
    }

    /**
     * 某一天时间段 间隔
     *
     * @param type
     */
    private void initOneDayTimeIntervalData(String type) {
        if (AppConstant.isLogin() || AppConstant.IS_OFFLINEMODE) {
            if (mBabyInfosBean != null) {
                String strDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(mSelectDate);
                mShareDataBean.setTime(strDateFormat);
                TempHistoryDataReqBean tempHistoryDataReqBean = new TempHistoryDataReqBean();
                tempHistoryDataReqBean
                        .setAccountId(AppConstant.getUserInfo().getUserId())
                        .setBabyId(mBabyInfosBean.getBabyId())
                        .setLocalId(mBabyInfosBean.getLocalId())
                        .setQueryTime(strDateFormat)
                        .setType(type)
                        .setTRANSID(ApiConstants.HISTORY_TEMP_ONE);
                mPresenter.getTempOneDayInfoRequest(tempHistoryDataReqBean, true);
            }

        }
    }


    /**
     * 历史记录
     */
    private void initHistoryData() {
        //获取 历史记录

        if (mBabyInfosBean != null) {
            TempHistoryDataReqBean tempHistoryDataReqBean = new TempHistoryDataReqBean();
            tempHistoryDataReqBean
                    .setAccountId(AppConstant.getUserInfo().getUserId())
                    .setBabyId(mBabyInfosBean.getBabyId())
                    .setLocalId(mBabyInfosBean.getLocalId())
                    .setTRANSID(ApiConstants.HISTORY_TEMP);
            mPresenter.getHistoryListInfoRequest(tempHistoryDataReqBean, false);
        }
    }


    /**
     *
     */


    /**
     * 历史记录列表
     */
    private void initAdapter(TempHistoryDataResBean tempHistoryDataResBean) {
        mTempHistoryAdapter = new TempHistoryAdapter(tempHistoryDataResBean.getRecord());
        mTempHistoryAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recycleTempHistoryList.setAdapter(mTempHistoryAdapter);
    }


    /**
     * 某一天图表
     */
    private void initOneDaLineChartData(TempOneDayDataResBean tempOneDayDataResBean) throws ParseException {

        LogUtils.logd("-- 时间：" + tempOneDayDataResBean.getMaxTemperature());
        // 最高温
        String time = getString(R.string.time);
        tvTempHeight.setText(ConstantUtils.IsCelsiusUnit(tempOneDayDataResBean.getMaxTemperature())
                + "\t\t" + time + ":" + tempOneDayDataResBean.getMaxTempTime());

        List<TemperaturesBean> temperatures = tempOneDayDataResBean.getTemperatures();
        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        LogUtils.logd("-- initOneDaLineChartData:" + temperatures.size());
        if (temperatures.size() > 0) {
            rlTempNoData.setVisibility(View.GONE);
            rlTempDataChart.setVisibility(View.VISIBLE);
            rlTempHourInterval.setVisibility(View.VISIBLE);
            drawOneDayLineChart(tempOneDayDataResBean);
        } else {
            rlTempNoData.setVisibility(View.VISIBLE);
            rlTempDataChart.setVisibility(View.GONE);
            rlTempHourInterval.setVisibility(View.GONE);
        }

    }


    /**
     * 时间段 图表
     */
    private void initOneDayTimeIntervalChart(TempOneDayDataResBean tempOneDayDataResBean) throws ParseException {
        TempDataLineChartHelper helper = new TempDataLineChartHelper(lcTempData);
        lcTempData.setVisibility(View.VISIBLE);
        //全天曲线可能分为很多段，根据前后两个时间差分割，相差超过1小时就认为是新曲线
        List<List<PointValue>> pointValuesListLeft = new ArrayList<>();
        List<List<PointValue>> pointValuesListRight = new ArrayList<>();
        ArrayList<PointValue> pointValuesLeft = new ArrayList<>();
        ArrayList<PointValue> pointValuesRight = new ArrayList<>();
        long time = 0;
        int size = tempOneDayDataResBean.getTemperatures().size();
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int sample= Math.max(1, (size/widthPixels));

        for (int i = 0; i < size; i+=sample) {
            TemperaturesBean bean = tempOneDayDataResBean.getTemperatures().get(i);
            long curTime = helper.date2long(bean.getTempTime());
            if (time == 0) {
                time = curTime;
                continue;
            }
            //取样，每隔2分钟取一次数据，不然曲线太密集
            //            if (curTime - time > 60 * 2) {
            //            if (i % 5 != 0&&tempOneDayDataResBean.getTemperatures().size()>20) {
            //                continue;
            //            }
            if (curTime - time < 60 * 60) {
                time = curTime;
                pointValuesLeft.add(new PointValue(curTime, Math.min(42, Math.max(Float.valueOf(bean.getTemperature()), 35))));

                if (Float.valueOf(bean.getTemperatureRight()) == 0) {
                    continue;
                }
                pointValuesRight.add(new PointValue(curTime, Math.min(42, Math.max(Float.valueOf(bean.getTemperatureRight()), 35))));
            } else {
                time = 0;
                pointValuesListLeft.add(pointValuesLeft);
                pointValuesListRight.add(pointValuesRight);
                pointValuesLeft = new ArrayList<>();
                pointValuesRight = new ArrayList<>();
            }
            //            }
        }
        pointValuesListLeft.add(pointValuesLeft);
        pointValuesListRight.add(pointValuesRight);
        helper.setDayChart(selectedTimeIndex);
        helper.showDayLineChart(pointValuesListLeft, pointValuesListRight);
    }


    /**
     * 列表隐藏显示切换
     *
     * @param isVisible
     */
    private void changeHistoryList(boolean isVisible) {
        if (isVisible) {
            // 显示
            ivTempHistoryArrow.setBackgroundResource(R.drawable.icon_arrow_bottom);
            swipeLayoutTempHistory.setVisibility(View.VISIBLE);
            llTempContain.setVisibility(View.GONE);
            llTempCalendar.setVisibility(View.GONE);
            llTempChart.setVisibility(View.GONE);
        } else {
            // 隐藏
            ivTempHistoryArrow.setBackgroundResource(R.drawable.icon_arrow_top);
            swipeLayoutTempHistory.setVisibility(View.GONE);
            llTempContain.setVisibility(View.VISIBLE);
            llTempCalendar.setVisibility(View.VISIBLE);
            llTempChart.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 绘制X轴 时间点
     */
    private void initXPoint() {

        selectType = 1;
        rlTempDataChart.setVisibility(View.VISIBLE);
        rlTempHourInterval.setVisibility(View.VISIBLE);
        rlHelloLineChart.setVisibility(View.GONE);
        rlHelloLineSelectChart.setVisibility(View.VISIBLE);
    }


    /**
     * 用户点击
     */
    @OnClick(R.id.ll_temp_user)
    public void onUserClick() {
        if (AppConstant.isLogin() && !AppConstant.IS_OFFLINEMODE) {
            startAct(LoginActivity.newIntent(mActivity));
            return;
        }

        if (mListBBPopupWindow != null) {

            //            mListBBPopupWindow.setWidth(DisplayMetricsUtil.dip2px(mActivity, 180));
            mListBBPopupWindow.setHeight(h_screen / 2);
            mListBBPopupWindow.showAsDropDown(rlTempUserNav);
        }

    }

    /**
     * 分享
     */
    @OnClick(R.id.rl_temp_share)
    public void onShareClick() {
        if (AppConstant.isLogin() && !AppConstant.IS_OFFLINEMODE) {
            startAct(LoginActivity.newIntent(mActivity));
            return;
        }
        //        startAct(ShareDataActivity.newIntent(mActivity));
        //        Viewport currentViewport = viewHelloLineChart.getCurrentViewport();
        //        LogUtils.logd("-- 视图："+currentViewport.toString());

        mShareDataBean.setBabyInfosBean(mBabyInfosBean);
        AppConstant.mShareDataBean = mShareDataBean;
//        startAct(ShareDataActivity.newIntent(mActivity));


    }


    /**
     * 时间间隔
     */
    @OnClick({R.id.tv_hour_interval_first, R.id.tv_hour_interval_second, R.id.tv_hour_interval_third, R.id.tv_hour_interval_four, R.id.tv_hour_interval_five})
    public void onHourIntervalClick(TextView textView) {
        switch (textView.getId()) {
            case R.id.tv_hour_interval_first:
                tvHourIntervalFirst.setSelected(true);
                tvHourIntervalSecond.setSelected(false);
                tvHourIntervalThird.setSelected(false);
                tvHourIntervalFour.setSelected(false);
                tvHourIntervalFive.setSelected(false);
                initXPoint();
                selectedTimeIndex = 0;
                initOneDayTimeIntervalData(ApiConstants.HISTORY_TEMP_ONE_TYPE_ZERO);
                break;
            case R.id.tv_hour_interval_second:
                tvHourIntervalFirst.setSelected(false);
                tvHourIntervalSecond.setSelected(true);
                tvHourIntervalThird.setSelected(false);
                tvHourIntervalFour.setSelected(false);
                tvHourIntervalFive.setSelected(false);
                initXPoint();
                selectedTimeIndex = 1;
                initOneDayTimeIntervalData(ApiConstants.HISTORY_TEMP_ONE_TYPE_FIRST);
                break;
            case R.id.tv_hour_interval_third:
                selectedTimeIndex = 2;
                tvHourIntervalFirst.setSelected(false);
                tvHourIntervalSecond.setSelected(false);
                tvHourIntervalThird.setSelected(true);
                tvHourIntervalFour.setSelected(false);
                tvHourIntervalFive.setSelected(false);
                initXPoint();
                initOneDayTimeIntervalData(ApiConstants.HISTORY_TEMP_ONE_TYPE_SECOND);
                break;
            case R.id.tv_hour_interval_four:
                selectedTimeIndex = 3;
                tvHourIntervalFirst.setSelected(false);
                tvHourIntervalSecond.setSelected(false);
                tvHourIntervalThird.setSelected(false);
                tvHourIntervalFour.setSelected(true);
                tvHourIntervalFive.setSelected(false);
                initXPoint();
                initOneDayTimeIntervalData(ApiConstants.HISTORY_TEMP_ONE_TYPE_THIRD);
                break;
            case R.id.tv_hour_interval_five:
                selectedTimeIndex = 5;
                tvHourIntervalFirst.setSelected(false);
                tvHourIntervalSecond.setSelected(false);
                tvHourIntervalThird.setSelected(false);
                tvHourIntervalFour.setSelected(false);
                tvHourIntervalFive.setSelected(true);
                initXPoint();
                initOneDayTimeIntervalData(ApiConstants.HISTORY_TEMP_ONE_TYPE_FOUR);
                break;
        }


    }


    /**
     * 历史记录
     * 显示/隐藏
     */
    @OnClick(R.id.rl_temp_history)
    public void onHistoryClick() {
        if (swipeLayoutTempHistory.getVisibility() == View.VISIBLE) {
            changeHistoryList(false);
        } else {
            changeHistoryList(true);
        }
    }


    /**
     * 日历选择
     *
     * @param widget
     * @param date
     * @param selected
     */
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());
        rlTempDataChart.setVisibility(View.VISIBLE);
        rlHelloLineChart.setVisibility(View.VISIBLE);
        rlHelloLineSelectChart.setVisibility(View.GONE);
        selectType = 0;
        initHourIntervalView();
        initOneDayData(date.getDate());
        mSelectDate = date.getDate();

    }


    /**
     * 历史记录
     *
     * @param tempHistoryDataResBean
     */
    @Override
    public void returnHistoryListInfo(final TempHistoryDataResBean tempHistoryDataResBean) {
        swipeLayoutTempHistory.setRefreshing(false);
        if (tempHistoryDataResBean.isOk()) {
            // 指示点
            addDayDecorator(tempHistoryDataResBean);
            mShareDataBean.setTempHistoryDataResBean(tempHistoryDataResBean);
            // 一年体温最高值、发热天数
            if (tempHistoryDataResBean.getRecord().size() > 0) {
                TempHistoryDataResBean.RecordBean recordBean = tempHistoryDataResBean.getRecord().get(0);
                if (!TextUtils.isEmpty(recordBean.getHightTemperature())) {
                    tvTempTopHeightUnit.setText(ConstantUtils.IsCelsiusUnit(recordBean.getHightTemperature()));
                }
                if (!TextUtils.isEmpty(recordBean.getFeverDay())) {
                    tvTempTopDay.setText(recordBean.getFeverDay());
                }
            } else {
                tvTempTopHeightUnit.setText("0");
                tvTempTopDay.setText("0");
            }
            // 记录列表
            initAdapter(tempHistoryDataResBean);
        } else {
            //            ToastUtils.showToast(tempHistoryDataResBean.getMsg());
            tvTempTopHeightUnit.setText("0");
            tvTempTopDay.setText("0");
            if (mTempHistoryAdapter != null) {
                mTempHistoryAdapter.getData().clear();
                mTempHistoryAdapter.notifyDataSetChanged();
            }
        }


    }

    /**
     * 宝宝信息
     *
     * @param mineBBResBean
     */
    @Override
    public void returnBBInfo(MineBBResBean mineBBResBean) {
        mListBBPopupWindow = new ListBBPopupWindow(mActivity, mineBBResBean);
        mListBBPopupWindow.setOnSelectItemListen(this);

        // 默认第一个BB
        if (mineBBResBean.getBabyInfos() != null
                && mineBBResBean.getBabyInfos().size() > 0) {
            mBabyInfosBean = mineBBResBean.getBabyInfos().get(0);
        } else {
            ConstantUtils.loadLoginUserImg("http", ivTempUserImg);
            tvTempUserName.setText("");
        }
        if (mBabyInfosBean != null) {
            ConstantUtils.loadLoginUserImg(mBabyInfosBean.getHeadImageUrl(), ivTempUserImg);
            tvTempUserName.setText(mBabyInfosBean.getNickname());
            initOneDayData(mSelectDate != null ? mSelectDate : new Date());
            initHistoryData();
        }
    }

    /**
     * 某一天数据
     *
     * @param baseResBean
     */
    @Override
    public void returnBBOneDayInfo(TempOneDayDataResBean baseResBean) {
        if (baseResBean.isOk() && baseResBean.getTemperatures().size() > 0) {
            mShareDataBean.setTempOneDayDataResBean(baseResBean);
            //绘制某天数据
            try {
                if (selectType == 0) {
                    // 整天 图表
                    initOneDaLineChartData(baseResBean);
                } else {
                    // 时间间隔 图表
                    initOneDayTimeIntervalChart(baseResBean);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            rlTempNoData.setVisibility(View.VISIBLE);
            rlTempDataChart.setVisibility(View.GONE);
            rlTempHourInterval.setVisibility(View.GONE);
        }


    }

    @Override
    public void returnErrInfo(String errMsg) {
        swipeLayoutTempHistory.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        initHistoryData();
    }

    /**
     * 选中的宝宝
     *
     * @param babyInfosBean
     */
    @Override
    public void selectStrItem(BabyInfosBean babyInfosBean) {
        mBabyInfosBean = babyInfosBean;
        if (mBabyInfosBean != null) {
            ConstantUtils.loadLoginUserImg(mBabyInfosBean.getHeadImageUrl(), ivTempUserImg);
            tvTempUserName.setText(mBabyInfosBean.getNickname());
            initOneDayData(mSelectDate);
            initHistoryData();
        }
    }


    /**
     * 刷新寶寶信息
     *
     * @param bbInfoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventMsg(BBInfoEvent bbInfoEvent) {
        //刷新界面
        initData();
    }


    /**
     * 登录/退出登录
     *
     * @param loginOutMsgEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLoginOutMsg(LoginOutMsgEvent loginOutMsgEvent) {
        initData();
    }


    /**
     * 网络切换
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNetStatusEventMsg(NetStatusEvent netStatusEvent) {
        if (netStatusEvent.isNetConn()) {
            //刷新界面
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
