package com.nepo.fevercat.ui.real.presenter;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.common.utils.NetWorkUtils;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.real.contract.RealTimeContract;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.presenter
 * 文件名:  MineEditInfoPresenter
 * 作者 :   <sen>
 * 时间 :  下午5:31 2017/6/24.
 * 描述 :
 */

public class RealTimePresenter extends RealTimeContract.Presenter {

    @Override
    public void putMineBBInfoRequest(MineBBReqBean mineBBReqBean, boolean isShow, final boolean isFirstLoad) {

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
