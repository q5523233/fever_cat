package com.nepo.fevercat.ui.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.nepo.fevercat.ui.main.bean.MainRegisterReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;
import com.nepo.fevercat.ui.main.contract.RegisterContract;
import com.nepo.fevercat.ui.main.model.RegisterModel;
import com.nepo.fevercat.ui.main.presenter.RegisterPresenter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui
 * 文件名:  RegisterActivity
 * 作者 :   <sen>
 * 时间 :  上午11:34 2017/4/25.
 * 描述 :  注册
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter, RegisterModel> implements RegisterContract.View {


    @BindView(R.id.iv_register_bar_back)
    ImageView ivRegisterBarBack;
    @BindView(R.id.iv_register_edt_left_phone)
    ImageView ivRegisterEdtLeftPhone;
    @BindView(R.id.edt_register_phone)
    EditText edtRegisterPhone;
    @BindView(R.id.iv_register_edt_left_code)
    ImageView ivRegisterEdtLeftCode;
    @BindView(R.id.edt_register_code)
    EditText edtRegisterCode;
    @BindView(R.id.rl_register_get_code)
    RelativeLayout rlRegisterGetCode;
    @BindView(R.id.iv_register_edt_left_pwd)
    ImageView ivRegisterEdtLeftPwd;
    @BindView(R.id.edt_register_pwd)
    EditText edtRegisterPwd;
    @BindView(R.id.rl_register_confirm)
    RelativeLayout rlRegisterConfirm;
    @BindView(R.id.tv_register_input_err_hint)
    TextView tvRegisterInputErrHint;
    @BindView(R.id.tv_register_get_code)
    TextView tvRegisterGetCode;
    @BindView(R.id.chk_register_pwd_eye)
    CheckBox chkRegisterPwdEye;
    @BindView(R.id.edt_register_pwd_again)
    EditText edtRegisterPwdAgain;


    private AnimatorSet mAnimatorSet;
    private MainValidateResBean mMainValidateResBean;
    private Subscription subscribe = null;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_register;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        HideUtil.init(this);

    }


    /**
     * 返回
     */
    @OnClick(R.id.rl_register_bar_back)
    public void onBackClick() {
        finish();
    }


    /**
     * 获取验证码
     */
    @OnClick(R.id.rl_register_get_code)
    public void onGetCodeClick() {
        String trimPhoneStr = edtRegisterPhone.getText().toString().trim();

        if (TextUtils.isEmpty(trimPhoneStr)) {
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return;
        }

        if (!VerificationUtils.matcherPhoneNum(trimPhoneStr)) {
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
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
     * 注册提交
     */
    @OnClick(R.id.rl_register_confirm)
    public void onRegisterClick() {
        // 注册
        checkInputInfo();
    }

    /**
     * 密码切换
     */
    @OnCheckedChanged(R.id.chk_register_pwd_eye)
    public void onEyeClickChange(CompoundButton cb, boolean check) {
        edtRegisterPwd.setTransformationMethod(check
                ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());
    }


    /**
     * 检查输入
     *
     * @return
     */
    private boolean checkInputInfo() {

        String trimPhoneStr = edtRegisterPhone.getText().toString().trim();
        String trimCodeStr = edtRegisterCode.getText().toString().trim();
        String trimPwdStr = edtRegisterPwd.getText().toString().trim();
        String trimPwdAgainStr = edtRegisterPwdAgain.getText().toString().trim();

        if (TextUtils.isEmpty(trimPhoneStr)) {
            tvRegisterInputErrHint.setText(getString(R.string.register_edt_phone_hint));
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (!VerificationUtils.matcherPhoneNum(trimPhoneStr)) {
            tvRegisterInputErrHint.setText(getString(R.string.register_edt_phone_err_hint));
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (TextUtils.isEmpty(trimCodeStr)) {
            tvRegisterInputErrHint.setText(getString(R.string.register_edt_code_hint));
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (!TextUtils.equals(trimPwdStr,trimPwdAgainStr)) {
            tvRegisterInputErrHint.setText(getString(R.string.register_edt_again_pwd_err_hint));
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (mMainValidateResBean == null) {
            tvRegisterInputErrHint.setText(getString(R.string.register_edt_get_code_err_hint));
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (mMainValidateResBean != null) {
            if (!TextUtils.equals(mMainValidateResBean.getYzm(), trimCodeStr)) {
                tvRegisterInputErrHint.setText(getString(R.string.register_edt_get_code_err_hint));
                tvRegisterInputErrHint.setVisibility(View.VISIBLE);
                shakeAnimate();
                if (subscribe != null) {
                    if (!subscribe.isUnsubscribed()) {
                        subscribe.unsubscribe();
                        countDownCompleted();
                    }
                }
                return false;
            }
        }

        if (TextUtils.isEmpty(trimPwdStr)) {
            tvRegisterInputErrHint.setText(getString(R.string.register_edt_pwd_hint));
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }


        MainRegisterReqBean mainRegisterReqBean = new MainRegisterReqBean();
        if (mMainValidateResBean != null) {
            mainRegisterReqBean.setMsgSid(mMainValidateResBean.getMsgSid())
                    .setValidateCode(mMainValidateResBean.getYzm());
        }
        mainRegisterReqBean
                .setUserName(trimPhoneStr)
                .setUserPwd(trimPwdStr)
                .setTRANSID(ApiConstants.REGISTER);
        mPresenter.getRegisterInfoRequest(mainRegisterReqBean, true);

        tvRegisterInputErrHint.setVisibility(View.GONE);
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
                ObjectAnimator.ofFloat(tvRegisterInputErrHint, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0));
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvRegisterInputErrHint.setVisibility(View.GONE);
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
        tvRegisterGetCode.setText(result);
    }

    /**
     * 计时完成
     */
    private void countDownCompleted() {
        mMainValidateResBean = null;
        subscribe = null;
        tvRegisterGetCode.setText(getResources().getString(R.string.register_edt_get_code_hint));
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
    public void returnRegisterInfo(BaseResBean baseResBean) {
        if (baseResBean.isOk()) {
            ToastUtils.showToast(getString(R.string.register_success));
            finish();
        } else {
            tvRegisterInputErrHint.setText(baseResBean.getMsg());
            tvRegisterInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            edtRegisterPhone.setText("");
            edtRegisterCode.setText("");
            edtRegisterPwd.setText("");
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
        tvRegisterInputErrHint.setText(errMsg);
        tvRegisterInputErrHint.setVisibility(View.VISIBLE);
        shakeAnimate();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
