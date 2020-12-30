package com.nepo.fevercat.ui.follow.presenter;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.ui.follow.bean.FollowListReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;
import com.nepo.fevercat.ui.follow.contract.FollowListContract;
import com.nepo.fevercat.ui.main.bean.BaseResBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.presenter
 * 文件名:  FollowListPresenter
 * 作者 :   <sen>
 * 时间 :  上午11:59 2017/6/24.
 * 描述 :
 */

public class FollowListPresenter extends FollowListContract.Presenter {
    @Override
    public void getFollowListInfoRequest(FollowListReqBean followListReqBean, boolean isShow) {
        if (AppConstant.IS_OFFLINEMODE)
        {
            FollowListResBean followListResBean = SharedPreferencesUtil.getObject(AppConstant.kEY_FREIEND, FollowListResBean.class);
            if (followListResBean==null)
            {
                followListResBean=new FollowListResBean();
                followListResBean.setCode("0");
            }
            mView.returnListInfo(followListResBean);
            return;
        }
        mRxManage.add(mModel.getFollowListInfo(followListReqBean)
                .subscribe(new RxSubscriber<FollowListResBean>(mContext,isShow) {
                    @Override
                    protected void _onNext(FollowListResBean followListResBean) {
                        mView.returnListInfo(followListResBean);

                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));
    }

    @Override
    public void putFollowInfoRequest(FollowListReqBean followListReqBean, boolean isShow) {
        if (AppConstant.IS_OFFLINEMODE)
        {
            return;
        }
        mRxManage.add(mModel.putFollowInfo(followListReqBean)
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

    public void deleteLocalInfo(FollowListResBean.BindUsersBean bindUsersBean) {
        if (AppConstant.IS_OFFLINEMODE)
        {
            FollowListResBean followListResBean = SharedPreferencesUtil.getObject(AppConstant.kEY_FREIEND, FollowListResBean.class);
            if (followListResBean==null)
            {
                followListResBean=new FollowListResBean();
                followListResBean.setCode("0");
            }else {
                followListResBean.getBindUsers().remove(bindUsersBean);
                SharedPreferencesUtil.set(AppConstant.kEY_FREIEND, followListResBean);
            }
            mView.returnListInfo(followListResBean);
        }
    }
}
