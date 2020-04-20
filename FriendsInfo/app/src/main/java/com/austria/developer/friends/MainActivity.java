package com.austria.developer.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.austria.developer.friends.adapter.FriendAdapter;
import com.austria.developer.friends.database.AppDatabase;
import com.austria.developer.friends.database.FriendContract;
import com.austria.developer.friends.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FriendAdapter.OnFriendClickListener {


    private static final String TAG = "MainActivity";

    private List<Friend> mFriendList = new ArrayList<>();
    private FriendAdapter mFriendAdapter;
    private RecyclerView mRecyclerView;

    private String[] projections = {
            FriendContract.Column._ID,
            FriendContract.Column.FRIEND_NAME,
            FriendContract.Column.FRIEND_AGE,
            FriendContract.Column.FRIEND_GENDER,
            FriendContract.Column.FRIEND_PHONE,
            FriendContract.Column.FRIEND_EMAIL
    };

    private String args = FriendContract.Column.FRIEND_NAME + "=?";
    private String[] selectionArgs = new String[]{"Areil Austrias"};
    private String selection = FriendContract.Column.FRIEND_NAME + " = 'Fiara Benienne Manalili'";
    private String sortOrder = FriendContract.Column.FRIEND_AGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase();
        EditText edtSearch = findViewById(R.id.edt_search_friend);
        mRecyclerView = findViewById(R.id.rv_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendAdapter = new FriendAdapter(new ArrayList<Friend>(), this);
        mRecyclerView.setAdapter(mFriendAdapter);
        select(projections, null, null, sortOrder);
        mFriendAdapter.addList(mFriendList);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String value) {
        List<Friend> filterList = new ArrayList<>();

        for (Friend friend : mFriendList) {
            if (friend.getName().toLowerCase().contains(value.toLowerCase())) {
                filterList.add(friend);
            }
        }

        mFriendAdapter.addList(filterList);
    }

    private void add(Friend friend) {
        ContentValues values = new ContentValues();
        values.put(FriendContract.Column.FRIEND_NAME, friend.getName());
        values.put(FriendContract.Column.FRIEND_AGE, friend.getAge());
        values.put(FriendContract.Column.FRIEND_GENDER, friend.getGender());
        values.put(FriendContract.Column.FRIEND_PHONE, friend.getPhone());
        values.put(FriendContract.Column.FRIEND_EMAIL, friend.getEmail());

        ContentResolver resolver = getContentResolver();
        resolver.insert(FriendContract.CONTENT_URI, values);
    }

    private void select(String[] projections, String selection, String[] selectionArgs, String sortOrder) {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(FriendContract.CONTENT_URI, projections, selection, selectionArgs, sortOrder);
        if (cursor != null) {
            Log.d(TAG, "select: row " + cursor.getCount());
            while (cursor.moveToNext()) {
                Log.d(TAG, "select: column " + cursor.getColumnCount());
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, "select: " + cursor.getColumnName(i) + " = " + cursor.getString(i));
                }
                Log.d(TAG, "select: ================================");
                mFriendList.add(new Friend(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            }
            cursor.close();
        }
    }

    private void update(Friend friend, String selection, String[] selectionArgs) {
        ContentValues values = new ContentValues();
        values.put(FriendContract.Column.FRIEND_NAME, friend.getName());
        values.put(FriendContract.Column.FRIEND_AGE, friend.getAge());
        values.put(FriendContract.Column.FRIEND_GENDER, friend.getGender());
        values.put(FriendContract.Column.FRIEND_PHONE, friend.getPhone());
        values.put(FriendContract.Column.FRIEND_EMAIL, friend.getEmail());

        ContentResolver resolver = getContentResolver();
        resolver.update(FriendContract.CONTENT_URI, values, selection, selectionArgs);
    }

    private void delete(String selection, String[] selectionArgs) {
        ContentResolver resolver = getContentResolver();
        resolver.delete(FriendContract.CONTENT_URI, selection, selectionArgs);
    }

    private void createDatabase() {
        AppDatabase database = AppDatabase.getInstance(this);
        database.getReadableDatabase();
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, "position = " + position, Toast.LENGTH_SHORT).show();
        Friend friend = mFriendList.get(position);
        Log.d(TAG, "onClick: friend " + friend.toString());
    }
}
//        add(new Friend("0", "Ariel Austria", 28, "Male", "0996565565", "yel@gmail.com"));
////        add(new Friend("0", "Faru Manalili", 5, "Male", "0996514151", "ru@gmail.com"));
////        update(new Friend("0", "Fiara Benienne Manalili", 7, "Female", "0996542416", "yen@gmail.com"),
////                args, selectionArgs);
//        select(projections, null, null, sortOrder);