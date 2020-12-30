package com.nepo.fevercat.ui.follow.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseFragment;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.NetWorkUtils;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.event.LoginOutMsgEvent;
import com.nepo.fevercat.event.NetStatusEvent;
import com.nepo.fevercat.ui.follow.FollowAddActivity;
import com.nepo.fevercat.ui.follow.FollowEditActivity;
import com.nepo.fevercat.ui.follow.FollowOfflineAddActivity;
import com.nepo.fevercat.ui.follow.adapter.FollowAdapter;
import com.nepo.fevercat.ui.follow.bean.FollowListReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;
import com.nepo.fevercat.ui.follow.contract.FollowListContract;
import com.nepo.fevercat.ui.follow.model.FollowListModel;
import com.nepo.fevercat.ui.follow.presenter.FollowListPresenter;
import com.nepo.fevercat.ui.main.LoginActivity;
import com.nepo.fevercat.ui.main.bean.BaseResBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow.fragment
 * 文件名:  FollowFragment
 * 作者 :   <sen>
 * 时间 :  上午11:27 2017/4/24.
 * 描述 :  关注
 */

public class FollowFragment extends BaseFragment<FollowListPresenter, FollowListModel> implements FollowListContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rl_follow_no_data)
    RelativeLayout rlFollowNoData;
    @BindView(R.id.recycle_follow_list)
    RecyclerView recycleFollowList;
    @BindView(R.id.swipeLayout_follow)
    SwipeRefreshLayout swipeLayoutFollow;
    @BindView(R.id.iv_follow_add)
    ImageView ivFollowAdd;

    private static final int FOLLOW_FRIEND_ADD_CODE = 841;
    private static final int FOLLOW_LOGIN_CODE = 591;



    private FollowAdapter mFollowAdapter;


    @Override
    protected int getLayoutResource() {
        return R.layout.frag_follow;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        recycleFollowList.setHasFixedSize(true);
        recycleFollowList.setLayoutManager(new LinearLayoutManager(mActivity));
        swipeLayoutFollow.setOnRefreshListener(this);
        swipeLayoutFollow.setColorSchemeColors(Color.rgb(47, 223, 189));
    }

    /**
     * 添加亲友关注
     */
    @OnClick({R.id.iv_follow_add, R.id.rl_follow_confirm})
    public void onFollowAddClick() {
        if (AppConstant.IS_OFFLINEMODE)
        {
            startActivityForResult(FollowOfflineAddActivity.newIntent(mActivity),FOLLOW_FRIEND_ADD_CODE);
            return;
        }
        if (AppConstant.isLogin()) {
            startActivityForResult(FollowAddActivity.newIntent(mActivity), FOLLOW_FRIEND_ADD_CODE);
        } else {
            startActivityForResult(LoginActivity.newIntent(mActivity), FOLLOW_LOGIN_CODE);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initNetData();
        }
    }

    /**
     * 加载信息
     */
    private void initNetData() {
        if (AppConstant.isLogin()&& NetWorkUtils.isNetConnected(mActivity)||AppConstant.IS_OFFLINEMODE) {
            FollowListReqBean followListReqBean = new FollowListReqBean();
            followListReqBean
                    .setAccountId(AppConstant.getUserInfo().getUserId())
                    .setTRANSID(ApiConstants.BIND_FRIENDS_LIST);
            rlFollowNoData.setVisibility(View.GONE);
            recycleFollowList.setVisibility(View.VISIBLE);
            ivFollowAdd.setVisibility(View.VISIBLE);
            mPresenter.getFollowListInfoRequest(followListReqBean, false);
        } else {
            rlFollowNoData.setVisibility(View.VISIBLE);
            recycleFollowList.setVisibility(View.GONE);
            ivFollowAdd.setVisibility(View.GONE);
        }
    }

    private void initAdapter(FollowListResBean followListResBean) {

        if (followListResBean.getBindUsers() != null
                && followListResBean.getBindUsers().size()>0
                ) {
            mFollowAdapter = new FollowAdapter(followListResBean.getBindUsers());
            mFollowAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recycleFollowList.setAdapter(mFollowAdapter);
            mFollowAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    FollowListResBean.BindUsersBean bindUsersBean = (FollowListResBean.BindUsersBean) adapter.getItem(position);
                    if (view.getId() == R.id.item_del_btn) {
                        // 删除
                        adapter.getData().remove(position);
                        adapter.notifyItemRemoved(position);
                        // 发送请求
                        FollowListReqBean followListReqBean = new FollowListReqBean();
                        followListReqBean
                                .setAccountId(AppConstant.getUserInfo().getUserId())
                                .setBindId(bindUsersBean.getBindId())
                                .setTRANSID(ApiConstants.BIND_FRIENDS_DEL);
                        mPresenter.deleteLocalInfo(bindUsersBean);
                        mPresenter.putFollowInfoRequest(followListReqBean, false);
                    }else if(view.getId()== R.id.rl_item_follow_contain){
                        // 编辑
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(FollowEditActivity.Follow_Edit_Flag,bindUsersBean);
                        startActivityForResult(FollowEditActivity.newIntent(mActivity,bundle),FOLLOW_LOGIN_CODE);
                    }
                }
            });
        }else{
            rlFollowNoData.setVisibility(View.VISIBLE);
            recycleFollowList.setVisibility(View.GONE);
        }



    }

    @Override
    public void returnListInfo(FollowListResBean followListResBean) {
        swipeLayoutFollow.setRefreshing(false);
        if (followListResBean.isOk()) {
            initAdapter(followListResBean);
        } else {
            ToastUtils.showToast(followListResBean.getMsg());
        }
    }

    @Override
    public void returnPutInfo(BaseResBean baseResBean) {
        ToastUtils.showToast(baseResBean.getMsg());

    }

    @Override
    public void returnErrInfo(String errMsg) {
        ToastUtils.showToast(errMsg);
        swipeLayoutFollow.setRefreshing(false);
        rlFollowNoData.setVisibility(View.VISIBLE);
        recycleFollowList.setVisibility(View.GONE);
    }


    @Override
    public void onRefresh() {
        initNetData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FOLLOW_FRIEND_ADD_CODE) {
            initNetData();
        } else if (requestCode == FOLLOW_LOGIN_CODE) {
            initNetData();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 登录/退出登录
     * @param loginOutMsgEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLoginOutMsg(LoginOutMsgEvent loginOutMsgEvent) {

        initNetData();

    }

    /**
     * 网络切换
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNetStatusEventMsg(NetStatusEvent netStatusEvent){
        if (netStatusEvent.isNetConn()) {
            //刷新界面
            initNetData();
        }
    }

}
