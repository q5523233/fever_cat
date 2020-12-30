package com.nepo.fevercat.ui.mine.model;

import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.mine.bean.MineEditReqBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.mine.contract.MineEditContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.model
 * 文件名:  MineEditInfoModel
 * 作者 :   <sen>
 * 时间 :  下午5:31 2017/6/24.
 * 描述 :
 */

public class MineEditInfoModel implements MineEditContract.Model {
    @Override
    public Observable<MineUploadResBean> putFile(File fileList) {
        return Api.getDefault().uploadFilesWithParts(filesToMultipartBodyParts(fileList)).compose(RxSchedulers.<MineUploadResBean>io_main());
    }

    @Override
    public Observable<BaseResBean> putMineEditInfo(MineEditReqBean mineEditReqBean) {
        return Api.getDefault().putMineInfo(mineEditReqBean).compose(new RxSchedulers().<BaseResBean>io_main());
    }

    /**
     * 封装文件列表
     * @param file
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(File file) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("headImage", file.getName(), requestBody);
        parts.add(part);

        return parts;
    }


}
