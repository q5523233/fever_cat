package com.nepo.fevercat.ui.main.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainForgetReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;
import com.nepo.fevercat.ui.main.contract.ForgetContract;

import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.model
 * 文件名:  RegisterModel
 * 作者 :   <sen>
 * 时间 :  下午3:08 2017/6/20.
 * 描述 :
 */

public class ForgetModel implements ForgetContract.Model {

    @Override
    public Observable<BaseResBean> getForgetPwdInfo(MainForgetReqBean forgetReqBean) {
        return Api.getDefault().getForgetPwdInfo(forgetReqBean).compose(RxSchedulers.<BaseResBean>io_main());
    }

    @Override
    public Observable<MainValidateResBean> getCodeInfo(MainValidateReqBean mainValidateReqBean) {
        return Api.getDefault().getCodeInfo(mainValidateReqBean).compose(RxSchedulers.<MainValidateResBean>io_main());
    }
}
