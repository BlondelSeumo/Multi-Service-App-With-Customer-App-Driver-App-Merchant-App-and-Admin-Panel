package com.ourdevelops.ornids.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.ourdevelops.ornids.models.FavouriteModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favouriteourride.db";
    public static final String TABLE_FAVOURITE_NAME = "favourite";

    public static final String KEY_ID_KEY = "id";
    public static final String KEY_ID = "news_id";
    public static final String KEY_USERID = "userid";
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE = "news_images";
    public static final String KEY_KATEGORI = "category";
    public static final String KEY_CONTENT = "content";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + TABLE_FAVOURITE_NAME + "("
                + KEY_ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ID + " TEXT,"
                + KEY_USERID + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_KATEGORI + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_FAVOURITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE_NAME);
        // Create tables again
        onCreate(db);
    }

    public boolean getFavouriteById(String story_id) {
        boolean count = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{story_id};
        Cursor cursor = db.rawQuery("SELECT news_id FROM favourite WHERE news_id=?", args);
        if (cursor.moveToFirst()) {
            count = true;
        }
        cursor.close();
        db.close();
        return count;
    }

    public boolean getFavouriteByMyid(String my_id) {
        boolean count = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{my_id};
        Cursor cursor = db.rawQuery("SELECT userid FROM favourite WHERE userid=?", args);
        if (cursor.moveToFirst()) {
            count = true;
        }
        cursor.close();
        db.close();
        return count;
    }

    public void removeFavouriteById(String _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  favourite " + " WHERE " + KEY_ID + " = " + _id);
        db.close();
    }

    public long addFavourite(String TableName, ContentValues contentvalues, String s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TableName, s1, contentvalues);
    }

    public ArrayList<FavouriteModel> getFavourite() {
        ArrayList<FavouriteModel> chapterList = new ArrayList<>();
        String selectQuery = "SELECT *  FROM "
                + TABLE_FAVOURITE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FavouriteModel contact = new FavouriteModel();
                contact.setIdberita(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
                contact.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)));
                contact.setUserid(cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERID)));
                contact.setFotoberita(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE)));
                contact.setContent(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)));
                contact.setKategori(cursor.getString(cursor.getColumnIndexOrThrow(KEY_KATEGORI)));
                chapterList.add(contact);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return chapterList;
    }
}
