package com.nepo.fevercat.common.widget.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nepo.fevercat.R;


/**
 * 项目名: TakeOutFood
 * 包名 :  com.nepo.takeoutfood.common.widget
 * 文件名:  DispatchPopupWindows
 * 作者 :   <sen>
 * 时间 :  下午4:31 2016/12/27.
 * 描述 :  選擇圖片彈窗
 */

public class TakePhotoPopupWindows extends BasePopupWindow {

    private View mView;
    private LinearLayout view_personal_photo_take_ll;
    private LinearLayout view_personal_photo_select_sd_ll;
    private LinearLayout view_personal_photo_cancel_ll;
    private OnTakePhotoListen mOnTakePhotoListen;

    public TakePhotoPopupWindows(Context context) {
        super(context);
        mView =  LayoutInflater.from(context).inflate(R.layout.view_select_photo_popup,null);
        setContentView(mView);
        initView();
    }

    public void setOnTakePhotoListen(OnTakePhotoListen onTakePhotoListen) {
        mOnTakePhotoListen = onTakePhotoListen;
    }

    private void initView(){
        view_personal_photo_take_ll= (LinearLayout) mView.findViewById(R.id.view_personal_photo_take_ll);
        view_personal_photo_select_sd_ll= (LinearLayout) mView.findViewById(R.id.view_personal_photo_select_sd_ll);
        view_personal_photo_cancel_ll= (LinearLayout) mView.findViewById(R.id.view_personal_photo_cancel_ll);

        // 拍照
        view_personal_photo_take_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnTakePhotoListen.selectPhotoType(0);
                dismiss();
            }
        });
        // 選擇圖片
        view_personal_photo_select_sd_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnTakePhotoListen.selectPhotoType(1);
                dismiss();
            }
        });
        // 取消
        view_personal_photo_cancel_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    public interface OnTakePhotoListen{
        void selectPhotoType(int takeType);
    }


}
