package com.nepo.fevercat.ui.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseFragment;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.event.BBInfoEvent;
import com.nepo.fevercat.event.LoginOutMsgEvent;
import com.nepo.fevercat.ui.main.LoginActivity;
import com.nepo.fevercat.ui.main.bean.MainLoginResBean;
import com.nepo.fevercat.ui.mine.AboutActivity;
import com.nepo.fevercat.ui.mine.AdjustActivity;
import com.nepo.fevercat.ui.mine.DisclaimerActivity;
import com.nepo.fevercat.ui.mine.ExcelActivity;
import com.nepo.fevercat.ui.mine.HelpActivity;
import com.nepo.fevercat.ui.mine.MineEditActivity;
import com.nepo.fevercat.ui.mine.MineEditBBInfoActivity;
import com.nepo.fevercat.ui.mine.adapter.MineBBListAdapter;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.mine.contract.MineBBInfoContract;
import com.nepo.fevercat.ui.mine.model.MineBBInfoModel;
import com.nepo.fevercat.ui.mine.presenter.MineBBInfoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.fragment
 * 文件名:  MineFragment
 * 作者 :   <sen>
 * 时间 :  上午11:27 2017/4/24.
 * 描述 :  我的
 */

public class MineFragment extends BaseFragment<MineBBInfoPresenter, MineBBInfoModel> implements MineBBInfoContract.View {


    @BindView(R.id.iv_mine_user_pic)
    CircularImage ivMineUserPic;
    @BindView(R.id.tv_mine_user_name)
    TextView tvMineUserName;
    @BindView(R.id.iv_mine_user_name_edit)
    ImageView ivMineUserNameEdit;
    @BindView(R.id.iv_mine_bb_first_pic)
    CircularImage ivMineBbFirstPic;
    @BindView(R.id.tv_mine_bb_first_name)
    TextView tvMineBbFirstName;
    @BindView(R.id.tv_mine_bb_first_hot)
    TextView tvMineBbFirstHot;
    @BindView(R.id.ll_mine_bb_first)
    LinearLayout llMineBbFirst;
    @BindView(R.id.iv_mine_bb_second_pic)
    CircularImage ivMineBbSecondPic;
    @BindView(R.id.tv_mine_bb_second_name)
    TextView tvMineBbSecondName;
    @BindView(R.id.tv_mine_bb_second_hot)
    TextView tvMineBbSecondHot;
    @BindView(R.id.ll_mine_bb_second)
    LinearLayout llMineBbSecond;
    @BindView(R.id.iv_mine_bb_third_pic)
    CircularImage ivMineBbThirdPic;
    @BindView(R.id.tv_mine_bb_third_name)
    TextView tvMineBbThirdName;
    @BindView(R.id.tv_mine_bb_third_hot)
    TextView tvMineBbThirdHot;
    @BindView(R.id.ll_mine_bb_third)
    LinearLayout llMineBbThird;
    @BindView(R.id.tbtn_mine_temp_unit)
    ToggleButton tbtnMineTempUnit;
    @BindView(R.id.rl_mine_temp_unit)
    RelativeLayout rlMineTempUnit;
    @BindView(R.id.rl_mine_help)
    RelativeLayout rlMineHelp;
    @BindView(R.id.rl_mine_disclaimer)
    RelativeLayout rlMineDisclaimer;
    @BindView(R.id.rl_mine_about)
    RelativeLayout rlMineAbout;
    @BindView(R.id.rl_mine_login_out)
    RelativeLayout rlMineLoginOut;
    @BindView(R.id.tv_mine_login)
    TextView tvMineLogin;
    @BindView(R.id.rl_mine_user_contain)
    RelativeLayout rlMineUserContain;
    @BindView(R.id.iv_mine_bb_first_pic_default)
    CircularImage ivMineBbFirstPicDefault;
    @BindView(R.id.iv_mine_bb_second_pic_default)
    CircularImage ivMineBbSecondPicDefault;
    @BindView(R.id.iv_mine_bb_third_pic_default)
    CircularImage ivMineBbThirdPicDefault;
    @BindView(R.id.recycle_mine_bb_list)
    RecyclerView recycleMineBbList;


    private static final int MINE_LOGIN_CODE = 150;
    private static final int MINE_EDIT_CODE = 737;
    private static final int MINE_EDIT_BB_CODE = 735;
    @BindView(R.id.rl_mine_document)
    RelativeLayout rlMineDocument;
    @BindView(R.id.rl_excel_Quantization)
    RelativeLayout rlExcelQuantization;
    @BindView(R.id.rl_excel_motion)
    RelativeLayout rlExcelMotion;


    // 宝宝信息
    private MineBBResBean mMineBBResBean;
    //宝宝列表
    private MineBBListAdapter mBBListInfoAdapter;


    @Override
    protected int getLayoutResource() {
        return R.layout.frag_mine;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initOfflineMode();
        recycleMineBbList.setHasFixedSize(true);
        recycleMineBbList.setLayoutManager(new LinearLayoutManager(mActivity, OrientationHelper.HORIZONTAL, false));
    }

    /**
     * 离线模式下隐藏部分功能
     */
    private void initOfflineMode() {
        if (AppConstant.IS_OFFLINEMODE) {
            rlMineLoginOut.setVisibility(View.GONE);
            rlMineUserContain.setVisibility(View.GONE);
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initUserData();
            initBBData();

        }
    }

    private void initUserData() {
        // 刷新用户信息(头像、昵称、是否可编辑、登录按钮信息)
        if (AppConstant.isLogin()) {
            MainLoginResBean userInfo = AppConstant.getUserInfo();
            ConstantUtils.loadLoginUserImg(userInfo.getHeadImageUrl(), ivMineUserPic);
            tvMineUserName.setText(userInfo.getNickName());
            ivMineUserNameEdit.setVisibility(View.VISIBLE);
            tvMineLogin.setText(getString(R.string.mine_login_out));
            tvMineLogin.setTextColor(ContextCompat.getColor(mActivity, R.color.color_07));

        } else {
            Glide.clear(ivMineUserPic);
            ivMineUserPic.setBackgroundResource(R.drawable.icon_no_login_default);
            tvMineUserName.setText(getString(R.string.mine_no_login));
            ivMineUserNameEdit.setVisibility(View.GONE);
            tvMineLogin.setText(getString(R.string.login));
            tvMineLogin.setTextColor(ContextCompat.getColor(mActivity, R.color.color_02));
        }

        // 切换温度单位(读取开关配置)
        String tempSwitch = SharedPreferencesUtil.getString(AppConstant.SP_TEMP_SWITCH, "true");
        tbtnMineTempUnit.setChecked(Boolean.valueOf(tempSwitch));
    }


    private void initBBData() {
        // 获取宝宝列表
        MineBBReqBean mineBBReqBean = new MineBBReqBean();
        mineBBReqBean
                .setAccountId(AppConstant.getUserInfo().getUserId())
                .setOperateID(ApiConstants.MODIFY_BABY_LIST_4)
                .setTRANSID(ApiConstants.MODIFY_BABY);
        mPresenter.putMineBBInfoRequest(mineBBReqBean, false);
    }


    /**
     * 绘制BB列表信息
     */
    private void initBBListInfo() {
        BabyInfosBean babyInfosBean = new BabyInfosBean();
        babyInfosBean.setAdd(true);
        if (mMineBBResBean != null
                && mMineBBResBean.getBabyInfos() != null
                && mMineBBResBean.getBabyInfos().size() > 0
                ) {
            List<BabyInfosBean> babyInfos = mMineBBResBean.getBabyInfos();
            babyInfos.add(babyInfosBean);
            mBBListInfoAdapter = new MineBBListAdapter(babyInfos);
            mBBListInfoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recycleMineBbList.setAdapter(mBBListInfoAdapter);
        } else {
            List<BabyInfosBean> babyInfosBeanList = new ArrayList<>();
            babyInfosBeanList.add(babyInfosBean);
            mBBListInfoAdapter = new MineBBListAdapter(babyInfosBeanList);
            mBBListInfoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recycleMineBbList.setAdapter(mBBListInfoAdapter);
        }
        // 宝宝点击
        mBBListInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BabyInfosBean babyInfosBean = (BabyInfosBean) adapter.getItem(position);
                if (babyInfosBean.isAdd()) {
                    if (AppConstant.IsDevicesConn)
                    {
                        ToastUtils.showToast(getString(R.string.conn_add_bb_info_err));
                        return;
                    }
                    // 添加宝宝
                    if (AppConstant.isLogin() || AppConstant.IS_OFFLINEMODE) {
                        if (adapter.getItemCount() == AppConstant.Max_BB_Info) {
                            ToastUtils.showToast(R.string.add_bb_err);
                            return;
                        }
                        startActivityForResult(MineEditBBInfoActivity.newIntent(mActivity, null), MINE_EDIT_BB_CODE);
                    } else {
                        startActivityForResult(LoginActivity.newIntent(mActivity), MINE_LOGIN_CODE);
                    }
                } else {
                    if (AppConstant.IsDevicesConn) {
                        ToastUtils.showToast(getString(R.string.conn_edit_bb_info_err));
                        return;
                    }
                    // 编辑宝宝
                    Bundle bundle = new Bundle();
                    bundle.putString(MineEditBBInfoActivity.MineEditBBInfo_Bundle_Tag, babyInfosBean.getLocalId());
                    bundle.putString(MineEditBBInfoActivity.MineEditBBInfo_Bundle_BabyID_Tag, babyInfosBean.getBabyId());
                    startActivityForResult(MineEditBBInfoActivity.newIntent(mActivity, bundle), MINE_EDIT_BB_CODE);
                }
            }
        });
    }


    /**
     * 初始化宝宝信息 废弃
     */
    private void initBBInfo() {
        if (mMineBBResBean != null
                && mMineBBResBean.getBabyInfos() != null
                && mMineBBResBean.getBabyInfos().size() > 0
                ) {
            // 绘制宝宝列表
            int size = mMineBBResBean.getBabyInfos().size();
            List<BabyInfosBean> babyInfos;
            BabyInfosBean babyInfosBean;
            switch (size) {
                case 1:
                    // 1 显示
                    babyInfos = mMineBBResBean.getBabyInfos();
                    babyInfosBean = babyInfos.get(0);
                    ivMineBbFirstPicDefault.setVisibility(View.GONE);
                    ivMineBbFirstPic.setVisibility(View.VISIBLE);
                    ConstantUtils.loadBBImg(babyInfosBean.getHeadImageUrl(), ivMineBbFirstPic);
                    tvMineBbFirstName.setTextColor(ContextCompat.getColor(mActivity, R.color.light_blue));
                    tvMineBbFirstName.setText(babyInfosBean.getNickname());
                    tvMineBbFirstHot.setText(getString(R.string.empty));

                    // 2 还原
                    ivMineBbSecondPic.setVisibility(View.GONE);
                    ivMineBbSecondPicDefault.setVisibility(View.VISIBLE);
                    ivMineBbSecondPicDefault.setBackgroundResource(R.drawable.icon_real_time_bb_add_ic);
                    tvMineBbSecondName.setTextColor(ContextCompat.getColor(mActivity, R.color.bg_white_gray));
                    tvMineBbSecondName.setText(getString(R.string.bb_add));
                    tvMineBbSecondHot.setText("");


                    // 3 还原
                    ivMineBbThirdPic.setVisibility(View.GONE);
                    ivMineBbThirdPicDefault.setVisibility(View.VISIBLE);
                    ivMineBbThirdPicDefault.setBackgroundResource(R.drawable.icon_real_time_bb_add_ic);
                    tvMineBbThirdName.setTextColor(ContextCompat.getColor(mActivity, R.color.bg_white_gray));
                    tvMineBbThirdName.setText(getString(R.string.bb_add));
                    tvMineBbThirdHot.setText("");

                    llMineBbFirst.setTag(true);
                    llMineBbSecond.setTag(false);
                    llMineBbThird.setTag(false);

                    break;
                case 2:
                    // 1 显示
                    babyInfos = mMineBBResBean.getBabyInfos();
                    babyInfosBean = babyInfos.get(0);
                    ivMineBbFirstPic.setVisibility(View.VISIBLE);
                    ivMineBbFirstPicDefault.setVisibility(View.GONE);
                    ConstantUtils.loadBBImg(babyInfosBean.getHeadImageUrl(), ivMineBbFirstPic);
                    tvMineBbFirstName.setTextColor(ContextCompat.getColor(mActivity, R.color.light_blue));
                    tvMineBbFirstName.setText(babyInfosBean.getNickname());
                    tvMineBbFirstHot.setText(getString(R.string.empty));
                    // 2 显示
                    babyInfos = mMineBBResBean.getBabyInfos();
                    babyInfosBean = babyInfos.get(1);
                    ivMineBbSecondPic.setVisibility(View.VISIBLE);
                    ivMineBbSecondPicDefault.setVisibility(View.GONE);
                    ConstantUtils.loadBBImg(babyInfosBean.getHeadImageUrl(), ivMineBbSecondPic);
                    tvMineBbSecondName.setTextColor(ContextCompat.getColor(mActivity, R.color.light_blue));
                    tvMineBbSecondName.setText(babyInfosBean.getNickname());
                    tvMineBbSecondHot.setText(getString(R.string.empty));
                    // 3 还原
                    ivMineBbThirdPic.setVisibility(View.GONE);
                    ivMineBbThirdPicDefault.setVisibility(View.VISIBLE);
                    ivMineBbThirdPicDefault.setBackgroundResource(R.drawable.icon_real_time_bb_add_ic);
                    tvMineBbThirdName.setTextColor(ContextCompat.getColor(mActivity, R.color.bg_white_gray));
                    tvMineBbThirdName.setText(getString(R.string.bb_add));
                    tvMineBbThirdHot.setText("");

                    llMineBbFirst.setTag(true);
                    llMineBbSecond.setTag(true);
                    llMineBbThird.setTag(false);
                    break;
                case 3:
                    // 1 显示
                    babyInfos = mMineBBResBean.getBabyInfos();
                    babyInfosBean = babyInfos.get(0);
                    ivMineBbFirstPic.setVisibility(View.VISIBLE);
                    ivMineBbFirstPicDefault.setVisibility(View.GONE);
                    ConstantUtils.loadBBImg(babyInfosBean.getHeadImageUrl(), ivMineBbFirstPic);
                    tvMineBbFirstName.setTextColor(ContextCompat.getColor(mActivity, R.color.light_blue));
                    tvMineBbFirstName.setText(babyInfosBean.getNickname());
                    tvMineBbFirstHot.setText(getString(R.string.empty));
                    // 2 显示
                    babyInfos = mMineBBResBean.getBabyInfos();
                    babyInfosBean = babyInfos.get(1);
                    ivMineBbSecondPic.setVisibility(View.VISIBLE);
                    ivMineBbSecondPicDefault.setVisibility(View.GONE);
                    ConstantUtils.loadBBImg(babyInfosBean.getHeadImageUrl(), ivMineBbSecondPic);
                    tvMineBbSecondName.setTextColor(ContextCompat.getColor(mActivity, R.color.light_blue));
                    tvMineBbSecondName.setText(babyInfosBean.getNickname());
                    tvMineBbSecondHot.setText(getString(R.string.empty));
                    // 3 显示
                    babyInfos = mMineBBResBean.getBabyInfos();
                    babyInfosBean = babyInfos.get(2);
                    ivMineBbThirdPic.setVisibility(View.VISIBLE);
                    ivMineBbThirdPicDefault.setVisibility(View.GONE);
                    ConstantUtils.loadBBImg(babyInfosBean.getHeadImageUrl(), ivMineBbThirdPic);
                    tvMineBbThirdName.setTextColor(ContextCompat.getColor(mActivity, R.color.light_blue));
                    tvMineBbThirdName.setText(babyInfosBean.getNickname());
                    tvMineBbThirdHot.setText(getString(R.string.empty));

                    llMineBbFirst.setTag(true);
                    llMineBbSecond.setTag(true);
                    llMineBbThird.setTag(true);
                    break;
            }


        } else {
            // 还原宝宝列表

            llMineBbFirst.setTag(false);
            llMineBbSecond.setTag(false);
            llMineBbThird.setTag(false);

            // 1 还原
            ivMineBbFirstPic.setVisibility(View.GONE);
            ivMineBbFirstPicDefault.setVisibility(View.VISIBLE);
            ivMineBbFirstPicDefault.setBackgroundResource(R.drawable.icon_real_time_bb_add_ic);
            tvMineBbFirstName.setTextColor(ContextCompat.getColor(mActivity, R.color.bg_white_gray));
            tvMineBbFirstName.setText(getString(R.string.bb_add));
            tvMineBbFirstHot.setText("");
            // 2 还原
            ivMineBbSecondPic.setVisibility(View.GONE);
            ivMineBbSecondPicDefault.setVisibility(View.VISIBLE);
            ivMineBbSecondPicDefault.setBackgroundResource(R.drawable.icon_real_time_bb_add_ic);
            tvMineBbSecondName.setTextColor(ContextCompat.getColor(mActivity, R.color.bg_white_gray));
            tvMineBbSecondName.setText(getString(R.string.bb_add));
            tvMineBbSecondHot.setText("");

            // 3 还原
            ivMineBbThirdPic.setVisibility(View.GONE);
            ivMineBbThirdPicDefault.setVisibility(View.VISIBLE);
            ivMineBbThirdPicDefault.setBackgroundResource(R.drawable.icon_real_time_bb_add_ic);
            tvMineBbThirdName.setTextColor(ContextCompat.getColor(mActivity, R.color.bg_white_gray));
            tvMineBbThirdName.setText(getString(R.string.bb_add));
            tvMineBbThirdHot.setText("");
        }
    }


    /**
     * 编辑用户信息
     */
    @OnClick(R.id.rl_mine_user_contain)
    public void onUserInfoClick() {
        if (AppConstant.isLogin()) {
            // 编辑
            startActivityForResult(MineEditActivity.newIntent(mActivity), MINE_EDIT_CODE);
        } else {
            // 登录
            startActivityForResult(LoginActivity.newIntent(mActivity), MINE_LOGIN_CODE);
        }
    }

    /**
     * 宝宝点击
     */
    @OnClick({R.id.ll_mine_bb_first, R.id.ll_mine_bb_second, R.id.ll_mine_bb_third})
    public void onBBInfoClick(LinearLayout linearLayout) {
        //Tag=true 有信息可编辑，=false 无信息添加
        Bundle bundle = null;
        switch (linearLayout.getId()) {
            case R.id.ll_mine_bb_first:
                if ((Boolean) llMineBbFirst.getTag()) {
                    if (AppConstant.IsDevicesConn) {
                        ToastUtils.showToast(getString(R.string.conn_edit_bb_info_err));
                        return;
                    }
                    bundle = new Bundle();
                    BabyInfosBean babyInfosBean = mMineBBResBean.getBabyInfos().get(0);
                    bundle.putString(MineEditBBInfoActivity.MineEditBBInfo_Bundle_Tag, babyInfosBean.getLocalId());
                }
                break;
            case R.id.ll_mine_bb_second:
                if ((Boolean) llMineBbSecond.getTag()) {
                    if (AppConstant.IsDevicesConn) {
                        ToastUtils.showToast(getString(R.string.conn_edit_bb_info_err));
                        return;
                    }
                    bundle = new Bundle();
                    BabyInfosBean babyInfosBean = mMineBBResBean.getBabyInfos().get(1);
                    bundle.putString(MineEditBBInfoActivity.MineEditBBInfo_Bundle_Tag, babyInfosBean.getLocalId());
                }
                break;
            case R.id.ll_mine_bb_third:
                if ((Boolean) llMineBbThird.getTag()) {

                    bundle = new Bundle();
                    BabyInfosBean babyInfosBean = mMineBBResBean.getBabyInfos().get(2);
                    bundle.putString(MineEditBBInfoActivity.MineEditBBInfo_Bundle_Tag, babyInfosBean.getLocalId());
                }
                break;
        }
        startActivityForResult(MineEditBBInfoActivity.newIntent(mActivity, bundle), MINE_EDIT_BB_CODE);

    }


    /**
     * 温度切换
     */
    @OnClick(R.id.tbtn_mine_temp_unit)
    public void onTempSwitchClick() {
        SharedPreferencesUtil.set(AppConstant.SP_TEMP_SWITCH, String.valueOf(tbtnMineTempUnit.isChecked()));
    }

    /**
     * 帮助
     */
    @OnClick(R.id.rl_mine_help)
    public void onHelpClick() {
        startAct(HelpActivity.newIntent(mActivity));
    }

    /**
     * 免责声明
     */
    @OnClick(R.id.rl_mine_disclaimer)
    public void onDisclaimerClick() {
        startAct(DisclaimerActivity.newIntent(mActivity));
    }

    /**
     * 关于我们
     */
    @OnClick(R.id.rl_mine_about)
    public void onAboutClick() {
        String url;
        if (TextUtils.equals(getResources().getConfiguration().locale.getLanguage(), "zh")) {
            // 中文
            url = "file:///android_asset/about.html";
        } else {
            // 非中文
            url = "file:///android_asset/about_en.html";
        }
        startAct(AboutActivity.newIntent(mActivity, url, getString(R.string.mine_about)));
    }

    /**
     * 退出登录
     */
    @OnClick(R.id.rl_mine_login_out)
    public void onLoginOutClick() {
        if (AppConstant.isLogin()) {
            // 退出登录
            AppConstant.saveUserInfo(null);
            AppConstant.sMainLoginResBean = null;
            mMineBBResBean = null;
            //刷新界面
            initUserData();
            // 通知各个界面 用户退出
            EventBus.getDefault().post(new LoginOutMsgEvent());
        } else {
            // 跳转登录
            startActivityForResult(LoginActivity.newIntent(mActivity), MINE_LOGIN_CODE);
        }
    }


    @Override
    public void returnBBInfo(MineBBResBean mineBBResBean) {
        if (mineBBResBean.isOk()) {
            mMineBBResBean = mineBBResBean;
            initBBListInfo();
        }
    }

    @Override
    public void returnUploadInfo(MineUploadResBean mineUploadResBean) {

    }

    @Override
    public void returnErrInfo(String errMsg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MINE_EDIT_CODE) {
            //修改用户信息
            initUserData();
        }
    }

    /**
     * 刷新寶寶信息
     *
     * @param bbInfoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventMsg(BBInfoEvent bbInfoEvent) {
        //刷新界面
        initBBData();
    }

    /**
     * 登录/退出登录
     *
     * @param loginOutMsgEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLoginOutMsg(LoginOutMsgEvent loginOutMsgEvent) {
        //刷新界面
        initBBData();
        initUserData();
    }

    /**
     * 校准密码
     */
    @OnClick(R.id.rl_mine_adjust)
    public void onAdjustClick() {
        startAct(AdjustActivity.newIntent(mActivity));

    }

    /**
     * 对称平衡测量操作及注意事项
     */
    @OnClick(R.id.rl_mine_document)
    public void onViewClicked() {
        startAct(AboutActivity.newIntent(mActivity, "file:///android_asset/attention.html", getString(R.string.mine_document)));
    }

    @OnClick({R.id.rl_excel_Quantization, R.id.rl_excel_motion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_excel_Quantization:
                ExcelActivity.newIntent(getContext(), 1);
                break;
            case R.id.rl_excel_motion:
                ExcelActivity.newIntent(getContext(), 0);
                break;
        }
    }
}
