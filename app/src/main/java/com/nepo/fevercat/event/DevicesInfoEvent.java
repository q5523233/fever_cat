package com.nepo.fevercat.event;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.event
 * 文件名:  DevicesInfoEvent
 * 作者 :   <sen>
 * 时间 :  下午2:41 2017/9/14.
 * 描述 :  设备信息
 */

public class DevicesInfoEvent {

    public final static String MANUFACTURER_NAME_FLAG="MANUFACTURER_NAME_FLAG";
    public final static String PRODUCT_SEQUENCE_FLAG="PRODUCT_SEQUENCE_FLAG";
    public final static String PRODUCT_MODELS_FLAG="PRODUCT_MODELS_FLAG";
    public final static String HARD_WARE_VERSION_FLAG="HARD_WARE_VERSION_FLAG";
    public final static String SOFT_WARE_VERSION_FLAG="SOFT_WARE_VERSION_FLAG";
    public final static String FIRM_WARE_VERSION_FLAG="FIRM_WARE_VERSION_FLAG";
    public final static String PROTOCOL_VERSION_FLAG="PROTOCOL_VERSION_FLAG";


    private String tag;

    public String getTag() {
        return tag;
    }

    public DevicesInfoEvent setTag(String tag) {
        this.tag = tag;
        return this;
    }
}
