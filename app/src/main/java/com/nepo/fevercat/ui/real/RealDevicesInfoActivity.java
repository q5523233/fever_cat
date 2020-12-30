package com.nepo.fevercat.ui.real;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.VersionUtil;
import com.nepo.fevercat.event.DevicesInfoEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.real
 * 文件名:  RealDevicesInfoActivity
 * 作者 :   <sen>
 * 时间 :  下午4:04 2017/8/14.
 * 描述 :  设备信息
 */

public class RealDevicesInfoActivity extends BaseActivity {


    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.tv_devices_manufacturer_name)
    TextView tvDevicesManufacturerName;
    @BindView(R.id.tv_devices_production_models)
    TextView tvDevicesProductionModels;
    @BindView(R.id.tv_devices_production_sequence)
    TextView tvDevicesProductionSequence;
    @BindView(R.id.tv_devices_hardware_version)
    TextView tvDevicesHardwareVersion;
    @BindView(R.id.tv_devices_software_version)
    TextView tvDevicesSoftwareVersion;
    @BindView(R.id.tv_devices_firmware_version)
    TextView tvDevicesFirmwareVersion;
    @BindView(R.id.tv_devices_protocol_version)
    TextView tvDevicesProtocolVersion;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RealDevicesInfoActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_devices_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        tvTopBarTitle.setText(getString(R.string.devices_info_title));
        setDevicesInfo();

    }

    /**
     * 返回
     */
    @OnClick(R.id.rl_top_bar_back)
    public void onBackClick() {
        finish();
    }

    /**
     * 设备信息
     */
    private void setDevicesInfo() {

        // 生产厂家
        String manufacturerName = SharedPreferencesUtil.getString(AppConstant.MANUFACTURER_NAME_Tag, "");
        tvDevicesManufacturerName.setText(manufacturerName);
        // 生产型号
        String productModels = SharedPreferencesUtil.getString(AppConstant.PRODUCT_MODELS_Tag, "");
        tvDevicesProductionModels.setText(productModels);
        // 生产序列号
        String productSequence = SharedPreferencesUtil.getString(AppConstant.PRODUCT_SEQUENCE_Tag, "");
        tvDevicesProductionSequence.setText(productSequence);
        // 硬件版本
        String hardwareVersion = SharedPreferencesUtil.getString(AppConstant.HARD_WARE_Tag, "");
        tvDevicesHardwareVersion.setText(hardwareVersion);
        // 软件版本
//        String softwareVersion = SharedPreferencesUtil.getString(AppConstant.SOFT_WARE_Tag, "");
        String softwareVersion = VersionUtil.getVersionName(mContext);
        tvDevicesSoftwareVersion.setText(softwareVersion);
        // 固件版本
        String firmwareVersion = SharedPreferencesUtil.getString(AppConstant.FIRM_WARE_Tag, "");
        tvDevicesFirmwareVersion.setText(firmwareVersion);
        // 协议版本
        String protocolVersion = SharedPreferencesUtil.getString(AppConstant.PROTOCOL_Tag, "");
        tvDevicesProtocolVersion.setText(protocolVersion);


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetDevicesMsg(DevicesInfoEvent devicesInfoEvent){
        if (devicesInfoEvent != null) {
            if (TextUtils.equals(devicesInfoEvent.getTag(), DevicesInfoEvent.MANUFACTURER_NAME_FLAG)) {
                // 生产厂家
                String manufacturerName = SharedPreferencesUtil.getString(AppConstant.MANUFACTURER_NAME_Tag, "");
                tvDevicesManufacturerName.setText(manufacturerName);
                LogUtils.logd("-- 生产厂家:"+manufacturerName);
            }else if (TextUtils.equals(devicesInfoEvent.getTag(), DevicesInfoEvent.PRODUCT_SEQUENCE_FLAG)) {
                // 生产型号
                String productModels = SharedPreferencesUtil.getString(AppConstant.PRODUCT_MODELS_Tag, "");
                tvDevicesProductionModels.setText(productModels);
                LogUtils.logd("-- 生产型号:"+productModels);
            }else if (TextUtils.equals(devicesInfoEvent.getTag(), DevicesInfoEvent.PRODUCT_MODELS_FLAG)) {
                // 生产序列号
                String productSequence = SharedPreferencesUtil.getString(AppConstant.PRODUCT_SEQUENCE_Tag, "");
                tvDevicesProductionSequence.setText(productSequence);
                LogUtils.logd("-- 生产序列号:"+productSequence);
            }else if (TextUtils.equals(devicesInfoEvent.getTag(), DevicesInfoEvent.HARD_WARE_VERSION_FLAG)) {
                // 硬件版本
                String hardwareVersion = SharedPreferencesUtil.getString(AppConstant.HARD_WARE_Tag, "");
                tvDevicesHardwareVersion.setText(hardwareVersion);
                LogUtils.logd("-- 硬件版本:"+hardwareVersion);
            }else if (TextUtils.equals(devicesInfoEvent.getTag(), DevicesInfoEvent.SOFT_WARE_VERSION_FLAG)) {
                // 软件版本
                String softwareVersion = SharedPreferencesUtil.getString(AppConstant.SOFT_WARE_Tag, "");
                tvDevicesSoftwareVersion.setText(softwareVersion);
                LogUtils.logd("-- 软件版本:"+softwareVersion);
            }else if (TextUtils.equals(devicesInfoEvent.getTag(), DevicesInfoEvent.FIRM_WARE_VERSION_FLAG)) {
                // 固件版本
                String firmwareVersion = SharedPreferencesUtil.getString(AppConstant.FIRM_WARE_Tag, "");
                tvDevicesFirmwareVersion.setText(firmwareVersion);
                LogUtils.logd("-- 固件版本:"+firmwareVersion);
            }else if (TextUtils.equals(devicesInfoEvent.getTag(), DevicesInfoEvent.PROTOCOL_VERSION_FLAG)) {
                // 协议版本
                String protocolVersion = SharedPreferencesUtil.getString(AppConstant.PROTOCOL_Tag, "");
                tvDevicesProtocolVersion.setText(protocolVersion);
                LogUtils.logd("-- 协议版本:"+protocolVersion);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
