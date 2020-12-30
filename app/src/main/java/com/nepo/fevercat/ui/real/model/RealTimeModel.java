package com.nepo.fevercat.ui.real.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.real.contract.RealTimeContract;

import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.model
 * 文件名:  MineBBInfoModel
 * 作者 :   <sen>
 * 时间 :  下午4:25 2017/6/27.
 * 描述 :
 */

public class RealTimeModel implements RealTimeContract.Model {
    @Override
    public Observable<MineBBResBean> putMineBBInfo(MineBBReqBean mineBBReqBean) {
        return Api.getDefault().putMineBBInfo(mineBBReqBean).compose(new RxSchedulers().<MineBBResBean>io_main());
    }
}
