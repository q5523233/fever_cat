package com.nepo.fevercat.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.nepo.fevercat.ui.real.bean.TemperaturesBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEMPERATURES_BEAN".
*/
public class TemperaturesBeanDao extends AbstractDao<TemperaturesBean, Long> {

    public static final String TABLENAME = "TEMPERATURES_BEAN";

    /**
     * Properties of entity TemperaturesBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BabyId = new Property(0, String.class, "babyId", false, "BABY_ID");
        public final static Property Temperature = new Property(1, String.class, "temperature", false, "TEMPERATURE");
        public final static Property TemperatureRight = new Property(2, String.class, "temperatureRight", false, "TEMPERATURE_RIGHT");
        public final static Property TempHex = new Property(3, String.class, "tempHex", false, "TEMP_HEX");
        public final static Property TempTime = new Property(4, String.class, "tempTime", false, "TEMP_TIME");
        public final static Property Time = new Property(5, long.class, "time", false, "TIME");
        public final static Property Id = new Property(6, Long.class, "id", true, "_id");
        public final static Property LocalBabyId = new Property(7, String.class, "localBabyId", false, "LOCAL_BABY_ID");
        public final static Property AccountId = new Property(8, String.class, "accountId", false, "ACCOUNT_ID");
        public final static Property LeftAdjustValue = new Property(9, String.class, "leftAdjustValue", false, "LEFT_ADJUST_VALUE");
        public final static Property RightAdjustValue = new Property(10, String.class, "RightAdjustValue", false, "RIGHT_ADJUST_VALUE");
    }


    public TemperaturesBeanDao(DaoConfig config) {
        super(config);
    }
    
    public TemperaturesBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEMPERATURES_BEAN\" (" + //
                "\"BABY_ID\" TEXT," + // 0: babyId
                "\"TEMPERATURE\" TEXT," + // 1: temperature
                "\"TEMPERATURE_RIGHT\" TEXT," + // 2: temperatureRight
                "\"TEMP_HEX\" TEXT," + // 3: tempHex
                "\"TEMP_TIME\" TEXT," + // 4: tempTime
                "\"TIME\" INTEGER NOT NULL ," + // 5: time
                "\"_id\" INTEGER PRIMARY KEY ," + // 6: id
                "\"LOCAL_BABY_ID\" TEXT," + // 7: localBabyId
                "\"ACCOUNT_ID\" TEXT," + // 8: accountId
                "\"LEFT_ADJUST_VALUE\" TEXT," + // 9: leftAdjustValue
                "\"RIGHT_ADJUST_VALUE\" TEXT);"); // 10: RightAdjustValue
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEMPERATURES_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TemperaturesBean entity) {
        stmt.clearBindings();
 
        String babyId = entity.getBabyId();
        if (babyId != null) {
            stmt.bindString(1, babyId);
        }
 
        String temperature = entity.getTemperature();
        if (temperature != null) {
            stmt.bindString(2, temperature);
        }
 
        String temperatureRight = entity.getTemperatureRight();
        if (temperatureRight != null) {
            stmt.bindString(3, temperatureRight);
        }
 
        String tempHex = entity.getTempHex();
        if (tempHex != null) {
            stmt.bindString(4, tempHex);
        }
 
        String tempTime = entity.getTempTime();
        if (tempTime != null) {
            stmt.bindString(5, tempTime);
        }
        stmt.bindLong(6, entity.getTime());
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(7, id);
        }
 
        String localBabyId = entity.getLocalBabyId();
        if (localBabyId != null) {
            stmt.bindString(8, localBabyId);
        }
 
        String accountId = entity.getAccountId();
        if (accountId != null) {
            stmt.bindString(9, accountId);
        }
 
        String leftAdjustValue = entity.getLeftAdjustValue();
        if (leftAdjustValue != null) {
            stmt.bindString(10, leftAdjustValue);
        }
 
        String RightAdjustValue = entity.getRightAdjustValue();
        if (RightAdjustValue != null) {
            stmt.bindString(11, RightAdjustValue);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TemperaturesBean entity) {
        stmt.clearBindings();
 
        String babyId = entity.getBabyId();
        if (babyId != null) {
            stmt.bindString(1, babyId);
        }
 
        String temperature = entity.getTemperature();
        if (temperature != null) {
            stmt.bindString(2, temperature);
        }
 
        String temperatureRight = entity.getTemperatureRight();
        if (temperatureRight != null) {
            stmt.bindString(3, temperatureRight);
        }
 
        String tempHex = entity.getTempHex();
        if (tempHex != null) {
            stmt.bindString(4, tempHex);
        }
 
        String tempTime = entity.getTempTime();
        if (tempTime != null) {
            stmt.bindString(5, tempTime);
        }
        stmt.bindLong(6, entity.getTime());
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(7, id);
        }
 
        String localBabyId = entity.getLocalBabyId();
        if (localBabyId != null) {
            stmt.bindString(8, localBabyId);
        }
 
        String accountId = entity.getAccountId();
        if (accountId != null) {
            stmt.bindString(9, accountId);
        }
 
        String leftAdjustValue = entity.getLeftAdjustValue();
        if (leftAdjustValue != null) {
            stmt.bindString(10, leftAdjustValue);
        }
 
        String RightAdjustValue = entity.getRightAdjustValue();
        if (RightAdjustValue != null) {
            stmt.bindString(11, RightAdjustValue);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6);
    }    

    @Override
    public TemperaturesBean readEntity(Cursor cursor, int offset) {
        TemperaturesBean entity = new TemperaturesBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // babyId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // temperature
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // temperatureRight
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // tempHex
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // tempTime
            cursor.getLong(offset + 5), // time
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // id
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // localBabyId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // accountId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // leftAdjustValue
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // RightAdjustValue
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TemperaturesBean entity, int offset) {
        entity.setBabyId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTemperature(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTemperatureRight(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTempHex(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTempTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTime(cursor.getLong(offset + 5));
        entity.setId(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setLocalBabyId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAccountId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLeftAdjustValue(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setRightAdjustValue(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TemperaturesBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TemperaturesBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TemperaturesBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}