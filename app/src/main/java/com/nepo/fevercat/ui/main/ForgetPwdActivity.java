package com.nepo.fevercat.ui.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.utils.RxCountDown;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.utils.VerificationUtils;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainForgetReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;
import com.nepo.fevercat.ui.main.contract.ForgetContract;
import com.nepo.fevercat.ui.main.model.ForgetModel;
import com.nepo.fevercat.ui.main.presenter.ForgetPresenter;


import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui
 * 文件名:  ForgetPwdActivity
 * 作者 :   <sen>
 * 时间 :  上午11:35 2017/4/25.
 * 描述 :  密码找回
 */

public class ForgetPwdActivity extends BaseActivity<ForgetPresenter,ForgetModel> implements ForgetContract.View {


    @BindView(R.id.iv_forget_edt_left_phone)
    ImageView ivForgetEdtLeftPhone;
    @BindView(R.id.edt_forget_phone)
    EditText edtForgetPhone;
    @BindView(R.id.iv_forget_edt_left_code)
    ImageView ivForgetEdtLeftCode;
    @BindView(R.id.edt_forget_code)
    EditText edtForgetCode;
    @BindView(R.id.rl_forget_get_code)
    RelativeLayout rlForgetGetCode;
    @BindView(R.id.iv_forget_edt_left_pwd)
    ImageView ivForgetEdtLeftPwd;
    @BindView(R.id.edt_forget_pwd)
    EditText edtForgetPwd;
    @BindView(R.id.iv_forget_edt_left_again_pwd)
    ImageView ivForgetEdtLeftAgainPwd;
    @BindView(R.id.edt_forget_again_pwd)
    EditText edtForgetAgainPwd;
    @BindView(R.id.rl_forget_confirm)
    RelativeLayout rlForgetConfirm;
    @BindView(R.id.tv_forget_input_err_hint)
    TextView tvForgetInputErrHint;
    @BindView(R.id.tv_forget_get_code)
    TextView tvForgetGetCode;


    private AnimatorSet mAnimatorSet;
    private MainValidateResBean mMainValidateResBean;
    private Subscription subscribe = null;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ForgetPwdActivity.class);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_forget_pwd;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        HideUtil.init(this);
    }


    /**
     * 返回
     */
    @OnClick(R.id.rl_forget_bar_back)
    public void onBackClick() {
        finish();
    }

    /**
     * 验证码
     */
    @OnClick(R.id.rl_forget_get_code)
    public void onGetCodeClick() {
        String trimPhoneStr = edtForgetPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trimPhoneStr)) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_phone_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return;
        }

        if (!VerificationUtils.matcherPhoneNum(trimPhoneStr)) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_phone_err_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return;
        }

        if (subscribe != null) {
            return;
        }

        MainValidateReqBean mainValidateReqBean = new MainValidateReqBean();
        mainValidateReqBean
                .setToNumber(trimPhoneStr)
                .setTRANSID(ApiConstants.VALIDATE_CODE);
        mPresenter.getCodeInfoRequest(mainValidateReqBean, true);


    }


    /**
     * 提交
     */
    @OnClick(R.id.rl_forget_confirm)
    public void onConfirmClick() {
        checkInputInfo();
    }

    private boolean checkInputInfo() {

        String trimPhoneStr = edtForgetPhone.getText().toString().trim();
        String trimCodeStr = edtForgetCode.getText().toString().trim();
        String trimPwdStr = edtForgetPwd.getText().toString().trim();
        String trimPwdAgainStr = edtForgetAgainPwd.getText().toString().trim();

        if (TextUtils.isEmpty(trimPhoneStr)) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_phone_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (!VerificationUtils.matcherPhoneNum(trimPhoneStr)) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_phone_err_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (TextUtils.isEmpty(trimCodeStr)) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_code_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (mMainValidateResBean == null) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_get_code_err_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (mMainValidateResBean != null) {
            if (!TextUtils.equals(mMainValidateResBean.getYzm(),trimCodeStr)) {
                tvForgetInputErrHint.setText(R.string.register_edt_get_code_err_hint);
                tvForgetInputErrHint.setVisibility(View.VISIBLE);
                shakeAnimate();
                if (subscribe != null) {
                    if (!subscribe.isUnsubscribed()) {
                        subscribe.unsubscribe();
                        countDownCompleted();
                    }
                }
                return false;
            }else{
                if(subscribe == null){
                    tvForgetInputErrHint.setText(R.string.register_edt_get_code_timeout_hint);
                    tvForgetInputErrHint.setVisibility(View.VISIBLE);
                    shakeAnimate();
                    return false;
                }
            }
        }
        if (TextUtils.isEmpty(trimPwdStr)) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_pwd_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (TextUtils.isEmpty(trimPwdAgainStr)) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_pwd_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (!TextUtils.equals(trimPwdStr, trimPwdAgainStr)) {
            tvForgetInputErrHint.setText(getString(R.string.register_edt_again_pwd_err_hint));
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        MainForgetReqBean mainForgetReqBean = new MainForgetReqBean();
        if (mMainValidateResBean != null) {
            mainForgetReqBean
                    .setMsgSid(mMainValidateResBean.getMsgSid())
                    .setValidateCode(mMainValidateResBean.getYzm());
        }
        mainForgetReqBean
                .setUserName(trimPhoneStr)
                .setNewPwd(trimPwdStr)
                .setTRANSID(ApiConstants.FORGOT_PASSWORD);
        mPresenter.getForgetInfoRequest(mainForgetReqBean,true);

        return true;
    }


    /**
     * 错误动画
     */
    private void shakeAnimate() {
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }

        mAnimatorSet.setDuration(1000);
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(tvForgetInputErrHint, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0));
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvForgetInputErrHint.setVisibility(View.GONE);
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

    /**
     * 正在计时
     */
    private void countDownNext(Integer integer) {
        String testStr = getResources().getString(R.string.register_resend_hint);
        String result = String.format(testStr, integer);
        tvForgetGetCode.setText(result);
    }

    /**
     * 计时完成
     */
    private void countDownCompleted() {
//        mMainValidateResBean = null;
        subscribe = null;
        tvForgetGetCode.setText(getResources().getString(R.string.register_edt_get_code_hint));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            if (!subscribe.isUnsubscribed()) {
                subscribe.unsubscribe();
                subscribe = null;
            }
        }
    }

    @Override
    public void returnCodeInfo(MainValidateResBean mainValidateResBean) {
        mMainValidateResBean = mainValidateResBean;
        subscribe = RxCountDown.countdown(180)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        countDownCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        countDownNext(integer);
                    }
                });
    }

    @Override
    public void returnForgetInfo(BaseResBean baseResBean) {
        if (baseResBean.isOk()) {
            ToastUtils.showToast(getString(R.string.forget_success));
            finish();
        }else{
            tvForgetInputErrHint.setText(baseResBean.getMsg());
            tvForgetInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            if (subscribe != null) {
                if (!subscribe.isUnsubscribed()) {
                    subscribe.unsubscribe();
                    countDownCompleted();
                }
            }
        }
    }

    @Override
    public void returnErrInfo(String errMsg) {
        tvForgetInputErrHint.setText(errMsg);
        tvForgetInputErrHint.setVisibility(View.VISIBLE);
        shakeAnimate();
    }
}
