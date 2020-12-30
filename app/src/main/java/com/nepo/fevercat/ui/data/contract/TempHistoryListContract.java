package com.nepo.fevercat.ui.data.contract;


import com.nepo.fevercat.base.BaseModel;
import com.nepo.fevercat.base.BasePresenter;
import com.nepo.fevercat.base.BaseView;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataReqBean;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;
import com.nepo.fevercat.ui.data.bean.TempOneDayDataResBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;

import rx.Observable;

/**
 * 项目名: KaKa
 * 包名 :  com.nepo.kaka.ui.main.contract
 * 文件名:  MainContract
 * 作者 :   <sen>
 * 时间 :  下午4:28 2017/5/12.
 * 描述 :  温度 历史记录 操作
 */

public interface TempHistoryListContract {

    interface Model extends BaseModel {
        // 获取历史记录列表
        Observable<TempHistoryDataResBean> getHistoryListInfo(TempHistoryDataReqBean tempHistoryDataReqBean);
        // 编辑宝宝信息
        Observable<MineBBResBean> putMineBBInfo(MineBBReqBean mineBBReqBean);
        // 获取某一天数据
        Observable<TempOneDayDataResBean> getTempOneDayInfo(TempHistoryDataReqBean tempHistoryDataReqBean);
    }

    interface View extends BaseView {
        void returnHistoryListInfo(TempHistoryDataResBean tempHistoryDataResBean);
        void returnBBInfo(MineBBResBean mineBBResBean);
        void returnBBOneDayInfo(TempOneDayDataResBean baseResBean);
        void returnErrInfo(String errMsg);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void getHistoryListInfoRequest(TempHistoryDataReqBean tempHistoryDataReqBean,boolean isShow);
        public abstract void putMineBBInfoRequest(MineBBReqBean mineBBReqBean);
        public abstract void getTempOneDayInfoRequest(TempHistoryDataReqBean tempHistoryDataReqBean, boolean isShow);
    }

}
