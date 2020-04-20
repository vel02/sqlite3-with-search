package com.austria.developer.friends.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppProvider extends ContentProvider {

    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;

    public static final String CONTENT_AUTHORITY = "com.austria.developer.friends.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private UriMatcher mUriMatcher = buildUriMatcher();

    private static final int FRIEND = 100;
    private static final int FRIEND_ID = 101;

    private UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, FriendContract.TABLE_NAME, FRIEND);
        matcher.addURI(CONTENT_AUTHORITY, FriendContract.TABLE_NAME + "/#", FRIEND_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: started");
        int match = mUriMatcher.match(uri);
        Log.d(TAG, "query: match " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (match) {
            case FRIEND:
                queryBuilder.setTables(FriendContract.TABLE_NAME);
                break;
            case FRIEND_ID:
                queryBuilder.setTables(FriendContract.TABLE_NAME);
                long id = FriendContract.getFriendId(uri);
                queryBuilder.appendWhere(FriendContract.Column._ID + " = " + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri.toString());
        }

        SQLiteDatabase database = mOpenHelper.getReadableDatabase();
        return queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType: started");
        int match = mUriMatcher.match(uri);
        switch (match) {
            case FRIEND:
                return FriendContract.CONTENT_TYPE;
            case FRIEND_ID:
                return FriendContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri.toString());
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: started");
        int match = mUriMatcher.match(uri);

        SQLiteDatabase database;

        Uri recordUri;
        long recordId;
        switch (match) {
            case FRIEND:
                database = mOpenHelper.getWritableDatabase();
                recordId = database.insert(FriendContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    recordUri = FriendContract.buildFriendUri(recordId);
                } else {
                    throw new IllegalStateException("Failed to insert data " + uri.toString());
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri.toString());
        }
        Log.d(TAG, "insert: stopped");
        return recordUri;
    }

    //DELETE Contacts WHERE id = 1 AND name = 'Yen';
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = mUriMatcher.match(uri);

        SQLiteDatabase database;
        String selectionCriteria;
        int recordId;
        switch (match) {
            case FRIEND:
                database = mOpenHelper.getWritableDatabase();
                recordId = database.delete(FriendContract.TABLE_NAME, selection, selectionArgs);
                break;
            case FRIEND_ID:
                database = mOpenHelper.getWritableDatabase();
                long id = FriendContract.getFriendId(uri);
                selectionCriteria = FriendContract.Column._ID + " = " + id;
                if (selection != null && selection.length() != 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                recordId = database.delete(FriendContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri.toString());
        }
        Log.d(TAG, "delete: stopped");
        return recordId;
    }

    //UPDATE Contacts SET email = 'yen@gmail.com' WHERE name ='Yen';
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: started");
        int match = mUriMatcher.match(uri);

        SQLiteDatabase database;
        String selectionCriteria;
        int recordId;
        switch (match) {
            case FRIEND:
                database = mOpenHelper.getWritableDatabase();
                recordId = database.update(FriendContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FRIEND_ID:
                database = mOpenHelper.getWritableDatabase();
                long id = FriendContract.getFriendId(uri);
                selectionCriteria = FriendContract.Column._ID + " = " + id;
                if (selection != null && selection.length() != 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                recordId = database.update(FriendContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }
        return recordId;
    }
}
