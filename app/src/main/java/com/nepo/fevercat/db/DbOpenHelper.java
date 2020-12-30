package com.nepo.fevercat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.nepo.fevercat.gen.AlertHeightBeanDao;
import com.nepo.fevercat.gen.AlertLowBeanDao;
import com.nepo.fevercat.gen.BabyInfosBeanDao;
import com.nepo.fevercat.gen.DaoMaster;
import com.nepo.fevercat.gen.MotionBeanDao;
import com.nepo.fevercat.gen.TemperaturesBeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by sm on 2019/3/26.
 */


public class DbOpenHelper extends DaoMaster.DevOpenHelper {
    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.d("数据库升级", "onUpgrade: ");
        //把需要管理的数据库表DAO作为最后一个参数传入到方法中
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
                Log.d("数据库升级", "onUpgrade:1 ");
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
                Log.d("数据库升级", "onUpgrade: 2");
            }
        },  AlertHeightBeanDao.class, AlertLowBeanDao.class, BabyInfosBeanDao.class, MotionBeanDao.class,
                TemperaturesBeanDao.class);
    }
}
