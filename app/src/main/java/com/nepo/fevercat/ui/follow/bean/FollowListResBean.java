package com.nepo.fevercat.ui.follow.bean;

import com.nepo.fevercat.ui.main.bean.BaseResBean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.bean
 * 文件名:  FollowListResBean
 * 作者 :   <sen>
 * 时间 :  上午11:49 2017/6/24.
 * 描述 :
 */

public class FollowListResBean extends BaseResBean {


    private List<BindUsersBean> bindUsers;

    public List<BindUsersBean> getBindUsers() {
        return bindUsers;
    }

    public void setBindUsers(List<BindUsersBean> bindUsers) {
        this.bindUsers = bindUsers;
    }

    public static class BindUsersBean implements Serializable {
        private String bindUserName;
        private String bindId;
        private String bindNickname;
        private String bindUserId;
        private String bindUserImgUrl;

        public String getBindUserName() {
            return bindUserName;
        }

        public void setBindUserName(String bindUserName) {
            this.bindUserName = bindUserName;
        }

        public String getBindId() {
            return bindId;
        }

        public void setBindId(String bindId) {
            this.bindId = bindId;
        }

        public String getBindNickname() {
            return bindNickname;
        }

        public void setBindNickname(String bindNickname) {
            this.bindNickname = bindNickname;
        }

        public String getBindUserId() {
            return bindUserId;
        }

        public void setBindUserId(String bindUserId) {
            this.bindUserId = bindUserId;
        }

        public String getBindUserImgUrl() {
            return bindUserImgUrl;
        }

        public void setBindUserImgUrl(String bindUserImgUrl) {
            this.bindUserImgUrl = bindUserImgUrl;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof BindUsersBean))
            {
                return false;
            }
            return ((BindUsersBean) obj).bindUserName.equals(this.bindUserName);
        }
    }
}
