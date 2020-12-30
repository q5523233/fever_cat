package com.nepo.fevercat.ui.follow.contract;


import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.follow.bean.FollowAddReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowEditReqBean;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;

import rx.Observable;

/**
 * 项目名: KaKa
 * 包名 :  com.nepo.kaka.ui.main.contract
 * 文件名:  MainContract
 * 作者 :   <sen>
 * 时间 :  下午4:28 2017/5/12.
 * 描述 :  添加好友
 */

public interface FollowAddContract {

    interface Model extends BaseModel {
        // 获取验证码
        Observable<MainValidateResBean> getCodeInfo(MainValidateReqBean mainValidateReqBean);
        // 添加好友
        Observable<BaseResBean> putFriendInfo(FollowAddReqBean followAddReqBean);
        // 编辑关注信息
        Observable<BaseResBean> editFollowInfo(FollowEditReqBean followEditReqBean);

    }

    interface View extends BaseView {
        void returnCodeInfo(MainValidateResBean mainValidateResBean);
        void returnPutInfo(BaseResBean baseResBean);
        void returnErrInfo(String errMsg);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void getCodeInfoRequest(MainValidateReqBean mainValidateReqBean,boolean isShow);
        public abstract void putFriendInfoRequest(FollowAddReqBean followAddReqBean, boolean isShow);
        public abstract void EditFollowInfoRequest(FollowEditReqBean followEditReqBean, boolean isShow);
    }

}
