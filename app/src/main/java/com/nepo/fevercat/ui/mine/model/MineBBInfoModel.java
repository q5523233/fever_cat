package com.nepo.fevercat.ui.mine.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.common.utils.Utils;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.mine.contract.MineBBInfoContract;

import java.io.File;

import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.model
 * 文件名:  MineBBInfoModel
 * 作者 :   <sen>
 * 时间 :  下午4:25 2017/6/27.
 * 描述 :
 */

public class MineBBInfoModel implements MineBBInfoContract.Model {


    @Override
    public Observable<MineBBResBean> putMineBBInfo(MineBBReqBean mineBBReqBean) {

        return Api.getDefault().putMineBBInfo(mineBBReqBean).compose(new RxSchedulers().<MineBBResBean>io_main());
    }

    @Override
    public Observable<MineUploadResBean> putFile(File fileList) {
        return Api.getDefault().uploadFilesWithParts(Utils.filesToMultipartBodyParts(fileList)).compose(RxSchedulers.<MineUploadResBean>io_main());
    }




}
