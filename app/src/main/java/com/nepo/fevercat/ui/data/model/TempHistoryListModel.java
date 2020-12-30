package com.nepo.fevercat.ui.data.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataReqBean;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;
import com.nepo.fevercat.ui.data.bean.TempOneDayDataResBean;
import com.nepo.fevercat.ui.data.contract.TempHistoryListContract;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;

import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.model
 * 文件名:  TempHistoryListModel
 * 作者 :   <sen>
 * 时间 :  上午9:46 2017/7/4.
 * 描述 :
 */

public class TempHistoryListModel implements TempHistoryListContract.Model {

    @Override
    public Observable<TempHistoryDataResBean> getHistoryListInfo(TempHistoryDataReqBean tempHistoryDataReqBean) {
        return Api.getDefault().getHistoryListInfo(tempHistoryDataReqBean).compose(RxSchedulers.<TempHistoryDataResBean>io_main());
    }

    @Override
    public Observable<MineBBResBean> putMineBBInfo(MineBBReqBean mineBBReqBean) {
        return Api.getDefault().putMineBBInfo(mineBBReqBean).compose(new RxSchedulers().<MineBBResBean>io_main());
    }

    @Override
    public Observable<TempOneDayDataResBean> getTempOneDayInfo(TempHistoryDataReqBean tempHistoryDataReqBean) {
        return Api.getDefault().getOneDayDataInfo(tempHistoryDataReqBean).compose(RxSchedulers.<TempOneDayDataResBean>io_main());
    }
}
