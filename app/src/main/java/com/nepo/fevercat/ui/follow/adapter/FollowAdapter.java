package com.nepo.fevercat.ui.follow.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.adapter
 * 文件名:  FollowAdapter
 * 作者 :   <sen>
 * 时间 :  上午11:45 2017/6/24.
 * 描述 :
 */

public class FollowAdapter extends BaseQuickAdapter<FollowListResBean.BindUsersBean, BaseViewHolder> {


    public FollowAdapter(List<FollowListResBean.BindUsersBean> followListResBeanList) {
        super(R.layout.item_follow_data, followListResBeanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, FollowListResBean.BindUsersBean item) {
        // 指定点击事件
        helper.addOnClickListener(R.id.rl_item_follow_contain).addOnClickListener(R.id.item_del_btn);
        helper.setText(R.id.tv_item_name, item.getBindNickname());
        helper.setText(R.id.tv_item_phone, item.getBindUserName());
        ImageView img = helper.getView(R.id.iv_item_follow_user);
        ConstantUtils.loadUserImg(item.getBindUserImgUrl(), img);
    }
}
