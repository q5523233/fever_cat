package com.nepo.fevercat.ui.alert.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.adapter
 * 文件名:  AlertLowAdapter
 * 作者 :   <sen>
 * 时间 :  上午9:47 2017/6/29.
 * 描述 :
 */

public class AlertLowAdapter extends BaseQuickAdapter<AlertLowBean,BaseViewHolder> {
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    public AlertLowAdapter(@Nullable List<AlertLowBean> data) {
        super(R.layout.item_alert_low, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertLowBean item) {
        // 指定点击事件
        helper.addOnClickListener(R.id.ll_item_alert_low_contain)
                .addOnClickListener(R.id.item_del_btn)
                .addOnClickListener(R.id.switch_item_alert_low)
                .addOnLongClickListener(R.id.ll_item_alert_low_contain);
        // 开关
        helper.setChecked(R.id.switch_item_alert_low,item.getMIsEnabledAlert());
        if (TextUtils.equals(item.getMAlertStatus(), AppConstant.ALERT_MEDICINE_STATUS)) {
            helper.setBackgroundRes(R.id.iv_item_alert_low,R.drawable.icon_item_alert_low_medicine);
            helper.setText(R.id.tv_item_alert_low_title,item.getMedicineName());

            if (!TextUtils.isEmpty(item.getMRepeatType())) {
                if (TextUtils.equals(item.getMRepeatType(),"周一、周二、周三、周四、周五、周六、周日")) {
                    helper.setText(R.id.tv_item_alert_low_time_interval,mContext.getString(R.string.Per_day));
                }else if(TextUtils.equals(item.getMRepeatType(),"Monday、Tuesday、Wednesday、Thursdays、fridays、Saturday、Sunday")){
                    helper.setText(R.id.tv_item_alert_low_time_interval,mContext.getString(R.string.Per_day));
                }else{
                    helper.setText(R.id.tv_item_alert_low_time_interval,item.getMRepeatType());
                }
            }
            helper.setText(R.id.tv_item_alert_low_time,item.getMTime());
        }else{
            helper.setBackgroundRes(R.id.iv_item_alert_low,R.drawable.icon_item_alert_low_water);
            String strUnit = mContext.getString(R.string.alert_water_time_interval_s);
            String strWaterUnit = mContext.getString(R.string.alert_water_unit_s);
            helper.setText(R.id.tv_item_alert_low_title,String.format(strWaterUnit,item.getMWaterUnits()));
            helper.setText(R.id.tv_item_alert_low_time,String.format(strUnit,item.getMWaterBeginTime(),item.getMWaterEndTime()));
            helper.setText(R.id.tv_item_alert_low_time_interval,item.getMWaterInterval());

        }
        if (mEditMode == MYLIVE_MODE_CHECK) {
            helper.setVisible(R.id.iv_item_alert_low_checkbtn,false);
        } else {
            helper.setVisible(R.id.iv_item_alert_low_checkbtn,true);

            if (item.isSelect()) {
                helper.setImageResource(R.id.iv_item_alert_low_checkbtn,R.mipmap.ic_checked);
            } else {
                helper.setImageResource(R.id.iv_item_alert_low_checkbtn,R.mipmap.ic_uncheck);
            }
        }



    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }
}
