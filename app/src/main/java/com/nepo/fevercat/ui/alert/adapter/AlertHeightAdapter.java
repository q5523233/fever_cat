package com.nepo.fevercat.ui.alert.adapter;


import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.Utils;
import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.adapter
 * 文件名:  AlertHeightAdapter
 * 作者 :   <sen>
 * 时间 :  下午5:45 2017/6/28.
 * 描述 :  高温提醒
 */

public class AlertHeightAdapter extends BaseQuickAdapter<AlertHeightBean,BaseViewHolder> {

    public AlertHeightAdapter(@Nullable List<AlertHeightBean> data) {
        super(R.layout.item_alert_height, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertHeightBean item) {
        // 指定点击事件
        helper.addOnClickListener(R.id.ll_item_alert_contain)
                .addOnClickListener(R.id.switch_item_alert_height)
                .addOnClickListener(R.id.item_del_btn);
        LinearLayout tipLayout =  helper.getView(R.id.ll_item_alert_tip);
        RelativeLayout statusLayout = helper.getView(R.id.rl_item_alert_height_status);
        int indexOf = helper.getAdapterPosition();
        if (indexOf==0) {
            tipLayout.setVisibility(View.VISIBLE);
        }else{
            tipLayout.setVisibility(View.GONE);
        }
//        String tempLimitToStr = ConstantUtils.CheckTempLimitToStr(Double.valueOf(item.getSTemp()));
//        Drawable checkTempLimitToBg = ConstantUtils.CheckTempLimitToBg(Double.valueOf(item.getSTemp()));
//        statusLayout.setBackground(checkTempLimitToBg);
//        helper.setText(R.id.tv_item_alert_height_temp,ConstantUtils.IsCelsiusUnit(item.getSTemp()));
//        helper.setText(R.id.tv_item_alert_height_status,ConstantUtils.CheckTempLimitToStr(Double.valueOf(item.getSTemp())));
        helper.setText(R.id.tv_item_alert_height_time,item.getSTimeTip());
        helper.setChecked(R.id.switch_item_alert_height,item.getSCheckStatus());
        helper.setText(R.id.tv_item_alert_height_temp,ConstantUtils.ConvertStrToFahrenheit(item.getSTemp())+ConstantUtils.GetCelsiusUnit());
        if (TextUtils.equals(item.getSSelectType(),"0")) {
            // 低温\
            Drawable bgStr = ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_low_bg);
            statusLayout.setBackground(bgStr);
            helper.setText(R.id.tv_item_alert_height_status,Utils.getContext().getString(R.string.temp_status_low));
        }else if (TextUtils.equals(item.getSSelectType(),"1")) {
            // 中温
            Drawable bgStr = ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_middle_bg);
            statusLayout.setBackground(bgStr);
            helper.setText(R.id.tv_item_alert_height_status,Utils.getContext().getString(R.string.temp_status_middle));
        }else{
            // 高温
            Drawable bgStr = ContextCompat.getDrawable(Utils.getContext(), R.drawable.icon_item_alert_height_hot_bg);
            statusLayout.setBackground(bgStr);
            helper.setText(R.id.tv_item_alert_height_status,Utils.getContext().getString(R.string.temp_status_hot));
        }

    }
}
