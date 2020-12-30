package com.nepo.fevercat.ui.alert.presenter;

import android.util.Log;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.common.utils.NetWorkUtils;
import com.nepo.fevercat.ui.alert.contract.AlertHeightAddContract;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;

import java.util.List;

/**
 * Created by shenmai8 on 2018/4/23.
 */

public class AlertHeightPresenter extends AlertHeightAddContract.Presenter {
    @Override
    public void putMineBBInfoRequest(MineBBReqBean mineBBReqBean, boolean isShow, boolean isFirstLoad) {
        // 判断有无网络 true 请求网络 false 保存到服务器
        if (NetWorkUtils.isNetConnected(mContext) && AppConstant.isLogin()) {
            // 查询本地宝宝信息
            mRxManage.add(mModel.putMineBBInfo(mineBBReqBean)
                    .subscribe(new RxSubscriber<MineBBResBean>(mContext,isShow) {
                        @Override
                        protected void _onNext(MineBBResBean mineBBResBean) {
                            // 获取本地有SID(服务器ID)并状态为正常的数据 用服务器数据进行匹配覆盖
                            // 并 查询本地列表返回
                            List<BabyInfosBean> babyListInfo = GreenDaoUtils.getInstance(mContext).updateCoverLocalByServer(mineBBResBean);
                            mineBBResBean = new MineBBResBean();
                            mineBBResBean.setCode("0");
                            mineBBResBean.setBabyInfos(babyListInfo);
                            mView.returnBBInfo(mineBBResBean);
                        }

                        @Override
                        protected void _onError(String message) {
//                            mView.returnErrInfo(message);
                            List<BabyInfosBean> babyListInfo = GreenDaoUtils.getInstance(mContext).getBabyListInfo();
                            MineBBResBean mineBBResBean = new MineBBResBean();
                            mineBBResBean.setCode("0");
                            mineBBResBean.setBabyInfos(babyListInfo);
                            mView.returnBBInfo(mineBBResBean);

                        }
                    }));
        }else{
            //查
            List<BabyInfosBean> babyListInfo = GreenDaoUtils.getInstance(mContext).getBabyListInfo();
            MineBBResBean mineBBResBean = new MineBBResBean();
            mineBBResBean.setCode("0");
            mineBBResBean.setBabyInfos(babyListInfo);
            mView.returnBBInfo(mineBBResBean);
        }
    }
}
