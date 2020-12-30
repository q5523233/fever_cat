package com.nepo.fevercat.ui;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.ble.BleScanner;
import com.nepo.fevercat.common.ble.BluetoothLeService;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.event.MsgEvent;
import com.nepo.fevercat.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;


import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class MainActivity extends BaseActivity {


    /**
     * 视图
     *
     * @return
     */
    @BindView(R.id.msg_tv)
    TextView msg_tv;
    @BindView(R.id.msg_tip_tv)
    TextView msg_tip_tv;

    /**
     * 数据
     *
     * @return
     */
    private BleScanner mBleScanner;
    // 蓝牙服务
    public static BluetoothLeService mBluetoothLeService;
    // 是否绑定服务标识
    private boolean isBindService;
    // 当前连接设备mac地址
    private String mDeviceAddress;


    // 轮询温度观察者
    private Subscription subscribeTemp;

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        // 开启蓝牙检测
        initScanBleInfo();


    }

    /**
     * 检测是否支持BLE蓝牙通信、打开蓝牙、扫描指定名称蓝牙
     */
    private void initScanBleInfo() {
        mBleScanner = BleScanner.core(mContext);
        if (mBleScanner.isBleSupport()) {
            if (mBleScanner.isBleOpen()) {
                // 扫描结果回调
                mBleScanner.setLeScanCallback(new BleScanner.BleScanCallBack() {
                    @Override
                    public void onBleScan(BluetoothDevice device, int rssi, byte[] scanResult) {
                        String name = device.getName();
                        String address = device.getAddress();
                        LogUtils.logd("-- 蓝牙名字:" + name);
                        if (name != null
                                && name.trim().contains(AppConstant.SYS_BLUETOOTH_NAME)) {
                            // 保存对应mac 地址
                            if (!TextUtils.isEmpty(address)) {
                                mDeviceAddress = address;
                                SharedPreferencesUtil.setString(AppConstant.SYS_CUR_DEVICE_ADDRESS, address);
                            }

                            // 找到设备结束扫描
                            mBleScanner.stopScan();
                            //TODO 成功, 提醒界面 并 开始后台服务进行连接
                            msg_tip_tv.setText("扫描成功");
                            //开启后台连接服务
                            startBluetoothService();
                        }

                    }

                    @Override
                    public void onScanFail(int errorCode) {
                        // TODO 失败,提醒界面并关闭扫描
                        msg_tip_tv.setText("扫描失败");
                        mBleScanner.stopScan();
                    }

                    @Override
                    public void onStartScan() {
                        //TODO 开始, 提醒界面
                    }

                    @Override
                    public void onStopScan() {
                        //TODO 结束, 提醒界面
                    }
                });
                // 开始扫描
                mBleScanner.startScan();
            }
        }
    }


    /**
     * 开启后台连接服务
     */
    private void startBluetoothService() {
        Intent gattServiceIntent = new Intent(this,
                BluetoothLeService.class);
        isBindService = bindService(gattServiceIntent, mServiceConnection,
                BIND_AUTO_CREATE);

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
        }
    };


    /**
     * 开始轮询获取温度
     * 间隔:1秒
     */
    @OnClick(R.id.temp_loop_btn)
    public void startLoop() {
        // 手动开始后,每隔1秒 获取一次温度
        if (subscribeTemp != null) {
            subscribeTemp = Observable.interval(1, TimeUnit.SECONDS)
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(Long number) {
//                            EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_TEMP_FLAG_WRITE, ""));
                        }
                    });
        }

        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_TEMP_FLAG_WRITE, ""));


    }

    /**
     * 结束轮询
     */
    @OnClick(R.id.temp_end_loop_btn)
    public void endLoop() {
        // 停止计时
        if (subscribeTemp != null) {
            if (!subscribeTemp.isUnsubscribed()) {
                subscribeTemp.unsubscribe();
                subscribeTemp = null;
            }
        }
    }


    /**
     * 功能列表
     */
    @OnClick(R.id.device_list_btn)
    public void getDevicesListOnClick(){
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_DEVICES_LIST_FLAG_WRITE, ""));
    }


    /**
     * 版本、名称
     */
    @OnClick(R.id.version_btn)
    public void getVersionOnClick(){
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_VERSION_FLAG_WRITE, ""));
    }

    /**
     * 电池
     */
    @OnClick(R.id.battery_btn)
    public void getBatteryOnClick(){
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_BATTERY_FLAG_WRITE, ""));
    }

    /**
     * 设置流水号
     */
    @OnClick(R.id.set_serial_btn)
    public void setSerialOnClick(){
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_SET_SERIAL_FLAG_WRITE, ""));
    }

    /**
     * 获取流水号
     */
    @OnClick(R.id.get_serial_btn)
    public void getSerialOnClick(){
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_GET_SERIAL_FLAG_WRITE, ""));
    }

    /**
     * 设置时间
     */
    @OnClick(R.id.set_time_btn)
    public void setTimeOnClick(){
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_SET_TIME_FLAG_WRITE, ""));
    }

    /**
     * 获取时间
     */
    @OnClick(R.id.get_time_btn)
    public void getTimeOnClick(){
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_GET_TIME_FLAG_WRITE, ""));
    }

    /**
     * 获取温度差
     */
    @OnClick(R.id.temp_different_btn)
    public void setTimeDiffOnClick(){
        EventBus.getDefault().post(new MsgEvent(AppConstant.SYS_BLUETOOTH_TEMP_DIFFERENT_FLAG_WRITE, ""));
    }






    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMsg(MsgEvent msgEvent) {
        if (msgEvent != null) {
            if (!TextUtils.isEmpty(msgEvent.getAction())) {
                switch (msgEvent.getAction()) {
                    case BluetoothLeService.ACTION_GATT_CONNECTED:
                        msg_tip_tv.setText("连接成功");
                        break;
                    case BluetoothLeService.ACTION_GATT_DISCONNECTED:
                        msg_tip_tv.setText("断开连接");
                        // 断开重连
                        Log.e("tag", "onGetMsg: 断开重连" );
                        String address = SharedPreferencesUtil.getString(AppConstant.SYS_CUR_DEVICE_ADDRESS, "");
                        mBluetoothLeService.closeGatt();
                        mBluetoothLeService.connect(address);

                        break;
                    case BluetoothLeService.ACTION_DATA_AVAILABLE:
                        if (!TextUtils.isEmpty(msgEvent.getMsg())) {
                            // 显示温度(暂时注释不做处理)
                            //String flagStr = StringUtils.checkFlagType(msgEvent.getMsg());
                            msg_tv.setText(msgEvent.getMsg());
                        }
                        break;
                }
            }
        }

    }


    /**
     * 解除服务绑定
     */
    private void unbindService() {
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnectGatt();
        }
        if (isBindService)
            unbindService(mServiceConnection);
        mBleScanner.stopScan();
        isBindService = false;
        mBluetoothLeService = null;
        mDeviceAddress = "";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbindService();
    }
}
