package com.nepo.fevercat.ui.alert;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.GreenDaoAlertWaterUtils;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.widget.popup.StrSelectPopupWindow;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert
 * 文件名:  AlertLowAddWaterActivity
 * 作者 :   <sen>
 * 时间 :  下午4:59 2017/9/14.
 * 描述 :  低温喝水
 */

public class AlertLowAddWaterActivity extends BaseActivity implements StrSelectPopupWindow.OnSelectItemListen {


    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.tv_item_start_time_start)
    TextView tvItemStartTimeStart;
    @BindView(R.id.tv_item_water_time_end)
    TextView tvItemWaterTimeEnd;
    @BindView(R.id.tv_frag_add_water_quantity)
    TextView tvFragAddWaterQuantity;
    @BindView(R.id.tv_frag_add_water_time_interval)
    TextView tvFragAddWaterTimeInterval;
    @BindView(R.id.tv_frag_add_water_ring)
    TextView tvFragAddWaterRing;
    @BindView(R.id.ll_alert_add_water_contain)
    LinearLayout llAlertAddWaterContain;
    @BindView(R.id.tv_alert_low_water_select_err_hint)
    TextView tvAlertLowWaterSelectErrHint;


    private String mSelectType;

    private final int Low_Add_Ring_Code = 6470;

    public static final String Low_Add_Intent_Flag = "Low_Add_Intent_Flag";

    private StrSelectPopupWindow mWaterSelectPopupWindow;
    private StrSelectPopupWindow mRepeatSelectPopupWindow;

    private AnimatorSet mAnimatorSet;

    List<String> mWaterList;
    List<String> mRepeatList;

    private String mSelectBeginTime,mSelectBeginTimeConvert,mSelectEndTime,mSelectEndTimeConvert,mSelectWater,mSelectInterval,mSelectIntervalMillion;

    private com.bigkoo.pickerview.TimePickerView pvStartCustomTime;
    private com.bigkoo.pickerview.TimePickerView pvEndCustomTime;
    private int starHour;
    private int starSecond;
    private int endHour;
    private int endSecond;
    private boolean isEdit;
    private AlertLowBean mAlertLowBeanBundle;


    public static Intent newIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, AlertLowAddWaterActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_alert_add_low_water;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        HideUtil.init(this);
        tvTopBarTitle.setText(getString(R.string.alert_add_low_nav_water));
        initData();
        initDataList();
        mWaterSelectPopupWindow = new StrSelectPopupWindow(mContext, mWaterList);
        mRepeatSelectPopupWindow = new StrSelectPopupWindow(mContext, mRepeatList);
        mWaterSelectPopupWindow.setOnSelectItemListen(this);
        mRepeatSelectPopupWindow.setOnSelectItemListen(this);
    }
    private void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAlertLowBeanBundle = (AlertLowBean) extras.getSerializable(Low_Add_Intent_Flag);
            if (mAlertLowBeanBundle != null) {
                isEdit=true;
                //开始时间
                tvItemStartTimeStart.setText(mAlertLowBeanBundle.getMWaterBeginTime());
                mSelectBeginTime=mAlertLowBeanBundle.getMWaterBeginTime();
                mSelectBeginTimeConvert =mAlertLowBeanBundle.getMWaterBeginTimeConvert();
                //结束
                tvItemWaterTimeEnd.setText(mAlertLowBeanBundle.getMWaterEndTime());
                mSelectEndTime=mAlertLowBeanBundle.getMWaterEndTime();
                mSelectEndTimeConvert =mAlertLowBeanBundle.getMWaterEndTimeConvert();
                // 喝水
                tvFragAddWaterQuantity.setText(mAlertLowBeanBundle.getMWaterUnits());
                mSelectWater = mAlertLowBeanBundle.getMWaterUnits();
                //提醒间隔
                tvFragAddWaterTimeInterval.setText(mAlertLowBeanBundle.getMWaterInterval());
                mSelectInterval = mAlertLowBeanBundle.getMWaterInterval();
                mSelectIntervalMillion = mAlertLowBeanBundle.getMWaterIntervalMilliSecond();
            }
        }else {
            mAlertLowBeanBundle=new AlertLowBean();
        }
    }

    /**
     * 返回
     */
    @OnClick(R.id.rl_top_bar_back)
    public void onBackClick() {
        finish();
    }

    /**
     * 初始化数据
     */
    private void initDataList() {
        mWaterList = new ArrayList<>();
        mRepeatList = new ArrayList<>();


        //
        mWaterList.add("150ml");
        mWaterList.add("200ml");
        mWaterList.add("250ml");
        mWaterList.add("300ml");
        mWaterList.add("350ml");
        mWaterList.add("400ml");
        mWaterList.add("450ml");
        mWaterList.add("500ml");
        //
        mRepeatList.add(getString(R.string.minutes));
        mRepeatList.add(getString(R.string.one_hour));
        mRepeatList.add(getString(R.string.one_half_hour));
        mRepeatList.add(getString(R.string.two_hour));
        mRepeatList.add(getString(R.string.two_half_hour));
        mRepeatList.add(getString(R.string.three_hour));
        mRepeatList.add(getString(R.string.four_hour));
        mRepeatList.add(getString(R.string.five_hour));
        mRepeatList.add(getString(R.string.six_hour));

    }


    /**
     * 开始时间
     */
    @OnClick(R.id.rl_item_water_time_start)
    public void onStartTimeClick() {


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);

        pvStartCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
                SimpleDateFormat sdfSecond = new SimpleDateFormat("mm");
                try{
                    starHour = Integer.parseInt(sdfHour.format(date));
                    starSecond = Integer.parseInt(sdfSecond.format(date));

                }catch (NumberFormatException e){
                    e.printStackTrace();
                }

                tvItemStartTimeStart.setText(sdf.format(date));
                mSelectBeginTime=sdf.format(date);
                mSelectBeginTimeConvert = sdfHour.format(date)+sdfSecond.format(date);

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartCustomTime.returnData();
                                pvStartCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel(getString(R.string.year), getString(R.string.month), getString(R.string.day), getString(R.string.hour), getString(R.string.minute), getString(R.string.second))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF4f9dff)
                .isCyclic(true)
                .build();

        pvStartCustomTime.show();



    }

    /**
     * 结束时间
     */
    @OnClick(R.id.rl_item_water_time_end)
    public void onEndTimeClick() {

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);

        pvEndCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
                SimpleDateFormat sdfSecond = new SimpleDateFormat("mm");
                try{
                    endHour = Integer.parseInt(sdfHour.format(date));
                    endSecond = Integer.parseInt(sdfSecond.format(date));

                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
                tvItemWaterTimeEnd.setText(sdf.format(date));
                mSelectEndTime=sdf.format(date);
                mSelectEndTimeConvert = sdfHour.format(date)+sdfSecond.format(date);

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvEndCustomTime.returnData();
                                pvEndCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvEndCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel(getString(R.string.year), getString(R.string.month), getString(R.string.day), getString(R.string.hour), getString(R.string.minute), getString(R.string.second))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF4f9dff)
                .isCyclic(true)
                .build();

        pvEndCustomTime.show();

    }



    /**
     * 喝水量
     */
    @OnClick(R.id.rl_frag_add_water_quantity)
    public void onWaterQuantityClick() {
        mSelectType = "0";
        mWaterSelectPopupWindow.showAtLocation(llAlertAddWaterContain, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 提醒间隔
     */
    @OnClick(R.id.rl_frag_add_water_time_interval)
    public void onWaterIntervalClick() {
        mSelectType = "1";
        mRepeatSelectPopupWindow.showAtLocation(llAlertAddWaterContain, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 提醒铃声
     */
    @OnClick(R.id.rl_frag_add_water_ring)
    public void onWaterRingClick() {
        startActivityForResult(AlertRingActivity.newIntent(mContext), Low_Add_Ring_Code);
    }

    /**
     * 保存
     */
    @OnClick(R.id.rl_frag_add_water_confirm)
    public void onWaterConfirmClick() {
        if (TextUtils.isEmpty(mSelectBeginTime)
                || TextUtils.isEmpty(mSelectEndTime)
                || TextUtils.isEmpty(mSelectWater)
                || TextUtils.isEmpty(mSelectInterval)
                ) {
            shakeAnimate();
            return;
        }
        if(starHour!=0&&endHour!=0){
            if(endHour==starHour&&endSecond<=starSecond){
                shakeAnimate();
                return;
            }else if((endHour!=0&&endHour<starHour)){
                shakeAnimate();
                return;
            }
        }

        // 添加降温-用药 提醒
        mAlertLowBeanBundle.setMAlertStatus(AppConstant.ALERT_WATER_STATUS);
        mAlertLowBeanBundle.setMIsEnabledAlert(true);
        mAlertLowBeanBundle.setMWaterBeginTime(mSelectBeginTime);
        mAlertLowBeanBundle.setMWaterBeginTimeConvert(mSelectBeginTimeConvert);
        mAlertLowBeanBundle.setMWaterEndTime(mSelectEndTime);
        mAlertLowBeanBundle.setMWaterEndTimeConvert(mSelectEndTimeConvert);
        mAlertLowBeanBundle.setMWaterUnits(mSelectWater);
        mAlertLowBeanBundle.setMWaterInterval(mSelectInterval);
        mAlertLowBeanBundle.setMWaterIntervalMilliSecond(mSelectIntervalMillion);
        // 保存记录
        if (isEdit){
            GreenDaoAlertWaterUtils.getInstance().updateAlertLowBean(mAlertLowBeanBundle);
        }else {
            GreenDaoAlertWaterUtils.getInstance().insertAlertLowBean(mAlertLowBeanBundle);
        }

        finish();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String stringExtra = data.getStringExtra(Low_Add_Intent_Flag);
            if (requestCode == Low_Add_Ring_Code) {
                // 提醒铃声
                tvFragAddWaterRing.setText(stringExtra);
            }
        }

    }


    @Override
    public void selectStrItem(String content) {
        if (TextUtils.equals(mSelectType, "0")) {
            // 喝水
            tvFragAddWaterQuantity.setText(content);
            mSelectWater = content;
        } else {
            // 重复天数
            tvFragAddWaterTimeInterval.setText(content);
            mSelectInterval = content;
            int indexOf = mRepeatList.indexOf(content);
            String selectMilliSecond =  ConstantUtils.getSelectMilliSecond(indexOf);
            mSelectIntervalMillion = selectMilliSecond;

        }
    }


    /**
     * 错误动画
     */
    private void shakeAnimate() {
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }

        if (TextUtils.isEmpty(mSelectBeginTime)) {
            tvAlertLowWaterSelectErrHint.setText(getString(R.string.alert_low_water_begin_select_err));
        }else if(TextUtils.isEmpty(mSelectEndTime)){
            tvAlertLowWaterSelectErrHint.setText(getString(R.string.alert_low_water_end_select_err));
        }else if(TextUtils.isEmpty(mSelectWater)){
            tvAlertLowWaterSelectErrHint.setText(getString(R.string.alert_low_water_ml_select_err));
        }else if(TextUtils.isEmpty(mSelectInterval)){
            tvAlertLowWaterSelectErrHint.setText(getString(R.string.alert_low_water_interval_select_err));
        }else{
            tvAlertLowWaterSelectErrHint.setText(getString(R.string.select_err));
        }



        tvAlertLowWaterSelectErrHint.setVisibility(View.VISIBLE);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(tvAlertLowWaterSelectErrHint, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0));
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvAlertLowWaterSelectErrHint.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimatorSet.start();


    }
}
