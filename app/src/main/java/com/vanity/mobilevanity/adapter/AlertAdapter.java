package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Notify;
import com.vanity.mobilevanity.view.AlertListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class AlertAdapter extends RecyclerView.Adapter<AlertListViewHolder> implements AlertListViewHolder.OnAlertItemClickListener {
    List<Notify> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Notify item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<Notify> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public AlertListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_item, parent, false);
        AlertListViewHolder holder = new AlertListViewHolder(view);
        holder.setOnAlertItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlertListViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAlertItemClick(View view, Notify data, int position) {
        if (mListener != null)
            mListener.onAdapterItemClick(view, data, position);
    }


    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, Notify item, int position);
    }

    OnAdapterItemClickListener mListener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.mListener = listener;
    }

}
