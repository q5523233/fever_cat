package com.nepo.fevercat.ui.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.VerificationUtils;
import com.nepo.fevercat.common.utils.VersionUtil;
import com.nepo.fevercat.event.LoginOutMsgEvent;
import com.nepo.fevercat.ui.ContainerActivity;
import com.nepo.fevercat.ui.main.bean.MainLoginReqBean;
import com.nepo.fevercat.ui.main.bean.MainLoginResBean;
import com.nepo.fevercat.ui.main.contract.LoginContract;
import com.nepo.fevercat.ui.main.model.LoginModel;
import com.nepo.fevercat.ui.main.presenter.LoginPresenter;

import org.greenrobot.eventbus.EventBus;


import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui
 * 文件名:  LoginActivity
 * 作者 :   <sen>
 * 时间 :  上午9:41 2017/4/25.
 * 描述 :  登錄
 */

public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {


    @BindView(R.id.edt_login_phone)
    EditText edtLoginPhone;
    @BindView(R.id.edt_login_pwd)
    EditText edtLoginPwd;
    @BindView(R.id.rl_login_edt_pwd)
    RelativeLayout rlLoginEdtPwd;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.tv_login_input_err_hint)
    TextView tvLoginInputErrHint;
    @BindView(R.id.rl_login_confirm)
    RelativeLayout rlLoginConfirm;
    @BindView(R.id.tv_login_no_register_hint)
    TextView tvLoginNoRegisterHint;
    @BindView(R.id.tv_login_to_register)
    TextView tvLoginToRegister;
    @BindView(R.id.rl_login_to_register)
    RelativeLayout rlLoginToRegister;
    @BindView(R.id.ll_login_other_qq)
    LinearLayout llLoginOtherQq;
    @BindView(R.id.ll_login_other_wx)
    LinearLayout llLoginOtherWx;
    @BindView(R.id.ll_login_other_wb)
    LinearLayout llLoginOtherWb;
    @BindView(R.id.tv_login_skip)
    TextView tvLoginSkip;

    /**
     * 数据
     */

    private static final String EXTRA_LOGIN = "EXTRA_LOGIN";


    private AnimatorSet mAnimatorSet;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        HideUtil.init(this);


        String firstSplash = SharedPreferencesUtil.getString(AppConstant.SPLASH_FIRST, "");
        if (TextUtils.isEmpty(firstSplash)) {
            tvLoginSkip.setVisibility(View.VISIBLE);
        }else{
            tvLoginSkip.setVisibility(View.GONE);
        }

    }


    /**
     * 返回
     */
    @OnClick(R.id.rl_login_bar_back)
    public void onBackClick() {
        finish();
    }

    /**
     * 跳过
     */
    @OnClick(R.id.tv_login_skip)
    public void onSkipClick() {
        // 第一次进入APP
        SharedPreferencesUtil.set(AppConstant.SPLASH_FIRST, "SPLASH_FIRST");
        startAct(ContainerActivity.newIntent(mContext));

        // 通知界面用户登录了
        EventBus.getDefault().post(new LoginOutMsgEvent());
        finish();
    }

    /**
     * 密码切换
     */
    @OnCheckedChanged(R.id.chk_login_pwd_eye)
    public void onEyeClickChange(CompoundButton cb, boolean check) {
        edtLoginPwd.setTransformationMethod(check
                ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());
    }

    /**
     * 登录
     */
    @OnClick(R.id.rl_login_confirm)
    public void onLoginConfirmClick() {
        checkInputInfo();
    }

    /**
     * 去注册
     */
    @OnClick(R.id.rl_login_to_register)
    public void onToRegisterClick() {
        startAct(RegisterActivity.newIntent(mContext));
    }

    /**
     * 忘记密码
     */
    @OnClick(R.id.tv_login_forget_pwd)
    public void onForgetPwdClick() {
        startAct(ForgetPwdActivity.newIntent(mContext));
    }

    /**
     * qq 登录
     */
    @OnClick(R.id.ll_login_other_qq)
    public void onQQClick() {

    }

    /**
     * Wx 登录
     */
    @OnClick(R.id.ll_login_other_wx)
    public void onWXClick() {

    }

    /**
     * Wb 登录
     */
    @OnClick(R.id.ll_login_other_wb)
    public void onWBClick() {

    }

    /**
     * 验证信息
     *
     * @return
     */
    private boolean checkInputInfo() {

        String trimPhoneStr = edtLoginPhone.getText().toString().trim();
        String trimPwdStr = edtLoginPwd.getText().toString().trim();

        if (TextUtils.isEmpty(trimPhoneStr)) {
            tvLoginInputErrHint.setText(getString(R.string.register_edt_phone_hint));
            tvLoginInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (!VerificationUtils.matcherPhoneNum(trimPhoneStr)) {
            tvLoginInputErrHint.setText(getString(R.string.register_edt_phone_err_hint));
            tvLoginInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (TextUtils.isEmpty(trimPwdStr)) {
            tvLoginInputErrHint.setText(getString(R.string.register_edt_pwd_hint));
            tvLoginInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        MainLoginReqBean mainLoginReqBean = new MainLoginReqBean();
        mainLoginReqBean
                .setUserName(trimPhoneStr)
                .setUserPwd(trimPwdStr)
                .setSysType("1")
                .setSysVersion(VersionUtil.getVersionCode(mContext))
                .setDeviceToken("")
                .setUuid("")
                .setTRANSID(ApiConstants.LOGIN);
        mPresenter.getLoginInfoRequest(mainLoginReqBean, true);


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
                ObjectAnimator.ofFloat(tvLoginInputErrHint, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0));
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(null!=tvLoginInputErrHint)
                tvLoginInputErrHint.setVisibility(View.GONE);
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
    public void returnLoginInfo(MainLoginResBean loginResBean) {
        if (loginResBean.isOk()) {
            AppConstant.saveUserInfo(loginResBean);
            AppConstant.sMainLoginResBean = loginResBean;
            String splashFlag = SharedPreferencesUtil.getString(AppConstant.SPLASH_FIRST, "");
            if (TextUtils.isEmpty(splashFlag)) {
                // 第一次进入APP
                SharedPreferencesUtil.set(AppConstant.SPLASH_FIRST, "SPLASH_FIRST");
                startAct(ContainerActivity.newIntent(mContext));
            }
            // 通知界面用户登录了
            EventBus.getDefault().post(new LoginOutMsgEvent());
            finish();
        } else {
            checkErrMsg(loginResBean.getMsg());
            tvLoginInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
        }
    }

    @Override
    public void returnErrInfo(String errMsg) {
        tvLoginInputErrHint.setText(errMsg);
        tvLoginInputErrHint.setVisibility(View.VISIBLE);
        shakeAnimate();
    }


    private void checkErrMsg(String errMsg){
        String language = SharedPreferencesUtil.getString(AppConstant.Language_set,"zh");
        if (!TextUtils.equals(language,"zh")) {
            if (errMsg.contains("密码输入错误")) {
                tvLoginInputErrHint.setText(getString(R.string.login_input_err_pwd));
            }else if(errMsg.contains("此用户未注册")){
                tvLoginInputErrHint.setText(getString(R.string.login_no_register));
            }else{
                tvLoginInputErrHint.setText(errMsg);
            }
        }else{
            tvLoginInputErrHint.setText(errMsg);
        }

    }


}
