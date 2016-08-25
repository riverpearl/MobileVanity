package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BannerData;
import com.vanity.mobilevanity.view.BannerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerViewHolder>
    implements BannerViewHolder.OnBannerItemClickListener {

    List<BannerData> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(BannerData item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<BannerData> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_banner, parent, false);
        BannerViewHolder holder = new BannerViewHolder(view);
        holder.setOnBannerItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, BannerData data, int position);
    }

    OnAdapterItemClickListener mListener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBannerItemClick(View view, BannerData data, int position) {
        if (mListener != null)
            mListener.onAdapterItemClick(view, data, position);
    }
}
