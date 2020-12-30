package com.nepo.fevercat.ui.alert;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.ui.alert.fragment.AlertAddLowMedicineFragment;
import com.nepo.fevercat.ui.alert.fragment.AlertAddLowWaterFragment;
import com.nepo.fevercat.ui.main.bean.TabEntity;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert
 * 文件名:  AlertLowAddActivity
 * 作者 :   <sen>
 * 时间 :  上午10:32 2017/6/29.
 * 描述 :
 */

public class AlertLowAddActivity extends BaseActivity {


    @BindView(R.id.rl_top_bar_back)
    RelativeLayout rlTopBarBack;
    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.tab_alert_low)
    CommonTabLayout tabAlertLow;


    private String[] mTitles =null ;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private AlertAddLowMedicineFragment mAlertAddLowMedicineFragment;
    private AlertAddLowWaterFragment mAlertAddLowWaterFragment;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AlertLowAddActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_alert_low;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        HideUtil.init(this);
        tvTopBarTitle.setText(getString(R.string.alert_add_low_title));
        String nav_medicine=getResources().getString(R.string.alert_add_low_nav_medicine);
        String nav_water=getResources().getString(R.string.alert_add_low_nav_water);
        mTitles = new String[]{nav_medicine,nav_water};
        tabAlertLow.setTextSelectColor(ContextCompat.getColor(mContext,R.color.nav_select));
        tabAlertLow.setTextUnselectColor(ContextCompat.getColor(mContext,R.color.nav_normal));
        tabAlertLow.setTextsize(14);
        //初始化菜单
        initTab();
        //初始化frament
        initFragment();
        tabAlertLow.measure(0,0);


    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        tabAlertLow.setTabData(mTabEntities);
        //点击监听
        tabAlertLow.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 初始化
     */
    private void initFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        mAlertAddLowMedicineFragment = new AlertAddLowMedicineFragment();
        mAlertAddLowWaterFragment = new AlertAddLowWaterFragment();

        transaction.add(R.id.fl_body_alert_low, mAlertAddLowMedicineFragment, "mAlertAddLowMedicineFragment");
        transaction.add(R.id.fl_body_alert_low, mAlertAddLowWaterFragment, "mAlertAddLowWaterFragment");
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabAlertLow.setCurrentTab(currentTabPosition);
    }


    /**
     * 切换
     */
    private void SwitchTo(int position) {

        try{
            if(AlertLowAddActivity.this==null){
                return;
            }
            if(AlertLowAddActivity.this.isFinishing()){
                return;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
                //用药提醒
                case 0:
                    transaction.hide(mAlertAddLowWaterFragment);
                    transaction.show(mAlertAddLowMedicineFragment);
                    transaction.commitAllowingStateLoss();
                    break;
                //喝水提醒
                case 1:
                    transaction.hide(mAlertAddLowMedicineFragment);
                    transaction.show(mAlertAddLowWaterFragment);
                    transaction.commitAllowingStateLoss();
                    break;

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * 返回
     */
    @OnClick(R.id.rl_top_bar_back)
    public void onBackClick() {
        finish();
    }



}
