package com.nepo.fevercat.common.widget.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.nepo.fevercat.R;

import butterknife.ButterKnife;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget.popup
 * 文件名:  DaySelectPopupWindow
 * 作者 :   <sen>
 * 时间 :  下午6:08 2017/6/30.
 * 描述 :  时间选择
 */

public class DaySelectPopupWindow extends BasePopupWindow {



    Context mContext;
    private View mView;

    public DaySelectPopupWindow(Context context) {
        super(context);
        mContext = context;
        setTouchable(true);
        mView = LayoutInflater.from(context).inflate(R.layout.view_date_select_popup, null);
        setContentView(mView);
        ButterKnife.bind(this, mView);


    }




}
