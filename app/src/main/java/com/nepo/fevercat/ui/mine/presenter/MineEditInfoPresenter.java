package com.nepo.fevercat.ui.mine.presenter;

import com.nepo.fevercat.R;
import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.common.utils.NetWorkUtils;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.mine.bean.MineEditReqBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.mine.contract.MineEditContract;

import java.io.File;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.presenter
 * 文件名:  MineEditInfoPresenter
 * 作者 :   <sen>
 * 时间 :  下午5:31 2017/6/24.
 * 描述 :
 */

public class MineEditInfoPresenter extends MineEditContract.Presenter {
    @Override
    public void putFileInfoRequest(File file, boolean isShow) {

        if (!NetWorkUtils.isNetConnected(mContext)) {
            ToastUtils.showToast(mContext.getString(R.string.no_net));
            return;
        }

        mRxManage.add(mModel.putFile(file)
                .subscribe(new RxSubscriber<MineUploadResBean>(mContext,isShow) {
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

    @Override
    public void putMineEditInfoRequest(MineEditReqBean mineEditReqBean, boolean isShow) {

        if (!NetWorkUtils.isNetConnected(mContext)) {
            ToastUtils.showToast(mContext.getString(R.string.no_net));
            return;
        }

        mRxManage.add(mModel.putMineEditInfo(mineEditReqBean)
                .subscribe(new RxSubscriber<BaseResBean>(mContext,isShow) {
                    @Override
                    protected void _onNext(BaseResBean baseResBean) {
                        mView.returnPutInfo(baseResBean);

                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));
    }
}
