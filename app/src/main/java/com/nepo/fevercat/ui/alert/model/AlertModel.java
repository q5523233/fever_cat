package com.nepo.fevercat.ui.alert.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.alert.contract.AlertHeightAddContract;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;

import rx.Observable;

/**
 * Created by shenmai8 on 2018/4/23.
 */

public class AlertModel implements AlertHeightAddContract.Model {
    @Override
    public Observable<MineBBResBean> putMineBBInfo(MineBBReqBean mineBBReqBean) {
        return Api.getDefault().putMineBBInfo(mineBBReqBean).compose(new RxSchedulers().<MineBBResBean>io_main());
    }
}
