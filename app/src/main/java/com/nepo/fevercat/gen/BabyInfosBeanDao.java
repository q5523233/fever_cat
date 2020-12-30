package com.nepo.fevercat.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BABY_INFOS_BEAN".
*/
public class BabyInfosBeanDao extends AbstractDao<BabyInfosBean, Long> {

    public static final String TABLENAME = "BABY_INFOS_BEAN";

    /**
     * Properties of entity BabyInfosBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Birthday = new Property(0, String.class, "birthday", false, "BIRTHDAY");
        public final static Property Sex = new Property(1, String.class, "sex", false, "SEX");
        public final static Property HeadImageUrl = new Property(2, String.class, "headImageUrl", false, "HEAD_IMAGE_URL");
        public final static Property HeadImageId = new Property(3, String.class, "headImageId", false, "HEAD_IMAGE_ID");
        public final static Property Nickname = new Property(4, String.class, "nickname", false, "NICKNAME");
        public final static Property BabyId = new Property(5, String.class, "babyId", false, "BABY_ID");
        public final static Property Id = new Property(6, Long.class, "id", true, "_id");
        public final static Property AccountId = new Property(7, String.class, "accountId", false, "ACCOUNT_ID");
        public final static Property LocalId = new Property(8, String.class, "localId", false, "LOCAL_ID");
        public final static Property ControlStatus = new Property(9, String.class, "controlStatus", false, "CONTROL_STATUS");
        public final static Property IsAdd = new Property(10, boolean.class, "isAdd", false, "IS_ADD");
        public final static Property IsScaleBig = new Property(11, String.class, "isScaleBig", false, "IS_SCALE_BIG");
    }


    public BabyInfosBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BabyInfosBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BABY_INFOS_BEAN\" (" + //
                "\"BIRTHDAY\" TEXT," + // 0: birthday
                "\"SEX\" TEXT," + // 1: sex
                "\"HEAD_IMAGE_URL\" TEXT," + // 2: headImageUrl
                "\"HEAD_IMAGE_ID\" TEXT," + // 3: headImageId
                "\"NICKNAME\" TEXT," + // 4: nickname
                "\"BABY_ID\" TEXT," + // 5: babyId
                "\"_id\" INTEGER PRIMARY KEY ," + // 6: id
                "\"ACCOUNT_ID\" TEXT," + // 7: accountId
                "\"LOCAL_ID\" TEXT," + // 8: localId
                "\"CONTROL_STATUS\" TEXT," + // 9: controlStatus
                "\"IS_ADD\" INTEGER NOT NULL ," + // 10: isAdd
                "\"IS_SCALE_BIG\" TEXT);"); // 11: isScaleBig
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BABY_INFOS_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BabyInfosBean entity) {
        stmt.clearBindings();
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(1, birthday);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(2, sex);
        }
 
        String headImageUrl = entity.getHeadImageUrl();
        if (headImageUrl != null) {
            stmt.bindString(3, headImageUrl);
        }
 
        String headImageId = entity.getHeadImageId();
        if (headImageId != null) {
            stmt.bindString(4, headImageId);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(5, nickname);
        }
 
        String babyId = entity.getBabyId();
        if (babyId != null) {
            stmt.bindString(6, babyId);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(7, id);
        }
 
        String accountId = entity.getAccountId();
        if (accountId != null) {
            stmt.bindString(8, accountId);
        }
 
        String localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindString(9, localId);
        }
 
        String controlStatus = entity.getControlStatus();
        if (controlStatus != null) {
            stmt.bindString(10, controlStatus);
        }
        stmt.bindLong(11, entity.getIsAdd() ? 1L: 0L);
 
        String isScaleBig = entity.getIsScaleBig();
        if (isScaleBig != null) {
            stmt.bindString(12, isScaleBig);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BabyInfosBean entity) {
        stmt.clearBindings();
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(1, birthday);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(2, sex);
        }
 
        String headImageUrl = entity.getHeadImageUrl();
        if (headImageUrl != null) {
            stmt.bindString(3, headImageUrl);
        }
 
        String headImageId = entity.getHeadImageId();
        if (headImageId != null) {
            stmt.bindString(4, headImageId);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(5, nickname);
        }
 
        String babyId = entity.getBabyId();
        if (babyId != null) {
            stmt.bindString(6, babyId);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(7, id);
        }
 
        String accountId = entity.getAccountId();
        if (accountId != null) {
            stmt.bindString(8, accountId);
        }
 
        String localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindString(9, localId);
        }
 
        String controlStatus = entity.getControlStatus();
        if (controlStatus != null) {
            stmt.bindString(10, controlStatus);
        }
        stmt.bindLong(11, entity.getIsAdd() ? 1L: 0L);
 
        String isScaleBig = entity.getIsScaleBig();
        if (isScaleBig != null) {
            stmt.bindString(12, isScaleBig);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6);
    }    

    @Override
    public BabyInfosBean readEntity(Cursor cursor, int offset) {
        BabyInfosBean entity = new BabyInfosBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // birthday
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sex
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // headImageUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // headImageId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // nickname
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // babyId
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // id
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // accountId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // localId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // controlStatus
            cursor.getShort(offset + 10) != 0, // isAdd
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // isScaleBig
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BabyInfosBean entity, int offset) {
        entity.setBirthday(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSex(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setHeadImageUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHeadImageId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNickname(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBabyId(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setId(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setAccountId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setLocalId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setControlStatus(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setIsAdd(cursor.getShort(offset + 10) != 0);
        entity.setIsScaleBig(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BabyInfosBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BabyInfosBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BabyInfosBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}