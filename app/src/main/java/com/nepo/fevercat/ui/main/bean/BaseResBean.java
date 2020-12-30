package com.nepo.fevercat.ui.main.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.bean
 * 文件名:  BaseResBean
 * 作者 :   <sen>
 * 时间 :  下午3:03 2017/6/20.
 * 描述 :
 */

public class BaseResBean implements Serializable {

    private String code;
    private String msg;


    public String getCode() {
        return code;
    }

    public BaseResBean setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isOk(){
//        if (TextUtils.equals(code,"0")) {
//            return true;
//        }else{
//            String language = SharedPreferencesUtil.getString(AppConstant.Language_set,"zh");
//            if (TextUtils.equals(language,"zh")) {
//                ToastUtils.showToast(msg);
//            }
//            return false;
//        }
        return TextUtils.equals(code,"0");
    }

    @Override
    public String toString() {
        return "BaseResBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
