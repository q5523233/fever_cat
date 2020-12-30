package com.nepo.fevercat.ui.alert;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.AudioPlayerUtils;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.ui.alert.adapter.AlertRingAdapter;
import com.nepo.fevercat.ui.alert.bean.AlertRingBean;
import com.nepo.fevercat.ui.alert.fragment.AlertAddLowMedicineFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert
 * 文件名:  AlertRingActivity
 * 作者 :   <sen>
 * 时间 :  下午3:56 2017/6/30.
 * 描述 :  铃声选择
 */

public class AlertRingActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{


    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.recycle_alert_ring_list)
    RecyclerView recycleAlertRingList;


    List<AlertRingBean> mAlertRingBeanList;
    AlertRingAdapter mAlertRingAdapter;
    String mSelectRing;
    /**
     * loader Id
     */
    private static final int LOADER_ID = 1;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AlertRingActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_alert_ring;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTopBarTitle.setText(getString(R.string.alert_add_low_ring));
        recycleAlertRingList.setHasFixedSize(true);
        recycleAlertRingList.setLayoutManager(new LinearLayoutManager(mContext));

        initLocalManager();




    }


    private void initLocalManager(){
        // 管理cursor
        LoaderManager loaderManager = getLoaderManager();
        // 注册Loader
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    private void initRingAdapter(){
        mAlertRingAdapter = new AlertRingAdapter(mAlertRingBeanList);
        mAlertRingAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recycleAlertRingList.setAdapter(mAlertRingAdapter);
        mAlertRingAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<AlertRingBean> alertRingBeanList = adapter.getData();

                for (int i = 0; i < alertRingBeanList.size(); i++) {
                    adapter.setData(i,alertRingBeanList.get(i).setCheck(false));
                }

                AlertRingBean alertRingBean = alertRingBeanList.get(position);
                alertRingBean.setCheck(true);
                adapter.setData(position,alertRingBean);
                mSelectRing = alertRingBean.getRingName();
                // 播放选中音频
                AudioPlayerUtils.getInstance(mContext).play(alertRingBean.getRingUrl(),false,false);
            }
        });
    }



    /**
     * 返回
     */
    @OnClick(R.id.rl_top_bar_back)
    public void onBackClick() {
        if (!TextUtils.isEmpty(mSelectRing)) {
            Intent intent = new Intent();
            intent.putExtra(AlertAddLowMedicineFragment.Low_Add_Intent_Flag,mSelectRing);
            setResult(0,intent);
        }
        finish();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // 查询内部存储音频文件
        return new CursorLoader(mContext,
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI, new String[]{
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA}, null, null,
                MediaStore.Audio.Media.DISPLAY_NAME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAlertRingBeanList = new ArrayList<>();
        // 过滤重复音频文件的Set
        HashSet<String> set = new HashSet<>();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
                    .moveToNext()) {
                // 音频文件名
                String ringName = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                if (ringName != null) {
                    // 当过滤集合里不存在此音频文件
                    if (!set.contains(ringName)) {
                        // 添加音频文件到列表过滤同名文件
                        set.add(ringName);
                        // 去掉音频文件的扩展名
                        ringName = ConstantUtils.removeEx(ringName);
                        // 取得音频文件的地址
                        String ringUrl = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DATA));
                        AlertRingBean alertRingBean = new AlertRingBean();
                        alertRingBean.setRingName(ringName).setRingUrl(ringUrl).setCheck(false);
                        mAlertRingBeanList.add(alertRingBean);
                    }
                }
            }
        }
        // 设置适配器
        initRingAdapter();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!AudioPlayerUtils.sIsRecordStopMusic) {
            // 停止播放
            AudioPlayerUtils.getInstance(mContext).stop();
        }
    }
}
