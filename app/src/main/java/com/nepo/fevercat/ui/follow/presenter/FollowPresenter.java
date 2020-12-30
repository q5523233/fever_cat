package com.nepo.fevercat.ui.follow.presenter;

import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.ui.follow.bean.FollowAddReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowEditReqBean;
import com.nepo.fevercat.ui.follow.contract.FollowAddContract;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.presenter
 * 文件名:  FollowPresenter
 * 作者 :   <sen>
 * 时间 :  下午5:13 2017/6/23.
 * 描述 :
 */

public class FollowPresenter extends FollowAddContract.Presenter {
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

    @Override
    public void putFriendInfoRequest(FollowAddReqBean followAddReqBean, boolean isShow) {
        mRxManage.add(mModel.putFriendInfo(followAddReqBean)
                .subscribe(new RxSubscriber<BaseResBean>(mContext,isShow) {
                    @Override
                    protected void _onNext(BaseResBean baseResBean) {
                        mView.returnPutInfo(baseResBean);

                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));
    }

    @Override
    public void EditFollowInfoRequest(FollowEditReqBean followEditReqBean, boolean isShow) {
        mRxManage.add(mModel.editFollowInfo(followEditReqBean)
                .subscribe(new RxSubscriber<BaseResBean>(mContext,isShow) {
                    @Override
                    protected void _onNext(BaseResBean baseResBean) {
                        mView.returnPutInfo(baseResBean);

                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));
    }
}
