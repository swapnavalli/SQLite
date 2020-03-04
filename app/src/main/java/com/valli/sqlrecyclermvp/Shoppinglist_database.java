package com.valli.sqlrecyclermvp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.valli.sqlrecyclermvp.Shoppinglist_entity.*;

import androidx.annotation.Nullable;

public class Shoppinglist_database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shoppinglist.db";
    public static final int DATABASE_VERSION = 1;

    public Shoppinglist_database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SHOPPINGLIST_TABLE = "CREATE TABLE " +
                Shoppinglist_entries.TABLE_NAME + " (" +
                Shoppinglist_entries._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Shoppinglist_entries.COLUMN_NAME + " TEXT NOT NULL, " +
                Shoppinglist_entries.COLUMN_VALUE + " INTEGER NOT NULL, " +
                Shoppinglist_entries.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_SHOPPINGLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Shoppinglist_entries.TABLE_NAME);
        onCreate(db);
    }
}
