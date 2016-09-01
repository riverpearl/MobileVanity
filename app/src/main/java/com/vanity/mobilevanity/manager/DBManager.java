package com.vanity.mobilevanity.manager;

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

    private final static String DB_NAME = "cosmetic_item_db";
    private final static int DB_VERSION = 1;

    public DBManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ DBContract.CosmeticItem.TABLE+" (" +
                DBContract.CosmeticItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.CosmeticItem.COLUMN_COSMETIC_ID + " INTEGER," +
                DBContract.CosmeticItem.COLUMN_REG_DATE + " TEXT," +
                DBContract.CosmeticItem.COLUMN_USEBY_DATE + " TEXT);";
        db.execSQL(sql);

        sql = "CREATE TABLE "+ DBContract.Notify.TABLE+" (" +
                DBContract.Notify._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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

    public long insertCosmeticItem(Cosmetic cosmetic) {
        return 0;
    }
}
