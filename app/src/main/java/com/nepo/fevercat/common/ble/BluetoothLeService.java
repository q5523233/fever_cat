package com.nepo.fevercat.common.ble;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.common.utils.StringUtils;
import com.nepo.fevercat.event.MsgEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.UUID;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.ble
 * 文件名:  BluetoothLeService
 * 作者 :   <sen>
 * 时间 :  上午11:28 2017/2/28.
 * 描述 :  蓝牙连接、数据交互 服务
 */

public class BluetoothLeService extends Service {


    /**
     * 数据
     */
    // 设备mac地址
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    //BLE适配器
    private BluetoothAdapter mBleAdapter;
    //Gatt Service集合
    private List<BluetoothGattService> mBluetoothGattServices = null;
    // 用于通信的Gatt service
    private BluetoothGattService mBluetoothGattService;
    // 用于读取数据Gatt通道
    private BluetoothGattCharacteristic mBlueCharaNotif;
    // 用于写入数据Gatt通道
    private BluetoothGattCharacteristic mBlueCharaWrite;


    ////////////////////////////////////////////////连接标识
    // 连接成功
    public final static String ACTION_GATT_CONNECTED = "bluetooth.le.ACTION_GATT_CONNECTED";
    // 断开连接
    public final static String ACTION_GATT_DISCONNECTED = "bluetooth.le.ACTION_GATT_DISCONNECTED";
    // 发现设备
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    // 有数据传过来
    public final static String ACTION_DATA_AVAILABLE = "bluetooth.le.ACTION_DATA_AVAILABLE";
    // 写数据给硬件
    public final static String ACTION_DATA_WRITE = "bluetooth.le.ACTION_DATA_WRITE";


    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        closeGatt();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 断开Gatt连接
     */
    public void disconnectGatt() {
        if (mBleAdapter == null || mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.disconnect();

        System.out.println("停止数据传输");
    }

    /**
     * 关闭Gatt数据传输
     */
    public void closeGatt() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }


    /**
     * 连接指定地址的 蓝牙设备
     *
     * @param address
     * @return
     */
    public boolean connect(final String address) {
        // 记录连接的设备mac地址
        mBluetoothDeviceAddress = address;

        // 初始化蓝牙适配器
        if (mBleAdapter == null) {
            mBleAdapter = BleScanner.core(this).getBleAdapter();
        }

        if (mBluetoothGatt != null) {
            // 开始连接设备
            if (mBluetoothGatt.connect()) {
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBleAdapter
                .getRemoteDevice(address);
        LogUtils.logd("-- 服务地址:" + device.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBluetoothGatt = device.connectGatt(this, false, mBluetoothGattCallback, BluetoothDevice.TRANSPORT_LE);
        } else {
            mBluetoothGatt = device.connectGatt(this, false, mBluetoothGattCallback);
        }


        return true;
    }

    /**
     * Gatt 数据读、取,是否连接回调
     */
    BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) { // 成功连接
                // 通知界面 广播
                notificationUIEvent(ACTION_GATT_CONNECTED, "");
                // 搜索服务
                mBluetoothGatt.discoverServices();


            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) { // 断开连接
                // 通知界面 广播
                notificationUIEvent(ACTION_GATT_DISCONNECTED, "");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (mBluetoothGattServices != null
                        && mBluetoothGattServices.size() > 0)
                    mBluetoothGattServices.clear();

                if (mBluetoothGatt != null)
                    mBluetoothGattServices = mBluetoothGatt.getServices();

                // 获取用于通信的service
                mBluetoothGattService = mBluetoothGatt.getService(UUID
                        .fromString(AppConstant.SYS_BLUETOOTH_UUID_SERVICE));

                // 获取用于读取数据的 GattCharacteristic
                mBlueCharaNotif = mBluetoothGattService.getCharacteristic(UUID
                        .fromString(AppConstant.SYS_BLUETOOTH_UUID_CHARA_NOTIFY));

                // 获取用于写入数据的 GattCharacteristic
                mBlueCharaWrite = mBluetoothGattService
                        .getCharacteristic(UUID
                                .fromString(AppConstant.SYS_BLUETOOTH_UUID_CHARA_WRITE));
                //设置硬件回应监听
                enableHTIndication();

            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            System.out.println("-- *******************读");


        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {

            System.out.println("-- *******************改:" + StringUtils.byte2HexStr(characteristic.getValue()));
            // 通知界面 广播
            notificationUIEvent(ACTION_DATA_AVAILABLE, StringUtils.byte2HexStr(characteristic.getValue()));

        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
            // 写入成功
            System.out.println("-- *******************写" + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
            }

        }
    };

    /**
     * enable Health Thermometer indication on Health Thermometer Measurement
     * characteristic
     */
    private void enableHTIndication() {
        if (mBluetoothGatt == null || mBlueCharaNotif == null)
            return;

        // 发送指令后，获取指令的Notify,设置硬件响应监听
        boolean b = mBluetoothGatt.setCharacteristicNotification(mBlueCharaNotif, true);
        BluetoothGattDescriptor descriptor = mBlueCharaNotif
                .getDescriptor(UUID
                        .fromString(AppConstant.SYS_BLUETOOTH_UUID_DESCRIPTOR_UUID));
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            SystemClock.sleep(200);

            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }


    /**
     * 发送指令
     *
     * @param values
     */
    private void writeValues(byte[] values) {
        Log.e("Tag", "---- 将发送指令: " + StringUtils.byte2HexStr(values));
        if (mBlueCharaWrite != null && mBluetoothGatt != null) {
            Log.e("Tag", "---- 发送指令: " + StringUtils.byte2HexStr(values));
            mBlueCharaWrite.setValue(values);
            mBluetoothGatt.writeCharacteristic(mBlueCharaWrite);
        }
    }

    /**
     * 获取温度
     * 0x0E
     */
    private void getTemperatureCode() {
        //        byte[] bytes = new byte[7];
        //        // 包头
        //        bytes[0] = (byte) 0xAA;
        //        // 流水号
        //        bytes[1] = (byte) 0x01;
        //        // 命令字
        //        bytes[2] = (byte) 0x0E;
        //        // 数据长度
        //        bytes[3] = (byte) 0x01;
        //        // 数据内容
        //        bytes[4] = (byte) 0x00;
        //        // 校验Sum
        //        bytes[5] =  StringUtils.getCheckBit(bytes)[0];
        //        // 包尾
        //        bytes[6] = (byte) 0x55;
        //        writeValues(bytes);
    }


    /**
     * 获取温度差值
     */
    private void getTemperatureDifferentCode() {
        //        byte[] bytes = new byte[7];
        //        // 包头
        //        bytes[0] = (byte) 0xAA;
        //        // 流水号
        //        bytes[1] = (byte) 0x01;
        //        // 命令字
        //        bytes[2] = (byte) 0x10;
        //        // 数据长度
        //        bytes[3] = (byte) 0x00;
        //        // 数据内容
        //        bytes[4] = (byte) 0x00;
        //        // 校验Sum
        //        bytes[5] =  StringUtils.getCheckBit(bytes)[0];
        //        // 包尾
        //        bytes[6] = (byte) 0x55;
        //        writeValues(bytes);
    }

    /**
     * 获取能力列表
     */
    private void getDevicesListCode() {
        //        byte[] bytes = new byte[7];
        //        // 包头
        //        bytes[0] = (byte) 0xAA;
        //        // 流水号
        //        bytes[1] = (byte) 0x01;
        //        // 命令字
        //        bytes[2] = (byte) 0x00;
        //        // 数据长度
        //        bytes[3] = (byte) 0x00;
        //        // 数据内容
        //        bytes[4] = (byte) 0x00;
        //        // 校验Sum
        //        bytes[5] =  StringUtils.getCheckBit(bytes)[0];
        //        // 包尾
        //        bytes[6] = (byte) 0x55;
        //        writeValues(bytes);
    }


    /**
     * 获取设备电量及状态
     * 体温计
     */
    private void getBatteryCode() {
        byte[] bytes = new byte[5];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x04;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x08;
        // 命令数据
        bytes[4] = (byte) 0x00;
        //        // 校验Sum
        //        bytes[5] =  StringUtils.getCheckBit(bytes)[0];
        //        // 包尾
        //        bytes[6] = (byte) 0x55;
        writeValues(bytes);
    }


    /**
     * 获取生产序列号
     */
    private void getProductSequence() {
        byte[] bytes = new byte[5];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x04;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x07;
        // 命令数据
        bytes[4] = (byte) 0x00;
        writeValues(bytes);
    }

    /**
     * 获取厂商名称
     */
    private void getManufacturerName() {
        byte[] bytes = new byte[5];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x04;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x01;
        // 命令数据
        bytes[4] = (byte) 0x00;
        writeValues(bytes);
    }

    /**
     * 获取设备型号
     */
    private void getProductModels() {
        byte[] bytes = new byte[5];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x04;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x03;
        // 命令数据
        bytes[4] = (byte) 0x00;
        writeValues(bytes);
    }

    /**
     * 获取硬件版本
     * 2：硬件版本
     */
    private void getHardWareVersion() {
        byte[] bytes = new byte[5];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x04;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x02;
        // 命令数据
        bytes[4] = (byte) 0x02;
        writeValues(bytes);
    }

    /**
     * 获取软件版本
     * 0：软件版本
     */
    private void getSoftWareVersion() {
        byte[] bytes = new byte[5];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x04;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x02;
        // 命令数据
        bytes[4] = (byte) 0x00;
        writeValues(bytes);
    }


    /**
     * 获取固件版本
     * 3：固件版本
     */
    private void getFirmWareVersion() {
        byte[] bytes = new byte[5];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x04;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x02;
        // 命令数据
        bytes[4] = (byte) 0x03;
        writeValues(bytes);
    }

    /**
     * 获取协议版本
     * 1：协议版本
     */
    private void getProtocolVersion() {
        byte[] bytes = new byte[5];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x04;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x02;
        // 命令数据
        bytes[4] = (byte) 0x01;
        writeValues(bytes);
    }

    /**
     * 校准
     */
    private void getAdjust(String msg) {
        String[] temps = null;
        if (msg.contains(AppConstant.TEMP_SPLIT)) {
            temps = msg.split(AppConstant.TEMP_SPLIT);
        }
        int value;
        int value2 = 0;
        if (temps == null)
            value = Integer.valueOf(msg);
        else {
            value = Integer.valueOf(temps[0]);
            value2 = Integer.valueOf(temps[1]);
        }
        byte[] bytes = new byte[11];
        // 帧头
        bytes[0] = (byte) 0xFF;
        // 帧长
        bytes[1] = (byte) 0x09;
        // 模块号
        bytes[2] = (byte) 0x04;
        // 命令
        bytes[3] = (byte) 0x06;
        // 命令数据
        //        bytes[4] = Byte.parseByte(msg,16);
        bytes[4] = (byte) value;
        bytes[5] = (byte) value2;
        int checkSum = 0;
        for (int i = 0; i < 6; i++) {
            checkSum += bytes[i];
        }
        bytes[10] = (byte) checkSum;
        writeValues(bytes);
    }


    // ////////////////////////////////////////////////////////界面操作//////////////////////////////////////////////////////////////


    /**
     * 通知界面
     *
     * @param action
     */
    private void notificationUIEvent(String action, String msg) {
        EventBus.getDefault().post(new MsgEvent(action, msg));
    }

    /**
     * 界面操作分发控制
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onControlMsgListen(MsgEvent msgEvent) {
        if (msgEvent != null) {
            if (!TextUtils.isEmpty(msgEvent.getAction())) {
                switch (msgEvent.getAction()) {
                    case AppConstant.SYS_BLUETOOTH_TEMP_FLAG_WRITE:
                        //获取温度
                        getTemperatureCode();
                        break;
                    case AppConstant.SYS_BLUETOOTH_BATTERY_FLAG_WRITE:
                        //获取电池、状态
                        getBatteryCode();
                        break;
                    case AppConstant.SYS_BLUETOOTH_SET_SERIAL_FLAG_WRITE:
                        //设置生产流水号
                        break;
                    case AppConstant.SYS_BLUETOOTH_GET_SERIAL_FLAG_WRITE:
                        //获取生产流水号
                        getProductSequence();
                        break;
                    case AppConstant.SYS_BLUETOOTH_GET_MANUFACTURER_NAME_FLAG_WRITE:
                        //获取厂商名称
                        getManufacturerName();
                        break;
                    case AppConstant.SYS_BLUETOOTH_GET_MODELS_FLAG_WRITE:
                        //获取设备型号
                        getProductModels();
                        break;
                    case AppConstant.SYS_BLUETOOTH_HARD_VERSION_FLAG_WRITE:
                        //获取硬件版本
                        getHardWareVersion();
                        break;
                    case AppConstant.SYS_BLUETOOTH_SOFT_VERSION_FLAG_WRITE:
                        //获取软件版本
                        getSoftWareVersion();
                        break;
                    case AppConstant.SYS_BLUETOOTH_FIRM_VERSION_FLAG_WRITE:
                        //获取固件版本
                        getFirmWareVersion();
                        break;
                    case AppConstant.SYS_BLUETOOTH_PROTOCOL_VERSION_FLAG_WRITE:
                        //获取协议版本
                        getProtocolVersion();
                        break;
                    case AppConstant.SYS_BLUETOOTH_ADJUST_FLAG_WRITE:
                        // 校准
                        String msg = msgEvent.getMsg();
                        getAdjust(msg);
                        break;

                }
            }
        }
    }

}
