package com.nepo.fevercat.ui.mine.bean;

import com.nepo.fevercat.ui.main.bean.BaseResBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.bean
 * 文件名:  MineBBResBean
 * 作者 :   <sen>
 * 时间 :  下午4:19 2017/6/27.
 * 描述 :  宝宝信息
 */

public class MineBBResBean extends BaseResBean {

    // 宝宝ID
    private String babyId;

    private List<BabyInfosBean> babyInfos;

    public List<BabyInfosBean> getBabyInfos() {
        return babyInfos;
    }

    public MineBBResBean setBabyInfos(List<BabyInfosBean> babyInfos) {
        this.babyInfos = babyInfos;
        return this;
    }

    public String getBabyId() {
        return babyId;
    }
}
