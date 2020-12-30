package com.nepo.fevercat.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.common.widget.popup.CustomHelperUtils;
import com.nepo.fevercat.common.widget.popup.TakePhotoPopupWindows;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.mine.bean.MineEditReqBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.mine.contract.MineEditContract;
import com.nepo.fevercat.ui.mine.model.MineEditInfoModel;
import com.nepo.fevercat.ui.mine.presenter.MineEditInfoPresenter;

import net.bither.util.NativeUtil;

import java.io.File;


import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine
 * 文件名:  MineEditActivity
 * 作者 :   <sen>
 * 时间 :  下午4:44 2017/6/24.
 * 描述 :
 */

public class MineEditActivity extends BaseActivity<MineEditInfoPresenter,MineEditInfoModel> implements MineEditContract.View,TakePhoto.TakeResultListener, InvokeListener, TakePhotoPopupWindows.OnTakePhotoListen {


    @BindView(R.id.rl_top_bar_back)
    RelativeLayout rlTopBarBack;
    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.iv_mine_edit_user_pic)
    CircularImage ivMineEditUserPic;
    @BindView(R.id.rl_mine_edit_user_pic)
    RelativeLayout rlMineEditUserPic;
    @BindView(R.id.edt_mine_edit_user_nick_name)
    EditText edtMineEditUserNickName;
    @BindView(R.id.rl_mine_edit_confirm)
    RelativeLayout rlMineEditConfirm;
    @BindView(R.id.ll_mine_edit_contain)
    LinearLayout llMineEditContain;


    private TakePhoto mTakePhoto;
    private InvokeParam invokeParam;
    private CustomHelperUtils mCustomHelperUtils;
    private TakePhotoPopupWindows mTakePhotoPopupWindows;

    private MineUploadResBean mMineUploadResBean;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MineEditActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_mine_edit;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {

        tvTopBarTitle.setText(getString(R.string.mine_edit_user_title));
        mCustomHelperUtils = CustomHelperUtils.of();
        mTakePhotoPopupWindows = new TakePhotoPopupWindows(mContext);

        if (!TextUtils.isEmpty(AppConstant.getUserInfo().getNickName())) {
            edtMineEditUserNickName.setText(AppConstant.getUserInfo().getNickName());
        }

        // 加载头像
        ConstantUtils.loadLoginUserImg(AppConstant.getUserInfo().getHeadImageUrl(),ivMineEditUserPic);

        edtMineEditUserNickName.setSelection(edtMineEditUserNickName.getText().length());


    }


    /**
     * 返回
     */
    @OnClick(R.id.rl_top_bar_back)
    public void onBackClick() {
        finish();
    }

    /**
     * 选择图片
     */
    @OnClick(R.id.rl_mine_edit_user_pic)
    public void onSelectPicClick() {
        mTakePhotoPopupWindows.showAtLocation(llMineEditContain, Gravity.BOTTOM, 0, 0);
        mTakePhotoPopupWindows.setOnTakePhotoListen(this);
    }

    /**
     * 提交信息
     */
    @OnClick(R.id.rl_mine_edit_confirm)
    public void onConfirmClick() {
        String nickNameStr = edtMineEditUserNickName.getText().toString().trim();
        if (!TextUtils.isEmpty(nickNameStr)) {

            AppConstant.sMainLoginResBean.setNickName(nickNameStr);
            AppConstant.saveUserInfo(AppConstant.sMainLoginResBean);

            // 提交信息
            MineEditReqBean mineEditReqBean = new MineEditReqBean();
            if (mMineUploadResBean!=null) {
                mineEditReqBean.setHeadImageId(mMineUploadResBean.getFileId());
            }
            mineEditReqBean
                    .setAccountId(AppConstant.getUserInfo().getUserId())
                    .setOperateID(ApiConstants.MODIFY_USER_INFO_1)
                    .setNickName(nickNameStr)
                    .setTRANSID(ApiConstants.MODIFY_USER_INFO);
            mPresenter.putMineEditInfoRequest(mineEditReqBean,true);


        }
    }


    /**
     * 获取TakePhoto实例
     */
    private TakePhoto getTakePhoto() {
        if (mTakePhoto == null) {
            mTakePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return mTakePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        if (!TextUtils.isEmpty(result.getImage().getPath())) {
            ConstantUtils.loadLoginUserImg(result.getImage().getPath(),ivMineEditUserPic);
            // 上傳圖片到服務器
            compressFile(result.getImage().getPath());
        }
    }

    /**
     * 上传前压缩图片
     * @param path
     */
    private void compressFile(final String path){
        final File file = new File(path);
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                LogUtils.logd("--before:"+file.length()+"");
                NativeUtil.compressBitmap(path,file.getPath());
                subscriber.onNext(null);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        LogUtils.logd("--after:"+file.length()+"");
                        AppConstant.sMainLoginResBean.setHeadImageUrl(file.getAbsolutePath());
                        AppConstant.saveUserInfo(AppConstant.sMainLoginResBean);
                        mPresenter.putFileInfoRequest(file, true);
                    }
                });


    }


    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void selectPhotoType(int takeType) {
        mCustomHelperUtils.takePhoto(takeType, getTakePhoto());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void returnUploadInfo(MineUploadResBean mineUploadResBean) {
        mMineUploadResBean = mineUploadResBean;
        if (!TextUtils.isEmpty(mineUploadResBean.getFileUrl())) {
            AppConstant.sMainLoginResBean.setHeadImageUrl(mineUploadResBean.getFileUrl());
            AppConstant.saveUserInfo(AppConstant.sMainLoginResBean);

        }
    }

    @Override
    public void returnPutInfo(BaseResBean baseResBean) {
        if (baseResBean.isOk()) {
            finish();
            setResult(0);
        }else{
            ToastUtils.showToast(baseResBean.getMsg());
        }
    }

    @Override
    public void returnErrInfo(String errMsg) {

    }
}
