package com.austria.developer.friends.util;

import androidx.recyclerview.widget.DiffUtil;

import com.austria.developer.friends.model.Friend;

import java.util.List;

public class FriendsDiffUtility extends DiffUtil.Callback {

    private List<Friend> oldList;
    private List<Friend> newList;

    public FriendsDiffUtility(List<Friend> oldList, List<Friend> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
