package com.nepo.fevercat.common.widget.popup;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.aigestudio.wheelpicker.WheelPicker;
import com.nepo.fevercat.R;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget.popup
 * 文件名:  StrSelectPopupWindow
 * 作者 :   <sen>
 * 时间 :  下午2:10 2017/6/28.
 * 描述 :
 */

public class StrSelectPopupWindow extends BasePopupWindow {



    @BindView(R.id.rl_str_popup_cancel)
    RelativeLayout tvStrPopupCancel;
    @BindView(R.id.rl_str_popup_confirm)
    RelativeLayout tvStrPopupConfirm;
    @BindView(R.id.str_picker_popup)
    WheelPicker strPickerPopup;



    Context mContext;
    private View mView;
    List<String> mStringList;
    private OnSelectItemListen mOnSelectItemListen;

    public StrSelectPopupWindow setOnSelectItemListen(OnSelectItemListen onSelectItemListen) {
        mOnSelectItemListen = onSelectItemListen;
        return this;
    }

    public StrSelectPopupWindow(Context context,List<String> stringList) {
        super(context);
        mContext = context;
        mStringList = stringList;
        setTouchable(true);
        mView = LayoutInflater.from(context).inflate(R.layout.view_str_select_popup, null);
        setContentView(mView);
        ButterKnife.bind(this, mView);
        initView();

    }

    private void initView() {
        strPickerPopup.setCyclic(false);
        strPickerPopup.setItemTextSize(66);
        strPickerPopup.setData(mStringList);
        strPickerPopup.setCurved(true);
        strPickerPopup.setSelectedItemTextColor(ContextCompat.getColor(mContext,R.color.color_02));
        tvStrPopupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvStrPopupConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSelectItemListen.selectStrItem(mStringList.get(strPickerPopup.getCurrentItemPosition()));
                dismiss();
            }
        });






    }


    public interface OnSelectItemListen {
        void selectStrItem(String content);
    }

}
