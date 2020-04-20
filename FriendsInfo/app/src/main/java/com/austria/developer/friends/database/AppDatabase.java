package com.austria.developer.friends.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = "AppDatabase";

    private static final String DATABASE_NAME = "FriendsList.db";
    private static final int DATABASE_VERSION = 1;

    private AppDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppDatabase(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: started");
        String sSQL = "CREATE TABLE IF NOT EXISTS " + FriendContract.TABLE_NAME + " ("
                + FriendContract.Column._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + FriendContract.Column.FRIEND_NAME + " TEXT NOT NULL, "
                + FriendContract.Column.FRIEND_AGE + " INTEGER, "
                + FriendContract.Column.FRIEND_GENDER + " TEXT, "
                + FriendContract.Column.FRIEND_PHONE + " TEXT, "
                + FriendContract.Column.FRIEND_EMAIL + " TEXT);";
        Log.d(TAG, "onCreate: sSQL: " + sSQL);
        db.execSQL(sSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: started");
        switch (oldVersion) {
            case 1:
                break;
            default:
                throw new IllegalArgumentException("Unknown newVersion " + newVersion);
        }
        Log.d(TAG, "onUpgrade: stopped");
    }
}
