package com.vanity.mobilevanity.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vanity.mobilevanity.MyApplication;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.DBContract;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DBManager extends SQLiteOpenHelper {
    private DBManager instance;

    public DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();

        return instance;
    }

    private ContentValues values = new ContentValues();

    private final static String DB_NAME = "cosmetic_item_db";
    private final static int DB_VERSION = 1;

    public DBManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ DBContract.CosmeticItem.TABLE+" (" +
                DBContract.CosmeticItem._ID + " LONG PRIMARY KEY AUTOINCREMENT," +
                DBContract.CosmeticItem.COLUMN_COSMETIC_ID + " LONG," +
                DBContract.CosmeticItem.COLUMN_REG_DATE + " TEXT," +
                DBContract.CosmeticItem.COLUMN_USEBY_DATE + " TEXT);";
        db.execSQL(sql);

        sql = "CREATE TABLE "+ DBContract.Notify.TABLE+" (" +
                DBContract.Notify._ID + " LONG PRIMARY KEY AUTOINCREMENT," +
                DBContract.Notify.COLUMN_TYPE + " TEXT," +
                DBContract.Notify.COLUMN_CONTENT_ID + " INTEGER," +
                DBContract.Notify.COLUMN_MESSAGE + " TEXT," +
                DBContract.Notify.COLUMN_DATE + "TEXT," +
                DBContract.Notify.COLUMN_LAST_NOTIFY_ID + " INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertCosmeticItem(long id, String reg, String useby) {
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContract.CosmeticItem.COLUMN_COSMETIC_ID, id);
        values.put(DBContract.CosmeticItem.COLUMN_REG_DATE, reg);
        values.put(DBContract.CosmeticItem.COLUMN_USEBY_DATE, useby);
        return db.insert(DBContract.CosmeticItem.TABLE, null, values);
    }

    public int updateCosmeticItem(long cid, String reg, String useby) {
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContract.CosmeticItem.COLUMN_REG_DATE, reg);
        values.put(DBContract.CosmeticItem.COLUMN_USEBY_DATE, useby);
        String where = DBContract.CosmeticItem.COLUMN_COSMETIC_ID + " = ?";
        String[] args = { cid + "" };
        return db.update(DBContract.CosmeticItem.TABLE, values, where, args);
    }

    public int deleteCosmeticItem(long cid) {
        SQLiteDatabase db = getWritableDatabase();
        String where = DBContract.CosmeticItem.COLUMN_COSMETIC_ID + " = ?";
        String[] args = { cid + "" };
        return db.delete(DBContract.CosmeticItem.TABLE, where, args);
    }
}
