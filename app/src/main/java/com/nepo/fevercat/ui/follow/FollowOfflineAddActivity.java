package com.nepo.fevercat.ui.follow;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.utils.VerificationUtils;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sm on 2019/3/12.
 */

public class FollowOfflineAddActivity extends BaseActivity {
    @BindView(R.id.iv_follow_add_edt_left_nick_name)
    TextView ivFollowAddEdtLeftNickName;
    @BindView(R.id.edt_follow_add_nick_name)
    EditText edtFollowAddNickName;
    @BindView(R.id.iv_follow_add_edt_left_phone)
    TextView ivFollowAddEdtLeftPhone;
    @BindView(R.id.edt_follow_add_phone)
    EditText edtFollowAddPhone;
    @BindView(R.id.iv_follow_add_edt_left_code)
    TextView ivFollowAddEdtLeftCode;
    @BindView(R.id.edt_follow_add_code)
    EditText edtFollowAddCode;
    @BindView(R.id.rl_verify_code)
    RelativeLayout rlVerifyCode;
    @BindView(R.id.rl_follow_add_get_code)
    RelativeLayout rlFollowAddGetCode;
    @BindView(R.id.tv_follow_add_input_err_hint)
    TextView tvFollowAddInputErrHint;
    @BindView(R.id.rl_follow_add_confirm)
    RelativeLayout rlFollowAddConfirm;
    @BindView(R.id.tv_follow_add_get_code)
    TextView tvFollowAddGetCode;
    private AnimatorSet mAnimatorSet;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FollowOfflineAddActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_follow_add;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        HideUtil.init(this);
        if (AppConstant.IS_OFFLINEMODE) {
            rlVerifyCode.setVisibility(View.GONE);
        }
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
        if (checkInputInfo()) {
            FollowListResBean followData = SharedPreferencesUtil.getObject(AppConstant.kEY_FREIEND, FollowListResBean.class);
            if (followData == null) {
                followData = new FollowListResBean();
                followData.setCode("0");
            }
            List<FollowListResBean.BindUsersBean> users = followData.getBindUsers();
            if (users == null) {
                users = new ArrayList<>();
                followData.setBindUsers(users);
            }
            FollowListResBean.BindUsersBean bindUsersBean = new FollowListResBean.BindUsersBean();
            bindUsersBean.setBindNickname(edtFollowAddNickName.getText().toString());
            bindUsersBean.setBindUserName(edtFollowAddPhone.getText().toString());
            if (users.contains(bindUsersBean)) {
                for (FollowListResBean.BindUsersBean usersBean : followData.getBindUsers()) {
                    if (edtFollowAddPhone.getText().toString().equals(usersBean.getBindUserName()))
                    {
                        usersBean.setBindNickname(edtFollowAddNickName.getText().toString());
                        break;
                    }
                }
            } else {
                users.add(bindUsersBean);
            }
            SharedPreferencesUtil.set(AppConstant.kEY_FREIEND, followData);
            ToastUtils.showToast(getString(R.string.post_success));
            setResult(RESULT_OK);
            finish();
        }
    }


    /**
     * 检查输入
     *
     * @return
     */
    private boolean checkInputInfo() {

        String trimNameStr = edtFollowAddNickName.getText().toString().trim();
        String trimPhoneStr = edtFollowAddPhone.getText().toString().trim();

        if (TextUtils.isEmpty(trimNameStr)) {
            tvFollowAddInputErrHint.setText(getString(R.string.follow_add_nick_name_hint));
            tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (TextUtils.isEmpty(trimPhoneStr)) {
            tvFollowAddInputErrHint.setText(getString(R.string.follow_add_phone_hint));
            tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (!VerificationUtils.matcherPhoneNum(trimPhoneStr)) {
            tvFollowAddInputErrHint.setText(getString(R.string.register_edt_phone_err_hint));
            tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }
        return true;
    }

    /**
     * 错误动画
     */
    private void shakeAnimate() {
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }

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

}
