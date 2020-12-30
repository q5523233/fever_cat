package com.nepo.fevercat.ui.follow;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.ui.follow.bean.FollowEditReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;
import com.nepo.fevercat.ui.follow.contract.FollowAddContract;
import com.nepo.fevercat.ui.follow.model.FollowModel;
import com.nepo.fevercat.ui.follow.presenter.FollowPresenter;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow
 * 文件名:  FollowEditActivity
 * 作者 :   <sen>
 * 时间 :  下午2:28 2017/8/10.
 * 描述 :  编辑亲友关注
 */

public class FollowEditActivity extends BaseActivity<FollowPresenter, FollowModel> implements FollowAddContract.View {


    @BindView(R.id.edt_follow_add_nick_name)
    EditText edtFollowAddNickName;
    @BindView(R.id.tv_follow_add_input_err_hint)
    TextView tvFollowAddInputErrHint;

    private FollowListResBean.BindUsersBean mBindUsersBean;

    private AnimatorSet mAnimatorSet;

    public static final String Follow_Edit_Flag = "Follow_Edit_Flag";

    public static Intent newIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, FollowEditActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_edit_follow;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        HideUtil.init(this);

        Bundle extras = getIntent().getExtras();
        mBindUsersBean = (FollowListResBean.BindUsersBean) extras.getSerializable(Follow_Edit_Flag);
        edtFollowAddNickName.setText(mBindUsersBean.getBindNickname());

    }

    /**
     * 返回
     */
    @OnClick(R.id.rl_follow_add_bar_back)
    public void onBackClick() {
        finish();
    }


    /**
     * 提交
     */
    @OnClick(R.id.rl_follow_add_confirm)
    public void onConfirmClick() {

        String nickNameStr = edtFollowAddNickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickNameStr)) {
            tvFollowAddInputErrHint.setText(getString(R.string.follow_add_nick_name_hint));
            shakeAnimate();
            return;
        }
        if (AppConstant.IS_OFFLINEMODE)
        {
            FollowListResBean followListResBean = SharedPreferencesUtil.getObject(AppConstant.kEY_FREIEND, FollowListResBean.class);
            for (FollowListResBean.BindUsersBean usersBean : followListResBean.getBindUsers()) {
                if (mBindUsersBean.getBindUserName().equals(usersBean.getBindUserName()))
                {
                    usersBean.setBindNickname(edtFollowAddNickName.getText().toString());
                    break;
                }
            }
            SharedPreferencesUtil.set(AppConstant.kEY_FREIEND,followListResBean);
            setResult(0);
            finish();
            return;
        }
        FollowEditReqBean followEditReqBean = new FollowEditReqBean();
        followEditReqBean.setAccountId(AppConstant.getUserInfo().getUserId())
                .setBindUserId(mBindUsersBean.getBindUserId())
                .setNickName(nickNameStr)
                .setTRANSID(ApiConstants.BIND_FRIENDS_EDIT);
        mPresenter.EditFollowInfoRequest(followEditReqBean,true);


    }

    /**

    /**
     * 错误动画
     */
    private void shakeAnimate() {
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }
        tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
        mAnimatorSet.setDuration(1500);
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(tvFollowAddInputErrHint, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0));
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvFollowAddInputErrHint.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimatorSet.start();


    }

    @Override
    public void returnCodeInfo(MainValidateResBean mainValidateResBean) {

    }

    @Override
    public void returnPutInfo(BaseResBean baseResBean) {
        if (baseResBean.isOk()) {
            setResult(0);
            finish();
        }else{
            tvFollowAddInputErrHint.setText(baseResBean.getMsg());
            shakeAnimate();
        }

    }

    @Override
    public void returnErrInfo(String errMsg) {

    }
}
