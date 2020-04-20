package com.austria.developer.friends.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class FriendContract {

    public static final String TABLE_NAME = "Friends";

    public class Column {

        private Column() {
            //empty constructor
        }

        public static final String _ID = BaseColumns._ID;
        public static final String FRIEND_NAME = "Name";
        public static final String FRIEND_AGE = "Age";
        public static final String FRIEND_GENDER = "Gender";
        public static final String FRIEND_PHONE = "Phone";
        public static final String FRIEND_EMAIL = "Email";

    }

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AppProvider.CONTENT_AUTHORITY_URI + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AppProvider.CONTENT_AUTHORITY_URI + "." + TABLE_NAME;

    public static final Uri CONTENT_URI = Uri.withAppendedPath(AppProvider.CONTENT_AUTHORITY_URI, TABLE_NAME);

    public static Uri buildFriendUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static long getFriendId(Uri uri) {
        return ContentUris.parseId(uri);
    }


}
