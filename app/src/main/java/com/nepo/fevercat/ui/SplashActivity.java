package com.nepo.fevercat.ui;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.RxCountDown;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.ui.main.LoginActivity;

import java.util.Locale;


import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import rx.Subscriber;
import rx.Subscription;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui
 * 文件名:  SplashActivity
 * 作者 :   <sen>
 * 时间 :  下午4:11 2017/8/8.
 * 描述 :  引导页
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash_bg)
    ImageView ivSplashBg;
    @BindView(R.id.banner_guide_foreground)
    BGABanner bannerGuideForeground;
    @BindView(R.id.btn_guide_enter)
    Button btnGuideEnter;
    @BindView(R.id.btn_guide_skip)
    Button btnSkip;
    @BindView(R.id.rl_guide_bg)
    RelativeLayout rlGuideBg;

    private Subscription subscribe;

    @Override
    public int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

        // 获取当前语言
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        SharedPreferencesUtil.set(AppConstant.Language_set, language);

        String firstSplash = SharedPreferencesUtil.getString(AppConstant.SPLASH_FIRST, "");
        if (!TextUtils.isEmpty(firstSplash)) {
            ivSplashBg.setVisibility(View.GONE);
            rlGuideBg.setVisibility(View.VISIBLE);
            bannerGuideForeground.setData(R.drawable.icon_guide_first, R.drawable.icon_guide_second, R.drawable.icon_guide_third);

            /**
             * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
             * 如果进入按钮和跳过按钮有一个不存在的话就传 0
             * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
             * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
             */
            bannerGuideForeground.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.btn_guide_skip, new BGABanner.GuideDelegate() {
                @Override
                public void onClickEnterOrSkip() {
                    if (!AppConstant.IS_OFFLINEMODE) {
                        startAct(LoginActivity.newIntent(mContext));
                    } else {
                        SharedPreferencesUtil.set(AppConstant.SPLASH_FIRST, "SPLASH_FIRST");
                        startAct(ContainerActivity.newIntent(mContext));
                    }
                    finish();
                }
            });

            bannerGuideForeground.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 2) {
                        btnGuideEnter.setVisibility(View.VISIBLE);
                        btnSkip.setVisibility(View.VISIBLE);
                    } else {
                        btnGuideEnter.setVisibility(View.VISIBLE);
                        btnSkip.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });


        } else {
            // 其他
            ivSplashBg.setVisibility(View.VISIBLE);
            rlGuideBg.setVisibility(View.GONE);
            // 定时进入主页
            subscribe = RxCountDown.countdown(2)
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            startAct(ContainerActivity.newIntent(mContext));
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            startAct(ContainerActivity.newIntent(mContext));
                            finish();
                        }

                        @Override
                        public void onNext(Integer integer) {
                        }
                    });

        }


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
