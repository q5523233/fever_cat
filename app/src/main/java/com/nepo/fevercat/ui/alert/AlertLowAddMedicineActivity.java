package com.nepo.fevercat.ui.alert;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.GreenDaoAlertMedicineUtils;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.widget.CustomLinearLayoutManager;
import com.nepo.fevercat.event.AlertMedicineEvent;
import com.nepo.fevercat.ui.alert.adapter.AlertMedicineTimeAdapter;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;
import com.nepo.fevercat.ui.alert.bean.AlertMedicineTimeBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import org.greenrobot.eventbus.EventBus;

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
 * 文件名:  AlertLowAddMedicineActivity
 * 作者 :   <sen>
 * 时间 :  下午4:58 2017/9/14.
 * 描述 :  低温用药
 */

public class AlertLowAddMedicineActivity extends BaseActivity {


    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.tv_frag_add_medicine)
    TextView tvFragAddMedicine;
    @BindView(R.id.tv_frag_add_medicine_repeat)
    TextView tvFragAddMedicineRepeat;
    @BindView(R.id.recycle_frag_add_medicine)
    RecyclerView recycleFragAddMedicine;
    @BindView(R.id.tv_alert_low_medicine_select_err_hint)
    TextView tvAlertLowMedicineSelectErrHint;
    @BindView(R.id.edt_alert_medicine_title)
    EditText edtAlertMedicineTitle;
    @BindView(R.id.ll_medicine_contain)
    LinearLayout llMedicineContain;
    private com.bigkoo.pickerview.TimePickerView pvCustomTime;


    List<AlertMedicineTimeBean> mAlertMedicineTimeBeanList;
    AlertMedicineTimeAdapter mAlertMedicineTimeAdapter;
    private final int Low_Add_Medicine_Code = 273;
    private final int Low_Add_Repeat_Code = 837;
    private final int Low_Add_Ring_Code = 647;

    public static final String Low_Add_Intent_Flag = "Low_Add_Intent_Flag";
    public static final String Low_Add_Code_Intent_Flag = "Low_Add_Code_Intent_Flag";
    public static final String Low_Add_Baby_Bundle_Flag = "Low_Add_Baby_Bundle_Flag";



    private AnimatorSet mAnimatorSet;

    private String mSelectMedicineName; // 药品名称
    private String mSelectRepeatTime; // 重复天数
    private String mSelectRepeatCodeTime; // 重复天数 code
    private BabyInfosBean babyInfosBean;




    public static Intent newIntent(Context context,Bundle bundle) {
        Intent intent = new Intent(context, AlertLowAddMedicineActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }



    @Override
    public int getLayoutId() {
        return R.layout.frag_alert_add_low_medicine;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        HideUtil.init(this);
        tvTopBarTitle.setText(getString(R.string.alert_add_low_nav_medicine));
        recycleFragAddMedicine.setHasFixedSize(true);
        CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(mContext, 4);
        recycleFragAddMedicine.setLayoutManager(customLinearLayoutManager);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            babyInfosBean = (BabyInfosBean) extras.getSerializable(Low_Add_Baby_Bundle_Flag);
        }
        Log.e("Tag", "initView: "+babyInfosBean.toString() );
        initTimeAdapter();

    }


    /**
     * 返回
     */
    @OnClick(R.id.rl_top_bar_back)
    public void onBackClick() {
        finish();
    }



    /**
     * 用药时间
     */
    private void initTimeAdapter() {
        initMedicineTime();

        mAlertMedicineTimeAdapter = new AlertMedicineTimeAdapter(mAlertMedicineTimeBeanList);
        mAlertMedicineTimeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recycleFragAddMedicine.setAdapter(mAlertMedicineTimeAdapter);
        mAlertMedicineTimeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AlertMedicineTimeBean alertMedicineTimeBean = (AlertMedicineTimeBean) adapter.getItem(position);
                if (alertMedicineTimeBean.isDefaultAdd()) {
                    // 添加时间
                    if (view.getId() == R.id.ll_item_medicine_time_contain) {
                        // 打开弹窗
                        selectMedicineHour(adapter, position);
                    }
                }

            }
        });
    }

    /**
     * 选择吃药时间
     */
    private void selectMedicineHour(final BaseQuickAdapter adapter, final int position) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        pvCustomTime =  new com.bigkoo.pickerview.TimePickerView.Builder(mContext, new com.bigkoo.pickerview.TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // btn_CustomTime.setText(getTime(date));
                String format = getTime(date);
                List<AlertMedicineTimeBean> data = adapter.getData();
                boolean isContain = false;
                for (AlertMedicineTimeBean alertMedicineTimeBean : data) {
                    if (TextUtils.equals(alertMedicineTimeBean.getTimeStr(), format)) {
                        isContain = true;
                    }
                }

                if (isContain) {
                    ToastUtils.showToast(getString(R.string.alert_add_repeat_tip));
                    return;
                }

                adapter.getData().add(getLastIndex(), new AlertMedicineTimeBean()
                        .setTimeStr(format)
                        .setDefaultAdd(false));
                adapter.notifyItemChanged(position);
                if (adapter.getItemCount() > 8) {
                    adapter.getData().remove(8);
                }

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
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel(getString(R.string.year),getString(R.string.month),getString(R.string.day),getString(R.string.hour),getString(R.string.minute),getString(R.string.second))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF4f9dff)
                .isCyclic(true)
                .build();
        pvCustomTime.show(); //弹出自定义时间选择器
    }





    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }


    /**
     * 初始化吃药时间
     */
    private void initMedicineTime() {
        mAlertMedicineTimeBeanList = new ArrayList<>();
        mAlertMedicineTimeBeanList.add(new AlertMedicineTimeBean().setDefaultAdd(true));
    }


    /**
     * 获取最后下标
     *
     * @return
     */
    private int getLastIndex() {
        int size = mAlertMedicineTimeBeanList.size();
        if (size != 0) {
            return size - 1;
        }
        return 0;
    }

    /**
     * 药品名称
     */
    @OnClick(R.id.rl_frag_add_medicine)
    public void onAddMedicineClick() {
        //startActivityForResult(AlertMedicineActivity.newIntent(mActivity), Low_Add_Medicine_Code);
    }

    /**
     * 重复天数
     */
    @OnClick(R.id.rl_frag_add_medicine_repeat)
    public void onAddRepeatClick() {
        Bundle bundle = new Bundle();
        bundle.putString(AlertRepeatDayActivity.Intent_Repeat_Day_Code_Flag, mSelectRepeatCodeTime);
        startActivityForResult(AlertRepeatDayActivity.newIntent(mContext, bundle), Low_Add_Repeat_Code);
    }


    /**
     * 恢复初始设置
     */
    @OnClick(R.id.tv_frag_add_medicine_time_init)
    public void onAddInitClick() {
        initTimeAdapter();
    }


    /**
     * 保存
     */
    @OnClick(R.id.rl_frag_add_medicine_confirm)
    public void onAddConfirmClick() {

        mSelectMedicineName = edtAlertMedicineTitle.getText().toString().trim();

        if (TextUtils.isEmpty(mSelectMedicineName)) {
            tvAlertLowMedicineSelectErrHint.setText(getString(R.string.alert_low_medicine_name_select_err));
            shakeAnimate();
            return;
        }

        if (TextUtils.isEmpty(mSelectRepeatTime)) {
            tvAlertLowMedicineSelectErrHint.setText(getString(R.string.alert_low_medicine_repeat_time_select_err));
            shakeAnimate();
            return;
        }

        // 时间列表
        List<AlertMedicineTimeBean> alertMedicineTimeBeanList = mAlertMedicineTimeAdapter.getData();
        if (alertMedicineTimeBeanList.size()==1)
        {
            tvAlertLowMedicineSelectErrHint.setText(getString(R.string.alert_low_medicine_take_select_err));
            shakeAnimate();
            return;
        }
        for (AlertMedicineTimeBean alertMedicineTimeBean : alertMedicineTimeBeanList) {
            if (!alertMedicineTimeBean.isDefaultAdd()) {
                // 添加降温-用药 提醒
                AlertLowBean alertLowBean = new AlertLowBean();
                alertLowBean.setMAlertStatus(AppConstant.ALERT_MEDICINE_STATUS);
                alertLowBean.setMedicineName(mSelectMedicineName);
                alertLowBean.setMRepeatCode(mSelectRepeatCodeTime);
                alertLowBean.setMRepeatType(mSelectRepeatTime);
                alertLowBean.setMTime(alertMedicineTimeBean.getTimeStr());
                alertLowBean.setMActive("true");
                alertLowBean.setMIsEnabledAlert(true);
                alertLowBean.setBelongBaby(babyInfosBean.getId()+"");
                long lID = GreenDaoAlertMedicineUtils.getInstance().insertAlertLowBean(alertLowBean);
                if (lID != -1) {
                    alertLowBean.setId(lID);
                    // 设定闹钟
                    EventBus.getDefault().post(new AlertMedicineEvent().setCancelAlert(false).setAlertLowBean(alertLowBean));
                }
            }
        }

        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String stringExtra = data.getStringExtra(Low_Add_Intent_Flag);
            if (requestCode == Low_Add_Medicine_Code) {
                // 药品
                tvFragAddMedicine.setText(stringExtra);
                mSelectMedicineName = stringExtra;
            } else if (requestCode == Low_Add_Repeat_Code) {
                // 重复天数
                if (TextUtils.isEmpty(stringExtra)) {
                    tvFragAddMedicineRepeat.setText(getString(R.string.choice));
                    mSelectRepeatCodeTime = "";
                    mSelectRepeatTime = "";
                    return;
                }
                // 选择日期
                String stringCodeExtra = data.getStringExtra(Low_Add_Code_Intent_Flag);
                mSelectRepeatCodeTime = stringCodeExtra;
                if (TextUtils.equals(stringExtra,"Monday、Tuesday、Wednesday、Thursdays、fridays、Saturday、Sunday")) {
                    tvFragAddMedicineRepeat.setText(getString(R.string.Per_day));
                }else if(TextUtils.equals(stringExtra,"周一、周二、周三、周四、周五、周六、周日")){
                    tvFragAddMedicineRepeat.setText(getString(R.string.Per_day));
                }else{
                    tvFragAddMedicineRepeat.setText(stringExtra);
                }
                mSelectRepeatTime = stringExtra;
            } else if (requestCode == Low_Add_Ring_Code) {
                // 提醒铃声
                //tvFragAddMedicineRing.setText(stringExtra);
            }
        }

    }

    /**
     * 错误动画
     */
    private void shakeAnimate() {
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }

        tvAlertLowMedicineSelectErrHint.setVisibility(View.VISIBLE);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(tvAlertLowMedicineSelectErrHint, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0));
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvAlertLowMedicineSelectErrHint.setVisibility(View.GONE);
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
