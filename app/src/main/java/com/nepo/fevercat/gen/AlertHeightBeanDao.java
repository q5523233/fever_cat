package com.nepo.fevercat.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALERT_HEIGHT_BEAN".
*/
public class AlertHeightBeanDao extends AbstractDao<AlertHeightBean, Long> {

    public static final String TABLENAME = "ALERT_HEIGHT_BEAN";

    /**
     * Properties of entity AlertHeightBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property STemp = new Property(1, String.class, "sTemp", false, "S_TEMP");
        public final static Property SMillisecond = new Property(2, String.class, "sMillisecond", false, "S_MILLISECOND");
        public final static Property STimeTip = new Property(3, String.class, "sTimeTip", false, "S_TIME_TIP");
        public final static Property SCheckStatus = new Property(4, boolean.class, "sCheckStatus", false, "S_CHECK_STATUS");
        public final static Property SLastRingMillisecond = new Property(5, String.class, "sLastRingMillisecond", false, "S_LAST_RING_MILLISECOND");
        public final static Property SGtTemp = new Property(6, String.class, "sGtTemp", false, "S_GT_TEMP");
        public final static Property SLeTemp = new Property(7, String.class, "sLeTemp", false, "S_LE_TEMP");
        public final static Property SSelectType = new Property(8, String.class, "sSelectType", false, "S_SELECT_TYPE");
        public final static Property BelongBaby = new Property(9, String.class, "belongBaby", false, "BELONG_BABY");
    }


    public AlertHeightBeanDao(DaoConfig config) {
        super(config);
    }
    
    public AlertHeightBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALERT_HEIGHT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"S_TEMP\" TEXT," + // 1: sTemp
                "\"S_MILLISECOND\" TEXT," + // 2: sMillisecond
                "\"S_TIME_TIP\" TEXT," + // 3: sTimeTip
                "\"S_CHECK_STATUS\" INTEGER NOT NULL ," + // 4: sCheckStatus
                "\"S_LAST_RING_MILLISECOND\" TEXT," + // 5: sLastRingMillisecond
                "\"S_GT_TEMP\" TEXT," + // 6: sGtTemp
                "\"S_LE_TEMP\" TEXT," + // 7: sLeTemp
                "\"S_SELECT_TYPE\" TEXT," + // 8: sSelectType
                "\"BELONG_BABY\" TEXT);"); // 9: belongBaby
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALERT_HEIGHT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AlertHeightBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sTemp = entity.getSTemp();
        if (sTemp != null) {
            stmt.bindString(2, sTemp);
        }
 
        String sMillisecond = entity.getSMillisecond();
        if (sMillisecond != null) {
            stmt.bindString(3, sMillisecond);
        }
 
        String sTimeTip = entity.getSTimeTip();
        if (sTimeTip != null) {
            stmt.bindString(4, sTimeTip);
        }
        stmt.bindLong(5, entity.getSCheckStatus() ? 1L: 0L);
 
        String sLastRingMillisecond = entity.getSLastRingMillisecond();
        if (sLastRingMillisecond != null) {
            stmt.bindString(6, sLastRingMillisecond);
        }
 
        String sGtTemp = entity.getSGtTemp();
        if (sGtTemp != null) {
            stmt.bindString(7, sGtTemp);
        }
 
        String sLeTemp = entity.getSLeTemp();
        if (sLeTemp != null) {
            stmt.bindString(8, sLeTemp);
        }
 
        String sSelectType = entity.getSSelectType();
        if (sSelectType != null) {
            stmt.bindString(9, sSelectType);
        }
 
        String belongBaby = entity.getBelongBaby();
        if (belongBaby != null) {
            stmt.bindString(10, belongBaby);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AlertHeightBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sTemp = entity.getSTemp();
        if (sTemp != null) {
            stmt.bindString(2, sTemp);
        }
 
        String sMillisecond = entity.getSMillisecond();
        if (sMillisecond != null) {
            stmt.bindString(3, sMillisecond);
        }
 
        String sTimeTip = entity.getSTimeTip();
        if (sTimeTip != null) {
            stmt.bindString(4, sTimeTip);
        }
        stmt.bindLong(5, entity.getSCheckStatus() ? 1L: 0L);
 
        String sLastRingMillisecond = entity.getSLastRingMillisecond();
        if (sLastRingMillisecond != null) {
            stmt.bindString(6, sLastRingMillisecond);
        }
 
        String sGtTemp = entity.getSGtTemp();
        if (sGtTemp != null) {
            stmt.bindString(7, sGtTemp);
        }
 
        String sLeTemp = entity.getSLeTemp();
        if (sLeTemp != null) {
            stmt.bindString(8, sLeTemp);
        }
 
        String sSelectType = entity.getSSelectType();
        if (sSelectType != null) {
            stmt.bindString(9, sSelectType);
        }
 
        String belongBaby = entity.getBelongBaby();
        if (belongBaby != null) {
            stmt.bindString(10, belongBaby);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AlertHeightBean readEntity(Cursor cursor, int offset) {
        AlertHeightBean entity = new AlertHeightBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sTemp
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sMillisecond
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // sTimeTip
            cursor.getShort(offset + 4) != 0, // sCheckStatus
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sLastRingMillisecond
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sGtTemp
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // sLeTemp
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // sSelectType
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // belongBaby
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AlertHeightBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSTemp(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSMillisecond(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSTimeTip(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSCheckStatus(cursor.getShort(offset + 4) != 0);
        entity.setSLastRingMillisecond(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSGtTemp(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSLeTemp(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSSelectType(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setBelongBaby(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AlertHeightBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AlertHeightBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AlertHeightBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
