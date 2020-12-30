package com.nepo.fevercat.ui.real.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.net.Api;
import com.nepo.fevercat.base.net.ApiConstants;
import com.nepo.fevercat.base.rx.RxSchedulers;
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.common.utils.NetWorkUtils;
import com.nepo.fevercat.common.utils.Utils;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.real.bean.BabyTempAddReqBean;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.real.service
 * 文件名:  UploadBabyTempService
 * 作者 :   <sen>
 * 时间 :  下午4:57 2017/7/19.
 * 描述 :  同步宝宝、温度信息
 */

public class UploadBabyTempService extends Service {


    Context mContext;
    Subscription mUploadSubscription;
    // 上传时间间隔 分钟
    private long TimeInterval = 5;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        mContext = UploadBabyTempService.this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mUploadSubscription != null) {
            if (!mUploadSubscription.isUnsubscribed()) {
                mUploadSubscription.unsubscribe();
                mUploadSubscription = null;
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 上传数据
        intervalUploadTempBB();

        return super.onStartCommand(intent, flags, startId);

    }

    /**
     * 上传宝宝温度
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadBybyTemp(BabyTempAddReqBean babyTempAddReqBean) {
        if (babyTempAddReqBean != null) {
            if (NetWorkUtils.isNetConnected(UploadBabyTempService.this)) {
                // 开始上传
                Api.getDefault().putUploadBBTempInfo(babyTempAddReqBean)
                        .compose(RxSchedulers.<BaseResBean>io_main()).subscribe(new Action1<BaseResBean>() {
                    @Override
                    public void call(BaseResBean baseResBean) {

                    }
                });
            }
        }
    }


    /**
     * 5分钟定时轮询
     * 上传宝宝信息 温度
     */
    private void intervalUploadTempBB() {
        mUploadSubscription = Observable.interval(TimeInterval, TimeUnit.MINUTES)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                        // 登录并联网
                        if ((AppConstant.isLogin()
                                && NetWorkUtils.isNetConnected(UploadBabyTempService.this)
                                )||AppConstant.IS_OFFLINEMODE) {
                            // 同步数据
                            SyncBBInfo();
                            SyncBBTempInfo();
                            SyncBBImgInfo();

                        }
                    }
                });
    }

    /**
     * 同步宝宝信息
     */
    private void SyncBBInfo() {
        List<BabyInfosBean> babyListInfo = GreenDaoUtils.getInstance(mContext).getBabyListAllInfo();
        for (final BabyInfosBean babyInfosBean : babyListInfo) {
            // 非同步过
            if (!TextUtils.equals(babyInfosBean.getControlStatus(), AppConstant.BB_CONTROL_STATUS_NORMAL)) {
                if (TextUtils.isEmpty(babyInfosBean.getBabyId())||TextUtils.equals(babyInfosBean.getBabyId(),"None")) {
                    // 添加操作
                    MineBBReqBean mineBBReqBean = new MineBBReqBean();
                    mineBBReqBean
                            .setAccountId(AppConstant.getUserInfo().getUserId())
                            .setOperateID(ApiConstants.MODIFY_BABY_ADD_1)
                            .setNickname(babyInfosBean.getNickname())
                            .setSex(babyInfosBean.getSex())
                            .setBirthday(babyInfosBean.getBirthday())
                            .setHeadImageId(babyInfosBean.getHeadImageId())
                            .setTRANSID(ApiConstants.MODIFY_BABY);

                    Api.getDefault().putMineBBInfo(mineBBReqBean)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Subscriber<MineBBResBean>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(MineBBResBean mineBBResBean) {
                                    if (mineBBResBean.isOk()) {
                                        //更新BabyID
                                        GreenDaoUtils.getInstance(mContext).updateBabyIDProperty(babyInfosBean, mineBBResBean.getBabyId());
                                        //根据LocalID更新温度表BabyID
                                        GreenDaoUtils.getInstance(mContext).updateBabyIDToTempInfo(babyInfosBean, mineBBResBean);
                                    }
                                }
                            });
                } else {
                    // 根据状态判断 删、改 操作
                    if (TextUtils.equals(babyInfosBean.getControlStatus(), AppConstant.BB_CONTROL_STATUS_UPDATE)) {
                        // 改
                        MineBBReqBean mineBBReqBean = new MineBBReqBean();
                        mineBBReqBean
                                .setAccountId(AppConstant.getUserInfo().getUserId())
                                .setOperateID(ApiConstants.MODIFY_BABY_UPDATE_2)
                                .setBabyId(babyInfosBean.getBabyId())
                                .setNickname(babyInfosBean.getNickname())
                                .setSex(babyInfosBean.getSex())
                                .setBirthday(babyInfosBean.getBirthday())
                                .setHeadImageId(babyInfosBean.getHeadImageId())
                                .setTRANSID(ApiConstants.MODIFY_BABY);
                        Api.getDefault().putMineBBInfo(mineBBReqBean)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Subscriber<MineBBResBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(MineBBResBean mineBBResBean) {
                                        if (mineBBResBean.isOk()) {
                                            GreenDaoUtils.getInstance(mContext).updateBabyControlStatusProperty(babyInfosBean);
                                        }
                                    }
                                });
                    } else if (TextUtils.equals(babyInfosBean.getControlStatus(), AppConstant.BB_CONTROL_STATUS_DEL)) {
                        // 删
                        MineBBReqBean mineBBReqBean = new MineBBReqBean();
                        mineBBReqBean
                                .setAccountId(AppConstant.getUserInfo().getUserId())
                                .setOperateID(ApiConstants.MODIFY_BABY_DEL_3)
                                .setBabyId(babyInfosBean.getBabyId())
                                .setTRANSID(ApiConstants.MODIFY_BABY);
                        Api.getDefault().putMineBBInfo(mineBBReqBean)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Subscriber<MineBBResBean>() {
                                    @Override
                                    public void onCompleted() {


                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(MineBBResBean mineBBResBean) {
                                        if (mineBBResBean.isOk()) {
                                            // 删除宝宝信息
                                            GreenDaoUtils.getInstance(mContext).delBybyInfo(babyInfosBean);
                                            // 删除宝宝对应的温度信息
                                            GreenDaoUtils.getInstance(mContext).delTempInfoByBabyID(babyInfosBean);

                                        }
                                    }
                                });
                    }
                }
            }

        }


    }

    /**
     * 同步宝宝温度信息
     */
    private void SyncBBTempInfo() {

        GreenDaoUtils.getInstance(mContext).getTempListInfo(AppConstant.mCurrentBabyInfo.getLocalId())
                .subscribe(new Subscriber<List<TemperaturesBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TemperaturesBean> temperaturesBeen) {
                        final BabyTempAddReqBean babyTempAddReqBean = new BabyTempAddReqBean();
                        babyTempAddReqBean.setAccountId(AppConstant.getUserInfo().getUserId());
                        babyTempAddReqBean.setTemperatures(temperaturesBeen);
                        babyTempAddReqBean.setTRANSID(ApiConstants.MODIFY_BABY_TEMP);
                        // 提交温度数据
                        Api.getDefault().putUploadBBTempInfo(babyTempAddReqBean)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Subscriber<BaseResBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(BaseResBean baseResBean) {
                                        if (baseResBean.isOk()) {
                                            // 清除温度信息
                                            GreenDaoUtils.getInstance(mContext).delAllTempInfo();
                                        }
                                    }
                                });
                    }
                });
    }

    /**
     * 同步宝宝头像
     */
    private void SyncBBImgInfo() {
        GreenDaoUtils.getInstance(mContext).getBabyListByNoUploadImg()
                .subscribe(new Subscriber<List<BabyInfosBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<BabyInfosBean> babyInfosBeen) {
                        Observable.from(babyInfosBeen)
                                .filter(new Func1<BabyInfosBean, Boolean>() {
                                    @Override
                                    public Boolean call(BabyInfosBean babyInfosBean) {
                                        return !babyInfosBean.getHeadImageUrl().contains("http") &&
                                                !TextUtils.isEmpty(babyInfosBean.getHeadImageUrl());
                                    }
                                }).subscribe(new Subscriber<BabyInfosBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(final BabyInfosBean babyInfosBean) {
                                File file = new File(babyInfosBean.getHeadImageUrl());
                                if (file.exists()) {
                                    // 上传图片
                                    Api.getDefault().
                                            uploadFilesWithParts(Utils.filesToMultipartBodyParts(file))
                                            .subscribeOn(Schedulers.io())
                                            .subscribe(new Subscriber<MineUploadResBean>() {
                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {

                                                }

                                                @Override
                                                public void onNext(MineUploadResBean mineUploadResBean) {
                                                    if (!TextUtils.isEmpty(mineUploadResBean.getFileId())) {
                                                        // 更新数据库
                                                        GreenDaoUtils.getInstance(mContext).updateBabyImgFile(babyInfosBean,mineUploadResBean);
                                                    }
                                                }
                                            });
                                }


                            }
                        });
                    }
                });


    }


}
