package com.nepo.fevercat.ui.follow.contract;


import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.follow.bean.FollowListReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;
import com.nepo.fevercat.ui.main.bean.BaseResBean;

import rx.Observable;

/**
 * 项目名: KaKa
 * 包名 :  com.nepo.kaka.ui.main.contract
 * 文件名:  MainContract
 * 作者 :   <sen>
 * 时间 :  下午4:28 2017/5/12.
 * 描述 :  好友列表 操作
 */

public interface FollowListContract {

    interface Model extends BaseModel {
        // 获取好友列表
        Observable<FollowListResBean> getFollowListInfo(FollowListReqBean followListReqBean);
        // 删除好友
        Observable<BaseResBean> putFollowInfo(FollowListReqBean followListReqBean);
    }

    interface View extends BaseView {
        void returnListInfo(FollowListResBean followListResBean);
        void returnPutInfo(BaseResBean baseResBean);
        void returnErrInfo(String errMsg);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void getFollowListInfoRequest(FollowListReqBean followListReqBean,boolean isShow);
        public abstract void putFollowInfoRequest(FollowListReqBean followListReqBean, boolean isShow);
    }

}
