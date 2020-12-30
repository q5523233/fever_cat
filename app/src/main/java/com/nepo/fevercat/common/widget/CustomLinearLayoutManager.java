package com.nepo.fevercat.common.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget
 * 文件名:  CustomLinearLayoutManager
 * 作者 :   <sen>
 * 时间 :  下午5:53 2017/6/30.
 * 描述 :
 */

public class CustomLinearLayoutManager extends GridLayoutManager {


    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
