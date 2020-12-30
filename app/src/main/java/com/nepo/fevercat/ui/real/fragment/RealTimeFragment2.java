package com.nepo.fevercat.ui.real.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chingteach.chartlibrary.view.LineChartView;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseFragment;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.ble.BleScanner;
import com.nepo.fevercat.common.ble.BluetoothLeService;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.DialogUtils;
import com.nepo.fevercat.common.utils.DisplayMetricsUtil;
import com.nepo.fevercat.common.utils.GreenDaoAlertHeightUtils;
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.common.utils.LineChartHelper;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.StringUtils;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.widget.RealTimeResultView;
import com.nepo.fevercat.event.AlertHeightRingEvent;
import com.nepo.fevercat.event.BBInfoEvent;
import com.nepo.fevercat.event.BluetoothStatusEvent;
import com.nepo.fevercat.event.DevicesInfoEvent;
import com.nepo.fevercat.event.LoginOutMsgEvent;
import com.nepo.fevercat.event.MsgEvent;
import com.nepo.fevercat.ui.data.adapter.TempAdapter;
import com.nepo.fevercat.ui.main.LoginActivity;
import com.nepo.fevercat.ui.mine.MineEditBBInfoActivity;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.utils.WriteTempRecordUtils2;
import com.nepo.fevercat.ui.real.RealDevicesInfoActivity;
import com.nepo.fevercat.ui.real.adapter.BBListInfoAdapter;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;
import com.nepo.fevercat.ui.real.contract.RealTimeContract;
import com.nepo.fevercat.ui.real.model.RealTimeModel;
import com.nepo.fevercat.ui.real.presenter.RealTimePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.nepo.fevercat.R.id.rl_real_time_warning_tip;
import static com.nepo.fevercat.common.ble.BluetoothLeService.ACTION_DATA_AVAILABLE;


/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.real.fragment
 * 文件名:  RealTimeFragment
 * 作者 :   <sen>
 * 时间 :  上午11:29 2017/4/24.
 * 描述 : 实时体温
 */

public class RealTimeFragment2 extends BaseFragment<RealTimePresenter, RealTimeModel> implements RealTimeContract.View {


    /**
     * 视图
     */
    @BindView(R.id.iv_real_time_oval_float_rotate)
    ImageView ivRealTimeOvalFloatRotate;
    @BindView(R.id.ll_real_time_oval_float_rotate)
    LinearLayout llRealTimeOvalFloatRotate;
    @BindView(R.id.ll_real_time_oval_float_conning_tip)
    LinearLayout llRealTimeOvalFloatConningTip;
    @BindView(R.id.tv_real_time_oval_float_temp_num_integer)
    TextView tvRealTimeOvalFloatTempNumInteger;
    @BindView(R.id.tv_real_time_oval_float_temp_num_decimal)
    TextView tvRealTimeOvalFloatTempNumDecimal;
    @BindView(R.id.tv_real_time_oval_float_temp_num_unit)
    TextView tvRealTimeOvalFloatTempNumUnit;
    @BindView(R.id.ll_real_time_oval_float_temp)
    LinearLayout llRealTimeOvalFloatTemp;
    @BindView(R.id.rl_real_time_oval_float)
    RelativeLayout rlRealTimeOvalFloat;
    @BindView(R.id.ll_real_time_touch_tip)
    LinearLayout llRealTimeTouchTip;
    @BindView(R.id.tv_baby_tmp_state)
    TextView tvRealTimeTempStatus;
    @BindView(R.id.iv_real_time_temp_status_line)
    ImageView ivRealTimeTempStatusLine;
    @BindView(R.id.tv_real_time_tmp_alarm)
    TextView tvRealTimeTempStatusNum;
    @BindView(R.id.ll_real_time_temp_below)
    LinearLayout llRealTimeTempBelow;
    @BindView(R.id.tv_battery)
    TextView tvRealTimeTempStatusBattery;
    @BindView(R.id.ll_real_time_temp_status)
    RelativeLayout llRealTimeTempStatus;
    @BindView(R.id.rl_real_time_temp_status_float_bg)
    RelativeLayout rlRealTimeTempStatusFloatBg;
    @BindView(R.id.iv_real_time_chart_line)
    ImageView ivRealTimeChartLine;
    @BindView(R.id.tv_real_time_temp_num)
    TextView tvRealTimeTempNum;
    @BindView(R.id.tv_real_time_time_num)
    TextView tvRealTimeTimeNum;
    @BindView(R.id.tv_real_time_chart_first_time)
    TextView tvRealTimeChartFirstTime;
    @BindView(R.id.tv_real_time_chart_second_time)
    TextView tvRealTimeChartSecondTime;
    @BindView(R.id.tv_real_time_chart_third_time)
    TextView tvRealTimeChartThirdTime;
    @BindView(R.id.tv_real_time_chart_fourth_time)
    TextView tvRealTimeChartFourthTime;
    @BindView(R.id.recycle_real_time_bb_list)
    RecyclerView recycleRealTimeBbList;
    @BindView(rl_real_time_warning_tip)
    RelativeLayout rlRealTimeWarningTip;
    @BindView(R.id.tv_real_time_chart_line_base)
    TextView tvRealTimeChartLineBase;
    @BindView(R.id.rl_real_time_version)
    RelativeLayout rlRealTimeVersion;
    @BindView(R.id.tv_real_time_temp_status_num_tip)
    TextView tvRealTimeTempStatusNumTip;
    @BindView(R.id.iv_real_time_adjust)
    ImageView ivRealTimeAdjust;
    @BindView(R.id.ll_normal_temp_contain)
    LinearLayout llNormalTempContain;
    @BindView(R.id.ll_err_temp_contain)
    LinearLayout llErrTempContain;
    @BindView(R.id.temp_recyclerview)
    RecyclerView tempRecyclerview;

    /**
     * 数据
     *
     * @return
     */
    float lastX;
    float rightBorder;
    @BindView(R.id.iv_real_time_top_bluetooth)
    ImageView ivRealTimeTopBluetooth;
    @BindView(R.id.rt_temp_left)
    RealTimeResultView rtTempLeft;
    @BindView(R.id.rt_temp_right)
    RealTimeResultView rtTempRight;
    @BindView(R.id.rt_tmp_delta)
    RealTimeResultView rtTmpDelta;
    @BindView(R.id.rl_temp_result)
    LinearLayout rlTempResult;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.lc_realtime_result)
    LineChartView lcRealtimeResult;
    @BindView(R.id.lc_realtime_delta_result)
    LineChartView lcRealtimeDelta;
    @BindView(R.id.tv_stop)
    TextView tvStop;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    private static final int REAL_TIME_LOGIN_CODE = 150;
    private static final int REAL_TIME_EDIT_BB_CODE = 149;


    // 宝宝信息
    private MineBBResBean mMineBBResBean;
    // 当前放大项
    private int scaleBigPosition;
    // 当前宝宝列表适配器
    private BaseQuickAdapter mBaseQuickAdapter;

    //宝宝列表
    private BBListInfoAdapter mBBListInfoAdapter;
    // 点击放大记录列表
    private List<View> focusViewList = new ArrayList<>();

    // 当前宝宝的体温最高值
    private Double currentBBHeightTemp = 0d;

    /***********  蓝牙  *************/
    private BleScanner mBleScanner;
    // 蓝牙服务
    public static BluetoothLeService mBluetoothLeService;
    // 是否绑定服务标识
    private boolean isBindService;
    // 当前连接设备mac地址
    private String mDeviceAddress;
    // 图表时间间隔
    private Subscription chartTimeLineSub;


    //内部接口对象
    OnAlertClickCallback onAlertClickCallback;


    private boolean isFirstLoad = true;
    private String oldBabyTempNum;
    private float nextX;
    private float moveX;
    private LineChartHelper tempHelper;
    private boolean isSingleTmp;//是否是单路
    private String adjustValue;//校准值
    private ArrayList<Long> deque;
    private boolean showToast = true;//一开始连接时要发送校准重置命令，蓝牙设备会1秒钟发送温度数据一次，12分钟后1分钟一次
    private LineChartHelper deltaHelper;
    private List<TemperaturesBean> result = new ArrayList<>();
    private double i1;
    private TempAdapter tempAdapter;
    private LinearLayoutManager tempLayoutManager;

    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        connectClose();
    }

    @OnClick(R.id.tv_clear)
    public void onRecordClear() {
        result.clear();
        tempAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_export_excel)
    public void onExportExcel() {
        //        test();
        WriteTempRecordUtils2.getInstance().writeToExcel(getContext(), result);
    }

    private void test() {
        result.clear();
        for (int i = 0; i < 20000; i++) {
            TemperaturesBean e = new TemperaturesBean();
            e.setTemperature("12.3");
            e.setTemperatureRight(".00");
            result.add(e);
        }
        WriteTempRecordUtils2.getInstance().writeToExcel(getContext(), result);
    }

    @OnClick(R.id.tv_stop)
    public void onRecord() {
        Object tag = tvStop.getTag();
        if (tag == null) {
            tvStop.setTag(new Object());
            tvStop.setText("继续记录");
        } else {
            tvStop.setTag(null);
            tvStop.setText("停止记录");
        }
    }

    //内部接口回调通信activity
    public interface OnAlertClickCallback {
        void onAlertClick();
    }

    public void setOnAlertClickCallback(OnAlertClickCallback onAlertClickCallback) {
        this.onAlertClickCallback = onAlertClickCallback;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_real_time_ex2;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        EventBus.getDefault().register(this);
        recycleRealTimeBbList.setHasFixedSize(true);
        recycleRealTimeBbList.setItemViewCacheSize(0);
        recycleRealTimeBbList.setDrawingCacheEnabled(false);
        recycleRealTimeBbList.setLayoutManager(new LinearLayoutManager(mActivity, OrientationHelper.HORIZONTAL, false));
        tempLayoutManager = new LinearLayoutManager(mActivity);
        tempRecyclerview.setLayoutManager(tempLayoutManager);
        tempAdapter = new TempAdapter(result);
        tempAdapter.addHeaderView(View.inflate(getContext(), R.layout.item_temp_result, null));
        tempAdapter.bindToRecyclerView(tempRecyclerview);
        tempAdapter.resizeRecyclerview(tempRecyclerview);
        tempAdapter.setEmptyView(R.layout.view_empty_table);
        tempAdapter.getEmptyView().findViewById(R.id.rl_add_table).setVisibility(View.GONE);
        mBleScanner = BleScanner.core(mActivity);
        changeBluetoothStatusImg(mBleScanner.isBleOpen());
        initListen();
        initNetBBInfo();
        initChartData();
        //        testDB(0);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            tvRealTimeChartLineBase.setText(ConstantUtils.IsCelsiusUnit("37.5"));
            if (currentBBHeightTemp != 0) {
                tvRealTimeTempNum.setText(ConstantUtils.IsCelsiusUnit(currentBBHeightTemp + ""));
            } else if (oldBabyTempNum != null) {
                tvRealTimeTempNum.setText(ConstantUtils.IsCelsiusUnit(oldBabyTempNum));
            } else {
                tvRealTimeTempNum.setText(ConstantUtils.IsCelsiusUnit("0"));
            }
            EventBus.getDefault().post(new AlertHeightRingEvent());
            if (!TextUtils.isEmpty(AppConstant.mCurrentAlertHeightTemp)) {
                Log.e("Tag", "onHiddenChanged: " + ConstantUtils.ConvertStrToFahrenheit(AppConstant.mCurrentAlertHeightTemp));
                tvRealTimeTempStatusNum.setText(ConstantUtils.ConvertStrToFahrenheit(AppConstant.mCurrentAlertHeightTemp));
            } else {
                tvRealTimeTempStatusNum.setText("--:--");
            }
        }
    }

    /**
     * 查看版本信息
     */
    @OnClick(R.id.rl_real_time_version)
    public void onVersionClick() {
        startAct(RealDevicesInfoActivity.newIntent(mActivity));
    }


    /**
     * 事件
     */
    private void initListen() {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;

        ViewGroup.LayoutParams layoutParams = rlRealTimeTempStatusFloatBg.getLayoutParams();
        int width = layoutParams.width;

        ViewGroup.LayoutParams ivLayout = rlRealTimeOvalFloat.getLayoutParams();
        int ivWidth = ivLayout.width;

        //右侧边界
        rightBorder = w_screen - (w_screen - width) - ivWidth;

        // 滑动中界限
        final float halfBorder = rightBorder / 2;

        tempRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (result != null && result.size() > 0) {
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                        tvStop.setTag(new Object());
                        tvStop.setText("继续记录");
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        /**
         * 滑动
         */
        rlRealTimeOvalFloat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        rlRealTimeOvalFloat.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_MOVE:

                        float distanceX = lastX - event.getRawX();
                        moveX = rlRealTimeOvalFloat.getX();
                        nextX = rlRealTimeOvalFloat.getX() - distanceX;

                        // 右侧边界值
                        if (nextX > rightBorder) {
                            nextX = rightBorder;
                        }

                        int leftDip = DisplayMetricsUtil.px2dip(mActivity, 0);
                        // 左侧边界值

                        if (nextX < leftDip) {
                            nextX = DisplayMetricsUtil.px2dip(mActivity, leftDip);
                        }

                        if (rlRealTimeOvalFloat.getX() > halfBorder) {
                            if (!isBindService) {
                                rlRealTimeOvalFloat.setBackgroundResource(R.drawable.icon_real_time_float_bg);
                                //隐藏开始连接提示语
                                llRealTimeTouchTip.setVisibility(View.GONE);
                                //显示连接成功提示语
                                llRealTimeTempStatus.setVisibility(View.VISIBLE);
                                //温度
                                llRealTimeOvalFloatTemp.setVisibility(View.GONE);
                            } else {
                                rlRealTimeOvalFloat.setBackgroundResource(R.drawable.icon_real_time_float_bg);
                                //隐藏开始连接提示语
                                llRealTimeTouchTip.setVisibility(View.GONE);
                                //显示连接成功提示语
                                llRealTimeTempStatus.setVisibility(View.VISIBLE);
                                //温度
                                llRealTimeOvalFloatTemp.setVisibility(View.VISIBLE);
                            }
                        } else {
                            rlRealTimeOvalFloat.setBackgroundResource(R.drawable.icon_real_time_no_conn_float_bg);
                            //隐藏开始连接提示语
                            llRealTimeTouchTip.setVisibility(View.VISIBLE);
                            //显示连接成功提示语
                            llRealTimeTempStatus.setVisibility(View.GONE);
                            //温度
                            llRealTimeOvalFloatTemp.setVisibility(View.GONE);
                        }


                        ObjectAnimator x = ObjectAnimator.ofFloat(rlRealTimeOvalFloat, "x", moveX, nextX);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(x);
                        animatorSet.setDuration(0);
                        animatorSet.start();

                        lastX = event.getRawX();

                        break;
                    case MotionEvent.ACTION_UP:
                        i1 = new Random().nextInt(3);
                        if (rlRealTimeOvalFloat.getX() > halfBorder) {
                            // 切换右
                            ObjectAnimator upX = ObjectAnimator.ofFloat(rlRealTimeOvalFloat, "x", rightBorder, rightBorder);
                            AnimatorSet animatorSetUp = new AnimatorSet();
                            animatorSetUp.playTogether(upX);
                            animatorSetUp.setDuration(0);
                            animatorSetUp.start();

                            if (!isBindService) {
                                // 向右 是否选择了宝宝
                                if (AppConstant.mCurrentBabyInfo != null) {
                                    connectOpen();
                                    //开始扫描设备
                                    initBlueDevices();
                                } else {
                                    // 关闭连接 左
                                    rlRealTimeOvalFloat.setBackgroundResource(R.drawable.icon_real_time_no_conn_float_bg);
                                    connectClose();
                                    // 弹窗提示
                                    choiceBBErrAlert();
                                }
                            }
                        } else {
                            // 关闭连接
                            connectClose();
                            // 向左
                        }
                        break;

                }

                return true;
            }
        });


    }

    /**
     * 校准
     */
    @OnClick(R.id.iv_real_time_adjust)
    public void onAdjustClick() {

        showAdjustPwd();
    }

    @OnClick(R.id.ll_real_time_temp_below)
    public void onRealTimeTempClick() {
        onAlertClickCallback.onAlertClick();
    }

    /**
     * 开启连接
     */
    private void connectOpen() {
        //显示旋转圆点
        llRealTimeOvalFloatRotate.setVisibility(View.VISIBLE);
        //设置连接图片
        rlRealTimeOvalFloat.setBackgroundResource(R.drawable.icon_real_time_float_bg);
        //隐藏开始连接提示语
        llRealTimeTouchTip.setVisibility(View.GONE);
        //旋转图片
        startRotateAnimation(ivRealTimeOvalFloatRotate);
        //显示连接成功提示语
        llRealTimeTempStatus.setVisibility(View.VISIBLE);
        //显示连接中..
        llRealTimeOvalFloatConningTip.setVisibility(View.VISIBLE);
        // 切换右
        ObjectAnimator upX = ObjectAnimator.ofFloat(rlRealTimeOvalFloat, "x", rightBorder, rightBorder);
        AnimatorSet animatorSetUp = new AnimatorSet();
        animatorSetUp.playTogether(upX);
        animatorSetUp.setDuration(500);
        animatorSetUp.start();

    }

    /**
     * 关闭连接
     */
    public void connectClose() {
        setConnectViewVisible(true);
        tvStop.setTag(new Object());
        tvStop.setText("开始记录");
        rlTempResult.setVisibility(View.INVISIBLE);
        // 解除绑定服务
        unbindService();
        //停止旋转
        stopRotateAnimation(ivRealTimeOvalFloatRotate);
        //隐藏旋转圆点
        llRealTimeOvalFloatRotate.setVisibility(View.GONE);
        rlRealTimeOvalFloat.setBackgroundResource(R.drawable.icon_real_time_no_conn_float_bg);
        //隐藏连接成功提示语
        llRealTimeTempStatus.setVisibility(View.GONE);
        //显示开始连接提示语
        llRealTimeTouchTip.setVisibility(View.VISIBLE);
        //隐藏连接中..
        llRealTimeOvalFloatConningTip.setVisibility(View.GONE);
        //温度
        llRealTimeOvalFloatTemp.setVisibility(View.GONE);
        //版本提示
        rlRealTimeVersion.setVisibility(View.GONE);
        // 测试高温提示
        rlRealTimeWarningTip.setVisibility(View.GONE);
        // 校验
        ivRealTimeAdjust.setVisibility(View.INVISIBLE);

        tvRealTimeTempStatusNum.setText("00.00");
        tvRealTimeTempStatus.setText("");
        tvRealTimeTempStatus.setTextColor(ContextCompat.getColor(mActivity, R.color.nav_normal));
        rtTempLeft.reset();
        rtTempRight.reset();
        rtTmpDelta.reset();
        i1 = 0;
        // 缩小
        //scaleSmallAllView();
        // 切换左
        if (!isBackground)
        {
            int leftDip = DisplayMetricsUtil.px2dip(mActivity, 0);
            ObjectAnimator upX = ObjectAnimator.ofFloat(rlRealTimeOvalFloat, "x", leftDip, leftDip);
            AnimatorSet animatorSetUp = new AnimatorSet();
            animatorSetUp.playTogether(upX);
            animatorSetUp.setDuration(500);
            animatorSetUp.start();
        }else {
            rlRealTimeOvalFloat.setX(rlRealTimeOvalFloat.getLeft());
        }

        // 还原高温响铃
        GreenDaoAlertHeightUtils.getInstance().restoreHeightAlert();
    }

    /**
     * 连接成功
     */
    private void connectSuccess() {
        //停止旋转
        //stopRotateAnimation(ivRealTimeOvalFloatRotate);
        llRealTimeOvalFloatTemp.setVisibility(View.VISIBLE);
        rlRealTimeVersion.setVisibility(View.VISIBLE);
        llRealTimeOvalFloatConningTip.setVisibility(View.GONE);
        // 校验
        ivRealTimeAdjust.setVisibility(View.VISIBLE);
        // 时间间隔
        //        setChartTimeInterval();
        AppConstant.IsDevicesConn = true;
        currentBBHeightTemp = 0d;
        // 显示最高体温值
        tvRealTimeTempNum.setText(AppConstant.getCurrentBBHeightTemp());
        showTempresultView();
        sendResetcmd();
    }

    /**
     * 双路体温连接成功时需要发送校准命令，这样它重置状态，前12分钟1秒钟发送1次
     */
    private void sendResetcmd() {
        showToast = false;
        adjustValue = getAdjustValue();
        getAdjustInfo(adjustValue);
    }

    private void showTempresultView() {
        setConnectViewVisible(false);
        rlTempResult.setVisibility(View.VISIBLE);
        rtTempRight.startAnimation();
        rtTempLeft.startAnimation();
        rtTmpDelta.startAnimation();
    }

    private void setConnectViewVisible(boolean isVisible) {
        rlRealTimeTempStatusFloatBg.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 连接失败
     */
    private void connectFail() {
        connectOpen();
        llRealTimeOvalFloatTemp.setVisibility(View.GONE);
    }


    /**
     * 匀速转动
     *
     * @param imageView
     */
    private void startRotateAnimation(ImageView imageView) {
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        imageView.startAnimation(animation);
    }

    private void stopRotateAnimation(ImageView imageView) {

        imageView.clearAnimation();

    }


    /**
     * 请求BB信息
     */
    private void initNetBBInfo() {

        // 判断是否有网络 true 请求网络 false 获取本地保存的
        MineBBReqBean mineBBReqBean = new MineBBReqBean();
        mineBBReqBean
                .setAccountId(AppConstant.getUserInfo().getUserId())
                .setOperateID(ApiConstants.MODIFY_BABY_LIST_4)
                .setTRANSID(ApiConstants.MODIFY_BABY);
        mPresenter.putMineBBInfoRequest(mineBBReqBean, false, isFirstLoad);
    }


    /**
     * 绘制BB列表信息
     */
    private void initBBListInfo() {
        focusViewList = new ArrayList<>();
        BabyInfosBean babyInfosBean = new BabyInfosBean();
        babyInfosBean.setAdd(true);
        if (mMineBBResBean != null
                && mMineBBResBean.getBabyInfos() != null
                && mMineBBResBean.getBabyInfos().size() > 0
                ) {
            List<BabyInfosBean> babyInfos = mMineBBResBean.getBabyInfos();
            babyInfos.add(babyInfosBean);
            // 默认上次选中的宝宝
            String bbID = SharedPreferencesUtil.getString(AppConstant.Current_Scale_BB, "-1");
            if (!TextUtils.equals(bbID, "-1")) {
                for (int i = 0; i < babyInfos.size(); i++) {
                    BabyInfosBean babyInfo = babyInfos.get(i);
                    if (TextUtils.equals(bbID, babyInfo.getBabyId())) {
                        babyInfo.setIsScaleBig("1");
                        //保存选中的宝宝
                        AppConstant.mCurrentBabyInfo = babyInfo;
                        scaleBigPosition = i;
                        // 体温最高值
                        String string1 = SharedPreferencesUtil.getString(AppConstant.mCurrentBabyInfo.getBabyId(), "0");
                        oldBabyTempNum = string1;
                        tvRealTimeTempNum.setText(ConstantUtils.IsCelsiusUnit(string1));
                        break;
                    }
                }

            }


            mBBListInfoAdapter = new BBListInfoAdapter(babyInfos);
            mBBListInfoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recycleRealTimeBbList.setAdapter(mBBListInfoAdapter);
        } else {
            List<BabyInfosBean> babyInfosBeanList = new ArrayList<>();
            babyInfosBeanList.add(babyInfosBean);

            // 默认上次选中的宝宝
            String bbID = SharedPreferencesUtil.getString(AppConstant.Current_Scale_BB, "-1");
            if (!TextUtils.equals(bbID, "-1")) {
                for (int i = 0; i < babyInfosBeanList.size(); i++) {
                    BabyInfosBean babyInfo = babyInfosBeanList.get(i);
                    if (TextUtils.equals(bbID, babyInfo.getBabyId())) {
                        babyInfo.setIsScaleBig("1");
                        //保存选中的宝宝
                        AppConstant.mCurrentBabyInfo = babyInfo;
                        scaleBigPosition = i;
                        // 体温最高值
                        String string1 = SharedPreferencesUtil.getString(AppConstant.mCurrentBabyInfo.getBabyId(), "0");
                        oldBabyTempNum = string1;
                        tvRealTimeTempNum.setText(ConstantUtils.IsCelsiusUnit(string1));
                        break;
                    }
                }
            }
            mBBListInfoAdapter = new BBListInfoAdapter(babyInfosBeanList);
            mBBListInfoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recycleRealTimeBbList.setAdapter(mBBListInfoAdapter);
        }

        // 宝宝点击
        mBBListInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mBaseQuickAdapter = adapter;
                List<BabyInfosBean> babyInfosBeanList = adapter.getData();
                BabyInfosBean babyInfosBean = (BabyInfosBean) adapter.getItem(position);
                if (babyInfosBean.isAdd()) {
                    //                    // 添加宝宝
                    if (isBindService) {
                        ToastUtils.showToast(R.string.conn_add_bb_info_err);
                        return;
                    }
                    if (AppConstant.isLogin() || AppConstant.IS_OFFLINEMODE) {
                        if (adapter.getItemCount() == AppConstant.Max_BB_Info) {
                            ToastUtils.showToast(R.string.add_bb_err);
                            return;
                        }
                        startActivityForResult(MineEditBBInfoActivity.newIntent(mActivity, null), REAL_TIME_EDIT_BB_CODE);
                    } else {
                        startActivityForResult(LoginActivity.newIntent(mActivity), REAL_TIME_EDIT_BB_CODE);
                    }
                } else {
                    if (isBindService) {
                        changeBBErrAlert();
                        return;
                    }
                    for (int i = 0; i < babyInfosBeanList.size(); i++) {
                        BabyInfosBean scaleBean = babyInfosBeanList.get(i);
                        scaleBean.setIsScaleBig("0");
                        adapter.setData(i, scaleBean);
                    }
                    BabyInfosBean scaleBean = babyInfosBeanList.get(position);
                    scaleBean.setIsScaleBig("1");
                    adapter.setData(position, scaleBean);

                    //保存选中的宝宝
                    AppConstant.mCurrentBabyInfo = babyInfosBean;
                    SharedPreferencesUtil.set(AppConstant.Current_Scale_BB, babyInfosBean.getBabyId());
                    EventBus.getDefault().post(new AlertHeightRingEvent());
                    scaleBigPosition = position;
                    // 体温最高值
                    String string1 = SharedPreferencesUtil.getString(AppConstant.mCurrentBabyInfo.getBabyId(), "0");
                    oldBabyTempNum = string1;
                    tvRealTimeTempNum.setText(ConstantUtils.IsCelsiusUnit(string1));
                }

            }
        });


    }


    /**
     * 图表数据
     */
    private void initChartData() {
        // 时间间隔
        //        setChartTimeInterval();
        tempHelper = new LineChartHelper(lcRealtimeResult);
        deltaHelper = new LineChartHelper(lcRealtimeDelta);
        tempHelper.init(2, 42.2f, 31.8f, getResources().getColor(R.color.light_blue), getResources().getColor(R.color.red));
        deltaHelper.init(1, 3, -3, getResources().getColor(R.color.light_yellow));
        rtTempLeft.reset();
        rtTempRight.reset();
        rtTmpDelta.reset();
    }
    boolean isBackground;
    @Override
    public void onStop() {
        super.onStop();
        isBackground=true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isBackground=false;
    }

    /**
     * 缩小全部视图
     */
    private void scaleSmallAllView() {
        if (mBaseQuickAdapter != null) {
            List<BabyInfosBean> babyInfosBeanList = mBaseQuickAdapter.getData();
            for (int i = 0; i < babyInfosBeanList.size(); i++) {
                BabyInfosBean scaleBean = babyInfosBeanList.get(i);
                scaleBean.setIsScaleBig("0");
                mBaseQuickAdapter.setData(i, scaleBean);
            }
        }
    }


    /**
     * 蓝牙开关图标
     */
    private void changeBluetoothStatusImg(boolean isOpen) {
        if (isOpen) {
            // 开 图标
            ivRealTimeTopBluetooth.setBackgroundResource(R.drawable.icon_main_top_bar_bluetooth_conn);
        } else {
            // 关 图标
            ivRealTimeTopBluetooth.setBackgroundResource(R.drawable.icon_main_top_bar_bluetooth_dis);
            // 关闭连接
            connectClose();
        }
    }

    /**
     * 蓝牙设备信息
     */
    private void initBlueDevices() {
        if (mBleScanner.isBleSupport()) {
            if (mBleScanner.isBleOpen()) {
                // 扫描结果回调
                if (!isBindService) {
                    mBleScanner.setLeScanCallback(new BleScanner.BleScanCallBack() {
                        @Override
                        public void onBleScan(BluetoothDevice device, int rssi, byte[] scanResult) {
                            String name = device.getName();
                            String address = device.getAddress();
                            //LogUtils.logd("-- 蓝牙名字:" + name + ",=," + address);
                            if (name != null
                                    && name.trim().contains(AppConstant.SYS_BLUETOOTH_NAME)) {
                                // 保存对应mac 地址
                                if (!TextUtils.isEmpty(address)) {
                                    mDeviceAddress = address;
                                    SharedPreferencesUtil.setString(AppConstant.SYS_CUR_DEVICE_ADDRESS, address);
                                }

                                // 找到设备结束扫描
                                mBleScanner.stopScan();
                                LogUtils.logd("-- 扫描成功");
                                //开启后台连接服务
                                startBluetoothService();
                            }

                        }

                        @Override
                        public void onScanFail(int errorCode) {
                            LogUtils.logd("-- 扫描失败");
                            mBleScanner.stopScan();
                            connectClose();
                            conErrAlert();
                        }

                        @Override
                        public void onStartScan() {
                            // 开始, 提醒界面
                            LogUtils.logd("-- 提醒界面 开始");
                        }

                        @Override
                        public void onStopScan() {
                            // 结束, 提醒界面
                            LogUtils.logd("-- 提醒界面 结束");
                        }
                    });
                    // 开始扫描
                    mBleScanner.startScan();
                }

            } else {
                buleToothNoOpen();
                connectClose();
            }
        }
    }

    /**
     * 开启后台连接服务
     */
    private void startBluetoothService() {
        Intent gattServiceIntent = new Intent(mActivity,
                BluetoothLeService.class);
        isBindService = mActivity.bindService(gattServiceIntent, mServiceConnection,
                mActivity.BIND_AUTO_CREATE);

    }

    /**
     * 绑定服务
     */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                mBluetoothLeService = ((BluetoothLeService.LocalBinder) iBinder)
                        .getService();
                if (!TextUtils.isEmpty(mDeviceAddress))
                    mBluetoothLeService.connect(mDeviceAddress);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            isBindService = false;

            LogUtils.logd("---------------------=================******************设备断开**************=================");

            DialogUtils.getInstance(mActivity)
                    .blueDisconnectDialog(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // 确定
                        }
                    }, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // 取消
                        }
                    });


        }
    };


    /**
     * 解除服务绑定
     */
    private void unbindService() {
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnectGatt();
        }
        if (isBindService)
            mActivity.unbindService(mServiceConnection);

        if (mBleScanner != null) {
            mBleScanner.stopScan();
        }
        if (chartTimeLineSub != null) {
            if (!chartTimeLineSub.isUnsubscribed()) {
                chartTimeLineSub.unsubscribe();
            }
        }

        isBindService = false;
        mBluetoothLeService = null;
        mDeviceAddress = "";
        AppConstant.IsDevicesConn = false;
    }


    /**
     * 获取设备电量
     */
    private void getDevicesBattery() {
        // 延迟10秒获取电量 给设备响应时间
        Observable.timer(11, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_BATTERY_FLAG_WRITE, ""));
            }
        });

    }

    /**
     * 获取厂商名字
     */
    private void getManufacturerName() {
        // 延时3秒
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_GET_MANUFACTURER_NAME_FLAG_WRITE, ""));
                    }
                });
    }

    /**
     * 获取设备序列号
     */
    private void getProductSequence() {
        // 延时4秒
        Observable.timer(4, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_GET_SERIAL_FLAG_WRITE, ""));
                    }
                });
    }


    /**
     * 获取设备型号
     */
    private void getProductModels() {
        // 延时5秒
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_GET_MODELS_FLAG_WRITE, ""));
                    }
                });
    }

    /**
     * 获取硬件版本
     */
    private void getHardWareVersion() {
        // 延时6秒
        Observable.timer(6, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_HARD_VERSION_FLAG_WRITE, ""));
                    }
                });
    }

    /**
     * 获取软件版本
     */
    private void getSoftWareVersion() {
        // 延时6秒
        Observable.timer(7, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_SOFT_VERSION_FLAG_WRITE, ""));
                    }
                });
    }

    /**
     * 获取固件版本
     */
    private void getFirmWareVersion() {
        // 延时6秒
        Observable.timer(8, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_FIRM_VERSION_FLAG_WRITE, ""));
                    }
                });
    }

    /**
     * 获取协议版本
     */
    private void getProtocolVersion() {
        // 延时6秒
        Observable.timer(9, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_PROTOCOL_VERSION_FLAG_WRITE, ""));
                    }
                });
    }

    /**
     * 获取校准信息
     */
    private void getAdjustInfo(String msgStr) {
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_ADJUST_FLAG_WRITE, msgStr));
    }

    private double getFormatTemp(String subHexInt, String subHexDecimal) {
        DecimalFormat df = new DecimalFormat("#.00");
        Double dTemp = Double.valueOf(subHexInt + subHexDecimal);
        if (!ConstantUtils.IsCelsius()) {
            dTemp = ConstantUtils.CConvert2F(dTemp);
        }
        return dTemp;
    }

    /**
     * 设置温度等信息
     */
    private void setTempDataToView(List<String> tempData) {
        if (rlTempResult.getVisibility() != View.VISIBLE) {
            showTempresultView();
        }
        DecimalFormat df = new DecimalFormat("00.00");
        String subHexInt = tempData.get(0);
        String subHexDecimal = tempData.get(1);
        String subHexInt2 = tempData.get(2);
        String subHexDecimal2 = tempData.get(3);
        final double dTempLeft = Double.valueOf(subHexInt + subHexDecimal);
        final double dTempRight = Double.valueOf(subHexInt2 + subHexDecimal2);
        String sTempLeft = df.format(dTempLeft);
        String sTempRight = df.format(dTempRight);
        rtTempLeft.setResult(sTempLeft);
        rtTempRight.setResult(sTempRight);
        isSingleTmp = dTempRight == 0;
        //        if (dTempLeft!=0)
        //        setChartTimeInterval();
        if (!isSingleTmp)
            rtTmpDelta.setResult(dTempLeft - dTempRight + "", true);
        if (dTempLeft > 0 &&!(dTempLeft < 32 || dTempLeft > 42))
            tempHelper.addPoint(0, (float) dTempLeft);
        if (dTempRight > 0)
            tempHelper.addPoint(1, (float) dTempRight);
        if (dTempLeft > 0 && dTempRight > 0)
            deltaHelper.addPoint(0, (float) (dTempLeft - dTempRight));
        deltaHelper.refresh();
        tempHelper.refresh();
        double dTemp = Math.max(dTempLeft, dTempRight);
        rlRealTimeWarningTip.setVisibility(dTemp < 32 || dTemp > 42 ? View.VISIBLE : View.GONE);
        // 体温状态
        int tempStatusColor = ConstantUtils.CheckTempLimitToColor(dTemp);
        String tempStatus = ConstantUtils.CheckTempLimitToStr(dTemp);
        tvRealTimeTempStatus.setTextColor(tempStatusColor);
        tvRealTimeTempStatus.setText(tempStatus);
        // 温度背景
        //        Drawable drawable = ConstantUtils.CheckTempLimitFloatBg(dTemp);
        //        rlRealTimeOvalFloat.setBackground(drawable);
        // 设置高温报警温度
        if (!TextUtils.isEmpty(AppConstant.mCurrentAlertHeightTemp)) {
            tvRealTimeTempStatusNum.setVisibility(View.VISIBLE);
            tvRealTimeTempStatusNumTip.setVisibility(View.GONE);
            tvRealTimeTempStatusNum.setText(ConstantUtils.ConvertStrToFahrenheit(AppConstant.mCurrentAlertHeightTemp));
        } else {
            tvRealTimeTempStatusNum.setVisibility(View.GONE);
            tvRealTimeTempStatusNumTip.setVisibility(View.VISIBLE);
        }
        // 是否是超高热
        if (ConstantUtils.IsSuperHot(dTemp)) {
            if (AppConstant.mCurrentSuperHotTime == 0l) {
                AppConstant.mCurrentSuperHotTime = System.currentTimeMillis();
            }
            long reduceTime = System.currentTimeMillis() - AppConstant.mCurrentSuperHotTime;
            String sTimeStr = ConstantUtils.formatTimeByMillisecond(reduceTime);
            tvRealTimeTimeNum.setText(sTimeStr);
        } else {
            AppConstant.mCurrentSuperHotTime = 0l;
        }

        // 体温最高值
        //        Double aDouble = AppConstant.compareHeightTemp(dTemp);
        if (dTemp > currentBBHeightTemp) {
            currentBBHeightTemp = dTemp;
        }
        tvRealTimeTempNum.setText(ConstantUtils.IsCelsiusUnit(String.valueOf(currentBBHeightTemp)));
        // 保存当前宝宝的最高体温值
        AppConstant.compareHeightTemp(currentBBHeightTemp);
        // 检测是否到达高温警告
        EventBus.getDefault().post(new AlertHeightRingEvent().setTemp(String.valueOf(dTemp)));
    }


    /**
     * 设置图表时间间隔
     * 5分钟
     */
    private void setChartTimeInterval() {
        if (deque == null) {
            deque = new ArrayList<>();
        }
        int time = 1;
        if (!isBindService) {
            String zero = getTimeByMinute(0);
            String one = getTimeByMinute(-1);
            String two = getTimeByMinute(-2);
            String three = getTimeByMinute(-3);
            tvRealTimeChartFirstTime.setText(three);
            tvRealTimeChartSecondTime.setText(two);
            tvRealTimeChartThirdTime.setText(one);
            tvRealTimeChartFourthTime.setText(zero);


        } else {
            if (deque.size() == 0) {
                time = 5;
                if (!isSingleTmp) {
                    time = 1;
                }
                deque.add(System.currentTimeMillis());
                deque.add(System.currentTimeMillis() + 1000 * time);
                deque.add(System.currentTimeMillis() + 1000 * time * 2);
                deque.add(System.currentTimeMillis() + 1000 * time * 3);
            }
            deque.remove(0);
            deque.add(System.currentTimeMillis());
            //            tvRealTimeChartFirstTime.setText(DateUtil.parseDateToString2(deque.get(0)));
            //            tvRealTimeChartSecondTime.setText(DateUtil.parseDateToString2(deque.get(1)));
            //            tvRealTimeChartThirdTime.setText(DateUtil.parseDateToString2(deque.get(2)));
            //            tvRealTimeChartFourthTime.setText(DateUtil.parseDateToString2(deque.get(3)));
        }


        if (isBindService) {
            final int finalTime = time;
            if (chartTimeLineSub == null || chartTimeLineSub.isUnsubscribed())
                chartTimeLineSub = Observable.interval(time, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                String zero = getTimeBySecond(0);
                                String one = getTimeBySecond(-finalTime);
                                String two = getTimeBySecond(-finalTime * 2);
                                String three = getTimeBySecond(-finalTime * 3);
                                tvRealTimeChartFirstTime.setText(three);
                                tvRealTimeChartSecondTime.setText(two);
                                tvRealTimeChartThirdTime.setText(one);
                                tvRealTimeChartFourthTime.setText(zero);
                            }
                        });

        }


    }


    /**
     * 增加温度差计算
     */
    private Float tempDiffValue(Float tempValue) {
        if (tempValue <= 35) {
            tempValue = 35f;
        } else if (tempValue >= 42) {
            tempValue = 42f;
        }

        return tempValue;
    }

    private static String getTimeByMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("HH:mm").format(calendar.getTime());

    }

    private static String getTimeBySecond(int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, second);
        return new SimpleDateFormat("HH:mm:ss").format(calendar.getTime());

    }


    /**
     * 选择宝宝提醒
     */
    private void choiceBBErrAlert() {
        DialogUtils.getInstance(mActivity).SingleConfirmDialog(getString(R.string.choice_empty_err_tip));
    }

    /**
     * 为开启蓝牙提醒
     */
    private void buleToothNoOpen() {
        DialogUtils.getInstance(mActivity).SingleConfirmDialog(getString(R.string.open_buletooth));
    }

    /**
     * 连接失败提醒
     * Alert
     */
    private void conErrAlert() {
        DialogUtils.getInstance(mActivity).SingleConfirmDialog(getString(R.string.con_err_tip));
    }

    /**
     * 连接中切换宝宝提醒
     * Alert
     */
    private void changeBBErrAlert() {
        DialogUtils.getInstance(mActivity).SingleConfirmDialog(getString(R.string.choice_err_tip));
    }


    /**
     * 保存温度到数据库
     */
    private void addTempDataToDB(TemperaturesBean temperaturesBean) {
        GreenDaoUtils.getInstance(mActivity).addTempInfo(temperaturesBean);
    }

    private TemperaturesBean getTempBean(String hexStr, List<String> tempData) {
        DecimalFormat df = new DecimalFormat("#.00");
        String subHexInt = tempData.get(0);
        String subHexDecimal = tempData.get(1);
        String subHexInt2 = tempData.get(2);
        String subHexDecimal2 = tempData.get(3);
        // 温度
        Double left = Double.valueOf(subHexInt + subHexDecimal);
        Double right = Double.valueOf(subHexInt2 + subHexDecimal2);
        TemperaturesBean temperaturesBean = new TemperaturesBean();
        if (AppConstant.mCurrentBabyInfo != null) {
            // 保存到本地数据库
            String accountId = "None";
            if (AppConstant.isLogin()) {
                accountId = AppConstant.getUserInfo().getUserId();
            }
            temperaturesBean.setBabyId(AppConstant.mCurrentBabyInfo.getBabyId());
            temperaturesBean.setAccountId(accountId);
            temperaturesBean.setLocalBabyId(AppConstant.mCurrentBabyInfo.getLocalId());
            temperaturesBean.setTempHex(hexStr);
            temperaturesBean.setTemperature(df.format(left));
            temperaturesBean.setTemperatureRight(df.format(right));
            temperaturesBean.setTempTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if (adjustValue == null)
                adjustValue = getAdjustValue();
            temperaturesBean.setLeftAdjustValue(adjustValue.split("/")[0]);
            temperaturesBean.setRightAdjustValue(adjustValue.split("/")[1]);
            temperaturesBean.setTime(System.currentTimeMillis());
        }
        return temperaturesBean;
    }

    private void setTempResultList(TemperaturesBean temperaturesBean) {
        result.add(temperaturesBean);
        if (tvStop.getTag() == null) {
            tempLayoutManager.scrollToPosition(result.size());
            tempAdapter.notifyItemInserted(result.size());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void returnBBInfo(MineBBResBean mineBBResBean) {
        if (mineBBResBean.isOk()) {
            mMineBBResBean = mineBBResBean;
            AppConstant.saveBBListInfo(mMineBBResBean);
            initBBListInfo();
        }
    }

    @Override
    public void returnErrInfo(String err) {
        LogUtils.logd("-- err:" + err);
    }


    /**
     * 刷新寶寶信息
     *
     * @param bbInfoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventMsg(BBInfoEvent bbInfoEvent) {
        //刷新界面
        initNetBBInfo();
    }

    /**
     * 登录/退出登录
     *
     * @param loginOutMsgEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLoginOutMsg(LoginOutMsgEvent loginOutMsgEvent) {

        // 更新用户ID到宝宝
        GreenDaoUtils.getInstance(mActivity).updateAccountIDToBabyInfo();
        // 更新用户ID到体温
        GreenDaoUtils.getInstance(mActivity).updateAccountIDToTempInfo();
        initNetBBInfo();

    }

    /**
     * 蓝牙开关切换
     *
     * @param bluetoothStatusEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetBluttoothStatusMsg(BluetoothStatusEvent bluetoothStatusEvent) {

        changeBluetoothStatusImg(bluetoothStatusEvent.isStatusOpen());
        if (!bluetoothStatusEvent.isStatusOpen()) {
            if (mBleScanner == null) {
                mBleScanner = BleScanner.core(mActivity);
            }
            mBleScanner.isBleOpen();
        }

    }


    /**
     * 蓝牙连接、通信
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMsg(MsgEvent msgEvent) {
        if (msgEvent != null) {
            if (!TextUtils.isEmpty(msgEvent.getAction())) {
                switch (msgEvent.getAction()) {
                    case BluetoothLeService.ACTION_GATT_CONNECTED:
                        LogUtils.logd("--- 连接成功");
                        // 连接成功
                        connectSuccess();
                        // 发送获取电量指令
                        getDevicesBattery();
                        // 发送获取厂商名称指令
                        getManufacturerName();
                        // 发送获取序列号指令
                        getProductSequence();
                        // 发送获取设备型号指令
                        getProductModels();
                        // 发送获取设备硬件版本指令
                        getHardWareVersion();
                        // 发送获取设备软件版本指令
                        getSoftWareVersion();
                        // 发送获取设备固件版本指令
                        getFirmWareVersion();
                        // 发送获取设备协议版本指令
                        getProtocolVersion();
                        break;
                    case BluetoothLeService.ACTION_GATT_DISCONNECTED:
                        // 断开重连
                        connectClose();
                        LogUtils.logd("--- 断开重连");
                        // 连接失败
                        if (mBluetoothLeService != null) {
                            String address = SharedPreferencesUtil.getString(AppConstant.SYS_CUR_DEVICE_ADDRESS, "");
                            mBluetoothLeService.closeGatt();
                            //                            mBluetoothLeService.connect(address);
                            //                            Log.e("tag", "onGetMsg: 断开重连了" );
                        }
                        break;
                    case ACTION_DATA_AVAILABLE:
                        // 通信数据
                        String hexStr = msgEvent.getMsg();
                        if (hexStr.contains(AppConstant.DEVICES_BATTERY_FLAG)) {
                            // 电量
                            Log.e("tag", "-- 电量1：" + hexStr);
                            String hexBatteryStr = StringUtils.subBatteryConvert10(hexStr);
                            Log.e("tag", "-- 电量2：" + hexBatteryStr);
                            //转换电量
                            tvRealTimeTempStatusBattery.setText(hexBatteryStr + "%");
                        } else if (hexStr.contains(AppConstant.DEVICES_TEMP_FLAG)) {
                            // 温度
                            LogUtils.logd("接受指令温度数据:" + hexStr);
                            List<String> tempList = StringUtils.subTempConvert10(hexStr);
                            setTempDataToView(tempList);
                            // 封装温度保存到数据库
                            TemperaturesBean tempBean = getTempBean(hexStr, tempList);
                            addTempDataToDB(tempBean);
                            setTempResultList(tempBean);
                            getDevicesBattery();
                        } else if (hexStr.contains(AppConstant.MANUFACTURER_NAME_FLAG)) {
                            // 厂商名称
                            String sName = StringUtils.subManufacturerNameToASCII(hexStr);
                            LogUtils.logd("-- 名称：" + sName);
                            SharedPreferencesUtil.set(AppConstant.MANUFACTURER_NAME_Tag, sName);
                            EventBus.getDefault().post(new DevicesInfoEvent().setTag(DevicesInfoEvent.MANUFACTURER_NAME_FLAG));
                        } else if (hexStr.contains(AppConstant.PRODUCT_SEQUENCE_FLAG)) {
                            // 设备序列号
                            String sName = StringUtils.subProductSequence(hexStr);
                            LogUtils.logd("-- 序列号：" + sName);
                            SharedPreferencesUtil.set(AppConstant.PRODUCT_SEQUENCE_Tag, sName);
                            EventBus.getDefault().post(new DevicesInfoEvent().setTag(DevicesInfoEvent.PRODUCT_SEQUENCE_FLAG));
                        } else if (hexStr.contains(AppConstant.PRODUCT_MODELS_FLAG)) {
                            // 设备型号
                            String sName = StringUtils.subProductModelsToSACII(hexStr);
                            LogUtils.logd("-- 型号：" + sName);
                            SharedPreferencesUtil.set(AppConstant.PRODUCT_MODELS_Tag, sName);
                            EventBus.getDefault().post(new DevicesInfoEvent().setTag(DevicesInfoEvent.PRODUCT_MODELS_FLAG));
                        } else if (hexStr.contains(AppConstant.HARD_WARE_VERSION_FLAG)) {
                            // 硬件
                            String sName = StringUtils.subVersionToSACII(AppConstant.HARD_WARE_VERSION_FLAG, hexStr);
                            LogUtils.logd("-- 硬件：" + sName);
                            SharedPreferencesUtil.set(AppConstant.HARD_WARE_Tag, sName);
                            EventBus.getDefault().post(new DevicesInfoEvent().setTag(DevicesInfoEvent.HARD_WARE_VERSION_FLAG));
                        } else if (hexStr.contains(AppConstant.SOFT_WARE_VERSION_FLAG)) {
                            // 软件
                            String sName = StringUtils.subVersionToSACII(AppConstant.SOFT_WARE_VERSION_FLAG, hexStr);
                            LogUtils.logd("-- 软件：" + sName);
                            SharedPreferencesUtil.set(AppConstant.SOFT_WARE_Tag, sName);
                            EventBus.getDefault().post(new DevicesInfoEvent().setTag(DevicesInfoEvent.SOFT_WARE_VERSION_FLAG));
                        } else if (hexStr.contains(AppConstant.FIRM_WARE_VERSION_FLAG)) {
                            // 固件
                            String sName = StringUtils.subVersionToSACII(AppConstant.FIRM_WARE_VERSION_FLAG, hexStr);
                            LogUtils.logd("-- 固件：" + sName);
                            SharedPreferencesUtil.set(AppConstant.FIRM_WARE_Tag, sName);
                            EventBus.getDefault().post(new DevicesInfoEvent().setTag(DevicesInfoEvent.FIRM_WARE_VERSION_FLAG));
                        } else if (hexStr.contains(AppConstant.PROTOCOL_VERSION_FLAG)) {
                            // 协议
                            String sName = StringUtils.subVersionToSACII(AppConstant.PROTOCOL_VERSION_FLAG, hexStr);
                            LogUtils.logd("-- 协议：" + sName);
                            SharedPreferencesUtil.set(AppConstant.PROTOCOL_Tag, sName);
                            EventBus.getDefault().post(new DevicesInfoEvent().setTag(DevicesInfoEvent.PROTOCOL_VERSION_FLAG));
                        } else if (hexStr.contains(AppConstant.ADJUST_FLAG)) {
                            boolean isAdjustTrue = StringUtils.subAdjustHex(AppConstant.ADJUST_FLAG, hexStr);
                            if (isAdjustTrue && showToast) {
                                ToastUtils.showToast(getString(R.string.mine_adjust_true));
                                saveAdjustValue();
                            } else {
                                ToastUtils.showToast(getString(R.string.mine_adjust_false));
                            }
                        }
                        break;
                }
            }
        }
    }

    /**
     * 保存校准值到本地 每个设备对应一个校准值
     */
    private void saveAdjustValue() {
        if (adjustValue != null) {
            SharedPreferencesUtil.set(AppConstant.KEY_ADJUST + getCurentDevice(), adjustValue);
        }
    }

    /**
     * 获取当前连接的设备地址
     *
     * @return
     */
    private String getCurentDevice() {
        return SharedPreferencesUtil.getString(AppConstant.SYS_CUR_DEVICE_ADDRESS, "");
    }


    /**
     * 开始校准
     * 密码
     */
    private void showAdjustPwd() {
        final String adjustPwd = SharedPreferencesUtil.getString(AppConstant.Adjust_Pwd, "");
        if (TextUtils.isEmpty(adjustPwd)) {
            new MaterialDialog.Builder(mActivity)
                    .title(R.string.mine_adjust_set_pwd)
                    .content(R.string.mine_adjust_set_pwd_detail)
                    .positiveText(R.string.confirm)
                    .show();
        } else {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.view_adjust_pwd_dialog, null);
            final AppCompatEditText editText = (AppCompatEditText) view.findViewById(R.id.edt_adjust_pwd_dialog);
            final TextView errTip = (TextView) view.findViewById(R.id.tv_adjust_pwd_tip_dialog);
            final TextView cancel = (TextView) view.findViewById(R.id.tv_adjust_pwd_cancel);
            final TextView confirm = (TextView) view.findViewById(R.id.tv_adjust_pwd_confirm);


            final MaterialDialog materialDialog = new MaterialDialog.Builder(mActivity)
                    .customView(view, false)
                    .title(R.string.app_name)
                    .cancelable(true)
                    .show();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDialog.dismiss();
                }
            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputPwd = editText.getText().toString();
                    if (!TextUtils.equals(adjustPwd, inputPwd)) {
                        errTip.setVisibility(View.VISIBLE);
                    } else {
                        errTip.setVisibility(View.GONE);
                        materialDialog.dismiss();
                        // 开始校准
                        showAdjustPanel();
                    }
                }
            });
        }

    }

    /**
     * 开始校准
     */
    private void showAdjustPanel() {
        if (adjustValue == null) {
            adjustValue = getAdjustValue();
        }
        View view = LayoutInflater.from(mActivity).inflate(R.layout.view_adjust_dialog, null);
        ImageView reduce = (ImageView) view.findViewById(R.id.iv_adjust_reduce);
        ImageView add = (ImageView) view.findViewById(R.id.iv_adjust_add);
        final TextView number = (TextView) view.findViewById(R.id.tv_adjust_count);
        number.setText(adjustValue.split(AppConstant.TEMP_SPLIT)[0]);
        final RelativeLayout confirmRL = (RelativeLayout) view.findViewById(R.id.rl_follow_confirm);
        ImageView reduce2 = (ImageView) view.findViewById(R.id.iv_adjust_reduce2);
        ImageView add2 = (ImageView) view.findViewById(R.id.iv_adjust_add2);
        view.findViewById(R.id.rl_adjust2).setVisibility(isSingleTmp ? View.GONE : View.VISIBLE);
        view.findViewById(R.id.tv_adjust_desc2).setVisibility(isSingleTmp ? View.GONE : View.VISIBLE);
        final TextView number2 = (TextView) view.findViewById(R.id.tv_adjust_count2);
        number2.setText(adjustValue.split(AppConstant.TEMP_SPLIT)[1]);
        final MaterialDialog materialDialog = new MaterialDialog.Builder(mActivity)
                .customView(view, false)
                .show();
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = number.getText().toString().trim();
                if (TextUtils.isDigitsOnly(trim)) {
                    Integer intNumber = Integer.valueOf(trim);
                    intNumber -= 1;
                    if (intNumber < 0) {
                        intNumber += 1;
                        number.setText(String.valueOf(intNumber));
                        return;
                    }
                    number.setText(String.valueOf(intNumber));
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = number.getText().toString().trim();
                if (TextUtils.isDigitsOnly(trim)) {
                    Integer intNumber = Integer.valueOf(trim);
                    intNumber += 1;
                    if (intNumber > 250) {
                        intNumber -= 1;
                        number.setText(String.valueOf(intNumber));
                        return;
                    }
                    number.setText(String.valueOf(intNumber));
                }
            }
        });

        // 校准
        confirmRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustValue = number.getText().toString().trim() + AppConstant.TEMP_SPLIT + number2.getText().toString().trim();
                showToast = true;
                getAdjustInfo(adjustValue);
                materialDialog.dismiss();
            }
        });
        reduce2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = number2.getText().toString().trim();
                if (TextUtils.isDigitsOnly(trim)) {
                    Integer intNumber = Integer.valueOf(trim);
                    intNumber -= 1;
                    if (intNumber < 0) {
                        intNumber += 1;
                        number2.setText(String.valueOf(intNumber));
                        return;
                    }
                    number2.setText(String.valueOf(intNumber));
                }
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = number2.getText().toString().trim();
                if (TextUtils.isDigitsOnly(trim)) {
                    Integer intNumber = Integer.valueOf(trim);
                    intNumber += 1;
                    if (intNumber > 250) {
                        intNumber -= 1;
                        number2.setText(String.valueOf(intNumber));
                        return;
                    }
                    number2.setText(String.valueOf(intNumber));
                }
            }
        });

    }

    @NonNull
    private String getAdjustValue() {
        return SharedPreferencesUtil.getString(AppConstant.KEY_ADJUST + getCurentDevice(), "125/125");
    }

    public void testDB(final int i) {
        //        if (BuildConfig.DEBUG) {
        //            List<String> data = new ArrayList<>();
        //            data.add("39.");
        //            data.add("" + 6);
        //            addTempDataToDB("123131", data);
        //
        //        }
        i1 = i;
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (i1 == 0) {
                            return;
                        }
                        ArrayList<String> tempData = new ArrayList<>();
                        tempData.add((int) (Math.random() * 3) + 35 + "");
                        tempData.add(".37");
                        if (i1 == 2) {
                            tempData.add("00");
                            tempData.add(".00");
                        } else {
                            tempData.add((int) (Math.random() * 3) + 34 + "");
                            tempData.add(".39");
                        }
                        TemperaturesBean tempBean = getTempBean("1231", tempData);
                        setTempResultList(tempBean);
                        setTempDataToView(tempData);
                    }
                });

    }
}
