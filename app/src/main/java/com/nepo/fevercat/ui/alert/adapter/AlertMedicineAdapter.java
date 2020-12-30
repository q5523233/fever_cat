package com.nepo.fevercat.ui.alert.adapter;

import android.support.annotation.Nullable;
import android.widget.SectionIndexer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.ui.alert.bean.AlertMedicineBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.adapter
 * 文件名:  AlertMedicineAdapter
 * 作者 :   <sen>
 * 时间 :  下午8:48 2017/6/29.
 * 描述 :
 */

public class AlertMedicineAdapter extends BaseQuickAdapter<AlertMedicineBean,BaseViewHolder> implements SectionIndexer {



    public AlertMedicineAdapter(@Nullable List<AlertMedicineBean> data) {
        super(R.layout.item_alert_medicine, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertMedicineBean item) {
        helper.addOnClickListener(R.id.ll_item_medicine_contain);
        helper.setText(R.id.tv_item_medicine_title,item.getName());
        int indexOf = helper.getAdapterPosition();
        int section = getSectionForPosition(indexOf);
        if (indexOf == getPositionForSection(section)) {
            helper.setVisible(R.id.rl_item_medicine_letters,true);
            helper.setText(R.id.tv_item_medicine_letters,item.getSortLetters());
        } else {
            helper.setVisible(R.id.rl_item_medicine_letters,false);
        }


    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < mData.size(); i++) {
            String sortStr = mData.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mData.get(position).getSortLetters().charAt(0);
    }
}
