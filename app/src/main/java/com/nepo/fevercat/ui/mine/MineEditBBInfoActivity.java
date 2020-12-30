package com.nepo.fevercat.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.common.utils.HideUtil;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.common.widget.popup.CustomHelperUtils;
import com.nepo.fevercat.common.widget.popup.DateSelectPopupWindow;
import com.nepo.fevercat.common.widget.popup.GenderPopupWindow;
import com.nepo.fevercat.common.widget.popup.TakePhotoPopupWindows;
import com.nepo.fevercat.event.BBInfoEvent;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.mine.contract.MineBBInfoContract;
import com.nepo.fevercat.ui.mine.model.MineBBInfoModel;
import com.nepo.fevercat.ui.mine.presenter.MineBBInfoPresenter;

import net.bither.util.NativeUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.mine
 * 文件名:  MineEditBBInfoActivity
 * 作者 :   <sen>
 * 时间 :  上午10:28 2017/6/28.
 * 描述 : 编辑宝宝信息
 */

public class MineEditBBInfoActivity extends BaseActivity<MineBBInfoPresenter,MineBBInfoModel> implements
        MineBBInfoContract.View,
        TakePhoto.TakeResultListener,
        InvokeListener,
        TakePhotoPopupWindows.OnTakePhotoListen,
        DateSelectPopupWindow.OnSelectItemListen,
        GenderPopupWindow.OnTakeGenderListen {



    @BindView(R.id.rl_top_bar_back)
    RelativeLayout rlTopBarBack;
    @BindView(R.id.tv_top_bar_title)
    TextView tvTopBarTitle;
    @BindView(R.id.iv_mine_edit_bb_user_pic)
    CircularImage ivMineEditBbUserPic;
    @BindView(R.id.rl_mine_edit_bb_user_pic)
    RelativeLayout rlMineEditBbUserPic;
    @BindView(R.id.edt_mine_edit_user_nick_name)
    EditText edtMineEditUserNickName;
    @BindView(R.id.tv_mine_edit_bb_gender)
    TextView tvMineEditBbGender;
    @BindView(R.id.iv_mine_edit_bb_gender_arrow)
    ImageView ivMineEditBbGenderArrow;
    @BindView(R.id.rl_mine_edit_bb_gender)
    RelativeLayout rlMineEditBbGender;
    @BindView(R.id.tv_mine_edit_bb_birth)
    TextView tvMineEditBbBirth;
    @BindView(R.id.iv_mine_edit_bb_birth_arrow)
    ImageView ivMineEditBbBirthArrow;
    @BindView(R.id.rl_mine_edit_bb_birth)
    RelativeLayout rlMineEditBbBirth;
    @BindView(R.id.rl_mine_edit_bb_confirm)
    RelativeLayout rlMineEditBbConfirm;
    @BindView(R.id.rl_mine_edit_bb_del)
    RelativeLayout rlMineEditBbDel;
    @BindView(R.id.ll_mine_edit_bb_contain)
    LinearLayout llMineEditBbContain;


    private TakePhoto mTakePhoto;
    private InvokeParam invokeParam;
    private CustomHelperUtils mCustomHelperUtils;
    private TakePhotoPopupWindows mTakePhotoPopupWindows;
    private DateSelectPopupWindow mDateSelectPopupWindow;
    private GenderPopupWindow mGenderPopupWindow;

    private BabyInfosBean mBabyInfosBean;
    private MineUploadResBean mMineUploadResBean;

    public static final String MineEditBBInfo_Bundle_Tag = "MineEditBBInfo_Bundle_Tag";
    public static final String MineEditBBInfo_Bundle_BabyID_Tag = "MineEditBBInfo_Bundle_BabyID_Tag";

    private String headImgUrl;
    private String genderStr;
    private String birthStr;

    public static Intent newIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MineEditBBInfoActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_mine_edit_bb;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        HideUtil.init(this);
        tvTopBarTitle.setText(getString(R.string.mine_edit_user_title));
        List<String> mStringList = new ArrayList<>();
        mStringList.add(mContext.getString(R.string.mine_edit_bb_male));
        mStringList.add(mContext.getString(R.string.mine_edit_bb_female));
        edtMineEditUserNickName.setSelection(edtMineEditUserNickName.getText().length());
        mCustomHelperUtils = CustomHelperUtils.of();
        mTakePhotoPopupWindows = new TakePhotoPopupWindows(mContext);
        mDateSelectPopupWindow = new DateSelectPopupWindow(mContext);
        mDateSelectPopupWindow.setOnSelectItemListen(this);
        mGenderPopupWindow  = new GenderPopupWindow(mContext);
        mGenderPopupWindow.setOnTakeGenderListen(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String uuid = extras.getString(MineEditBBInfo_Bundle_Tag);
            String babyId =  extras.getString(MineEditBBInfo_Bundle_BabyID_Tag);
            if (!TextUtils.isEmpty(uuid)) {
                mBabyInfosBean = GreenDaoUtils.getInstance(mContext).getSingleBabyInfo(uuid);
            }else{
                mBabyInfosBean =  GreenDaoUtils.getInstance(mContext).getSingleBabyInfoByBabyID(babyId);
            }
        }

        initData();

    }


    /**
     * 设置数据
     */
    private void initData(){
        // 填充数据
        if (mBabyInfosBean != null) {
            ConstantUtils.loadLoginUserImg(mBabyInfosBean.getHeadImageUrl(),ivMineEditBbUserPic);
            edtMineEditUserNickName.setText(mBabyInfosBean.getNickname());
            String language = SharedPreferencesUtil.getString(AppConstant.Language_set,"zh");
            if (!TextUtils.equals(language,"zh")) {
                if (TextUtils.equals(mBabyInfosBean.getSex(),"男")) {
                    tvMineEditBbGender.setText(getString(R.string.mine_edit_bb_male));
                }else if (TextUtils.equals(mBabyInfosBean.getSex(),"女")){
                    tvMineEditBbGender.setText(getString(R.string.mine_edit_bb_female));
                }else{
                    tvMineEditBbGender.setText(mBabyInfosBean.getSex());
                }
            }else{
                tvMineEditBbGender.setText(mBabyInfosBean.getSex());
            }

            tvMineEditBbBirth.setText(mBabyInfosBean.getBirthday());
            rlMineEditBbDel.setVisibility(View.VISIBLE);
            headImgUrl = mBabyInfosBean.getHeadImageUrl();
            genderStr = mBabyInfosBean.getSex();
            birthStr = mBabyInfosBean.getBirthday();

        }else{
            rlMineEditBbDel.setVisibility(View.GONE);
        }

    }


    /**
     * 检查输入信息
     */
    private boolean checkPutInfo(){

        String trimNickNameStr = edtMineEditUserNickName.getText().toString().trim();

//        if (TextUtils.isEmpty(headImgUrl)) {
//            ToastUtils.showToast(getString(R.string.input_err));
//            return false;
//        }

        if (TextUtils.isEmpty(trimNickNameStr)) {
            ToastUtils.showToast(getString(R.string.mine_add_nick_err));
            return false;
        }

        if (trimNickNameStr.length()>10) {
            ToastUtils.showToast(getString(R.string.mine_add_nick_length_err));
            return false;
        }

        if (TextUtils.isEmpty(genderStr)) {
            ToastUtils.showToast(getString(R.string.mine_add_gender_err));
            return false;
        }

        if (TextUtils.isEmpty(birthStr)) {
            ToastUtils.showToast(getString(R.string.mine_add_birth_err));
            return false;
        }


        // 提交信息
        MineBBReqBean mineBBReqBean = new MineBBReqBean();
        // 默认设置
        mineBBReqBean.setHeadImageId("None").setAccountId("None").setBabyId("None");
        if (mBabyInfosBean != null) {
            // 修改
            mineBBReqBean.setOperateID(ApiConstants.MODIFY_BABY_UPDATE_2)
                    .setId(mBabyInfosBean.getId())
                    .setLocalUUID(mBabyInfosBean.getLocalId())
                    .setBabyId(mBabyInfosBean.getBabyId());
        }else{
            if (TextUtils.isEmpty(headImgUrl)) {
                ToastUtils.showToast(getString(R.string.select_user_pic_err));
                return false;
            }
            // 添加
            mineBBReqBean.setOperateID(ApiConstants.MODIFY_BABY_ADD_1);
        }


        // 图片上传ID
        if (mMineUploadResBean != null) {
            mineBBReqBean.setHeadImageId(mMineUploadResBean.getFileId());
            headImgUrl = mMineUploadResBean.getFileUrl();
            mMineUploadResBean = null;
        }
        // 是否登录
        if (AppConstant.isLogin()) {
            mineBBReqBean
                    .setAccountId(AppConstant.getUserInfo().getUserId());
        }
        mineBBReqBean
                .setNickname(trimNickNameStr)
                .setSex(genderStr)
                .setBirthday(birthStr)
                .setLocalImgUrl(headImgUrl)
                .setTRANSID(ApiConstants.MODIFY_BABY);
        // 更改用户信息
        mPresenter.putMineBBInfoRequest(mineBBReqBean,!AppConstant.IS_OFFLINEMODE);



        return true;
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
    @OnClick(R.id.rl_mine_edit_bb_user_pic)
    public void onSelectPicClick() {
        mTakePhotoPopupWindows.showAtLocation(llMineEditBbContain, Gravity.BOTTOM, 0, 0);
        mTakePhotoPopupWindows.setOnTakePhotoListen(this);
    }

    /**
     * 性别
     */
    @OnClick(R.id.rl_mine_edit_bb_gender)
    public void onGenderClick() {

        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(MineEditBBInfoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        mGenderPopupWindow.showAtLocation(llMineEditBbContain, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 出生日期
     */
    @OnClick(R.id.rl_mine_edit_bb_birth)
    public void onBirthClick() {
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(MineEditBBInfoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        mDateSelectPopupWindow.showAtLocation(llMineEditBbContain, Gravity.BOTTOM,0,0);
    }

    /**
     * 保存
     */
    @OnClick(R.id.rl_mine_edit_bb_confirm)
    public void onConfirmClick() {
        if (AppConstant.IsDevicesConn) {
            ToastUtils.showToast(getString(R.string.conn_edit_bb_info_err));
            return;
        }
            // 提交信息
            checkPutInfo();
    }

    /**
     * 删除
     */
    @OnClick(R.id.rl_mine_edit_bb_del)
    public void onDelClick() {
        if (AppConstant.IsDevicesConn) {
            ToastUtils.showToast(getString(R.string.conn_edit_bb_info_err));
            return;
        }
        if (mBabyInfosBean != null) {
            // 提交信息
            MineBBReqBean mineBBReqBean = new MineBBReqBean();
            if (AppConstant.isLogin()) {
                mineBBReqBean
                        .setAccountId(AppConstant.getUserInfo().getUserId());
            }
            mineBBReqBean
                    .setOperateID(ApiConstants.MODIFY_BABY_DEL_3)
                    .setId(mBabyInfosBean.getId())
                    .setBabyId(mBabyInfosBean.getBabyId())
                    .setLocalUUID(mBabyInfosBean.getLocalId())
                    .setTRANSID(ApiConstants.MODIFY_BABY);
            mPresenter.putMineBBInfoRequest(mineBBReqBean,!AppConstant.IS_OFFLINEMODE);
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
                        mPresenter.putFileInfoRequest(file, true);
                    }
                });


    }


    @Override
    public void takeSuccess(TResult result) {
        if (!TextUtils.isEmpty(result.getImage().getPath())) {
            headImgUrl = result.getImage().getPath();
            ConstantUtils.loadLoginUserImg(result.getImage().getPath(),ivMineEditBbUserPic);
            // 上傳圖片到服務器
            compressFile(result.getImage().getPath());
        }
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
    public void selectItem(String content) {
        tvMineEditBbBirth.setText(content);
        birthStr = content;
    }

//    @Override
//    public void selectStrItem(String content) {
//        tvMineEditBbGender.setText(content);
//        genderStr = content;
//    }

    @Override
    public void returnBBInfo(MineBBResBean mineBBResBean) {
        if (mineBBResBean.isOk()) {
            EventBus.getDefault().post(new BBInfoEvent());
            finish();
        }
    }

    @Override
    public void returnUploadInfo(MineUploadResBean mineUploadResBean) {
        mMineUploadResBean = mineUploadResBean;
    }

    @Override
    public void returnErrInfo(String errMsg) {

    }

    @Override
    public void selectGenderStr(String mGenderStr) {
        String language = SharedPreferencesUtil.getString(AppConstant.Language_set,"zh");
        if (!TextUtils.equals(language,"zh")) {
            if (TextUtils.equals(mGenderStr,"男")) {
                tvMineEditBbGender.setText(getString(R.string.mine_edit_bb_male));
            }else if(TextUtils.equals(mGenderStr,"女")){
                tvMineEditBbGender.setText(getString(R.string.mine_edit_bb_female));
            }
            genderStr = mGenderStr;
        }else{
            tvMineEditBbGender.setText(mGenderStr);
            genderStr = mGenderStr;
        }
    }
}
