package com.nepo.fevercat.ui.follow.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.follow.bean.FollowAddReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowEditReqBean;
import com.nepo.fevercat.ui.follow.contract.FollowAddContract;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;

import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.model
 * 文件名:  FollowModel
 * 作者 :   <sen>
 * 时间 :  下午5:12 2017/6/23.
 * 描述 :
 */

public class FollowModel implements FollowAddContract.Model {
    @Override
    public Observable<MainValidateResBean> getCodeInfo(MainValidateReqBean mainValidateReqBean) {
        return Api.getDefault().getCodeInfo(mainValidateReqBean).compose(RxSchedulers.<MainValidateResBean>io_main());
    }

    @Override
    public Observable<BaseResBean> putFriendInfo(FollowAddReqBean followAddReqBean) {
        return Api.getDefault().putFriendInfo(followAddReqBean).compose(RxSchedulers.<BaseResBean>io_main());
    }

    @Override
    public Observable<BaseResBean> editFollowInfo(FollowEditReqBean followEditReqBean) {
        return Api.getDefault().editFollowInfo(followEditReqBean).compose(RxSchedulers.<BaseResBean>io_main());
    }
}
