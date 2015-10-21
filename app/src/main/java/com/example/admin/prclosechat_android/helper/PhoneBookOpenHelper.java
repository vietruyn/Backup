package com.example.admin.prclosechat_android.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PhoneBookOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "REGISTRATION_DB";
    public static final String TABLE_NAME = "REGISTRATION_TABLE";
    public static final int VERSION = 2;
    public static final String KEY_ID = "_id";
    public static final String FNAME = "_fname";
    public static final String PNUMBER = "_phonenumber";
    public static final String EMAIL = "_email";
    public static final String TYPE = "_type";
    public static final String SCRIPT = "create table " + TABLE_NAME + " ("
            + KEY_ID + " integer primary key autoincrement, " + FNAME
            + " text not null, " + PNUMBER + " text not null, " + EMAIL + " text, " + TYPE + " text not null );";

    public PhoneBookOpenHelper(Context context, String name,
                               CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }




}
