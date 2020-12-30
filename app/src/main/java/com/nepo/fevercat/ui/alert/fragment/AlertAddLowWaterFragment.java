package com.nepo.fevercat.ui.alert.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
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
import com.nepo.fevercat.base.BaseFragment;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.GreenDaoAlertWaterUtils;
import com.nepo.fevercat.common.widget.popup.StrSelectPopupWindow;
import com.nepo.fevercat.ui.alert.AlertRingActivity;
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
 * 包名 :  com.nepo.fevercat.ui.alert.fragment
 * 文件名:  AlertAddLowWaterFragment
 * 作者 :   <sen>
 * 时间 :  下午5:11 2017/6/29.
 * 描述 :  喝水提醒
 */

public class AlertAddLowWaterFragment extends BaseFragment implements StrSelectPopupWindow.OnSelectItemListen {

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


    @Override
    protected int getLayoutResource() {
        return R.layout.frag_alert_add_low_water;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

        initDataList();
        mWaterSelectPopupWindow = new StrSelectPopupWindow(mActivity, mWaterList);
        mRepeatSelectPopupWindow = new StrSelectPopupWindow(mActivity, mRepeatList);
        mWaterSelectPopupWindow.setOnSelectItemListen(this);
        mRepeatSelectPopupWindow.setOnSelectItemListen(this);
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
        mRepeatList.add("30分钟");
        mRepeatList.add("1小时");
        mRepeatList.add("1.5小时");
        mRepeatList.add("2小时");
        mRepeatList.add("2.5小时");
        mRepeatList.add("3小时");
        mRepeatList.add("4小时");
        mRepeatList.add("5小时");
        mRepeatList.add("6小时");

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

        pvStartCustomTime = new TimePickerView.Builder(mActivity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
                SimpleDateFormat sdfSecond = new SimpleDateFormat("mm");
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
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF4f9dff)
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

        pvEndCustomTime = new TimePickerView.Builder(mActivity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
                SimpleDateFormat sdfSecond = new SimpleDateFormat("mm");
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
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF4f9dff)
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
        startActivityForResult(AlertRingActivity.newIntent(mActivity), Low_Add_Ring_Code);
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

        // 添加降温-用药 提醒
        AlertLowBean alertLowBean = new AlertLowBean();
        alertLowBean.setMAlertStatus(AppConstant.ALERT_WATER_STATUS);
        alertLowBean.setMIsEnabledAlert(true);
        alertLowBean.setMWaterBeginTime(mSelectBeginTime);
        alertLowBean.setMWaterBeginTimeConvert(mSelectBeginTimeConvert);
        alertLowBean.setMWaterEndTime(mSelectEndTime);
        alertLowBean.setMWaterEndTimeConvert(mSelectEndTimeConvert);
        alertLowBean.setMWaterUnits(mSelectWater);
        alertLowBean.setMWaterInterval(mSelectInterval);
        alertLowBean.setMWaterIntervalMilliSecond(mSelectIntervalMillion);
        // 保存记录
        GreenDaoAlertWaterUtils.getInstance().insertAlertLowBean(alertLowBean);

        mActivity.finish();

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

        if (TextUtils.isEmpty(mSelectBeginTime)
                ) {
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
