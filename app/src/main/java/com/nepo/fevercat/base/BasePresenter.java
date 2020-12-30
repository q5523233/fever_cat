package com.nepo.fevercat.base;

import android.content.Context;

import com.nepo.fevercat.base.rx.RxManager;


/**
 * Created by <sen> on 2016/11/3.
 *
 * 基类presenter
 *
 */

public class BasePresenter<T,E> {

    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }
    public void onStart(){
    }

    public void onDestroy() {
        mRxManage.clear();
    }

}
