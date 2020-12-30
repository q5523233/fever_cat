package com.nepo.fevercat.ui.mine;

import android.content.Context;
import android.content.Intent;

import com.nepo.fevercat.R;
import com.nepo.fevercat.base.BaseActivity;

import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine
 * 文件名:  HelpActivity
 * 作者 :   <sen>
 * 时间 :  上午10:21 2017/4/28.
 * 描述 :  帮助
 */

public class HelpActivity extends BaseActivity {



    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_help;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
    }

    @OnClick(R.id.rl_mine_help_bar_back)
    public void onBackClick(){
        finish();
    }
}
