package com.nepo.fevercat.ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.BaseApplication;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.event.ExcelUpdateEvent;
import com.nepo.fevercat.ui.mine.adapter.MineMotionListAdapter;
import com.nepo.fevercat.ui.mine.bean.MotionBean;
import com.nepo.fevercat.ui.mine.utils.WriteExcelUtils2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;


/**
 * Created by sm on 2019/3/25.
 */

public class ExcelActivity extends BaseActivity {
    @BindView(R.id.toolbar_excel)
    Toolbar toolbarExcel;
    @BindView(R.id.rv_table)
    RecyclerView rvTable;
    @BindView(R.id.rv_table_refresh)
    SwipeRefreshLayout rvTableRefresh;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    static String EXTRA = "TYPE";
    private int type;//0:运动量化数据 1:基础生命量化数据
    private MineMotionListAdapter adapter;
    private List<MotionBean> beanList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_excel;
    }

    @Override
    public void initPresenter() {

    }

    private void initData() {
        EventBus.getDefault().register(this);
        beanList = getDataList();
        rvTableRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.setNewData(getDataList());
                rvTableRefresh.setRefreshing(false);
            }
        });
        initAdapter();
    }

    private void initAdapter() {
        adapter = new MineMotionListAdapter(beanList);
        adapter.bindToRecyclerView(rvTable);
        adapter.setEmptyView(R.layout.view_empty_table);
        adapter.getEmptyView().findViewById(R.id.rl_add_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotionBean bean = new MotionBean();
                bean.type = type;
                POIExcelModeActivity.newIntent(ExcelActivity.this, bean);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List data = adapter.getData();
                MotionBean o = (MotionBean) data.get(position);
                if (view.getId() == R.id.item_del_btn) {
                    // 删除
                    GreenDaoUtils.getInstance(getApplicationContext()).delMotionBean(o);
                    WriteExcelUtils2.deleteFile(o);
                    data.remove(position);
                    adapter.notifyItemRemoved(position);
                } else if (view.getId() == R.id.rl_item_follow_contain) {
                    POIExcelModeActivity.newIntent(ExcelActivity.this, o);
                }
            }
        });
    }

    private List<MotionBean> getDataList() {
        return GreenDaoUtils.getInstance(BaseApplication.getAppContext()).getMotionList(type);
    }

    @Override
    public void initView() {
        handleIntent();
        setSupportActionBar(toolbarExcel);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTable.setLayoutManager(manager);
        initData();
    }

    private void handleIntent() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA)) {
            type = getIntent().getIntExtra(EXTRA, 0);
        }
        tvTitle.setText(type == 1 ? getString(R.string.base_info_excel) : getString(R.string.motion_quantization_table));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excel_opration, menu);
        menu.findItem(R.id.menu_save).setVisible(false);
        menu.findItem(R.id.menu_edit).setIcon(R.drawable.icon_follow_top_bar_add);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                MotionBean bean = new MotionBean();
                bean.type = type;
                POIExcelModeActivity.newIntent(this, bean);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void newIntent(Context context, int type) {
        Intent i = new Intent(context, ExcelActivity.class);
        i.putExtra(EXTRA, type);
        if (!(context instanceof Activity)) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(i);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(ExcelUpdateEvent excelUpdateEvent) {
        adapter.setNewData(getDataList());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
