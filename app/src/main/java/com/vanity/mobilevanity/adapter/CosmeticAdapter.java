package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.view.MyCosmeticViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class CosmeticAdapter extends RecyclerView.Adapter<MyCosmeticViewHolder>
        implements MyCosmeticViewHolder.OnCosmeticListItemClickListener, MyCosmeticViewHolder.OnCosmeticListItemLongClickListener {
    List<CosmeticItem> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(CosmeticItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<CosmeticItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public MyCosmeticViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_cosmetic, parent, false);
        MyCosmeticViewHolder holder = new MyCosmeticViewHolder(view);
        holder.setOnCosmeticListItemClickListener(this);
        holder.setOnCosmeticListItemLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyCosmeticViewHolder holder, int position) {
        holder.setCosmeticList(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, CosmeticItem data, int position);
    }

    OnAdapterItemClickListener mListener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onCosmeticListItemClick(View view, CosmeticItem data, int position) {
        if (mListener != null)
            mListener.onAdapterItemClick(view, data, position);
    }

    public interface  OnAdapterItemLongClickListener {
        public void onAdapterItemLongClick(View view, CosmeticItem data, int position);
    }

    OnAdapterItemLongClickListener longClickListener;
    public void setOnAdapterItemLongClickListener(OnAdapterItemLongClickListener listener) {
        longClickListener = listener;
    }

    @Override
    public void onCosmeticListItemLongClick(View view, CosmeticItem data, int position) {
        if (longClickListener != null)
            longClickListener.onAdapterItemLongClick(view, data, position);
    }
}
