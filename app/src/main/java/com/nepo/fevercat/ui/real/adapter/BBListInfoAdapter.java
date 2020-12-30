package com.nepo.fevercat.ui.real.adapter;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nepo.fevercat.R;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.real.adapter
 * 文件名:  BBListInfoAdapter
 * 作者 :   <sen>
 * 时间 :  上午11:38 2017/7/24.
 * 描述 :
 */

public class BBListInfoAdapter extends BaseQuickAdapter<BabyInfosBean, BaseViewHolder> {

    public BBListInfoAdapter(@Nullable List<BabyInfosBean> data) {
        super(R.layout.item_bb_info,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BabyInfosBean item) {
        helper.addOnClickListener(R.id.ll_item_bb);
        View view = helper.getView(R.id.ll_item_bb);
        CircularImage circularImage = helper.getView(R.id.iv_item_bb_img);
        if (!item.isAdd()) {
            ConstantUtils.loadBBImg(item.getHeadImageUrl(), circularImage);
            helper.setText(R.id.tv_item_bb_name,item.getNickname());
            // 视图放大/缩小
            if (TextUtils.equals(item.getIsScaleBig(),"0")) {
                // 缩小
                helper.setTextColor(R.id.tv_item_bb_name, ContextCompat.getColor(mContext,R.color.role_right_gray));
                // 灰色
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);//饱和度 0灰色 100过度彩色，50正常
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                circularImage.setColorFilter(filter);
            }else{
                Animation testAnim = AnimationUtils.loadAnimation(mContext, R.anim.scale_big);
                view.startAnimation(testAnim);
                // 放大
                helper.setTextColor(R.id.tv_item_bb_name, ContextCompat.getColor(mContext,R.color.nav_select));
                // 恢复正常色
                circularImage.clearColorFilter();
            }

        }else{
            // 展示添加图片
            Glide.with(mContext).load(R.drawable.icon_real_time_bb_add_ic).into(circularImage);
            helper.setText(R.id.tv_item_bb_name,mContext.getString(R.string.bb_add));
        }
    }

    /**
     * 放大
     *
     * @param view
     */
    private void scaleBigView(final View view) {

    }

    /**
     * 缩小
     *
     * @param view
     */
    private void scaleSmallView(View view) {
        ScaleAnimation animation_suofang = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_suofang.setDuration(90);
        animation_suofang.setFillAfter(true);
        animation_suofang.setRepeatMode(Animation.INFINITE);
        view.startAnimation(animation_suofang);
    }


}
