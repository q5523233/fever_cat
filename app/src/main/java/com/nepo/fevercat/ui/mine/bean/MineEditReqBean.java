package com.nepo.fevercat.ui.mine.bean;

import com.nepo.fevercat.ui.main.bean.BaseReqBean;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.bean
 * 文件名:  MineEditReqBean
 * 作者 :   <sen>
 * 时间 :  下午5:26 2017/6/24.
 * 描述 :
 */

public class MineEditReqBean extends BaseReqBean {

    private String accountId;
    private String operateID;
    private String nickName;
    private String headImageId;

    public MineEditReqBean setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public MineEditReqBean setHeadImageId(String headImageId) {
        this.headImageId = headImageId;
        return this;
    }

    public MineEditReqBean setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public MineEditReqBean setOperateID(String operateID) {
        this.operateID = operateID;
        return this;
    }
}
