package com.nepo.fevercat.ui.mine.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.ui.mine.bean.MotionBean;

import java.util.List;

/**
 * Created by sm on 2019/3/25.
 * 运动量化表数据
 */

public class MineMotionListAdapter extends BaseQuickAdapter<MotionBean, BaseViewHolder> {
    public MineMotionListAdapter(@Nullable List<MotionBean> data) {
        super(R.layout.item_motion_bean, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MotionBean item) {
        helper.setText(R.id.tv_name,  item.name);
        helper.setText(R.id.tv_age, "年龄: " + item.age);
        helper.setText(R.id.tv_sex, item.sex);
        helper.setText(R.id.tv_diagnosis, "诊断" + item.diagnosis);
        helper.addOnClickListener(R.id.rl_item_follow_contain).addOnClickListener(R.id.item_del_btn);
    }
}
