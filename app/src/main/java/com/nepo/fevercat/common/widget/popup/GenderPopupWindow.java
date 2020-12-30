package com.nepo.fevercat.common.widget.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nepo.fevercat.R;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget.popup
 * 文件名:  GenderPopupWindow
 * 作者 :   <sen>
 * 时间 :  下午3:47 2017/8/25.
 * 描述 :
 */

public class GenderPopupWindow extends BasePopupWindow {


    LinearLayout llGenderMale;
    LinearLayout llGenderFemale;

    private View mView;

    private OnTakeGenderListen mOnTakeGenderListen;

    public GenderPopupWindow(Context context) {
        super(context);
        mView = LayoutInflater.from(context).inflate(R.layout.view_select_gender_popup, null);
        setContentView(mView);
        initView();
    }

    public GenderPopupWindow setOnTakeGenderListen(OnTakeGenderListen onTakeGenderListen) {
        mOnTakeGenderListen = onTakeGenderListen;
        return this;
    }

    private void initView(){

        llGenderMale = (LinearLayout) mView.findViewById(R.id.ll_gender_male);
        llGenderFemale = (LinearLayout) mView.findViewById(R.id.ll_gender_female);

        llGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTakeGenderListen.selectGenderStr("男");
                dismiss();
            }
        });
        llGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTakeGenderListen.selectGenderStr("女");
                dismiss();
            }
        });
    }


    public interface OnTakeGenderListen{
        void selectGenderStr(String genderStr);
    }


}
