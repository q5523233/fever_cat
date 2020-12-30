package com.nepo.fevercat.ui.alert.contract;

import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;

import rx.Observable;

/**
 * Created by shenmai8 on 2018/4/23.
 */

public interface AlertHeightAddContract {
    interface Model extends BaseModel {
        // 编辑宝宝信息
        Observable<MineBBResBean> putMineBBInfo(MineBBReqBean mineBBReqBean);
    }


    interface View extends BaseView {
        void returnBBInfo(MineBBResBean mineBBResBean);
        void returnErrInfo(String err);
    }

    abstract class Presenter extends BasePresenter<AlertHeightAddContract.View,AlertHeightAddContract.Model> {
        public abstract void putMineBBInfoRequest(MineBBReqBean mineBBReqBean, boolean isShow,boolean isFirstLoad);
    }
}
