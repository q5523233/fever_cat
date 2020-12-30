package com.nepo.fevercat.common.utils;

import android.text.TextUtils;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.app.BaseApplication;
import com.nepo.fevercat.gen.AlertLowBeanDao;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import java.util.List;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  GreenDaoAlertMedicineUtils
 * 作者 :   <sen>
 * 时间 :  下午6:19 2017/8/1.
 * 描述 :
 */

public class GreenDaoAlertMedicineUtils {

    private static GreenDaoAlertMedicineUtils sGreenDaoAlertMedicineUtils;
    private AlertLowBeanDao mAlertLowBeanDao;


    private GreenDaoAlertMedicineUtils() {
        mAlertLowBeanDao = BaseApplication.getAppContext().getDaoSession().getAlertLowBeanDao();
    }

    public static GreenDaoAlertMedicineUtils getInstance() {
        if (sGreenDaoAlertMedicineUtils == null) {
            sGreenDaoAlertMedicineUtils = new GreenDaoAlertMedicineUtils();
        }
        return sGreenDaoAlertMedicineUtils;
    }


    /**
     * 查询全部降温提醒
     *
     * @return
     */
    public List<AlertLowBean> getAllAlertLowBeanList() {

        return mAlertLowBeanDao.queryBuilder().list();
    }


    /**
     * 查询用药提醒
     */
    public List<AlertLowBean> getAlertLowMedicineBeanList(){
        return mAlertLowBeanDao.queryBuilder()
                .where(AlertLowBeanDao.Properties.MAlertStatus.eq(AppConstant.ALERT_MEDICINE_STATUS))
                .list();
    }

    /**
     * 查询用药提醒
     * @param mBabyInfosBean
     */
    public List<AlertLowBean> getAlertLowMedicineBeanListByBabyinfo(BabyInfosBean mBabyInfosBean){
        return mAlertLowBeanDao.queryBuilder()
                .where(AlertLowBeanDao.Properties.BelongBaby.eq(mBabyInfosBean.getId()+""))
                .where(AlertLowBeanDao.Properties.MAlertStatus.eq(AppConstant.ALERT_MEDICINE_STATUS))
                .list();
    }

    /**
     * 有相同药品、相同时间段提醒 降温提醒
     *
     * @return
     */
    public List<AlertLowBean> getAlertLowBeanListByCondition(AlertLowBean alertLowBean) {

        return mAlertLowBeanDao.queryBuilder()
                .where(AlertLowBeanDao.Properties.MAlertStatus.eq(AppConstant.ALERT_MEDICINE_STATUS)
                        , AlertLowBeanDao.Properties.MedicineName.eq(alertLowBean.getMedicineName())
                        , AlertLowBeanDao.Properties.MTime.eq(alertLowBean.getMTime())
                        , AlertLowBeanDao.Properties.MRepeatCode.eq(alertLowBean.getMRepeatCode())
                        , AlertLowBeanDao.Properties.BelongBaby.eq(alertLowBean.getBelongBaby())
                ).list();

    }

    /**
     * 获取单个提醒
     * @param alarmID
     * @return
     */
    public AlertLowBean getAlertLowBeanByID(int alarmID){
        return mAlertLowBeanDao.queryBuilder()
                .where(
                        AlertLowBeanDao.Properties.MAlertStatus.eq(AppConstant.ALERT_MEDICINE_STATUS)
                       , AlertLowBeanDao.Properties.Id.eq(alarmID))
                .unique();
    }


    /**
     * 插入降温提醒
     * 吃药：有相同药品、相同时间段提醒 不重复添加
     * 喝水：……
     */
    public long insertAlertLowBean(AlertLowBean alertLowBean) {
        long insert = -1;
        if (TextUtils.equals(alertLowBean.getMAlertStatus(), AppConstant.ALERT_MEDICINE_STATUS)) {
            // 判断是否有吃药：重复提醒
            List<AlertLowBean> alertLowBeanListByCondition = getAlertLowBeanListByCondition(alertLowBean);
            if (alertLowBeanListByCondition.size() < 1) {
                insert = mAlertLowBeanDao.insert(alertLowBean);
            }

        }


        return insert;
    }

    /**
     * 更新
     */
    public void updateAlertLowBean(AlertLowBean alertLowBean) {

        mAlertLowBeanDao.update(alertLowBean);
    }


    /**
     * 删除降温提醒
     */
    public void delAlertLowBean(AlertLowBean alertLowBean) {
        mAlertLowBeanDao.delete(alertLowBean);
    }


}
