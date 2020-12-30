package com.nepo.fevercat.ui.alert.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseFragment;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.GreenDaoAlertHeightUtils;
import com.nepo.fevercat.common.utils.GreenDaoAlertMedicineUtils;
import com.nepo.fevercat.common.utils.GreenDaoAlertWaterUtils;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.common.widget.popup.ListBBPopupWindow;
import com.nepo.fevercat.event.AlertMedicineEvent;
import com.nepo.fevercat.ui.alert.AlertHeightAddActivity;
import com.nepo.fevercat.ui.alert.AlertLowAddMedicineActivity;
import com.nepo.fevercat.ui.alert.AlertLowAddWaterActivity;
import com.nepo.fevercat.ui.alert.adapter.AlertHeightAdapter;
import com.nepo.fevercat.ui.alert.adapter.AlertLowAdapter;
import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;
import com.nepo.fevercat.ui.alert.contract.AlertHeightAddContract;
import com.nepo.fevercat.ui.alert.model.AlertModel;
import com.nepo.fevercat.ui.alert.presenter.AlertHeightPresenter;
import com.nepo.fevercat.ui.main.LoginActivity;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.alert.fragment
 * 文件名:  AlertFragment
 * 作者 :   <sen>
 * 时间 :  上午11:28 2017/4/24.
 * 描述 :  提醒
 */

public class AlertFragment extends BaseFragment<AlertHeightPresenter, AlertModel> implements View.OnClickListener, AlertHeightAddContract.View, ListBBPopupWindow.OnSelectItemListen {
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;

    @BindView(R.id.iv_alert_user_img)
    CircularImage ivAlertUserImg;
    @BindView(R.id.tv_alert_user_name)
    TextView tvAlertUserName;
    @BindView(R.id.tab_alert_nav)
    SegmentTabLayout tabAlertNav;
    @BindView(R.id.rl_temp_user_nav)
    RelativeLayout rlTempUserNav;
    @BindView(R.id.rl_alert_data)
    RelativeLayout rlAlertData;
    @BindView(R.id.rl_alert_confirm)
    RelativeLayout rlAlertConfirm;
    @BindView(R.id.rl_alert_no_data)
    RelativeLayout rlAlertNoData;
    @BindView(R.id.recycle_alert_list)
    RecyclerView recycleAlertList;
    @BindView(R.id.ll_alert_bottom_dialog)
    LinearLayout ll_alert_bottom_dialog;
    @BindView(R.id.tv_alert_selected)
    TextView tv_alert_selected;
    @BindView(R.id.tv_select_num)
    TextView tv_select_num;
    @BindView(R.id.btn_alter_delete)
    Button btn_alter_delete;
    @BindView(R.id.tv_alert_select_all)
    TextView tv_alert_select_all;


    private int mEditMode = MYLIVE_MODE_CHECK;

    private String[] mTitles = new String[3];
    private List<AlertHeightBean> mAlertHeightBeanList;
    private List<AlertLowBean> mAlertLowBeanList;
    private AlertHeightAdapter mAlertHeightAdapter;
    private AlertLowAdapter mAlertLowAdapter;
    BabyInfosBean mBabyInfosBean;

    private String currentType;

    private final int Alert_Request_Code = 953;
    private boolean editorStatus = false;
    private boolean isSelectAll = false;
    private int index;
    private boolean isFirstLoad = true;
    private MineBBResBean mMineBBResBean;
    private ListBBPopupWindow mListBBPopupWindow;

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_alert;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }


    @Override
    protected void initView() {
        String heightTempTitle = getString(R.string.alert_height_tip);
        String lowMedicineTitle = getString(R.string.alert_medicine_tip);
        String lowWaterTitle = getString(R.string.alert_water_tip);
        mTitles[0] = heightTempTitle;
        mTitles[1] = lowMedicineTitle;
        mTitles[2] = lowWaterTitle;
        tabAlertNav.setTabData(mTitles);
        recycleAlertList.setHasFixedSize(true);
        recycleAlertList.setLayoutManager(new LinearLayoutManager(mActivity));
        initNetBBInfo();
        currentType = "0";
        tabAlertNav.setCurrentTab(0);
        tabAlertNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    currentType = "0";
                    initHeightData();
                } else if (position == 1) {
                    currentType = "1";
                    initLowMedicineData();
                } else if (position == 2) {
                    currentType = "2";
                    initLowWaterData();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initAlert();
            if (mMineBBResBean.getBabyInfos().size() == 0){
                initNetBBInfo();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initAlert();
    }

    private void initAlert() {
        if (TextUtils.equals(currentType, "0")) {
            // 高温
            initHeightData();
        } else if (TextUtils.equals(currentType, "1")) {
            // 用药
            initLowMedicineData();
        } else if (TextUtils.equals(currentType, "2")) {
            // 喝水
            initLowWaterData();
        }
    }

    /**
     * 添加提醒
     */
    @OnClick({R.id.rl_alert_add, R.id.rl_alert_confirm})
    public void onAddClick() {
        if (TextUtils.equals(currentType, "0")) {
            // 高温
            if (null == mBabyInfosBean) {
                Toast.makeText(mActivity, getString(R.string.choice_empty_err_tip), Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(AlertHeightAddActivity.Baby_Bundle_Flag, mBabyInfosBean);
            startActivityForResult(AlertHeightAddActivity.newIntent(mActivity, bundle), Alert_Request_Code);
        } else if (TextUtils.equals(currentType, "1")) {
            // 用药
            if (null == mBabyInfosBean) {
                Toast.makeText(mActivity, getString(R.string.choice_empty_err_tip), Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(AlertLowAddMedicineActivity.Low_Add_Baby_Bundle_Flag, mBabyInfosBean);
            startActivityForResult(AlertLowAddMedicineActivity.newIntent(mActivity,bundle), Alert_Request_Code);
        } else if (TextUtils.equals(currentType, "2")) {
            // 喝水
            startActivityForResult(AlertLowAddWaterActivity.newIntent(mActivity, null), Alert_Request_Code);
        }
    }

    private void initBBListInfo() {
        mListBBPopupWindow = new ListBBPopupWindow(mActivity, mMineBBResBean);
        mListBBPopupWindow.setOnSelectItemListen(this);

        // 默认第一个BB
        if (mMineBBResBean.getBabyInfos() != null
                && mMineBBResBean.getBabyInfos().size() > 0) {
            mBabyInfosBean = mMineBBResBean.getBabyInfos().get(0);
        } else {
            ConstantUtils.loadLoginUserImg("http", ivAlertUserImg);
            tvAlertUserName.setText("");
        }
        if (mBabyInfosBean != null) {
            ConstantUtils.loadLoginUserImg(mBabyInfosBean.getHeadImageUrl(), ivAlertUserImg);
            tvAlertUserName.setText(mBabyInfosBean.getNickname());
            initHeightData();
        }

    }


    /**
     * 获取高温列表
     */
    private void initHeightData() {
        if (editorStatus) {
            updataEditMode();
        }
        mAlertHeightBeanList = new ArrayList<>();
        if (null != mBabyInfosBean) {
            mAlertHeightBeanList = GreenDaoAlertHeightUtils.getInstance().getAllAlertHeightListByBabyInfo(mBabyInfosBean);
        }
        if (mAlertHeightBeanList != null
                && mAlertHeightBeanList.size() > 0
                ) {
            hasDataView();
            mAlertHeightAdapter = new AlertHeightAdapter(mAlertHeightBeanList);
            mAlertHeightAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recycleAlertList.setAdapter(mAlertHeightAdapter);
            mAlertHeightAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    AlertHeightBean alertHeightBean = (AlertHeightBean) adapter.getItem(position);
                    if (view.getId() == R.id.ll_item_alert_contain) {
                        // 编辑
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(AlertHeightAddActivity.Alert_Height_Bundle_Flag, alertHeightBean);
                        bundle.putSerializable(AlertHeightAddActivity.Baby_Bundle_Flag, mBabyInfosBean);
                        startAct(AlertHeightAddActivity.newIntent(mActivity, bundle));
                    } else if (view.getId() == R.id.item_del_btn) {
                        // 删除
                        adapter.getData().remove(position);
                        adapter.notifyItemRemoved(position);
                        //移除高温提醒
                        GreenDaoAlertHeightUtils.getInstance().delAlertHeight(alertHeightBean);
                    } else if (view.getId() == R.id.switch_item_alert_height) {
                        // 关闭提醒
                        Switch switchView = (Switch) view;
                        alertHeightBean.setSCheckStatus(switchView.isChecked());
                        adapter.setData(position, alertHeightBean);
                        //更改高温开关
                        GreenDaoAlertHeightUtils.getInstance().updateAlertHeight(alertHeightBean);
                    }
                    if (adapter.getItemCount() == 0) {
                        noDataView();
                    }
                }
            });
        } else {
            noDataView();
        }

    }


    /**
     * 获取用药列表
     */
    private void initLowMedicineData() {
        if (editorStatus) {
            updataEditMode();
        }
        mAlertLowBeanList = new ArrayList<>();
        if (null != mBabyInfosBean) {
            mAlertLowBeanList = GreenDaoAlertMedicineUtils.getInstance().getAlertLowMedicineBeanListByBabyinfo(mBabyInfosBean);
        }
        if (mAlertLowBeanList != null
                && mAlertLowBeanList.size() > 0
                ) {
            hasDataView();

            mAlertLowAdapter = new AlertLowAdapter(mAlertLowBeanList);
            mAlertLowAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recycleAlertList.setAdapter(mAlertLowAdapter);
            //长按事件
            mAlertLowAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
                @Override
                public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.ll_item_alert_low_contain) {
                        updataEditMode();
                        return true;
                    }
                    return false;
                }
            });
            btn_alter_delete.setOnClickListener(this);
            tv_alert_select_all.setOnClickListener(this);
            mAlertLowAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    AlertLowBean alertLowBean = (AlertLowBean) adapter.getItem(position);
                    boolean isSelect = alertLowBean.isSelect();
                    if (view.getId() == R.id.item_del_btn) {
                        if (index > 0) {
                            index--;
                        }
                        // 删除
                        adapter.getData().remove(position);
                        adapter.notifyItemRemoved(position);
                        // 移除降温提醒
                        GreenDaoAlertMedicineUtils.getInstance().delAlertLowBean(alertLowBean);
                        // 取消闹钟
                        if (TextUtils.equals(alertLowBean.getMAlertStatus(), AppConstant.ALERT_MEDICINE_STATUS)) {
                            EventBus.getDefault().post(new AlertMedicineEvent().setCancelAlert(true).setAlertLowBean(alertLowBean));
                        }
                    } else if (view.getId() == R.id.switch_item_alert_low) {
                        // 开关状态
                        Switch switchView = (Switch) view;
                        alertLowBean.setMIsEnabledAlert(switchView.isChecked());
                        adapter.setData(position, alertLowBean);
                        // 修改降温提醒
                        GreenDaoAlertMedicineUtils.getInstance().updateAlertLowBean(alertLowBean);
                        // 取消闹钟/设定闹钟
                        if (TextUtils.equals(alertLowBean.getMAlertStatus(), AppConstant.ALERT_MEDICINE_STATUS)) {
                            if (!switchView.isChecked()) {
                                EventBus.getDefault().post(new AlertMedicineEvent().setCancelAlert(true).setAlertLowBean(alertLowBean));
                            } else {
                                EventBus.getDefault().post(new AlertMedicineEvent().setCancelAlert(false).setAlertLowBean(alertLowBean));
                            }
                        }
                    } else {
                        if (editorStatus) {
                            if (!isSelect) {
                                alertLowBean.setSelect(true);
                                index++;
                                if (index == adapter.getItemCount()) {
                                    isSelectAll = true;
                                    tv_alert_select_all.setText(getString(R.string.select_all_cancel));
                                }
                            } else {
                                alertLowBean.setSelect(false);
                                index--;
                                isSelectAll = false;
                                tv_alert_select_all.setText(getString(R.string.select_all));
                            }
                            setBtnBackground(index);
                            tv_select_num.setText(String.valueOf(index));
                            adapter.notifyItemChanged(position);
                        }
                    }
                    if (adapter.getItemCount() == 0) {
                        noDataView();
                    }
                }
            });
        } else {
            noDataView();
        }


    }

    /**
     * 获取喝水列表
     */
    private void initLowWaterData() {
        if (editorStatus) {
            updataEditMode();
        }
        mAlertLowBeanList = new ArrayList<>();
        mAlertLowBeanList = GreenDaoAlertWaterUtils.getInstance().getAlertLowWaterBeanList();
        if (mAlertLowBeanList != null
                && mAlertLowBeanList.size() > 0
                ) {
            hasDataView();

            mAlertLowAdapter = new AlertLowAdapter(mAlertLowBeanList);
            mAlertLowAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recycleAlertList.setAdapter(mAlertLowAdapter);
            //长按事件
            mAlertLowAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
                @Override
                public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.ll_item_alert_low_contain) {
                        updataEditMode();
                        return true;
                    }
                    return false;
                }
            });
            btn_alter_delete.setOnClickListener(this);
            tv_alert_select_all.setOnClickListener(this);

            mAlertLowAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    AlertLowBean alertLowBean = (AlertLowBean) adapter.getItem(position);
                    boolean isSelect = alertLowBean.isSelect();
                    if (view.getId() == R.id.item_del_btn && !editorStatus) {
                        // 删除
                        if (index > 0) {
                            index--;
                        }
                        adapter.getData().remove(position);
                        adapter.notifyItemRemoved(position);
                        // 移除降温提醒
                        GreenDaoAlertMedicineUtils.getInstance().delAlertLowBean(alertLowBean);
                        // 取消闹钟
                        if (TextUtils.equals(alertLowBean.getMAlertStatus(), AppConstant.ALERT_MEDICINE_STATUS)) {
                            EventBus.getDefault().post(new AlertMedicineEvent().setCancelAlert(true).setAlertLowBean(alertLowBean));
                        }
                    } else if (view.getId() == R.id.switch_item_alert_low && !editorStatus) {
                        // 开关状态
                        Switch switchView = (Switch) view;
                        alertLowBean.setMIsEnabledAlert(switchView.isChecked());
                        adapter.setData(position, alertLowBean);
                        // 修改降温提醒
                        GreenDaoAlertMedicineUtils.getInstance().updateAlertLowBean(alertLowBean);
                        // 取消闹钟/设定闹钟
                        if (TextUtils.equals(alertLowBean.getMAlertStatus(), AppConstant.ALERT_MEDICINE_STATUS)) {
                            if (!switchView.isChecked()) {
                                EventBus.getDefault().post(new AlertMedicineEvent().setCancelAlert(true).setAlertLowBean(alertLowBean));
                            } else {
                                EventBus.getDefault().post(new AlertMedicineEvent().setCancelAlert(false).setAlertLowBean(alertLowBean));
                            }
                        }
                    } else {
                        if (editorStatus) {
                            if (!isSelect) {
                                alertLowBean.setSelect(true);
                                index++;
                                if (index == adapter.getItemCount()) {
                                    isSelectAll = true;
                                    tv_alert_select_all.setText(getString(R.string.select_all_cancel));
                                }
                            } else {
                                alertLowBean.setSelect(false);
                                index--;
                                isSelectAll = false;
                                tv_alert_select_all.setText(getString(R.string.select_all));
                            }
                            setBtnBackground(index);
                            tv_select_num.setText(String.valueOf(index));
                            adapter.notifyItemChanged(position);
                        } else {
                            // 编辑
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(AlertLowAddWaterActivity.Low_Add_Intent_Flag, alertLowBean);
                            startAct(AlertLowAddWaterActivity.newIntent(mActivity, bundle));
                        }

                    }
                    if (adapter.getItemCount() == 0) {
                        noDataView();
                    }
                }
            });
        } else {
            noDataView();
        }
    }

    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            ll_alert_bottom_dialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            ll_alert_bottom_dialog.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        mAlertLowAdapter.setEditMode(mEditMode);
    }

    private void clearAll() {
        setAllItemSelectStatus(false, mAlertLowAdapter);
        isSelectAll = false;
        index = 0;
        tv_select_num.setText(String.valueOf(0));
        tv_alert_select_all.setText(getString(R.string.select_all));
        setBtnBackground(0);
    }

    /**
     * 全选和反选
     */
    private void selectAll() {
        if (mAlertLowAdapter == null) return;
        if (!isSelectAll) {
            setAllItemSelectStatus(true, mAlertLowAdapter);
            index = mAlertLowAdapter.getItemCount();
            tv_alert_select_all.setText("取消全选");
            isSelectAll = true;
        } else {
            setAllItemSelectStatus(false, mAlertLowAdapter);
            index = 0;
            tv_alert_select_all.setText("全选");
            isSelectAll = false;
        }
        mAlertLowAdapter.notifyDataSetChanged();
        setBtnBackground(index);
        tv_select_num.setText(String.valueOf(index));
    }

    /**
     * 无数据
     */
    private void noDataView() {
        rlAlertNoData.setVisibility(View.VISIBLE);
        recycleAlertList.setVisibility(View.GONE);

    }

    /**
     * 有数据
     */
    private void hasDataView() {
        rlAlertNoData.setVisibility(View.GONE);
        recycleAlertList.setVisibility(View.VISIBLE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Alert_Request_Code) {
            if (TextUtils.equals(currentType, "0")) {
                // 高温
                initHeightData();
            } else if (TextUtils.equals(currentType, "1")) {
                // 用药
                initLowMedicineData();
            } else if (TextUtils.equals(currentType, "2")) {
                // 喝水
                initLowWaterData();
            }

        }
    }


    public void setBtnBackground(int size) {
        if (size != 0) {
            btn_alter_delete.setBackgroundResource(R.drawable.button_shape);
            btn_alter_delete.setEnabled(true);
            btn_alter_delete.setTextColor(Color.WHITE);
        } else {
            btn_alter_delete.setBackgroundResource(R.drawable.button_noclickable_shape);
            btn_alter_delete.setEnabled(false);
            btn_alter_delete.setTextColor(ContextCompat.getColor(getContext(), R.color.color_b7b8bd));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_alert_select_all:
                selectAll();
                break;
            case R.id.btn_alter_delete:
                deleteItem();
                break;
        }
    }

    private void setAllItemSelectStatus(boolean isSelectAll, AlertLowAdapter adapter) {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            adapter.getItem(i).setSelect(isSelectAll);
        }
        btn_alter_delete.setEnabled(isSelectAll);
    }

    private void deleteItem() {
        if (index == 0) {
            btn_alter_delete.setEnabled(false);
            return;
        }
        for (int i = 0; i < mAlertLowAdapter.getItemCount(); ) {
            AlertLowBean alertLowBean = mAlertLowAdapter.getItem(i);
            if (alertLowBean.isSelect()) {
                GreenDaoAlertMedicineUtils.getInstance().delAlertLowBean(alertLowBean);
                // 取消闹钟
                if (TextUtils.equals(alertLowBean.getMAlertStatus(), AppConstant.ALERT_MEDICINE_STATUS)) {
                    EventBus.getDefault().post(new AlertMedicineEvent().setCancelAlert(true).setAlertLowBean(alertLowBean));
                }
                mAlertLowAdapter.getData().remove(alertLowBean);
            }
        }
        if (mAlertLowAdapter.getItemCount() == 0) {
            noDataView();
        }
        updataEditMode();
    }

    public void checkEditorStatus() {
        if (editorStatus) {
            updataEditMode();
        }
    }

    @Override
    public void returnBBInfo(MineBBResBean mineBBResBean) {

        if (mineBBResBean.isOk()) {
            mMineBBResBean = mineBBResBean;
            AppConstant.saveBBListInfo(mMineBBResBean);
            initBBListInfo();
        }

    }

    @Override
    public void returnErrInfo(String err) {

    }

    /**
     * 请求BB信息
     */
    private void initNetBBInfo() {

        // 判断是否有网络 true 请求网络 false 获取本地保存的
        MineBBReqBean mineBBReqBean = new MineBBReqBean();
        mineBBReqBean
                .setAccountId(AppConstant.getUserInfo().getUserId())
                .setOperateID(ApiConstants.MODIFY_BABY_LIST_4)
                .setTRANSID(ApiConstants.MODIFY_BABY);

        mPresenter.putMineBBInfoRequest(mineBBReqBean, false, isFirstLoad);
    }

    @Override
    public void selectStrItem(BabyInfosBean babyInfosBean) {
        mBabyInfosBean = babyInfosBean;
        if (mBabyInfosBean != null) {
            ConstantUtils.loadLoginUserImg(mBabyInfosBean.getHeadImageUrl(), ivAlertUserImg);
            tvAlertUserName.setText(mBabyInfosBean.getNickname());
            initAlert();
        }
    }


    @OnClick(R.id.ll_alert_user)
    public void onUserSelect() {
        if (!AppConstant.isLogin()&&!AppConstant.IS_OFFLINEMODE) {
            startAct(LoginActivity.newIntent(mActivity));
            return;
        }

        if (mListBBPopupWindow != null) {

//            mListBBPopupWindow.setWidth(DisplayMetricsUtil.dip2px(mActivity, 180));
            mListBBPopupWindow.setHeight(getResources().getDisplayMetrics().heightPixels / 2);
            mListBBPopupWindow.showAsDropDown(rlTempUserNav);
        }
    }
}
