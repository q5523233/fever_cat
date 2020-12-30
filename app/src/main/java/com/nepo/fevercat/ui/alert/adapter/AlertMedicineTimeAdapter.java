package com.nepo.fevercat.ui.alert.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.ui.alert.bean.AlertMedicineTimeBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.adapter
 * 文件名:  AlertMedicineTimeAdapter
 * 作者 :   <sen>
 * 时间 :  下午5:49 2017/6/29.
 * 描述 :
 */

public class AlertMedicineTimeAdapter extends BaseQuickAdapter<AlertMedicineTimeBean,BaseViewHolder> {


    public AlertMedicineTimeAdapter(@Nullable List<AlertMedicineTimeBean> data) {
        super(R.layout.item_alert_medicine_time, data);



    }

    @Override
    protected void convert(BaseViewHolder helper, AlertMedicineTimeBean item) {
        helper.addOnClickListener(R.id.ll_item_medicine_time_contain);
        TextView time =  helper.getView(R.id.tv_item_medicine_time);
        int indexOf = helper.getAdapterPosition();
        indexOf +=1;
        if (!item.isDefaultAdd()) {
            time.setTextColor(ContextCompat.getColor(mContext,R.color.color_12));
            helper.setText(R.id.tv_item_medicine_time,item.getTimeStr());
            helper.setText(R.id.tv_item_medicine_time_title,String.valueOf(indexOf));
        }else{
            time.setTextColor(ContextCompat.getColor(mContext,R.color.nav_normal));
            helper.setText(R.id.tv_item_medicine_time,"+");
            helper.setText(R.id.tv_item_medicine_time_title,"Add");
        }
    }
}
