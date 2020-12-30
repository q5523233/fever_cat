package com.nepo.fevercat.ui.alert.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.ui.alert.bean.AlertRingBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.adapter
 * 文件名:  AlertRingAdapter
 * 作者 :   <sen>
 * 时间 :  下午4:00 2017/6/30.
 * 描述 :
 */

public class AlertRingAdapter extends BaseQuickAdapter<AlertRingBean,BaseViewHolder> {


    public AlertRingAdapter(@Nullable List<AlertRingBean> data) {
        super(R.layout.item_alert_ring, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertRingBean item) {
        helper.addOnClickListener(R.id.ll_alert_ring_contain);
        helper.setText(R.id.tv_alert_ring_title,item.getRingName());
        helper.setChecked(R.id.chk_alert_ring_select,item.isCheck());
    }
}
