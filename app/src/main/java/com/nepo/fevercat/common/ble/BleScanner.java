package com.nepo.fevercat.common.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.ble
 * 文件名:  BleScanner
 * 作者 :   <sen>
 * 时间 :  上午10:36 2017/2/28.
 * 描述 :  蓝牙适配、扫描 工具
 */

public class BleScanner {

    private static final int SCAN_FAILED_FEATURE_UNSUPPORTED = 4;
    //上下文设置
    private Context mContext;
    //Ble管理器
    private BluetoothManager mBleManger;
    //是否在扫描
    private boolean mScaning;
    //本类
    private static BleScanner mBleScaner;
    //回调接口
    private BleScanCallBack mBleScanCallBack;
    //5.0一下扫描的接口
    private BluetoothAdapter.LeScanCallback mLeScanCallback;
    //5.0以上接口
    private ScanCallback mScanCallback;
    //BLE适配器
    private BluetoothAdapter mBleAdapter;
    //Ble扫描接口
    private BluetoothLeScanner mScanner;


    /*
    * BlE is Supports
    */
    public boolean isBleSupport() {
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /*
    * BLE is Open
    */

    public boolean isBleOpen() {
        mBleManger = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBleAdapter = mBleManger.getAdapter();
        if (mBleAdapter == null) {
            mBleAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return mBleAdapter != null && mBleAdapter.isEnabled();
    }

    /*
        BLE 适配器
     */
    public BluetoothAdapter getBleAdapter(){

        mBleManger = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBleAdapter = mBleManger.getAdapter();
        if (mBleAdapter == null) {
            mBleAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        return mBleAdapter;
    }



    //构造方法
    public BleScanner(Context context) {
        mContext = context;
    }

    //单例
    public static BleScanner core(Context context) {
        if (mBleScaner == null) {
            synchronized (BleScanner.class) {
                if (mBleScaner == null) {
                    mBleScaner = new BleScanner(context);
                }
            }
        }
        return mBleScaner;
    }

/*
* 获取手机品牌
* */

    public String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /*
     * 5.0以上
     */
    public boolean isSupportLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public void setLeScanCallback(BleScanCallBack callback) {
        mBleScanCallBack = callback;
        if (mBleScanCallBack == null) return;

            if (isSupportLollipop()) {
                mScanCallback = new ScanCallback() {

                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        if (isSupportLollipop()) {
                            byte[] scanRecord = null;
                            if (result.getScanRecord() != null)
                                scanRecord = result.getScanRecord().getBytes();
                            if (mBleScanCallBack != null)
                                mBleScanCallBack.onBleScan(result.getDevice(), result.getRssi(), scanRecord);
                        }
                    }

                    @Override
                    public void onScanFailed(int errorCode) {
                        if (errorCode != ScanCallback.SCAN_FAILED_ALREADY_STARTED)
                            if (mBleScanCallBack != null)
                                mBleScanCallBack.onScanFail(errorCode);
                    }
                };
            } else {
                this.mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

                    @Override
                    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                        if (mBleScanCallBack != null)
                            mBleScanCallBack.onBleScan(device, rssi, scanRecord);

                    }
                };
            }
    }


    public interface BleScanCallBack {
        void onBleScan(BluetoothDevice device, int rssi, byte[] scanResult);

        void onScanFail(int errorCode);

        void onStartScan();

        void onStopScan();

    }


    synchronized public void startScan() {
        synchronized (this) {
            if (mScaning) {
                return;
            }
            if (!isBleSupport() && !isBleOpen()) {
                return;
            }
            mScaning = true;
                if (isSupportLollipop()) {
                    mScanner = mBleAdapter.getBluetoothLeScanner();
                    if (mScanner == null) {
                        synchronized (this) {
                            mScaning = false;
                        }
                        if (mBleScanCallBack != null)
                            mBleScanCallBack.onScanFail(SCAN_FAILED_FEATURE_UNSUPPORTED);
                    } else {
                        mScanner.startScan(mScanCallback);
                        synchronized (this) {
                            mScaning = true;
                        }
                        mBleScanCallBack.onStartScan();
                    }

                } else {
                    if (!mBleAdapter.startLeScan(
                            mLeScanCallback)) {
                        synchronized (this) {
                            mScaning = false;
                        }
                        if (mBleScanCallBack != null)
                            mBleScanCallBack.onScanFail(SCAN_FAILED_FEATURE_UNSUPPORTED);
                    } else {
                        synchronized (this) {
                            mScaning = true;
                        }
                        mBleScanCallBack.onStartScan();
                    }
                }

        }
    }

    synchronized public void stopScan() {

        synchronized (this) {
            if (!mScaning)
                return;
        }

        try {
            if (isSupportLollipop()) {
                if (mScanner != null)
                    mScanner.stopScan(mScanCallback);
            } else {
                if (mBleAdapter != null)
                    mBleAdapter.stopLeScan(mLeScanCallback);
            }
        } catch (Exception e) {

        }

        synchronized (this) {
            mScaning = false;
        }

        if (mBleScanCallBack != null)
            mBleScanCallBack.onStopScan();
    }
}
