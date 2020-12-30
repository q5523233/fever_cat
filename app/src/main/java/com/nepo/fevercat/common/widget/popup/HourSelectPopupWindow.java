package com.nepo.fevercat.common.widget.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget.popup
 * 文件名:  DaySelectPopupWindow
 * 作者 :   <sen>
 * 时间 :  下午6:08 2017/6/30.
 * 描述 :  时间选择
 */

public class HourSelectPopupWindow extends BasePopupWindow {



    Context mContext;
    private View mView;
    private Button btn_hour_popup_cancel,btn_hour_popup_confirm;

    public HourSelectPopupWindow(Context context) {
        super(context);
        mContext = context;
        setTouchable(true);
    }

    private void initView(){


    }




}
