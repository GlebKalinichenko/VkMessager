package com.example.gleb.vkmessager.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gleb.vkmessager.R;
import com.example.gleb.vkmessager.user.Dialog;
import com.example.gleb.vkmessager.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleb on 27.08.15.
 */
public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogViewHolder> {
    private SparseBooleanArray selectedItems;
    private List<Dialog> dialogs;

    public static class DialogViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView bodyTextView;

        DialogViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.title);
            bodyTextView = (TextView)itemView.findViewById(R.id.body);
        }
    }

    public DialogAdapter(List<Dialog> dialogs){
        this.dialogs = dialogs;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dialog, viewGroup, false);
        DialogViewHolder pvh = new DialogViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DialogViewHolder aboutViewHolder, int i) {
        aboutViewHolder.titleTextView.setText(dialogs.get(i).getTitle());
        aboutViewHolder.bodyTextView.setText(dialogs.get(i).getBody());
    }

    public void removeData(int position) {
        dialogs.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return dialogs.size();
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
