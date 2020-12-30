package com.nepo.fevercat.app;

import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;
import com.nepo.fevercat.ui.data.bean.ShareDataBean;
import com.nepo.fevercat.ui.main.bean.MainLoginResBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.app
 * 文件名:  AppConstant
 * 作者 :   <sen>
 * 时间 :  下午3:10 2017/2/15.
 * 描述 :   系统常量
 */

public class AppConstant {


    // 是否第一次进入APP
    public final static String SPLASH_FIRST = "SPLASH_FIRST";
    //当前App是否是脱机模式
    public final static boolean IS_OFFLINEMODE=true;
    //脱机模式下的亲好友保存key
    public final static String kEY_FREIEND="kEY_FREIEND";
    // 当前用户信息
    public final static String USER_INFO = "USER_INFO";

    // 温度单位(℃/℉)开关
    public static final String SP_TEMP_SWITCH = "SP_TEMP_SWITCH";

    // 宝宝列表信息
    public final static String BB_LIST_INFO = "BB_LIST_INFO";

    // 宝宝操作状态 增、删、改、已同步
    public final static String BB_CONTROL_STATUS_ADD = "BB_CONTROL_STATUS_ADD";
    public final static String BB_CONTROL_STATUS_UPDATE = "BB_CONTROL_STATUS_UPDATE";
    public final static String BB_CONTROL_STATUS_DEL = "BB_CONTROL_STATUS_DEL";
    public final static String BB_CONTROL_STATUS_NORMAL = "BB_CONTROL_STATUS_NORMAL";

    // 用药提醒、喝水提醒
    public final static String ALERT_MEDICINE_STATUS = "ALERT_MEDICINE_STATUS";
    public final static String ALERT_WATER_STATUS = "ALERT_WATER_STATUS";
    // 超过37.5的数据
    public final static String HEIGHT_TEMP_FLAG = "HEIGHT_TEMP_FLAG";
    // 厂商名称
    public final static String MANUFACTURER_NAME_Tag = "MANUFACTURER_NAME_Tag";
    // 设备序列号
    public final static String PRODUCT_SEQUENCE_Tag = "PRODUCT_SEQUENCE_Tag";
    // 设备型号
    public final static String PRODUCT_MODELS_Tag = "PRODUCT_MODELS_Tag";
    // 硬件版本
    public final static String HARD_WARE_Tag = "HARD_WARE_Tag";
    // 软件版本
    public final static String SOFT_WARE_Tag = "SOFT_WARE_Tag";
    // 固件版本
    public final static String FIRM_WARE_Tag = "FIRM_WARE_Tag";
    // 协议版本
    public final static String PROTOCOL_Tag = "PROTOCOL_Tag";
    // 校准密码
    public static final String Adjust_Pwd = "Adjust_Pwd";
    //多个校准温度分隔符
    public static final String TEMP_SPLIT = "/";
    // 当前语言
    public static final String Language_set = "Language_set";
    // 当前选中放大的宝宝id
    public static final String Current_Scale_BB = "Current_Scale_BB";
    // 宝宝最大数量 实际是5个多出一个按钮的
    public static final int Max_BB_Info = 6;

    //当前用户信息
    public static MainLoginResBean sMainLoginResBean;
    //当前选中宝宝
    public static BabyInfosBean mCurrentBabyInfo;
    // 当前设备连接情况
    public static boolean IsDevicesConn;
    // 当前报警温度
    public static String mCurrentAlertHeightTemp;
    // 当前体温最高值
    public static Double mRealTimeHeightTemp = 0d;
    // 当前分享的数据
    public static ShareDataBean mShareDataBean;
    // 当前超高热报警时长
    public static Long mCurrentSuperHotTime = 0l;
    // 当前高温报警数据
    public static AlertHeightBean mAlertHeightBean;
    //当前是否低温状态
    public static boolean isLowTemp=true;
    //运动量化表数据
    public static String kEY_MOTION_LIST="MOTION_LIST";
    //校准数据保存
    public static String KEY_ADJUST="KEY_ADJUST";

    public static MainLoginResBean getUserInfo() {
        if (sMainLoginResBean != null) {
            return sMainLoginResBean;
        }
        if (getLocalUserInfo() != null) {
            sMainLoginResBean = getLocalUserInfo();
            return sMainLoginResBean;
        }
        return new MainLoginResBean();
    }

    /**
     * 保存用户信息
     *
     * @param user
     */
    public static void saveUserInfo(MainLoginResBean user) {
        SharedPreferencesUtil.set(USER_INFO, user);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    private static MainLoginResBean getLocalUserInfo() {
        return SharedPreferencesUtil.getObject(USER_INFO, MainLoginResBean.class);
    }

    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        if (getLocalUserInfo() != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取本地存储的宝宝列表
     */
    public static MineBBResBean getLocalBBListInfo() {

        return SharedPreferencesUtil.getObject(BB_LIST_INFO, MineBBResBean.class);
    }

    /**
     * 存储宝宝列表
     */
    public static void saveBBListInfo(MineBBResBean bbResBean) {
        SharedPreferencesUtil.set(BB_LIST_INFO, bbResBean);
    }


    /**
     * 比较当前是否是体温最高值
     */
    public static Double compareHeightTemp(Double temp) {
        // 取出对应的宝宝的信息 key:宝宝id
        BabyInfosBean mCurrentBabyInfo = AppConstant.mCurrentBabyInfo;
        if (mCurrentBabyInfo != null) {
            // 保存当前用户的温度值
            SharedPreferencesUtil.set(mCurrentBabyInfo.getLocalId(), String.valueOf(temp));
            return Double.valueOf(temp);
        }

        return 0d;
    }

    /**
     * 获取当前宝宝的最高体温值
     *
     * @return
     */
    public static String getCurrentBBHeightTemp() {
        // 取出对应的宝宝的信息 key:宝宝id
        BabyInfosBean mCurrentBabyInfo = AppConstant.mCurrentBabyInfo;
        String string = SharedPreferencesUtil.getString(mCurrentBabyInfo.getLocalId(), "0");
        return ConstantUtils.IsCelsiusUnit(string);
    }
    //*******************************************************蓝牙数据交互 命令字*********************************************

    public static final String SYS_BLUETOOTH_NAME = "FeverCat";
//        public static final String SYS_BLUETOOTH_NAME = "bk_temperature";
    public static final String SYS_BLUETOOTH_UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String SYS_BLUETOOTH_UUID_CHARA_NOTIFY = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String SYS_BLUETOOTH_UUID_CHARA_WRITE = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    //    public static final String SYS_BLUETOOTH_NAME = "LCeFM6017090007";
    //    public static final String SYS_BLUETOOTH_UUID_SERVICE = "0000fff0-0000-1000-8000-00805f9b34fb";
    //    public static final String SYS_BLUETOOTH_UUID_CHARA_NOTIFY = "0000fff1-0000-1000-8000-00805f9b34fb";
    //    public static final String SYS_BLUETOOTH_UUID_CHARA_WRITE = "0000fff2-0000-1000-8000-00805f9b34fb";
    public static final String SYS_BLUETOOTH_UUID_DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805f9b34fb";
    //当前设备mac 地址
    public static String SYS_CUR_DEVICE_ADDRESS = "";


    //发送:获取温度 - 命令字
    public static final String SYS_BLUETOOTH_TEMP_FLAG_WRITE = "0E";
    //响应:获取温度 - 命令字
    public static final String SYS_BLUETOOTH_TEMP_FLAG_READ = "0F";

    //发送:获取温度差值 - 命令字
    public static final String SYS_BLUETOOTH_TEMP_DIFFERENT_FLAG_WRITE = "10";
    //响应:获取温度差值 - 命令字
    public static final String SYS_BLUETOOTH_TEMP_DIFFERENT_FLAG_READ = "11";

    //发送:获取能力列表 - 命令字
    public static final String SYS_BLUETOOTH_DEVICES_LIST_FLAG_WRITE = "00";
    //响应:获取能力列表 - 命令字
    public static final String SYS_BLUETOOTH_DEVICES_LIST_FLAG_READ = "01";

    //发送:获取版本 - 命令字
    public static final String SYS_BLUETOOTH_VERSION_FLAG_WRITE = "02";
    //响应:获取版本、设备名称 - 命令字
    public static final String SYS_BLUETOOTH_VERSION_FLAG_READ = "03";


    //响应:获取电池、状态 - 命令字
    public static final String SYS_BLUETOOTH_BATTERY_FLAG_READ = "05";

    //发送:设置生产流水号 - 命令字
    public static final String SYS_BLUETOOTH_SET_SERIAL_FLAG_WRITE = "06";
    //响应:设置生产流水号 - 命令字
    public static final String SYS_BLUETOOTH_SET_SERIAL_FLAG_READ = "07";


    //响应:获取生产流水号 - 命令字
    public static final String SYS_BLUETOOTH_GET_SERIAL_FLAG_READ = "09";

    //发送:设置设备时间 - 命令字
    public static final String SYS_BLUETOOTH_SET_TIME_FLAG_WRITE = "0A";
    //响应:设置设备时间 - 命令字
    public static final String SYS_BLUETOOTH_SET_TIME_FLAG_READ = "0B";

    //发送:获取设备时间 - 命令字
    public static final String SYS_BLUETOOTH_GET_TIME_FLAG_WRITE = "0C";
    //响应:获取设备时间 - 命令字
    public static final String SYS_BLUETOOTH_GET_TIME_FLAG_READ = "0D";


    //发送:获取生产流水号 - 命令字 1
    public static final String SYS_BLUETOOTH_GET_SERIAL_FLAG_WRITE = "0x01";
    //发送：获取设备厂商名称 -命令字 2
    public static final String SYS_BLUETOOTH_GET_MANUFACTURER_NAME_FLAG_WRITE = "0x02";
    //发送：获取设备型号 -命令字 3
    public static final String SYS_BLUETOOTH_GET_MODELS_FLAG_WRITE = "0x03";
    //发送:获取电池、状态 - 命令字 4
    public static final String SYS_BLUETOOTH_BATTERY_FLAG_WRITE = "0x04";
    //发送:获取硬件版本 - 命令字 5
    public static final String SYS_BLUETOOTH_HARD_VERSION_FLAG_WRITE = "0x05";
    //发送:获取软件版本 - 命令字 6
    public static final String SYS_BLUETOOTH_SOFT_VERSION_FLAG_WRITE = "0x06";
    //发送:获取固件版本 - 命令字 7
    public static final String SYS_BLUETOOTH_FIRM_VERSION_FLAG_WRITE = "0x07";
    //发送:获取协议版本 - 命令字 8
    public static final String SYS_BLUETOOTH_PROTOCOL_VERSION_FLAG_WRITE = "0x08";
    //发送：校准 - 命令字 9
    public static final String SYS_BLUETOOTH_ADJUST_FLAG_WRITE = "0x09";


    // 设备响应头部 电池
    public static final String DEVICES_BATTERY_FLAG = "FF060435";
    // 设备响应头部 温度
    public static final String DEVICES_TEMP_FLAG = "FF080431";
    // 设备响应头部 厂商名称
    public static final String MANUFACTURER_NAME_FLAG = "FF110434";
    // 设备响应头部 设备序列号
    public static final String PRODUCT_SEQUENCE_FLAG = "FF0A0432";
    // 设备响应头部 设备型号
    public static final String PRODUCT_MODELS_FLAG = "FF0D0436";
    // 设备响应头部 硬件版本
    public static final String HARD_WARE_VERSION_FLAG = "FF0A043302";
    // 设备响应头部 软件版本
    public static final String SOFT_WARE_VERSION_FLAG = "FF0A043300";
    // 设备响应头部 固件版本
    public static final String FIRM_WARE_VERSION_FLAG = "FF0B043303";
    // 设备响应头部 协议版本
    public static final String PROTOCOL_VERSION_FLAG = "FF0A043301";
    // 设备响应头部 校准
    public static final String ADJUST_FLAG = "FF04040A";


}

