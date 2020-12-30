package com.nepo.fevercat.ui.alert;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.utils.PinyinComparator;
import com.nepo.fevercat.common.utils.PinyinUtils;
import com.nepo.fevercat.common.widget.slidebar.SideBar;
import com.nepo.fevercat.ui.alert.adapter.AlertMedicineAdapter;
import com.nepo.fevercat.ui.alert.bean.AlertMedicineBean;
import com.nepo.fevercat.ui.alert.fragment.AlertAddLowMedicineFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert
 * 文件名:  AlertMedicineActivity
 * 作者 :   <sen>
 * 时间 :  下午7:09 2017/6/29.
 * 描述 :  药物名称
 */

public class AlertMedicineActivity extends BaseActivity {


    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.edt_alert_medicine_search)
    EditText edtAlertMedicineSearch;
    @BindView(R.id.tv_alert_no_medicine_tip)
    TextView tvAlertNoMedicineTip;
    @BindView(R.id.rl_alert_medicine_no_data)
    RelativeLayout rlAlertMedicineNoData;
    @BindView(R.id.recycle_alert_medicine_list)
    RecyclerView recycleAlertMedicineList;
    @BindView(R.id.sidebar_alert_medicine)
    SideBar sidebarAlertMedicine;
    @BindView(R.id.tv_sidebar_title)
    TextView tvSidebarTitle;

    List<String> mMockMedicineList;
    List<AlertMedicineBean> mAlertMedicineBeanList;
    AlertMedicineAdapter mAlertMedicineAdapter;
    Subscription subscribe;
    String searchKey;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AlertMedicineActivity.class);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_alert_medicine;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        HideUtil.init(this);
        tvTopBarTitle.setText(getString(R.string.alert_add_low_medicine_title));

        initMockMedicineData();
        sidebarAlertMedicine.setTextView(tvSidebarTitle);
        recycleAlertMedicineList.setHasFixedSize(true);
        recycleAlertMedicineList.setLayoutManager(new LinearLayoutManager(mContext));
        initEvent();
    }

    private void initEvent() {
        //设置右侧触摸监听
        sidebarAlertMedicine.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int positionForSection = mAlertMedicineAdapter.getPositionForSection(s.charAt(0));
                recycleAlertMedicineList.scrollToPosition(positionForSection);
            }
        });

        // 搜索输入事件
        subscribe = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                edtAlertMedicineSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        subscriber.onNext(s.toString());
                    }
                });
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // 查询数据
                filterDataBySearchKey(s);
            }
        });


    }


    /**
     * 返回
     */
    @OnClick(R.id.rl_top_bar_back)
    public void onBackClick() {
        finish();
    }

    /**
     * 使用此药品
     */
    @OnClick(R.id.rl_alert_no_medicine_confirm)
    public void onMedicineConfirmClick() {
        Intent intent = new Intent();
        intent.putExtra(AlertAddLowMedicineFragment.Low_Add_Intent_Flag, searchKey);
        setResult(0, intent);
        finish();
    }

    /**
     * 模拟药品数据
     */
    private void initMockMedicineData() {
        mMockMedicineList = new ArrayList<>();
        mAlertMedicineBeanList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        mMockMedicineList.add("感冒咳嗽冲剂");
        mMockMedicineList.add("感冒咳嗽胶囊");
        mMockMedicineList.add("感冒咳嗽颗粒");
        mMockMedicineList.add("感冒康胶囊");
        mMockMedicineList.add("感冒清热软胶囊");
        mMockMedicineList.add("感冒软胶囊");
        mMockMedicineList.add("柴黄颗粒");
        mMockMedicineList.add("阿司匹林肠溶片");
        mMockMedicineList.add("安瑞克");
        mMockMedicineList.add("葛根汤颗粒");
        mMockMedicineList.add("穿心莲片");
        mMockMedicineList.add("东山感冒片");
        mMockMedicineList.add("九味羌活丸");
        mMockMedicineList.add("五粒回春丸");
        mMockMedicineList.add("儿感清口服液");
        mMockMedicineList.add("伤风停胶囊");
        mMockMedicineList.add("儿科七厘散");
        mMockMedicineList.add("儿感清口服液");
        mMockMedicineList.add("儿童复方氨酚肾素片（儿童科达琳）");
        mMockMedicineList.add("克感利咽口服液");
        mMockMedicineList.add("兰草片");
        mMockMedicineList.add("利巴韦林喷剂");
        mMockMedicineList.add("利巴韦林喷雾剂");
        mMockMedicineList.add("利巴韦林泡腾颗粒（奥得清)");
        mMockMedicineList.add("双分伪麻片（日片)");
        mMockMedicineList.add("双分伪麻胶囊");
        mMockMedicineList.add("双黄连分散片");
        mMockMedicineList.add("双黄连口服液");
        mMockMedicineList.add("双黄连含片");
        mMockMedicineList.add("双黄连气雾剂");
        mMockMedicineList.add("双黄连片");
        mMockMedicineList.add("双黄连胶囊");
        mMockMedicineList.add("咳喘丸");
        mMockMedicineList.add("四季抗病毒合剂");
        mMockMedicineList.add("复方四季青片");
        mMockMedicineList.add("复方大青叶合剂");
        mMockMedicineList.add("复方对乙酰氨基酚片");
        mMockMedicineList.add("复方小儿退热栓");
        mMockMedicineList.add("复方感冒灵片");
        mMockMedicineList.add("大力丸");
        mMockMedicineList.add("阿玛尼");
        mMockMedicineList.add("Gucci");
        mMockMedicineList.add("Hermes");
        mMockMedicineList.add("大风丸");
        mMockMedicineList.add("小风丸");
        mMockMedicineList.add("狗皮膏药");
        mMockMedicineList.add("帖风寒");

        for (String str : mMockMedicineList) {
            AlertMedicineBean alertMedicineBean = new AlertMedicineBean();
            alertMedicineBean.setName(str);
            String pinyin = PinyinUtils.getPingYin(str);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                alertMedicineBean.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
            mAlertMedicineBeanList.add(alertMedicineBean);
        }
        Collections.sort(indexString);
        sidebarAlertMedicine.setIndexText(indexString);
        Collections.sort(mAlertMedicineBeanList, new PinyinComparator());

        // 设置适配器
        mAlertMedicineAdapter = new AlertMedicineAdapter(mAlertMedicineBeanList);
        mAlertMedicineAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recycleAlertMedicineList.setAdapter(mAlertMedicineAdapter);
        mAlertMedicineAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AlertMedicineBean alertMedicineBean = (AlertMedicineBean) adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(AlertAddLowMedicineFragment.Low_Add_Intent_Flag, alertMedicineBean.getName());
                setResult(0, intent);
                finish();
            }
        });
    }


    /**
     * 根据输入筛选数据
     */
    private void filterDataBySearchKey(String key) {
        searchKey = key;
        List<AlertMedicineBean> searchData  = new ArrayList<>();
        if (TextUtils.isEmpty(key)) {
            searchData = mAlertMedicineBeanList;
        }else{
            for (AlertMedicineBean alertMedicineBean : mAlertMedicineBeanList) {
                String name = alertMedicineBean.getName();
                // 检索
                if (name.toUpperCase().indexOf(key.toString().toUpperCase()) != -1
                        || PinyinUtils.getPingYin(name).toUpperCase().startsWith(key.toString().toUpperCase())) {
                    searchData.add(alertMedicineBean);
                }
            }
        }
        if (searchData.size()==0) {
            // 显示无数据页面
            sidebarAlertMedicine.setVisibility(View.GONE);
            recycleAlertMedicineList.setVisibility(View.GONE);
            rlAlertMedicineNoData.setVisibility(View.VISIBLE);
            String emptyPlace = getString(R.string.alert_medicine_search_empty);
            tvAlertNoMedicineTip.setText(String.format(emptyPlace,key));
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(AlertMedicineActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }else{
            // 显示列表
            sidebarAlertMedicine.setVisibility(View.VISIBLE);
            recycleAlertMedicineList.setVisibility(View.VISIBLE);
            rlAlertMedicineNoData.setVisibility(View.GONE);
        }
        // 根据a-z进行排序
        Collections.sort(searchData, new PinyinComparator());
        mAlertMedicineAdapter.setNewData(searchData);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            if (!subscribe.isUnsubscribed()) {
                subscribe.unsubscribe();
            }
        }
    }
}
