package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.view.CosmeticListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class CosmeticAdapter extends RecyclerView.Adapter<CosmeticListViewHolder> implements CosmeticListViewHolder.OnCosmeticListItemClickListener {
    List<Cosmetic> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Cosmetic item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<Cosmetic> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public CosmeticListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_cosmetic, parent, false);
        CosmeticListViewHolder holder = new CosmeticListViewHolder(view);
        holder.setOnCosmeticListItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(CosmeticListViewHolder holder, int position) {
        holder.setCosmeticList(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, Cosmetic data, int position);
    }

    OnAdapterItemClickListener mListener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onCosmeticListItemClick(View view, Cosmetic data, int position) {
        if (mListener != null)
            mListener.onAdapterItemClick(view, data, position);
    }
}
