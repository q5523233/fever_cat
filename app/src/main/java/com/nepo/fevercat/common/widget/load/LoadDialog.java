package com.nepo.fevercat.common.widget.load;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.cunoraz.gifview.library.GifView;
import com.nepo.fevercat.R;


/**
 * 项目名: TakeOutFood
 * 包名 :  com.nepo.takeoutfood.common
 * 文件名:  LoadDialog
 * 作者 :   <sen>
 * 时间 :  下午3:51 2016/12/27.
 * 描述 :
 */

public class LoadDialog extends Dialog {

    private Context mContext;
    private View mView;

    private GifView loadImg;
    private ImageView iv_loading;
    public LoadDialog(Context context) {
        super(context);
        mContext=context;
        mView = LayoutInflater.from(context).inflate(R.layout.view_load_dialog,null);
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(mView);
        setCanceledOnTouchOutside(false);
        initView();

    }

    private void initView(){
       loadImg= (GifView) mView.findViewById(R.id.view_load_iv);
        iv_loading= (ImageView) mView.findViewById(R.id.iv_loading);
        loadImg.play();
        loadImg.setGifResource(R.drawable.loading);
//        Glide.with(mContext).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_loading);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (loadImg!=null&&loadImg.isPlaying())
            loadImg.pause();
    }
}
