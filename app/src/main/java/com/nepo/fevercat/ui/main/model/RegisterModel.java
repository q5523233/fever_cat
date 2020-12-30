package com.nepo.fevercat.ui.main.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainRegisterReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;
import com.nepo.fevercat.ui.main.contract.RegisterContract;

import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.model
 * 文件名:  RegisterModel
 * 作者 :   <sen>
 * 时间 :  下午3:08 2017/6/20.
 * 描述 :
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<BaseResBean> getRegisterInfo(MainRegisterReqBean registerReqBean) {
        return Api.getDefault().getRegisterInfo(registerReqBean).compose(RxSchedulers.<BaseResBean>io_main());
    }

    @Override
    public Observable<MainValidateResBean> getCodeInfo(MainValidateReqBean mainValidateReqBean) {
        return Api.getDefault().getCodeInfo(mainValidateReqBean).compose(RxSchedulers.<MainValidateResBean>io_main());
    }
}
