package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.view.CosmeticViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<CosmeticViewHolder>
    implements CosmeticViewHolder.OnCosmeticItemClickListener {

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
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public CosmeticViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cosmetic, parent, false);
        CosmeticViewHolder holder = new CosmeticViewHolder(view);
        holder.setOnLikeItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(CosmeticViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onCosmeticItemClick(View view, Cosmetic data, int position) {
        if (mListener != null)
            mListener.onAdapterItemClick(view, data, position);
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, Cosmetic data, int position);
    }

    OnAdapterItemClickListener mListener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }
}
