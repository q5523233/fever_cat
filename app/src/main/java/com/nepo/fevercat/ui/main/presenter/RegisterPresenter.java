package com.nepo.fevercat.ui.main.presenter;

import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainRegisterReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;
import com.nepo.fevercat.ui.main.contract.RegisterContract;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.main.presenter
 * 文件名:  RegisterPresenter
 * 作者 :   <sen>
 * 时间 :  下午3:10 2017/6/20.
 * 描述 :
 */

public class RegisterPresenter extends RegisterContract.Presenter {
    @Override
    public void getRegisterInfoRequest(MainRegisterReqBean registerReqBean, boolean isShow) {
        mRxManage.add(mModel.getRegisterInfo(registerReqBean)
                .subscribe(new RxSubscriber<BaseResBean>(mContext,isShow) {
                    @Override
                    protected void _onNext(BaseResBean baseResBean) {
                        mView.returnRegisterInfo(baseResBean);

                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));
    }

    @Override
    public void getCodeInfoRequest(MainValidateReqBean mainValidateReqBean, boolean isShow) {
        mRxManage.add(mModel.getCodeInfo(mainValidateReqBean)
                .subscribe(new RxSubscriber<MainValidateResBean>(mContext,isShow) {
                    @Override
                    protected void _onNext(MainValidateResBean loginResBean) {
                        mView.returnCodeInfo(loginResBean);

                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));
    }
}
