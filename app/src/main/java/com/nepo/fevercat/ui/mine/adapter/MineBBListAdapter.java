package com.nepo.fevercat.ui.mine.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.adapter
 * 文件名:  MineBBListAdapter
 * 作者 :   <sen>
 * 时间 :  上午11:50 2017/11/21.
 * 描述 :
 */

public class MineBBListAdapter extends BaseQuickAdapter<BabyInfosBean, BaseViewHolder> {

    public MineBBListAdapter(@Nullable List<BabyInfosBean> data) {
        super( R.layout.item_bb_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BabyInfosBean item) {
        helper.addOnClickListener(R.id.ll_item_bb);
        View view = helper.getView(R.id.ll_item_bb);
        CircularImage circularImage = helper.getView(R.id.iv_item_bb_img);
        if (!item.isAdd()) {
            ConstantUtils.loadBBImg(item.getHeadImageUrl(), circularImage);
            helper.setText(R.id.tv_item_bb_name,item.getNickname());
        }else{
            // 展示添加图片
            Glide.with(mContext).load(R.drawable.icon_real_time_bb_add_ic).into(circularImage);
            helper.setText(R.id.tv_item_bb_name,mContext.getString(R.string.bb_add));
        }
    }
}
