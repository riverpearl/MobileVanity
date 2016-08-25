package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.view.BeautyTipItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class BeautyTipAdapter extends RecyclerView.Adapter<BeautyTipItemViewHolder> {
    List<BeautyTip> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(BeautyTip b) {
        items.add(b);
        notifyDataSetChanged();
    }

    public void addAll(List<BeautyTip> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public BeautyTipItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_beauty_tip_item, parent, false);
        return new BeautyTipItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeautyTipItemViewHolder holder, int position) {
        holder.setBeautyTipItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
