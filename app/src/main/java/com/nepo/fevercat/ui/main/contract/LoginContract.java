package com.nepo.fevercat.ui.main.contract;


import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.main.bean.MainLoginReqBean;
import com.nepo.fevercat.ui.main.bean.MainLoginResBean;

import rx.Observable;

/**
 * 项目名: KaKa
 * 包名 :  com.nepo.kaka.ui.main.contract
 * 文件名:  MainContract
 * 作者 :   <sen>
 * 时间 :  下午4:28 2017/5/12.
 * 描述 :  注册
 */

public interface LoginContract {

    interface Model extends BaseModel {
        // 注册
        Observable<MainLoginResBean> getLoginInfo(MainLoginReqBean loginReqBean);
    }

    interface View extends BaseView {
        void returnLoginInfo(MainLoginResBean loginResBean);
        void returnErrInfo(String errMsg);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void getLoginInfoRequest(MainLoginReqBean loginReqBean,boolean isShow);
    }

}
