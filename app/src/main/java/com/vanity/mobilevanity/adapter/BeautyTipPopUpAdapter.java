package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.view.BeautyTipPopUpViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class BeautyTipPopUpAdapter extends RecyclerView.Adapter<BeautyTipPopUpViewHolder> {
    List<Comment> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Comment item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<Comment> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public BeautyTipPopUpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_beauty_tip_pop_up, parent, false);
        BeautyTipPopUpViewHolder holder = new BeautyTipPopUpViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BeautyTipPopUpViewHolder holder, int position) {
        holder.setPopUp(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
