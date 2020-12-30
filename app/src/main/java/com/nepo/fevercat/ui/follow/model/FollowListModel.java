package com.nepo.fevercat.ui.follow.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.follow.bean.FollowListReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;
import com.nepo.fevercat.ui.follow.contract.FollowListContract;
import com.nepo.fevercat.ui.main.bean.BaseResBean;

import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.model
 * 文件名:  FollowListModel
 * 作者 :   <sen>
 * 时间 :  上午11:59 2017/6/24.
 * 描述 :
 */

public class FollowListModel implements FollowListContract.Model {
    @Override
    public Observable<FollowListResBean> getFollowListInfo(FollowListReqBean followListReqBean) {
        return Api.getDefault().getFollowListInfo(followListReqBean).compose(RxSchedulers.<FollowListResBean>io_main());
    }

    @Override
    public Observable<BaseResBean> putFollowInfo(FollowListReqBean followListReqBean) {
        return Api.getDefault().putFollowInfo(followListReqBean).compose(RxSchedulers.<BaseResBean>io_main());
    }
}
