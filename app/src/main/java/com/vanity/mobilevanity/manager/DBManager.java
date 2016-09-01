package com.vanity.mobilevanity.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vanity.mobilevanity.MyApplication;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.DBContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DBManager extends SQLiteOpenHelper {
    private static DBManager instance;

    public static DBManager getInstance() {
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
        String sql = "CREATE TABLE "+ DBContract.CosmeticItem.TABLE + " (" +
                DBContract.CosmeticItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.CosmeticItem.COLUMN_SERVER_ID + " INTEGER," +
                DBContract.CosmeticItem.COLUMN_COSMETIC_ID + " INTEGER," +
                DBContract.CosmeticItem.COLUMN_REG_DATE + " TEXT," +
                DBContract.CosmeticItem.COLUMN_USEBY_DATE + " TEXT);";
        db.execSQL(sql);

        sql = "CREATE TABLE "+ DBContract.Notify.TABLE + " (" +
                DBContract.Notify._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.Notify.COLUMN_COSMETIC_ITEM_ID + " INTEGER," +
                DBContract.Notify.COLUMN_MESSAGE + " TEXT," +
                DBContract.Notify.COLUMN_DATE + " TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertCosmeticItem(long cid, String dateAdded, int term) {
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContract.CosmeticItem.COLUMN_COSMETIC_ID, cid);
        values.put(DBContract.CosmeticItem.COLUMN_REG_DATE, dateAdded);
        values.put(DBContract.CosmeticItem.COLUMN_USEBY_DATE, calculateUseby(dateAdded, term));
        return db.insert(DBContract.CosmeticItem.TABLE, null, values);
    }

    public int updateCosmeticItem(long sid, String dateAdded, int term) {
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContract.CosmeticItem.COLUMN_REG_DATE, dateAdded);
        values.put(DBContract.CosmeticItem.COLUMN_USEBY_DATE, calculateUseby(dateAdded, term));
        String where = DBContract.CosmeticItem.COLUMN_COSMETIC_ID + " = ?";
        String[] args = { sid + "" };
        return db.update(DBContract.CosmeticItem.TABLE, values, where, args);
    }

    public int deleteCosmeticItem(long sid) {
        SQLiteDatabase db = getWritableDatabase();
        String where = DBContract.CosmeticItem.COLUMN_SERVER_ID + " = ?";
        String[] args = { sid + "" };
        return db.delete(DBContract.CosmeticItem.TABLE, where, args);
    }

    private String calculateUseby(String start, int term) {
        Calendar addedDate = Calendar.getInstance();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSZ");
        try {
            addedDate.setTime(form.parse(start));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addedDate.add(Calendar.DATE, term);
        String usebyDate = form.format(addedDate.getTime());
        return usebyDate;
    }

    public long insertNotify(long cid, String message, String date) {
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContract.Notify.COLUMN_COSMETIC_ITEM_ID, cid);
        values.put(DBContract.Notify.COLUMN_MESSAGE, message);
        values.put(DBContract.Notify.COLUMN_DATE, date);
        return db.insert(DBContract.CosmeticItem.TABLE, null, values);
    }

    public Cursor selectNotify(String lastDate) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { DBContract.Notify.COLUMN_COSMETIC_ITEM_ID, DBContract.Notify.COLUMN_MESSAGE };
        String orderBy = DBContract.Notify.COLUMN_DATE + " DESC";
        Cursor c = db.query(DBContract.Notify.TABLE, columns, null, null, null, null, orderBy);
        return c;
    }
}
