package com.nepo.fevercat.common.utils;

import android.content.Context;
import android.text.TextUtils;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.app.BaseApplication;
import com.nepo.fevercat.gen.BabyInfosBeanDao;
import com.nepo.fevercat.gen.MotionBeanDao;
import com.nepo.fevercat.gen.TemperaturesBeanDao;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.mine.bean.MotionBean;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  GreenDaoUtils
 * 作者 :   <sen>
 * 时间 :  上午10:23 2017/7/21.
 * 描述 :  数据库操作工具类
 */

public class GreenDaoUtils {

    private static GreenDaoUtils mGreenDaoUtils;
    private BabyInfosBeanDao mBabyInfosBeanDao;
    private TemperaturesBeanDao mTemperaturesBeanDao;
    private MotionBeanDao motionBeanDao;

    private GreenDaoUtils(Context context) {
        mBabyInfosBeanDao = BaseApplication.getAppContext().getDaoSession().getBabyInfosBeanDao();
        mTemperaturesBeanDao = BaseApplication.getAppContext().getDaoSession().getTemperaturesBeanDao();
        motionBeanDao = BaseApplication.getAppContext().getDaoSession().getMotionBeanDao();
    }

    public static GreenDaoUtils getInstance(Context context) {
        if (mGreenDaoUtils == null) {
            mGreenDaoUtils = new GreenDaoUtils(context);
        }
        return mGreenDaoUtils;
    }


    /**
     * 查询宝宝列表
     * 非删除
     */
    public List<BabyInfosBean> getBabyListInfo() {
        List<BabyInfosBean> babyInfosBeanList;
        if (AppConstant.isLogin()) {
            babyInfosBeanList = mBabyInfosBeanDao
                    .queryBuilder()
                    .where(BabyInfosBeanDao.Properties.ControlStatus.notEq(AppConstant.BB_CONTROL_STATUS_DEL)
                            , BabyInfosBeanDao.Properties.AccountId.eq(AppConstant.getUserInfo().getUserId()))
                    .list();
        } else {
            babyInfosBeanList = mBabyInfosBeanDao
                    .queryBuilder()
                    .where(BabyInfosBeanDao.Properties.ControlStatus.notEq(AppConstant.BB_CONTROL_STATUS_DEL))
                    .list();
        }


        return babyInfosBeanList;

    }


    /**
     * 全部宝宝列表
     */
    public List<BabyInfosBean> getBabyListAllInfo() {
        List<BabyInfosBean> babyInfosBeanList;
        babyInfosBeanList = mBabyInfosBeanDao
                .queryBuilder()
                .where(BabyInfosBeanDao.Properties.AccountId.eq(AppConstant.getUserInfo().getUserId()))
                .list();

        return babyInfosBeanList;
    }

    /**
     * 查询未上传头像的宝宝信息
     */
    public Observable<List<BabyInfosBean>> getBabyListByNoUploadImg() {
        return mBabyInfosBeanDao
                .queryBuilder()
                .where(BabyInfosBeanDao.Properties.BabyId.notEq("None")
                ).rx().list();
    }


    /**
     * 查询单个宝宝
     */
    public BabyInfosBean getSingleByID(long id) {

        return mBabyInfosBeanDao
                .queryBuilder()
                .where(BabyInfosBeanDao.Properties.Id.eq(id))
                .unique();
    }


    /**
     * 查询单个宝宝
     *
     * @param uuid 本地UUID
     * @return
     */
    public BabyInfosBean getSingleBabyInfo(String uuid) {
        BabyInfosBean unique = mBabyInfosBeanDao
                .queryBuilder()
                .where(BabyInfosBeanDao.Properties.LocalId.eq(uuid), BabyInfosBeanDao.Properties.ControlStatus.notEq(AppConstant.BB_CONTROL_STATUS_DEL))
                .unique();
        return unique;
    }

    /**
     * 根据BabyID查询
     */
    public BabyInfosBean getSingleBabyInfoByBabyID(String babyId) {
        List<BabyInfosBean> list = mBabyInfosBeanDao
                .queryBuilder()
                .where(BabyInfosBeanDao.Properties.BabyId.eq(babyId), BabyInfosBeanDao.Properties.ControlStatus.notEq(AppConstant.BB_CONTROL_STATUS_DEL))
                .list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return new BabyInfosBean();
    }


    /**
     * 增加宝宝信息
     */
    public void addBabyControlStatus(BabyInfosBean babyInfosBean) {

        if (AppConstant.isLogin()) {
            if (TextUtils.isEmpty(babyInfosBean.getAccountId())) {
                babyInfosBean.setAccountId(AppConstant.getUserInfo().getUserId());
            }
        }

        if (TextUtils.isEmpty(babyInfosBean.getControlStatus())) {
            babyInfosBean.setControlStatus(AppConstant.BB_CONTROL_STATUS_NORMAL);
        }

        mBabyInfosBeanDao.insert(babyInfosBean);
    }

    /**
     * 更改宝宝操作状态
     *
     * @param babyInfosBean
     */
    public void updateBabyControlStatus(BabyInfosBean babyInfosBean) {
        if (AppConstant.isLogin()) {
            if (TextUtils.isEmpty(babyInfosBean.getAccountId())) {
                babyInfosBean.setAccountId(AppConstant.getUserInfo().getUserId());
            }
        }

        if (TextUtils.isEmpty(babyInfosBean.getControlStatus())) {
            babyInfosBean.setControlStatus(AppConstant.BB_CONTROL_STATUS_NORMAL);
        }

        mBabyInfosBeanDao.update(babyInfosBean);
    }


    /**
     * 更改宝宝头像
     */
    public void updateBabyImgFile(BabyInfosBean babyInfosBean, MineUploadResBean mineUploadResBean) {
        BabyInfosBean unique = mBabyInfosBeanDao.queryBuilder()
                .where(BabyInfosBeanDao.Properties.BabyId.eq(babyInfosBean.getBabyId()))
                .unique();
        unique.setHeadImageId(mineUploadResBean.getFileId());
        unique.setHeadImageUrl(mineUploadResBean.getFileUrl());
        mBabyInfosBeanDao.update(unique);
    }

    /**
     * 删除宝宝信息
     */
    public void delBabyInfo(String uuid, String babyID) {
        BabyInfosBean singleBabyInfo;
        if (!TextUtils.isEmpty(uuid)) {
            singleBabyInfo = getSingleBabyInfo(uuid);
        } else {
            singleBabyInfo = getSingleBabyInfoByBabyID(babyID);
        }
        // 未同步过 舍弃
        // 删除数据
        if (TextUtils.isEmpty(singleBabyInfo.getBabyId())) {
            mBabyInfosBeanDao.delete(singleBabyInfo);
        } else {
            //更改操作状态
            singleBabyInfo.setControlStatus(AppConstant.BB_CONTROL_STATUS_DEL);
            updateBabyControlStatus(singleBabyInfo);
        }

    }


    /**
     * 删除宝宝信息
     *
     * @param babyInfosBean
     */
    public void delBybyInfo(BabyInfosBean babyInfosBean) {
        mBabyInfosBeanDao.delete(babyInfosBean);
    }


    /**
     * 更新数据库BabyID字段
     */
    public void updateBabyIDProperty(BabyInfosBean babyInfosBean, String babyId) {
        babyInfosBean.setBabyId(babyId);
        babyInfosBean.setControlStatus(AppConstant.BB_CONTROL_STATUS_NORMAL);
        updateBabyControlStatus(babyInfosBean);
    }


    /**
     * 更新数据库操作状态
     *
     * @param babyInfosBean
     */
    public void updateBabyControlStatusProperty(BabyInfosBean babyInfosBean) {
        if (TextUtils.equals(babyInfosBean.getHeadImageId(), "None")) {
            babyInfosBean.setHeadImageId("");
        }
        babyInfosBean.setControlStatus(AppConstant.BB_CONTROL_STATUS_NORMAL);
        updateBabyControlStatus(babyInfosBean);
    }

    /**
     * 登录后更新用户ID到数据库
     */
    public void updateAccountIDToBabyInfo() {
        List<BabyInfosBean> list = mBabyInfosBeanDao.queryBuilder()
                .where(BabyInfosBeanDao.Properties.AccountId.eq("None"))
                .list();
        for (BabyInfosBean babyInfosBean : list) {
            if (AppConstant.isLogin()) {
                babyInfosBean.setAccountId(AppConstant.getUserInfo().getUserId());
                mBabyInfosBeanDao.update(babyInfosBean);
            }
        }
    }


    /**
     * 用服务器数据覆盖本地
     */
    public List<BabyInfosBean> updateCoverLocalByServer(MineBBResBean mineBBResBean) {
        List<BabyInfosBean> babyInfos = mineBBResBean.getBabyInfos();
        // 获取本地匹配服务器SID并状态为正常的数据
        if (babyInfos != null
                && babyInfos.size() > 0
                ) {

            for (BabyInfosBean babyInfo : babyInfos) {
                BabyInfosBean uniqueBean = null;

                List<BabyInfosBean> list = mBabyInfosBeanDao
                        .queryBuilder()
                        .where(BabyInfosBeanDao.Properties.BabyId.eq(babyInfo.getBabyId())
                        ).list();
                if (list != null && list.size() > 0) {
                    uniqueBean = list.get(0);
                }
                if (uniqueBean != null) {
                    if (TextUtils.equals(uniqueBean.getControlStatus(), AppConstant.BB_CONTROL_STATUS_NORMAL)) {
                        // 进行覆盖
                        if (!TextUtils.isEmpty(babyInfo.getBirthday())) {
                            uniqueBean.setBirthday(babyInfo.getBirthday());
                        }
                        if (!TextUtils.isEmpty(babyInfo.getHeadImageId())) {
                            uniqueBean.setHeadImageId(babyInfo.getHeadImageId());
                        }
                        if (!TextUtils.isEmpty(babyInfo.getHeadImageUrl())) {
                            uniqueBean.setHeadImageUrl(babyInfo.getHeadImageUrl());
                        }
                        if (!TextUtils.isEmpty(babyInfo.getNickname())) {
                            uniqueBean.setNickname(babyInfo.getNickname());
                        }
                        if (!TextUtils.isEmpty(babyInfo.getSex())) {
                            uniqueBean.setSex(babyInfo.getSex());
                        }
                        //进行覆盖
                        updateBabyControlStatus(uniqueBean);
                    }

                } else {
                    // 添加到本地库
                    addBabyControlStatus(babyInfo);
                }

            }

        }


        return getBabyListInfo();
    }


    /*********************************************温度操作***********************************************/

    /**
     * 添加温度
     *
     * @param temperaturesBean
     */
    public void addTempInfo(TemperaturesBean temperaturesBean) {
        mTemperaturesBeanDao.insert(temperaturesBean);
    }

    /**
     * 获取温度列表
     */
    public Observable<List<TemperaturesBean>> getTempListInfo(String localId) {

        Observable<List<TemperaturesBean>> listObservable = mTemperaturesBeanDao.queryBuilder()
                //之前的代码
                //                .where(TemperaturesBeanDao.Properties.AccountId.eq(AppConstant.getUserInfo().getUserId()),
                //                        TemperaturesBeanDao.Properties.BabyId.notEq("None"),
                //                        TemperaturesBeanDao.Properties.BabyId.notEq(""))
                .where(TemperaturesBeanDao.Properties.LocalBabyId.eq(localId))
                .rx()
                .list()
                .subscribeOn(Schedulers.io());
        return listObservable;

    }

    /**
     * 获取某日温度列表
     *
     * @param type 时间段 分为5类
     */
    public Observable<List<TemperaturesBean>> getTempListInfoByDay(String date, String type, String localId) {
        int range;
        if (!TextUtils.isEmpty(type)) {
            range = Integer.parseInt(type);
        } else {
            range = 5;
        }
        int rangeMin = 0;
        int rangeMax = 24;
        if (range < 4) {
            rangeMin = range * 6;
            rangeMax = rangeMin + 6;
        }
        String timeMin;
        if (rangeMin < 10)
            timeMin = date + " 0" + rangeMin + ":00:00";
        else
            timeMin = date + " "+rangeMin + ":00:00";
        String timeMax=date +" "+rangeMax + ":00:00";
//        final int finalRangeMin = rangeMin;
//        final int finalRangeMax = rangeMax;
        Observable<List<TemperaturesBean>> listObservable = mTemperaturesBeanDao.queryBuilder()
                .where(TemperaturesBeanDao.Properties.LocalBabyId.eq(localId),
                        TemperaturesBeanDao.Properties.TempTime.ge(timeMin),
                        TemperaturesBeanDao.Properties.TempTime.lt(timeMax))
                .rx()
                .list()
                //没想到数据量大时，很耗时，不能用这种方法筛选
//                .flatMap(new Func1<List<TemperaturesBean>, Observable<List<TemperaturesBean>>>() {
//                    @Override
//                    public Observable<List<TemperaturesBean>> call(final List<TemperaturesBean> temperaturesBeen) {
//                        for (int i = 0; i < temperaturesBeen.size(); i++) {
//                            int[] out = new int[3];
//                            TemperaturesBean bean = temperaturesBeen.get(i);
//                            DateUtil.getDaySplit(bean.getTempTime(), out);
//                            if (out[0] < finalRangeMin || out[0] >= finalRangeMax) {
//                                temperaturesBeen.remove(i);
//                                i--;
//                            }
//                        }
//                        return Observable.create(new Observable.OnSubscribe<List<TemperaturesBean>>() {
//                            @Override
//                            public void call(Subscriber<? super List<TemperaturesBean>> subscriber) {
//                                subscriber.onNext(temperaturesBeen);
//                            }
//                        });
//                    }
//                })
                .subscribeOn(Schedulers.io());
        return listObservable;

    }

    /**
     * 更新用户ID到温度列表
     */
    public void updateAccountIDToTempInfo() {
        List<TemperaturesBean> list = mTemperaturesBeanDao.queryBuilder()
                .where(TemperaturesBeanDao.Properties.AccountId.eq("None"))
                .list();
        for (TemperaturesBean temperaturesBean : list) {
            if (AppConstant.isLogin()) {
                temperaturesBean.setAccountId(AppConstant.getUserInfo().getUserId());
                mTemperaturesBeanDao.update(temperaturesBean);
            }
        }
    }

    /**
     * 更新温度BabyID
     */
    public void updateBabyIDToTempInfo(BabyInfosBean babyInfosBean, MineBBResBean mineBBResBean) {
        List<TemperaturesBean> list = mTemperaturesBeanDao.queryBuilder()
                .where(TemperaturesBeanDao.Properties.Id.eq(babyInfosBean.getId()))
                .list();
        for (TemperaturesBean temperaturesBean : list) {
            temperaturesBean.setBabyId(mineBBResBean.getBabyId());
            mTemperaturesBeanDao.update(temperaturesBean);
        }
    }

    /**
     * 删除温度信息根据宝宝ID
     */
    public void delTempInfoByBabyID(BabyInfosBean babyInfosBean) {
        TemperaturesBean temperaturesBean = new TemperaturesBean();
        temperaturesBean.setBabyId(babyInfosBean.getBabyId());
        mTemperaturesBeanDao.delete(temperaturesBean);
    }

    /**
     * 删除温度信息根据宝宝LocalID
     */
    public void delTempInfoByBabyLocalID(String localID) {
        if (!TextUtils.isEmpty(localID)) {
            List<TemperaturesBean> temperaturesBeanList = mTemperaturesBeanDao.queryBuilder()
                    .where(TemperaturesBeanDao.Properties.Id.eq(localID))
                    .list();
            for (TemperaturesBean temperaturesBean : temperaturesBeanList) {
                mTemperaturesBeanDao.delete(temperaturesBean);
            }
        }

    }


    /**
     * 清除所有温度信息
     */
    public void delAllTempInfo() {
        getTempListInfo(AppConstant.mCurrentBabyInfo.getLocalId())
                .subscribe(new Subscriber<List<TemperaturesBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TemperaturesBean> temperaturesBeen) {
                        for (TemperaturesBean temperaturesBean : temperaturesBeen) {
                            mTemperaturesBeanDao.delete(temperaturesBean);
                        }
                    }
                });
    }

    /**
     * 根据日期查询宝宝温度
     */
    public List<TempHistoryDataResBean> convertTmpToHis(List<TemperaturesBean> list) {
        List<TempHistoryDataResBean> historyDatalist = new ArrayList<>();
        for (TemperaturesBean temp : list) {
            historyDatalist.add(new TempHistoryDataResBean());
        }
        return historyDatalist;
    }

    public List<MotionBean> getMotionList(int type) {
        return motionBeanDao.queryBuilder().where(MotionBeanDao.Properties.Type.eq(type))
                .list();
    }

    public void addMotionBean(MotionBean motionBean) {
        motionBeanDao.insertOrReplace(motionBean);
    }

    public void delMotionBean(MotionBean motionBean) {
        motionBeanDao.delete(motionBean);
    }
}
