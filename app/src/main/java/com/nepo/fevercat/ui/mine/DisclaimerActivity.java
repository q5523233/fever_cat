package com.nepo.fevercat.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebView;

import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.LogUtils;

import java.util.Locale;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine
 * 文件名:  DisclaimerActivity
 * 作者 :   <sen>
 * 时间 :  上午10:23 2017/4/28.
 * 描述 :  免责声明
 */

public class DisclaimerActivity extends BaseActivity {


    @BindView(R.id.web_mine_disclaimer)
    WebView webMineDisclaimer;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DisclaimerActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_disclaimer;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        webMineDisclaimer.getSettings().setJavaScriptEnabled(true);
        if (TextUtils.equals(language,"zh")) {
            // 中文
            webMineDisclaimer.loadUrl("file:///android_asset/disclaimer.html");
        }else{
            // 非中文
            webMineDisclaimer.loadUrl("file:///android_asset/disclaimer_en.html");
        }
        // zh
        LogUtils.logd("-- language:"+language);




    }


    @OnClick(R.id.rl_mine_disclaimer_bar_back)
    public void onBackClick() {
        finish();
    }


}
