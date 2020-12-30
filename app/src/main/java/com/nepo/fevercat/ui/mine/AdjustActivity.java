package com.nepo.fevercat.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;

import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.ToastUtils;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine
 * 文件名:  AdjustAtivity
 * 作者 :   <sen>
 * 时间 :  下午3:39 2017/10/31.
 * 描述 : 校准
 */

public class AdjustActivity extends BaseActivity {


    @BindView(R.id.edt_mine_adjust_pwd)
    AppCompatEditText edtMineAdjustPwd;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AdjustActivity.class);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_adjust;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.iv_mine_help_bar_back)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.rl_mine_adjust_save)
    public void onSaveClick() {
        String adjustPwd = edtMineAdjustPwd.getText().toString().trim();
        SharedPreferencesUtil.set(AppConstant.Adjust_Pwd, adjustPwd);
        ToastUtils.showToast(getString(R.string.mine_adjust_pwd_success));
        finish();
    }

}
