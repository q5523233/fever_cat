package com.nepo.fevercat.ui.data.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.adapter
 * 文件名:  TempHistoryItemItemDayAdapter
 * 作者 :   <sen>
 * 时间 :  下午8:30 2017/7/3.
 * 描述 :  历史温度 日
 */

public class TempHistoryItemItemDayAdapter extends BaseQuickAdapter<TempHistoryDataResBean.RecordBean.MonthBean.RecordDetailsBean, BaseViewHolder> {


    public TempHistoryItemItemDayAdapter(@Nullable List<TempHistoryDataResBean.RecordBean.MonthBean.RecordDetailsBean> data) {
        super(R.layout.item_item_item_temp_history_day, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TempHistoryDataResBean.RecordBean.MonthBean.RecordDetailsBean item) {

        helper.setText(R.id.tv_temp_history_item_item_day, item.getRecordDay());

        int indexOf = helper.getAdapterPosition();
        if (indexOf==0) {
            helper.setVisible(R.id.line_temp_history_item_item_height_temp,false);
            helper.setVisible(R.id.line_temp_history_item_item_record,false);
        }else{
            helper.setVisible(R.id.line_temp_history_item_item_height_temp,true);
            helper.setVisible(R.id.line_temp_history_item_item_record,true);
        }

        if (!TextUtils.isEmpty(item.getRecordTemperatureValue())) {
            int colorId = ConstantUtils.CheckTempLimitToColor(Double.valueOf(item.getRecordTemperatureValue()));
            helper.setTextColor(R.id.tv_temp_history_item_item_height_temp, colorId);
            helper.setText(R.id.tv_temp_history_item_item_height_temp, ConstantUtils.IsCelsiusUnit(item.getRecordTemperatureValue()));

            //天数
            String tempStr = ConstantUtils.IsCelsiusUnit("37.5");
            String sustainUnit = mContext.getString(R.string.temp_history_data_item_height_temp_sustain);
            helper.setText(R.id.tv_temp_history_item_item_height_temp_sustain, String.format(sustainUnit, tempStr, item.getRecordSustain()));
            // 记录时间
            helper.setText(R.id.tv_temp_history_item_item_record, item.getRecordTime());

        }


    }
}
