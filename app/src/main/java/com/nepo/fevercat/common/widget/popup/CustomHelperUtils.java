package com.nepo.fevercat.common.widget.popup;

import android.net.Uri;
import android.os.Environment;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.CropOptions;

import java.io.File;

/**
 * 项目名: TakeOutFood
 * 包名 :  com.nepo.takeoutfood.common.utils
 * 文件名:  CustomHelperUtils
 * 作者 :   <sen>
 * 时间 :  下午2:42 2016/11/22.
 * 描述 :  支持通过相机拍照获取图片
 * 支持从相册选择图片
 * 支持从文件选择图片
 */

public class CustomHelperUtils {


    public static CustomHelperUtils of() {
        return new CustomHelperUtils();
    }

    private CustomHelperUtils() {

    }

    /**
     * 操作圖片
     *
     * @param operationId 拍照/選擇圖片
     */
    public void takePhoto(int operationId, TakePhoto takePhoto) {

        File file = new File(Environment.getExternalStorageDirectory(), "/android/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        if (operationId == 0) {
            // 拍照
//            takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
            takePhoto.onPickFromCapture(imageUri);
        } else {
            // 選擇圖片
//            takePhoto.onPickMultipleWithCrop(1,getCropOptions());
//            takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
            takePhoto.onPickFromDocuments();
        }
    }


    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setWithOwnCrop(false);
        return builder.create();
    }


}
