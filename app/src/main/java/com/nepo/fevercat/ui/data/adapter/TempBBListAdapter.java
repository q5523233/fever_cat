package com.nepo.fevercat.ui.data.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.adapter
 * 文件名:  TempBBListAdapter
 * 作者 :   <sen>
 * 时间 :  下午2:33 2017/7/6.
 * 描述 :  宝宝列表
 */

public class TempBBListAdapter extends BaseQuickAdapter<BabyInfosBean,BaseViewHolder> {

    public TempBBListAdapter(@Nullable List<BabyInfosBean> data) {
        super(R.layout.item_bb_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BabyInfosBean item) {
        helper.addOnClickListener(R.id.rl_item_bb_list_contain);
        CircularImage ivTempUserImg = helper.getView(R.id.iv_item_bb_list);
        ConstantUtils.loadLoginUserImg(item.getHeadImageUrl(), ivTempUserImg);
        helper.setText(R.id.tv_item_bb_list,item.getNickname());

    }
}
