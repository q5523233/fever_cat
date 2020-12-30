package com.nepo.fevercat.common.widget.popup;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nepo.fevercat.R;
import com.nepo.fevercat.ui.data.adapter.TempBBListAdapter;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget.popup
 * 文件名:  ListBBPopupWindow
 * 作者 :   <sen>
 * 时间 :  下午2:27 2017/7/6.
 * 描述 :  列表弹窗
 */

public class ListBBPopupWindow extends BasePopupWindow {


    @BindView(R.id.recycle_bb_popup_list)
    RecyclerView recycleBbPopupList;

    View mView;
    Context mContext;
    MineBBResBean mMineBBResBean;
    TempBBListAdapter mTempBBListAdapter;
    OnSelectItemListen mOnSelectItemListen;

    public ListBBPopupWindow setOnSelectItemListen(OnSelectItemListen onSelectItemListen) {
        mOnSelectItemListen = onSelectItemListen;
        return this;
    }

    public ListBBPopupWindow(Context context, MineBBResBean mineBBResBean) {
        super(context);
        mContext = context;
        mMineBBResBean = mineBBResBean;
        setTouchable(true);
        mView = LayoutInflater.from(context).inflate(R.layout.view_bb_list_popup, null);
        setContentView(mView);
        ButterKnife.bind(this, mView);
        initView();
    }

    private void initView(){
        recycleBbPopupList.setHasFixedSize(true);
        recycleBbPopupList.setLayoutManager(new LinearLayoutManager(mContext));
        mTempBBListAdapter = new TempBBListAdapter(mMineBBResBean.getBabyInfos());
        mTempBBListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recycleBbPopupList.setAdapter(mTempBBListAdapter);
        mTempBBListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mOnSelectItemListen.selectStrItem((BabyInfosBean) adapter.getItem(position));
                dismiss();
            }
        });


    }


    public interface OnSelectItemListen {
        void selectStrItem(BabyInfosBean babyInfosBean);
    }


}
