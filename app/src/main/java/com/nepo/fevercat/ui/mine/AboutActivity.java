package com.nepo.fevercat.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.TextView;

import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine.fragment
 * 文件名:  AboutActivity
 * 作者 :   <sen>
 * 时间 :  上午10:23 2017/4/28.
 * 描述 :  关于我们
 */

public class AboutActivity extends BaseActivity {


    @BindView(R.id.web_mine_about)
    WebView webMineAbout;
    @BindView(R.id.tv_url_title)
    TextView tvTitle;
    private static String URL = "URL";
    private static String TITLE = "TITLE";

    public static Intent newIntent(Context context, String url,String title) {
        Intent intent = new Intent(context, AboutActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_about;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        webMineAbout.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        if (intent.hasExtra(URL)) {
            webMineAbout.loadUrl(intent.getStringExtra(URL));
        }
        if (intent.hasExtra(TITLE)) {
            tvTitle.setText(intent.getStringExtra(TITLE));
        }

    }

    @OnClick(R.id.rl_mine_about_bar_back)
    public void onBackClick() {
        finish();
    }


}
