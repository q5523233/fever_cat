package com.nepo.fevercat.ui.main.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.main.bean.MainLoginReqBean;
import com.nepo.fevercat.ui.main.bean.MainLoginResBean;
import com.nepo.fevercat.ui.main.contract.LoginContract;

import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.model
 * 文件名:  RegisterModel
 * 作者 :   <sen>
 * 时间 :  下午3:08 2017/6/20.
 * 描述 :
 */

public class LoginModel implements LoginContract.Model {


    @Override
    public Observable<MainLoginResBean> getLoginInfo(MainLoginReqBean loginReqBean) {
        return Api.getDefault().getLoginInfo(loginReqBean).compose(RxSchedulers.<MainLoginResBean>io_main());
    }
}
