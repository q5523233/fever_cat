package com.nepo.fevercat.ui.mine.presenter;

import android.text.TextUtils;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.common.utils.NetWorkUtils;
import com.nepo.fevercat.common.utils.StringUtils;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.mine.contract.MineBBInfoContract;

import java.io.File;
import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.presenter
 * 文件名:  MineEditInfoPresenter
 * 作者 :   <sen>
 * 时间 :  下午5:31 2017/6/24.
 * 描述 :
 */

public class MineBBInfoPresenter extends MineBBInfoContract.Presenter {

    @Override
    public void putMineBBInfoRequest(MineBBReqBean mineBBReqBean, boolean isShow) {

        // 判断有无网络 true 请求网络 false 保存到本地
        if (NetWorkUtils.isNetConnected(mContext)&&!AppConstant.IS_OFFLINEMODE) {
            mRxManage.add(mModel.putMineBBInfo(mineBBReqBean)
                    .subscribe(new RxSubscriber<MineBBResBean>(mContext, isShow) {
                        @Override
                        protected void _onNext(MineBBResBean mineBBResBean) {
                            mView.returnBBInfo(mineBBResBean);

                        }

                        @Override
                        protected void _onError(String message) {
                            mView.returnErrInfo(message);
                        }
                    }));
        }
        // 更新宝宝信息数据库
        if (TextUtils.equals(mineBBReqBean.getOperateID(), ApiConstants.MODIFY_BABY_LIST_4)) {
            //查
            List<BabyInfosBean> babyListInfo = GreenDaoUtils.getInstance(mContext).getBabyListInfo();
            MineBBResBean mineBBResBean = new MineBBResBean();
            mineBBResBean.setCode("0");
            mineBBResBean.setBabyInfos(babyListInfo);
            mView.returnBBInfo(mineBBResBean);
        }else if(TextUtils.equals(mineBBReqBean.getOperateID(), ApiConstants.MODIFY_BABY_ADD_1)){
            // 增
            BabyInfosBean babyInfosBean = new BabyInfosBean();
            babyInfosBean.setBabyId("None");
            babyInfosBean.setBirthday(mineBBReqBean.getBirthday());
            babyInfosBean.setAccountId(mineBBReqBean.getAccountId());
            babyInfosBean.setControlStatus(AppConstant.BB_CONTROL_STATUS_ADD);
            babyInfosBean.setHeadImageUrl(mineBBReqBean.getLocalImgUrl());
            babyInfosBean.setHeadImageId(mineBBReqBean.getHeadImageId());
            babyInfosBean.setLocalId(StringUtils.getGenerateUUID());
            babyInfosBean.setNickname(mineBBReqBean.getNickname());
            babyInfosBean.setSex(mineBBReqBean.getSex());
            GreenDaoUtils.getInstance(mContext).addBabyControlStatus(babyInfosBean);
            MineBBResBean mineBBResBean = new MineBBResBean();
            mineBBResBean.setCode("0");
            mView.returnBBInfo(mineBBResBean);

        }else if(TextUtils.equals(mineBBReqBean.getOperateID(), ApiConstants.MODIFY_BABY_UPDATE_2)){
            // 改
//            if (!TextUtils.isEmpty(mineBBReqBean.getLocalUUID())) {
//                babyInfosBean = GreenDaoUtils.getInstance(mContext).getSingleBabyInfo(mineBBReqBean.getLocalUUID());
//            }else{
//                babyInfosBean = GreenDaoUtils.getInstance(mContext).getSingleBabyInfoByBabyID(mineBBReqBean.getBabyId());
//            }
            BabyInfosBean babyInfosBean =  GreenDaoUtils.getInstance(mContext).getSingleByID(mineBBReqBean.getId());
            babyInfosBean.setAccountId(mineBBReqBean.getAccountId());
            babyInfosBean.setBabyId(mineBBReqBean.getBabyId());
            babyInfosBean.setBirthday(mineBBReqBean.getBirthday());
            babyInfosBean.setControlStatus(AppConstant.BB_CONTROL_STATUS_UPDATE);
            babyInfosBean.setHeadImageUrl(mineBBReqBean.getLocalImgUrl());
            babyInfosBean.setHeadImageId(mineBBReqBean.getHeadImageId());
            babyInfosBean.setNickname(mineBBReqBean.getNickname());
            babyInfosBean.setSex(mineBBReqBean.getSex());
            GreenDaoUtils.getInstance(mContext).updateBabyControlStatus(babyInfosBean);
            MineBBResBean mineBBResBean = new MineBBResBean();
            mineBBResBean.setCode("0");
            mView.returnBBInfo(mineBBResBean);

        }else if(TextUtils.equals(mineBBReqBean.getOperateID(), ApiConstants.MODIFY_BABY_DEL_3)){
            //删
            GreenDaoUtils.getInstance(mContext).delBabyInfo(mineBBReqBean.getLocalUUID(),mineBBReqBean.getBabyId());
            // 删除宝宝对应的温度数据
            GreenDaoUtils.getInstance(mContext).delTempInfoByBabyLocalID(mineBBReqBean.getId()+"");
            MineBBResBean mineBBResBean = new MineBBResBean();
            mineBBResBean.setCode("0");
            mView.returnBBInfo(mineBBResBean);
        }


    }

    @Override
    public void putFileInfoRequest(File file, boolean isShow) {
        if (NetWorkUtils.isNetConnected(mContext)) {
            mRxManage.add(mModel.putFile(file)
                    .subscribe(new RxSubscriber<MineUploadResBean>(mContext, isShow) {
                        @Override
                        protected void _onNext(MineUploadResBean mineUploadResBean) {
                            mView.returnUploadInfo(mineUploadResBean);

                        }

                        @Override
                        protected void _onError(String message) {
                            mView.returnErrInfo(message);
                        }
                    }));
        }

    }
}
