package com.example.gleb.vkmessager.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gleb.vkmessager.R;
import com.example.gleb.vkmessager.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleb on 25.08.15.
 */
public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder> {
    private SparseBooleanArray selectedItems;
    private List<User> users;

    public static class AboutViewHolder extends RecyclerView.ViewHolder {
        EditText FIOTextView;
        EditText cityTextView;
        EditText countryTextView;
        EditText universityTextView;
        EditText facultyTextView;
        EditText bDateTextView;

        AboutViewHolder(View itemView) {
            super(itemView);
            FIOTextView = (EditText)itemView.findViewById(R.id.FIO);
            cityTextView = (EditText)itemView.findViewById(R.id.city);
            countryTextView = (EditText)itemView.findViewById(R.id.country);
            universityTextView = (EditText)itemView.findViewById(R.id.university);
            facultyTextView = (EditText)itemView.findViewById(R.id.faculty);
            bDateTextView = (EditText)itemView.findViewById(R.id.bDate);
        }
    }

    public AboutAdapter(List<User> users){
        this.users = users;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AboutViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_about, viewGroup, false);
        AboutViewHolder pvh = new AboutViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AboutViewHolder aboutViewHolder, int i) {
        aboutViewHolder.FIOTextView.setText(users.get(i).getFirstName() + " " + users.get(i).getLastName());
        aboutViewHolder.cityTextView.setText(users.get(i).getCity());
        aboutViewHolder.countryTextView.setText(users.get(i).getCountry());
        aboutViewHolder.universityTextView.setText(users.get(i).getUniversity());
        aboutViewHolder.facultyTextView.setText(users.get(i).getFaculty());
        aboutViewHolder.bDateTextView.setText(users.get(i).getbDate());
    }

    public void removeData(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
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
