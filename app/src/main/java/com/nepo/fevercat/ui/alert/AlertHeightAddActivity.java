package com.nepo.fevercat.ui.alert;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.DisplayMetricsUtil;
import com.nepo.fevercat.common.utils.GreenDaoAlertHeightUtils;
import com.nepo.fevercat.common.utils.Utils;
import com.nepo.fevercat.common.widget.popup.StrSelectPopupWindow;
import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert
 * 文件名:  AlertHeightAddActivity
 * 作者 :   <sen>
 * 时间 :  上午10:32 2017/6/29.
 * 描述 :
 */

public class AlertHeightAddActivity extends BaseActivity implements StrSelectPopupWindow.OnSelectItemListen {


    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.tv_alert_add_water_tip)
    TextView tvAlertAddWaterTip;
    @BindView(R.id.tv_alert_add_repeat_tip)
    TextView tvAlertAddRepeatTip;
    @BindView(R.id.ll_alert_add_height_contain)
    LinearLayout llAlertAddHeightContain;
    @BindView(R.id.picker_alert_add_height)
    WheelPicker pickerAlertAddHeight;
    @BindView(R.id.tv_alert_add_height_status)
    TextView tvAlertAddHeightStatus;
    @BindView(R.id.rl_alert_add_height_status)
    RelativeLayout rlAlertAddHeightStatus;
    @BindView(R.id.tv_alert_height_select_err_hint)
    TextView tvAlertHeightSelectErrHint;


    List<String> mWaterList;
    List<String> mRepeatList;
    //    List<String> mTempList;
    List<String> mTempIntList;


    private String mSelectType;
    private String mSelectContent;
    private int mSelectTempPosition;

    private StrSelectPopupWindow mWaterSelectPopupWindow;
    private StrSelectPopupWindow mRepeatSelectPopupWindow;
    private AnimatorSet mAnimatorSet;

    private AlertHeightBean mAlertHeightBeanBundle;

    public static final String Alert_Height_Bundle_Flag = "Alert_Height_Bundle_Flag";
    public static final String Baby_Bundle_Flag = "Baby_Bundle_Flag";
    private BabyInfosBean babyInfosBean;

    public static Intent newIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, AlertHeightAddActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_alert_height;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTopBarTitle.setText(getString(R.string.alert_add_height_title));
        initData();
        initDataList();
        mWaterSelectPopupWindow = new StrSelectPopupWindow(mContext, mWaterList);
        mRepeatSelectPopupWindow = new StrSelectPopupWindow(mContext, mRepeatList);
        mWaterSelectPopupWindow.setOnSelectItemListen(this);
        mRepeatSelectPopupWindow.setOnSelectItemListen(this);
        pickerAlertAddHeight.setData(mTempIntList);
        pickerAlertAddHeight.setItemTextSize(DisplayMetricsUtil.sp2px(this,25));
        pickerAlertAddHeight.setSelectedItemTextColor(ContextCompat.getColor(mContext, R.color.color_11));
        if (mAlertHeightBeanBundle != null) {
            pickerAlertAddHeight.setSelectedItemPosition(Integer.valueOf(mAlertHeightBeanBundle.getSSelectType()));
            changeSelectTemp(Integer.valueOf(mAlertHeightBeanBundle.getSSelectType()));
            if (TextUtils.equals(mAlertHeightBeanBundle.getSSelectType(), "0")) {
                pickerAlertAddHeight.setSelectedItemTextColor(ContextCompat.getColor(mContext, R.color.color_11));
            } else if (TextUtils.equals(mAlertHeightBeanBundle.getSSelectType(), "1")) {
                pickerAlertAddHeight.setSelectedItemTextColor(ContextCompat.getColor(mContext, R.color.color_13));
            } else {
                pickerAlertAddHeight.setSelectedItemTextColor(ContextCompat.getColor(mContext, R.color.color_14));
            }
        } else {
            pickerAlertAddHeight.setSelectedItemPosition(0);
            changeSelectTemp(0);
        }

        pickerAlertAddHeight.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                changeSelectTemp(position);
            }
        });
    }


    private void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAlertHeightBeanBundle = (AlertHeightBean) extras.getSerializable(Alert_Height_Bundle_Flag);
            if (mAlertHeightBeanBundle != null) {
                tvAlertAddRepeatTip.setText(mAlertHeightBeanBundle.getSTimeTip());
                mSelectContent = mAlertHeightBeanBundle.getSTimeTip();
            }
            babyInfosBean = (BabyInfosBean) extras.getSerializable(Baby_Bundle_Flag);
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
     * 喝水量
     */
    @OnClick(R.id.rl_alert_add_water)
    public void onWaterClick() {
        mSelectType = "0";
        mWaterSelectPopupWindow.showAtLocation(llAlertAddHeightContain, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 重复天数
     */
    @OnClick(R.id.rl_alert_add_repeat)
    public void onRepeatClick() {
        mSelectType = "1";
        mRepeatSelectPopupWindow.showAtLocation(llAlertAddHeightContain, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 保存
     */
    @OnClick(R.id.rl_alert_add_height_confirm)
    public void onConfirmClick() {
        if (TextUtils.isEmpty(mSelectContent)) {
            shakeAnimate();
            return;
        }
        // 编辑
        if (mAlertHeightBeanBundle != null) {
            if (mSelectTempPosition == 0) {
                mAlertHeightBeanBundle.setSGtTemp("37.50");
                mAlertHeightBeanBundle.setSLeTemp("38.00");
                mAlertHeightBeanBundle.setSTemp("37.50~37.99");
                mAlertHeightBeanBundle.setSSelectType("0");
            } else if (mSelectTempPosition == 1) {
                mAlertHeightBeanBundle.setSGtTemp("38.00");
                mAlertHeightBeanBundle.setSLeTemp("39.00");
                mAlertHeightBeanBundle.setSTemp("38.00~38.99");
                mAlertHeightBeanBundle.setSSelectType("1");
            } else if (mSelectTempPosition == 2) {
                mAlertHeightBeanBundle.setSGtTemp("39.00");
                mAlertHeightBeanBundle.setSLeTemp("40.00");
                mAlertHeightBeanBundle.setSTemp("39.00~");
                mAlertHeightBeanBundle.setSSelectType("2");
            }

            //添加到数据库
            int indexOf = mRepeatList.indexOf(mSelectContent);
            String selectMilliSecond = ConstantUtils.getSelectMilliSecond(indexOf);
            mAlertHeightBeanBundle.setSTimeTip(mSelectContent);
            mAlertHeightBeanBundle.setSMillisecond(selectMilliSecond);
            mAlertHeightBeanBundle.setSCheckStatus(true);
            //编辑高温提醒
            GreenDaoAlertHeightUtils.getInstance().updateAlertHeight(mAlertHeightBeanBundle);
            finish();
            return;
        }

        // 新增
        AlertHeightBean alertHeightBean = new AlertHeightBean();
        // 温度
        if (mSelectTempPosition == 0) {
            // 低热
            alertHeightBean.setSGtTemp("37.50");
            alertHeightBean.setSLeTemp("38.00");
            alertHeightBean.setSTemp("37.50~37.99");
            alertHeightBean.setSSelectType("0");
        } else if (mSelectTempPosition == 1) {
            // 中热
            alertHeightBean.setSGtTemp("38.00");
            alertHeightBean.setSLeTemp("39.00");
            alertHeightBean.setSTemp("38.00~38.99");
            alertHeightBean.setSSelectType("1");
        } else if (mSelectTempPosition == 2) {
            // 高热
            alertHeightBean.setSGtTemp("39.00");
            alertHeightBean.setSLeTemp("40.00");
            alertHeightBean.setSTemp("39.00~");
            alertHeightBean.setSSelectType("2");
        }
        //添加到数据库
        int indexOf = mRepeatList.indexOf(mSelectContent);
        String selectMilliSecond = ConstantUtils.getSelectMilliSecond(indexOf);
        alertHeightBean.setSTimeTip(mSelectContent);
        alertHeightBean.setSMillisecond(selectMilliSecond);
        alertHeightBean.setSCheckStatus(true);
        alertHeightBean.setBelongBaby(babyInfosBean.getId()+"");
        //添加高温提醒
        boolean isSuccess = GreenDaoAlertHeightUtils.getInstance().insertAlertHeight(alertHeightBean);
        if (!isSuccess) {
            // 提示有重复数据
            tvAlertHeightSelectErrHint.setText(getString(R.string.alert_add_repeat_tip));
            shakeAnimate();
            return;
        }
        finish();

    }


    /**
     * 初始化数据
     */
    private void initDataList() {
        mTempIntList = new ArrayList<>();
        mWaterList = new ArrayList<>();
        mRepeatList = new ArrayList<>();
        //
        mTempIntList.add(ConstantUtils.ConvertStrToFahrenheit("37.50~37.99"));
        mTempIntList.add(ConstantUtils.ConvertStrToFahrenheit("38.00~38.99"));
        mTempIntList.add(ConstantUtils.ConvertStrToFahrenheit("39.00~"));

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
     * 选中的温度
     */
    private void changeSelectTemp(int position) {
//        String textStr = ConstantUtils.CheckTempLimitToStr(dTemp);
//        tvAlertAddHeightStatus.setText(textStr);
//        Drawable bgStr = ConstantUtils.CheckTempLimitToBg(dTemp);
//        rlAlertAddHeightStatus.setBackground(bgStr);
//        int colorStr = ConstantUtils.CheckTempLimitToColor(dTemp);
//        pickerAlertAddHeight.setSelectedItemTextColor(colorStr);
//        mSelectTemp = String.valueOf(dTemp);
        mSelectTempPosition = position;
        if (mSelectTempPosition == 0) {
            // 低热
            Drawable bgStr = ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_low_bg);
            rlAlertAddHeightStatus.setBackground(bgStr);
            int colorStr = ContextCompat.getColor(Utils.getContext(), R.color.color_11);
            pickerAlertAddHeight.setSelectedItemTextColor(colorStr);
            tvAlertAddHeightStatus.setText(Utils.getContext().getString(R.string.temp_status_low));
        } else if (mSelectTempPosition == 1) {
            // 中热
            Drawable bgStr = ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_middle_bg);
            rlAlertAddHeightStatus.setBackground(bgStr);
            int colorStr = ContextCompat.getColor(Utils.getContext(), R.color.color_13);
            pickerAlertAddHeight.setSelectedItemTextColor(colorStr);
            tvAlertAddHeightStatus.setText(Utils.getContext().getString(R.string.temp_status_middle));
        } else if (mSelectTempPosition == 2) {
            // 高热
            Drawable bgStr = ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_hot_bg);
            rlAlertAddHeightStatus.setBackground(bgStr);
            int colorStr = ContextCompat.getColor(Utils.getContext(), R.color.color_14);
            pickerAlertAddHeight.setSelectedItemTextColor(colorStr);
            tvAlertAddHeightStatus.setText(Utils.getContext().getString(R.string.temp_status_hot));
        }
    }


    /**
     * 错误动画
     */
    private void shakeAnimate() {
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }

        tvAlertHeightSelectErrHint.setVisibility(View.VISIBLE);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(tvAlertHeightSelectErrHint, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0));
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvAlertHeightSelectErrHint.setVisibility(View.GONE);
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


    @Override
    public void selectStrItem(String content) {
        if (TextUtils.equals(mSelectType, "0")) {
            // 喝水
            tvAlertAddWaterTip.setText(content);
        } else {
            // 重复天数
            tvAlertAddRepeatTip.setText(content);
            mSelectContent = content;
        }
    }


}
