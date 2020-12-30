package com.nepo.fevercat.ui.follow;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.DialogUtils;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.utils.RxCountDown;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.utils.VerificationUtils;
import com.nepo.fevercat.ui.follow.bean.FollowAddReqBean;
import com.nepo.fevercat.ui.follow.contract.FollowAddContract;
import com.nepo.fevercat.ui.follow.model.FollowModel;
import com.nepo.fevercat.ui.follow.presenter.FollowPresenter;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;


import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.follow
 * 文件名:  FollowAddActivity
 * 作者 :   <sen>
 * 时间 :  下午12:02 2017/4/27.
 * 描述 :  添加关注
 */

public class FollowAddActivity extends BaseActivity<FollowPresenter, FollowModel> implements FollowAddContract.View {


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
    private MainValidateResBean mMainValidateResBean;
    private Subscription subscribe = null;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FollowAddActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_follow_add;
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
    @OnClick(R.id.rl_follow_add_bar_back)
    public void onBackClick() {
        finish();
    }


    /**
     * 提交
     */
    @OnClick(R.id.rl_follow_add_confirm)
    public void onConfirmClick() {
        checkInputInfo();
    }

    /**
     * 获取验证码
     */
    @OnClick({R.id.rl_follow_add_get_code,R.id.tv_follow_add_get_code})
    public void onGetCodeClick() {
        String trimPhoneStr = edtFollowAddPhone.getText().toString().trim();

        if (TextUtils.isEmpty(trimPhoneStr)) {
            tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return;
        }

        if (!VerificationUtils.matcherPhoneNum(trimPhoneStr)) {
            tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
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
     * 检查输入
     *
     * @return
     */
    private boolean checkInputInfo() {

        String trimNameStr = edtFollowAddNickName.getText().toString().trim();
        String trimPhoneStr = edtFollowAddPhone.getText().toString().trim();
        String trimCodeStr = edtFollowAddCode.getText().toString().trim();

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

        if (TextUtils.isEmpty(trimCodeStr)&&!AppConstant.IS_OFFLINEMODE) {
            tvFollowAddInputErrHint.setText(getString(R.string.register_edt_code_hint));
            tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (mMainValidateResBean==null) {
            tvFollowAddInputErrHint.setText(getString(R.string.register_edt_get_code_err_hint));
            tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
            shakeAnimate();
            return false;
        }

        if (mMainValidateResBean != null) {
            if (!TextUtils.equals(mMainValidateResBean.getYzm(), trimCodeStr)) {
                tvFollowAddInputErrHint.setText(getString(R.string.register_edt_get_code_err_hint));
                tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
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


        FollowAddReqBean friendReqBean = new FollowAddReqBean();
        if (mMainValidateResBean != null) {
            friendReqBean.setMsgSid(mMainValidateResBean.getMsgSid())
                    .setValidateCode(mMainValidateResBean.getYzm());
        }
        friendReqBean
                .setUserName(trimPhoneStr)
                .setNickName(trimNameStr)
                .setAccountId(AppConstant.getUserInfo().getUserId())
                .setTRANSID(ApiConstants.BIND_FRIENDS_ADD);

        mPresenter.putFriendInfoRequest(friendReqBean,true);

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

    /**
     * 正在计时
     */
    private void countDownNext(Integer integer) {
        String testStr = getResources().getString(R.string.register_resend_hint);
        String result = String.format(testStr, integer);
        tvFollowAddGetCode.setText(result);
    }

    /**
     * 计时完成
     */
    private void countDownCompleted() {
        mMainValidateResBean = null;
        tvFollowAddGetCode.setText(getResources().getString(R.string.register_edt_get_code_hint));
        subscribe = null;
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
    public void returnPutInfo(BaseResBean baseResBean) {
        if (baseResBean.isOk()) {
            ToastUtils.showToast(baseResBean.getMsg());
            Log.d("tag","312"+baseResBean.getMsg());
            finish();
        }else{
            edtFollowAddCode.setText("");
            checkErrMsg(baseResBean.getMsg());
            tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
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
        checkErrMsg(errMsg);
        tvFollowAddInputErrHint.setVisibility(View.VISIBLE);
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

    private void checkErrMsg(String errMsg){
        String language = SharedPreferencesUtil.getString(AppConstant.Language_set,"zh");
        if (!TextUtils.equals(language,"zh")) {
            if (errMsg.contains("对不起，不能自己绑定自己")) {
                tvFollowAddInputErrHint.setText("I'm sorry I can't bind myself");
            }else if (errMsg.contains("对不起，亲友信息不存在")) {
                tvFollowAddInputErrHint.setText("I'm sorry, the information doesn't exist");
            }else{
                tvFollowAddInputErrHint.setText(errMsg);
            }
        }else{
            if(errMsg.contains("对不起，亲友信息不存在")){
                tvFollowAddInputErrHint.setText(getResources().getString(R.string.follow_add_phone_err_noregister));
                sendWechatAlert();
            }else{
                tvFollowAddInputErrHint.setText(errMsg);
            }

        }
    }

    private void sendWechatAlert(){
        DialogUtils.getInstance(this)
                .DoubleOptionsDialog(getResources().getString(R.string.follow_add_phone_err_noregister)+getResources().getString(R.string.follow_send_wechat),
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //确定
                                sendToWechat();
                            }
                        }, new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //取消
                            }
                        });
    }

    private void sendToWechat() {
        String WxPackageStr = "com.tencent.mm";
        String WxTimeLinePackageStr = "com.tencent.mm.ui.tools.ShareImgUI";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setComponent(new ComponentName(WxPackageStr,WxTimeLinePackageStr));
        intent.putExtra(Intent.EXTRA_TEXT,getResources().getString(R.string.follow_send));
        intent.setType("text/plain");
        startActivity(intent);
    }


}
