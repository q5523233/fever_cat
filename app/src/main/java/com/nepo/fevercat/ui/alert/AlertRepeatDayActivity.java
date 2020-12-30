package com.nepo.fevercat.ui.alert;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.ui.alert.adapter.AlertRepeatDayAdapter;
import com.nepo.fevercat.ui.alert.bean.AlertRepeatDayBean;
import com.nepo.fevercat.ui.alert.fragment.AlertAddLowMedicineFragment;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert
 * 文件名:  AlertRepeatDayActivity
 * 作者 :   <sen>
 * 时间 :  上午11:37 2017/6/30.
 * 描述 :  重复天数
 */

public class AlertRepeatDayActivity extends BaseActivity {


    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.recycle_alert_repeat_list)
    RecyclerView recycleAlertRepeatList;

    List<AlertRepeatDayBean> mAlertRepeatDayBeanList;
    AlertRepeatDayAdapter mAlertRepeatDayAdapter;
    List<String> mSelectStr;
    List<String> mSelectTimeCode;
    String mSelectRepeatCodeTime;

    public static final String Intent_Repeat_Day_Code_Flag = "Intent_Repeat_Day_Code_Flag";


    public static Intent newIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, AlertRepeatDayActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_alert_repeat_day;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTopBarTitle.setText(getString(R.string.alert_add_height_repeat_day));

        recycleAlertRepeatList.setHasFixedSize(true);
        recycleAlertRepeatList.setLayoutManager(new LinearLayoutManager(mContext));

        Bundle extras = getIntent().getExtras();
        mSelectRepeatCodeTime = extras.getString(Intent_Repeat_Day_Code_Flag);


        initRepeatDayData();
    }

    @OnClick({R.id.rl_top_bar_back,R.id.rl_repeat_day_confirm})
    public void onBackClick() {
        putSelectStrList(mAlertRepeatDayAdapter.getData());
        Intent intent = new Intent();
        if (mSelectStr != null && mSelectStr.size() > 0) {
            StringBuilder sbStr = new StringBuilder();
            StringBuilder sbCodeStr = new StringBuilder();
            for (String s : mSelectStr) {
                sbStr.append(s);
            }
            for (String s : mSelectTimeCode) {
                sbCodeStr.append(s).append(",");
            }
            if (sbCodeStr.lastIndexOf(",") != -1) {
                sbCodeStr.deleteCharAt(sbCodeStr.length() - 1);
            }

            if (sbStr.lastIndexOf("、") != -1) {
                sbStr.deleteCharAt(sbStr.length() - 1);
            }

            intent.putExtra(AlertAddLowMedicineFragment.Low_Add_Intent_Flag, sbStr.toString());
            intent.putExtra(AlertAddLowMedicineFragment.Low_Add_Code_Intent_Flag, sbCodeStr.toString());
        } else {
            intent.putExtra(AlertAddLowMedicineFragment.Low_Add_Intent_Flag, "");
            intent.putExtra(AlertAddLowMedicineFragment.Low_Add_Code_Intent_Flag, "");
        }
        setResult(0, intent);
        finish();
    }


    /**
     * 初始化数据
     */
    private void initRepeatDayData() {
        mAlertRepeatDayBeanList = new ArrayList<>();
        mAlertRepeatDayBeanList.add(new AlertRepeatDayBean().setTitle(getString(R.string.Monday)).setCode("2").setCheck(false));
        mAlertRepeatDayBeanList.add(new AlertRepeatDayBean().setTitle(getString(R.string.Tuesday)).setCode("3").setCheck(false));
        mAlertRepeatDayBeanList.add(new AlertRepeatDayBean().setTitle(getString(R.string.Wednesday)).setCode("4").setCheck(false));
        mAlertRepeatDayBeanList.add(new AlertRepeatDayBean().setTitle(getString(R.string.Thursdays)).setCode("5").setCheck(false));
        mAlertRepeatDayBeanList.add(new AlertRepeatDayBean().setTitle(getString(R.string.fridays)).setCode("6").setCheck(false));
        mAlertRepeatDayBeanList.add(new AlertRepeatDayBean().setTitle(getString(R.string.Saturday)).setCode("7").setCheck(false));
        mAlertRepeatDayBeanList.add(new AlertRepeatDayBean().setTitle(getString(R.string.Sunday)).setCode("8").setCheck(false));


        if (!TextUtils.isEmpty(mSelectRepeatCodeTime)) {
            String[] split = mSelectRepeatCodeTime.split(",");
            for (String s : split) {
                for (int i = 0; i < mAlertRepeatDayBeanList.size(); i++) {
                    if (TextUtils.equals(mAlertRepeatDayBeanList.get(i).getCode(), s)) {
                        mAlertRepeatDayBeanList.get(i).setCheck(true);
                    }
                }
            }
        }


        mAlertRepeatDayAdapter = new AlertRepeatDayAdapter(mAlertRepeatDayBeanList);
        mAlertRepeatDayAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recycleAlertRepeatList.setAdapter(mAlertRepeatDayAdapter);
        mAlertRepeatDayAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AlertRepeatDayBean alertRepeatDayBean = (AlertRepeatDayBean) adapter.getData().get(position);
                alertRepeatDayBean.setCheck(!alertRepeatDayBean.isCheck());
                adapter.setData(position, alertRepeatDayBean);

            }
        });


    }


    /**
     * 选中的日期
     */
    private void putSelectStrList(List<AlertRepeatDayBean> mList) {
        mSelectStr = new ArrayList<>();
        mSelectTimeCode = new ArrayList<>();
        for (AlertRepeatDayBean alertRepeatDayBean : mList) {
            if (alertRepeatDayBean.isCheck()) {
                mSelectStr.add(alertRepeatDayBean.getTitle() + "、");
                mSelectTimeCode.add(alertRepeatDayBean.getCode());
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
