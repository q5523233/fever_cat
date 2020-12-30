package com.nepo.fevercat.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nepo.fevercat.R;
import com.nepo.fevercat.common.utils.ConstantUtils;

import java.text.DecimalFormat;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sm on 2019/3/18.
 */

public class RealTimeResultView extends RelativeLayout {

    int tempTextsize;//整数部分字体大小
    int tempFloatTextsize;//小数部分字体大小
    int realTimeTempTextsize;
    @BindView(R.id.iv_bg_rotate)
    ImageView ivBgRotate;
    @BindView(R.id.ll_real_time_oval_float_rotate)
    LinearLayout llRealTimeOvalFloatRotate;
    @BindView(R.id.tv_real_time_oval_float_temp_num_integer)
    TextView tvRealTimeOvalFloatTempNumInteger;
    @BindView(R.id.tv_real_time_oval_float_temp_num_decimal)
    TextView tvRealTimeOvalFloatTempNumDecimal;
    @BindView(R.id.tv_real_time_oval_float_temp_num_unit)
    TextView tvRealTimeOvalFloatTempNumUnit;
    @BindView(R.id.ll_normal_temp_contain)
    RelativeLayout llNormalTempContain;
    @BindView(R.id.ll_err_temp_contain)
    LinearLayout llErrTempContain;
    @BindView(R.id.ll_real_time_oval_float_temp)
    LinearLayout llRealTimeOvalFloatTemp;
    @BindView(R.id.rl_real_time_oval_float_ball)
    RelativeLayout rlRealTimeOvalFloatBall;
    private Animation animation;

    public RealTimeResultView(Context context) {
        super(context);
    }

    public RealTimeResultView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RealTimeResultView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View rootView = View.inflate(context, R.layout.realtime_result_ball, this);
        ButterKnife.bind(this);
        TypedArray ta = context.getResources().obtainAttributes(attrs, R.styleable.RealTimeResultView);
        tempTextsize = ta.getDimensionPixelSize(R.styleable.RealTimeResultView_tmp_size, 20);
        tempFloatTextsize = ta.getDimensionPixelSize(R.styleable.RealTimeResultView_tmp_float_size, 14);
        realTimeTempTextsize = ta.getDimensionPixelSize(R.styleable.RealTimeResultView_realTimeTempTextsize, 12);
        tvRealTimeOvalFloatTempNumInteger.setTextSize(tempTextsize);
        tvRealTimeOvalFloatTempNumDecimal.setTextSize(tempFloatTextsize);
        ta.recycle();
    }


    public void startAnimation() {
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.img_animation);
        animation.setInterpolator(new LinearInterpolator());
        ivBgRotate.setAnimation(animation);
        animation.start();
    }

    //设置温度值
    private void setTmp(String sTemp) {

        if (sTemp.contains(".")) {
            String subHexInt = sTemp.substring(0, sTemp.lastIndexOf("."));
            String subHexDecimal = sTemp.substring(sTemp.lastIndexOf("."), sTemp.length());
            tvRealTimeOvalFloatTempNumInteger.setText(subHexInt);
            tvRealTimeOvalFloatTempNumDecimal.setText(subHexDecimal);
        }
    }

    public void setResult(String sTemp, boolean isDelta) {
        // 小温度
        DecimalFormat df = new DecimalFormat("00.00");
        float dTemp = Float.valueOf(sTemp);
        if (isDelta) {
            setTmp(dTemp + "");
        } else if (dTemp >= 25 && dTemp <= 45) {
            setTmp(ConstantUtils.IsCelsiusUnit(df.format(dTemp)));
        }else {
            setTmp("--.--");
        }
        // 温度背景
        dTemp = Math.max(Math.min(45, dTemp), 35);
        Drawable drawable = ConstantUtils.CheckTempLimitFloatBg((double) dTemp);
        rlRealTimeOvalFloatBall.setBackground(drawable);
    }

    public void setResult(String sTemp) {
        setResult(sTemp, false);
    }

    public void reset() {
        rlRealTimeOvalFloatBall.setBackground(getResources().getDrawable(R.drawable.icon_real_time_float_bg));
        setTmp("--.--");
//        if (animation!=null)
//        {
//            animation.cancel();
//        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animation!=null)
        {
            animation.cancel();
        }
    }
}
