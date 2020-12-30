package com.nepo.fevercat.ui.data.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.adapter
 * 文件名:  TempHistoryAdapter
 * 作者 :   <sen>
 * 时间 :  下午8:22 2017/7/3.
 * 描述 :  历史温度  年
 */

public class TempHistoryAdapter extends BaseQuickAdapter<TempHistoryDataResBean.RecordBean,BaseViewHolder> {



    public TempHistoryAdapter(@Nullable List<TempHistoryDataResBean.RecordBean> data) {
        super(R.layout.item_temp_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TempHistoryDataResBean.RecordBean item) {

        // 设置年份
        helper.setText(R.id.tv_item_temp_history_year,item.getYear());
        // 设置月 列表
        final RecyclerView recyclerMonth =  helper.getView(R.id.recycle_item_temp_history_month);
        recyclerMonth.setLayoutManager(new LinearLayoutManager(mContext));
        TempHistoryItemMonthAdapter tempHistoryItemMonthAdapter = new TempHistoryItemMonthAdapter(item.getMonth());
        tempHistoryItemMonthAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerMonth.setAdapter(tempHistoryItemMonthAdapter);

        tempHistoryItemMonthAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<TempHistoryDataResBean.RecordBean.MonthBean> data  =adapter.getData();
                adapter.setData(position,data.get(position).setExtendParent(!data.get(position).isExtendParent()));

            }
        });


    }
}
