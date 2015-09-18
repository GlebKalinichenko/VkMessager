package com.example.gleb.vkmessager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gleb.vkmessager.R;
import com.example.gleb.vkmessager.fragment.FriendFragment;
import com.example.gleb.vkmessager.user.Friend;
import com.example.gleb.vkmessager.user.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleb on 27.08.15.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private SparseBooleanArray selectedItems;
    private List<Friend> friends;
    public Context context;

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView FIOTextView;
        ImageView photoImageView;

        FriendViewHolder(View itemView) {
            super(itemView);
            FIOTextView = (TextView)itemView.findViewById(R.id.FIO);
            photoImageView = (ImageView)itemView.findViewById(R.id.photoImageView);
        }
    }

    public FriendAdapter(List<Friend> friends, Context context){
        this.friends = friends;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend, viewGroup, false);
        FriendViewHolder pvh = new FriendViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder aboutViewHolder, int i) {
        aboutViewHolder.FIOTextView.setText(friends.get(i).getFirstName() + " " + friends.get(i).getLastName());
        Picasso.with(context).load(friends.get(i).getPhoto()).into(aboutViewHolder.photoImageView);
    }

    public void removeData(int position) {
        friends.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

}
