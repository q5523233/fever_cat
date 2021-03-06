package com.nepo.fevercat.ui.real.contract;


import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;

import rx.Observable;

/**
 * 项目名: KaKa
 * 包名 :  com.nepo.kaka.ui.main.contract
 * 文件名:  MainContract
 * 作者 :   <sen>
 * 时间 :  下午4:28 2017/5/12.
 * 描述 :  实时体温
 */

public interface RealTimeContract {

    interface Model extends BaseModel {
        // 编辑宝宝信息
        Observable<MineBBResBean> putMineBBInfo(MineBBReqBean mineBBReqBean);
    }

    interface View extends BaseView {
        void returnBBInfo(MineBBResBean mineBBResBean);
        void returnErrInfo(String err);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void putMineBBInfoRequest(MineBBReqBean mineBBReqBean, boolean isShow,boolean isFirstLoad);
    }

}
