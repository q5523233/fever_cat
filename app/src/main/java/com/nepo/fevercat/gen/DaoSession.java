package com.nepo.fevercat.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MotionBean;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;

import com.nepo.fevercat.gen.AlertHeightBeanDao;
import com.nepo.fevercat.gen.AlertLowBeanDao;
import com.nepo.fevercat.gen.BabyInfosBeanDao;
import com.nepo.fevercat.gen.MotionBeanDao;
import com.nepo.fevercat.gen.TemperaturesBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig alertHeightBeanDaoConfig;
    private final DaoConfig alertLowBeanDaoConfig;
    private final DaoConfig babyInfosBeanDaoConfig;
    private final DaoConfig motionBeanDaoConfig;
    private final DaoConfig temperaturesBeanDaoConfig;

    private final AlertHeightBeanDao alertHeightBeanDao;
    private final AlertLowBeanDao alertLowBeanDao;
    private final BabyInfosBeanDao babyInfosBeanDao;
    private final MotionBeanDao motionBeanDao;
    private final TemperaturesBeanDao temperaturesBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        alertHeightBeanDaoConfig = daoConfigMap.get(AlertHeightBeanDao.class).clone();
        alertHeightBeanDaoConfig.initIdentityScope(type);

        alertLowBeanDaoConfig = daoConfigMap.get(AlertLowBeanDao.class).clone();
        alertLowBeanDaoConfig.initIdentityScope(type);

        babyInfosBeanDaoConfig = daoConfigMap.get(BabyInfosBeanDao.class).clone();
        babyInfosBeanDaoConfig.initIdentityScope(type);

        motionBeanDaoConfig = daoConfigMap.get(MotionBeanDao.class).clone();
        motionBeanDaoConfig.initIdentityScope(type);

        temperaturesBeanDaoConfig = daoConfigMap.get(TemperaturesBeanDao.class).clone();
        temperaturesBeanDaoConfig.initIdentityScope(type);

        alertHeightBeanDao = new AlertHeightBeanDao(alertHeightBeanDaoConfig, this);
        alertLowBeanDao = new AlertLowBeanDao(alertLowBeanDaoConfig, this);
        babyInfosBeanDao = new BabyInfosBeanDao(babyInfosBeanDaoConfig, this);
        motionBeanDao = new MotionBeanDao(motionBeanDaoConfig, this);
        temperaturesBeanDao = new TemperaturesBeanDao(temperaturesBeanDaoConfig, this);

        registerDao(AlertHeightBean.class, alertHeightBeanDao);
        registerDao(AlertLowBean.class, alertLowBeanDao);
        registerDao(BabyInfosBean.class, babyInfosBeanDao);
        registerDao(MotionBean.class, motionBeanDao);
        registerDao(TemperaturesBean.class, temperaturesBeanDao);
    }
    
    public void clear() {
        alertHeightBeanDaoConfig.clearIdentityScope();
        alertLowBeanDaoConfig.clearIdentityScope();
        babyInfosBeanDaoConfig.clearIdentityScope();
        motionBeanDaoConfig.clearIdentityScope();
        temperaturesBeanDaoConfig.clearIdentityScope();
    }

    public AlertHeightBeanDao getAlertHeightBeanDao() {
        return alertHeightBeanDao;
    }

    public AlertLowBeanDao getAlertLowBeanDao() {
        return alertLowBeanDao;
    }

    public BabyInfosBeanDao getBabyInfosBeanDao() {
        return babyInfosBeanDao;
    }

    public MotionBeanDao getMotionBeanDao() {
        return motionBeanDao;
    }

    public TemperaturesBeanDao getTemperaturesBeanDao() {
        return temperaturesBeanDao;
    }

}