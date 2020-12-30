package com.nepo.fevercat.ui.main.presenter;

import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.ui.main.bean.MainLoginReqBean;
import com.nepo.fevercat.ui.main.bean.MainLoginResBean;
import com.nepo.fevercat.ui.main.contract.LoginContract;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.presenter
 * 文件名:  RegisterPresenter
 * 作者 :   <sen>
 * 时间 :  下午3:10 2017/6/20.
 * 描述 :
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void getLoginInfoRequest(MainLoginReqBean loginReqBean, boolean isShow) {
        mRxManage.add(mModel.getLoginInfo(loginReqBean)
                .subscribe(new RxSubscriber<MainLoginResBean>(mContext,isShow) {
                    @Override
                    protected void _onNext(MainLoginResBean loginResBean) {
                        mView.returnLoginInfo(loginResBean);

                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));
    }
}
