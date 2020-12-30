package com.nepo.fevercat.ui.alert.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.ui.alert.bean.AlertRepeatDayBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.adapter
 * 文件名:  AlertRepeatDayAdapter
 * 作者 :   <sen>
 * 时间 :  上午11:55 2017/6/30.
 * 描述 :
 */

public class AlertRepeatDayAdapter extends BaseQuickAdapter<AlertRepeatDayBean,BaseViewHolder> {



    public AlertRepeatDayAdapter(@Nullable List<AlertRepeatDayBean> data) {
        super(R.layout.item_alert_repeat_day, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertRepeatDayBean item) {
        helper.addOnClickListener(R.id.ll_alert_repeat_contain).addOnClickListener(R.id.chk_alert_repeat_select);
        helper.setText(R.id.tv_alert_repeat_title,item.getTitle());
        helper.setChecked(R.id.chk_alert_repeat_select,item.isCheck());

    }
}
