package com.austria.developer.friends.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.austria.developer.friends.R;
import com.austria.developer.friends.model.Friend;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private static final String TAG = "FriendAdapter";

    private List<Friend> mFriendList;

    private OnFriendClickListener mListener;

    public interface OnFriendClickListener {
        void onClick(int position);
    }


    public FriendAdapter(List<Friend> friendList, OnFriendClickListener listener) {
        mFriendList = friendList;
        mListener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_friend_layout, parent, false);
        return new FriendViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = mFriendList.get(position);
        holder.mName.setText(friend.getName());
        holder.mAge.setText(String.valueOf(friend.getAge()));
        holder.mEmail.setText(friend.getEmail());
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mName, mAge, mEmail;
        private OnFriendClickListener mListener;

        FriendViewHolder(@NonNull View itemView, OnFriendClickListener listener) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_friend_name);
            mAge = itemView.findViewById(R.id.tv_friend_age);
            mEmail = itemView.findViewById(R.id.tv_friend_email);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getAdapterPosition());
        }
    }

    public void addList(List<Friend> friends) {
        mFriendList = friends;
        notifyDataSetChanged();
    }

}
