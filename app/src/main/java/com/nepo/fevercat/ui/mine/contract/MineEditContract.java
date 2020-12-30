package com.nepo.fevercat.ui.mine.contract;


import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.mine.bean.MineEditReqBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;

import java.io.File;

import rx.Observable;

/**
 * 项目名: KaKa
 * 包名 :  com.nepo.kaka.ui.main.contract
 * 文件名:  MainContract
 * 作者 :   <sen>
 * 时间 :  下午4:28 2017/5/12.
 * 描述 :  添加好友
 */

public interface MineEditContract {

    interface Model extends BaseModel {
        // 上传头像
        Observable<MineUploadResBean> putFile(File fileList);
        // 修改信息
        Observable<BaseResBean> putMineEditInfo(MineEditReqBean mineEditReqBean);
    }

    interface View extends BaseView {
        void returnUploadInfo(MineUploadResBean mineUploadResBean);
        void returnPutInfo(BaseResBean baseResBean);
        void returnErrInfo(String errMsg);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void putFileInfoRequest(File file,boolean isShow);
        public abstract void putMineEditInfoRequest(MineEditReqBean mineEditReqBean, boolean isShow);
    }

}
