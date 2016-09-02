package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.view.MyBeautyTipViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class MyBeautyTipAdapter extends RecyclerView.Adapter<MyBeautyTipViewHolder>
    implements MyBeautyTipViewHolder.OnMyBeautyTipItemClickListener, MyBeautyTipViewHolder.OnMyBeautyTipItemLongClickListener {
    private List<BeautyTip> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(BeautyTip item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<BeautyTip> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public MyBeautyTipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_beauty_tip, parent, false);
        MyBeautyTipViewHolder holder = new MyBeautyTipViewHolder(view);
        holder.setOnMyBeautyTipItemClickListener(this);
        holder.setOnMyBeautyTipItemLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyBeautyTipViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, BeautyTip data, int position);
    }

    OnAdapterItemClickListener mListener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onMyBeautyTipItemClick(View view, BeautyTip data, int position) {
        if (mListener != null)
            mListener.onAdapterItemClick(view, data, position);
    }

    public interface OnAdapterItemLongClickListener {
        public void onAdapterItemLongClick(View view, BeautyTip data, int position);
    }

    OnAdapterItemLongClickListener longClickListener;
    public void setOnAdapterItemLongClickListener(OnAdapterItemLongClickListener listener) {
        longClickListener = listener;
    }

    @Override
    public void onMyBeautyTipItemLongClick(View view, BeautyTip data, int position) {
        if (mListener != null)
            longClickListener.onAdapterItemLongClick(view, data, position);
    }
}
