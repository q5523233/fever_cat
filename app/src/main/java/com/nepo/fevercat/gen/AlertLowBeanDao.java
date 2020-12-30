package com.nepo.fevercat.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.nepo.fevercat.ui.alert.bean.AlertLowBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALERT_LOW_BEAN".
*/
public class AlertLowBeanDao extends AbstractDao<AlertLowBean, Long> {

    public static final String TABLENAME = "ALERT_LOW_BEAN";

    /**
     * Properties of entity AlertLowBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MTitle = new Property(1, String.class, "mTitle", false, "M_TITLE");
        public final static Property MTime = new Property(2, String.class, "mTime", false, "M_TIME");
        public final static Property MRepeatType = new Property(3, String.class, "mRepeatType", false, "M_REPEAT_TYPE");
        public final static Property MRepeatCode = new Property(4, String.class, "mRepeatCode", false, "M_REPEAT_CODE");
        public final static Property MActive = new Property(5, String.class, "mActive", false, "M_ACTIVE");
        public final static Property MWakeType = new Property(6, String.class, "mWakeType", false, "M_WAKE_TYPE");
        public final static Property MRing = new Property(7, String.class, "mRing", false, "M_RING");
        public final static Property MAlertStatus = new Property(8, String.class, "mAlertStatus", false, "M_ALERT_STATUS");
        public final static Property MIsEnabledAlert = new Property(9, boolean.class, "mIsEnabledAlert", false, "M_IS_ENABLED_ALERT");
        public final static Property MedicineName = new Property(10, String.class, "medicineName", false, "MEDICINE_NAME");
        public final static Property MWaterBeginTime = new Property(11, String.class, "mWaterBeginTime", false, "M_WATER_BEGIN_TIME");
        public final static Property MWaterBeginTimeConvert = new Property(12, String.class, "mWaterBeginTimeConvert", false, "M_WATER_BEGIN_TIME_CONVERT");
        public final static Property MWaterEndTime = new Property(13, String.class, "mWaterEndTime", false, "M_WATER_END_TIME");
        public final static Property MWaterEndTimeConvert = new Property(14, String.class, "mWaterEndTimeConvert", false, "M_WATER_END_TIME_CONVERT");
        public final static Property MWaterUnits = new Property(15, String.class, "mWaterUnits", false, "M_WATER_UNITS");
        public final static Property MWaterInterval = new Property(16, String.class, "mWaterInterval", false, "M_WATER_INTERVAL");
        public final static Property MWaterIntervalMilliSecond = new Property(17, String.class, "mWaterIntervalMilliSecond", false, "M_WATER_INTERVAL_MILLI_SECOND");
        public final static Property MWaterLastIntervalMilliSecond = new Property(18, String.class, "mWaterLastIntervalMilliSecond", false, "M_WATER_LAST_INTERVAL_MILLI_SECOND");
        public final static Property IsSelect = new Property(19, boolean.class, "isSelect", false, "IS_SELECT");
        public final static Property BelongBaby = new Property(20, String.class, "belongBaby", false, "BELONG_BABY");
    }


    public AlertLowBeanDao(DaoConfig config) {
        super(config);
    }
    
    public AlertLowBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALERT_LOW_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"M_TITLE\" TEXT," + // 1: mTitle
                "\"M_TIME\" TEXT," + // 2: mTime
                "\"M_REPEAT_TYPE\" TEXT," + // 3: mRepeatType
                "\"M_REPEAT_CODE\" TEXT," + // 4: mRepeatCode
                "\"M_ACTIVE\" TEXT," + // 5: mActive
                "\"M_WAKE_TYPE\" TEXT," + // 6: mWakeType
                "\"M_RING\" TEXT," + // 7: mRing
                "\"M_ALERT_STATUS\" TEXT," + // 8: mAlertStatus
                "\"M_IS_ENABLED_ALERT\" INTEGER NOT NULL ," + // 9: mIsEnabledAlert
                "\"MEDICINE_NAME\" TEXT," + // 10: medicineName
                "\"M_WATER_BEGIN_TIME\" TEXT," + // 11: mWaterBeginTime
                "\"M_WATER_BEGIN_TIME_CONVERT\" TEXT," + // 12: mWaterBeginTimeConvert
                "\"M_WATER_END_TIME\" TEXT," + // 13: mWaterEndTime
                "\"M_WATER_END_TIME_CONVERT\" TEXT," + // 14: mWaterEndTimeConvert
                "\"M_WATER_UNITS\" TEXT," + // 15: mWaterUnits
                "\"M_WATER_INTERVAL\" TEXT," + // 16: mWaterInterval
                "\"M_WATER_INTERVAL_MILLI_SECOND\" TEXT," + // 17: mWaterIntervalMilliSecond
                "\"M_WATER_LAST_INTERVAL_MILLI_SECOND\" TEXT," + // 18: mWaterLastIntervalMilliSecond
                "\"IS_SELECT\" INTEGER NOT NULL ," + // 19: isSelect
                "\"BELONG_BABY\" TEXT);"); // 20: belongBaby
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALERT_LOW_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AlertLowBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String mTitle = entity.getMTitle();
        if (mTitle != null) {
            stmt.bindString(2, mTitle);
        }
 
        String mTime = entity.getMTime();
        if (mTime != null) {
            stmt.bindString(3, mTime);
        }
 
        String mRepeatType = entity.getMRepeatType();
        if (mRepeatType != null) {
            stmt.bindString(4, mRepeatType);
        }
 
        String mRepeatCode = entity.getMRepeatCode();
        if (mRepeatCode != null) {
            stmt.bindString(5, mRepeatCode);
        }
 
        String mActive = entity.getMActive();
        if (mActive != null) {
            stmt.bindString(6, mActive);
        }
 
        String mWakeType = entity.getMWakeType();
        if (mWakeType != null) {
            stmt.bindString(7, mWakeType);
        }
 
        String mRing = entity.getMRing();
        if (mRing != null) {
            stmt.bindString(8, mRing);
        }
 
        String mAlertStatus = entity.getMAlertStatus();
        if (mAlertStatus != null) {
            stmt.bindString(9, mAlertStatus);
        }
        stmt.bindLong(10, entity.getMIsEnabledAlert() ? 1L: 0L);
 
        String medicineName = entity.getMedicineName();
        if (medicineName != null) {
            stmt.bindString(11, medicineName);
        }
 
        String mWaterBeginTime = entity.getMWaterBeginTime();
        if (mWaterBeginTime != null) {
            stmt.bindString(12, mWaterBeginTime);
        }
 
        String mWaterBeginTimeConvert = entity.getMWaterBeginTimeConvert();
        if (mWaterBeginTimeConvert != null) {
            stmt.bindString(13, mWaterBeginTimeConvert);
        }
 
        String mWaterEndTime = entity.getMWaterEndTime();
        if (mWaterEndTime != null) {
            stmt.bindString(14, mWaterEndTime);
        }
 
        String mWaterEndTimeConvert = entity.getMWaterEndTimeConvert();
        if (mWaterEndTimeConvert != null) {
            stmt.bindString(15, mWaterEndTimeConvert);
        }
 
        String mWaterUnits = entity.getMWaterUnits();
        if (mWaterUnits != null) {
            stmt.bindString(16, mWaterUnits);
        }
 
        String mWaterInterval = entity.getMWaterInterval();
        if (mWaterInterval != null) {
            stmt.bindString(17, mWaterInterval);
        }
 
        String mWaterIntervalMilliSecond = entity.getMWaterIntervalMilliSecond();
        if (mWaterIntervalMilliSecond != null) {
            stmt.bindString(18, mWaterIntervalMilliSecond);
        }
 
        String mWaterLastIntervalMilliSecond = entity.getMWaterLastIntervalMilliSecond();
        if (mWaterLastIntervalMilliSecond != null) {
            stmt.bindString(19, mWaterLastIntervalMilliSecond);
        }
        stmt.bindLong(20, entity.getIsSelect() ? 1L: 0L);
 
        String belongBaby = entity.getBelongBaby();
        if (belongBaby != null) {
            stmt.bindString(21, belongBaby);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AlertLowBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String mTitle = entity.getMTitle();
        if (mTitle != null) {
            stmt.bindString(2, mTitle);
        }
 
        String mTime = entity.getMTime();
        if (mTime != null) {
            stmt.bindString(3, mTime);
        }
 
        String mRepeatType = entity.getMRepeatType();
        if (mRepeatType != null) {
            stmt.bindString(4, mRepeatType);
        }
 
        String mRepeatCode = entity.getMRepeatCode();
        if (mRepeatCode != null) {
            stmt.bindString(5, mRepeatCode);
        }
 
        String mActive = entity.getMActive();
        if (mActive != null) {
            stmt.bindString(6, mActive);
        }
 
        String mWakeType = entity.getMWakeType();
        if (mWakeType != null) {
            stmt.bindString(7, mWakeType);
        }
 
        String mRing = entity.getMRing();
        if (mRing != null) {
            stmt.bindString(8, mRing);
        }
 
        String mAlertStatus = entity.getMAlertStatus();
        if (mAlertStatus != null) {
            stmt.bindString(9, mAlertStatus);
        }
        stmt.bindLong(10, entity.getMIsEnabledAlert() ? 1L: 0L);
 
        String medicineName = entity.getMedicineName();
        if (medicineName != null) {
            stmt.bindString(11, medicineName);
        }
 
        String mWaterBeginTime = entity.getMWaterBeginTime();
        if (mWaterBeginTime != null) {
            stmt.bindString(12, mWaterBeginTime);
        }
 
        String mWaterBeginTimeConvert = entity.getMWaterBeginTimeConvert();
        if (mWaterBeginTimeConvert != null) {
            stmt.bindString(13, mWaterBeginTimeConvert);
        }
 
        String mWaterEndTime = entity.getMWaterEndTime();
        if (mWaterEndTime != null) {
            stmt.bindString(14, mWaterEndTime);
        }
 
        String mWaterEndTimeConvert = entity.getMWaterEndTimeConvert();
        if (mWaterEndTimeConvert != null) {
            stmt.bindString(15, mWaterEndTimeConvert);
        }
 
        String mWaterUnits = entity.getMWaterUnits();
        if (mWaterUnits != null) {
            stmt.bindString(16, mWaterUnits);
        }
 
        String mWaterInterval = entity.getMWaterInterval();
        if (mWaterInterval != null) {
            stmt.bindString(17, mWaterInterval);
        }
 
        String mWaterIntervalMilliSecond = entity.getMWaterIntervalMilliSecond();
        if (mWaterIntervalMilliSecond != null) {
            stmt.bindString(18, mWaterIntervalMilliSecond);
        }
 
        String mWaterLastIntervalMilliSecond = entity.getMWaterLastIntervalMilliSecond();
        if (mWaterLastIntervalMilliSecond != null) {
            stmt.bindString(19, mWaterLastIntervalMilliSecond);
        }
        stmt.bindLong(20, entity.getIsSelect() ? 1L: 0L);
 
        String belongBaby = entity.getBelongBaby();
        if (belongBaby != null) {
            stmt.bindString(21, belongBaby);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AlertLowBean readEntity(Cursor cursor, int offset) {
        AlertLowBean entity = new AlertLowBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mTitle
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mRepeatType
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // mRepeatCode
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // mActive
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mWakeType
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // mRing
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // mAlertStatus
            cursor.getShort(offset + 9) != 0, // mIsEnabledAlert
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // medicineName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // mWaterBeginTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // mWaterBeginTimeConvert
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // mWaterEndTime
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // mWaterEndTimeConvert
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // mWaterUnits
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // mWaterInterval
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // mWaterIntervalMilliSecond
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // mWaterLastIntervalMilliSecond
            cursor.getShort(offset + 19) != 0, // isSelect
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20) // belongBaby
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AlertLowBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMRepeatType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMRepeatCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMActive(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMWakeType(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMRing(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMAlertStatus(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMIsEnabledAlert(cursor.getShort(offset + 9) != 0);
        entity.setMedicineName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setMWaterBeginTime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMWaterBeginTimeConvert(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMWaterEndTime(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setMWaterEndTimeConvert(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setMWaterUnits(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setMWaterInterval(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setMWaterIntervalMilliSecond(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setMWaterLastIntervalMilliSecond(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setIsSelect(cursor.getShort(offset + 19) != 0);
        entity.setBelongBaby(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AlertLowBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AlertLowBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AlertLowBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}