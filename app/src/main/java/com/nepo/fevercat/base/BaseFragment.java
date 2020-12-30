package com.nepo.fevercat.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nepo.fevercat.base.rx.RxManager;
import com.nepo.fevercat.common.utils.StatusBarCompat;
import com.nepo.fevercat.common.utils.TUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名: TakeOutFood
 * 包名 :  com.nepo.takeoutfood.base
 * 文件名:  BaseFragment
 * 作者 :   <sen>
 * 时间 :  下午2:44 2016/11/15.
 * 描述 :
 */

public abstract class BaseFragment<T extends BasePresenter,E extends BaseModel> extends Fragment {

    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;
    public Activity mActivity;
    public Unbinder bind;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mRxManager=new RxManager();
        bind = ButterKnife.bind(this, rootView);


        //EventBus.getDefault().register(this);
        mPresenter = TUtil.getT(this, 0);
        mModel= TUtil.getT(this,1);
        mActivity = getActivity();
        if(mPresenter!=null){
            mPresenter.mContext=this.getActivity();
        }
        initPresenter();
        initView();
        return rootView;
    }
    //获取布局文件
    protected abstract int getLayoutResource();
    //简单页面无需mvp就不用管此方法即可
    public abstract void initPresenter();
    //初始化view
    protected abstract void initView();


    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 打开界面
     * @param intent
     */
    public void startAct(Intent intent){
        startActivity(intent);

    }


    /**
     * 着色状态栏（4.4以上系统有效）
     *
     * @param color 自定义颜色
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(mActivity, color);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
    }


}
