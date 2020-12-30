package com.nepo.fevercat.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.app.AppManager;
import com.nepo.fevercat.app.BaseApplication;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.DialogUtils;
import com.nepo.fevercat.common.utils.SendSmsUtils;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.event.AlertDialogEvent;
import com.nepo.fevercat.event.CancelDialogEvent;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;
import com.nepo.fevercat.ui.alert.fragment.AlertFragment;
import com.nepo.fevercat.ui.alert.service.AlertHeightRingService;
import com.nepo.fevercat.ui.alert.service.AlertMedicineService;
import com.nepo.fevercat.ui.alert.service.AlertWaterService;
import com.nepo.fevercat.ui.data.fragment.TemperatureDataFragment;
import com.nepo.fevercat.ui.follow.fragment.FollowFragment;
import com.nepo.fevercat.ui.main.bean.TabEntity;
import com.nepo.fevercat.ui.mine.fragment.MineFragment;
import com.nepo.fevercat.ui.real.fragment.RealTimeFragment;
import com.nepo.fevercat.ui.real.service.ProcessMonitorService;
import com.nepo.fevercat.ui.real.service.UploadBabyTempService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui
 * 文件名:  ContainerActivity
 * 作者 :   <sen>
 * 时间 :  上午11:24 2017/4/6.
 * 描述 :  主界面视图
 */

public class ContainerActivity extends BaseActivity {


    /**
     * 视图
     */
    @BindView(R.id.tab_layout)
    CommonTabLayout mCommonTabLayout;


    /***
     * 数据
     */
    private String[] mTitles =null ;
    private int[] mIconUnSelectIds = {
            R.drawable.icon_nav_alert_normal,R.drawable.icon_nav_follow_normal,R.drawable.icon_nav_real_time_normal,R.drawable.icon_nav_temperature_data_normal,R.drawable.icon_nav_about_normal};
    private int[] mIconSelectIds = {
            R.drawable.icon_nav_alert_select,R.drawable.icon_nav_follow_select,R.drawable.icon_nav_real_time_select,R.drawable.icon_nav_temperature_data_select,R.drawable.icon_nav_about_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private AlertFragment mAlertFragment;
    private FollowFragment mFollowFragment;
    private RealTimeFragment mRealTimeFragment;
    private TemperatureDataFragment mTemperatureDataFragment;
    private MineFragment mMineFragment;

    private long exitTime = 0;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ContainerActivity.class);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_container;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void initView() {
        AppManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);

        String nav_remind=getResources().getString(R.string.nav_remind);
        String nav_attention=getResources().getString(R.string.nav_attention);
        String nav_main=getResources().getString(R.string.nav_main);
        String nav_data=getResources().getString(R.string.nav_data);
        String nav_about=getResources().getString(R.string.nav_about);
        mTitles = new String[]{nav_remind,nav_attention,nav_main, nav_data,nav_about};
        mCommonTabLayout.setTextSelectColor(ContextCompat.getColor(mContext,R.color.nav_select));
        mCommonTabLayout.setTextUnselectColor(ContextCompat.getColor(mContext,R.color.nav_normal));
        mCommonTabLayout.setTextsize(10);
        mCommonTabLayout.getBackground().setAlpha(160);

        //初始化菜单
        initTab();
        //初始化frament
        initFragment();
        mCommonTabLayout.measure(0,0);
        //启动定时上传服务
        Intent intent = new Intent(mContext, UploadBabyTempService.class);
        startService(intent);
        // 启动高温提醒服务
        Intent intentAlertHeight = new Intent(mContext, AlertHeightRingService.class);
        startService(intentAlertHeight);
        // 启动吃药闹钟提醒服务
        Intent intentAlertMedicine = new Intent(mContext, AlertMedicineService.class);
        startService(intentAlertMedicine);
        // 启动喝水闹钟提醒服务
        Intent intentAlertWater = new Intent(mContext, AlertWaterService.class);
        startService(intentAlertWater);
        // 应用在后台时间
//        Intent intentProcess = new Intent(mContext,ProcessMonitorService.class);
//        startService(intentProcess);
        initIntent();
    }

    private void initIntent() {
        if (getIntent()==null)
        {
            return;
        }
        if (getIntent().hasExtra(AppConstant.ALERT_MEDICINE_STATUS))
        {
            DialogUtils.getInstance(mContext).AlertMedicineDialog((AlertLowBean) getIntent().getSerializableExtra(AppConstant.ALERT_MEDICINE_STATUS));
        }
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mCommonTabLayout.setTabData(mTabEntities);
        //点击监听
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
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
     * 切换
     */
    private void SwitchTo(int position) {

        try{
            if(ContainerActivity.this==null){
                return;
            }
            if(ContainerActivity.this.isFinishing()){
                return;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
                //提醒
                case 0:
                    transaction.hide(mFollowFragment);
                    transaction.hide(mRealTimeFragment);
                    transaction.hide(mTemperatureDataFragment);
                    transaction.hide(mMineFragment);
                    transaction.show(mAlertFragment);
                    transaction.commitAllowingStateLoss();
                    break;
                //关注
                case 1:
                    transaction.hide(mAlertFragment);
                    transaction.hide(mRealTimeFragment);
                    transaction.hide(mTemperatureDataFragment);
                    transaction.hide(mMineFragment);
                    transaction.show(mFollowFragment);
                    transaction.commitAllowingStateLoss();
                    break;
                //实时体温
                case 2:
                    transaction.hide(mAlertFragment);
                    transaction.hide(mFollowFragment);
                    transaction.hide(mTemperatureDataFragment);
                    transaction.hide(mMineFragment);
                    transaction.show(mRealTimeFragment);
                    transaction.commitAllowingStateLoss();
                    break;
                // 体温数据
                case 3:
                    transaction.hide(mAlertFragment);
                    transaction.hide(mFollowFragment);
                    transaction.hide(mRealTimeFragment);
                    transaction.hide(mMineFragment);
                    transaction.show(mTemperatureDataFragment);
                    transaction.commitAllowingStateLoss();
                    break;
                // 我的
                case 4:
                    transaction.hide(mAlertFragment);
                    transaction.hide(mFollowFragment);
                    transaction.hide(mRealTimeFragment);
                    transaction.hide(mTemperatureDataFragment);
                    transaction.show(mMineFragment);
                    transaction.commitAllowingStateLoss();
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 2;
        mAlertFragment = new AlertFragment();
        mFollowFragment = new FollowFragment();
        mRealTimeFragment = new RealTimeFragment();
        mTemperatureDataFragment = new TemperatureDataFragment();
        mMineFragment = new MineFragment();
        //监听Fragment
        mRealTimeFragment.setOnAlertClickCallback(new RealTimeFragment.OnAlertClickCallback() {
            @Override
            public void onAlertClick() {
                if (mCommonTabLayout != null) {
                    SwitchTo(0);
                    mCommonTabLayout.setCurrentTab(0);
                }
            }
        });

        transaction.add(R.id.fl_body, mAlertFragment, "mAlertFragment");
        transaction.add(R.id.fl_body, mFollowFragment, "mFollowFragment");
        transaction.add(R.id.fl_body, mRealTimeFragment, "mRealTimeFragment");
        transaction.add(R.id.fl_body, mTemperatureDataFragment, "mTemperatureDataFragment");
        transaction.add(R.id.fl_body, mMineFragment, "mMineFragment");
        transaction.commit();
        SwitchTo(currentTabPosition);
        mCommonTabLayout.setCurrentTab(currentTabPosition);
    }
    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClickBack = System.currentTimeMillis();
            if(secondClickBack - exitTime >1500){
                ToastUtils.showToast(getString(R.string.again_exit));
                exitTime = secondClickBack;
                return false;
            }else{
//                AppManager.getAppManager().AppExit(mContext,false);
                finish();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 高温弹窗
     *
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlertDialogEvent(AlertDialogEvent alertDialogEvent) {
        if (alertDialogEvent.isAlertHeight()) {
            // 高温
            BaseApplication.getAppContext().showAlertHeightDialog();
            BaseApplication.getAppContext().sendHeightTempToFollow();
        }else{
            // 用药、喝水
            AlertLowBean alertLowBean = alertDialogEvent.getAlertLowBean();
            if (alertLowBean != null) {
                if (TextUtils.equals(alertLowBean.getMAlertStatus(), AppConstant.ALERT_MEDICINE_STATUS)) {
                    // 用药提醒
                    BaseApplication.getAppContext().showAlertMedicineDialog(alertLowBean);
                }else if(TextUtils.equals(alertLowBean.getMAlertStatus(), AppConstant.ALERT_WATER_STATUS)){
                    // 喝水提醒
                    BaseApplication.getAppContext().showAlertWaterDialog(alertLowBean);
                }

            }
        }

    }


    /**
     * 切换到提醒界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeAlertFragment(CancelDialogEvent cancelDialogEvent){
        if (mCommonTabLayout != null) {
            SwitchTo(0);
            mCommonTabLayout.setCurrentTab(0);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!mAlertFragment.isHidden()){
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                mAlertFragment.checkEditorStatus();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        mRealTimeFragment.connectClose();
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        EventBus.getDefault().unregister(this);
        // 注销上传服务
        Intent intent = new Intent(mContext, UploadBabyTempService.class);
        stopService(intent);
        // 注销高温提醒服务
        Intent intentAlertHeight = new Intent(mContext, AlertHeightRingService.class);
        stopService(intentAlertHeight);
        // 注销吃药提醒服务
        Intent intentAlertMedicine = new Intent(mContext, AlertMedicineService.class);
        stopService(intentAlertMedicine);
        // 注销喝水提醒服务
        Intent intentAlertWater = new Intent(mContext, AlertWaterService.class);
        stopService(intentAlertWater);
        // 注销应用监测服务
        Intent intentProcess = new Intent(mContext,ProcessMonitorService.class);
        stopService(intentProcess);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==0)
        {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                SendSmsUtils.getInstance(mContext).SendSmsToFollow(AppConstant.mAlertHeightBean.getSTemp());
            }else {
                ToastUtils.showToast(getString(R.string.sms_permission_denied));
            }
        }
    }
}
