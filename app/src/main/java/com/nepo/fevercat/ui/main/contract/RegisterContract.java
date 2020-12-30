package com.nepo.fevercat.ui.main.contract;


import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainRegisterReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;

import rx.Observable;

/**
 * 项目名: KaKa
 * 包名 :  com.nepo.kaka.ui.main.contract
 * 文件名:  MainContract
 * 作者 :   <sen>
 * 时间 :  下午4:28 2017/5/12.
 * 描述 :  注册
 */

public interface RegisterContract {

    interface Model extends BaseModel {
        // 注册
        Observable<BaseResBean> getRegisterInfo(MainRegisterReqBean registerReqBean);
        // 获取验证码
        Observable<MainValidateResBean> getCodeInfo(MainValidateReqBean mainValidateReqBean);
    }

    interface View extends BaseView {
        void returnCodeInfo(MainValidateResBean mainValidateResBean);
        void returnRegisterInfo(BaseResBean baseResBean);
        void returnErrInfo(String errMsg);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void getRegisterInfoRequest(MainRegisterReqBean registerReqBean,boolean isShow);
        public abstract void getCodeInfoRequest(MainValidateReqBean mainValidateReqBean,boolean isShow);
    }

}
