package com.nepo.fevercat.common.utils;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.app.BaseApplication;
import com.nepo.fevercat.gen.AlertLowBeanDao;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  GreenDaoAlertWaterUtils
 * 作者 :   <sen>
 * 时间 :  下午5:26 2017/8/2.
 * 描述 :  喝水提醒
 */

public class GreenDaoAlertWaterUtils {

    private static GreenDaoAlertWaterUtils sGreenDaoAlertWaterUtils;
    private AlertLowBeanDao mAlertLowBeanDao;


    private GreenDaoAlertWaterUtils() {
        mAlertLowBeanDao = BaseApplication.getAppContext().getDaoSession().getAlertLowBeanDao();
    }

    public static GreenDaoAlertWaterUtils getInstance() {
        if (sGreenDaoAlertWaterUtils == null) {
            sGreenDaoAlertWaterUtils = new GreenDaoAlertWaterUtils();
        }
        return sGreenDaoAlertWaterUtils;
    }

    /**
     * 查询用药提醒
     */
    public List<AlertLowBean> getAlertLowWaterBeanList(){
        return mAlertLowBeanDao.queryBuilder()
                .where(AlertLowBeanDao.Properties.MAlertStatus.eq(AppConstant.ALERT_WATER_STATUS))
                .list();
    }



    /**
     * 有相同起始时间、相同喝水量、相同时间段提醒 喝水提醒
     *
     * @return
     */
    public List<AlertLowBean> getAlertLowBeanListByCondition(AlertLowBean alertLowBean) {

        return mAlertLowBeanDao.queryBuilder()
                .where(AlertLowBeanDao.Properties.MAlertStatus.eq(AppConstant.ALERT_WATER_STATUS)
                        , AlertLowBeanDao.Properties.MWaterBeginTime.eq(alertLowBean.getMWaterBeginTime())
                        , AlertLowBeanDao.Properties.MWaterEndTime.eq(alertLowBean.getMWaterEndTime())
                        , AlertLowBeanDao.Properties.MWaterUnits.eq(alertLowBean.getMWaterUnits())
                        , AlertLowBeanDao.Properties.MWaterInterval.eq(alertLowBean.getMWaterInterval())
                ).list();

    }


    /**
     * 查询指定时间段 喝水提醒
     * 1730 1749  1755
     */
    public List<AlertLowBean> getAlertLowBeanListByTime(String time) {

        return mAlertLowBeanDao.queryBuilder()
                .where(
                        AlertLowBeanDao.Properties.MAlertStatus.eq(AppConstant.ALERT_WATER_STATUS)
                        , AlertLowBeanDao.Properties.MIsEnabledAlert.ge(true)
                        , AlertLowBeanDao.Properties.MWaterBeginTimeConvert.le(time)
                        , AlertLowBeanDao.Properties.MWaterEndTimeConvert.ge(time)
                ).list();

    }


    /**
     * 插入喝水提醒
     * 喝水：……
     */
    public void insertAlertLowBean(AlertLowBean alertLowBean) {
        List<AlertLowBean> alertLowBeanListByCondition = getAlertLowBeanListByCondition(alertLowBean);
        if (alertLowBeanListByCondition.size() < 1) {
            mAlertLowBeanDao.insert(alertLowBean);
        }
    }

    /**
     * 更新
     */
    public void updateAlertLowBean(AlertLowBean alertLowBean){
        mAlertLowBeanDao.update(alertLowBean);
    }


    /**
     * 删除降温提醒
     */
    public void delAlertLowBean(AlertLowBean alertLowBean) {
        mAlertLowBeanDao.delete(alertLowBean);
    }


}
