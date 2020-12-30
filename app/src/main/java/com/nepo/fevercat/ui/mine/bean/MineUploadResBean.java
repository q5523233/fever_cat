package com.nepo.fevercat.ui.mine.bean;

import com.nepo.fevercat.ui.main.bean.BaseResBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.bean
 * 文件名:  MineUploadResBean
 * 作者 :   <sen>
 * 时间 :  下午6:12 2017/6/24.
 * 描述 :
 */

public class MineUploadResBean extends BaseResBean {


    private String fileId;
    private String fileUrl;


    public String getFileId() {
        return fileId;
    }

    public MineUploadResBean setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public MineUploadResBean setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    @Override
    public String toString() {
        return "MineUploadResBean{" +
                "fileId='" + fileId + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
