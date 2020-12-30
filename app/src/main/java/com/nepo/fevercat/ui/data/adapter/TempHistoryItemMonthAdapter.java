package com.nepo.fevercat.ui.data.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * 文件名:  TempHistoryItemMonthAdapter
 * 作者 :   <sen>
 * 时间 :  下午8:22 2017/7/3.
 * 描述 :  历史温度 月
 */

public class TempHistoryItemMonthAdapter extends BaseQuickAdapter<TempHistoryDataResBean.RecordBean.MonthBean,BaseViewHolder> {


    public TempHistoryItemMonthAdapter(@Nullable List<TempHistoryDataResBean.RecordBean.MonthBean> data) {
        super(R.layout.item_item_temp_history_month,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TempHistoryDataResBean.RecordBean.MonthBean item) {

        helper.addOnClickListener(R.id.rl_item_temp_history_month_contain);
        helper.setText(R.id.tv_item_temp_history_month,item.getMonthNumber());
        helper.setVisible(R.id.recycle_item_temp_history_day,item.isExtendParent());

        // 最高温度
        if (!TextUtils.isEmpty(item.getHightTemperature())) {
            int colorId = ConstantUtils.CheckTempLimitToColor(Double.valueOf(item.getHightTemperature()));
            helper.setTextColor(R.id.tv_item_temp_history_height_temp,colorId);
            helper.setText(R.id.tv_item_temp_history_height_temp,ConstantUtils.IsCelsiusUnit(item.getHightTemperature()));
        }
        // 发热天数
        if (!TextUtils.isEmpty(item.getFeverDay())) {
            String strFormat = mContext.getString(R.string.temp_history_data_item_hot_day);
            helper.setText(R.id.tv_item_temp_history_hot_day,String.format(strFormat,item.getFeverDay()));
        }



        // 设置天数 列表
        RecyclerView recyclerMonth =  helper.getView(R.id.recycle_item_temp_history_day);
        recyclerMonth.setHasFixedSize(true);
        recyclerMonth.setLayoutManager(new LinearLayoutManager(mContext));
        TempHistoryItemItemDayAdapter tempHistoryItemMonthAdapter = new TempHistoryItemItemDayAdapter(item.getRecordDetails());
        tempHistoryItemMonthAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recyclerMonth.setAdapter(tempHistoryItemMonthAdapter);






    }
}
