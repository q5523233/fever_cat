package com.nepo.fevercat.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.nepo.fevercat.common.utils.BuildConfigUtils;
import com.nepo.fevercat.common.utils.DialogUtils;
import com.nepo.fevercat.common.utils.GsonHelper;
import com.nepo.fevercat.common.utils.LogUtils;
import com.nepo.fevercat.common.utils.SharedPreferencesUtil;
import com.nepo.fevercat.common.utils.Utils;
import com.nepo.fevercat.common.utils.screenshot.FileUtils;
import com.nepo.fevercat.common.widget.notify.AppStateUtil;
import com.nepo.fevercat.db.DbOpenHelper;
import com.nepo.fevercat.gen.DaoMaster;
import com.nepo.fevercat.gen.DaoSession;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.io.File;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.app
 * 文件名:  BaseApplication
 * 作者 :   <sen>
 * 时间 :  上午11:25 2017/2/15.
 * 描述 :
 */

public class BaseApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static BaseApplication baseApplication;

    public static BaseApplication getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);
        baseApplication = this;
        Utils.init(this);
        // 初始化Log日志
        LogUtils.logInit(BuildConfigUtils.DEBUG);
        SharedPreferencesUtil.init(this, GsonHelper.builderGson());
        // 初始化异常统计日志
        //CrashHandlerUtil.getInstance().init(baseApplication);
        Stetho.initializeWithDefaults(this);
        setDatabase();
//        initUMShare();
        AppStateUtil.init(this);

    }


    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DbOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化友盟分享
     */
    private void initUMShare() {
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "5eb6bd7a90b736a1cdcf5f817330384a");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("2321751822", "4e33acece7478192b1abbb2b84325329", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    /**
     * 发送高温提醒短信给亲友
     */
    public void sendHeightTempToFollow() {
        DialogUtils.getInstance(AppManager.getAppManager().currentActivity()).SendSmsToFollowDialog();
    }

    /**
     * 高温提醒
     */
    public void showAlertHeightDialog() {
        DialogUtils.getInstance(AppManager.getAppManager().currentActivity()).AlertHeightDialog();
    }


    /**
     * 用药提醒
     */
    public void showAlertMedicineDialog(AlertLowBean alertLowBean) {
        DialogUtils.getInstance(AppManager.getAppManager().currentActivity()).AlertMedicineDialog(alertLowBean);
    }

    /**
     * 喝水提醒
     */
    public void showAlertWaterDialog(AlertLowBean alertLowBean) {
        DialogUtils.getInstance(AppManager.getAppManager().currentActivity()).AlertWaterDialog(alertLowBean);
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        File file = getExternalCacheDir();
        String s = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".log";
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuffer buffer=new StringBuffer();
        for (int i = 0; i < stackTrace.length; i++) {
            buffer.append(stackTrace[i].toString());
        }
        FileUtils.writeFile(s, e.getMessage(), false);
    }
}

