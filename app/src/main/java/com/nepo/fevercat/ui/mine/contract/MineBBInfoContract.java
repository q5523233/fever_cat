package com.nepo.fevercat.ui.mine.contract;


import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;

import java.io.File;

import rx.Observable;

/**
 * 项目名: KaKa
 * 包名 :  com.nepo.kaka.ui.main.contract
 * 文件名:  MainContract
 * 作者 :   <sen>
 * 时间 :  下午4:28 2017/5/12.
 * 描述 :  宝宝信息
 */

public interface MineBBInfoContract {

    interface Model extends BaseModel {
        // 编辑宝宝信息
        Observable<MineBBResBean> putMineBBInfo(MineBBReqBean mineBBReqBean);
        // 上传头像
        Observable<MineUploadResBean> putFile(File fileList);
    }

    interface View extends BaseView {
        void returnBBInfo(MineBBResBean mineBBResBean);
        void returnUploadInfo(MineUploadResBean mineUploadResBean);
        void returnErrInfo(String errMsg);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void putMineBBInfoRequest(MineBBReqBean mineBBReqBean, boolean isShow);
        public abstract void putFileInfoRequest(File file,boolean isShow);
    }

}
