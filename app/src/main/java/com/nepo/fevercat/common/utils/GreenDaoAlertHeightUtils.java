package com.nepo.fevercat.common.utils;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.app.BaseApplication;
import com.nepo.fevercat.gen.AlertHeightBeanDao;
import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  GreenDaoAlertHeightUtils
 * 作者 :   <sen>
 * 时间 :  下午4:40 2017/7/31.
 * 描述 : 高温提醒
 */

public class GreenDaoAlertHeightUtils {

    private static GreenDaoAlertHeightUtils sGreenDaoAlertHeightUtils;
    private AlertHeightBeanDao mAlertHeightBeanDao;


    private GreenDaoAlertHeightUtils() {
        mAlertHeightBeanDao = BaseApplication.getAppContext().getDaoSession().getAlertHeightBeanDao();
    }

    public static GreenDaoAlertHeightUtils getInstance(){
        if (sGreenDaoAlertHeightUtils == null) {
            sGreenDaoAlertHeightUtils = new GreenDaoAlertHeightUtils();
        }
        return sGreenDaoAlertHeightUtils;
    }


    /**
     * 获取所有高温列表
     */
    public List<AlertHeightBean> getAllAlertHeightList(){

        return mAlertHeightBeanDao.queryBuilder().orderAsc(AlertHeightBeanDao.Properties.SSelectType).list();
    }


    /**
     * 获取所有高温列表 当前选中宝宝开启的
     */
    public List<AlertHeightBean> getAllAlertHeightListByCheckStatus(){
        if (AppConstant.mCurrentBabyInfo == null){
            return null;
        }
        return mAlertHeightBeanDao.queryBuilder()
                .where(AlertHeightBeanDao.Properties.SCheckStatus.eq(true),
                        AlertHeightBeanDao.Properties.BelongBaby.eq(AppConstant.mCurrentBabyInfo.getId()))
                .orderAsc(AlertHeightBeanDao.Properties.SSelectType).list();
    }




    /**
     * 获取选中宝宝的高温列表
     */
    public List<AlertHeightBean> getAllAlertHeightListByBabyInfo(BabyInfosBean babyInfosBean){
        return mAlertHeightBeanDao.queryBuilder()
                .where(AlertHeightBeanDao.Properties.BelongBaby.eq(babyInfosBean.getId()+""))
                .orderAsc(AlertHeightBeanDao.Properties.SSelectType).list();

    }


    /**
     * 获取指定温度间隔内的高温提醒列表 并开关状态为开
     * @return
     */
    public List<AlertHeightBean> getAlertHeightListByTemp(String tempValue){

        return mAlertHeightBeanDao
                .queryBuilder()
                .where(AlertHeightBeanDao.Properties.SCheckStatus.eq(true),
                        AlertHeightBeanDao.Properties.SGtTemp.lt(tempValue),
                        AlertHeightBeanDao.Properties.SLeTemp.gt(tempValue),
                        AlertHeightBeanDao.Properties.BelongBaby.eq(AppConstant.mCurrentBabyInfo.getId())
                        )
                .orderDesc(AlertHeightBeanDao.Properties.SMillisecond)
                .list();
    }

    /**
     * 获取相同宝宝、相同温度、相同间隔的数据
     */
    private List<AlertHeightBean> getAlertHeightListByTempAndInterval(AlertHeightBean alertHeightBean){
        return mAlertHeightBeanDao
                .queryBuilder()
                .where(AlertHeightBeanDao.Properties.SGtTemp.eq(alertHeightBean.getSGtTemp()),
                        AlertHeightBeanDao.Properties.SLeTemp.eq(alertHeightBean.getSLeTemp()),
                        AlertHeightBeanDao.Properties.BelongBaby.eq(alertHeightBean.getBelongBaby()))
                .list();
    }


    /**
     * 添加高温提醒
     * 判断 温度、时间间隔是否重复 true 拒绝添加
     */
    public boolean insertAlertHeight(AlertHeightBean alertHeightBean){
        int size = getAlertHeightListByTempAndInterval(alertHeightBean).size();
        if (size==0) {
            // 插入温度
            mAlertHeightBeanDao.insert(alertHeightBean);
            return true;
        }else{
            return false;
        }
    }


    /**
     * 修改高温提醒
     */
    public void updateAlertHeight(AlertHeightBean alertHeightBean){
        mAlertHeightBeanDao.update(alertHeightBean);
    }

    /**
     * 删除高温提醒
     */
    public void delAlertHeight(AlertHeightBean alertHeightBean){
        mAlertHeightBeanDao.delete(alertHeightBean);
    }


    /**
     * 还原所有高温报警最后响铃时间
     *  = 0
     */
    public void restoreHeightAlert(){
        List<AlertHeightBean> allAlertHeightList = getAllAlertHeightList();
        for (AlertHeightBean alertHeightBean : allAlertHeightList) {
            alertHeightBean.setSLastRingMillisecond("0");
            updateAlertHeight(alertHeightBean);
        }
    }



}
