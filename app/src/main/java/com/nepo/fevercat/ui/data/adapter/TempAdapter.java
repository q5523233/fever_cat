package com.nepo.fevercat.ui.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by sm on 2019/4/12.
 */

public class TempAdapter extends BaseQuickAdapter<TemperaturesBean,BaseViewHolder> {
    public TempAdapter(List<TemperaturesBean> result) {
        super(R.layout.item_temp_result,result);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemperaturesBean item) {
        helper.setText(R.id.tv_num,helper.getAdapterPosition()+"");
        helper.setText(R.id.tv_time,item.getTempTime().split(" ")[1]);
        String temperature = item.getTemperature();
        helper.setText(R.id.tv_left, temperature);
        String temperatureRight = item.getTemperatureRight();
        helper.setText(R.id.tv_right, temperatureRight);
        double delta = Double.valueOf(temperature) - Double.valueOf(temperatureRight);
        DecimalFormat df = new DecimalFormat("00.00");
        helper.setText(R.id.tv_delta, df.format(delta) +"");
    }

    public void resizeRecyclerview(RecyclerView recyclerView) {
        View view = View.inflate(getRecyclerView().getContext(), R.layout.item_temp_result, null);
        view.measure(0,0);
        int itemHeight = view.getMeasuredHeight();
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height=itemHeight*10;
        recyclerView.setLayoutParams(params);
    }
}
